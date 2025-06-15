package net.ultra.vehiclemod.vehicles;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.custom.FuelTank;
import net.ultra.vehiclemod.vehicles.components.entity.seat.custom.Seat;
import net.ultra.vehiclemod.vehicles.components.entity.trunk.custom.Trunk;

import java.util.ArrayList;

/**
 *
 */
public abstract class Vehicle extends Entity {

    public final double MAX_SPEED;
    protected double speed = 0;
    public final double BRAKE_POWER;
    public final double MAX_EXPLOSION_POWER;
    protected final double ACCELERATION_TICKS;

    protected static final int DEFAULT_ACCELERATION_TICKS = 100;

    protected final ArrayList<Seat> SEATS = new ArrayList<>();
    protected FuelTank tank = null;
    protected Trunk trunk = null;

    protected Vehicle(
        EntityType<? extends Vehicle> type,
        World world,
        double MAX_SPEED,
        double BRAKE_POWER,
        double MAX_EXPLOSION_POWER
    ) {
        super(type, world);

        this.MAX_SPEED = MAX_SPEED;
        this.BRAKE_POWER = BRAKE_POWER;
        this.MAX_EXPLOSION_POWER = MAX_EXPLOSION_POWER;
        ACCELERATION_TICKS = DEFAULT_ACCELERATION_TICKS;

        createSeats();
    }

    protected Vehicle(
        EntityType<? extends Vehicle> type,
        World world,
        double MAX_SPEED,
        double BRAKE_POWER,
        double MAX_EXPLOSION_POWER,
        double ACCELERATION_TICKS
    ) {
        super(type, world);

        this.MAX_SPEED = MAX_SPEED;
        this.BRAKE_POWER = BRAKE_POWER;
        this.MAX_EXPLOSION_POWER = MAX_EXPLOSION_POWER;
        this.ACCELERATION_TICKS = ACCELERATION_TICKS;

        createSeats();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

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

        if (!getWorld().isClient && age == 1) {
            spawnComponents();
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
//            getWorld()
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

        System.out.println("vehicle in server: " + (world instanceof ServerWorld));

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

//    @Override
//    public ActionResult interact(PlayerEntity player, Hand hand) {
//        System.out.println("interacted vehicle");
//        return ActionResult.PASS;
//    }
}
