package net.ultra.vehiclemod.vehicles.vehicle_types.rav4.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import net.ultra.vehiclemod.vehicles.Vehicle;

/** A Rav 4 Vehicle. It is hybrid, so it accepts redstone and coal as fuel. */
public class Rav4 extends Vehicle {
    public static final String ITEM_ID = "rav4_spawn";
    public static final String ENTITY_ID = "rav4";
    public static final float VEHICLE_HITBOX_WIDTH = 4.0f;
    public static final float VEHICLE_HITBOX_HEIGHT = 4.0f;

    /**
     * Initializes a Rav 4 entity.
     * @param type The entity type of vehicle (Rav 4).
     * @param world The world that this vehicle is in.
     */
    public Rav4(EntityType<? extends Vehicle> type, World world) {
        super(
            type,
            world,
            27,
            7,
            50,
            10
        );
    }

    @Override
    protected void createSeats() {
        addSeat(-0.5, 0.5, 0.5);
        addSeat(0.5, 0.5, 0.5);
        addSeat(-0.5, 0.5, -0.5);
        addSeat(0.5, 0.5, -0.5);
    }

    @Override
    protected void createFuelTank() {
        setFuelTank(-1.5, 0.5, -1.5, new Item[] {Items.COAL, Items.REDSTONE});
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
