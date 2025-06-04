package net.ultra.vehiclemod;

import net.fabricmc.api.ModInitializer;

import net.ultra.vehiclemod.vehicles.HondaCivic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VehicleMod implements ModInitializer {
	public static final String MOD_ID = "vehiclemod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		HondaCivic.registerItem();
	}
}