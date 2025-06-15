package net.ultra.vehiclemod.vehicles;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class VehicleItem<T extends Vehicle> extends Item {

    private EntityType.EntityFactory<T> FACTORY = null;
    private String ENTITY_ID = null;

    public VehicleItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack itemStack = player.getStackInHand(hand);
        HitResult hitResult = raycast(world, player, RaycastContext.FluidHandling.NONE);

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return ActionResult.PASS;
        }

        T entity = FACTORY.create(
            VehicleRegisterer.getVehicleType(ENTITY_ID),
            world
        );

        if (entity == null) {
            return ActionResult.PASS;
        }

        Vec3d position = hitResult.getPos();

        entity.refreshPositionAndAngles(
            position.getX(),
            position.getY(),
            position.getZ(),
            player.getYaw(),
            0.0f
        );

        if (!world.isSpaceEmpty(entity, entity.getBoundingBox())) {
            return ActionResult.PASS;
        }

        world.spawnEntity(entity);
        world.emitGameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getPos());
        itemStack.decrementUnlessCreative(1, player);

        return ActionResult.SUCCESS;
    }

    public VehicleItem<T> factory(EntityType.EntityFactory<T> factory) {
        this.FACTORY = factory;
        return this;
    }

    public VehicleItem<T> entityId(String entityId) {
        this.ENTITY_ID = entityId;
        return this;
    }
}
