package net.ultra.vehiclemod.vehicles;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;

import java.util.HashMap;

public final class VehicleRegisterer {
    private VehicleRegisterer() {}

    private static HashMap<String, EntityType<? extends Vehicle>> ENTITY_TYPES = new HashMap<>();
    private static HashMap<String, VehicleItem> ITEMS = new HashMap<>();

    public static void registerAll() {
        registerItem(Civic.ITEM_ID);

        registerEntity(Civic.ENTITY_ID, Civic::new);
    }

    private static void registerItem(String ID) {
        VehicleMod.LOGGER.info("Registering {} item for {}", ID, VehicleMod.MOD_ID);

        Identifier identifier = Identifier.of(VehicleMod.MOD_ID, ID);

        VehicleItem item = Registry.register(
            Registries.ITEM,
            identifier,
            new VehicleItem(new VehicleItem.Settings().registryKey(RegistryKey.of(
                RegistryKeys.ITEM,
                identifier
            )))
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
            .register(entries -> entries.add(item));

        ITEMS.put(ID, item);
    }

    private static <T extends Vehicle> void registerEntity(
        String ID,
        EntityType.EntityFactory<T> factory
    ) {
        VehicleMod.LOGGER.info("Registering {} entity for {}", ID, VehicleMod.MOD_ID);

        Identifier identifier = Identifier.of(VehicleMod.MOD_ID, ID);

        EntityType<T> vehicleEntityType = EntityType.Builder.create(
            factory,
            SpawnGroup.MISC
        ).dimensions(2.0f, 2.0f)
         .dropsNothing()
         .maxTrackingRange(128)
         .makeFireImmune()
         .trackingTickInterval(1)
         .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier));

        Registry.register(
            Registries.ENTITY_TYPE,
            identifier,
            vehicleEntityType
        );

        ENTITY_TYPES.put(ID, vehicleEntityType);
    }

    public static <T extends Vehicle> EntityType<T> getEntityType(String ID) {
        return (EntityType<T>) ENTITY_TYPES.get(ID);
    }

    public static VehicleItem getItem(String ID) {
        return ITEMS.get(ID);
    }
}
