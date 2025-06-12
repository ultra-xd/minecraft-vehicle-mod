package net.ultra.vehiclemod.vehicles.vehicle_types.civic.client;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;
import net.ultra.vehiclemod.vehicles.VehicleRenderer;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;

public class CivicRenderer extends VehicleRenderer<Civic, EntityRenderState> {

    public static final Identifier TEXTURE = Identifier.of(VehicleMod.MOD_ID, "textures/entity/civic.png");
    public final CivicModel MODEL;

    public static final float MODEL_LENGTH = 2.0f;
    public static final float MODEL_WIDTH = 2.0f;
    public static final float MODEL_HEIGHT = 2.0f;


    public CivicRenderer(EntityRendererFactory.Context context) {
        super(context);
        MODEL = new CivicModel(context.getPart(CivicModel.CIVIC));
    }

    @Override
    public void render(
        EntityRenderState entityRenderState,
        MatrixStack ms,
        VertexConsumerProvider vcp,
        int light
    ) {
        VertexConsumer vertexConsumer = vcp.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
        MODEL.render(ms, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
    }
}
