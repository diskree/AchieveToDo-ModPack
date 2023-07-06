package com.diskree.achievetodo;

public interface WorldCreatorImpl {
    boolean achieveToDo$isItemRewardsEnabled();
    boolean achieveToDo$isExperienceRewardsEnabled();
    boolean achieveToDo$isTrophyRewardsEnabled();
    boolean achieveToDo$isTerralithEnabled();
    boolean achieveToDo$isAmplifiedNetherEnabled();

    void achieveToDo$setItemRewardsEnabled(boolean itemRewardsEnabled);
    void achieveToDo$setExperienceRewardsEnabled(boolean experienceRewardsEnabled);
    void achieveToDo$setTrophyRewardsEnabled(boolean trophyRewardsEnabled);
    void achieveToDo$setTerralithEnabled(boolean terralithEnabled);
    void achieveToDo$setAmplifiedNetherEnabled(boolean amplifiedNetherEnabled);
}
