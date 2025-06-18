package net.ultra.vehiclemod.vehicles.components.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

/**
 * Some entities (vehicle components) require no rendering.
 * The NoOpRenderer attaches a required renderer to the entity whilst rendering nothing.
 * @param <T> The entity class to render.
 */
public class NoOpRenderer<T extends Entity> extends EntityRenderer<T, EntityRenderState> {
    /**
     * Initializes a new NoOpRenderer
     * @param context A renderer factory, creating NoOpRenderers
     */
    public NoOpRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    /**
     * No entity state, since no rendering.
     * @return Empty render state.
     */
    @Override
    public EntityRenderState createRenderState() {
        return new EntityRenderState();
    }
}
