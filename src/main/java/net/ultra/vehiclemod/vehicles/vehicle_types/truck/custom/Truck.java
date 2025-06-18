package net.ultra.vehiclemod.vehicles.vehicle_types.truck.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

public class Truck extends Vehicle {

    public static final String ITEM_ID = "truck_spawn";
    public static final String ENTITY_ID = "truck";
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

    @Override
    protected void createSeats() {
        addSeat(0.5, 0.5, 2, 0);
        addSeat(-0.5, 0.5, 2, 0);
    }

    @Override
    protected void createFuelTank() {
        setFuelTank(-0.5, 0.5, -3, new Item[] {Items.COAL});
    }

    @Override
    protected void createTrunk() {
        setTrunk(0, 0.5, -4);
    }

    @Override
    protected float getExplosionLookForwardDistance() {
        return 4;
    }
}
