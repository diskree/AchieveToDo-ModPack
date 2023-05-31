package com.diskree.achievetodo;

public interface WorldCreatorImpl {
    boolean isItemRewardsEnabled();
    boolean isExperienceRewardsEnabled();
    boolean isTrophyRewardsEnabled();

    void setItemRewardsEnabled(boolean itemRewardsEnabled);
    void setExperienceRewardsEnabled(boolean experienceRewardsEnabled);
    void setTrophyRewardsEnabled(boolean trophyRewardsEnabled);
}
