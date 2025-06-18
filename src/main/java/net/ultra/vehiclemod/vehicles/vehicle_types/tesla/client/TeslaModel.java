package net.ultra.vehiclemod.vehicles.vehicle_types.tesla.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;

import java.util.Optional;

public class TeslaModel extends EntityModel<EntityRenderState> {
	public static final String MODEL_ID = "tesla_model";

    public static final EntityModelLayer TESLA = new EntityModelLayer(
		Identifier.of(VehicleMod.MOD_ID, MODEL_ID),
		"main"
	);

	private final ModelPart root;

	public TeslaModel(ModelPart root) {
		super(root);
		this.root = root;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(36, 22).cuboid(-5.0F, -1.0F, -3.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(34, 36).cuboid(2.0F, -1.0F, 2.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 0).cuboid(2.0F, -1.0F, -3.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 2).cuboid(-5.0F, -1.0F, 2.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-7.0F, -2.0F, -3.0F, 13.0F, 1.0F, 6.0F, new Dilation(0.0F))
				.uv(18, 14).cuboid(-7.0F, -4.0F, -3.0F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 12).cuboid(-0.75F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(26, 38).cuboid(0.0F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 31).cuboid(-0.75F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 33).cuboid(0.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(34, 38).cuboid(-0.75F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 38).cuboid(-0.75F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(16, 24).cuboid(-7.0F, -4.0F, -3.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F))
				.uv(18, 16).cuboid(-7.0F, -4.0F, 2.0F, 10.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 28).cuboid(-6.0F, -3.25F, -3.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F))
				.uv(4, 40).cuboid(-3.75F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(8, 40).cuboid(-3.75F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(40, 14).cuboid(-2.75F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(40, 16).cuboid(-2.75F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(26, 40).cuboid(-3.75F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(34, 40).cuboid(-3.75F, -5.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 8).cuboid(-3.0F, -5.0F, -2.75F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(38, 10).cuboid(-3.0F, -5.0F, 2.75F, 3.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(40, 35).cuboid(0.0F, -5.0F, 2.75F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(38, 40).cuboid(-5.0F, -5.0F, 2.75F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(12, 41).cuboid(-5.0F, -5.0F, -2.75F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(16, 41).cuboid(0.0F, -5.0F, -2.75F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 7).cuboid(-7.0F, -3.0F, -3.0F, 13.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = root.addChild("cube_r1", ModelPartBuilder.create().uv(0, 21).cuboid(3.0F, -1.0F, -5.0F, 2.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -4.25F, 2.0F, 0.0F, 0.0F, 0.3491F));

		ModelPartData cube_r2 = root.addChild("cube_r2", ModelPartBuilder.create().uv(0, 35).cuboid(-1.0F, -3.0F, -2.0F, 0.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-6.25F, -4.0F, 0.0F, 0.0F, 0.0F, 1.2654F));

		ModelPartData cube_r3 = root.addChild("cube_r3", ModelPartBuilder.create().uv(30, 41).cuboid(-1.0F, -3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-6.25F, -4.25F, -5.0F, 0.0F, 0.0F, 1.309F));

		ModelPartData cube_r4 = root.addChild("cube_r4", ModelPartBuilder.create().uv(22, 38).cuboid(-1.0F, -3.0F, -3.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-6.5F, -3.25F, 0.0F, 0.0F, 0.0F, 0.9599F));

		ModelPartData cube_r5 = root.addChild("cube_r5", ModelPartBuilder.create().uv(0, 40).cuboid(-1.0F, -3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-6.25F, -4.0F, 0.0F, 0.0F, 0.0F, 1.2217F));

		ModelPartData cube_r6 = root.addChild("cube_r6", ModelPartBuilder.create().uv(30, 36).cuboid(-1.0F, -2.0F, 2.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
				.uv(14, 31).cuboid(-1.0F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(8, 35).cuboid(-1.0F, -2.0F, -3.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.75F, -5.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		ModelPartData cube_r7 = root.addChild("cube_r7", ModelPartBuilder.create().uv(16, 39).cuboid(-1.0F, -2.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(12, 39).cuboid(-1.0F, -2.0F, 7.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(30, 24).cuboid(-1.0F, -2.0F, 2.0F, 0.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-1.25F, -5.5F, -5.0F, 0.0F, 0.0F, 1.9635F));

		ModelPartData cube_r8 = root.addChild("cube_r8", ModelPartBuilder.create().uv(38, 4).cuboid(-1.0F, -3.0F, -3.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(22, 31).cuboid(-1.0F, -3.0F, -7.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-6.5F, -3.25F, 5.0F, 0.0F, 0.0F, 1.0036F));

		ModelPartData cube_r9 = root.addChild("cube_r9", ModelPartBuilder.create().uv(36, 20).cuboid(3.0F, -1.0F, -5.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(36, 18).cuboid(3.0F, -1.0F, -10.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -6.75F, 7.0F, 0.0F, 0.0F, 0.6545F));

		ModelPartData cube_r10 = root.addChild("cube_r10", ModelPartBuilder.create().uv(20, 20).cuboid(3.0F, -1.0F, -4.0F, 3.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-2.25F, -6.75F, 2.0F, 0.0F, 0.0F, 0.6545F));

		ModelPartData cube_r11 = root.addChild("cube_r11", ModelPartBuilder.create().uv(0, 14).cuboid(3.0F, -1.0F, -5.0F, 3.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.25F, 2.0F, 0.0F, 0.0F, 0.1745F));
		return TexturedModelData.of(modelData, 48, 48);
	}

	@Override
	public Optional<ModelPart> getPart(String name) {
		return switch (name) {
			case "root" -> Optional.of(root);
			default -> Optional.empty();
		};
	}
}
