package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.LevelInfoImpl;
import com.mojang.serialization.Dynamic;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelInfo.class)
public class LevelInfoMixin implements LevelInfoImpl {

    private boolean isItemRewardsEnabled;
    private boolean isExperienceRewardsEnabled;
    private boolean isTrophyRewardsEnabled = true;

    @Override
    public boolean isItemRewardsEnabled() {
        return isItemRewardsEnabled;
    }

    @Override
    public boolean isExperienceRewardsEnabled() {
        return isExperienceRewardsEnabled;
    }

    @Override
    public boolean isTrophyRewardsEnabled() {
        return isTrophyRewardsEnabled;
    }

    @Override
    public void setItemRewardsEnabled(boolean itemRewardsEnabled) {
        isItemRewardsEnabled = itemRewardsEnabled;
    }

    @Override
    public void setExperienceRewardsEnabled(boolean experienceRewardsEnabled) {
        isExperienceRewardsEnabled = experienceRewardsEnabled;
    }

    @Override
    public void setTrophyRewardsEnabled(boolean trophyRewardsEnabled) {
        isTrophyRewardsEnabled = trophyRewardsEnabled;
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "fromDynamic", at = @At("RETURN"), cancellable = true)
    private static void fromDynamicInject(Dynamic<?> dynamic, DataConfiguration dataConfiguration, CallbackInfoReturnable<LevelInfo> cir) {
        LevelInfo levelInfo = cir.getReturnValue();
        LevelInfoImpl levelInfoImpl = (LevelInfoImpl) (Object) levelInfo;
        levelInfoImpl.setItemRewardsEnabled(dynamic.get("ItemRewardsEnabled").asBoolean(false));
        levelInfoImpl.setExperienceRewardsEnabled(dynamic.get("ExperienceRewardsEnabled").asBoolean(false));
        levelInfoImpl.setTrophyRewardsEnabled(dynamic.get("TrophyRewardsEnabled").asBoolean(true));
        cir.setReturnValue(levelInfo);
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "withGameMode", at = @At("RETURN"), cancellable = true)
    private void withGameModeInject(GameMode mode, CallbackInfoReturnable<LevelInfo> cir) {
        LevelInfo levelInfo = cir.getReturnValue();
        LevelInfoImpl levelInfoImpl = (LevelInfoImpl) (Object) levelInfo;
        levelInfoImpl.setItemRewardsEnabled(isItemRewardsEnabled());
        levelInfoImpl.setExperienceRewardsEnabled(isExperienceRewardsEnabled());
        levelInfoImpl.setTrophyRewardsEnabled(isTrophyRewardsEnabled());
        cir.setReturnValue(levelInfo);
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "withDifficulty", at = @At("RETURN"), cancellable = true)
    private void withDifficultyInject(Difficulty difficulty, CallbackInfoReturnable<LevelInfo> cir) {
        LevelInfo levelInfo = cir.getReturnValue();
        LevelInfoImpl levelInfoImpl = (LevelInfoImpl) (Object) levelInfo;
        levelInfoImpl.setItemRewardsEnabled(isItemRewardsEnabled());
        levelInfoImpl.setExperienceRewardsEnabled(isExperienceRewardsEnabled());
        levelInfoImpl.setTrophyRewardsEnabled(isTrophyRewardsEnabled());
        cir.setReturnValue(levelInfo);
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "withDataConfiguration", at = @At("RETURN"), cancellable = true)
    private void withDataConfigurationInject(DataConfiguration dataConfiguration, CallbackInfoReturnable<LevelInfo> cir) {
        LevelInfo levelInfo = cir.getReturnValue();
        LevelInfoImpl levelInfoImpl = (LevelInfoImpl) (Object) levelInfo;
        levelInfoImpl.setItemRewardsEnabled(isItemRewardsEnabled());
        levelInfoImpl.setExperienceRewardsEnabled(isExperienceRewardsEnabled());
        levelInfoImpl.setTrophyRewardsEnabled(isTrophyRewardsEnabled());
        cir.setReturnValue(levelInfo);
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "withCopiedGameRules", at = @At("RETURN"), cancellable = true)
    private void withCopiedGameRulesInject(CallbackInfoReturnable<LevelInfo> cir) {
        LevelInfo levelInfo = cir.getReturnValue();
        LevelInfoImpl levelInfoImpl = (LevelInfoImpl) (Object) levelInfo;
        levelInfoImpl.setItemRewardsEnabled(isItemRewardsEnabled());
        levelInfoImpl.setExperienceRewardsEnabled(isExperienceRewardsEnabled());
        levelInfoImpl.setTrophyRewardsEnabled(isTrophyRewardsEnabled());
        cir.setReturnValue(levelInfo);
    }
}
