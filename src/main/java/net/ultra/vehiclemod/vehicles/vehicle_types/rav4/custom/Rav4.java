package net.ultra.vehiclemod.vehicles.vehicle_types.rav4.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

public class Rav4 extends Vehicle {
    public static final String ITEM_ID = "rav4_spawn";
    public static final String ENTITY_ID = "rav4";

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

    @Override
    protected void createSeats() {
        addSeat(-0.5, 0.5, 0.5, 0);
        addSeat(0.5, 0.5, 0.5, 1);
        addSeat(-0.5, 0.5, -0.5, 2);
        addSeat(0.5, 0.5, -0.5, 3);
    }

    @Override
    protected void createFuelTank() {
        setFuelTank(-1.5, 0.5, -1.5, new Item[] {Items.COAL, Items.REDSTONE});
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
