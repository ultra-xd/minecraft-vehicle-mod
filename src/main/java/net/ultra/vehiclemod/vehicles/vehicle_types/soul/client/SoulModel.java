package net.ultra.vehiclemod.vehicles.vehicle_types.soul.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.core.VehicleMod;

import java.util.Optional;
/** A KIA Soul model to render into the world, represented using Minecraft's standard entity modelling data. */
public class SoulModel extends EntityModel<EntityRenderState> {
	public static final String MODEL_ID = "soul_model";

    public static final EntityModelLayer SOUL = new EntityModelLayer(
		Identifier.of(VehicleMod.MOD_ID, MODEL_ID),
		"main"
	);

	private final ModelPart root;

	/**
	 * Initializes a KIA Soul model.
	 * @param root The model data.
	 */
	public SoulModel(ModelPart root) {
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
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(40, 53).cuboid(-3.5F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(46, 53).cuboid(-3.5F, -1.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(52, 53).cuboid(2.5F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-4.0F, -3.0F, -7.0F, 8.0F, 1.0F, 14.0F, new Dilation(0.0F))
				.uv(32, 54).cuboid(2.5F, -1.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(18, 41).cuboid(-4.0F, -4.0F, 3.0F, 8.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 15).cuboid(-4.0F, -2.0F, -7.0F, 8.0F, 1.0F, 14.0F, new Dilation(0.0F))
				.uv(0, 30).cuboid(-3.5F, -7.75F, -6.25F, 7.0F, 0.0F, 8.0F, new Dilation(0.0F))
				.uv(32, 46).cuboid(0.75F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(48, 28).cuboid(-3.0F, -6.75F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(48, 32).cuboid(-3.0F, -5.0F, 0.25F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(48, 36).cuboid(0.75F, -6.75F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 49).cuboid(0.75F, -5.0F, 0.25F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(8, 49).cuboid(-3.0F, -5.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(40, 49).cuboid(-3.0F, -5.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(48, 49).cuboid(-3.0F, -6.75F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(32, 50).cuboid(-3.0F, -5.0F, -4.75F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(16, 51).cuboid(0.75F, -5.0F, -4.75F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(24, 51).cuboid(0.75F, -6.75F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 53).cuboid(0.75F, -5.0F, -6.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = root.addChild("cube_r1", ModelPartBuilder.create().uv(44, 27).cuboid(-3.0F, -3.0F, -1.0F, 6.0F, 1.0F, 0.0F, new Dilation(0.0F))
				.uv(38, 54).cuboid(-3.0F, -3.0F, -1.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
				.uv(48, 40).cuboid(-2.0F, -2.0F, -1.0F, 4.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.75F, -5.75F, -0.1309F, 0.0F, 0.0F));

		ModelPartData cube_r2 = root.addChild("cube_r2", ModelPartBuilder.create().uv(44, 28).cuboid(-3.0F, -3.0F, -1.0F, 2.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.5F, -5.75F, -0.0057F, 0.1308F, 1.5268F));

		ModelPartData cube_r3 = root.addChild("cube_r3", ModelPartBuilder.create().uv(18, 46).cuboid(-3.0F, -5.0F, 1.0F, 7.0F, 5.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -3.0F, 3.25F, 0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r4 = root.addChild("cube_r4", ModelPartBuilder.create().uv(20, 55).cuboid(1.0F, -1.0F, -6.0F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.75F, -8.0F, 5.75F, 0.4804F, 0.0387F, -0.0671F));

		ModelPartData cube_r5 = root.addChild("cube_r5", ModelPartBuilder.create().uv(16, 55).cuboid(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.5F, -4.75F, 2.0F, 0.607F, 0.0857F, 0.0842F));

		ModelPartData cube_r6 = root.addChild("cube_r6", ModelPartBuilder.create().uv(44, 10).cuboid(1.0F, -2.0F, -6.0F, 0.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(2.75F, -5.75F, 0.0F, 0.0F, 0.0F, -0.0873F));

		ModelPartData cube_r7 = root.addChild("cube_r7", ModelPartBuilder.create().uv(44, 0).cuboid(0.0F, -3.0F, -6.0F, 0.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-3.75F, -4.75F, 0.0F, 0.0F, 0.0F, 0.1309F));

		ModelPartData cube_r8 = root.addChild("cube_r8", ModelPartBuilder.create().uv(42, 41).cuboid(-1.0F, -2.0F, -2.0F, 1.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -4.0F, 4.75F, 2.0985F, 0.0386F, 0.0247F));

		ModelPartData cube_r9 = root.addChild("cube_r9", ModelPartBuilder.create().uv(44, 20).cuboid(0.0F, -2.0F, -2.0F, 1.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -4.75F, 4.5F, 2.098F, 0.0209F, 0.0622F));

		ModelPartData cube_r10 = root.addChild("cube_r10", ModelPartBuilder.create().uv(12, 53).cuboid(-1.0F, -3.0F, -1.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.75F, -4.75F, -5.75F, -0.1302F, 0.0133F, 0.1303F));

		ModelPartData cube_r11 = root.addChild("cube_r11", ModelPartBuilder.create().uv(16, 49).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.75F, -5.0F, -5.75F, -0.0869F, -0.0057F, -0.1308F));

		ModelPartData cube_r12 = root.addChild("cube_r12", ModelPartBuilder.create().uv(8, 53).cuboid(-1.0F, -3.0F, -1.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.75F, -4.75F, -5.75F, -0.1301F, -0.0133F, -0.1302F));

		ModelPartData cube_r13 = root.addChild("cube_r13", ModelPartBuilder.create().uv(40, 46).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.75F, -5.0F, -5.75F, -0.043F, 0.0076F, 0.1744F));

		ModelPartData cube_r14 = root.addChild("cube_r14", ModelPartBuilder.create().uv(0, 38).cuboid(0.0F, -2.0F, -6.0F, 0.0F, 2.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(-3.75F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

		ModelPartData cube_r15 = root.addChild("cube_r15", ModelPartBuilder.create().uv(30, 30).cuboid(1.0F, -2.0F, -6.0F, 0.0F, 2.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -2.75F, 0.0F, 0.0F, 0.0F, -0.0873F));

		ModelPartData cube_r16 = root.addChild("cube_r16", ModelPartBuilder.create().uv(18, 38).cuboid(-3.0F, -3.0F, -1.0F, 6.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.75F, -6.0F, -0.1309F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 60, 60);
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
