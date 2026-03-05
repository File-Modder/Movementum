package File.movementum.called;

import File.movementum.client.MovementKeybindings;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.Vec3d;


import static File.movementum.animations.AnimationDefiner.SLIDING;
import static com.zigythebird.playeranim.PlayerAnimLibMod.ANIMATION_LAYER_ID;

public class Slide {

    public static boolean isSliding = false;
    static int i = 1000;
    static double s;


    public static void registerSlide() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (isSliding) {
            System.out.println(i);
            }
            isSliding = MovementKeybindings.SLIDE.isPressed();

      if (client.player != null) {
          PlayerAnimationController controller = (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(client.player, ANIMATION_LAYER_ID);

          if (controller != null) {
              // Trigger animation when we start sliding
              if (isSliding) {
                  controller.triggerAnimation(SLIDING);
              }

          }
      }

      if (isSliding && i <= 1000 && i > 0) {
          i-= 10;
      } else if (i >= 350 && i < 1000) {
          i += 10;
      } else if (i >= 0&& i < 350 && !isSliding) {
          i += 5;
      } else if (i < 0 && !isSliding) {
          i ++;
      }

      if (i >= 750 && i <= 1000) {
          s = 0.125;
      }
      if (i >= 500 && i < 750) {
          s = 0.1;
      }
      if (i >= 250 && i < 500) {
          s = 0.075 ;
      }
      if (i >= 0 && i < 250) {
          s = 0.05;
      }
      if (i <= 0) {
          s = -0.05;
      }


          if (client.player != null && isSliding) {
              if (client.player.isOnGround()
                      && client.player.isSprinting()
                      && !client.player.isSwimming()
                      && !client.player.isSneaking()
                      && !client.player.isClimbing()
                      && !client.player.isRiding()
                      && !client.player.isInFluid()
                      && !client.player.isSleeping()) {
                  Vec3d look = client.player.getRotationVec(1);
                  client.player.addVelocity(
                          look.x * s,
                          0,
                      look.z * s
                  );
              }
          }
        });
    }
}

