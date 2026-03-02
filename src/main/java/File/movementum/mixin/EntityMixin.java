package File.movementum.mixin;

import File.movementum.client.MovementKeybindings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    private Box boundingBox;

    @Inject(method = "getBoundingBox", at = @At("HEAD"), cancellable = true)
    private void modifyBoundingBoxDuringSlide(CallbackInfoReturnable<Box> cir) {
        Entity entity = (Entity) (Object) this;
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player != null
                && entity == client.player
                && MovementKeybindings.SLIDE.isPressed()
                && entity.isOnGround()) {

            Box originalBox = boundingBox;

            if (originalBox != null) {
                double height = originalBox.maxY - originalBox.minY;
                double newHeight = height * 0.5;

                Box slidingBox = new Box(
                        originalBox.minX,
                        originalBox.minY,
                        originalBox.minZ,
                        originalBox.maxX,
                        originalBox.minY + newHeight,
                        originalBox.maxZ
                );

                cir.setReturnValue(slidingBox);
            }
        }
    }
}