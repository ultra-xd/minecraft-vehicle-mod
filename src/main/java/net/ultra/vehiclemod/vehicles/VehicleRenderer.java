package net.ultra.vehiclemod.vehicles;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;

public abstract class VehicleRenderer<T extends Vehicle> extends EntityRenderer<T, VehicleRenderState> {
    private static final float SHADOW_RADIUS = 0.75F;

    protected VehicleRenderer(
        EntityRendererFactory.Context context
    ) {
        super(context);
    }

    @Override
    public VehicleRenderState createRenderState() {
        return new VehicleRenderState();
    }

    @Override
    public void updateRenderState(
        T vehicle,
        VehicleRenderState renderState,
        float f
    ) {
        super.updateRenderState(vehicle, renderState, f);
        renderState.yaw = vehicle.getYaw();
    }
}
