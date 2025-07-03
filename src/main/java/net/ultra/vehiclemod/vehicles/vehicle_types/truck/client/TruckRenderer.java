package net.ultra.vehiclemod.vehicles.vehicle_types.truck.client;

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
import net.ultra.vehiclemod.vehicles.vehicle_types.truck.custom.Truck;


public class TruckRenderer extends VehicleRenderer<Truck> {
    public static final Identifier TEXTURE = Identifier.of(VehicleMod.MOD_ID, "textures/entity/truck.png");
    public final TruckModel MODEL;

    public static final float SCALE_MULTIPLIER = 7.5f;

    public TruckRenderer(EntityRendererFactory.Context context) {
        super(context);
        MODEL = new TruckModel(context.getPart(TruckModel.TRUCK));
    }

    public void render(
        VehicleRenderState entityRenderState,
        MatrixStack ms,
        VertexConsumerProvider vcp,
        int light
    ) {
        ms.push();

        ms.scale(SCALE_MULTIPLIER, SCALE_MULTIPLIER, SCALE_MULTIPLIER);
        ms.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));
        ms.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(156f + entityRenderState.yaw));
        VertexConsumer vertexConsumer = vcp.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        MODEL.render(ms, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        ms.pop();
    }
}
