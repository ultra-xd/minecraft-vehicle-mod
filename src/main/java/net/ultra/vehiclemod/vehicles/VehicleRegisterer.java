package net.ultra.vehiclemod.vehicles;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;
import net.ultra.vehiclemod.vehicles.components.entity.NoOpRenderer;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.custom.FuelTank;
import net.ultra.vehiclemod.vehicles.components.entity.seat.custom.Seat;
import net.ultra.vehiclemod.vehicles.components.entity.trunk.custom.Trunk;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;

import java.util.HashMap;

public final class VehicleRegisterer {
    private VehicleRegisterer() {}

    private static final HashMap<String, EntityType<? extends Vehicle>> ENTITY_TYPES = new HashMap<>();
    private static final HashMap<String, VehicleItem<? extends Vehicle>> ITEMS = new HashMap<>();
    private static final HashMap<String, EntityType<FuelTank>> FUEL_TANKS = new HashMap<>();
    private static final HashMap<String, EntityType<Trunk>> TRUNKS = new HashMap<>();

    private static final Identifier SEAT_IDENTIFIER = Identifier.of(
        VehicleMod.MOD_ID, Seat.ENTITY_ID
    );

    public static final EntityType<Seat> SEAT_ENTITY_TYPE = EntityType.Builder.<Seat>create(
        Seat::new,
        SpawnGroup.MISC
    ).dimensions((float) Seat.SEAT_WIDTH, (float) Seat.SEAT_HEIGHT)
     .disableSummon()
     .trackingTickInterval(1)
     .maxTrackingRange(8)
     .makeFireImmune()
     .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, SEAT_IDENTIFIER));

    public static void serverRegisterAll() {
        registerSeat();
        registerFuelTanks();
        registerTrunks();
        registerVehicle(Civic.ENTITY_ID, Civic::new);
        registerItem(Civic.ITEM_ID, Civic.ENTITY_ID, Civic::new);
    }

    public static void clientRegisterAll() {
        registerComponentRenderers();

        registerRenderer(
            VehicleRegisterer.getVehicleType(Civic.ENTITY_ID),
            CivicRenderer::new
        );

        registerModel(CivicModel.CIVIC, CivicModel::getTexturedModelData);
    }

    private static <T extends Vehicle> void registerItem(
        String itemId,
        String entityId,
        EntityType.EntityFactory<T> factory
    ) {
        VehicleMod.LOGGER.info("Registering {} item for {}", itemId, VehicleMod.MOD_ID);

        Identifier identifier = Identifier.of(VehicleMod.MOD_ID, itemId);

        VehicleItem<T> item = Registry.register(
            Registries.ITEM,
            identifier,
            new VehicleItem<T>(new VehicleItem.Settings().registryKey(RegistryKey.of(
                RegistryKeys.ITEM,
                identifier
            ))).factory(factory).entityId(entityId)
        );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
            .register(entries -> entries.add(item));

        ITEMS.put(itemId, item);
    }

    private static <T extends Vehicle> void registerVehicle(
        String ID,
        EntityType.EntityFactory<T> factory
    ) {
        VehicleMod.LOGGER.info("Registering {} entity for {}", ID, VehicleMod.MOD_ID);

        Identifier identifier = Identifier.of(VehicleMod.MOD_ID, ID);

        EntityType<T> vehicleEntityType = EntityType.Builder.create(
            factory,
            SpawnGroup.MISC
        ).dimensions(4.0f, 4.0f)
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

    private static <T extends Entity> void registerRenderer(
            EntityType<T> vehicleType,
            EntityRendererFactory<T> factory
    ) {
        VehicleMod.LOGGER.info(
                "Registering {} renderer for {}", vehicleType.getName(), VehicleMod.MOD_ID
        );

        EntityRendererRegistry.register(vehicleType, factory);
    }

    private static void registerModel(
            EntityModelLayer model,
            EntityModelLayerRegistry.TexturedModelDataProvider provider
    ) {
        VehicleMod.LOGGER.info(
                "Registering {} model for {}", model.name(), VehicleMod.MOD_ID
        );

        EntityModelLayerRegistry.registerModelLayer(model, provider);
    }

    private static void registerFuelTank(String ID) {
        Identifier identifier = Identifier.of(VehicleMod.MOD_ID, ID);

        EntityType<FuelTank> tankEntityType = EntityType.Builder.<FuelTank>create(
                        FuelTank::new,
                        SpawnGroup.MISC
                ).dimensions(1f, 1f)
                .disableSummon()
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier));

        Registry.register(
                Registries.ENTITY_TYPE,
                identifier,
                tankEntityType
        );

        FUEL_TANKS.put(ID, tankEntityType);
    }

    private static void registerTrunk(String ID) {
        Identifier identifier = Identifier.of(VehicleMod.MOD_ID, ID);

        EntityType<Trunk> trunkEntityType = EntityType.Builder.<Trunk>create(
                        Trunk::new,
                        SpawnGroup.MISC
                ).dimensions(1f, 1f)
                .disableSummon()
                .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier));

        Registry.register(
                Registries.ENTITY_TYPE,
                identifier,
                trunkEntityType
        );

        TRUNKS.put(ID, trunkEntityType);
    }

    private static void registerSeat() {
        Registry.register(Registries.ENTITY_TYPE, SEAT_IDENTIFIER, SEAT_ENTITY_TYPE);
    }

    private static void registerFuelTanks() {
        registerFuelTank(Civic.FUEL_TANK_ID);
    }

    private static void registerTrunks() {
        registerTrunk(Civic.TRUNK_ID);
    }

    private static void registerComponentRenderers() {
        registerRenderer(
            SEAT_ENTITY_TYPE,
            NoOpRenderer<Seat>::new
        );

        for (String ID: FUEL_TANKS.keySet()) {
            registerRenderer(
                FUEL_TANKS.get(ID),
                NoOpRenderer<FuelTank>::new
            );
        }

        for (String ID: TRUNKS.keySet()) {
            registerRenderer(
                TRUNKS.get(ID),
                NoOpRenderer<Trunk>::new
            );
        }
    }

    public static EntityType<FuelTank> getFuelTankType(String ID) {
        return FUEL_TANKS.get(ID);
    }

    public static EntityType<Trunk> getTrunkType(String ID) {
        return TRUNKS.get(ID);
    }

    public static <T extends Vehicle> EntityType<T> getVehicleType(String ID) {
        return (EntityType<T>) ENTITY_TYPES.get(ID);
    }

    public static <T extends Vehicle> VehicleItem<T> getItem(String ID) {
        return (VehicleItem<T>) ITEMS.get(ID);
    }
}
