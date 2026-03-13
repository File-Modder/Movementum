package File.movementum.client;

import net.fabricmc.api.ClientModInitializer;

import static File.movementum.called.AirStride.registerStride;
import static File.movementum.called.Slide.registerSlide;

public class MovementumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MovementKeybindings.register();
        File.movementum.animations.controller.registerController();
        registerSlide();
        registerStride();
    }
}
