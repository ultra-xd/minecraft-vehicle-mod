package net.ultra.vehiclemod.vehicles;

import net.minecraft.item.Item;

public class VehicleItem extends Item {
    public VehicleItem(Settings settings) {
        super(settings.maxCount(1));
    }
}
