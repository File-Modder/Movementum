package File.movementum.client;

import File.movementum.called.Slide;
import net.fabricmc.api.ClientModInitializer;

public class MovementumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MovementKeybindings.register();
        File.movementum.animations.controller.registerController();
        Slide.registerSlide();
    }
}
