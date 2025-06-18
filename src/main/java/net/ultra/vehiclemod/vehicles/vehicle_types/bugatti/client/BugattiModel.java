package net.ultra.vehiclemod.vehicles.vehicle_types.bugatti.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;

import java.util.Optional;

public class BugattiModel extends EntityModel<EntityRenderState> {
	public static final String MODEL_ID = "bugatti_model";

    public static final EntityModelLayer BUGATTI = new EntityModelLayer(
		Identifier.of(VehicleMod.MOD_ID, MODEL_ID),
		"main"
	);

	private final ModelPart root;

	public BugattiModel(ModelPart root) {
		super(root);
		this.root = root;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData hexadecagon = modelPartData.addChild("hexadecagon", ModelPartBuilder.create().uv(38, 0).cuboid(-16.0F, -8.1989F, 7.0F, 1.0F, 0.3978F, 2.0F, new Dilation(0.0F))
				.uv(6, 42).cuboid(-16.0F, -9.0F, 7.8011F, 1.0F, 2.0F, 0.3978F, new Dilation(0.0F)), ModelTransform.of(-8.0F, 30.25F, -20.75F, 0.0F, 1.5708F, 0.0F));

		ModelPartData hexadecagon_r1 = hexadecagon.addChild("hexadecagon_r1", ModelPartBuilder.create().uv(42, 6).cuboid(-8.0F, -1.0F, -0.1989F, 1.0F, 2.0F, 0.3978F, new Dilation(0.0F))
				.uv(38, 2).cuboid(-8.0F, -0.1989F, -1.0F, 1.0F, 0.3978F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -8.0F, 8.0F, -0.3927F, 0.0F, 0.0F));

		ModelPartData hexadecagon_r2 = hexadecagon.addChild("hexadecagon_r2", ModelPartBuilder.create().uv(4, 42).cuboid(-8.0F, -1.0F, -0.1989F, 1.0F, 2.0F, 0.3978F, new Dilation(0.0F))
				.uv(18, 37).cuboid(-8.0F, -0.1989F, -1.0F, 1.0F, 0.3978F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -8.0F, 8.0F, 0.3927F, 0.0F, 0.0F));

		ModelPartData hexadecagon_r3 = hexadecagon.addChild("hexadecagon_r3", ModelPartBuilder.create().uv(38, 4).cuboid(-8.0F, -0.1989F, -1.0F, 1.0F, 0.3978F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -8.0F, 8.0F, -0.7854F, 0.0F, 0.0F));

		ModelPartData hexadecagon_r4 = hexadecagon.addChild("hexadecagon_r4", ModelPartBuilder.create().uv(12, 37).cuboid(-8.0F, -0.1989F, -1.0F, 1.0F, 0.3978F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-8.0F, -8.0F, 8.0F, 0.7854F, 0.0F, 0.0F));

		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(32, 0).cuboid(-3.0F, -1.0F, 2.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(32, 3).cuboid(2.0F, -1.0F, 2.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(32, 6).cuboid(-3.0F, -1.0F, -4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(10, 32).cuboid(2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-3.0F, -2.0F, -4.0F, 6.0F, 1.0F, 10.0F, new Dilation(0.0F))
				.uv(38, 32).cuboid(-2.0F, -3.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 34).cuboid(1.0F, -3.75F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(10, 39).cuboid(-2.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(14, 39).cuboid(1.0F, -3.0F, -2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(18, 39).cuboid(-2.0F, -3.75F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(22, 39).cuboid(1.0F, -3.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 11).cuboid(2.0F, -3.0F, -3.0F, 1.0F, 1.0F, 9.0F, new Dilation(0.0F))
				.uv(20, 11).cuboid(-3.0F, -3.0F, -3.0F, 1.0F, 1.0F, 9.0F, new Dilation(0.0F))
				.uv(16, 28).cuboid(-2.0F, -3.0F, 5.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(30, 39).cuboid(1.5F, -4.0F, 5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(26, 28).cuboid(-2.0F, -3.0F, -4.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(20, 24).cuboid(-2.0F, -3.25F, -4.0F, 4.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(0, 25).cuboid(-3.0F, -4.0F, 5.0F, 6.0F, 0.0F, 1.0F, new Dilation(0.0F))
				.uv(40, 41).cuboid(-2.5F, -4.0F, 5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 21).cuboid(-3.0F, -4.25F, -0.25F, 6.0F, 0.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 26).cuboid(3.0F, -4.25F, 0.0F, 0.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(42, 20).cuboid(3.0F, -3.25F, -1.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(42, 22).cuboid(3.0F, -4.25F, 3.25F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(42, 24).cuboid(-3.0F, -4.25F, 3.25F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(8, 26).cuboid(-3.0F, -4.25F, 0.0F, 0.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(42, 26).cuboid(-3.0F, -3.25F, -1.0F, 0.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 32).cuboid(-2.0F, -2.0F, -5.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(18, 39).cuboid(-2.0F, -3.75F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 34).cuboid(1.0F, -3.75F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(22, 39).cuboid(1.0F, -3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(10, 39).cuboid(-2.0F, -3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(14, 39).cuboid(1.0F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(38, 32).cuboid(-2.0F, -3.0F, 1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 24.0F, 0.0F));

		ModelPartData cube_r1 = root.addChild("cube_r1", ModelPartBuilder.create().uv(16, 30).cuboid(-2.0F, -1.0F, 1.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -2.0F, -3.5F, 0.3478F, 0.0298F, -0.082F));

		ModelPartData cube_r2 = root.addChild("cube_r2", ModelPartBuilder.create().uv(12, 41).cuboid(-3.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(32, 41).cuboid(-3.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5F, 4.75F, 1.3957F, -0.0052F, 0.0049F));

		ModelPartData cube_r3 = root.addChild("cube_r3", ModelPartBuilder.create().uv(0, 42).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.0F, -3.25F, -3.5F, -0.3552F, -0.7176F, -2.6279F));

		ModelPartData cube_r4 = root.addChild("cube_r4", ModelPartBuilder.create().uv(0, 42).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -3.25F, -3.5F, -0.3552F, 0.7176F, 2.6279F));

		ModelPartData cube_r5 = root.addChild("cube_r5", ModelPartBuilder.create().uv(24, 30).cuboid(1.0F, -1.0F, 1.0F, 1.0F, 1.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -2.0F, -3.5F, 0.3478F, -0.0298F, 0.082F));

		ModelPartData cube_r6 = root.addChild("cube_r6", ModelPartBuilder.create().uv(36, 41).cuboid(-3.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -3.0F, 3.25F, -0.8733F, -0.0052F, 0.0049F));

		ModelPartData cube_r7 = root.addChild("cube_r7", ModelPartBuilder.create().uv(28, 41).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -3.0F, 5.0F, 1.3957F, 0.0052F, -0.0049F));

		ModelPartData cube_r8 = root.addChild("cube_r8", ModelPartBuilder.create().uv(24, 41).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -3.0F, 5.0F, 1.3957F, -0.0052F, 0.0049F));

		ModelPartData cube_r9 = root.addChild("cube_r9", ModelPartBuilder.create().uv(32, 9).cuboid(0.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -2.25F, 5.25F, 0.7418F, 0.0F, 0.0F));

		ModelPartData cube_r10 = root.addChild("cube_r10", ModelPartBuilder.create().uv(20, 41).cuboid(-3.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(26, 39).cuboid(2.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5F, 5.25F, 0.7418F, 0.0F, 0.0F));

		ModelPartData cube_r11 = root.addChild("cube_r11", ModelPartBuilder.create().uv(16, 41).cuboid(2.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -3.0F, 3.25F, -0.8733F, 0.0052F, -0.0049F));

		ModelPartData cube_r12 = root.addChild("cube_r12", ModelPartBuilder.create().uv(16, 27).cuboid(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.25F, 3.25F, -1.615F, -0.0052F, 0.0049F));

		ModelPartData cube_r13 = root.addChild("cube_r13", ModelPartBuilder.create().uv(12, 41).cuboid(-3.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -2.5F, 4.75F, 1.3957F, -0.0052F, 0.0049F));

		ModelPartData cube_r14 = root.addChild("cube_r14", ModelPartBuilder.create().uv(20, 21).cuboid(-3.0F, -1.0F, 1.0F, 6.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, -3.25F, 0.3491F, 0.0F, 0.0F));

		ModelPartData cube_r15 = root.addChild("cube_r15", ModelPartBuilder.create().uv(0, 37).cuboid(-2.0F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -1.0F, 1.0F, 0.0F, 0.0F, 1.0908F));

		ModelPartData cube_r16 = root.addChild("cube_r16", ModelPartBuilder.create().uv(34, 36).cuboid(1.0F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -1.0F, 1.0F, 0.0F, 0.0F, -1.0908F));

		ModelPartData cube_r17 = root.addChild("cube_r17", ModelPartBuilder.create().uv(28, 27).cuboid(-2.0F, -1.0F, 1.0F, 4.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.75F, 4.5F, 0.9593F, -0.0052F, 0.0049F));

		ModelPartData cube_r18 = root.addChild("cube_r18", ModelPartBuilder.create().uv(28, 36).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -3.0F, -4.0F, -0.2645F, -0.7161F, -2.5682F));

		ModelPartData cube_r19 = root.addChild("cube_r19", ModelPartBuilder.create().uv(8, 41).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.25F, -3.0F, -3.5F, -0.2645F, 0.7161F, 2.5682F));

		ModelPartData cube_r20 = root.addChild("cube_r20", ModelPartBuilder.create().uv(40, 36).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, -5.25F, -1.3892F, -0.4505F, 0.0927F));

		ModelPartData cube_r21 = root.addChild("cube_r21", ModelPartBuilder.create().uv(40, 18).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, -5.25F, -1.3892F, 0.4505F, -0.0927F));

		ModelPartData cube_r22 = root.addChild("cube_r22", ModelPartBuilder.create().uv(40, 16).cuboid(-2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(40, 12).cuboid(-2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -0.75F, 0.0F, 0.0F, 0.0F, 1.3526F));

		ModelPartData cube_r23 = root.addChild("cube_r23", ModelPartBuilder.create().uv(40, 14).cuboid(1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
				.uv(4, 40).cuboid(1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, -0.75F, 0.0F, 0.0F, 0.0F, -1.3526F));

		ModelPartData cube_r24 = root.addChild("cube_r24", ModelPartBuilder.create().uv(40, 10).cuboid(1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.25F, -2.5F, -0.25F, 0.1276F, 0.5093F, -0.7705F));

		ModelPartData cube_r25 = root.addChild("cube_r25", ModelPartBuilder.create().uv(40, 8).cuboid(-2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -0.75F, 0.0F, 0.0F, 0.0F, 1.0908F));

		ModelPartData cube_r26 = root.addChild("cube_r26", ModelPartBuilder.create().uv(0, 40).cuboid(-2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.75F, -0.75F, 0.0F, 0.0F, 0.0F, 0.9163F));

		ModelPartData cube_r27 = root.addChild("cube_r27", ModelPartBuilder.create().uv(38, 39).cuboid(1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -0.75F, 0.0F, 0.0F, 0.0F, -1.0908F));

		ModelPartData cube_r28 = root.addChild("cube_r28", ModelPartBuilder.create().uv(34, 39).cuboid(-2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.25F, -2.5F, -0.25F, 0.1276F, -0.5093F, 0.7705F));

		ModelPartData cube_r29 = root.addChild("cube_r29", ModelPartBuilder.create().uv(36, 27).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.75F, -2.75F, -4.5F, -0.4937F, -0.4073F, -2.3148F));

		ModelPartData cube_r30 = root.addChild("cube_r30", ModelPartBuilder.create().uv(6, 35).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -3.0F, -4.0F, -0.2645F, 0.7161F, 2.5682F));

		ModelPartData cube_r31 = root.addChild("cube_r31", ModelPartBuilder.create().uv(38, 30).cuboid(1.0F, -1.0F, -4.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.75F, -0.75F, 0.0F, 0.0F, 0.0F, -0.9163F));

		ModelPartData cube_r32 = root.addChild("cube_r32", ModelPartBuilder.create().uv(38, 25).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -2.5F, -5.5F, -1.08F, 0.4943F, -0.0731F));

		ModelPartData cube_r33 = root.addChild("cube_r33", ModelPartBuilder.create().uv(38, 23).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.75F, -5.5F, -1.2098F, -0.3826F, -0.1342F));

		ModelPartData cube_r34 = root.addChild("cube_r34", ModelPartBuilder.create().uv(38, 21).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -2.0F, -6.0F, -0.9458F, 0.6025F, -0.2337F));

		ModelPartData cube_r35 = root.addChild("cube_r35", ModelPartBuilder.create().uv(38, 6).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -2.5F, -5.5F, -1.08F, -0.4943F, 0.0731F));

		ModelPartData cube_r36 = root.addChild("cube_r36", ModelPartBuilder.create().uv(22, 34).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -2.0F, -4.25F, 0.0F, 0.6545F, -3.1416F));

		ModelPartData cube_r37 = root.addChild("cube_r37", ModelPartBuilder.create().uv(16, 34).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.75F, -2.75F, -4.25F, -0.3887F, -0.4242F, -2.3587F));

		ModelPartData cube_r38 = root.addChild("cube_r38", ModelPartBuilder.create().uv(0, 34).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.75F, -2.75F, -4.5F, -0.4937F, 0.4073F, 2.3148F));

		ModelPartData cube_r39 = root.addChild("cube_r39", ModelPartBuilder.create().uv(6, 38).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -1.0F, -6.0F, 0.0F, 0.4363F, 0.0F));

		ModelPartData cube_r40 = root.addChild("cube_r40", ModelPartBuilder.create().uv(24, 37).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -2.0F, -6.0F, -0.9458F, -0.6025F, 0.2337F));

		ModelPartData cube_r41 = root.addChild("cube_r41", ModelPartBuilder.create().uv(32, 33).cuboid(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.75F, -2.75F, -4.25F, -0.3887F, 0.4242F, 2.3587F));

		ModelPartData cube_r42 = root.addChild("cube_r42", ModelPartBuilder.create().uv(12, 35).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.25F, -3.0F, -3.5F, -0.2645F, -0.7161F, -2.5682F));

		ModelPartData cube_r43 = root.addChild("cube_r43", ModelPartBuilder.create().uv(28, 34).cuboid(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -1.0F, -6.0F, 0.0F, -0.4363F, 0.0F));

		ModelPartData cube_r44 = root.addChild("cube_r44", ModelPartBuilder.create().uv(16, 25).cuboid(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.75F, -5.5F, -1.2703F, 0.3977F, 0.0899F));

		ModelPartData cube_r45 = root.addChild("cube_r45", ModelPartBuilder.create().uv(32, 30).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.5F, -2.0F, -4.25F, 0.0F, -0.6545F, 3.1416F));

		ModelPartData cube_r46 = root.addChild("cube_r46", ModelPartBuilder.create().uv(32, 24).cuboid(-1.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, -1.0F, -4.25F, 0.0F, -0.5672F, 0.0F));
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
