package File.movementum.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.math.Box;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WallRunClient {

    public static final Map<UUID, Boolean> touchingWall = new HashMap<>();


    public static void registerClientWallRun() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;

            UUID id = client.player.getUuid();
            touchingWall.putIfAbsent(id, false);
            Box box = client.player.getBoundingBox();

            if (
                !client.world.isSpaceEmpty(client.player, box.offset(0.1, 0, 0)) ||
                !client.world.isSpaceEmpty(client.player, box.offset(-0.1, 0, 0)) ||
                !client.world.isSpaceEmpty(client.player, box.offset(0, 0, 0.1)) ||
                !client.world.isSpaceEmpty(client.player, box.offset(0, 0, -0.1))) {
            if (
                !client.player.isOnGround()
                && client.options.sneakKey.isPressed()
                && !client.player.isHoldingOntoLadder()
                && !client.player.isSubmergedInWater()) {

                    touchingWall.put(id, true);
            }
            }
            else {
                touchingWall.put(id, false);
            }

            if (touchingWall.get(id)) {
                client.player.setVelocity(client.player.getVelocity().x, 0, client.player.getVelocity().z);
                client.player.setSprinting(false);
                client.player.velocityDirty = true;
            }

        });
    }
}
