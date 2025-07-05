package net.ultra.vehiclemod.vehicles;

import com.mojang.serialization.Codec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.fuel_tank.FuelTank;
import net.ultra.vehiclemod.vehicles.components.entity.seat.Seat;
import net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.trunk.Trunk;
import net.ultra.vehiclemod.vehicles.register.VehicleRegisterer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * An entity that a player can control.
 *
 * Each vehicle has a maximum speed and brake power, which controls how fast the vehicle
 * can go. If the player is in the driver seat, the player can hold the forwards key to
 * accelerate the vehicle forwards. Likewise, the player can also hold the backwards key
 * to accelerate the vehicle backwards.
 *
 * The player in the driver seat can steer the vehicle by holding the left and right movement
 * keys.
 *
 * Each vehicle also has a fuel tank. If the driver seat is occupied, the tank will by default
 * consume fuel.
 */
public abstract class Vehicle extends Entity {
    // Sets the car's tick speed to 20 ticks per second, the default Minecraft tick speed
    private static final double TPS = 20;

    // Vehicle physics properties
    public final double MAX_SPEED; // Max speed in blocks / tick
    protected double speed = 0; // Speed in blocks / tick
    public final double BRAKE_POWER; // Acceleration in blocks / tick^2
    private static final double COASTING_PROPORTIONALITY = 0.2; // coasting slowing down is proportional to brake strength
    protected final double ACCELERATION_TICKS; // how many ticks it takes to accelerate to full speed
    private static final double GRAVITY_ACCELERATION_PER_TICK = -0.08; // this is the default gravity acceleration in Minecraft
    protected static final int DEFAULT_ACCELERATION_LENGTH = 5; // how long it takes for vehicle to reach max speed, in seconds
    private boolean noControl = true; // determines if a player is controlling the vehicle in that tick
    private static final double STEERING_SENSITIVITY = 0.05;
    private Vec3d previousPos = null;

    // Explosion properties
    public final double MAX_EXPLOSION_POWER;
    private static final double EXPLOSION_PROPORTIONALITY = 0.25;

    // Fuel properties
    protected final double FUEL_CONSUMPTION_RATE; // how much ticks it requires to consume one fuel
    protected int fuelTicks = 0;
    protected int fuelParticleTicks = 0;

    protected static final float DAMAGE_RATE = 1.0f; // damage / speed

    // Store vehicle components
    protected final ArrayList<Seat> SEATS = new ArrayList<>();
    protected final ArrayList<UUID> SEATS_UUID = new ArrayList<>();
    protected FuelTank tank = null;
    protected UUID tankUuid = null;
    protected Trunk trunk = null;
    protected UUID trunkUuid = null;

    // Track if car is initialized from saved NBT data
    private boolean fromSavedData = false;

    /**
     * Creates a new vehicle.
     * @param type The entity type of vehicle.
     * @param world The world that the vehicle is in.
     * @param MAX_SPEED Max speed the vehicle can travel forwards, in blocks / second.
     * @param BRAKE_POWER The brake power of the car, in blocks / second ^ 2.
     * @param MAX_EXPLOSION_POWER The max explosion power.
     * @param FUEL_CONSUMPTION_RATE The fuel consumption rate, in items / second.
     */
    protected Vehicle(
        EntityType<? extends Vehicle> type,
        World world,
        double MAX_SPEED,
        double BRAKE_POWER,
        double MAX_EXPLOSION_POWER,
        double FUEL_CONSUMPTION_RATE
    ) {
        super(type, world);

        this.MAX_SPEED = MAX_SPEED / TPS; // blocks per tick
        this.BRAKE_POWER = BRAKE_POWER / Math.pow(TPS, 2); // blocks per tick^2
        this.MAX_EXPLOSION_POWER = MAX_EXPLOSION_POWER;
        this.FUEL_CONSUMPTION_RATE = FUEL_CONSUMPTION_RATE * TPS;
        ACCELERATION_TICKS = DEFAULT_ACCELERATION_LENGTH * TPS;
    }

