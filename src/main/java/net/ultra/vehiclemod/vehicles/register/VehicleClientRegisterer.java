package net.ultra.vehiclemod.vehicles.register;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.ultra.vehiclemod.core.VehicleMod;
import net.ultra.vehiclemod.vehicles.components.entity.NoOpRenderer;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.fuel_tank.FuelTank;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.fuel_tank.FuelTankInventoryScreen;
import net.ultra.vehiclemod.vehicles.components.entity.seat.Seat;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.trunk.Trunk;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.trunk.TrunkInventoryScreen;
import net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.client.BugattiModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.client.BugattiRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.custom.Bugatti;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;
import net.ultra.vehiclemod.vehicles.vehicle_types.rav4.client.Rav4Model;
import net.ultra.vehiclemod.vehicles.vehicle_types.rav4.client.Rav4Renderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.rav4.custom.Rav4;
import net.ultra.vehiclemod.vehicles.vehicle_types.soul.client.SoulModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.soul.client.SoulRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.soul.custom.Soul;
import net.ultra.vehiclemod.vehicles.vehicle_types.tesla.client.TeslaModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.tesla.client.TeslaRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.tesla.custom.Tesla;
import net.ultra.vehiclemod.vehicles.vehicle_types.truck.client.TruckModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.truck.client.TruckRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.truck.custom.Truck;

/** Registers all objects on client side, such as renderers, models and inventory screen UIs. */
public final class VehicleClientRegisterer {
    private VehicleClientRegisterer() {}

    /**
     * Registers all that is required on the client, including
     * renderers and models.
     */
    public static void clientRegisterAll() {
        registerComponentRenderers();
        registerScreens();

        // Register all vehicle renderers
        registerRenderer(
            VehicleRegisterer.getVehicleType(Civic.ENTITY_ID),
            CivicRenderer::new
        );

        registerRenderer(
            VehicleRegisterer.getVehicleType(Rav4.ENTITY_ID),
            Rav4Renderer::new
        );

        registerRenderer(
            VehicleRegisterer.getVehicleType(Truck.ENTITY_ID),
            TruckRenderer::new
        );

        registerRenderer(
            VehicleRegisterer.getVehicleType(Tesla.ENTITY_ID),
            TeslaRenderer::new
        );

        registerRenderer(
            VehicleRegisterer.getVehicleType(Bugatti.ENTITY_ID),
            BugattiRenderer::new
        );

        registerRenderer(
            VehicleRegisterer.getVehicleType(Soul.ENTITY_ID),
            SoulRenderer::new
        );

        // Register all vehicle models
        registerModel(CivicModel.CIVIC, CivicModel::getTexturedModelData);
        registerModel(TruckModel.TRUCK, TruckModel::getTexturedModelData);
        registerModel(TeslaModel.TESLA, TeslaModel::getTexturedModelData);
        registerModel(Rav4Model.RAV4, Rav4Model::getTexturedModelData);
        registerModel(BugattiModel.BUGATTI, BugattiModel::getTexturedModelData);
        registerModel(SoulModel.SOUL, SoulModel::getTexturedModelData);
    }

    /** Registers renders of all vehicle components. They don't render, so they all use NoOpRenderer. */
    private static void registerComponentRenderers() {
        registerRenderer(
            VehicleRegisterer.SEAT_ENTITY_TYPE,
            NoOpRenderer<Seat>::new
        );

        registerRenderer(
            VehicleRegisterer.FUEL_TANK_ENTITY_TYPE,
            NoOpRenderer<FuelTank>::new
        );

        registerRenderer(
            VehicleRegisterer.TRUNK_ENTITY_TYPE,
            NoOpRenderer<Trunk>::new
        );
    }

    /**
     * Registers a renderer for an entity.
     * @param type The entity type.
     * @param factory The renderer factory which creates entity renderers.
     * @param <T> The class of the entity to render.
     */
    private static <T extends Entity> void registerRenderer(
        EntityType<T> type,
        EntityRendererFactory<T> factory
    ) {
        VehicleMod.LOGGER.info(
            "Registering {} renderer for {}", type.getName(), VehicleMod.MOD_ID
        );

        // Register the renderer
        EntityRendererRegistry.register(type, factory);
    }

    /**
     * Registers the model of an entity, i.e how it looks.
     * @param model The model of the entity.
     * @param provider The provider of the model data.
     */
    private static void registerModel(
        EntityModelLayer model,
        EntityModelLayerRegistry.TexturedModelDataProvider provider
    ) {
        VehicleMod.LOGGER.info(
            "Registering {} model for {}", model.name(), VehicleMod.MOD_ID
        );

        EntityModelLayerRegistry.registerModelLayer(model, provider);
    }

    /** Registers fuel tank and trunk screens */
    private static void registerScreens() {
        HandledScreens.register(
            VehicleRegisterer.FUEL_TANK_SCREEN_HANDLER_TYPE,
            FuelTankInventoryScreen::new
        );

        HandledScreens.register(
            VehicleRegisterer.TRUNK_SCREEN_HANDLER_TYPE,
            TrunkInventoryScreen::new
        );
    }
}
