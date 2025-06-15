package net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.VehicleRegisterer;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.custom.FuelTank;
import net.ultra.vehiclemod.vehicles.components.entity.trunk.custom.Trunk;

public class Civic extends Vehicle {
    public static final String ITEM_ID = "civic_spawn";
    public static final String ENTITY_ID = "civic";
    public static final String FUEL_TANK_ID = "civic_fuel_tank";
    public static final String TRUNK_ID = "civic_trunk";

    public Civic(EntityType<? extends Vehicle> type, World world) {
        super(
            type,
            world,
            30,
            10,
            30
        );

        tank = new FuelTank(
            VehicleRegisterer.getFuelTankType(FUEL_TANK_ID),
            this,
            5,
            1,
            -1.5,
            0.5,
            -1.5
        );

        trunk = new Trunk(
            VehicleRegisterer.getTrunkType(TRUNK_ID),
            this,
            10,
            1,
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
}
