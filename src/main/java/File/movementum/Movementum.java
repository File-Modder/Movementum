package File.movementum;

import File.movementum.networking.C2S.AirStrideC2SPacket;
import File.movementum.networking.C2S.SlideVelocityC2SPacket;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import static File.movementum.common.Atrophy.registerAtrophy;
import static File.movementum.common.DeadMansSprint.registerDeadMansSprint;
import static File.movementum.enchantment.ModEnchantmentEffects.registerModEnchantmentEffects;


public class Movementum implements ModInitializer {

    @Override
    public void onInitialize() {
        registerModEnchantmentEffects();
        registerAtrophy();
        registerDeadMansSprint();


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
                    });
                }
        );
    }
}