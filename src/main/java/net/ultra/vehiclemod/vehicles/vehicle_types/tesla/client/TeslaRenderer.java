package net.ultra.vehiclemod.vehicles.vehicle_types.tesla.client;

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
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;
import net.ultra.vehiclemod.vehicles.vehicle_types.tesla.custom.Tesla;

public class TeslaRenderer extends VehicleRenderer<Tesla> {
    public static final Identifier TEXTURE = Identifier.of(VehicleMod.MOD_ID, "textures/entity/tesla.png");
    public final TeslaModel MODEL;

    public static final float MODEL_LENGTH = 8.0f;
    public static final float MODEL_WIDTH = 8.0f;
    public static final float MODEL_HEIGHT = 8.0f;


    public TeslaRenderer(EntityRendererFactory.Context context) {
        super(context);
        MODEL = new TeslaModel(context.getPart(TeslaModel.TESLA));
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
        ms.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        ms.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(156f + entityRenderState.yaw));
        VertexConsumer vertexConsumer = vcp.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        MODEL.render(ms, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        ms.pop();
    }
}
