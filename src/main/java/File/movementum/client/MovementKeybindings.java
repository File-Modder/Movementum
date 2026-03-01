package File.movementum.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import static net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper.registerKeyBinding;

public class MovementKeybindings {
    public static KeyBinding SLIDE;



    public static void register() {
        SLIDE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.movementum.slide",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_CONTROL,
                KeyBinding.Category.MOVEMENT
        ));
    }
}