    /**
     * Creates a new vehicle. Can specify acceleration.
     * @param type The entity type of vehicle.
     * @param world The world that the vehicle is in.
     * @param MAX_SPEED Max speed the vehicle can travel forwards, in blocks / second.
     * @param BRAKE_POWER The brake power of the car, in blocks / second ^ 2.
     * @param MAX_EXPLOSION_POWER The max explosion power.
     * @param ACCELERATION_LENGTH How fast the vehicle accelerates to max speed from rest, in seconds.
     * @param FUEL_CONSUMPTION_RATE The fuel consumption rate, in items / second.
     */
    protected Vehicle(
        EntityType<? extends Vehicle> type,
        World world,
        double MAX_SPEED,
        double BRAKE_POWER,
        double MAX_EXPLOSION_POWER,
        double ACCELERATION_LENGTH,
        double FUEL_CONSUMPTION_RATE
    ) {
        super(type, world);

        this.MAX_SPEED = MAX_SPEED / TPS;
        this.BRAKE_POWER = BRAKE_POWER / Math.pow(TPS, 2);
        this.MAX_EXPLOSION_POWER = MAX_EXPLOSION_POWER;
        this.ACCELERATION_TICKS = ACCELERATION_LENGTH * TPS;
        this.FUEL_CONSUMPTION_RATE = FUEL_CONSUMPTION_RATE * TPS;
    }

    /**
     * This method is only here because this class extends the entity class, and this method is
     * abstract which requires it to be overridden. However, no data tracking is required for
     * this entity, hence it being empty.
     */
    @Override
    protected void initDataTracker(DataTracker.Builder builder) {}

    /**
     * Prevents the car from taking damage.
     * @return Always false.
     */
    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    /**
     * Reloads all child components from NBT data.
     * Minecraft does not save which entities are the vehicle's components,
     * so save data and UUIDs are required to reconnect these entities upon reloading
     * the world.
     * @param readView The NBT data.
     */
    @Override
    protected void readCustomData(ReadView readView) {
        // Register that data comes from save file
        fromSavedData = true;
        SEATS_UUID.clear();
        SEATS.clear();

        // Find all seats
        ReadView.TypedListReadView<Long> seatMostSavedList = readView.getTypedListView("seatMost", Codec.LONG);
        ReadView.TypedListReadView<Long> seatLeastSavedList = readView.getTypedListView("seatLeast", Codec.LONG);

        ArrayList<Long> seatMostList = new ArrayList<>();
        ArrayList<Long> seatLeastList = new ArrayList<>();

        for (long mostUuid: seatMostSavedList) seatMostList.add(mostUuid);
        for (long leastUuid: seatLeastSavedList) seatLeastList.add(leastUuid);

        assert seatMostList.size() == seatLeastList.size();

        int size = seatMostList.size();

        for (int i = 0; i < size; i++) {
            long most = seatMostList.get(i);
            long least = seatLeastList.get(i);

            SEATS_UUID.add(new UUID(most, least));
            SEATS.add(null);
        }

        // Add fuel tank UUID
        Optional<Long> fuelTankMost = readView.getOptionalLong("tankMost");
        Optional<Long> fuelTankLeast = readView.getOptionalLong("tankLeast");

        if (fuelTankMost.isPresent() && fuelTankLeast.isPresent()) {
            tankUuid = new UUID(fuelTankMost.get(), fuelTankLeast.get());
        }

        // Add trunk UUID
        Optional<Long> trunkMost = readView.getOptionalLong("trunkMost");
        Optional<Long> trunkLeast = readView.getOptionalLong("trunkLeast");

        if (trunkMost.isPresent() && trunkLeast.isPresent()) {
            trunkUuid = new UUID(trunkMost.get(), trunkLeast.get());
        }
    }

