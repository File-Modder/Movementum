package File.movementum.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;

public class DeadMansSprint {
    private static int ticks = 0;
    public static void registerDeadMansSprint() {

        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> {

            serverWorld.getPlayers().forEach(player -> {
                ticks ++;
                if (player == null) return;

                if (hasSprint(player) && player.isSprinting() && player.getHungerManager().getFoodLevel() <= 6 && ticks % 30 == 0) {
                    player.damage(serverWorld, serverWorld.getDamageSources().magic(), 1.0F);


                }
            });
        });
    }

    public static boolean hasSprint(PlayerEntity player) {
        return player.getEquippedStack(EquipmentSlot.LEGS)
                .getEnchantments()
                .toString()
                .contains("movementum:dead_mans_sprint");
    }
}


