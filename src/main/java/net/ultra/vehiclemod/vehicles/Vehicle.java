package net.ultra.vehiclemod.vehicles;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;

public abstract class Vehicle {

    protected Vehicle() {

    }


    protected static void registerItem(String ID) {
        assert (ID != null);

        VehicleMod.LOGGER.info(String.format(
            "Registering for %s", VehicleMod.MOD_ID
        ));

        Item item = Registry.register(
            Registries.ITEM,
            Identifier.of(VehicleMod.MOD_ID, ID),
                new Item(new Item.Settings()
                        .maxCount(1)
                        .registryKey(RegistryKey.of(
                        RegistryKeys.ITEM,
                        Identifier.of(VehicleMod.MOD_ID, ID)
                )))
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register(entries -> entries.add(item));

    }
}
