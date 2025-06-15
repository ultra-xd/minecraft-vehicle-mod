package net.ultra.vehiclemod.vehicles.components.entity.trunk.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.components.entity.VehicleComponent;

public class Trunk extends VehicleComponent implements Inventory {

    private final DefaultedList<ItemStack> INVENTORY;

    public Trunk(
        EntityType<?> type,
        Vehicle parent,
        int INVENTORY_WIDTH,
        int INVENTORY_HEIGHT,
        double offsetX,
        double offsetY,
        double offsetZ
    ) {
        super(type, parent, offsetX, offsetY, offsetZ);
        INVENTORY = DefaultedList.ofSize(INVENTORY_WIDTH * INVENTORY_HEIGHT, ItemStack.EMPTY);
    }

    public Trunk(EntityType<?> type, World world) {
        super(type, world);
        INVENTORY = null;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(1.0f, 1.0f); // Example dimensions, adjust as needed
    }

    public class ScreenHandler {

    }
}
