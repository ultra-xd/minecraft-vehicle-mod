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
    protected final Vec3d OFFSET;
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
        OFFSET = new Vec3d(offsetX, offsetY, offsetZ);

        updatePosition();

        setBoundingBox(getDimensions(EntityPose.STANDING).getBoxAt(getPos()));
    }

    protected VehicleComponent(EntityType<?> type, World world) {
        super(type, world);
        this.parent = null;
        OFFSET = new Vec3d(0d, 0d, 0d);
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
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        if (parent != null) {
            nbt.putLong("parentMost", parentUuid.getMostSignificantBits());
            nbt.putLong("parentLeast", parentUuid.getLeastSignificantBits());
        }
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

            updatePosition();

        }
    }

    protected void updatePosition() {
        if (parent == null || parent.isRemoved()) return;

        Vec3d newPos = parent.getPos().add(OFFSET);
        setPos(newPos.getX(), newPos.getY(), newPos.getZ());
        setBoundingBox(getDimensions(getPose()).getBoxAt(newPos));
    }

    public abstract EntityDimensions getDimensions(EntityPose pose);
}
