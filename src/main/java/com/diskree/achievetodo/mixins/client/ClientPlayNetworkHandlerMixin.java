package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalExperienceOrbEntity;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalExperienceOrbSpawnS2CPacket;
import com.diskree.achievetodo.client.ClientPlayNetworkHandlerImpl;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.ScoreboardScoreUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin implements ClientPlayNetworkHandlerImpl {

    @Shadow
    private ClientWorld world;

    @Override
    public void achieveToDo$onAncientCityPortalExperienceOrbSpawn(AncientCityPortalExperienceOrbSpawnS2CPacket packet) {
        ClientPlayNetworkHandler clientPlayNetworkHandler = (ClientPlayNetworkHandler) (Object) this;
        NetworkThreadUtils.forceMainThread(packet, clientPlayNetworkHandler, clientPlayNetworkHandler.client);
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        AncientCityPortalExperienceOrbEntity entity = new AncientCityPortalExperienceOrbEntity(world, x, y, z, packet.getPortalTarget(), packet.getInclineTarget(), packet.getSize());
        entity.updateTrackedPosition(x, y, z);
        entity.setYaw(0.0f);
        entity.setPitch(0.0f);
        entity.setId(packet.getId());
        world.addEntity(entity);
    }

    @Inject(method = "onScoreboardScoreUpdate", at = @At("RETURN"))
    private void isAutoJumpEnabledInject(ScoreboardScoreUpdateS2CPacket packet, CallbackInfo ci) {
        if (AchieveToDo.BACAP_SCORE_OBJECTIVE.equals(packet.objectiveName)) {
            AchieveToDo.setAdvancementsCount(packet.score);
        }
    }
}
