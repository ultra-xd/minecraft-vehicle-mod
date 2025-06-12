package net.ultra.vehiclemod.vehicles;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.ultra.vehiclemod.VehicleMod;

/**
 *
 */
public abstract class Vehicle extends Entity {

    public final double MAX_SPEED;
    protected double speed = 0;
    public final double BRAKE_POWER;
    public final double MAX_EXPLOSION_POWER;
    private final double ACCELERATION_TICKS;

    private static final int DEFAULT_ACCELERATION_TICKS = 100;

    protected Vehicle(
        EntityType<? extends Vehicle> type,
        World world,
        double MAX_SPEED,
        double BRAKE_POWER,
        double MAX_EXPLOSION_POWER
    ) {
        super(type, world);

        this.MAX_SPEED = MAX_SPEED;
        this.BRAKE_POWER = BRAKE_POWER;
        this.MAX_EXPLOSION_POWER = MAX_EXPLOSION_POWER;
        ACCELERATION_TICKS = DEFAULT_ACCELERATION_TICKS;
    }

    protected Vehicle(
        EntityType<? extends Vehicle> type,
        World world,
        double MAX_SPEED,
        double BRAKE_POWER,
        double MAX_EXPLOSION_POWER,
        double ACCELERATION_TICKS
    ) {
        super(type, world);

        this.MAX_SPEED = MAX_SPEED;
        this.BRAKE_POWER = BRAKE_POWER;
        this.MAX_EXPLOSION_POWER = MAX_EXPLOSION_POWER;
        this.ACCELERATION_TICKS = ACCELERATION_TICKS;
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
        RegistryOps<NbtElement> registryOps = this.getRegistryManager().getOps(NbtOps.INSTANCE);

        nbt.putDouble("max_speed", MAX_SPEED);
        nbt.putDouble("brake_power", BRAKE_POWER);
        nbt.putDouble("max_explosion_power", MAX_EXPLOSION_POWER);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        RegistryOps<NbtElement> registryOps = this.getRegistryManager().getOps(NbtOps.INSTANCE);


    }

    @Override
    public void tick() {
        super.tick();
    }
}
