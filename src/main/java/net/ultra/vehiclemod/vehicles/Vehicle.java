package net.ultra.vehiclemod.vehicles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.FuelTank;
import net.ultra.vehiclemod.vehicles.components.entity.seat.Seat;
import net.ultra.vehiclemod.vehicles.components.entity.trunk.Trunk;

import java.util.ArrayList;

/**
 *
 */
public abstract class Vehicle extends Entity {
    private static final double TPS = 20;

    // Vehicle physics properties
    public final double MAX_SPEED;
    protected double speed = 0;
    public final double BRAKE_POWER;
    private static final double COASTING_PROPORTIONALITY = 0.2;
    public final double MAX_EXPLOSION_POWER;
    protected final double ACCELERATION_TICKS;
    private static final double GRAVITY_ACCELERATION_PER_TICK = -0.08; // this is the default gravity acceleration in Minecraft
    private boolean noControl = true;

    protected final double FUEL_CONSUMPTION_RATE;
    protected double fuelTicks = 0;

    protected static final int DEFAULT_ACCELERATION_TICKS = 100;

    protected final ArrayList<Seat> SEATS = new ArrayList<>();
    protected FuelTank tank = null;
    protected Trunk trunk = null;

    protected Vehicle(
        EntityType<? extends Vehicle> type,
        World world,
        double MAX_SPEED,
        double BRAKE_POWER,
        double MAX_EXPLOSION_POWER,
        double FUEL_CONSUMPTION_RATE
    ) {
        super(type, world);

        this.MAX_SPEED = MAX_SPEED / TPS;
        this.BRAKE_POWER = BRAKE_POWER / Math.pow(TPS, 2);
        this.MAX_EXPLOSION_POWER = MAX_EXPLOSION_POWER;
        this.FUEL_CONSUMPTION_RATE = FUEL_CONSUMPTION_RATE * TPS;
        ACCELERATION_TICKS = DEFAULT_ACCELERATION_TICKS;

        createSeats();
    }

