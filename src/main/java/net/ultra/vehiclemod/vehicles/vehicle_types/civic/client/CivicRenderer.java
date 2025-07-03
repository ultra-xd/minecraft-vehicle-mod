package net.ultra.vehiclemod.vehicles.vehicle_types.civic.client;

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
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;

public class CivicRenderer extends VehicleRenderer<Civic> {
    public static final Identifier TEXTURE = Identifier.of(VehicleMod.MOD_ID, "textures/entity/civic.png");
    public final CivicModel MODEL;

    public static final float SCALE_MULTIPLIER = 10.0f;

    public CivicRenderer(EntityRendererFactory.Context context) {
        super(context);
        MODEL = new CivicModel(context.getPart(CivicModel.CIVIC));
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
        ms.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(246f + entityRenderState.yaw));
        VertexConsumer vertexConsumer = vcp.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        MODEL.render(ms, vertexConsumer, light, OverlayTexture.DEFAULT_UV);

        ms.pop();
    }
}
