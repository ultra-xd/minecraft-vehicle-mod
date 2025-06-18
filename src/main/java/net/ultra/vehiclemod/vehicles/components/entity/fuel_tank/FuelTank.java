package net.ultra.vehiclemod.vehicles.components.entity.fuel_tank;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.components.entity.abstract_vehicle_inventory.VehicleInventory;
import org.jetbrains.annotations.Nullable;

/** The fuel tank stores fuel for the vehicle. It gradually decreases in fuel over time.*/
public class FuelTank extends VehicleInventory {
    private final Item[] items; // The types of fuels allowed in the fuel tank
    public static final int INVENTORY_WIDTH = 5;
    public static final int INVENTORY_HEIGHT = 1;
    public static final int INVENTORY_SIZE = INVENTORY_WIDTH * INVENTORY_HEIGHT;
    public static final String ENTITY_ID = "vehicle_fuel_tank";

    /** Constructor used to register a fuel tank. */
    public FuelTank(EntityType<?> type, World world) {
        super(type, world);
        items = new Item[] {Items.COAL};
    }

    /**
     * Initializes a new fuel tank.
     * @param type The entity type of the fuel tank.
     * @param parent The vehicle which the entity belongs to.
     * @param offsetX The offset of the entity in the X direction.
     * @param offsetY The offset of the entity in the Y direction.
     * @param offsetZ The offset of the entity in the Z direction.
     * @param items The allowed fuel items in the fuel tank.
     */
    public FuelTank(
        EntityType<?> type,
        Vehicle parent,
        double offsetX,
        double offsetY,
        double offsetZ,
        Item[] items
    ) {
        super(type, parent, offsetX, offsetY, offsetZ);
        this.items = items;
    }

    /**
     * Sets the stack in the inventory.
     * @param slot The slot number to set the stack at.
     * @param stack The items to add to the slot.
     */
    @Override
    public void setStack(int slot, ItemStack stack) {
        // Return if slot number is out of bounds
        if (slot < 0 || slot >= INVENTORY.size()) return;

        // Do nothing if stack is empty
        if (stack.isEmpty()) {
            INVENTORY.set(slot, stack);
            markDirty();
            return;
        }

        // If stack is not empty, set if stack is appropriate fuel item
        for (Item item: items) {
            if (stack.getItem() == item) {
                INVENTORY.set(slot, stack);
                markDirty();
                return;
            }
        }
    }

    /**
     * Gets the size of the fuel tank hitbox.
     * @param pose The pose of the entity, which does not matter since
     *             this entity only has one pose (the default pose).
     * @return The entity dimensions, which will be 1x1/
     */
    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(1.0f, 1.0f);
    }

    /**
     * Creates a new fuel tank screen handler.
     * @param syncId Integer to specify the specific GUI ID.
     * @param playerInventory The player's inventory.
     * @param player The player.
     * @return The fuel tank screen handler.
     */
    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FuelTankScreenHandler(
            syncId,
            this,
            playerInventory,
            items
        );
    }

    /**
     * Gets the inventory width in slots.
     * @return The inventory width in slots.
     */
    @Override
    public int getInventoryWidth() {
        return INVENTORY_WIDTH;
    }

    /**
     * Gets the inventory height in slots.
     * @return The inventory height in slots.
     */
    @Override
    public int getInventoryHeight() {
        return INVENTORY_HEIGHT;
    }

    /** Consumes one unit of fuel, if possible. Removes an item from the fuel tank. */
    public void consumeFuel() {
        // Go through all slots
        for (int i = 0; i < getInventorySize(); i++) {

            // Decrease item stack if it exists.
            ItemStack itemStack = getStack(i);
            if (!itemStack.isEmpty()) {
                itemStack.decrement(1);

                // Set empty item stack if amount in item stack is 0
                if (itemStack.isEmpty()) {
                    INVENTORY.set(i, ItemStack.EMPTY);
                }

                return;
            }
        }
    }
}
