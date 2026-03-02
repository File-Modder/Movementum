package File.movementum.client;

import net.fabricmc.api.ClientModInitializer;

public class MovementumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MovementKeybindings.register();
    }
}
