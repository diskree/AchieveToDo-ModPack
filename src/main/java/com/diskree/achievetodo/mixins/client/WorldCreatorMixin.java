package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.client.WorldCreatorImpl;
import net.minecraft.client.world.WorldCreator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldCreator.class)
public class WorldCreatorMixin implements WorldCreatorImpl {

    @Unique
    private boolean isItemRewardsEnabled;

    @Unique
    private boolean isExperienceRewardsEnabled;

    @Unique
    private boolean isTrophyRewardsEnabled = true;

    @Unique
    private boolean isTerralithEnabled;

    @Unique
    private boolean isAmplifiedNetherEnabled;

    @Unique
    private boolean isNullscapeEnabled;

    @Unique
    private boolean isCooperativeModeEnabled;

    @Override
    public boolean achieveToDo$isItemRewardsEnabled() {
        return isItemRewardsEnabled;
    }

    @Override
    public boolean achieveToDo$isExperienceRewardsEnabled() {
        return isExperienceRewardsEnabled;
    }

    @Override
    public boolean achieveToDo$isTrophyRewardsEnabled() {
        return isTrophyRewardsEnabled;
    }

    @Override
    public boolean achieveToDo$isTerralithEnabled() {
        return isTerralithEnabled;
    }

    @Override
    public boolean achieveToDo$isAmplifiedNetherEnabled() {
        return isAmplifiedNetherEnabled;
    }

    @Override
    public boolean achieveToDo$isNullscapeEnabled() {
        return isNullscapeEnabled;
    }

    @Override
    public boolean achieveToDo$isCooperativeModeEnabled() {
        return isCooperativeModeEnabled;
    }

    @Override
    public void achieveToDo$setItemRewardsEnabled(boolean itemRewardsEnabled) {
        isItemRewardsEnabled = itemRewardsEnabled;
    }

    @Override
    public void achieveToDo$setExperienceRewardsEnabled(boolean experienceRewardsEnabled) {
        isExperienceRewardsEnabled = experienceRewardsEnabled;
    }

    @Override
    public void achieveToDo$setTrophyRewardsEnabled(boolean trophyRewardsEnabled) {
        isTrophyRewardsEnabled = trophyRewardsEnabled;
    }

    @Override
    public void achieveToDo$setTerralithEnabled(boolean terralithEnabled) {
        isTerralithEnabled = terralithEnabled;
    }

    @Override
    public void achieveToDo$setAmplifiedNetherEnabled(boolean amplifiedNetherEnabled) {
        isAmplifiedNetherEnabled = amplifiedNetherEnabled;
    }

    @Override
    public void achieveToDo$setNullscapeEnabled(boolean nullscapeEnabled) {
        isNullscapeEnabled = nullscapeEnabled;
    }

    @Override
    public void achieveToDo$setCooperativeModeEnabled(boolean cooperativeModeEnabled) {
        isCooperativeModeEnabled = cooperativeModeEnabled;
    }
}
