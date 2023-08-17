package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.advancements.hints.AncientCityPortalExperienceOrbSpawnS2CPacket;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.NetworkState;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.ExperienceOrbSpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NetworkState.class)
public class NetworkStateMixin {

    @Mixin(NetworkState.PacketHandlerInitializer.class)
    public static class PacketHandlerInitializerMixin {

        @Inject(method = "setup", at = @At("HEAD"))
        private void setupInject(NetworkSide side, NetworkState.PacketHandler<ClientPlayPacketListener> handler, CallbackInfoReturnable<NetworkState.PacketHandlerInitializer> cir) {
            if (side == NetworkSide.C2S && handler.getId(ExperienceOrbSpawnS2CPacket.class) != -1) {
                handler.register(AncientCityPortalExperienceOrbSpawnS2CPacket.class, AncientCityPortalExperienceOrbSpawnS2CPacket::new);
            }
        }
    }
}
