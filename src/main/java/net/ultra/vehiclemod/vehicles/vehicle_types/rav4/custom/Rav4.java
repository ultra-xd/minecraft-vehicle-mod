package net.ultra.vehiclemod.vehicles.vehicle_types.rav4.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

/**
 * Creates a Rav4 class that extends the Vehicle class
 */
public class Rav4 extends Vehicle {
    //rav4 ids
    public static final String ITEM_ID = "rav4_spawn";
    public static final String ENTITY_ID = "rav4";

    /**
     * rav4 constructor
     * @param type the type of vehicle (Rav4)
     * @param world the world that this vehicle is in
     */
    public Rav4(EntityType<? extends Vehicle> type, World world) {
        super(
                type,
                world,
                27,
                7,
                50,
                10
        );
    }

    /**
     * creates the seats
     */
    @Override
    protected void createSeats() {
        addSeat(-0.5, 0.5, 0.5, 0);
        addSeat(0.5, 0.5, 0.5, 1);
        addSeat(-0.5, 0.5, -0.5, 2);
        addSeat(0.5, 0.5, -0.5, 3);
    }

    /**
     * creates the fuel tank and determines what items can be placed inside
     */
    @Override
    protected void createFuelTank() {
        setFuelTank(-1.5, 0.5, -1.5, new Item[] {Items.COAL, Items.REDSTONE});
    }

    /**
     * creates the trunk
     */
    @Override
    protected void createTrunk() {
        setTrunk(0, 0.5, -2);
    }

    /**
     * a getter for how far a wall should be to be considered colliding with the Rav4
     * @return the minimum distance a wall should be in front of the vehicle to not collide
     */
    @Override
    protected float getExplosionLookForwardDistance() {
        return 2.2f;
    }
}
