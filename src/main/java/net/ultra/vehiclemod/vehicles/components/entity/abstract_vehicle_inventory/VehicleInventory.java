package net.ultra.vehiclemod.vehicles.components.entity.abstract_vehicle_inventory;

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
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.components.entity.VehicleComponent;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.custom.FuelTankScreenHandler;
import org.jetbrains.annotations.Nullable;

public abstract class VehicleInventory extends VehicleComponent implements Inventory, NamedScreenHandlerFactory {
    protected final DefaultedList<ItemStack> INVENTORY = DefaultedList.ofSize(
        getInventorySize(),
        ItemStack.EMPTY
    );

    public VehicleInventory(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    public VehicleInventory(
        EntityType<?> type,
        Vehicle parent,
        double offsetX,
        double offsetY,
        double offsetZ
    ) {
        super(type, parent, offsetX, offsetY, offsetZ);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        Inventories.readNbt(nbt, INVENTORY, getWorld().getRegistryManager());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        Inventories.writeNbt(nbt, INVENTORY, getWorld().getRegistryManager());
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    public int size() {
        return getInventorySize();
    }

    @Override
    public boolean isEmpty() {
        return INVENTORY.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStack(int slot) {
        return INVENTORY.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        markDirty();
        return Inventories.splitStack(INVENTORY, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        markDirty();
        return Inventories.removeStack(INVENTORY, slot);
    }

    @Override
    public abstract void setStack(int slot, ItemStack stack);

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return isAlive() && player.distanceTo(this) <= 8;
    }

    @Override
    public void clear() {
        INVENTORY.clear();
    }

    @Override
    public abstract EntityDimensions getDimensions(EntityPose pose);

    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!getWorld().isClient) {
            player.openHandledScreen(this);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public abstract @Nullable ScreenHandler createMenu(
        int syncId,
        PlayerInventory playerInventory,
        PlayerEntity player
    );

    public abstract int getInventoryWidth();
    public abstract int getInventoryHeight();

    protected final int getInventorySize() {
        return getInventoryWidth() * getInventoryHeight();
    }
}
