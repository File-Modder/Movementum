package File.movementum.called;

import File.movementum.client.MovementKeybindings;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Slide {

    public static boolean isSliding = false;
    static int i = 1000;
    static double s;

    public static void registerSlide() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            System.out.println(i);

      if (MovementKeybindings.SLIDE.isPressed()) {
          isSliding = true;
        } else {
          isSliding = false;
        }

      if (isSliding && i <= 1000 && i > 0) {
          i-= 10;
      } else if (i >= 350 && i < 1000 && !isSliding) {
          i += 10;
      } else if (i >= 0&& i < 350 && !isSliding) {
          i += 5;
      } else if (i < 0 && !isSliding) {
          i ++;
      }

      if (i >= 750 && i <= 1000) {
          s = 0.3;
      }
      if (i >= 500 && i < 750) {
          s = 0.25;
      }
      if (i >= 250 && i < 500) {
          s = 0.2 ;
      }
      if (i >= 0 && i < 250) {
          s = 0.15;
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

