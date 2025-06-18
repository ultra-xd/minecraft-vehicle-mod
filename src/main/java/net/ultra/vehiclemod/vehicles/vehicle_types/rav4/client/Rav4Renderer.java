package net.ultra.vehiclemod.vehicles.vehicle_types.rav4.client;

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
import net.ultra.vehiclemod.vehicles.vehicle_types.rav4.custom.Rav4;

/**
 * creates a rav4 renderer class to handle all things rav4 rendering
 */
public class Rav4Renderer extends VehicleRenderer<Rav4> {
    public static final Identifier TEXTURE = Identifier.of(VehicleMod.MOD_ID, "textures/entity/rav4.png");
    public final Rav4Model MODEL;

    //rav4 model dimensions
    public static final float MODEL_LENGTH = 8.0f;
    public static final float MODEL_WIDTH = 8.0f;
    public static final float MODEL_HEIGHT = 8.0f;


    /**
     * rav4 renderer constructor
     * @param context creates the rav4 entity renderer
     */
    public Rav4Renderer(EntityRendererFactory.Context context) {
        super(context);
        MODEL = new Rav4Model(context.getPart(Rav4Model.RAV4));
    }

    /**
     * creates the 3d model of the Rav4
     * @param entityRenderState determines how the entity is rotated
     * @param ms how the model is transformed
     * @param vcp how the vertices of the model is passed in
     * @param light the brightness of the model
     */
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
        ms.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(66f + entityRenderState.yaw));
        VertexConsumer vertexConsumer = vcp.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        MODEL.render(ms, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        ms.pop();
    }
}
