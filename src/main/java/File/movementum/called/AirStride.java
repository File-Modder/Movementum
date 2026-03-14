package File.movementum.called;

import File.movementum.client.MovementKeybindings;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AirStride {

    private static final Map<UUID, Boolean> latch = new HashMap<>();
    public static final Map<UUID, Integer> jump = new HashMap<>();

    public static void registerStride() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            String EnchantString = client.player.getEquippedStack(EquipmentSlot.FEET).getEnchantments().toString();
            UUID id = client.player.getUuid();
            jump.putIfAbsent(id, 0);
            latch.putIfAbsent(id, false);



            if (client.player.isOnGround() && jump.get(id) < 30) {
                latch.put(id, true);
            } if (Boolean.TRUE.equals(latch.get(id)) && jump.get(id) >= 30) {
                latch.put(id, false);
            } if (EnchantString.contains("Air Strider") && Boolean.TRUE.equals(latch.get(id))) {
                jump.put(id, jump.get(id) + 1);
            }


            if (EnchantString.contains("Air Strider")
                    && !client.player.isOnGround()
                    && MovementKeybindings.AIR != null
                    && MovementKeybindings.AIR.wasPressed()
                    && jump.get(id) == 30) {
                Vec3d look = client.player.getRotationVec(1);
                jump.put(id, 0);
                client.player.addVelocity(look.x * 0.75, 0.42, look.z * 0.75);
            }
        });
    }
}