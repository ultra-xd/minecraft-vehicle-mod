package net.ultra.vehiclemod.vehicles.components;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import org.jetbrains.annotations.Nullable;

public class FuelTank extends Entity implements Inventory {
    private final DefaultedList<ItemStack> INVENTORY;

    private final Vehicle PARENT;

    public FuelTank(
        EntityType<?> type,
        World world,
        int INVENTORY_WIDTH,
        int INVENTORY_HEIGHT,
        Vehicle PARENT
    ) {
        super(type, world);
        INVENTORY = DefaultedList.ofSize(INVENTORY_WIDTH * INVENTORY_HEIGHT, ItemStack.EMPTY);
        this.PARENT = PARENT;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

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
        markDirty();
        return null;
    }

    @Override
    public ItemStack removeStack(int slot) {
        markDirty();
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        markDirty();
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

    public class FuelTankScreenHandler extends ScreenHandler {

        private final Inventory INVENTORY;
        private static final int SLOT_SIZE = 18;

        protected FuelTankScreenHandler(
            @Nullable ScreenHandlerType<?> type,
            int syncId,
            int INVENTORY_WIDTH,
            int INVENTORY_HEIGHT,
            Inventory INVENTORY,
            PlayerInventory playerInventory,
            Item[] items
        ) {
            super(type, syncId);

            this.INVENTORY = INVENTORY;

            for (int y = 0; y < INVENTORY_HEIGHT; y++) {
                for (int x = 0; x < INVENTORY_WIDTH; x++) {
                    addSlot(new FilteredSlot(
                        INVENTORY,
                        x + y * INVENTORY_WIDTH,
                        62 + x * SLOT_SIZE, // TODO change the exact slot positions
                        17 + y * SLOT_SIZE,
                        items
                    ));
                }
            }

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 9; x++) {
                    addSlot(new Slot(
                        playerInventory,
                        x + y * 9 + 9,
                        8 + x * SLOT_SIZE,
                        84 + y * SLOT_SIZE
                    ));
                }
            }

            for (int x = 0; x < 9; x++) {
                addSlot(new Slot(
                    playerInventory,
                    x,
                    8 + x * SLOT_SIZE,
                    142
                ));
            }
        }

        @Override
        public ItemStack quickMove(PlayerEntity player, int slot) {
            return null;
        }

        @Override
        public boolean canUse(PlayerEntity player) {
            return INVENTORY.canPlayerUse(player);
        }
    }
}
