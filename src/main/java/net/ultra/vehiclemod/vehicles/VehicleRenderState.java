package net.ultra.vehiclemod.vehicles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.EntityRenderState;

@Environment(EnvType.CLIENT)
public class VehicleRenderState extends EntityRenderState {
    public float yaw;
}
