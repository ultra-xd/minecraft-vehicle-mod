package net.ultra.vehiclemod.vehicles;

public class Bugatti extends Vehicle{
    protected static String ID = "bugatti_spawn";

    public static void registerItem() {
        Vehicle.registerItem(Bugatti.ID);
    }
}
