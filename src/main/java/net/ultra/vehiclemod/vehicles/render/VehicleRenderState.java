package net.ultra.vehiclemod.vehicles.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EntityRenderState;

/** Keeps track of the vehicle's direction to be used in rendering. */
@Environment(EnvType.CLIENT)
public class VehicleRenderState extends EntityRenderState {
    public float yaw;
}
