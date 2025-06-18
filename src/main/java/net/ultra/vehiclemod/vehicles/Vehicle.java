package net.ultra.vehiclemod.vehicles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Creates a vehicle class that extends minecraft's entity class
 */
public abstract class Vehicle extends Entity {
    //sets the car's tick speed to 20 ticks per second
    private static final double TPS = 20;

    // Vehicle physics properties
    public final double MAX_SPEED;
    protected double speed = 0;
    public final double BRAKE_POWER;
    private static final double COASTING_PROPORTIONALITY = 0.2;
    protected final double ACCELERATION_TICKS;
    private static final double GRAVITY_ACCELERATION_PER_TICK = -0.08; // this is the default gravity acceleration in Minecraft
    protected static final int DEFAULT_ACCELERATION_TICKS = 100; // how long it takes for vehicle to reach max speed
    private boolean noControl = true;

    // Explosion properties
    public final double MAX_EXPLOSION_POWER;
    private static final double EXPLOSION_PROPORTIONALITY = 0.25;

    // Fuel properties
    protected final double FUEL_CONSUMPTION_RATE;
    protected double fuelTicks = 0;

    protected static final float DAMAGE_RATE = 1.0f;


    protected final ArrayList<Seat> SEATS = new ArrayList<>();
    protected final ArrayList<UUID> SEATS_UUID = new ArrayList<>();
    protected FuelTank tank = null;
    protected UUID tankUuid = null;
    protected Trunk trunk = null;
    protected UUID trunkUuid = null;
    private boolean fromSavedData = false;

    protected Vehicle(
        EntityType<? extends Vehicle> type,
        World world,
        double MAX_SPEED,
        double BRAKE_POWER,
        double MAX_EXPLOSION_POWER,
        double FUEL_CONSUMPTION_RATE
    ) {
        super(type, world);

        this.MAX_SPEED = MAX_SPEED / TPS; // blocks per tick
        this.BRAKE_POWER = BRAKE_POWER / Math.pow(TPS, 2);
        this.MAX_EXPLOSION_POWER = MAX_EXPLOSION_POWER;
        this.FUEL_CONSUMPTION_RATE = FUEL_CONSUMPTION_RATE * TPS;
        ACCELERATION_TICKS = DEFAULT_ACCELERATION_TICKS;
    }

