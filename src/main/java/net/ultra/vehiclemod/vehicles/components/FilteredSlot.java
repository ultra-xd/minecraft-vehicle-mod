package net.ultra.vehiclemod.vehicles.components;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

/** An inventory slot that can only selectively store certain items. */
public class FilteredSlot extends Slot {

    // Store all allowed items
    private final Item[] items;

    /**
     * Initializes a new filtered slot.
     * @param inventory The inventory of which the slot belongs to.
     * @param index The slot number of the slot of its inventory, zero-indexed.
     * @param x The x coordinate of the slot in its inventory, in pixels.
     * @param y The y coordinate of the slot in its inventory, in pixels.
     * @param items An array of all the items allowed in the slot.
     */
    public FilteredSlot(
        Inventory inventory,
        int index,
        int x,
        int y,
        Item[] items
    ) {
        super(inventory, index, x, y);
        this.items = items;
    }

    /**
     * Determines if an item can be inserted into the slot.
     * @param stack The stack of items to be inserted.
     * @return True if can be inserted, false otherwise.
     */
    @Override
    public boolean canInsert(ItemStack stack) {
        // Return true if matches one of the allowed items
        for (Item item: items) {
            if (item == stack.getItem()) {
                return true;
            }
        }

        return false;
    }
}
