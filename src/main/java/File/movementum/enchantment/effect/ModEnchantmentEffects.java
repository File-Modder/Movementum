package File.movementum.enchantment.effect;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {

    public static final Identifier AIR_STRIDE =
            Identifier.of("movementum", "air_stride");

    public static void registerEnchants() {
        System.out.println("[Movementum] Registering air_stride effect...");
        Registry.register(
                Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE,
                AIR_STRIDE,
                AirStrideEnchantmentEffect.CODEC
        );
    }
}