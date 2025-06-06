package net.ultra.vehiclemod;

import net.fabricmc.api.ModInitializer;

import net.ultra.vehiclemod.vehicles.HondaCivic;
import net.ultra.vehiclemod.vehicles.Bugatti;
import net.ultra.vehiclemod.vehicles.Tesla;
import net.ultra.vehiclemod.vehicles.Rav4;
import net.ultra.vehiclemod.vehicles.KiaSoul;
import net.ultra.vehiclemod.vehicles.GarbageTruck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VehicleMod implements ModInitializer {
	public static final String MOD_ID = "vehiclemod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		HondaCivic.registerItem();
		Bugatti.registerItem();
		Tesla.registerItem();
		Rav4.registerItem();
		KiaSoul.registerItem();
		GarbageTruck.registerItem();
	}

}