package net.ultra.vehiclemod.vehicles.vehicle_types.rav4.client;

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
import net.ultra.vehiclemod.vehicles.vehicle_types.rav4.custom.Rav4;

public class Rav4Renderer extends VehicleRenderer<Rav4> {
    public static final Identifier TEXTURE = Identifier.of(VehicleMod.MOD_ID, "textures/entity/rav4.png");
    public final Rav4Model MODEL;

    public static final float SCALE_MULTIPLIER = 8.0f;

    public Rav4Renderer(EntityRendererFactory.Context context) {
        super(context);
        MODEL = new Rav4Model(context.getPart(Rav4Model.RAV4));
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
