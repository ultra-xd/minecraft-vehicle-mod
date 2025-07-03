package net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

/** A Bugatti Vehicle. It accelerates faster than other vehicles. */
public class Bugatti extends Vehicle {
    public static final String ITEM_ID = "bugatti_spawn";
    public static final String ENTITY_ID = "bugatti";
    public static final float VEHICLE_HITBOX_WIDTH = 4.0f;
    public static final float VEHICLE_HITBOX_HEIGHT = 2.5f;

    /**
     * Initializes a Bugatti entity.
     * @param type The entity type of vehicle (Bugatti).
     * @param world The world that this vehicle is in.
     */
    public Bugatti(EntityType<? extends Vehicle> type, World world) {
        super(
                type,
                world,
                55,
                35,
                70,
                2,
                2
        );
    }

    @Override
    protected void createSeats() {
        addSeat(-0.5, 0.5, 0.5);
        addSeat(0.5, 0.5, 0.5);
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
