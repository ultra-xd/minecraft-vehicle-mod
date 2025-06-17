package net.ultra.vehiclemod.vehicles;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;
import net.ultra.vehiclemod.vehicles.components.entity.NoOpRenderer;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.FuelTank;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.FuelTankScreenHandler;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.FuelTankInventoryScreen;
import net.ultra.vehiclemod.vehicles.components.entity.seat.Seat;
import net.ultra.vehiclemod.vehicles.components.entity.trunk.Trunk;
import net.ultra.vehiclemod.vehicles.components.entity.trunk.TrunkInventoryScreen;
import net.ultra.vehiclemod.vehicles.components.entity.trunk.TrunkScreenHandler;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;

import java.util.HashMap;

public final class VehicleRegisterer {
    private VehicleRegisterer() {}

    private static final HashMap<String, EntityType<? extends Vehicle>> ENTITY_TYPES = new HashMap<>();
    private static final HashMap<String, VehicleItem<? extends Vehicle>> ITEMS = new HashMap<>();

    private static final Identifier SEAT_IDENTIFIER = Identifier.of(
        VehicleMod.MOD_ID, Seat.ENTITY_ID
    );

    private static final Identifier FUEL_TANK_IDENTIFIER = Identifier.of(
        VehicleMod.MOD_ID, FuelTank.ENTITY_ID
    );

    private static final Identifier TRUNK_IDENTIFIER = Identifier.of(
        VehicleMod.MOD_ID, Trunk.ENTITY_ID
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

    public static final EntityType<FuelTank> FUEL_TANK_ENTITY_TYPE = EntityType.Builder.<FuelTank>create(
        FuelTank::new,
        SpawnGroup.MISC
    ).dimensions(1f, 1f)
     .disableSummon()
     .trackingTickInterval(1)
     .maxTrackingRange(8)
     .makeFireImmune()
     .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, FUEL_TANK_IDENTIFIER));

    public static final EntityType<Trunk> TRUNK_ENTITY_TYPE = EntityType.Builder.<Trunk>create(
        Trunk::new,
        SpawnGroup.MISC
    ).dimensions(1f, 1f)
     .disableSummon()
     .trackingTickInterval(1)
     .maxTrackingRange(8)
     .makeFireImmune()
     .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, TRUNK_IDENTIFIER));

    public static final ScreenHandlerType<FuelTankScreenHandler> FUEL_TANK_SCREEN_HANDLER_TYPE = new ScreenHandlerType<>(
        (syncId, playerInventory) -> new FuelTankScreenHandler(
            syncId,
            new SimpleInventory(FuelTank.INVENTORY_SIZE),
            playerInventory,
            new Item[] {Items.COAL}
        ),
        FeatureFlags.VANILLA_FEATURES
    );

    public static final ScreenHandlerType<TrunkScreenHandler> TRUNK_SCREEN_HANDLER_TYPE = new ScreenHandlerType<>(
        (syncId, playerInventory) -> new TrunkScreenHandler(
            syncId,
            new SimpleInventory(Trunk.INVENTORY_SIZE),
            playerInventory
        ),
        FeatureFlags.VANILLA_FEATURES
    );

    public static void serverRegisterAll() {
        registerSeat();
        registerFuelTanks();
        registerTrunks();
        registerScreenHandlers();
        registerScreens();
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

    private static void registerSeat() {
        Registry.register(Registries.ENTITY_TYPE, SEAT_IDENTIFIER, SEAT_ENTITY_TYPE);
    }

    private static void registerFuelTanks() {
        Registry.register(Registries.ENTITY_TYPE, FUEL_TANK_IDENTIFIER, FUEL_TANK_ENTITY_TYPE);
    }

    private static void registerTrunks() {
        Registry.register(Registries.ENTITY_TYPE, TRUNK_IDENTIFIER, TRUNK_ENTITY_TYPE);
    }

    private static void registerComponentRenderers() {
        registerRenderer(
            SEAT_ENTITY_TYPE,
            NoOpRenderer<Seat>::new
        );

        registerRenderer(
            FUEL_TANK_ENTITY_TYPE,
            NoOpRenderer<FuelTank>::new
        );

        registerRenderer(
            TRUNK_ENTITY_TYPE,
            NoOpRenderer<Trunk>::new
        );
    }

    private static void registerScreenHandlers() {
        Registry.register(
            Registries.SCREEN_HANDLER,
            FuelTank.ENTITY_ID,
            FUEL_TANK_SCREEN_HANDLER_TYPE
        );

        Registry.register(
            Registries.SCREEN_HANDLER,
            Trunk.ENTITY_ID,
            TRUNK_SCREEN_HANDLER_TYPE
        );
    }

    private static void registerScreens() {
        HandledScreens.register(
            FUEL_TANK_SCREEN_HANDLER_TYPE,
            FuelTankInventoryScreen::new
        );

        HandledScreens.register(
            TRUNK_SCREEN_HANDLER_TYPE,
            TrunkInventoryScreen::new
        );
    }

    public static <T extends Vehicle> EntityType<T> getVehicleType(String ID) {
        return (EntityType<T>) ENTITY_TYPES.get(ID);
    }

    public static <T extends Vehicle> VehicleItem<T> getItem(String ID) {
        return (VehicleItem<T>) ITEMS.get(ID);
    }
}
