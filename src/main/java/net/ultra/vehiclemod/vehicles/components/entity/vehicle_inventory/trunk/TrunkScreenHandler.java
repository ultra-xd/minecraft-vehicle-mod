package net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.trunk;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.ultra.vehiclemod.vehicles.register.VehicleRegisterer;

/** Screen handler for the trunk. */
public class TrunkScreenHandler extends ScreenHandler {
    private Inventory inventory;
    private static final int SLOT_SIZE = 18;

    /**
     * Initializes a new trunk screen handler.
     * @param syncId Integer to specify the specific GUI ID.
     * @param inventory The inventory of the trunk.
     * @param playerInventory The player's inventory.
     */
    public TrunkScreenHandler(
        int syncId,
        Inventory inventory,
        PlayerInventory playerInventory
    ) {
        super(VehicleRegisterer.TRUNK_SCREEN_HANDLER_TYPE, syncId);

        this.inventory = inventory;

        // Verify that the size of the inventory matches the trunk size
        checkSize(this.inventory, Trunk.INVENTORY_SIZE);

        // Notifies inventory that player has opened it
        this.inventory.onOpen(playerInventory.player);

        // Add all slots
        addStorageSlots();
        addPlayerSlots(playerInventory,8, 84);
    }

    /** Adds the trunk's storage slots. */
    public void addStorageSlots() {
        if (inventory == null) return;

        for (int y = 0; y < Trunk.INVENTORY_HEIGHT; y++) {
            for (int x = 0; x < Trunk.INVENTORY_WIDTH; x++) {
                int slotIndex = x + y * Trunk.INVENTORY_WIDTH;
                addSlot(new Slot(
                    inventory,
                    slotIndex,
                    8 + x * SLOT_SIZE,
                    18 + y * SLOT_SIZE
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
