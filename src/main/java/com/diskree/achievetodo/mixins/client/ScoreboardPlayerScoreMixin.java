package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(ScoreboardPlayerScore.class)
public class ScoreboardPlayerScoreMixin {

    @Shadow
    @Final
    @Nullable
    private ScoreboardObjective objective;

    @Shadow
    @Final
    private String playerName;

    @Inject(method = "setScore", at = @At("HEAD"))
    public void setScoreInject(int score, CallbackInfo ci) {
        if (this.objective != null && "bac_advancements".equals(this.objective.getName())) {
            AchieveToDo.setAdvancementsCount(playerName, score);
        }
    }
}