package net.ultra.vehiclemod;

import net.fabricmc.api.ClientModInitializer;
import net.ultra.vehiclemod.vehicles.VehicleRegisterer;

/** Initializes the vehicle mod from the client*/
public class VehicleModClient implements ClientModInitializer {

    /**
     * Initializes the mod from the client side. This is called automatically
     * when the Minecraft client starts.
     */
    @Override
    public void onInitializeClient() {
        VehicleMod.LOGGER.info("Initializing mod client " + VehicleMod.MOD_ID);

        // Register all objects on client
        VehicleRegisterer.clientRegisterAll();
    }
}
