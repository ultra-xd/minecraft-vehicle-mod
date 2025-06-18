package net.ultra.vehiclemod.vehicles.vehicle_types.soul.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.ultra.vehiclemod.VehicleMod;
import net.ultra.vehiclemod.vehicles.VehicleRenderState;
import net.ultra.vehiclemod.vehicles.VehicleRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.client.BugattiModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;
import net.ultra.vehiclemod.vehicles.vehicle_types.soul.custom.Soul;

public class SoulRenderer extends VehicleRenderer<Soul> {
    public static final Identifier TEXTURE = Identifier.of(VehicleMod.MOD_ID, "textures/entity/soul.png");
    public final SoulModel MODEL;

    public static final float MODEL_LENGTH = 8.0f;
    public static final float MODEL_WIDTH = 8.0f;
    public static final float MODEL_HEIGHT = 8.0f;


    public SoulRenderer(EntityRendererFactory.Context context) {
        super(context);
        MODEL = new SoulModel(context.getPart(SoulModel.SOUL));
    }

    @Override
    public void render(
        VehicleRenderState entityRenderState,
        MatrixStack ms,
        VertexConsumerProvider vcp,
        int light
    ) {
        ms.push();

        ms.scale(MODEL_LENGTH, MODEL_HEIGHT, MODEL_WIDTH);
        ms.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(360f - entityRenderState.yaw));
        VertexConsumer vertexConsumer = vcp.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        MODEL.render(ms, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

        ms.pop();
    }
}
