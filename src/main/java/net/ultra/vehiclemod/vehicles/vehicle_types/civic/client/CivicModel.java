package net.ultra.vehiclemod.vehicles.vehicle_types.civic.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;

import java.util.Optional;

public class CivicModel extends EntityModel<EntityRenderState> {
	public static final String MODEL_ID = "civic_model";

    public static final EntityModelLayer CIVIC = new EntityModelLayer(
		Identifier.of(VehicleMod.MOD_ID, MODEL_ID),
		"main"
	);

	private final ModelPart root;

	public CivicModel(ModelPart root) {
		super(root);
		this.root = root;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(32, 26).cuboid(1.5F, -1.0F, -4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 33).cuboid(1.5F, -1.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(6, 33).cuboid(-2.5F, -1.0F, -4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(12, 33).cuboid(-2.5F, -1.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 11).cuboid(1.5F, -2.5F, -5.0F, 1.0F, 1.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-2.5F, -2.0F, -5.0F, 5.0F, 1.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 22).cuboid(-2.5F, -2.5F, -5.0F, 1.0F, 1.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 36).cuboid(-1.5F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(4, 36).cuboid(-1.5F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(8, 36).cuboid(-1.5F, -3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(12, 36).cuboid(0.5F, -3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(36, 34).cuboid(0.5F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(36, 36).cuboid(0.5F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(30, 0).cuboid(-1.5F, -2.5F, 4.0F, 3.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(32, 16).cuboid(-1.5F, -3.0F, -5.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(16, 37).cuboid(0.5F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(20, 37).cuboid(0.5F, -3.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(24, 37).cuboid(0.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(28, 37).cuboid(-1.5F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 37).cuboid(-1.5F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 38).cuboid(-1.5F, -3.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 18).cuboid(-1.5F, -2.0F, 5.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(18, 34).cuboid(-2.5F, -3.0F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(30, 30).cuboid(1.5F, -3.0F, -5.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F))
				.uv(38, 28).cuboid(-1.5F, -3.5F, -5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 30).cuboid(0.5F, -3.5F, -5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(30, 3).cuboid(-2.5F, -3.5F, -5.0F, 5.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 24).cuboid(-1.5F, -3.0F, -4.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(22, 11).cuboid(-2.5F, -4.0F, -3.0F, 5.0F, 0.0F, 5.0F, new Dilation(0.0F))
				.uv(22, 16).cuboid(-2.25F, -4.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.0F))
				.uv(38, 32).cuboid(2.25F, -3.0F, 2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(22, 23).cuboid(2.25F, -4.0F, -3.0F, 0.0F, 2.0F, 5.0F, new Dilation(0.0F))
				.uv(36, 38).cuboid(-2.25F, -3.0F, 2.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = root.addChild("cube_r1", ModelPartBuilder.create().uv(30, 34).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(24, 34).cuboid(-5.0F, -1.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -2.25F, -4.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r2 = root.addChild("cube_r2", ModelPartBuilder.create().uv(30, 8).cuboid(0.0F, -1.0F, -2.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -3.25F, -2.25F, 0.5672F, 0.0F, 0.0F));

		ModelPartData cube_r3 = root.addChild("cube_r3", ModelPartBuilder.create().uv(22, 30).cuboid(-1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -3.75F, 0.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r4 = root.addChild("cube_r4", ModelPartBuilder.create().uv(30, 4).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -3.25F, 1.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r5 = root.addChild("cube_r5", ModelPartBuilder.create().uv(32, 20).cuboid(-1.0F, -3.0F, 0.0F, 3.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -2.5F, 4.5F, 1.0036F, 0.0F, 0.0F));

		ModelPartData cube_r6 = root.addChild("cube_r6", ModelPartBuilder.create().uv(38, 26).cuboid(-1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 4).cuboid(-1.0F, -1.5F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.75F, -1.0F, 3.75F, 0.0F, 0.9163F, 0.0F));

		ModelPartData cube_r7 = root.addChild("cube_r7", ModelPartBuilder.create().uv(38, 22).cuboid(-1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(4, 38).cuboid(-1.0F, -1.5F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -1.0F, 4.0F, 0.0F, 0.9163F, 0.0F));

		ModelPartData cube_r8 = root.addChild("cube_r8", ModelPartBuilder.create().uv(38, 20).cuboid(-1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 6).cuboid(-1.0F, -1.5F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.75F, -1.0F, 4.0F, 0.0F, 0.829F, 0.0F));

		ModelPartData cube_r9 = root.addChild("cube_r9", ModelPartBuilder.create().uv(12, 38).cuboid(-1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(8, 38).cuboid(-1.0F, -1.5F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -1.0F, 3.75F, 0.0F, 0.9163F, 0.0F));
		return TexturedModelData.of(modelData, 44, 44);
	}

	@Override
	public Optional<ModelPart> getPart(String name) {
		return switch (name) {
			case "root" -> Optional.of(root);
			default -> Optional.empty();
		};
	}
}
