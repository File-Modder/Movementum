package File.movementum.networking.C2S;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;


public record AirStrideC2SPacket(double velX, double velY, double velZ) implements CustomPayload {

    // A unique ID for this packet — "movementum:slide_velocity"
    public static final CustomPayload.Id<AirStrideC2SPacket> PACKET_ID =
            new CustomPayload.Id<>(Identifier.of("movementum", "stride_velocity"));


    public static final PacketCodec<PacketByteBuf, AirStrideC2SPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.DOUBLE, AirStrideC2SPacket::velX,
                    PacketCodecs.DOUBLE, AirStrideC2SPacket::velY,
                    PacketCodecs.DOUBLE, AirStrideC2SPacket::velZ,
                    AirStrideC2SPacket::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}

