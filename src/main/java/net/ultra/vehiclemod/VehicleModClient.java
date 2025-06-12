package net.ultra.vehiclemod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.ultra.vehiclemod.vehicles.VehicleClientRegisterer;

public class VehicleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        VehicleClientRegisterer.registerAll();
    }
}
