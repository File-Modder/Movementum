package File.movementum.enchantment.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record AirStrideEnchantmentEffect(EnchantmentLevelBasedValue amount)
        implements EnchantmentEntityEffect {

    public static final MapCodec<AirStrideEnchantmentEffect> CODEC =
            RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                            EnchantmentLevelBasedValue.CODEC.fieldOf("amount")
                                    .forGetter(AirStrideEnchantmentEffect::amount)
                    ).apply(instance, AirStrideEnchantmentEffect::new)
            );

    @Override
    public void apply(ServerWorld world, int level,
                      EnchantmentEffectContext context,
                      Entity user,
                      Vec3d pos) {

        if (!user.isOnGround()) {
            user.setVelocity(user.getVelocity().add(0, level, 0));
            user.velocityDirty = true;
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}