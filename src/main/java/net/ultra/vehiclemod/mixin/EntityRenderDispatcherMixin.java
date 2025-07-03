package net.ultra.vehiclemod.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.ultra.vehiclemod.vehicles.components.entity.seat.Seat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Inject(method = "render*", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void onRender(
        E entity,
        double x,
        double y,
        double z,
        float tickProgress,
        MatrixStack matrices,
        VertexConsumerProvider vertexConsumers,
        int light,
        CallbackInfo callbackInfo
    ) {
        if (
            entity instanceof PlayerEntity player &&
            player.hasVehicle() &&
            player.getVehicle() instanceof Seat
        ) {
            callbackInfo.cancel();
        }
    }

}
