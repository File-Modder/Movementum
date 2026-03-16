package File.movementum.networking.S2C;

import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public record ApplyAirStrideC2S(ServerPlayerEntity player) implements CustomPayload {
    public static final Identifier APPLY_SLIDING_VELOCITY_ID = Identifier.of("movementum", "air_stride");
    public static final Id<ApplySlidingVelocityC2S> ID = new Id<>(APPLY_SLIDING_VELOCITY_ID);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
