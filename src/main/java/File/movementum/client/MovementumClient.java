package File.movementum.client;

import File.movementum.called.Slide;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import static File.movementum.called.Slide.registerSlide;


public class MovementumClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MovementKeybindings.register();
        Slide.registerSlide();
    }
}