package net.ultra.vehiclemod.vehicles.components.entity.trunk;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.ultra.vehiclemod.vehicles.VehicleRegisterer;

public class TrunkScreenHandler extends ScreenHandler {
    private Inventory inventory;
    private static final int SLOT_SIZE = 18;

    public TrunkScreenHandler(
            int syncId,
            Inventory inventory,
            PlayerInventory playerInventory
    ) {
        super(VehicleRegisterer.TRUNK_SCREEN_HANDLER_TYPE, syncId);

        this.inventory = inventory;
        checkSize(this.inventory, Trunk.INVENTORY_SIZE);
        this.inventory.onOpen(playerInventory.player);

        addStorageSlots();
        addPlayerSlots(playerInventory,8, 84);
    }

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
