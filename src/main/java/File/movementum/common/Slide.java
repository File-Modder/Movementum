package File.movementum.common;

import File.movementum.client.MovementKeybindings;
import File.movementum.networking.C2S.SlideVelocityC2SPacket;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static File.movementum.animations.AnimationDefiner.SLIDING;
import static File.movementum.common.AirStride.jump;
import static com.zigythebird.playeranim.PlayerAnimLibMod.ANIMATION_LAYER_ID;

public class Slide {

    private static final Map<UUID, Integer> stamina = new HashMap<>();
    private static final Map<UUID, Integer> speed = new HashMap<>();
    private static final Map<UUID, Integer> staminaMax = new HashMap<>();
    private static final Map<UUID, Integer> regenSpeed = new HashMap<>();

    public static boolean isSliding = false;
    public static boolean isStanding = false;

    public static void registerSlide() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) return;
            if (client.world == null) return;
            if (client.getServer() != null) return;


            client.getServer().getPlayerManager().getPlayerList().forEach(player -> {
                stamina.putIfAbsent(player.getUuid(), 2000);
                staminaMax.putIfAbsent(player.getUuid(), 2000);
                speed.putIfAbsent(player.getUuid(), 0);
                regenSpeed.putIfAbsent(player.getUuid(), 10);



            String EnchantString = client.player.getEquippedStack(EquipmentSlot.FEET).getEnchantments().toString();
                UUID id = client.player.getUuid();
                client.player.sendMessage(Text.literal(stamina.get(id) + " stamina, Air Stride " + jump.get(id)), true);
                stamina.putIfAbsent(id, 2000);
                staminaMax.putIfAbsent(id, 2000);



                boolean isDescending = client.player.getVelocity().y < -0.08;

                if (MovementKeybindings.SLIDE.isPressed()
                    && (player.isOnGround() || isDescending)
                    && player.isSprinting()
                    && !player.isSwimming()
                    && !player.isSneaking()
                    && !player.isClimbing()
                    && !player.isInFluid()
                    && !player.isSleeping()
                    && !player.isJumping()) {
                    isSliding = true;
                } else {
                    isSliding = false;
                }

                if (client.player.isOnGround()
                    && !isSliding
                    && !player.isSprinting()
                    && !player.isMovingHorizontally()
                    && !player.isSwimming()
                    && !player.isSneaking()
                    && !player.isClimbing()
                    && !player.isInFluid()
                    && !player.isJumping()) {
                    isStanding = true;
                } else {
                    isStanding = false;
                }


                    PlayerAnimationController controller =
                        (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(
                            player,
                            ANIMATION_LAYER_ID
                        );

                    if (controller != null) {
                        if (isSliding) {
                            controller.replaceAnimationWithFade(
                                AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_BACK),
                                SLIDING
                            );
                        } else {
                            controller.stopTriggeredAnimation();
                        }
                    }


                     if (isSliding && stamina.get(id) > 0) {
                        stamina.put(id, stamina.get(id) - 10);
                    }
                     else if (isSliding && !isStanding && stamina.get(id) < staminaMax.get(id) && stamina.get(id) >= 0) {
                        stamina.put(id, stamina.get(id) + regenSpeed.get(id)) ;
                    }
                     else if (!isSliding && isStanding && stamina.get(id) < staminaMax.get(id) && stamina.get(id) >= 0) {
                        stamina.put(id, stamina.get(id) + regenSpeed.get(id) += 15);
                    }

                     if (isSliding) {
                         if (stamina.get(id) > staminaMax.get(id)) {
                             stamina.put(id, staminaMax.get(id));
                         }
                         if (stamina.get(id) >= staminaMax.get(id)*0.875) {
                             speed.put(id, 75);
                         }
                         else if (stamina.get(id) >= staminaMax.get(id)*0.75) {
                             speed.put(id, 65);
                         }
                         else if (stamina.get(id) >= staminaMax.get(id)*0.625) {
                             speed.put(id, 55);
                         }
                         else if (stamina.get(id) >= staminaMax.get(id)*0.5) {
                             speed.put(id, 45);
                         }
                         else if (stamina.get(id) >= staminaMax.get(id)*0.375) {
                             speed.put(id, 35);
                         }

                         else if (stamina.get(id) >= staminaMax.get(id)*0.25) {
                             speed.put(id, 25);
                         }
                         else if (stamina.get(id) >= staminaMax.get(id)*0.25) {
                             speed.put(id, 25);
                         }
                         else if (stamina.get(id) >= staminaMax.get(id)*0.125) {
                             speed.put(id, -25);
                         }
                         else if (stamina.get(id) >= staminaMax.get(id)*0.125) {
                             speed.put(id, -45);
                         }




                     }
                if (isSliding) {
                    Vec3d look = player.getRotationVec(1);
                    Vec3d vel = player.getVelocity
                    int s = speed.getOrDefault(id, 0);
                    double dx = look.x * s / 1000.0;
                    double dz = look.z * s / 1000.0;

                    if (!vel <= 0) :
                    player.addVelocity(dx, 0, dz);
                    }

                    ClientPlayNetworking.send(new SlideVelocityC2SPacket(dx, dz));
                }
            });
        });
    }

    public static boolean getSliding() {
        return isSliding;
    }

    public static boolean getStanding() {
        return isStanding;
    }
}
