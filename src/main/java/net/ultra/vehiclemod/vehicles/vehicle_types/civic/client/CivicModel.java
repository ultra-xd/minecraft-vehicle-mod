package net.ultra.vehiclemod.vehicles.vehicle_types.civic.client;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;
import net.ultra.vehiclemod.vehicles.vehicle_types.civic.custom.Civic;

import java.util.Optional;

public class CivicModel extends EntityModel<EntityRenderState> {
	public static final String MODEL_ID = "civic_model";

    public static final EntityModelLayer CIVIC = new EntityModelLayer(
		Identifier.of(VehicleMod.MOD_ID, MODEL_ID),
		"main"
	);

	private final ModelPart root;
	private final ModelPart wheels;
	private final ModelPart body;

	public CivicModel(ModelPart root) {
		super(root);
		this.root = root;
		this.wheels = root.getChild("wheels");
		this.body = root.getChild("body");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		ModelPartData wheels = root.addChild("wheels", ModelPartBuilder.create()
						.uv(0, 0).cuboid(8.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F)
						.uv(0, 0).cuboid(0.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F)
						.uv(0, 0).cuboid(8.0F, -2.0F, -7.0F, 2.0F, 2.0F, 2.0F)
						.uv(0, 0).cuboid(0.0F, -2.0F, -7.0F, 2.0F, 2.0F, 2.0F),
				ModelTransform.of(-5.0F, 24.0F, 3.0F, 0, 0, 0)
		);

		ModelPartData body = root.addChild("body", ModelPartBuilder.create()
						.uv(0, 0).cuboid(-7.0F, -3.0F, -4.0F, 14.0F, 1.0F, 8.0F)
						.uv(0, 10).cuboid(-6.0F, -5.0F, 3.0F, 12.0F, 2.0F, 1.0F)
						.uv(0, 13).cuboid(-4.0F, -7.0F, 3.0F, 8.0F, 2.0F, 1.0F)
						.uv(0, 16).cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 2.0F, 1.0F)
						.uv(0, 19).cuboid(-6.0F, -5.0F, -4.0F, 12.0F, 2.0F, 1.0F)
						.uv(0, 22).cuboid(4.0F, -5.0F, -3.0F, 2.0F, 1.0F, 6.0F)
						.uv(0, 25).cuboid(-6.0F, -5.0F, -3.0F, 2.0F, 1.0F, 6.0F)
						.uv(0, 28).cuboid(-4.0F, -7.0F, -3.0F, 8.0F, 1.0F, 6.0F),
				ModelTransform.of(0.0F, 24.0F, 0.0F, 0, 0, 0)
		);

		body.addChild("back_r1", ModelPartBuilder.create()
						.uv(32, 0).cuboid(-0.9F, -1.6F, -1.0F, 1.0F, 2.0F, 8.0F),
				ModelTransform.of(-6.0F, -3.0F, -3.0F, 0.0F, 0.0F, 0.4363F)
		);

		body.addChild("front_r1", ModelPartBuilder.create()
						.uv(32, 10).cuboid(-0.1F, -1.6F, -1.0F, 1.0F, 2.0F, 8.0F),
				ModelTransform.of(6.0F, -3.0F, -3.0F, 0.0F, 0.0F, -0.3927F)
		);

		// Update to your texture resolution
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public Optional<ModelPart> getPart(String name) {
		return switch (name) {
			case "wheels" -> Optional.of(wheels);
			case "body" -> Optional.of(body);
			default -> Optional.empty();
		};
	}
}
