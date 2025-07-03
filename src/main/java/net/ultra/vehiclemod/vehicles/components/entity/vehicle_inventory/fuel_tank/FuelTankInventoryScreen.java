package net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.fuel_tank;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.core.VehicleMod;

/** Inventory screen for the fuel tank. */
public class FuelTankInventoryScreen extends HandledScreen<FuelTankScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(
        VehicleMod.MOD_ID,
        "textures/gui/5x1.png"
    );

    /**
     * Creates a new FuelTankInventoryScreen.
     * @param handler The fuel tank screen handler.
     * @param inventory The player inventory to display.
     * @param title This title is not used, as the title is set to "Fuel Tank" in the constructor.
     *              It is only here to match the constructor signature of HandledScreen.
     */
    public FuelTankInventoryScreen(
        FuelTankScreenHandler handler,
        PlayerInventory inventory,
        Text title
    ) {
        super(handler, inventory, Text.of("Fuel Tank"));
        // Set the dimensions of the screen in pixels.
        backgroundWidth = 176;
        backgroundHeight = 133;

        // Set the "player inventory" title to a specific height, in pixels
        playerInventoryTitleY = backgroundHeight - 94;
    }

    /**
     * Draws the GUI.
     * @param context The drawing context for the GUI
     * @param deltaTicks The partial tick time used for interpolation.
     *                   This field is not used.
     * @param mouseX The mouse position x coordinate, in pixels (which is not used)
     * @param mouseY The mouse position y coordinate, in pixels (which is not used)
     */
    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        // Draw background
        context.drawTexture(
            RenderPipelines.GUI_TEXTURED,
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
