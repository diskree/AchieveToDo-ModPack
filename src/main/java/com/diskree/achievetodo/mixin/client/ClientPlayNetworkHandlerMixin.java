package com.diskree.achievetodo.mixin.client;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.ScoreboardScoreUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onScoreboardScoreUpdate", at = @At("RETURN"))
    private void onScoreboardScoreUpdateInject(ScoreboardScoreUpdateS2CPacket packet, CallbackInfo ci) {
        if (AchieveToDo.BACAP_SCORE_OBJECTIVE.equals(packet.objectiveName)) {
            AchieveToDo.setAdvancementsCount(packet.score);
        }
    }
}
