package File.movementum.called;

import File.movementum.client.MovementKeybindings;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.Vec3d;


import static File.movementum.animations.AnimationDefiner.SLIDING;
import static com.zigythebird.playeranim.PlayerAnimLibMod.ANIMATION_LAYER_ID;

public class Slide {

    public static boolean isSliding = false;
    public static boolean isStanding = false;
    static int i = 2000;
    static double s;


    public static void registerSlide() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player != null
                    && MovementKeybindings.SLIDE.isPressed()
                    && client.player.isOnGround()
                    && client.player.isSprinting()
                    && !client.player.isSwimming()
                    && !client.player.isSneaking()
                    && !client.player.isClimbing()
                    && !client.player.isRiding()
                    && !client.player.isInFluid()
                    && !client.player.isSleeping()) {
                isSliding = true;
            } else {
                isSliding = false;
            }

            if (client.player != null
                    && client.player.isOnGround()
                    && !isSliding
                    && !client.player.isSprinting()
                    && !client.player.isMovingHorizontally()
                    && !client.player.isSwimming()
                    && !client.player.isSneaking()
                    && !client.player.isClimbing()
                    && !client.player.isRiding()
                    && !client.player.isInFluid()) {
                isStanding = true;
            } else {
                isStanding = false;
            }


            System.out.println(i);
a
            if (isSliding) {

                PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(client.player, ANIMATION_LAYER_ID);
                assert controller != null;
                if (isSliding) {
                    controller.triggerAnimation(SLIDING);
                } if (!isSliding){
                    controller.stopTriggeredAnimation();
                }
            }
                    if (isSliding && i <= 2000 && i > 0) {
                        i -= 10;
                    }
                    if (!isSliding && i < 2000) {
                        i += 10;
                    }
                    if (!isSliding && isStanding && i < 2000) {
                        i += 25;
                    }
                    if (!isSliding && i <= 1000) {
                        i += 5;
                    }
                    if (!isSliding && isStanding && i <= 1000 ) {
                         i += 20;
                    }
                    if (i > 2000) {
                        i = 2000;
                    }



                    if (i >= 1750 && i <= 2000) {
                        s = 0.075;
                    }
                    if (i >= 1500 && i < 1750) {
                        s = 0.065;
                    }
                    if (i >= 1250 && i < 1500) {
                        s = 0.055;
                    }
                    if (i >= 1000 && i < 1250) {
                        s = 0.045;
                    }
                    if (i >= 750 && i < 1000) {
                        s = 0.035;
                    }
                    if (i >= 500 && i < 750) {
                        s = 0.00;
                    }
                    if (i >= 250 && i < 500) {
                        s = -0.025;
                    }
                    if (i > 0 && i < 250) {
                        s = -0.05;
                    }
                    if (i <= 0) {
                        client.player.setVelocity(0, 0, 0);
                    }


                    if (client.player != null && isSliding) {
                            Vec3d look = client.player.getRotationVec(1);
                            client.player.addVelocity(
                                    look.x * s,
                                    0,
                                    look.z * s
                );
            }
      });
    }
}
