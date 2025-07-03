package net.ultra.vehiclemod.core;

import net.fabricmc.api.ModInitializer;

import net.ultra.vehiclemod.vehicles.register.VehicleRegisterer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Initializes the vehicle mod from the server. */
public class VehicleMod implements ModInitializer {
	// The mod ID encapsulates all mod objects
	public static final String MOD_ID = "vehiclemod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	/**
	 * Initializes the mod from the server. This is called automatically
	 * by the fabric modding system on game startup.
	 * */
	@Override
	public void onInitialize() {
		LOGGER.info("Initializing mod " + MOD_ID);

		// Register all object on the server
		VehicleRegisterer.serverRegisterAll();
	}
}