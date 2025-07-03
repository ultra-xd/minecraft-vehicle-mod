package net.ultra.vehiclemod.vehicles.components.entity;

import net.minecraft.entity.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

import java.util.Optional;
import java.util.UUID;

/** A vehicle component follows around its vehicle constantly. */
public abstract class VehicleComponent extends Entity {

    // The vehicle that the component follows around
    protected Vehicle parent;
    protected Vec3d offset;

    // The UUID helps reconnect the child to its parent upon loading a save file.
    protected UUID parentUuid = null;

    /**
     * Initializes a new vehicle component.
     * @param type The entity type of the vehicle component.
     * @param parent The vehicle which the entity belongs to.
     * @param offsetX The offset of the entity in the X direction.
     * @param offsetY The offset of the entity in the Y direction.
     * @param offsetZ The offset of the entity in the Z direction.
     */
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

        // Update hitbox
        updatePosition();
        setBoundingBox(getDimensions(EntityPose.STANDING).getBoxAt(getPos()));
    }

    /** Constructor used to register a vehicle component. */
    protected VehicleComponent(EntityType<?> type, World world) {
        super(type, world);
        this.parent = null;
        offset = new Vec3d(0d, 0d, 0d);
    }

    /**
     * Reassigns parent and offset coordinates to parent upon reload.
     * @param readView The data containing the UUID of the parent and offset.
     */
    @Override
    protected void readCustomData(ReadView readView) {
        // Find parent UUID
        Optional<Long> parentMost = readView.getOptionalLong("parentMost");
        Optional<Long> parentLeast = readView.getOptionalLong("parentLeast");

        if (parentMost.isPresent() && parentLeast.isPresent()) {
            parentUuid = new UUID(parentMost.get(), parentLeast.get());
        }

        // Find offset NBT and reassign
        double x = readView.getDouble("offsetX", 0d);
        double y = readView.getDouble("offsetY", 0d);
        double z = readView.getDouble("offsetZ", 0d);

        offset = new Vec3d(x, y, z);
    }

    /**
     * Saves parent UUID and offset when quitting world.
     * @param writeView The data containing the UUID of the parent and offset.
     */
    @Override
    protected void writeCustomData(WriteView writeView) {
        // Add parent UUID
        if (parent != null) {
            writeView.putLong("parentMost", parentUuid.getMostSignificantBits());
            writeView.putLong("parentLeast", parentUuid.getLeastSignificantBits());
        }

        // Add offsets
        writeView.putDouble("offsetX", offset.getX());
        writeView.putDouble("offsetY", offset.getY());
        writeView.putDouble("offsetZ", offset.getZ());
    }

    /** Updates the vehicle component. */
    @Override
    public void tick() {
        super.tick();

        World world = getWorld();
        if (world != null && !world.isClient) {
            // Reassign parent if no parent and we have parent UUID
            if (parent == null || parent.isRemoved()) {
                Entity entity = world.getEntity(parentUuid);

                if (entity instanceof Vehicle vehicle) {
                    parent = vehicle;
                }
            }
        }

        updatePosition();
    }

    /** Updates position of vehicle component relative to vehicle on the server. */
    public void updatePosition() {
        if (parent == null || parent.isRemoved()) return;

        // Get position of parent and where it's facing
        Vec3d parentPos = parent.getPos();
        double parentYaw = Math.toRadians(parent.getYaw());

        // Use rotation matrix to calculate new position based on parent's yaw
        Vec3d newPos = new Vec3d(
            offset.getX() * Math.cos(parentYaw) - offset.getZ() * Math.sin(parentYaw) + parentPos.getX(),
            offset.getY() + parent.getY(),
            offset.getX() * Math.sin(parentYaw) + offset.getZ() * Math.cos(parentYaw) + parentPos.getZ()
        );

        // Update hitbox
        setPos(newPos.getX(), newPos.getY(), newPos.getZ());
        setBoundingBox(getDimensions(getPose()).getBoxAt(newPos));
    }

    /**
     * Gets the size of the vehicle component hitbox.
     * @param pose The pose of the entity, which does not matter since
     *             this entity only has one pose (the default pose).
     * @return The entity dimensions.
     */
    public abstract EntityDimensions getDimensions(EntityPose pose);

    /**
     * Makes sure entity is not invisible
     * @return Always true, since not invisible.
     */
    @Override
    public boolean isInvisible() {
        return false;
    }

    /**
     * Allow entity to be collidable.
     * @param other The other entity.
     * @return Always true, since it can be collided with.
     */
    @Override
    public boolean collidesWith(Entity other) {
        return true;
    }

    /**
     * Allows entity to be hit, and therefore be
     * able to interact with.
     * @return Always true, since it can be hit.
     */
    @Override
    public boolean canHit() {
        return true;
    }
}
