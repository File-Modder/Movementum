package File.movementum.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class MovementKeybindings {
    public static KeyBinding AIR;
    public static KeyBinding SLIDE;
    public static KeyBinding JUMP;


    public static void register() {
        SLIDE = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.movementum.slide",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_CONTROL,
                KeyBinding.Category.MOVEMENT
        ));
        AIR = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.movementum.air_jump",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_SHIFT,
                KeyBinding.Category.MOVEMENT
        ));
        JUMP = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.movementum.double_jump",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KeyBinding.Category.MOVEMENT
        ));
    }
}


