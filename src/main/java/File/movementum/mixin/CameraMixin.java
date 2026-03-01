package File.movementum.mixin;

import File.movementum.client.MovementKeybindings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {

    @Unique private double smoothX = Double.NaN;
    @Unique private double smoothY = Double.NaN;
    @Unique private double smoothZ = Double.NaN;

    @Inject(method = "update", at = @At("TAIL"))
    private void cameraOverride(CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Get player's proper interpolated eye position
        Vec3d eyePos = client.player.getCameraPosVec(1.0f);

        double slideOffset = -0.6; // how much lower camera goes
        double targetX = eyePos.x;
        double targetY = eyePos.y;
        double targetZ = eyePos.z;

        if (MovementKeybindings.SLIDE.isPressed()) {
            if (client.player.isOnGround()) {
                if (client.player.isSprinting()) {
                    targetY += slideOffset;
                }
            }
        }

        // Initialize once
        if (Double.isNaN(smoothX)) {
            smoothX = targetX;
            smoothY = targetY;
            smoothZ = targetZ;
        }

        // Smooth all axes (lower = smoother)
        double smoothing = 0.15;

        smoothX += (targetX - smoothX) * smoothing;
        smoothY += (targetY - smoothY) * smoothing;
        smoothZ += (targetZ - smoothZ) * smoothing;

        ((CameraAccessor) (Object) this).callSetPos(
                smoothX,
                smoothY,
                smoothZ
        );
    }
}