    /**
     * Writes all child components UUID into data.
     * Minecraft does not save which entities are the vehicle's components,
     * so save data and UUIDs are required to reconnect these entities upon reloading
     * the world.
     * @param writeView The write view.
     */
    @Override
    protected void writeCustomData(WriteView writeView) {
        // Get UUID of all seats and write to NBT

        WriteView.ListAppender<Long> seatMostList = writeView.getListAppender("seatMost", Codec.LONG);
        WriteView.ListAppender<Long> seatLeastList = writeView.getListAppender("seatLeast", Codec.LONG);

        for (Seat seat: SEATS) {
            if (seat != null) {
                UUID uuid = seat.getUuid();
                seatMostList.add(uuid.getMostSignificantBits());
                seatLeastList.add(uuid.getLeastSignificantBits());
            }
        }

        // Write the fuel tank UUID to NBT
        if (tank != null && !tank.isRemoved()) {
            UUID uuid = tank.getUuid();
            writeView.putLong("tankMost", uuid.getMostSignificantBits());
            writeView.putLong("tankLeast", uuid.getLeastSignificantBits());
        }

        // Write the trunk UUID to NBT
        if (trunk != null && !trunk.isRemoved()) {
            UUID uuid = trunk.getUuid();
            writeView.putLong("trunkMost", uuid.getMostSignificantBits());
            writeView.putLong("trunkLeast", uuid.getLeastSignificantBits());
        }
    }


    /** Updates the vehicle and handles any physics. */
    @Override
    public void tick() {
        previousPos = getPos();

        super.tick();

        if (age == 1) {
            // Create new components if no save data
            if (!fromSavedData) {
                createSeats();
                createFuelTank();
                createTrunk();
            }

            loadComponents();
        }

        if (!getWorld().isClient) {
            // Load all components on startup


            // Handle dealing damage to living entities & explosions due to other vehicles
            manageCollidingLivingEntities(getCollidingLivingEntities());
            if (manageCollidingVehicles(getCollidingVehicles())) return;

            noControl = true;
            // Handle player input & consume fuel if someone is in driver seat
            if (
                driverSeatIsOccupied() &&
                SEATS.getFirst().getFirstPassenger() instanceof ServerPlayerEntity player
            ) {
                handlePlayerInput(player);

                // Consume fuel
                fuelTicks++;
                if (fuelTicks >= FUEL_CONSUMPTION_RATE) {
                    fuelTicks = 0;
                    tank.consumeFuel();
                }
            }

            // Explode if in contact with lava
            if (isInLava() && explodesOnLavaContact()) {
                explode((float) MAX_EXPLOSION_POWER);
            }

            // Accelerate to the ground if not on the ground
            if (!isOnGround()) {
                setVelocity(getVelocity().add(0, GRAVITY_ACCELERATION_PER_TICK, 0));
            } else {
                setVelocity(getVelocity().getX(), 0, getVelocity().getZ());
            }

            // If vehicle is not under control, start coasting
            if (noControl) adjustVelocityWhileCoasting();

            // If crashed into >1 block high wall, explode
            if (shouldExplode()) {
                explodeProportionalToSpeed();
                return;
            }

            // Update velocity
            double yaw = Math.toRadians(getYaw());

            setVelocity(new Vec3d(
                -Math.sin(yaw) * speed,
                getVelocity().getY(),
                Math.cos(yaw) * speed
            ));
        }

        if (getWorld().isClient && !getVelocity().equals(Vec3d.ZERO)) {
            handleFuelParticles();
        }

        // Movement must be updated on the server and client
        move(MovementType.SELF, getVelocity());
    }

    /**
     * Handles user input and updates car velocity accordingly.
     * @param player The player entity controlling the car.
     */
    private void handlePlayerInput(ServerPlayerEntity player) {
        PlayerInput input = player.getPlayerInput();

        // The speed change per tick
        double unitAccelerationChange = MAX_SPEED / ACCELERATION_TICKS;

        // Hits brakes
        if (input.jump()) {
            noControl = false;
            // Slow vehicle down according to brake power
            if (speed >= 0) {
                speed = Math.max(speed - BRAKE_POWER, 0);
            } else {
                speed = Math.min(speed + BRAKE_POWER, 0);
            }
        } else if (!tank.isEmpty()) {
            // Accelerate if fwd/bwd keys are hit
            if (input.forward()) {
                noControl = false;
                speed = Math.min(speed + unitAccelerationChange, getCurrentMaxSpeed());
            } else if (input.backward()) {
                noControl = false;
                speed = Math.max(speed - unitAccelerationChange, -getCurrentMaxSpeed() / 3);
            }
        }

        // Change direction if left/right keys are hit
        double yaw = Math.toRadians(getYaw());
        double changeInYaw = (speed == 0) ? 0: STEERING_SENSITIVITY;

        if (input.left()) yaw -= changeInYaw;
        if (input.right()) yaw += changeInYaw;

        setYaw((float) Math.toDegrees(yaw));

        velocityDirty = true;
    }

