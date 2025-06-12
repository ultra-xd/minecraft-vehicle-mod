package net.ultra.vehiclemod.vehicles;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicRenderer;

public final class VehicleClientRegisterer {

    private VehicleClientRegisterer() {}

    public static void registerAll() {
        registerRenderer(
            VehicleRegisterer.getEntityType(Civic.ENTITY_ID),
            CivicRenderer::new
        );

        registerModel(CivicModel.CIVIC, CivicModel::getTexturedModelData);
    }

    private static <T extends Vehicle> void registerRenderer(
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
}
