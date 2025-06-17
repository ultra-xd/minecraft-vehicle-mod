package net.ultra.vehiclemod.vehicles.components.entity.fuel_tank;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.ultra.vehiclemod.vehicles.VehicleRegisterer;
import net.ultra.vehiclemod.vehicles.components.FilteredSlot;

public class FuelTankScreenHandler extends ScreenHandler {
    private Inventory inventory;
    private static final int SLOT_SIZE = 18;

    public FuelTankScreenHandler(
        int syncId,
        Inventory inventory,
        PlayerInventory playerInventory,
        Item[] items
    ) {
        super(VehicleRegisterer.FUEL_TANK_SCREEN_HANDLER_TYPE, syncId);

        this.inventory = inventory;
        checkSize(this.inventory, FuelTank.INVENTORY_SIZE);
        this.inventory.onOpen(playerInventory.player);

        addCustomSlots(items);
        addPlayerSlots(playerInventory,8, 51);
    }

    public void addCustomSlots(Item[] items) {
        if (inventory == null) return;

        for (int y = 0; y < FuelTank.INVENTORY_HEIGHT; y++) {
            for (int x = 0; x < FuelTank.INVENTORY_WIDTH; x++) {
                int slotIndex = x + y * FuelTank.INVENTORY_WIDTH;
                addSlot(new FilteredSlot(
                    inventory,
                    slotIndex,
                    44 + x * SLOT_SIZE, // TODO change the exact slot positions
                    20 + y * SLOT_SIZE,
                    items
                ));
            }
        }
    }



    @Override
    public ItemStack quickMove(PlayerEntity player, int slotNumber) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(slotNumber);

        if (slot.hasStack()) {
            ItemStack newItemStack = slot.getStack();
            itemStack = newItemStack.copy();

            if (slotNumber < inventory.size()) {
                if (!insertItem(newItemStack, inventory.size(), slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!insertItem(newItemStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

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
