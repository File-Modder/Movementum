package File.movementum.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static File.movementum.common.DeadMansSprint.hasSprint;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(
            method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/PlayerLikeEntity;tickMovement()V"
            )
    )
    private void deadMansSprint(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        if (!(player instanceof ServerPlayerEntity serverPlayer)) return;

        int foodLevel = serverPlayer.getHungerManager().getFoodLevel();
        boolean deadMansSprint = foodLevel <= 6 && hasSprint(serverPlayer);

        if (deadMansSprint) {
            serverPlayer.setSprinting(serverPlayer.getPlayerInput().sprint());
        }
    }
}

