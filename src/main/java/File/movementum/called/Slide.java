package File.movementum.called;

import File.movementum.client.MovementKeybindings;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static File.movementum.animations.AnimationDefiner.SLIDING;
import static File.movementum.called.AirStride.jump;
import static com.zigythebird.playeranim.PlayerAnimLibMod.ANIMATION_LAYER_ID;

public class Slide {

    private static final Map<UUID, Integer> stamina = new HashMap<>();
    private static final Map<UUID, Integer> speed = new HashMap<>();

    public static boolean isSliding = false;
    public static boolean isStanding = false;

    public static void registerSlide() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                UUID id = client.player.getUuid();
                client.player.sendMessage(Text.literal(stamina.get(id) + " stamina, Air Stride " + jump.get(id)), true);
                stamina.putIfAbsent(id, 2000);

                boolean isDescending = client.player.getVelocity().y < -0.08;

                if (MovementKeybindings.SLIDE.isPressed()
                    && (client.player.isOnGround() || isDescending)
                    && client.player.isSprinting()
                    && !client.player.isSwimming()
                    && !client.player.isSneaking()
                    && !client.player.isClimbing()
                    && !client.player.isRiding()
                    && !client.player.isInFluid()
                    && !client.player.isSleeping()
                    && !client.player.isJumping()) {
                    isSliding = true;
                } else {
                    isSliding = false;
                }

                if (client.player.isOnGround()
                    && !isSliding
                    && !client.player.isSprinting()
                    && !client.player.isMovingHorizontally()
                    && !client.player.isSwimming()
                    && !client.player.isSneaking()
                    && !client.player.isClimbing()
                    && !client.player.isRiding()
                    && !client.player.isInFluid()
                    && !client.player.isJumping()) {
                    isStanding = true;
                } else {
                    isStanding = false;
                }

                if (client.player != null) {
                    PlayerAnimationController controller =
                        (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(
                            client.player,
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

                    int i = stamina.get(id);

                    if (isSliding && !isStanding && i > 0) {
                        i -= 10;
                    }

                    if (i > 2000) {
                        i = 2000;
                    } else if (!isSliding && isStanding && i < 2000 && i >= 0) {
                        i += 25;
                    } else if (!isSliding && i < 2000 && i >= 0) {
                        i += 12;
                    } else if (i < 0 && !isSliding) {
                        i = 0;
                    }

                    if (isSliding) {

                        Vec3d vel = client.player.getVelocity();

                        if (i >= 1750) {
                            speed.put(id, 75);
                        } else if (i >= 1500) {
                            speed.put(id, 65);
                        } else if (i >= 1250) {
                            speed.put(id, 55);
                        } else if (i >= 1000) {
                            speed.put(id, 45);
                        } else if (i >= 750) {
                            speed.put(id, 35);
                        } else if (i >= 500) {
                            speed.put(id, 0);
                        } else if (i >= 250) {
                            client.player.setVelocity(
                                vel.x / 1.25,
                                vel.y,
                                vel.z / 2
                            );
                        } else if (i > 0) {
                            client.player.setVelocity(
                                vel.x / 1.5,
                                vel.y,
                                vel.z / 2
                            );
                        } else {
                            client.player.setVelocity(
                                vel.x / 2,
                                vel.y,
                                vel.z / 2
                            );
                        }
                    }

                    stamina.put(id, i);

                    if (isSliding) {
                        Vec3d look = client.player.getRotationVec(1);
                        client.player.addVelocity(
                                look.x * speed.get(id) / 1000,
                                0,
                                look.z * speed.get(id) / 1000
                        );
                    }
                }
            }
        });
    }

    public static boolean getSliding() {
        return isSliding;
    }

    public static boolean getStanding() {
        return isStanding;
    }
}
