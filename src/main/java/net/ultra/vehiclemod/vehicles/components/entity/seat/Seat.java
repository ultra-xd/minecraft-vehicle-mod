package net.ultra.vehiclemod.vehicles.components.entity.seat;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;
import net.ultra.vehiclemod.vehicles.components.entity.VehicleComponent;

/** A vehicle seat. */
public class Seat extends VehicleComponent {

    public static final String ENTITY_ID = "vehicle_seat";
    public static final double SEAT_WIDTH = 1d;
    public static final double SEAT_HEIGHT = 1d;

    private static final TrackedData<Boolean> HAS_PASSENGER = DataTracker.registerData(
        Seat.class,
        TrackedDataHandlerRegistry.BOOLEAN
    );

    private static boolean perspectiveChangeRegistered = false;

    /**
     * Initializes a vehicle seat.
     * @param type The type of the entity
     * @param parent The vehicle which the entity belongs to.
     * @param offsetX The offset of the entity in the X direction.
     * @param offsetY The offset of the entity in the Y direction.
     * @param offsetZ The offset of the entity in the Z direction.
     */
    public Seat(EntityType<?> type, Vehicle parent, double offsetX, double offsetY, double offsetZ) {
        super(type, parent, offsetX, offsetY, offsetZ);
    }

    /** Constructor used to register a seat. */
    public Seat(EntityType<?> type, World world) {
        super(type, world);
    }

    /** Seats don't require a data tracker so no data tracker builder required. */
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(HAS_PASSENGER, false);
    }

    /**
     * Ensure that entity does not take damage
     * @param world The world the entity is in.
     * @param source The source of the damage.
     * @param amount The amount of damage taken.
     * @return Always false, since the entity cannot be damaged.
     */
    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    /**
     * Ensures the passenger doesn't dismount the entity underwater.
     * @return False, since passenger doesn't dismount underwater.
     */
    @Override
    public boolean shouldDismountUnderwater() {
        return false;
    }

    /**
     * Ensures only one passenger of type player entity can ride the seat.
     * @param passenger The passenger attempting toride the seat.
     * @return True if can ride, false otherwise.
     */
    @Override
    public boolean canAddPassenger(Entity passenger) {
        return !hasPassengers() && (passenger instanceof PlayerEntity);
    }

    /** Updates the seat/*/
    @Override
    public void tick() {
        super.tick();

        if (!getWorld().isClient) {
            // Kick player out if sneaking
            if (hasPassengers()) {
                Entity passenger = getFirstPassenger();

                if (passenger instanceof PlayerEntity player && player.isSneaking()) {
                    dismount(player);
                }
            }
        }
    }

    /**
     * Allows player to potentially ride the seat if interacted with
     * @param player the player
     * @param hand the hand the player used to interact with this entity
     * @return ActionResult.SUCCESS if should move hand, PASS otherwise.
     */
    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        ActionResult actionResult = super.interact(player, hand);
        if (actionResult != ActionResult.PASS) {
            return actionResult;
        } else {
            // Move hand if can ride, stationary otherwise.
            return (player.shouldCancelInteraction() || !this.getWorld().isClient && !player.startRiding(this)
                    ? ActionResult.PASS
                    : ActionResult.SUCCESS);
        }
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);

        dataTracker.set(HAS_PASSENGER, true);

        passenger.setInvisible(true);
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);

        dataTracker.set(HAS_PASSENGER, false);

        passenger.setInvisible(false);
    }

    /**
     * Gets the hitbox dimensions of the entity.
     * @param pose The pose of the entity, which does not matter since
     *             this entity only has one pose (the default pose).
     * @return The hitbox dimensions of the entity.
     */
    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed((float) SEAT_WIDTH, (float) SEAT_HEIGHT);
    }

    /**
     * Handles the position at which the passenger dismounts at.
     * @param passenger The passenger dismounting.
     * @return The position of the seat, since the passenger will
     * have to dismount at the seat.
     */
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

            // Dismount player and move to dismount position.
            player.stopRiding();
            player.teleport(
                dismountPosition.getX(),
                dismountPosition.getY(),
                dismountPosition.getZ(),
                false
            );
        }
    }

    public static void registerPerspectiveChange() {
        if (perspectiveChangeRegistered) {
            throw new Error("Perspective already changed");
        }

        perspectiveChangeRegistered = true;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.player.hasVehicle()) {
                Entity riding = client.player.getVehicle();

                if (riding instanceof Seat) {
                    if (client.options.getPerspective() == Perspective.THIRD_PERSON_BACK) return;

                    client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                }
            }
        });
    }
}
