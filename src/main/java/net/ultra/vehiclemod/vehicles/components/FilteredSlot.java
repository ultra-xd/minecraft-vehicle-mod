package net.ultra.vehiclemod.vehicles.components;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class FilteredSlot extends Slot {

    private final Item[] items;

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

    @Override
    public boolean canInsert(ItemStack stack) {
        for (Item item: items) {
            if (item == stack.getItem()) {
                return true;
            }
        }

        return false;
    }
}
