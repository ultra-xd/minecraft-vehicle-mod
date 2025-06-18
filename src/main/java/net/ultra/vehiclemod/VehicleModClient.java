package net.ultra.vehiclemod;

import net.fabricmc.api.ClientModInitializer;
import net.ultra.vehiclemod.vehicles.VehicleRegisterer;

public class VehicleModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VehicleMod.LOGGER.info("Initializing mod client " + VehicleMod.MOD_ID);
        VehicleRegisterer.clientRegisterAll();
    }
}
