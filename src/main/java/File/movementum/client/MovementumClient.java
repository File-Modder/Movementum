package File.movementum.client;

import net.fabricmc.api.ClientModInitializer;

import static File.movementum.common.AirStride.registerStride;
import static File.movementum.common.Slide.registerSlide;

public class MovementumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MovementKeybindings.register();
        File.movementum.animations.controller.registerController();
        registerSlide();
        registerStride();
    }
}
