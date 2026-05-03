package File.movementum.common;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;

public class MiscEvents {
    public static void initMiscEvents() {
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof PlayerEntity player) {
                if (source.isOf(DamageTypes.FALL) && AirStride.cancelFD.getOrDefault(player.getUuid(), false)) {
                    return false;
                }
            }
            return true;
        });
    }
}
