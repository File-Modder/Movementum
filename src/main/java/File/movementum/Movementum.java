package File.movementum;

import net.fabricmc.api.ModInitializer;

import static File.movementum.called.AirStride.registerStride;
import static File.movementum.enchantment.effect.ModEnchantmentEffects.registerEnchants;
import static File.movementum.enchantment.ModEnchantments.registerEnchantments;


public class Movementum implements ModInitializer {

    @Override
    public void onInitialize() {
        registerStride();
        registerEnchants();
        registerEnchantments();
    }
}
