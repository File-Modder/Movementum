package File.movementum.called;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EquipmentSlot;

public class Atrophy {
    public static void registerAtrophy() {
        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> {
            serverWorld.getPlayers().forEach(player -> {
                if (player == null) return;

                String EnchantString = player.getEquippedStack(EquipmentSlot.FEET).getEnchantments().toString();


                if (EnchantString.contains("movementum:curse_of_movement") && !player.isMovingHorizontally()) {
                    player.damage(serverWorld, serverWorld.getDamageSources().magic(), 1.0F);
                    System.out.println(EnchantString);
                }
            });
        });
    }
}
