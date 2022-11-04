package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScoreboardPlayerScore.class)
public class ScoreboardPlayerScoreMixin {

    @Shadow
    @Final
    @Nullable
    private ScoreboardObjective objective;

    @Inject(method = "setScore", at = @At("HEAD"))
    public void setScoreInject(int score, CallbackInfo ci) {
        if (this.objective != null && this.objective.getName().equalsIgnoreCase("bac_advancements")) {
            AchieveToDoMod.setAchievementsCount(score);
        }
    }
}
