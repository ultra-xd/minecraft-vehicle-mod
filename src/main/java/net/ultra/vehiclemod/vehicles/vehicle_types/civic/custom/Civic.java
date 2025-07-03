package net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

/** A Honda Civic Vehicle. */
public class Civic extends Vehicle {
    public static final String ITEM_ID = "civic_spawn";
    public static final String ENTITY_ID = "civic";
    public static final float VEHICLE_HITBOX_WIDTH = 4.0f;
    public static final float VEHICLE_HITBOX_HEIGHT = 3.0f;

    /**
     * Initializes a Honda Civic entity.
     * @param type The entity type of vehicle (Honda Civic).
     * @param world The world that this vehicle is in.
     */
    public Civic(EntityType<? extends Vehicle> type, World world) {
        super(
            type,
            world,
            30,
            10,
            30,
            5
        );
    }

    @Override
    protected void createSeats() {
        addSeat(0.5, 0.5, 1);
        addSeat(-0.5, 0.5, 1);
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
