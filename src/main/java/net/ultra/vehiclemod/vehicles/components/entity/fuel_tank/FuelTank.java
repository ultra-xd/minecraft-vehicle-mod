package net.ultra.vehiclemod.vehicles.components.entity.fuel_tank;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.components.entity.abstract_vehicle_inventory.VehicleInventory;
import org.jetbrains.annotations.Nullable;

public class FuelTank extends VehicleInventory {
    private final Item[] items;
    public static final int INVENTORY_WIDTH = 5;
    public static final int INVENTORY_HEIGHT = 1;
    public static final int INVENTORY_SIZE = INVENTORY_WIDTH * INVENTORY_HEIGHT;
    public static final String ENTITY_ID = "vehicle_fuel_tank";

    public FuelTank(EntityType<?> type, World world) {
        super(type, world);
        items = new Item[] {Items.COAL};
    }

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

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot < 0 || slot >= INVENTORY.size()) return;

        if (stack.isEmpty()) {
            INVENTORY.set(slot, stack);
            markDirty();
            return;
        }

        for (Item item: items) {
            if (stack.getItem() == item) {
                INVENTORY.set(slot, stack);
                markDirty();
                return;
            }
        }
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(1.0f, 1.0f);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new FuelTankScreenHandler(
            syncId,
            this,
            playerInventory,
            items
        );
    }

    @Override
    public int getInventoryWidth() {
        return INVENTORY_WIDTH;
    }

    @Override
    public int getInventoryHeight() {
        return INVENTORY_HEIGHT;
    }

    public void consumeFuel() {
        for (int i = 0; i < getInventorySize(); i++) {
            ItemStack itemStack = getStack(i);
            if (!itemStack.isEmpty()) {
                itemStack.decrement(1);
                if (itemStack.isEmpty()) {
                    INVENTORY.set(i, ItemStack.EMPTY);
                }

                return;
            }
        }
    }
}
