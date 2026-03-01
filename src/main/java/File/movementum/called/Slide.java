package File.movementum.called;

import File.movementum.client.MovementKeybindings;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class Slide {

    public static void registerSlide() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player == null) return;

            if (!MovementKeybindings.SLIDE.isPressed()) return;
            if (client.player.isOnGround()
                    && client.player.isSprinting()
                    && !client.player.isSwimming()
                    && !client.player.isClimbing()
                    && !client.player.isCrawling()
                    && !client.player.isRiding()
                    && !client.player.isInFluid()
                    && !client.player.isSleeping()) {

                Vec3d look = client.player.getRotationVec(1);
                client.player.addVelocity(
                        look.x * 0.08,
                        0,
                        look.z * 0.08
                );

            }
        });
    }
}