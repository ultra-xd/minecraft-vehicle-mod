package net.ultra.vehiclemod.vehicles.vehicle_types.soul.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

/** A KIA Soul Vehicle. It leaks fuel every 5 seconds someone is driving the car. */
public class Soul extends Vehicle {
    public static final String ITEM_ID = "soul_spawn";
    public static final String ENTITY_ID = "soul";
    public static final float VEHICLE_HITBOX_WIDTH = 4.0f;
    public static final float VEHICLE_HITBOX_HEIGHT = 4.0f;

    private int fuelLeakTicks = 0;
    private static final int FUEL_LEAK_INTERVAL_TICKS = 100;

    /**
     * Initializes a KIA Soul entity.
     * @param type The entity type of vehicle (Kia Soul).
     * @param world The world that this vehicle is in.
     */
    public Soul(EntityType<? extends Vehicle> type, World world) {
        super(
            type,
            world,
            5,
            3,
            20,
            1
        );
    }

    /** Updates the vehicle and handles any physics. Also handles leaking fuel. */
    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient && driverSeatIsOccupied()) {
            fuelLeakTicks++;

            if (fuelLeakTicks == FUEL_LEAK_INTERVAL_TICKS) {
                tank.dropFuel();
                fuelLeakTicks = 0;
            }
        }
    }

    @Override
    protected void createSeats() {
        addSeat(0.5, 0.5, 0.5);
        addSeat(-0.5, 0.5, 0.5);
        addSeat(-0.5, 0.5, -0.5);
        addSeat(0.5, 0.5, -0.5);
    }

    @Override
    protected void createFuelTank() {
        setFuelTank(-1.5, 0.5, -1.5, new Item[] {Items.COAL});
    }

    @Override
    protected void createTrunk() {
        setTrunk(0, 0.5, -2);
    }

    @Override
    protected float getExplosionLookForwardDistance() {
        return 1.2f;
    }
}
