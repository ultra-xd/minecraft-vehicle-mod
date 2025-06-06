package net.ultra.vehiclemod.vehicles;

public class Tesla extends Vehicle {
    protected static String ID = "tesla_spawn";

    public static void registerItem() {
        Vehicle.registerItem(Tesla.ID);
    }
}
