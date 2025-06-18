package net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

/**
 * Creates a bugatti class that extends the Vehicle class
 */
public class Bugatti extends Vehicle {
    //bugatti ids
    public static final String ITEM_ID = "bugatti_spawn";
    public static final String ENTITY_ID = "bugatti";

    /**
     * bugatti constructor
     * @param type the type of vehicle (Bugatti)
     * @param world the world that this vehicle is in
     */
    public Bugatti(EntityType<? extends Vehicle> type, World world) {
        super(
                type,
                world,
                55,
                35,
                70,
                2
        );
    }

    /**
     * creates the seats
     */
    @Override
    protected void createSeats() {
        addSeat(-0.5, 0.5, 0.5, 0);
        addSeat(0.5, 0.5, 0.5, 1);
    }

    /**
     * creates the fuel tank and determines what items can be placed inside
     */
    @Override
    protected void createFuelTank() {
        setFuelTank(-1.5, 0.5, -1.5, new Item[] {Items.COAL});
    }

    /**
     * creates the trunk
     */
    @Override
    protected void createTrunk() {
        setTrunk(0, 0.5, -2);
    }

    /**
     * a getter for how far a wall should be to be considered colliding with the Bugatti
     * @return the minimum distance a wall should be in front of the vehicle to not collide
     */
    @Override
    protected float getExplosionLookForwardDistance() {
        return 2.2f;
    }
}
