package File.movementum;

import net.fabricmc.api.ModInitializer;

import static File.movementum.called.Atrophy.registerAtrophy;
import static File.movementum.enchantment.ModEnchantmentEffects.registerModEnchantmentEffects;


public class Movementum implements ModInitializer {

    @Override
    public void onInitialize() {
        registerModEnchantmentEffects();
        registerAtrophy();
    }
}
