package File.movementum.called;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class AirStride {
        public static void registerStride() {
            ServerTickEvents.END_SERVER_TICK.register(server ->
                server.getPlayerManager().getPlayerList().forEach(player -> {
            })
        );
    }
}