    /**
     * This constructor is used only for the bugatti due to the faster acceleration perk
      * @param type the type of vehicle
     * @param world the world that the vehicle is in
     * @param MAX_SPEED max speed the vehicle can go
     * @param BRAKE_POWER the brake power of the car
     * @param MAX_EXPLOSION_POWER the max explosion power
     * @param ACCELERATION_TICKS the custom acceleration
     * @param FUEL_CONSUMPTION_RATE the fuel consumption rate
     */
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
    }

    @Override
    /**
     * this method is only here because this class extends the entity class, and this method is
     * abstract which requires it to be overridden
     */
    protected void initDataTracker(DataTracker.Builder builder) {}

    @Override
    /**
     * this method prevents the car from taking damage
     */
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    /**
     * Reloads all child components from NBT data.
     * Minecraft does not save which entities are the vehicle's components,
     * so NBT data and UUIDs are required to reconnect these entities upon reloading
     * the world.
     * @param nbt The NBT data.
     */
    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        // Log that data comes from save file
        fromSavedData = true;
        SEATS_UUID.clear();
        SEATS.clear();

        // Loop through all seat UUIDs
        int i = 0;
        while (true) {
            // Add seat NBT to ArrayList of UUIDs
            if (nbt.contains("seatMost" + i) && nbt.contains("seatLeast" + i)) {
                Optional<Long> most = nbt.getLong("seatMost" + i);
                Optional<Long> least = nbt.getLong("seatLeast" + i);

                if (most.isPresent() && least.isPresent()) {
                    SEATS_UUID.add(new UUID(most.get(), least.get()));
                    SEATS.add(null);
                }

                i++;
            } else break;
        }

        // Find fuel tank UUID
        if (nbt.contains("tankMost") && nbt.contains("tankLeast")) {
            Optional<Long> most = nbt.getLong("tankMost");
            Optional<Long> least = nbt.getLong("tankLeast");

            if (most.isPresent() && least.isPresent()) {
                tankUuid = new UUID(most.get(), least.get());
            }
        }

        // Find trunk UUID
        if (nbt.contains("trunkMost") && nbt.contains("trunkLeast")) {
            Optional<Long> most = nbt.getLong("trunkMost");
            Optional<Long> least = nbt.getLong("trunkLeast");

            if (most.isPresent() && least.isPresent()) {
                trunkUuid = new UUID(most.get(), least.get());
            }
        }
    }

    /**
     * Writes all child components UUID into NBT data.
     * Minecraft does not save which entities are the vehicle's components,
     * so NBT data and UUIDs are required to reconnect these entities upon reloading
     * the world.
     * @param nbt The NBT data.
     */
    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        // Get UUID of all seats and write to NBT
        for (int i = 0; i < SEATS.size(); i++) {
            Seat seat = SEATS.get(i);

            if (seat != null) {
                UUID uuid = seat.getUuid();
                nbt.putLong("seatMost" + i, uuid.getMostSignificantBits());
                nbt.putLong("seatLeast" + i, uuid.getLeastSignificantBits());
            }
        }

        // Write the fuel tank UUID to NBT
        if (tank != null && !tank.isRemoved()) {
            UUID uuid = tank.getUuid();
            nbt.putLong("tankMost", uuid.getMostSignificantBits());
            nbt.putLong("tankLeast", uuid.getLeastSignificantBits());
        }

        // Write the trunk UUID to NBT
        if (trunk != null && !trunk.isRemoved()) {
            UUID uuid = trunk.getUuid();
            nbt.putLong("trunkMost", uuid.getMostSignificantBits());
            nbt.putLong("trunkLeast", uuid.getLeastSignificantBits());
        }
    }

    /**
     * this method is used to update the vehicles both on server-side and client-side
     * also handles how vehicles interact with objects
     */
    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient) {
            if (age == 1) {
                // Load new seats if there's no save data
                if (!fromSavedData) {
                    createSeats();
                    createFuelTank();
                    createTrunk();
                }

                loadComponents();
            }

            // Handle dealing damage to living entities
            List<Entity> collidedEntities = getCollidingLivingEntities();
            for (Entity entity: collidedEntities) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (Math.abs(speed) > 0.15f) {
                        float damage = (float) (DAMAGE_RATE * (Math.abs(speed) * TPS));
                        Vec3d direction = getVelocity().normalize();

                        /*
                        * Living entity must take the negative of the velocity since
                        * the inputs calculate the direction vector from the entity
                        * to the damage source, but we want the knockback in the
                        * direction of the velocity vector.
                        */
                        livingEntity.takeKnockback(
                            Math.abs(speed),
                            -direction.getX(),
                            -direction.getZ()
                        );

                        livingEntity.damage(
                            (ServerWorld) getWorld(),
                            getWorld().getDamageSources().outOfWorld(),
                            damage
                        );
                    }
                }
            }

            // Handle explosions with other vehicles
            List<Entity> collidedVehicles = getCollidingVehicles();
            for (Entity entity: collidedVehicles) {
                if (entity instanceof Vehicle vehicle) {
                    double relativeSpeed = getVelocity().subtract(vehicle.getVelocity()).length();

                    if (
                        relativeSpeed > MAX_SPEED * EXPLOSION_PROPORTIONALITY ||
                        relativeSpeed > vehicle.MAX_SPEED * EXPLOSION_PROPORTIONALITY
                    ) {
                        explodeProportionalToSpeed();
                        vehicle.explodeProportionalToSpeed();
                        return;
                    }
                }
            }

            noControl = true;

            if (!SEATS.isEmpty()) {
                Seat driver = SEATS.getFirst();
                if (driver != null) {
                    if (driver.hasPassengers() && driver.getFirstPassenger() instanceof ServerPlayerEntity player) {
                        handlePlayerInput(player);

                        fuelTicks++;
                        if (fuelTicks >= FUEL_CONSUMPTION_RATE) {
                            fuelTicks = 0;
                            tank.consumeFuel();
                        }
                    } else noControl = true;

                } else noControl = true;

            }

            if (isInLava()) {
                explode((float) MAX_EXPLOSION_POWER);
            }

            // Accelerate to the ground if not on the ground
            if (!isOnGround()) {
                setVelocity(getVelocity().add(0, GRAVITY_ACCELERATION_PER_TICK, 0));
            } else {
                setVelocity(getVelocity().getX(), 0, getVelocity().getZ());
            }

            if (noControl) adjustVelocityWhileCoasting();

            if (exceedsMaxClimbHeight()) {
                explodeProportionalToSpeed();
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
        for (Seat seat : SEATS) {
            if (seat != null && !seat.isRemoved()) seat.updatePosition();
        }

        if (tank != null && !tank.isRemoved()) tank.updatePosition();
        if (trunk != null && !trunk.isRemoved()) trunk.updatePosition();
    }

    /**
     * handles user input
     * @param player the player entity
     */
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
                speed = Math.min(speed + unitAccelerationChange, getCurrentMaxSpeed());
            } else if (input.backward()) {
                noControl = false;
                speed = Math.max(speed - unitAccelerationChange, -getCurrentMaxSpeed() / 3);
            }
        }

        double yaw = Math.toRadians(getYaw());
        double changeInYaw = (speed == 0) ? 0 : 0.05;

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

    protected abstract void createFuelTank();

    protected abstract void createTrunk();

    /**
     * Adds a seat to the vehicle at the specified offsets.
     * The first seat added will always be the driver's seat.
     * Subclasses should use this method to add a seat instead of
     * directly creating a seat object to ensure that the seat's NBT
     * data is saved.
     *
     * @param offsetX The offset of the seat in the X direction.
     * @param offsetY The offset of the seat in the Y direction.
     * @param offsetZ The offset of the seat in the Z direction.
     * @param seatId  The ith seat added to the vehicle, zero-indexed. Seat IDs
     *                should always be assigned in ascending order.
     */
    protected void addSeat(double offsetX, double offsetY, double offsetZ, int seatId) {
        Seat seat = new Seat(
            VehicleRegisterer.SEAT_ENTITY_TYPE,
            this,
            offsetX,
            offsetY,
            offsetZ
        );

        SEATS.add(seat);
        SEATS_UUID.add(seat.getUuid());
    }

    protected void setFuelTank(double offsetX, double offsetY, double offsetZ, Item[] items) {
        tank = new FuelTank(
            VehicleRegisterer.FUEL_TANK_ENTITY_TYPE,
            this,
            offsetX,
            offsetY,
            offsetZ,
            items
        );

        tankUuid = tank.getUuid();
    }

    protected void setTrunk(double offsetX, double offsetY, double offsetZ) {
        trunk = new Trunk(
            VehicleRegisterer.TRUNK_ENTITY_TYPE,
            this,
            offsetX,
            offsetY,
            offsetZ
        );

        trunkUuid = trunk.getUuid();
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);

        for (Seat seat : SEATS) {
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

    private void loadComponents() {
        World world = getWorld();

        if (!fromSavedData) {
            for (Seat seat : SEATS) world.spawnEntity(seat);
            world.spawnEntity(tank);
            world.spawnEntity(trunk);
        } else {
            SEATS.clear();

            for (UUID uuid : SEATS_UUID) {
                for (Seat other : world.getEntitiesByClass(
                        Seat.class,
                        getBoundingBox().expand(16),
                        e -> true
                )) {
                    if (other.getUuid().equals(uuid)) SEATS.add(other);
                }
            }

            for (FuelTank other : world.getEntitiesByClass(
                    FuelTank.class,
                    getBoundingBox().expand(16),
                    e -> true
            )) {
                if (other.getUuid().equals(tankUuid)) tank = other;
            }

            for (Trunk other : world.getEntitiesByClass(
                    Trunk.class,
                    getBoundingBox().expand(16),
                    e -> true
            )) {
                if (other.getUuid().equals(trunkUuid)) trunk = other;
            }
        }
    }

    @Override
    public float getStepHeight() {
        return 1.0f;
    }

    public double getCurrentMaxSpeed() {
        return isInFluid() ? MAX_SPEED / 2: MAX_SPEED;
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

    public void explodeProportionalToSpeed() {
        explode((float) (Math.abs(speed) / MAX_SPEED * MAX_EXPLOSION_POWER));
    }

    private boolean exceedsMaxClimbHeight() {
        if (Math.abs(speed) <= MAX_SPEED * EXPLOSION_PROPORTIONALITY) return false;

        Vec3d direction = getVelocity().normalize().multiply(getExplosionLookForwardDistance());

        Box futureBox = getBoundingBox().offset(direction.getX(), 1.5, direction.getZ());

        for (VoxelShape shape : getWorld().getBlockCollisions(this, futureBox)) {
            if (!shape.isEmpty()) return true;
        }

        return false;
    }

    private List<Entity> getCollidingLivingEntities() {
        return getWorld().getOtherEntities(
            this,
            getBoundingBox(),
            entity -> {
                for (Seat seat : SEATS) {
                    if (seat != null && seat.getFirstPassenger() == entity) {
                        return false;
                    }
                }

                return entity instanceof LivingEntity;
            }
        );
    }

    private List<Entity> getCollidingVehicles() {
        return getWorld().getOtherEntities(
            this,
            getBoundingBox(),
            entity -> entity instanceof Vehicle
        );
    }

    /**
     * Vehicles explode if they encounter a wall in front of them larger than 2 blocks.
     * This method returns the distance the vehicle looks forward to check for walls.
     *
     * @return The distance the vehicle looks forward to check for walls.
     */
    protected abstract float getExplosionLookForwardDistance();
}
