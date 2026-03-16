package File.movementum.common;

import File.movementum.client.MovementKeybindings;
import File.movementum.networking.C2S.AirStrideC2SPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.particle.ParticleTypes;
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
            if (client.world == null) return;

            String EnchantString = client.player.getEquippedStack(EquipmentSlot.FEET).getEnchantments().toString();
            UUID id = client.player.getUuid();
            jump.putIfAbsent(id, 0);
            latch.putIfAbsent(id, false);



            if (client.player.isOnGround() && jump.getOrDefault(id, 0) < 30) {
                latch.put(id, true);
            } if (Boolean.TRUE.equals(latch.getOrDefault(id, false)) && jump.getOrDefault(id, 0) >= 30) {
                latch.put(id, false);
            } if (EnchantString.contains("movementum:air_stride") && Boolean.TRUE.equals(latch.getOrDefault(id, false))) {
                jump.put(id, jump.getOrDefault(id, 0) + 1);
            } if (!EnchantString.contains("movementum:air_stride")) {
                jump.put(id, 0);
            }

            if (EnchantString.contains("movementum:air_stride")
                    && !client.player.isOnGround()
                    && MovementKeybindings.AIR != null
                    && MovementKeybindings.AIR.wasPressed()
                    && jump.getOrDefault(id, 0) == 30) {
                   
                Vec3d look = client.player.getRotationVec(1);
                jump.put(id, 0);
                double dx = look.x * 0.75;
                double dz = look.z * 0.75;

                client.player.addVelocity(dx, 0.42, dz);

                ClientPlayNetworking.send(new AirStrideC2SPacket(dx, dz));

                for (int i = 0; i < 13; i++) {
                    client.world.addImportantParticleClient(ParticleTypes.CLOUD, true, client.player.getX(), client.player.getY(), client.player.getZ(), (Math.random()) * -0.25, (Math.random() * -0.07), (Math.random() * -0.25));
                }
            }
        });
    }
}