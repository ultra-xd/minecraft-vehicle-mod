package net.ultra.vehiclemod.vehicles.components.entity.vehicle_inventory.trunk;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ultra.vehiclemod.core.VehicleMod;

/** Inventory screen for the Trunk. */
public class TrunkInventoryScreen extends HandledScreen<TrunkScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(
            VehicleMod.MOD_ID,
            "textures/gui/9x3.png"
    );

    /**
     * Creates a new TrunkInventoryScreen.
     * @param handler The trunk inventory screen handler.
     * @param inventory The player inventory to display.
     * @param title This title is not used, as the title is set to "Trunk" in the constructor.
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

    /**
     * Draws the GUI.
     * @param context The drawing context for the GUI.
     * @param deltaTicks The partial tick time used for interpolation.
     *                   This field is not used.
     * @param mouseX The mouse position x coordinate, in pixels (which is not used)
     * @param mouseY The mouse position y coordinate, in pixels (which is not used)
     */
    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
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
