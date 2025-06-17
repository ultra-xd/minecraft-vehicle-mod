package net.ultra.vehiclemod.vehicles.components.entity.fuel_tank;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.VehicleMod;

public class FuelTankInventoryScreen extends HandledScreen<FuelTankScreenHandler> {

    private static final Identifier TEXTURE = Identifier.of(
        VehicleMod.MOD_ID,
        "textures/gui/5x1.png"
    );

    /**
     * Creates a new FuelTankInventoryScreen.
     * @param handler
     * @param inventory
     * @param title This title is not used, as the title is set to "Fuel Tank" in the constructor.
     *              It is only here to match the constructor signature of HandledScreen.
     */
    public FuelTankInventoryScreen(
        FuelTankScreenHandler handler,
        PlayerInventory inventory,
        Text title
    ) {
        super(handler, inventory, Text.of("Fuel Tank"));
        backgroundWidth = 176;
        backgroundHeight = 133;

        playerInventoryTitleY = backgroundHeight - 94;
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
