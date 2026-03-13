package File.movementum.enchantment;

import File.movementum.enchantment.effects.AirStrideEnchantmentEffect;
import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public final class ModEnchantmentEffects {
    public static final MapCodec<AirStrideEnchantmentEffect> AIR_STRIDE =
            register("air_stride", AirStrideEnchantmentEffect.CODEC);

    private ModEnchantmentEffects() {
    }

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of("movementum", id), codec);
    }

    public static void registerModEnchantmentEffects() {
        System.out.println("[Movementum] Registered enchantment effect types");
    }
}