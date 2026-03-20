package File.movementum.networking.S2C;

import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ApplyAirStrideC2S() implements CustomPayload {
    public static final Id<ApplyAirStrideC2S> ID = new Id<>(Identifier.of("movementum", "air_stride"));
    public static final PacketCodec<io.netty.buffer.ByteBuf, ApplyAirStrideC2S> CODEC = PacketCodec.unit(new ApplyAirStrideC2S());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}

