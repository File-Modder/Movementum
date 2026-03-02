package File.movementum.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MovementKeybindings {
    public static KeyBinding SLIDE;


    public static void register() {
        SLIDE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.movementum.slide",               // Translation key for the keybinding name
                InputUtil.Type.KEYSYM,                 // Input type (keyboard)
                GLFW.GLFW_KEY_LEFT_CONTROL,           // Default key (Left Control)
                KeyBinding.Category.MOVEMENT                 // Translation key for the category
        ));
    }
}


