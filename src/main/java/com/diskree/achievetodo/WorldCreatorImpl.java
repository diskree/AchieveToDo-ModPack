package com.diskree.achievetodo;

public interface WorldCreatorImpl {
    boolean isItemRewardsEnabled();
    boolean isExperienceRewardsEnabled();
    boolean isTrophyRewardsEnabled();
    boolean isTerralithEnabled();

    void setItemRewardsEnabled(boolean itemRewardsEnabled);
    void setExperienceRewardsEnabled(boolean experienceRewardsEnabled);
    void setTrophyRewardsEnabled(boolean trophyRewardsEnabled);
    void setTerralithEnabled(boolean terralithEnabled);
}
