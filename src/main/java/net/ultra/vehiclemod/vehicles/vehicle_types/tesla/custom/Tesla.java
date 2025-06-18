package net.ultra.vehiclemod.vehicles.vehicle_types.tesla.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

/**
 * Creates a Tesla class that extends the Vehicle class
 */
public class Tesla extends Vehicle {
    //tesla ids
    public static final String ITEM_ID = "tesla_spawn";
    public static final String ENTITY_ID = "tesla";

    /**
     * Constructor for the tesla that passes in its stats
     * @param type the type of vehicle it is (telsa)
     * @param world the world that the tesla is in
     */
    public Tesla(EntityType<? extends Vehicle> type, World world) {
        super(
            type,
            world,
            30,
            10,
            30,
            5
        );
    }

    /**
     * creates the seats
     */
    @Override
    protected void createSeats() {
        addSeat(0.5, 0.5, 1, 0);
        addSeat(-0.5, 0.5, 1, 1);
        addSeat(-0.5, 0.5, -0.5, 2);
        addSeat(0.5, 0.5, -0.5, 3);
    }

    /**
     * creates the fuel tank and determines what items can be placed inside
     */
    @Override
    protected void createFuelTank() {
        setFuelTank(-1.5, 0.5, -1.5, new Item[] {Items.REDSTONE});
    }

    /**
     * creates the trunk
     */
    @Override
    protected void createTrunk() {
        setTrunk(0, 0.5, -2);
    }

    /**
     * a getter for how far a wall should be to be considered colliding with the truck
     * @return the minimum distance a wall should be in front of the vehicle to not collide
     */
    @Override
    protected float getExplosionLookForwardDistance() {
        return 2.2f;
    }
}
