package net.ultra.vehiclemod.vehicles;

public class HondaCivic extends Vehicle {
    protected static String ID = "civic_spawn";

    public static void registerItem() {
        Vehicle.registerItem(HondaCivic.ID);
    }
}
