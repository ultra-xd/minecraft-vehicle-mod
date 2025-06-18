package net.ultra.vehiclemod.vehicles.vehicle_types.truck.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

/**
 * Creates a Truck class that extends the Vehicle class
 */
public class Truck extends Vehicle {
    //Truck ids
    public static final String ITEM_ID = "truck_spawn";
    public static final String ENTITY_ID = "truck";

    /**
     * Constructor for the truck that passes in its stats
     * @param type the type of vehicle it is (truck)
     * @param world the world that the truck is in
     */
    public Truck(EntityType<? extends Vehicle> type, World world) {
        super(
            type,
            world,
            12,
            3,
            20,
            20
        );
    }

    /**
     * creates the seats
     */
    @Override
    protected void createSeats() {
        addSeat(0.5, 0.5, 2, 0);
        addSeat(-0.5, 0.5, 2, 0);
    }

    /**
     * creates the fuel tank and determines what items can be placed inside
     */
    @Override
    protected void createFuelTank() {
        setFuelTank(-0.5, 0.5, -3, new Item[] {Items.COAL});
    }

    /**
     * creates the trunk
     */
    @Override
    protected void createTrunk() {
        setTrunk(0, 0.5, -4);
    }

    /**
     * a getter for how far a wall should be to be considered colliding with the truck
     * @return the minimum distance a wall should be in front of the vehicle to not collide
     */
    @Override
    protected float getExplosionLookForwardDistance() {
        return 4;
    }
}
