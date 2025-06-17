package net.ultra.vehiclemod.vehicles.components.entity.seat;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.components.entity.VehicleComponent;



public class Seat extends VehicleComponent {

    public static final String ENTITY_ID = "vehicle_seat";
    public static final double SEAT_WIDTH = 1d;
    public static final double SEAT_HEIGHT = 1d;

    public Seat(EntityType<?> type, Vehicle parent, double offsetX, double offsetY, double offsetZ) {
        super(type, parent, offsetX, offsetY, offsetZ);
    }

    public Seat(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean shouldDismountUnderwater() {
        return false;
    }

    @Override
    public boolean canAddPassenger(Entity passenger) {
        return !hasPassengers() && (passenger instanceof PlayerEntity);
    }

    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient) {
            if (hasPassengers()) {
                Entity passenger = getFirstPassenger();

                if (passenger instanceof PlayerEntity player && player.isSneaking()) {
                    dismount(player);
                }
            }
        }
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        ActionResult actionResult = super.interact(player, hand);
        if (actionResult != ActionResult.PASS) {
            return actionResult;
        } else {
            return (player.shouldCancelInteraction() || !this.getWorld().isClient && !player.startRiding(this)
                    ? ActionResult.PASS
                    : ActionResult.SUCCESS);
        }
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed((float) SEAT_WIDTH, (float) SEAT_HEIGHT);
    }

    @Override
    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        return getPos();
    }

    /**
     * Dismounts the player from the seat.
     * This method is necessary because Minecraft's default dismount logic prevents players
     * from dismounting when it cannot find a "valid" position to dismount to, which causes bugs
     * especially when the seat is on a vehicle.
     * @param player The player to dismount.
     */
    public void dismount(PlayerEntity player) {
        if (player.getVehicle() == this && !getWorld().isClient) {
            Vec3d dismountPosition = getPos();

            player.stopRiding();
            player.teleport(
                dismountPosition.getX(),
                dismountPosition.getY(),
                dismountPosition.getZ(),
                false
            );

        }
    }
}
