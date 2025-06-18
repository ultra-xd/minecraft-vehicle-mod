package net.ultra.vehiclemod.vehicles.vehicle_types.truck.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;

import java.util.Optional;

/** A Honda Civic model to render into the world, represented using Minecraft's standard entity modelling data. */
public class TruckModel extends EntityModel<EntityRenderState> {
    public static final String MODEL_ID = "truck_model";

    public static final EntityModelLayer TRUCK = new EntityModelLayer(
            Identifier.of(VehicleMod.MOD_ID, MODEL_ID),
            "main"
    );

    private final ModelPart root;

    /**
     * Initializes a truck model.
     * @param root The model data.
     */
    public TruckModel(ModelPart root) {
        super(root);
        this.root = root;
    }

    /**
     * Gets model data.
     * @return The model data.
     */
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(66, 43).cuboid(-8.0F, -2.0F, -4.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(64, 66).cuboid(4.0F, -2.0F, 3.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 68).cuboid(-5.0F, -2.0F, 3.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(6, 68).cuboid(4.0F, -2.0F, -4.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-9.0F, -2.0F, -3.5F, 17.0F, 1.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-9.0F, -3.0F, -4.5F, 17.0F, 1.0F, 9.0F, new Dilation(0.0F))
                .uv(68, 7).cuboid(-8.0F, -2.0F, 3.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(12, 68).cuboid(-5.0F, -2.0F, -4.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(48, 20).cuboid(-9.0F, -9.0F, -4.5F, 11.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(52, 0).cuboid(-9.0F, -9.0F, 3.5F, 11.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(20, 58).cuboid(1.0F, -9.0F, -3.5F, 1.0F, 6.0F, 7.0F, new Dilation(0.0F))
                .uv(66, 37).cuboid(3.0F, -8.0F, -4.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(70, 37).cuboid(6.0F, -5.0F, -4.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(60, 66).cuboid(3.0F, -8.0F, 3.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(70, 40).cuboid(6.0F, -5.0F, 3.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 57).cuboid(7.0F, -5.0F, -4.5F, 1.0F, 2.0F, 9.0F, new Dilation(0.0F))
                .uv(36, 62).cuboid(-9.0F, -9.0F, -3.5F, 1.0F, 6.0F, 7.0F, new Dilation(0.0F))
                .uv(26, 18).cuboid(-11.0F, -9.0F, -4.5F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F))
                .uv(66, 27).cuboid(-13.0F, -7.0F, 3.25F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(66, 32).cuboid(-13.25F, -7.0F, -4.25F, 2.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(52, 62).cuboid(-12.0F, -8.0F, 3.25F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(62, 59).cuboid(-12.0F, -8.0F, -4.25F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F))
                .uv(52, 7).cuboid(4.0F, -8.0F, 3.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(60, 7).cuboid(4.0F, -8.0F, -4.5F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(52, 69).cuboid(4.0F, -5.0F, -4.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(64, 69).cuboid(4.0F, -5.0F, 3.5F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(66, 46).cuboid(4.0F, -7.0F, -4.5F, 3.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(70, 18).cuboid(4.0F, -7.0F, 4.5F, 3.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(70, 59).cuboid(4.0F, -5.0F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(70, 61).cuboid(4.0F, -6.0F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(70, 63).cuboid(5.0F, -5.0F, 1.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(70, 65).cuboid(5.0F, -5.0F, -2.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(70, 67).cuboid(4.0F, -5.0F, -2.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(70, 69).cuboid(4.0F, -6.0F, -2.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 18).cuboid(3.0F, -8.0F, -4.5F, 4.0F, 0.0F, 9.0F, new Dilation(0.0F))
                .uv(44, 48).cuboid(3.0F, -8.0F, -4.5F, 0.0F, 5.0F, 9.0F, new Dilation(0.0F))
                .uv(36, 58).cuboid(-1.0F, -5.0F, 1.5F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 71).cuboid(-2.0F, -4.0F, 2.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(4, 71).cuboid(0.0F, -4.0F, 0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 24.0F, 0.0F));

        ModelPartData cube_r1 = root.addChild("cube_r1", ModelPartBuilder.create().uv(70, 14).cuboid(-1.0F, -3.0F, 4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(70, 10).cuboid(-1.0F, -3.0F, -4.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, -5.0F, -0.5F, 0.0F, 0.0F, -0.2618F));

        ModelPartData cube_r2 = root.addChild("cube_r2", ModelPartBuilder.create().uv(62, 48).cuboid(0.0F, -4.0F, -3.0F, 0.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, -4.25F, -0.5F, 0.0F, 0.0F, -0.2618F));

        ModelPartData cube_r3 = root.addChild("cube_r3", ModelPartBuilder.create().uv(22, 48).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-10.75F, -2.0F, -4.5F, -0.0019F, -0.0436F, -3.0979F));

        ModelPartData cube_r4 = root.addChild("cube_r4", ModelPartBuilder.create().uv(48, 10).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-12.75F, -3.5F, -4.5F, -0.0309F, -0.0308F, -2.3557F));

        ModelPartData cube_r5 = root.addChild("cube_r5", ModelPartBuilder.create().uv(0, 47).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-11.75F, -2.5F, -4.5F, -0.0131F, -0.0416F, -2.8359F));

        ModelPartData cube_r6 = root.addChild("cube_r6", ModelPartBuilder.create().uv(44, 38).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-13.5F, -4.5F, -4.5F, -0.0346F, -0.0266F, -2.2248F));

        ModelPartData cube_r7 = root.addChild("cube_r7", ModelPartBuilder.create().uv(44, 28).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-13.25F, -5.5F, -4.5F, -0.0436F, 0.0F, -1.5708F));

        ModelPartData cube_r8 = root.addChild("cube_r8", ModelPartBuilder.create().uv(22, 38).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-12.5F, -6.5F, -4.5F, 0.0F, 0.0F, -1.1345F));

        ModelPartData cube_r9 = root.addChild("cube_r9", ModelPartBuilder.create().uv(0, 37).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-12.0F, -7.0F, -4.5F, 0.0F, 0.0F, -0.829F));

        ModelPartData cube_r10 = root.addChild("cube_r10", ModelPartBuilder.create().uv(22, 28).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-11.0F, -7.75F, -4.5F, 0.0F, 0.0F, -0.5672F));

        ModelPartData cube_r11 = root.addChild("cube_r11", ModelPartBuilder.create().uv(0, 27).cuboid(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, -8.0F, -4.5F, 0.0F, 0.0F, -0.2618F));
        return TexturedModelData.of(modelData, 76, 76);
    }

    /**
     * Gets individual model parts, given the name of the part.
     * @param name The name of the part.
     * @return The corresponding model part.
     */
    @Override
    public Optional<ModelPart> getPart(String name) {
        return switch (name) {
            case "root" -> Optional.of(root);
            default -> Optional.empty();
        };
    }
}
