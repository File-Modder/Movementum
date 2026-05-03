package File.movementum.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class Atrophy {

    private static final RegistryKey<Enchantment> ATROPHY_KEY =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("movementum", "curse_of_movement"));
    public static final Map<UUID, Integer> movTicks = new HashMap<>();
    public static final Map<UUID, Integer> stillTicks = new HashMap<>();
    public static void registerAtrophy() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            Optional<RegistryEntry<Enchantment>> optionalEntry =
                    world.getRegistryManager()
                            .getOptional(RegistryKeys.ENCHANTMENT)
                            .flatMap(registry -> registry.getOptional(ATROPHY_KEY));
            if (optionalEntry.isEmpty()) return;
            RegistryEntry<Enchantment> atrophy = optionalEntry.get();

            world.getPlayers().forEach(player -> {

                UUID id = player.getUuid();
                movTicks.putIfAbsent(id, 0);
                stillTicks.putIfAbsent(id, 0);

                int level = EnchantmentHelper.getLevel(
                        atrophy,
                        player.getEquippedStack(EquipmentSlot.FEET)
                );

                if (level < 1) return;


                boolean moving = player.isMovingHorizontally() || !player.isOnGround();

                if (moving) {
                    movTicks.put(id, movTicks.get(id) + 1);
                    stillTicks.put(id, 0);
                } else {
                    int still = stillTicks.getOrDefault(id, 0) + 1;
                    stillTicks.put(id, still);


                    if (still >= 10 && movTicks.get(id) > 100) {
                        player.addStatusEffect(
                                new StatusEffectInstance(
                                        StatusEffects.SLOWNESS,
                                        Math.round(movTicks.get(id)/30f),
                                        level - 1,
                                        false, false, false
                                )
                        );
                        player.sendMessage(Text.of("You feel your legs growing weak..."), false);
                        movTicks.put(id, 0);
                        stillTicks.put(id, 0);
                    }
                }
            });
        });
    }
}