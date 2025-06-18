package net.ultra.vehiclemod.vehicles.vehicle_types.truck.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.ultra.vehiclemod.VehicleMod;
import net.ultra.vehiclemod.vehicles.VehicleRenderState;
import net.ultra.vehiclemod.vehicles.VehicleRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.client.CivicModel;
import net.ultra.vehiclemod.vehicles.vehicle_types.truck.custom.Truck;

/**
 * creates a truck renderer class to handle all things truck rendering
 */
public class TruckRenderer extends VehicleRenderer<Truck> {
    public static final Identifier TEXTURE = Identifier.of(VehicleMod.MOD_ID, "textures/entity/truck.png");
    public final TruckModel MODEL;

    //truck dimensions
    public static final float MODEL_LENGTH = 6.0f;
    public static final float MODEL_WIDTH = 6.0f;
    public static final float MODEL_HEIGHT = 6.0f;

    /**
     * truck renderer constructor
     * @param context creates the truck entity renderer
     */
    public TruckRenderer(EntityRendererFactory.Context context) {
        super(context);
        MODEL = new TruckModel(context.getPart(TruckModel.TRUCK));
    }

    /**
     * creates the 3d model of the truck
     * @param entityRenderState determines how the entity is rotated
     * @param ms how the model is transformed
     * @param vcp how the vertices of the model is passed in
     * @param light the brightness of the model
     */
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
