package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.client.ClientPlayNetworkHandlerImpl;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalExperienceOrbEntity;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalExperienceOrbSpawnS2CPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.NetworkThreadUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin implements ClientPlayNetworkHandlerImpl {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private ClientWorld world;

    @Override
    public void achieveToDo$onAncientCityPortalExperienceOrbSpawn(AncientCityPortalExperienceOrbSpawnS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, (ClientPlayNetworkHandler) (Object) this, client);
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        AncientCityPortalExperienceOrbEntity entity = new AncientCityPortalExperienceOrbEntity(world, x, y, z, packet.getPortalTarget(), packet.getInclineTarget(), packet.getSize());
        entity.syncPacketPositionCodec(x, y, z);
        entity.setYaw(0.0f);
        entity.setPitch(0.0f);
        entity.setId(packet.getId());
        world.addEntity(packet.getId(), entity);
    }
}
