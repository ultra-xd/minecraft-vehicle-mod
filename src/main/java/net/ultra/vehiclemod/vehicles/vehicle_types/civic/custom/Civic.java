package net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

public class Civic extends Vehicle {
    public static final String ITEM_ID = "civic_spawn";
    public static final String ENTITY_ID = "civic";

    public Civic(EntityType<? extends Vehicle> type, World world) {
        super(
            type,
            world,
            30,
            10,
            30
        );
    }
}
