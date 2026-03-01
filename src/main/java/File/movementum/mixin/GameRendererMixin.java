package File.movementum.mixin;

import File.movementum.client.MovementKeybindings;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void disableBob(MatrixStack matrices, float tickProgress, CallbackInfo ci) {

        // Disable bobbing while sliding
        if (MovementKeybindings.SLIDE.isPressed()) {
            ci.cancel();
        }
    }
}