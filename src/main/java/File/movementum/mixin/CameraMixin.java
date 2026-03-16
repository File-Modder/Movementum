package File.movementum.mixin;

import File.movementum.common.Slide;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow
    public abstract boolean isThirdPerson();


    @Unique private double smoothX = Double.NaN;
    @Unique private double smoothY = Double.NaN;
    @Unique private double smoothZ = Double.NaN;

    @Inject(method = "update", at = @At("TAIL"))
    private void cameraOverride(CallbackInfo ci) {

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            smoothX = Double.NaN;
            smoothY = Double.NaN;
            smoothZ = Double.NaN;
            return;
        }

        if (isThirdPerson() || client.player.isRiding()) {
            smoothX = Double.NaN;
            smoothY = Double.NaN;
            smoothZ = Double.NaN;
            return;
        }

        float tickDelta = client.getRenderTickCounter().getTickProgress(true);
        Vec3d basePos = client.player.getLerpedPos(tickDelta);

        double targetX = basePos.x;
        double targetY = basePos.y + client.player.getEyeHeight(client.player.getPose());
        double targetZ = basePos.z;

        if (Slide.isSliding) {
            targetY -= 0.3;
        }

        if (Double.isNaN(smoothX)) {
            smoothX = targetX;
            smoothY = targetY;
            smoothZ = targetZ;
        }

        double smoothing = 0.15;

        smoothX += (targetX - smoothX) * smoothing;
        smoothY += (targetY - smoothY) * smoothing;
        smoothZ += (targetZ - smoothZ) * smoothing;

        ((CameraAccessor) this).callSetPos(smoothX, smoothY, smoothZ);
    }
}