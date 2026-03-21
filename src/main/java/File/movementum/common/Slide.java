package File.movementum.common;

import File.movementum.client.MovementKeybindings;
import File.movementum.networking.C2S.SlideVelocityC2SPacket;
import com.zigythebird.playeranim.animation.PlayerAnimationController;
import com.zigythebird.playeranim.api.PlayerAnimationAccess;
import com.zigythebird.playeranimcore.animation.layered.modifier.AbstractFadeModifier;
import com.zigythebird.playeranimcore.easing.EasingType;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static File.movementum.animations.AnimationDefiner.SLIDING;
import static File.movementum.common.AirStride.jump;
import static com.zigythebird.playeranim.PlayerAnimLibMod.ANIMATION_LAYER_ID;

public class Slide {

    private static final RegistryKey<Enchantment> LUNGS =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("movementum", "max_stamina"));
    private static final RegistryKey<Enchantment> REGEN =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("movementum", "regen_speed"));



    private static final Map<UUID, Integer> stamina = new HashMap<>();
    private static final Map<UUID, Integer> speed = new HashMap<>();
    private static final Map<UUID, Integer> staminaMax = new HashMap<>();
    private static final Map<UUID, Integer> regenSpeed = new HashMap<>();

    public static boolean isSliding = false;
    public static boolean isStanding = false;

    public static void registerSlide() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (client.world == null) return;
            UUID id = client.player.getUuid();


            stamina.putIfAbsent(id, 2000);
            staminaMax.putIfAbsent(id, 2000);
            speed.putIfAbsent(id, 0);
            regenSpeed.putIfAbsent(id, 10);


            Optional<RegistryEntry<Enchantment>> lungsEntry =
                    client.world.getRegistryManager()
                            .getOptional(RegistryKeys.ENCHANTMENT)
                            .flatMap(registry -> registry.getOptional(LUNGS));
            if (lungsEntry.isEmpty()) return;
            RegistryEntry<Enchantment> lungs = lungsEntry.get();

            Optional<RegistryEntry<Enchantment>> regenEntry =
                    client.world.getRegistryManager()
                            .getOptional(RegistryKeys.ENCHANTMENT)
                            .flatMap(registry -> registry.getOptional(REGEN));
            if (regenEntry.isEmpty()) return;
            RegistryEntry<Enchantment> regen = regenEntry.get();


            int lung = EnchantmentHelper.getLevel(
                    lungs,
                    client.player.getEquippedStack(EquipmentSlot.CHEST)
            );
            int regen_speed = EnchantmentHelper.getLevel(
                    regen,
                    client.player.getEquippedStack(EquipmentSlot.CHEST)
            );


            if (lung == 0) {
                staminaMax.put(id, 2000);
            }
            else if (lung == 1) {
                staminaMax.put(id, 2500);
            }
            else if (lung == 2) {
                staminaMax.put(id, 3000);
            }
            else if (lung == 3) {
                staminaMax.put(id, 3500);
            }
            else if (lung == 4) {
                staminaMax.put(id, 4000);
            }
            else if (lung == 5) {
                staminaMax.put(id, 5000);
            }

            if (regen_speed == 0) {
                regenSpeed.put(id, 10);
            }
            else if (regen_speed == 1) {
                regenSpeed.put(id, 15);
            }
            else if (regen_speed == 2) {
                regenSpeed.put(id, 20);
            }
            else if (regen_speed == 3) {
                regenSpeed.put(id, 25);
            }






            client.player.sendMessage(Text.literal(stamina.get(id) + " stamina, Air Stride " + jump.get(id)), true);



            boolean isDescending = client.player.getVelocity().y < -0.08;

            if (MovementKeybindings.SLIDE.isPressed()
                && (client.player.isOnGround() || isDescending)
                && client.player.isSprinting()
                && !client.player.isSwimming()
                && !client.player.isSneaking()
                && !client.player.isClimbing()
                && !client.player.isInFluid()
                && !client.player.isSleeping()
                && !client.player.isJumping()) {
                isSliding = true;
            } else {
                isSliding = false;
            }

            if (client.player.isOnGround()
                && !isSliding
                && !client.player.isSprinting()
                && !client.player.isMovingHorizontally()
                && !client.player.isSwimming()
                && !client.player.isSneaking()
                && !client.player.isClimbing()
                && !client.player.isInFluid()
                && !client.player.isJumping()) {
                isStanding = true;
            } else {
                isStanding = false;
            }

            PlayerAnimationController controller =
                (PlayerAnimationController) PlayerAnimationAccess.getPlayerAnimationLayer(
                    client.player,
                    ANIMATION_LAYER_ID
                );

            if (controller != null) {
                if (isSliding) {
                    controller.replaceAnimationWithFade(
                        AbstractFadeModifier.standardFadeIn(3, EasingType.EASE_IN_BACK),
                        SLIDING
                    );
                } else {
                    controller.stopTriggeredAnimation();
                }
            }

            if (isSliding) {
                if (stamina.get(id) > 0) {
                    stamina.put(id, stamina.get(id) - 10);
                }
            } else if (!isStanding && stamina.get(id) < staminaMax.get(id)) {
                stamina.put(id, stamina.get(id) + regenSpeed.get(id));
            } else if (isStanding && stamina.get(id) < staminaMax.get(id)) {
                stamina.put(id, stamina.get(id) + regenSpeed.get(id) + 15);
            }

            if (stamina.get(id) > staminaMax.get(id)) {
                stamina.put(id, staminaMax.get(id));
            }

            if (stamina.get(id) >= staminaMax.get(id) * 0.875) {
                speed.put(id, 75);
            } else if (stamina.get(id) >= staminaMax.get(id) * 0.75) {
                speed.put(id, 65);
            } else if (stamina.get(id) >= staminaMax.get(id) * 0.625) {
                speed.put(id, 55);
            } else if (stamina.get(id) >= staminaMax.get(id) * 0.5) {
                speed.put(id, 45);
            } else if (stamina.get(id) >= staminaMax.get(id) * 0.375) {
                speed.put(id, 35);
            } else if (stamina.get(id) >= staminaMax.get(id) * 0.25) {
                speed.put(id, 25);
            } else if (stamina.get(id) >= staminaMax.get(id) * 0.125) {
                speed.put(id, -25);
            } else if (stamina.get(id) < staminaMax.get(id) * 0.125) {
                speed.put(id, -45);
            }

            Vec3d look = client.player.getRotationVec(1);
            double dx = look.x * speed.get(id) / 1000.0;
            double dz = look.z * speed.get(id) / 1000.0;

            if (isSliding && stamina.get(id) > 0) {
                client.player.addVelocity(dx, 0, dz);
                client.player.velocityDirty = true;
            }

            ClientPlayNetworking.send(new SlideVelocityC2SPacket(dx, dz));
        });
    }

}
