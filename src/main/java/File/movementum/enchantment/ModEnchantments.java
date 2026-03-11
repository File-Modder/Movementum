package File.movementum.enchantment;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.enchantment.Enchantment;

public class ModEnchantments {
    // Registry key for the Air Stride enchantment
    // The actual enchantment is defined in data/movementum/enchantments/air_stride.json
    public static final RegistryKey<Enchantment> AIR_STRIDE =
        RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("movementum", "air_stride"));

    public static void registerEnchantments() {
        // In Minecraft 1.21+, enchantments are registered through JSON data files
        // located in src/main/resources/data/movementum/enchantments/
        // This method is called to ensure the class is loaded
        System.out.println("[Movementum] Enchantments are loaded from JSON datapacks");
    }
}