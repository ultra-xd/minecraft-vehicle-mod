package net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.VehicleRegisterer;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.custom.FuelTank;
import net.ultra.vehiclemod.vehicles.components.entity.trunk.custom.Trunk;

public class Civic extends Vehicle {
    public static final String ITEM_ID = "civic_spawn";
    public static final String ENTITY_ID = "civic";

    public Civic(EntityType<? extends Vehicle> type, World world) {
        super(
            type,
            world,
            2,
            1,
            30,
            5
        );

        tank = new FuelTank(
            VehicleRegisterer.FUEL_TANK_ENTITY_TYPE,
            this,
            -1.5,
            0.5,
            -1.5,
            new Item[] {Items.COAL}
        );

        trunk = new Trunk(
            VehicleRegisterer.TRUNK_ENTITY_TYPE,
            this,
            0,
            0.5,
            -2
        );
    }

    @Override
    protected void createSeats() {
        addSeat(-0.5, 0.5, 0.5);
        addSeat(0.5, 0.5, 0.5);
        addSeat(-0.5, 0.5, -0.5);
        addSeat(0.5, 0.5, -0.5);
    }

    @Override
    protected float getExplosionLookForwardDistance() {
        return 3f;
    }
}
