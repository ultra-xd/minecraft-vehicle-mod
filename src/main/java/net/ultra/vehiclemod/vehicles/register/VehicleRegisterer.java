package net.ultra.vehiclemod.vehicles.register;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
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
import net.ultra.vehiclemod.core.VehicleMod;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.VehicleItem;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.fuel_tank.FuelTank;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.fuel_tank.FuelTankScreenHandler;
import net.ultra.vehiclemod.vehicles.components.entity.seat.Seat;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.trunk.Trunk;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.trunk.TrunkScreenHandler;
import net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.custom.Bugatti;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;
import net.ultra.vehiclemod.vehicles.vehicle_types.tesla.custom.Tesla;
import net.ultra.vehiclemod.vehicles.vehicle_types.truck.custom.Truck;
import net.ultra.vehiclemod.vehicles.vehicle_types.rav4.custom.Rav4;
import net.ultra.vehiclemod.vehicles.vehicle_types.soul.custom.Soul;

import java.util.HashMap;
/** Registers all objects on server side, such as entities, items and screen handlers. */
public final class VehicleRegisterer {
    private VehicleRegisterer() {}

    // Store all entity types and items in a HashMap, with the entity/item id being the key
    private static final HashMap<String, EntityType<? extends Vehicle>> ENTITY_TYPES = new HashMap<>();
    private static final HashMap<String, VehicleItem<? extends Vehicle>> ITEMS = new HashMap<>();

    // Create identifiers
    private static final Identifier SEAT_IDENTIFIER = Identifier.of(
        VehicleMod.MOD_ID, Seat.ENTITY_ID
    );

    private static final Identifier FUEL_TANK_IDENTIFIER = Identifier.of(
        VehicleMod.MOD_ID, FuelTank.ENTITY_ID
    );

    private static final Identifier TRUNK_IDENTIFIER = Identifier.of(
        VehicleMod.MOD_ID, Trunk.ENTITY_ID
    );

    // Create entity types
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

    // Create screen handler types
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

    /**
     * Registers all that is required on the server, including
     * seats, fuel tanks, trunks, screen handlers, screens,
     * vehicles and items.
     */
    public static void serverRegisterAll() {
        // Register vehicle components
        registerSeat();
        registerFuelTanks();
        registerTrunks();
        registerScreenHandlers();

        // Register vehicles
        registerVehicle(
            Civic.ENTITY_ID,
            Civic::new,
            Civic.VEHICLE_HITBOX_WIDTH,
            Civic.VEHICLE_HITBOX_HEIGHT
        );

        registerVehicle(
            Truck.ENTITY_ID,
            Truck::new,
            Truck.VEHICLE_HITBOX_WIDTH,
            Truck.VEHICLE_HITBOX_HEIGHT
        );

        registerVehicle(
            Tesla.ENTITY_ID,
            Tesla::new,
            Tesla.VEHICLE_HITBOX_WIDTH,
            Tesla.VEHICLE_HITBOX_HEIGHT
        );

        registerVehicle(
            Rav4.ENTITY_ID,
            Rav4::new,
            Rav4.VEHICLE_HITBOX_WIDTH,
            Rav4.VEHICLE_HITBOX_HEIGHT
        );

        registerVehicle(
            Bugatti.ENTITY_ID,
            Bugatti::new,
            Bugatti.VEHICLE_HITBOX_WIDTH,
            Bugatti.VEHICLE_HITBOX_HEIGHT
        );
        registerVehicle(
            Soul.ENTITY_ID,
            Soul::new,
            Soul.VEHICLE_HITBOX_WIDTH,
            Soul.VEHICLE_HITBOX_HEIGHT
        );

        // Register items
        registerItem(Civic.ITEM_ID, Civic.ENTITY_ID, Civic::new);
        registerItem(Truck.ITEM_ID, Truck.ENTITY_ID, Truck::new);
        registerItem(Tesla.ITEM_ID, Tesla.ENTITY_ID, Tesla::new);
        registerItem(Rav4.ITEM_ID, Rav4.ENTITY_ID, Rav4::new);
        registerItem(Bugatti.ITEM_ID, Bugatti.ENTITY_ID, Bugatti::new);
        registerItem(Soul.ITEM_ID, Soul.ENTITY_ID, Soul::new);
    }

