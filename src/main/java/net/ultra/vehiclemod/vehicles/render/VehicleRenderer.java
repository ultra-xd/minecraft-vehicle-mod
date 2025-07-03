package net.ultra.vehiclemod.vehicles.render;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.ultra.vehiclemod.vehicles.Vehicle;

/**
 * The vehicle renderer renders the vehicle in the world
 * @param <T> The class of the vehicle to render.
 */
public abstract class VehicleRenderer<T extends Vehicle> extends EntityRenderer<T, VehicleRenderState> {

    /**
     * Initializes a new vehicle renderer. Must be registered in the registry.
     * @param context Factory used to provide context information to create entity renderer.
     */
    protected VehicleRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    /** Creates a new vehicle render state.*/
    @Override
    public VehicleRenderState createRenderState() {
        return new VehicleRenderState();
    }

    /**
     * Updates the render state with the vehicle direction.
     * @param vehicle The vehicle to be rendered.
     * @param renderState The render state of the vehicle, including its direction (yaw).
     * @param f The "partial tick delta" or the interpolation factor, required for smooth rotation.
     */
    @Override
    public void updateRenderState(
        T vehicle,
        VehicleRenderState renderState,
        float f
    ) {
        super.updateRenderState(vehicle, renderState, f);

        // Update vehicle yaw
        renderState.yaw = vehicle.getYaw(f);
    }
}
