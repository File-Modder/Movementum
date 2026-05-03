package File.movementum;

import File.movementum.networking.C2S.AirStrideC2SPacket;
import File.movementum.networking.C2S.SlideVelocityC2SPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;

import static File.movementum.common.Atrophy.registerAtrophy;
import static File.movementum.common.DeadMansSprint.registerDeadMansSprint;
import static File.movementum.common.MiscEvents.initMiscEvents;
import static File.movementum.enchantment.ModEnchantmentEffects.registerModEnchantmentEffects;


public class Movementum implements ModInitializer {

    @Override
    public void onInitialize() {
        registerModEnchantmentEffects();
        registerAtrophy();
        registerDeadMansSprint();
        initMiscEvents();



        PayloadTypeRegistry.playC2S().register(
                SlideVelocityC2SPacket.PACKET_ID,
                SlideVelocityC2SPacket.CODEC
        );
        PayloadTypeRegistry.playC2S().register(
                AirStrideC2SPacket.PACKET_ID,
                AirStrideC2SPacket.CODEC
        );


        ServerPlayNetworking.registerGlobalReceiver(
                SlideVelocityC2SPacket.PACKET_ID,
                (payload, context) -> {

                    context.server().execute(() -> {
                        context.player().addVelocity(payload.velX(), 0, payload.velZ());
                        context.player().velocityDirty = true;
                    });
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                AirStrideC2SPacket.PACKET_ID,
                (payload, context) -> {
                    context.server().execute(() -> {
                        context.player().addVelocity(payload.velX(), payload.velY(), payload.velZ());
                        context.player().velocityDirty = true;
                        ServerWorld world = context.player().getEntityWorld();


                        world.spawnParticles(
                                ParticleTypes.CLOUD,
                                context.player().getX(),
                                context.player().getY(),
                                context.player().getZ(),
                                13,        // particle count
                                0.25,      // offset X
                                0.35,      // offset Y
                                0.25,      // offset Z
                                -0.1       // speed
                        );
                    });
                }
        );
    }
}