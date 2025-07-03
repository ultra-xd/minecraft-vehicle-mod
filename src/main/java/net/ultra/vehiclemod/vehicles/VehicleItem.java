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
import net.ultra.vehiclemod.vehicles.register.VehicleRegisterer;

/** Vehicle item that can place a vehicle. */
public class VehicleItem<T extends Vehicle> extends Item {
    // Entity creator: creates vehicles
    private EntityType.EntityFactory<T> factory = null;
    private String entityId = null;

    /**
     * Initializes new vehicle item.
     * @param settings The settings of the item.
     */
    public VehicleItem(Settings settings) {
        super(settings.maxCount(1)); // Set max stacking limit to 1
    }

    /**
     * Places a vehicle down, if possible
     * @param world the world the item was used in
     * @param player the player who used the item
     * @param hand the hand used
     * @return SUCCESS if hand should swing, PASS otherwise
     */
    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        assert factory != null && entityId != null;

        // Don't place entity on client side
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        ItemStack itemStack = player.getStackInHand(hand);

        // Find block the player hit
        HitResult hitResult = raycast(world, player, RaycastContext.FluidHandling.NONE);

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return ActionResult.PASS;
        }

        // Create entity
        T entity = factory.create(
            VehicleRegisterer.getVehicleType(entityId),
            world
        );

        if (entity == null) {
            return ActionResult.PASS;
        }

        // Move entity to position
        Vec3d position = hitResult.getPos();

        entity.refreshPositionAndAngles(
            position.getX(),
            position.getY(),
            position.getZ(),
            player.getYaw(),
            0.0f
        );

        // Don't spawn entity if no space
        if (!world.isSpaceEmpty(entity, entity.getBoundingBox())) {
            return ActionResult.PASS;
        }

        // Spawn entity & consume the item
        world.spawnEntity(entity);
        world.emitGameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getPos());
        itemStack.decrementUnlessCreative(1, player);

        return ActionResult.SUCCESS;
    }

    /**
     * Sets the vehicle factory of the item.
     * @param factory The vehicle factory.
     * @return Item with new vehicle factory.
     */
    public VehicleItem<T> factory(EntityType.EntityFactory<T> factory) {
        this.factory = factory;
        return this;
    }

    /**
     * Sets the entity ID of the entity to be spawned.
     * @param entityId The entity ID.
     * @return Item with new entity ID.
     */
    public VehicleItem<T> entityId(String entityId) {
        this.entityId = entityId;
        return this;
    }
}
