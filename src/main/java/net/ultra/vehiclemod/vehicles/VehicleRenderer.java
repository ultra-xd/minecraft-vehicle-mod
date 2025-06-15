package net.ultra.vehiclemod.vehicles;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

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