    /** Slows vehicle down while coasting. */
    private void adjustVelocityWhileCoasting() {
        if (speed >= 0) {
            speed = Math.max(speed - BRAKE_POWER * COASTING_PROPORTIONALITY, 0);
        } else {
            speed = Math.min(speed + BRAKE_POWER * COASTING_PROPORTIONALITY, 0);
        }
    }

    /**
     * Creates the seats for the vehicle.
     * This method should be implemented by subclasses to define how many and where the seats are placed.
     */
    protected abstract void createSeats();

    /** Creates the fuel tank for the vehicle. This method should be implemented by subclasses. */
    protected abstract void createFuelTank();

    /** Creates the fuel trunk for the vehicle. This method should be implemented by subclasses. */
    protected abstract void createTrunk();

    /**
     * Adds a seat to the vehicle at the specified offsets.
     * The first seat added will always be the driver's seat.
     * Subclasses should use this method to add a seat instead of
     * directly creating a seat object to ensure that the seat's NBT
     * data is saved.
     *
     * @param offsetX The offset of the seat in the X direction.
     * @param offsetY The offset of the seat in the Y direction.
     * @param offsetZ The offset of the seat in the Z direction.
     */
    protected void addSeat(double offsetX, double offsetY, double offsetZ) {
        Seat seat = new Seat(
            VehicleRegisterer.SEAT_ENTITY_TYPE,
            this,
            offsetX,
            offsetY,
            offsetZ
        );

        SEATS.add(seat);
        SEATS_UUID.add(seat.getUuid());
    }

    /**
     * Creates the fuel tank for the vehicle, including its UUID.
     * @param offsetX The offset of the fuel tank in the X direction.
     * @param offsetY The offset of the fuel tank in the Y direction.
     * @param offsetZ The offset of the fuel tank in the Z direction.
     * @param items The items that are allowed inside the fuel tank.
     */
    protected void setFuelTank(double offsetX, double offsetY, double offsetZ, Item[] items) {
        tank = new FuelTank(
            VehicleRegisterer.FUEL_TANK_ENTITY_TYPE,
            this,
            offsetX,
            offsetY,
            offsetZ,
            items
        );

        tankUuid = tank.getUuid();
    }

    /**
     * Creates the trunk for the vehicle, including its UUID.
     * @param offsetX The offset of the trunk in the X direction.
     * @param offsetY The offset of the trunk in the Y direction.
     * @param offsetZ The offset of the trunk in the Z direction.
     */
    protected void setTrunk(double offsetX, double offsetY, double offsetZ) {
        trunk = new Trunk(
            VehicleRegisterer.TRUNK_ENTITY_TYPE,
            this,
            offsetX,
            offsetY,
            offsetZ
        );

        trunkUuid = trunk.getUuid();
    }

