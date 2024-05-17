package com.diskree.achievetodo.networking;

import com.diskree.achievetodo.BuildConfig;
import com.diskree.achievetodo.blocked_actions.BlockedActionType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public record GrantBlockedActionPayload(
        @NotNull BlockedActionType blockedAction,
        boolean isDemystifyOnly
) implements CustomPayload {

    public static final Id<GrantBlockedActionPayload> ID =
            new CustomPayload.Id<>(new Identifier(BuildConfig.MOD_ID, "grant_blocked_action"));

    public static final PacketCodec<PacketByteBuf, GrantBlockedActionPayload> CODEC =
            CustomPayload.codecOf(GrantBlockedActionPayload::write, GrantBlockedActionPayload::new);

    private GrantBlockedActionPayload(PacketByteBuf buf) {
        this(buf.readEnumConstant(BlockedActionType.class), buf.readBoolean());
    }

    private void write(PacketByteBuf buf) {
        buf.writeEnumConstant(blockedAction);
        buf.writeBoolean(isDemystifyOnly);
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
