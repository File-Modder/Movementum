package File.movementum.called;

import File.movementum.client.MovementKeybindings;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AirStride {
    private static final Map<UUID, Integer> jump = new HashMap<>();

    public static void registerStride() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            String EnchantString = client.player.getEquippedStack(EquipmentSlot.FEET).getEnchantments().toString();
            UUID id = client.player.getUuid();
            jump.putIfAbsent(id, 0);

            client.player.sendMessage(Text.literal(String.valueOf(jump.get(id))), true);

            if (client.player.isOnGround() && EnchantString.contains("Air Strider") && jump.get(id) < 30) {
                jump.put(id, jump.get(id) + 1);
            }

            if (EnchantString.contains("Air Strider")
                    && !client.player.isOnGround()
                    && MovementKeybindings.AIR.wasPressed()
                    && jump.get(id) == 30) {
                Vec3d look = client.player.getRotationVec(1);
                jump.put(id, 0);
                client.player.addVelocity(look.x * 0.75, 0.42, look.z * 0.75);
            }
        });
    }
}