    /**
     * Removes all entities connected to the vehicle when it despawns.
     * @param reason The reason the vehicle is being removed
     */
    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);

        // Remove seats
        for (Seat seat: SEATS) {
            if (seat != null && !seat.isRemoved()) {
                seat.remove(reason);
            }
        }

        // Remove fuel tank
        if (tank != null && !tank.isRemoved()) {
            tank.remove(reason);
        }

        // Remove trunk
        if (trunk != null && !trunk.isRemoved()) {
            trunk.remove(reason);
        }
    }

    /**
     * Loads all components of vehicle into world.
     * If there is no save data, spawn in new components.
     * If there is save data, reconnect the components.
     */
    private void loadComponents() {
        World world = getWorld();

        // Spawn entities if no save data
        if (!fromSavedData) {
            for (Seat seat: SEATS) world.spawnEntity(seat);
            world.spawnEntity(tank);
            world.spawnEntity(trunk);
        } else {
            SEATS.clear();

            // Reconnect all seats
            for (UUID uuid: SEATS_UUID) {
                for (Seat other: world.getEntitiesByClass(
                    Seat.class,
                    getBoundingBox().expand(16),
                    e -> true
                )) {
                    if (other.getUuid().equals(uuid)) SEATS.add(other);
                }
            }

            // Reconnect fuel tank
            for (FuelTank other: world.getEntitiesByClass(
                FuelTank.class,
                getBoundingBox().expand(16),
                e -> true
            )) {
                if (other.getUuid().equals(tankUuid)) tank = other;
            }

            // Reconnect trunk
            for (Trunk other: world.getEntitiesByClass(
                Trunk.class,
                getBoundingBox().expand(16),
                e -> true
            )) {
                if (other.getUuid().equals(trunkUuid)) trunk = other;
            }
        }
    }

    /**
     * Determines the vehicle's max speed based on its environment (for example, water can slow the vehicle down)
     * @return The max speed the vehicle can go
     */
    public double getCurrentMaxSpeed() {
        if (resistsWater()) return MAX_SPEED;
        return isInFluid() ? MAX_SPEED / 2: MAX_SPEED;
    }

    /**
     * Creates an explosion and deletes the vehicle.
     * @param power The explosion power.
     */
    public void explode(float power) {
        World world = getWorld();

        if (!world.isClient) {
            world.createExplosion(
                this,
                getX(),
                getY(),
                getZ(),
                power,
                World.ExplosionSourceType.TNT
            );

            discard();
        }
    }

    /** Explodes the vehicle proportionally to its speed. */
    public void explodeProportionalToSpeed() {
        explode((float) (Math.abs(speed) / MAX_SPEED * MAX_EXPLOSION_POWER));
    }

    /**
     * Checks if the vehicle is able to climb the blocks in front of it and is going fast enough.
     * @return True if vehicle should explode, false otherwise.
     */
    private boolean shouldExplode() {
        // Don't explode if too slow
        if (Math.abs(speed) <= MAX_SPEED * EXPLOSION_PROPORTIONALITY) return false;

        // Detect collisions in nearby area for future hitbox
        Vec3d direction = getVelocity().normalize().multiply(getExplosionLookForwardDistance());

        Box futureBox = getBoundingBox().offset(direction.getX(), 1.5, direction.getZ());

        for (VoxelShape shape: getWorld().getBlockCollisions(this, futureBox)) {
            if (!shape.isEmpty()) return true;
        }

        return false;
    }

    private void handleFuelParticles() {
        fuelParticleTicks++;

        if (fuelParticleTicks >= getSmokeParticleEmissionRate()) {
            System.out.println("emitting smoke particle jajaja :)");
            fuelParticleTicks = 0;

            if (emitsSmokeParticles()) {
                emitSmokeParticle();
            }
        }
    }

    private void emitSmokeParticle() {
        getWorld().addParticleClient(
            ParticleTypes.CAMPFIRE_COSY_SMOKE,
            getX(),
            getY() + 0.5d,
            getZ(),
            0d,
            0d,
            0d
        );
    }

    /**
     * Determines all living entities colliding with the vehicle.
     * @return List of all entities that are living entities colliding with the entity.
     */
    private List<Entity> getCollidingLivingEntities() {
        return getWorld().getOtherEntities(
            this,
            getBoundingBox(),
            entity -> {
                // Filter out all passengers
                for (Seat seat: SEATS) {
                    if (seat != null && seat.getFirstPassenger() == entity) {
                        return false;
                    }
                }

                return entity instanceof LivingEntity; // filter for living entities
            }
        );
    }

    /**
     * Determines all vehicle entities colliding with the vehicle.
     * @return List of all entities that are vehicles colliding with the entity.
     */
    private List<Entity> getCollidingVehicles() {
        return getWorld().getOtherEntities(
            this,
            getBoundingBox(),
            entity -> entity instanceof Vehicle // filter for vehicles
        );
    }

    /**
     * Deals knockback and damage to all entities given.
     * It is assumed all entities provided are collided with the vehicle.
     * @param entities List of all entities colliding with the vehicle.
     */
    private void manageCollidingLivingEntities(List<Entity> entities) {
        for (Entity entity: entities) {
            if (entity instanceof LivingEntity livingEntity) {
                // Only damage entity if vehicle is beyond a certain speed
                if (Math.abs(speed) > 0.15f) {
                    // Take damage & knockback proportional to speed
                    float damage = (float) (DAMAGE_RATE * (Math.abs(speed) * TPS));
                    Vec3d direction = getVelocity().normalize();

                    /*
                     * Living entity must take the negative of the velocity since
                     * the takeKnockback method calculates the direction vector from the entity
                     * to the damage source, but we want the knockback in the
                     * direction of the velocity vector.
                     */
                    livingEntity.takeKnockback(
                        Math.abs(speed),
                        -direction.getX(),
                        -direction.getZ()
                    );

                    livingEntity.damage(
                        (ServerWorld) getWorld(),
                        getWorld().getDamageSources().outOfWorld(),
                        damage
                    );
                }
            }
        }
    }

    /**
     * Explodes all vehicles the vehicle collides with.
     * It is assumed every vehicle provided is already colliding with this vehicle.
     * @param vehicles The vehicles colliding with this vehicle.
     * @return True if the vehicle explodes, false otherwise.
     */
    private boolean manageCollidingVehicles(List<Entity> vehicles) {
        boolean hasCollided = false;
        for (Entity entity: vehicles) {
            if (entity instanceof Vehicle vehicle) {
                // Get speed of vehicles relative to each other
                double relativeSpeed = getVelocity().subtract(vehicle.getVelocity()).length();

                // Explode both if beyond max speed of either
                if (
                    relativeSpeed > MAX_SPEED * EXPLOSION_PROPORTIONALITY ||
                    relativeSpeed > vehicle.MAX_SPEED * EXPLOSION_PROPORTIONALITY
                ) {
                    vehicle.explodeProportionalToSpeed();
                    hasCollided = true;
                }
            }
        }

        if (hasCollided) explodeProportionalToSpeed();
        return hasCollided;
    }

    /**
     * Determines if there is a player entity on the driver seat.
     * The driver seat is always the first seat added to the list of seats.
     * @return True if the driver seat is occupied, false otherwise.
     */
    public boolean driverSeatIsOccupied() {
        return (
            !SEATS.isEmpty() &&
            SEATS.getFirst() != null &&
            SEATS.getFirst().getFirstPassenger() instanceof PlayerEntity
        );
    }

    /**
     * Vehicles explode if they encounter a wall in front of them larger than 2 blocks.
     * This method returns the distance the vehicle looks forward to check for walls.
     *
     * @return The distance the vehicle looks forward to check for walls.
     */
    protected abstract float getExplosionLookForwardDistance();

    /**
     * Determines if the vehicle should slow down if in contact with water.
     * By default, this value is false. Subclasses should override this if the
     * vehicle should not slow down in water.
     * @return True if resists water, false otherwise.
     */
    public boolean resistsWater() {
        return false;
    }

    /**
     * Determines if the vehicle should explode if in contact with lava.
     * By default, this value is true. Subclasses should override this if the
     * vehicle should not explode in lava.
     * @return True if explodes in lava, false otherwise.
     */
    public boolean explodesOnLavaContact() {
        return true;
    }

    public boolean emitsSmokeParticles() {
        return true;
    }

    public int getSmokeParticleEmissionRate() {
        return 1;
    }

    /**
     * The maximum height the vehicle can drive up. By default, this value is 1 block.
     * @return The max height the vehicle is able to drive up.
     */
    @Override
    public float getStepHeight() {
        return 1.0f;
    }

    public Vec3d getPreviousPos() {
        return previousPos;
    }
}
