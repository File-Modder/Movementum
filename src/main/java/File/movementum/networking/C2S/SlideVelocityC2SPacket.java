package File.movementum.networking.C2S;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;



public record SlideVelocityC2SPacket(double velX, double velZ) implements CustomPayload {

    // A unique ID for this packet — "movementum:slide_velocity"
    public static final CustomPayload.Id<SlideVelocityC2SPacket> PACKET_ID =
            new CustomPayload.Id<>(Identifier.of("movementum", "slide_velocity"));


    public static final PacketCodec<PacketByteBuf, SlideVelocityC2SPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.DOUBLE, SlideVelocityC2SPacket::velX,
                    PacketCodecs.DOUBLE, SlideVelocityC2SPacket::velZ,
                    SlideVelocityC2SPacket::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}

