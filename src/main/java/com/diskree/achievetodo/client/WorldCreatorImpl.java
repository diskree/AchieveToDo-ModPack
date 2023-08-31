package com.diskree.achievetodo.client;

import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public interface WorldCreatorImpl {

    boolean achieveToDo$isItemRewardsEnabled();

    boolean achieveToDo$isExperienceRewardsEnabled();

    boolean achieveToDo$isTrophyRewardsEnabled();

    boolean achieveToDo$isTerralithEnabled();

    boolean achieveToDo$isAmplifiedNetherEnabled();

    boolean achieveToDo$isNullscapeEnabled();

    boolean achieveToDo$isCooperativeModeEnabled();

    void achieveToDo$setItemRewardsEnabled(boolean itemRewardsEnabled);

    void achieveToDo$setExperienceRewardsEnabled(boolean experienceRewardsEnabled);

    void achieveToDo$setTrophyRewardsEnabled(boolean trophyRewardsEnabled);

    void achieveToDo$setTerralithEnabled(boolean terralithEnabled);

    void achieveToDo$setAmplifiedNetherEnabled(boolean amplifiedNetherEnabled);

    void achieveToDo$setNullscapeEnabled(boolean nullscapeEnabled);

    void achieveToDo$setCooperativeModeEnabled(boolean cooperativeModeEnabled);
}
