package File.movementum.called;

import File.movementum.client.MovementKeybindings;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SlideArchived {

    private static Vec3d cachedLookDirection = Vec3d.ZERO;
    private static boolean isSliding = false;
    private static boolean wasSliding = false;

    public static void registerSlide() {
        // Client-side slide movement
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                isSliding = false;
                if (wasSliding) {
                    wasSliding = false;
                }
                return;
            }

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
                        look.x * 0.08,
                        0,
                        look.z * 0.08
                );

            }
        });

        // Server-side entity pushing
        ServerTickEvents.END_SERVER_TICK.register(server -> {

            List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
            for (ServerPlayerEntity player : players) {
                if (MovementKeybindings.SLIDE.isPressed()) {
                Vec3d vel = player.getVelocity();
                Box box = player.getBoundingBox().expand(0.5, 0, 0.5);
                List<Entity> entities = player.getEntityWorld().getOtherEntities(player, box, e -> true);
                for (Entity entity : entities) {
                    if (entity.isPushable() && !entity.equals(player)) {
                        entity.setVelocity(
                                vel.x * 1.5,
                                0.5,
                                vel.z * 1.5
                            );
                        }
                    }
                }
            }
        });
    }
}

