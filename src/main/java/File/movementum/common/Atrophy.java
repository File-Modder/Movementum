package File.movementum.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class Atrophy {

    private static final RegistryKey<Enchantment> ATROPHY_KEY =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("movementum", "curse_of_movement"));
            private static int ticks = 0;
    public static void registerAtrophy() {

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            ticks++;

            // ✅ Safe registry access (won’t crash)
            Optional<RegistryEntry<Enchantment>> optionalEntry =
                    world.getRegistryManager()
                            .getOptional(RegistryKeys.ENCHANTMENT)
                            .flatMap(registry -> registry.getOptional(ATROPHY_KEY));

            // If enchantment isn't loaded yet, just skip this tick
            if (optionalEntry.isEmpty()) return;

            RegistryEntry<Enchantment> atrophy = optionalEntry.get();

            // Loop players
            world.getPlayers().forEach(player -> {

                int level = EnchantmentHelper.getLevel(
                        atrophy,
                        player.getEquippedStack(EquipmentSlot.FEET)
                );

                // Your effect
                if (level >= 1 && !player.isMovingHorizontally() && ticks % 20 == 0) {
                    player.damage(world, world.getDamageSources().magic(), level);
                }


            });
        });
    }
}