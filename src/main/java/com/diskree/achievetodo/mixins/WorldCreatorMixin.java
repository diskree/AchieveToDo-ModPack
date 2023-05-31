package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.WorldCreatorImpl;
import net.minecraft.client.gui.screen.world.WorldCreator;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WorldCreator.class)
public class WorldCreatorMixin implements WorldCreatorImpl {

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
}
