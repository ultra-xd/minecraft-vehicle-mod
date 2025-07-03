package net.ultra.vehiclemod.vehicles.vehicle_types.tesla.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

/** A Tesla Vehicle. It only accepts redstone as fuel, not coal. */
public class Tesla extends Vehicle {
    public static final String ITEM_ID = "tesla_spawn";
    public static final String ENTITY_ID = "tesla";
    public static final float VEHICLE_HITBOX_WIDTH = 4.0f;
    public static final float VEHICLE_HITBOX_HEIGHT = 3.0f;

    /**
     * Initializes a Tesla entity.
     * @param type The entity type of vehicle (Tesla).
     * @param world The world that this vehicle is in.
     */
    public Tesla(EntityType<? extends Vehicle> type, World world) {
        super(
            type,
            world,
            30,
            10,
            30,
            5
        );
    }

    @Override
    protected void createSeats() {
        addSeat(0.5, 0.5, 1);
        addSeat(-0.5, 0.5, 1);
        addSeat(-0.5, 0.5, -0.5);
        addSeat(0.5, 0.5, -0.5);
    }

    @Override
    protected void createFuelTank() {
        setFuelTank(-1.5, 0.5, -1.5, new Item[] {Items.REDSTONE});
    }

    @Override
    protected void createTrunk() {
        setTrunk(0, 0.5, -2);
    }

    @Override
    protected float getExplosionLookForwardDistance() {
        return 1.2f;
    }
}
