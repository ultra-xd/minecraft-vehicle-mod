package net.ultra.vehiclemod.vehicles.components.entity.trunk.custom;

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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.ultra.vehiclemod.vehicles.components.entity.abstract_vehicle_inventory.VehicleInventory;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.custom.FuelTankScreenHandler;
import org.jetbrains.annotations.Nullable;

public class Trunk extends VehicleInventory {

    public static final int INVENTORY_WIDTH = 9;
    public static final int INVENTORY_HEIGHT = 3;
    public static final int INVENTORY_SIZE = INVENTORY_WIDTH * INVENTORY_HEIGHT;
    public static final String ENTITY_ID = "vehicle_trunk";

    public Trunk(EntityType<?> type, World world) {
        super(type, world);
    }

    public Trunk(
        EntityType<?> type,
        Vehicle parent,
        double offsetX,
        double offsetY,
        double offsetZ
    ) {
        super(type, parent, offsetX, offsetY, offsetZ);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot < 0 || slot >= INVENTORY.size()) return;

        INVENTORY.set(slot, stack);
        markDirty();
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(1.0f, 1.0f);
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TrunkScreenHandler(
            syncId,
            this,
            playerInventory
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
}
