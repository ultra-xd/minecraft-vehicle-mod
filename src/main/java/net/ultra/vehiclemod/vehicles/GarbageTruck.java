package net.ultra.vehiclemod.vehicles;

public class GarbageTruck extends Vehicle {
    protected static String ID = "garbage_spawn";

    public static void registerItem() {
        Vehicle.registerItem(GarbageTruck.ID);
    }
}