    /**
     * Registers an vehicle item, which can place down a vehicle entity.
     * @param itemId The item ID.
     * @param entityId The vehicle ID to be placed.
     * @param factory The vehicle entity factory, which creates the vehicle.
     * @param <T> The class of the vehicle to be created.
     */
    private static <T extends Vehicle> void registerItem(
        String itemId,
        String entityId,
        EntityType.EntityFactory<T> factory
    ) {
        VehicleMod.LOGGER.info("Registering {} item for {}", itemId, VehicleMod.MOD_ID);

        // Create identifier for the item
        Identifier identifier = Identifier.of(VehicleMod.MOD_ID, itemId);

        // Register the item
        VehicleItem<T> item = Registry.register(
            Registries.ITEM,
            identifier,
            new VehicleItem<T>(new VehicleItem.Settings().registryKey(RegistryKey.of(
                RegistryKeys.ITEM,
                identifier
            ))).factory(factory).entityId(entityId) // Add the vehicle to be spawned
        );

        // Add item into search menu
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL)
            .register(entries -> entries.add(item));

        // Add item to HashMap
        ITEMS.put(itemId, item);
    }

    /**
     * Registers a vehicle entity.
     * @param ID The entity ID of the vehicle entity.
     * @param factory The vehicle entity factory, which creates the vehicle.
     * @param <T> The class of the vehicle to be created.
     */
    private static <T extends Vehicle> void registerVehicle(
        String ID,
        EntityType.EntityFactory<T> factory,
        float vehicleWidth,
        float vehicleHeight
    ) {
        VehicleMod.LOGGER.info("Registering {} entity for {}", ID, VehicleMod.MOD_ID);

        // Create the entity identifier
        Identifier identifier = Identifier.of(VehicleMod.MOD_ID, ID);

        // Create the entity type of the vehicle
        EntityType<T> vehicleEntityType = EntityType.Builder.create(
            factory,
            SpawnGroup.MISC
        ).dimensions(vehicleWidth, vehicleHeight)
         .dropsNothing()
         .maxTrackingRange(128)
         .makeFireImmune()
         .trackingTickInterval(1)
         .build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier));

        // Register the entity type
        Registry.register(
            Registries.ENTITY_TYPE,
            identifier,
            vehicleEntityType
        );

        // Add the entity type to HashMap
        ENTITY_TYPES.put(ID, vehicleEntityType);
    }

    /** Registers vehicle seat entity. */
    private static void registerSeat() {
        Registry.register(Registries.ENTITY_TYPE, SEAT_IDENTIFIER, SEAT_ENTITY_TYPE);
    }

    /** Registers vehicle fuel tank entity. */
    private static void registerFuelTanks() {
        Registry.register(Registries.ENTITY_TYPE, FUEL_TANK_IDENTIFIER, FUEL_TANK_ENTITY_TYPE);
    }

    /** Registers vehicle trunk entity. */
    private static void registerTrunks() {
        Registry.register(Registries.ENTITY_TYPE, TRUNK_IDENTIFIER, TRUNK_ENTITY_TYPE);
    }

    /** Registers fuel tank and trunk screen handlers. */
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

    /**
     * Gets vehicle type based on entity ID.
     * @param ID The vehicle ID
     * @return The vehicle type.
     * @param <T> The class of the vehicle.
     */
    public static <T extends Vehicle> EntityType<T> getVehicleType(String ID) {
        return (EntityType<T>) ENTITY_TYPES.get(ID);
    }
}
