package net.ultra.vehiclemod.vehicles.vehicle_types.rav4.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.core.VehicleMod;

import java.util.Optional;
/** A Rav4 model to render into the world, represented using Minecraft's standard entity modelling data. */
public class Rav4Model extends EntityModel<EntityRenderState> {
	public static final String MODEL_ID = "rav4_model";

    public static final EntityModelLayer RAV4 = new EntityModelLayer(
		Identifier.of(VehicleMod.MOD_ID, MODEL_ID),
		"main"
	);

	private final ModelPart root;

	/**
	 * Initializes a Rav4 model.
	 * @param root The model data.
	 */
	public Rav4Model(ModelPart root) {
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
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(32, 41).cuboid(-3.0F, -1.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(38, 41).cuboid(-3.0F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-3.0F, -3.0F, -7.0F, 6.0F, 2.0F, 14.0F, new Dilation(0.0F))
				.uv(0, 26).cuboid(2.0F, -4.0F, -5.0F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F))
				.uv(40, 8).cuboid(-2.0F, -4.0F, 6.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(46, 15).cuboid(-2.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(46, 17).cuboid(1.0F, -5.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(46, 19).cuboid(-2.0F, -4.0F, -3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(46, 21).cuboid(1.0F, -4.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(46, 23).cuboid(-2.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(24, 46).cuboid(1.0F, -4.0F, -3.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(46, 39).cuboid(-2.0F, -5.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(28, 47).cuboid(1.0F, -4.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 47).cuboid(-2.0F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(48, 10).cuboid(1.0F, -5.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(48, 12).cuboid(-2.0F, -4.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(16, 48).cuboid(1.0F, -4.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 16).cuboid(-3.0F, -7.0F, -3.5F, 6.0F, 1.0F, 9.0F, new Dilation(0.0F))
				.uv(30, 16).cuboid(-2.75F, -6.0F, -4.0F, 0.0F, 2.0F, 8.0F, new Dilation(0.0F))
				.uv(26, 26).cuboid(-3.0F, -4.0F, -5.0F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F))
				.uv(24, 43).cuboid(2.0F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(30, 44).cuboid(2.0F, -1.0F, 3.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 39).cuboid(2.85F, -6.0F, -4.0F, 0.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = root.addChild("cube_r1", ModelPartBuilder.create().uv(-2, 0).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -6.5F, 0.5627F, -0.0067F, 0.0267F));

		ModelPartData cube_r2 = root.addChild("cube_r2", ModelPartBuilder.create().uv(-2, 0).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 6.0F, 0.5627F, -0.0067F, 0.0267F));

		ModelPartData cube_r3 = root.addChild("cube_r3", ModelPartBuilder.create().uv(40, 6).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.75F, 4.75F, 0.2182F, 0.0F, 0.0F));

		ModelPartData cube_r4 = root.addChild("cube_r4", ModelPartBuilder.create().uv(40, 48).cuboid(-5.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(20, 48).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -5.5F, 5.0F, -1.0908F, 0.0F, 0.0F));

		ModelPartData cube_r5 = root.addChild("cube_r5", ModelPartBuilder.create().uv(36, 48).cuboid(-5.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(24, 48).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -5.25F, 6.25F, -2.5744F, 0.0F, 0.0F));

		ModelPartData cube_r6 = root.addChild("cube_r6", ModelPartBuilder.create().uv(44, 45).cuboid(0.0F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(40, 44).cuboid(5.5F, -2.0F, 0.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.75F, -3.75F, 4.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData cube_r7 = root.addChild("cube_r7", ModelPartBuilder.create().uv(40, 13).cuboid(-2.0F, -2.0F, 1.0F, 4.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.0F, 5.75F, 0.5236F, 0.0F, 0.0F));

		ModelPartData cube_r8 = root.addChild("cube_r8", ModelPartBuilder.create().uv(44, 41).cuboid(0.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(36, 44).cuboid(-5.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -3.75F, 3.5F, -0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r9 = root.addChild("cube_r9", ModelPartBuilder.create().uv(20, 43).cuboid(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
				.uv(16, 43).cuboid(-5.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -3.25F, -5.25F, -0.4363F, 0.0F, 0.0F));

		ModelPartData cube_r10 = root.addChild("cube_r10", ModelPartBuilder.create().uv(40, 4).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.25F, -5.25F, 2.0944F, 0.0F, 0.0F));

		ModelPartData cube_r11 = root.addChild("cube_r11", ModelPartBuilder.create().uv(40, 2).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, -7.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData cube_r12 = root.addChild("cube_r12", ModelPartBuilder.create().uv(40, 10).cuboid(-2.0F, -2.0F, 1.0F, 4.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, -5.25F, -0.4363F, 0.0F, 0.0F));

		ModelPartData cube_r13 = root.addChild("cube_r13", ModelPartBuilder.create().uv(40, 0).cuboid(-1.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -6.5F, 5.5F, -2.5744F, 0.0F, 0.0F));

		ModelPartData cube_r14 = root.addChild("cube_r14", ModelPartBuilder.create().uv(32, 39).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.25F, 5.75F, -1.0908F, 0.0F, 0.0F));

		ModelPartData cube_r15 = root.addChild("cube_r15", ModelPartBuilder.create().uv(16, 39).cuboid(-3.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.25F, -5.0F, 0.1745F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 56, 56);
	}
}
