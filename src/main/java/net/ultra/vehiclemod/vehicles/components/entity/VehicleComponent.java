package net.ultra.vehiclemod.vehicles.components.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

import java.util.Optional;
import java.util.UUID;

public abstract class VehicleComponent extends Entity {

    protected Vehicle parent;
    protected Vec3d offset;
    protected UUID parentUuid = null;

    protected VehicleComponent(
        EntityType<?> type,
        Vehicle parent,
        double offsetX,
        double offsetY,
        double offsetZ
    ) {
        super(type, parent.getWorld());
        this.parent = parent;
        parentUuid = parent.getUuid();
        offset = new Vec3d(offsetX, offsetY, offsetZ);

        updatePosition();

        setBoundingBox(getDimensions(EntityPose.STANDING).getBoxAt(getPos()));
    }

    protected VehicleComponent(EntityType<?> type, World world) {
        super(type, world);
        this.parent = null;
        offset = new Vec3d(0d, 0d, 0d);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("parentMost") && nbt.contains("parentLeast")) {
            Optional<Long> most = nbt.getLong("parentMost");
            Optional<Long> least = nbt.getLong("parentLeast");

            if (most.isPresent() && least.isPresent()) {
                parentUuid = new UUID(most.get(), least.get());
            }
        }

        if (
            nbt.contains("offsetX") &&
            nbt.contains("offsetY") &&
            nbt.contains("offsetZ")
        ) {
            Optional<Double> x = nbt.getDouble("offsetX");
            Optional<Double> y = nbt.getDouble("offsetY");
            Optional<Double> z = nbt.getDouble("offsetZ");

            if (x.isPresent() && y.isPresent() && z.isPresent()) {
                offset = new Vec3d(x.get(), y.get(), z.get());
            }
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (parent != null) {
            nbt.putLong("parentMost", parentUuid.getMostSignificantBits());
            nbt.putLong("parentLeast", parentUuid.getLeastSignificantBits());
        }

        nbt.putDouble("offsetX", offset.getX());
        nbt.putDouble("offsetY", offset.getY());
        nbt.putDouble("offsetZ", offset.getZ());
    }

    @Override
    public void tick() {
        super.tick();

        World world = getWorld();

        if (world != null && !world.isClient) {
            if (parent == null || parent.isRemoved()) {
                Entity entity = world.getEntity(parentUuid);

                if (entity instanceof Vehicle vehicle) {
                    parent = vehicle;
                }
            }
        }
    }

    public void updatePosition() {
        if (parent == null || parent.isRemoved()) return;

        Vec3d parentPos = parent.getPos();
        double parentYaw = Math.toRadians(parent.getYaw());

        // Use rotation matrix to calculate new position based on parent's yaw
        Vec3d newPos = new Vec3d(
                offset.getX() * Math.cos(parentYaw) - offset.getZ() * Math.sin(parentYaw) + parentPos.getX(),
                offset.getY() + parent.getY(),
                offset.getX() * Math.sin(parentYaw) + offset.getZ() * Math.cos(parentYaw) + parentPos.getZ()
        );

        setPos(newPos.getX(), newPos.getY(), newPos.getZ());
        setBoundingBox(getDimensions(getPose()).getBoxAt(newPos));
    }

    public abstract EntityDimensions getDimensions(EntityPose pose);

    @Override
    public boolean isInvisible() {
        return false;
    }

    @Override
    public boolean collidesWith(Entity other) {
        return true;
    }

    @Override
    public boolean canHit() {
        return true;
    }

    public Vehicle getParent() {
        return parent;
    }
}
