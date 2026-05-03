package File.movementum.common;

import File.movementum.client.MovementKeybindings;
import File.movementum.networking.C2S.AirStrideC2SPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AirStride {

    public static final Map<UUID, Boolean> strideLatch = new HashMap<>();
    public static final Map<UUID, Boolean> jumpLatch = new HashMap<>();
    public static final Map<UUID, Boolean> cancelFD = new HashMap<>();
    public static final Map<UUID, Integer> stride = new HashMap<>();
    public static final Map<UUID, Integer> jump = new HashMap<>();
    public static final Map<UUID, Integer> groundTicks = new HashMap<>();

    public static void registerStride() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (client.world == null) return;

            String EnchantString = client.player.getEquippedStack(EquipmentSlot.FEET).getEnchantments().toString();

            UUID id = client.player.getUuid();
            stride.putIfAbsent(id, 0);
            jump.putIfAbsent(id, 0);
            groundTicks.putIfAbsent(id, 0);
            strideLatch.putIfAbsent(id, false);
            jumpLatch.putIfAbsent(id, false);
            cancelFD.putIfAbsent(id, false);


            if (client.player.isOnGround() && stride.getOrDefault(id, 0) < 30) {
                strideLatch.put(id, true);
            }
            if (client.player.isOnGround() && jump.getOrDefault(id, 0) < 30) {
                jumpLatch.put(id, true);
            }

            if (Boolean.TRUE.equals(strideLatch.getOrDefault(id, false)) && stride.get(id) >= 30) {
                strideLatch.put(id, false);
            }
            if (Boolean.TRUE.equals(jumpLatch.getOrDefault(id, false)) && jump.get(id) >= 30) {
                jumpLatch.put(id, false);
            }

            if (EnchantString.contains("movementum:air_stride") && Boolean.TRUE.equals(strideLatch.getOrDefault(id, false))) {
                stride.put(id, stride.getOrDefault(id, 0) + 1);
            }
            if (EnchantString.contains("movementum:double_jump") && Boolean.TRUE.equals(jumpLatch.getOrDefault(id, false))) {
                jump.put(id, jump.getOrDefault(id, 0) + 1);
            }


            if (!(EnchantString.contains("movementum:air_stride"))) {
                stride.put(id, 0);
            }
            if (!(EnchantString.contains("movementum:double_jump"))) {
                jump.put(id, 0);
            }

            if (EnchantString.contains("movementum:air_stride") || EnchantString.contains("movementum:double_jump")) {
                if (client.player.isOnGround() && groundTicks.getOrDefault(id, 0) < 10) {
                    groundTicks.put(id, groundTicks.get(id) + 1);
                }
                if (!client.player.isOnGround() && groundTicks.getOrDefault(id, 0) > 0) {
                    groundTicks.put(id, 0);
                }


                if ((MovementKeybindings.AIR != null && MovementKeybindings.AIR.isPressed() || MovementKeybindings.JUMP != null && MovementKeybindings.JUMP.isPressed()) && Boolean.FALSE.equals(cancelFD.get(id))) {
                    cancelFD.put(id, true);
                }
                else if (client.player.isOnGround() && groundTicks.getOrDefault(id, 0) >= 10 && Boolean.TRUE.equals(cancelFD.get(id))) {

                    cancelFD.put(id, false);
                }
            }


                if (EnchantString.contains("movementum:air_stride")
                        && !client.player.isOnGround()
                        && MovementKeybindings.AIR != null
                        && MovementKeybindings.AIR.isPressed()
                        && stride.getOrDefault(id, 0) == 30) {

                    Vec3d look = client.player.getRotationVec(1);

                    if (Slide.stamina.getOrDefault(id, 2000) < 250) return;

                    stride.put(id, 0);
                    Slide.stamina.put(id, Slide.stamina.getOrDefault(id, 2000) - 250);


                    double dx = look.x * 0.95;
                    double dy = look.y * .42;
                    double dz = look.z * 0.95;

                    client.player.addVelocity(dx, dy, dz);
                    client.player.velocityDirty = true;

                    ClientPlayNetworking.send(new AirStrideC2SPacket(dx, dy, dz));
                }
                else if (EnchantString.contains("movementum:double_jump")
                        && MovementKeybindings.JUMP != null
                        && MovementKeybindings.JUMP.isPressed()
                        && jump.getOrDefault(id, 0) == 30) {
                    Vec3d vel = client.player.getVelocity();

                    if (Slide.stamina.getOrDefault(id, 2000) < 250) return;

                    jump.put(id, 0);
                        Slide.stamina.put(id, Slide.stamina.getOrDefault(id, 2000) - 250);

                    double dx = 0;
                    double dy = -vel.y + 1;
                    double dz = 0;

                    client.player.addVelocity(dx, dy, dz);
                    client.player.velocityDirty = true;

                    
                    ClientPlayNetworking.send(new AirStrideC2SPacket(dx, dy, dz));
                }

        });
    }
}
