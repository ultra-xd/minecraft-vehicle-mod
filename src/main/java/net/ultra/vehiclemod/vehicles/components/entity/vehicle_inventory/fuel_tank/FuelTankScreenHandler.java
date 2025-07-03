package net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.fuel_tank;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.ultra.vehiclemod.vehicles.register.VehicleRegisterer;
import net.ultra.vehiclemod.vehicles.components.FilteredSlot;

/** Screen handler for the fuel tank. */
public class FuelTankScreenHandler extends ScreenHandler {
    private Inventory inventory;
    private static final int SLOT_SIZE = 18; // Slot width, in pixels

    /**
     * Initializes a new fuel tank screen handler.
     * @param syncId Integer to specify the specific GUI ID.
     * @param inventory The inventory of the fuel tank.
     * @param playerInventory The player's inventory.
     * @param items The allowed items in the fuel tank.
     */
    public FuelTankScreenHandler(
        int syncId,
        Inventory inventory,
        PlayerInventory playerInventory,
        Item[] items
    ) {
        super(VehicleRegisterer.FUEL_TANK_SCREEN_HANDLER_TYPE, syncId);

        this.inventory = inventory;

        // Verify that the size of the inventory matches the fuel tank size
        checkSize(this.inventory, FuelTank.INVENTORY_SIZE);

        // Notifies inventory that player has opened it
        this.inventory.onOpen(playerInventory.player);

        // Add all slots
        addCustomSlots(items);
        addPlayerSlots(playerInventory,8, 51);
    }

    /**
     * Adds the fuel tank's custom selective slots.
     * @param items The allowed items in the fuel tank.
     */
    public void addCustomSlots(Item[] items) {
        if (inventory == null) return;

        // Add fuel tank slots
        for (int y = 0; y < FuelTank.INVENTORY_HEIGHT; y++) {
            for (int x = 0; x < FuelTank.INVENTORY_WIDTH; x++) {
                int slotIndex = x + y * FuelTank.INVENTORY_WIDTH;
                addSlot(new FilteredSlot(
                    inventory,
                    slotIndex,
                    44 + x * SLOT_SIZE,
                    20 + y * SLOT_SIZE,
                    items
                ));
            }
        }
    }

    /**
     * Determines how items move when a player quick-moves an item
     * (shift-clicks it)
     * @param player The player shift clicking.
     * @param slotNumber the index of the slot to quick-move from
     * @return The moved stack, or the empty stack if the move is not possible.
     */
    @Override
    public ItemStack quickMove(PlayerEntity player, int slotNumber) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(slotNumber);

        // If the slot has an item stack
        if (slot.hasStack()) {
            ItemStack newItemStack = slot.getStack();
            itemStack = newItemStack.copy();

            // If the slot is in the fuel tank inventory, move to player inventory
            if (slotNumber < inventory.size()) {
                if (!insertItem(newItemStack, inventory.size(), slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }

            // Otherwise, move from player inventory to fuel tank
            else if (!insertItem(newItemStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            // Update the slot if the stack is now empty
            if (newItemStack.isEmpty()) slot.setStack(ItemStack.EMPTY);
            else slot.markDirty();
        }

        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        inventory.onClose(player);
    }
}
