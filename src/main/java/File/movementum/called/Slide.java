package File.movementum.called;

import File.movementum.client.MovementKeybindings;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;


import java.util.*;

import static File.movementum.animations.AnimationDefiner.SLIDING;
import static com.zigythebird.playeranim.PlayerAnimLibMod.ANIMATION_LAYER_ID;

public class Slide {


    private static final Map<UUID, Integer> stamina = new HashMap<>();
    private static final Map<UUID, Integer> speed   = new HashMap<>();

    public static boolean isSliding = false;
    public static boolean isStanding = false;



    public static void registerSlide() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {




            if (client.player != null) {
                UUID ID = client.player.getUuid();
                stamina.putIfAbsent(ID, 2000);

                boolean isDescending = client.player.getVelocity().y < -0.08;

                if (        MovementKeybindings.SLIDE.isPressed()
                        && (client.player.isOnGround() || isDescending)
                        &&  client.player.isSprinting()
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

                 if (       client.player.isOnGround()
                        && !isSliding
                        && !client.player.isSprinting()
                        && !client.player.isMovingHorizontally()
                        && !client.player.isSwimming()
                        && !client.player.isSneaking()
                        && !client.player.isClimbing()
                        && !client.player.isRiding()
                        && !client.player.isInFluid()
                        && !client.player.isJumping()
                 ) {
                         isStanding = true;
                     } else {
                         isStanding = false;
                     }

            if (client.player != null) {
                PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(client.player, ANIMATION_LAYER_ID);
                if (controller != null) {
                    if (isSliding) {
                        controller.replaceAnimationWithFade(AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_BACK), SLIDING);
                    } else {
                        controller.stopTriggeredAnimation();
                    }
                }

                int i = stamina.get(ID);

                if(isSliding && !isStanding && i > 0) {
                    i -= 10;
                }

                if (i > 2000){
                    i = 2000;
                } else if (!isSliding && isStanding && i < 2000 && i >= 0) {
                    i += 25;
                } else if (!isSliding && i < 2000 && i >= 0) {
                    i += 12;
                } else if (i < 0 && !isSliding) {
                    i = 0;
                }
                if (isSliding) {
                    client.player.sendMessage(Text.literal(i + " stamina"), true);
                    Vec3d vel = client.player.getVelocity();
                    if (i >= 1750) {
                        speed.put(ID, 75);
                    } else if (i >= 1500) {
                        speed.put(ID, 65);
                    } else if (i >= 1250) {
                        speed.put(ID, 55);
                    } else if (i >= 1000) {
                        speed.put(ID, 45);
                    } else if (i >= 750) {
                        speed.put(ID, 35);
                    } else if (i >= 500) {
                        speed.put(ID, 0);
                    } else if (i >= 250) {
                        client.player.setVelocity(client.player.getVelocity().x / 1.25, client.player.getVelocity().y, client.player.getVelocity().z / 2);
                    } else if (i > 0) {
                        client.player.setVelocity(client.player.getVelocity().x / 1.5, client.player.getVelocity().y, client.player.getVelocity().z / 2);
                    } else {
                        client.player.setVelocity(client.player.getVelocity().x / 2, client.player.getVelocity().y, client.player.getVelocity().z / 2);
                    }
                }

                stamina.put(ID, i);

                if (isSliding) {
                    Vec3d look = client.player.getRotationVec(1);
                    client.player.addVelocity(
                            look.x * speed.get(ID) / 1000,
                            0,
                            look.z * speed.get(ID) / 1000
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
