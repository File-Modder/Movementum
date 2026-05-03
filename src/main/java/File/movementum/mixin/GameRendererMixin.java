package File.movementum.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

import static File.movementum.common.Slide.isSliding;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void disableBob(MatrixStack matrices, float tickProgress, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player == null) return;
        UUID id = MinecraftClient.getInstance().player.getUuid();

        if (isSliding.getOrDefault(id, false)) {
            ci.cancel();
        }
    }
}