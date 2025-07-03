package net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.ultra.vehiclemod.core.VehicleMod;
import net.ultra.vehiclemod.vehicles.render.VehicleRenderState;
import net.ultra.vehiclemod.vehicles.render.VehicleRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.custom.Bugatti;

public class BugattiRenderer extends VehicleRenderer<Bugatti> {
    public static final Identifier TEXTURE = Identifier.of(VehicleMod.MOD_ID, "textures/entity/bugatti.png");
    public final BugattiModel MODEL;

    public static final float SCALE_MULTIPLIER = 8.0f;

    public BugattiRenderer(EntityRendererFactory.Context context) {
        super(context);
        MODEL = new BugattiModel(context.getPart(BugattiModel.BUGATTI));
    }

    @Override
    public void render(
        VehicleRenderState entityRenderState,
        MatrixStack ms,
        VertexConsumerProvider vcp,
        int light
    ) {
        ms.push();

        ms.scale(SCALE_MULTIPLIER, SCALE_MULTIPLIER, SCALE_MULTIPLIER);
        ms.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        ms.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(66f + entityRenderState.yaw));
        VertexConsumer vertexConsumer = vcp.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        MODEL.render(ms, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        ms.pop();
    }
}