    protected Vehicle(
        EntityType<? extends Vehicle> type,
        World world,
        double MAX_SPEED,
        double BRAKE_POWER,
        double MAX_EXPLOSION_POWER,
        double ACCELERATION_TICKS,
        double FUEL_CONSUMPTION_RATE
    ) {
        super(type, world);

        this.MAX_SPEED = MAX_SPEED / TPS;
        this.BRAKE_POWER = BRAKE_POWER / Math.pow(TPS, 2);
        this.MAX_EXPLOSION_POWER = MAX_EXPLOSION_POWER;
        this.ACCELERATION_TICKS = ACCELERATION_TICKS;
        this.FUEL_CONSUMPTION_RATE = FUEL_CONSUMPTION_RATE * TPS;

        createSeats();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        RegistryOps<NbtElement> registryOps = this.getRegistryManager().getOps(NbtOps.INSTANCE);

        nbt.putDouble("max_speed", MAX_SPEED);
        nbt.putDouble("brake_power", BRAKE_POWER);
        nbt.putDouble("max_explosion_power", MAX_EXPLOSION_POWER);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        RegistryOps<NbtElement> registryOps = this.getRegistryManager().getOps(NbtOps.INSTANCE);


    }

    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient) {
            noControl = true;
            if (age == 1) {
                spawnComponents();
            }

            if (!SEATS.isEmpty()) {
                Seat driver = SEATS.getFirst();

                if (driver.hasPassengers() && driver.getFirstPassenger() instanceof ServerPlayerEntity player) {
                    handlePlayerInput(player);

                    fuelTicks++;
                    if (fuelTicks >= FUEL_CONSUMPTION_RATE) {
                        fuelTicks = 0;
                        tank.consumeFuel();
                    }
                } else {
                    noControl = true;
                }


            } else {
                noControl = true;
            }

            // Accelerate to the ground if not on the ground
            if (!isOnGround()) {
                setVelocity(getVelocity().add(0, GRAVITY_ACCELERATION_PER_TICK, 0));
            } else {
                setVelocity(getVelocity().getX(), 0, getVelocity().getZ());
            }

            if (noControl) adjustVelocityWhileCoasting();

            if (exceedsMaxClimbHeight()) {
                explode((float) (Math.abs(speed) / MAX_SPEED * MAX_EXPLOSION_POWER));
                return;
            }

            double yaw = Math.toRadians(getYaw());

            setVelocity(new Vec3d(
                -Math.sin(yaw) * speed,
                getVelocity().getY(),
                Math.cos(yaw) * speed
            ));
        }

        // Movement must be updated on the server and client
        move(MovementType.SELF, getVelocity());

        // Update position of seats on server and client
        for (Seat seat: SEATS) {
            if (seat != null && !seat.isRemoved()) seat.updatePosition();
        }

        if (tank != null && !tank.isRemoved()) tank.updatePosition();
        if (trunk != null && !trunk.isRemoved()) trunk.updatePosition();
    }

    private void handlePlayerInput(ServerPlayerEntity player) {
        PlayerInput input = player.getPlayerInput();

        double unitAccelerationChange = MAX_SPEED / ACCELERATION_TICKS;

        if (input.jump()) {
            noControl = false;
            if (speed >= 0) {
                speed = Math.max(speed - BRAKE_POWER, 0);
            } else {
                speed = Math.min(speed + BRAKE_POWER, 0);
            }
        } else if (!tank.isEmpty()) {
            if (input.forward()) {
                noControl = false;
                speed = Math.min(speed + unitAccelerationChange, MAX_SPEED);
            } else if (input.backward()) {
                noControl = false;
                speed = Math.max(speed - unitAccelerationChange, -MAX_SPEED / 3);
            }
        }

        double yaw = Math.toRadians(getYaw());
        double changeInYaw = (speed == 0) ? 0: 0.05;

        if (input.left()) yaw -= changeInYaw;
        if (input.right()) yaw += changeInYaw;

        setYaw((float) Math.toDegrees(yaw));

        velocityDirty = true;
    }

    private void adjustVelocityWhileCoasting() {
        if (speed >= 0) {
            speed = Math.max(speed - BRAKE_POWER * COASTING_PROPORTIONALITY, 0);
        } else {
            speed = Math.min(speed + BRAKE_POWER * COASTING_PROPORTIONALITY, 0);
        }
    }

    /**
     * Creates the seats for the vehicle.
     * This method should be implemented by subclasses to define how many and where the seats are placed.
     */
    protected abstract void createSeats();

    /**
     * Adds a seat to the vehicle at the specified offsets.
     * The first seat added will always be the driver's seat.
     * @param offsetX The offset of the seat in the X direction.
     * @param offsetY The offset of the seat in the Y direction.
     * @param offsetZ The offset of the seat in the Z direction.
     */
    protected void addSeat(double offsetX, double offsetY, double offsetZ) {
        Seat seat = new Seat(
            VehicleRegisterer.SEAT_ENTITY_TYPE,
            this,
            offsetX,
            offsetY,
            offsetZ
        );

        this.SEATS.add(seat);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);

        for (Seat seat: SEATS) {
            if (seat != null && !seat.isRemoved()) {
                seat.remove(RemovalReason.DISCARDED);
            }
        }

        if (tank != null && !tank.isRemoved()) {
            tank.remove(RemovalReason.DISCARDED);
        }

        if (trunk != null && !trunk.isRemoved()) {
            trunk.remove(RemovalReason.DISCARDED);
        }
    }

    private void spawnComponents() {
        World world = getWorld();

        for (Seat seat: SEATS) {
            if (seat != null && !seat.isRemoved()) {
                world.spawnEntity(seat);
            }
        }

        if (tank != null && !tank.isRemoved()) {
            world.spawnEntity(tank);
        }

        if (trunk != null && !trunk.isRemoved()) {
            world.spawnEntity(trunk);
        }
    }

    @Override
    public float getStepHeight() {
        return 1.0f;
    }

    public void explode(float power) {
        World world = getWorld();

        if (!world.isClient) {
            world.createExplosion(
                this,
                getX(),
                getY(),
                getZ(),
                power,
                World.ExplosionSourceType.TNT
            );

            discard();
        }
    }

    private boolean exceedsMaxClimbHeight() {
        if (Math.abs(speed) <= MAX_SPEED / 4) return false;

        Vec3d direction = getVelocity().normalize().multiply(getExplosionLookForwardDistance());

        Box futureBox = getBoundingBox().offset(direction.getX(), 1.5, direction.getZ());

        for (VoxelShape shape: getWorld().getBlockCollisions(this, futureBox)) {
            if (!shape.isEmpty()) return true;
        }

        return false;
    }

    /**
     * Vehicles explode if they encounter a wall in front of them larger than 2 blocks.
     * This method returns the distance the vehicle looks forward to check for walls.
     * @return The distance the vehicle looks forward to check for walls.
     */
    protected abstract float getExplosionLookForwardDistance();
}
