package net.ultra.vehiclemod;

import net.fabricmc.api.ModInitializer;

import net.ultra.vehiclemod.vehicles.VehicleRegisterer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VehicleMod implements ModInitializer {
	public static final String MOD_ID = "vehiclemod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing mod " + MOD_ID);

		VehicleRegisterer.serverRegisterAll();
	}
}