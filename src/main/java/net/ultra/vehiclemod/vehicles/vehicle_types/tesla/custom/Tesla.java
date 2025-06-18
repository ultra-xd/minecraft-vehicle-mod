package net.ultra.vehiclemod.vehicles.vehicle_types.tesla.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

public class Tesla extends Vehicle {
    public static final String ITEM_ID = "tesla_spawn";
    public static final String ENTITY_ID = "tesla";

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

    @Override
    protected void createSeats() {
        addSeat(0.5, 0.5, 1, 0);
        addSeat(-0.5, 0.5, 1, 1);
        addSeat(-0.5, 0.5, -0.5, 2);
        addSeat(0.5, 0.5, -0.5, 3);
    }

    @Override
    protected void createFuelTank() {
        setFuelTank(-1.5, 0.5, -1.5, new Item[] {Items.REDSTONE});
    }

    @Override
    protected void createTrunk() {
        setTrunk(0, 0.5, -2);
    }

    @Override
    protected float getExplosionLookForwardDistance() {
        return 2.2f;
    }
}
