package net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.components.entity.VehicleComponent;
import org.jetbrains.annotations.Nullable;

/** An entity that stores an inventory*/
public abstract class VehicleInventory extends VehicleComponent implements Inventory, NamedScreenHandlerFactory {
    // Creates an inventory of a set size
    protected final DefaultedList<ItemStack> INVENTORY = DefaultedList.ofSize(
        getInventorySize(),
        ItemStack.EMPTY
    );

    /** Constructor used to register a vehicle inventory. */
    public VehicleInventory(EntityType<?> type, World world) {
        super(type, world);
    }

    /**
     * Initializes a new vehicle inventory.
     * @param type The entity type of the vehicle inventory.
     * @param parent The vehicle which the entity belongs to.
     * @param offsetX The offset of the entity in the X direction.
     * @param offsetY The offset of the entity in the Y direction.
     * @param offsetZ The offset of the entity in the Z direction.
     */
    public VehicleInventory(
        EntityType<?> type,
        Vehicle parent,
        double offsetX,
        double offsetY,
        double offsetZ
    ) {
        super(type, parent, offsetX, offsetY, offsetZ);
    }

    /** Vehicle inventories don't require a data tracker so no data tracker builder required. */
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    /**
     * Updates location of the entity and the contents of the inventory
     * from NBT data when reloading world.
     * @param nbt The NBT data.
     */
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, INVENTORY, getWorld().getRegistryManager());
    }

    /**
     * Writes location of the entity and the contents of the inventory
     * to NBT to be saved when leaving world.
     * @param nbt The NBT data.
     */
    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Inventories.writeNbt(nbt, INVENTORY, getWorld().getRegistryManager());
    }

    /**
     * Ensure that entity does not take damage
     * @param world The world the entity is in.
     * @param source The source of the damage.
     * @param amount The amount of damage taken.
     * @return Always false, since the entity cannot be damaged.
     */
    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    /**
     * Gets size of inventory.
     * @return The size of the inventory.
     */
    @Override
    public int size() {
        return getInventorySize();
    }

    /**
     * Determines if there are no items in the inventory.
     * @return True if the inventory is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return INVENTORY.stream().allMatch(ItemStack::isEmpty);
    }

    /**
     * Gets the stack at the specific slot number of inventory.
     * @param slot The slot number.
     * @return The item stack at the slot number.
     */
    @Override
    public ItemStack getStack(int slot) {
        return INVENTORY.get(slot);
    }

    /**
     * Removes a specific number of items from the stack from the inventory.
     * @param slot The slot number of the stack to remove from.
     * @param amount The amount of items to remove from the stack.
     * @return The item stack received from removing from the stack.
     */
    @Override
    public ItemStack removeStack(int slot, int amount) {
        markDirty();
        return Inventories.splitStack(INVENTORY, slot, amount);
    }

    /**
     * Removes all items from the stack from the inventory.
     * @param slot The slot number of the stack to remove from.
     * @return The item stack received from removing from the stack.
     */
    @Override
    public ItemStack removeStack(int slot) {
        markDirty();
        return Inventories.removeStack(INVENTORY, slot);
    }

    /**
     * Sets the stack at a specific slot.
     * Subclasses should implement this to determine
     * if a stack can be set or not.
     * @param slot The slot number to set the stack at.
     * @param stack The items to add to the slot.
     */
    @Override
    public abstract void setStack(int slot, ItemStack stack);

    /**
     * Marks the inventory as "dirty", i.e changed. This method is not necessary
     * for this entity.
     */
    @Override
    public void markDirty() {}

    /**
     * Determines if a player can open the inventory of this entity.
     * @param player The player opening the inventory.
     * @return True if can open, false otherwise.
     */
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return isAlive() && player.distanceTo(this) <= 8;
    }

    /** Removes all the items from the inventory. */
    @Override
    public void clear() {
        INVENTORY.clear();
    }

    /**
     * Gets the hitbox dimensions of the entity.
     * @param pose The pose of the entity, which does not matter since
     *             this entity only has one pose (the default pose).
     * @return The hitbox dimensions of the entity.
     */
    @Override
    public abstract EntityDimensions getDimensions(EntityPose pose);

    /**
     * Guarantees that nothing can collide with the entity.
     * @param other The other entity to determine if it can collide.
     * @return False always, since this entity is non-collidable.
     */
    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    /**
     * Handles when player clicks the entity. The inventory will open.
     * @param player The player opening the inventory.
     * @param hand The hand the player used to interact with this entity
     *             (which doesn't end up mattering)
     * @return ActionResult.SUCCESS, indicating the player's hand should swing.
     */
    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!getWorld().isClient) {
            // Open the inventory
            player.openHandledScreen(this);
        }

        return ActionResult.SUCCESS;
    }

    /**
     * Creates the screen handler for the inventory. Subclasses should
     * implement this to open a GUI specific for the inventory wanted.
     * @param syncId Integer to specify the specific GUI ID.
     * @param playerInventory The player's inventory.
     * @param player The player.
     * @return A screen handler specific to the entity.
     */
    @Override
    public abstract @Nullable ScreenHandler createMenu(
        int syncId,
        PlayerInventory playerInventory,
        PlayerEntity player
    );

    /**
     * Gets the inventory width, in slots. Subclasses should implement
     * this to determining the inventory width.
     * @return The width of the inventory, in number of slots.
     */
    public abstract int getInventoryWidth();

    /**
     * Gets the inventory height, in slots. Subclasses should implement
     * this to determining the inventory height.
     * @return The height of the inventory, in number of slots.
     */
    public abstract int getInventoryHeight();

    /**
     * Gets how many slots are in the inventory.
     * @return The number of slots in the inventory.
     */
    protected final int getInventorySize() {
        return getInventoryWidth() * getInventoryHeight();
    }
}
