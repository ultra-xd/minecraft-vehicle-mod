package net.ultra.vehiclemod.vehicles.components.entity.trunk.custom;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;
import net.ultra.vehiclemod.vehicles.components.entity.fuel_tank.custom.FuelTankScreenHandler;

public class TrunkInventoryScreen extends HandledScreen<TrunkScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(
            VehicleMod.MOD_ID,
            "textures/gui/9x3.png"
    );

    /**
     * Creates a new FuelTankInventoryScreen.
     * @param handler
     * @param inventory
     * @param title This title is not used, as the title is set to "Fuel Tank" in the constructor.
     *              It is only here to match the constructor signature of HandledScreen.
     */
    public TrunkInventoryScreen(
            TrunkScreenHandler handler,
            PlayerInventory inventory,
            Text title
    ) {
        super(handler, inventory, Text.of("Trunk"));
        this.backgroundHeight++;
    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        context.drawTexture(
                RenderLayer::getGuiTextured,
                TEXTURE,
                x,
                y,
                0,
                0,
                backgroundWidth,
                backgroundHeight,
                256,
                256
        );
    }
}
