package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.WorldCreatorImpl;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.screen.world.WorldScreenOptionGrid;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.text.Text;

public class CreateWorldAdvancementsTab extends GridScreenTab {

    private static final Text ITEM_REWARDS = Text.translatable("createWorld.rewards.item");
    private static final Text EXPERIENCE_REWARDS = Text.translatable("createWorld.rewards.experience");
    private static final Text TROPHY_REWARDS = Text.translatable("createWorld.rewards.trophy");

    private static final Text ITEM_REWARDS_INFO = Text.translatable("createWorld.rewards.item.info");
    private static final Text EXPERIENCE_REWARDS_INFO = Text.translatable("createWorld.rewards.experience.info");
    private static final Text TROPHY_REWARDS_INFO = Text.translatable("createWorld.rewards.trophy.info");

    public CreateWorldAdvancementsTab(CreateWorldScreen screen) {
        super(Text.of("AchieveToDo"));
        WorldCreator worldCreator = screen.getWorldCreator();
        WorldCreatorImpl worldSettings = (WorldCreatorImpl) worldCreator;
        GridWidget.Adder adder = this.grid.setColumnSpacing(10).setRowSpacing(8).createAdder(2);
        WorldScreenOptionGrid.Builder builder = WorldScreenOptionGrid.builder(170).marginLeft(1);
        builder.add(ITEM_REWARDS, worldSettings::isItemRewardsEnabled, worldSettings::setItemRewardsEnabled).tooltip(ITEM_REWARDS_INFO);
        builder.add(EXPERIENCE_REWARDS, worldSettings::isExperienceRewardsEnabled, worldSettings::setExperienceRewardsEnabled).tooltip(EXPERIENCE_REWARDS_INFO);
        builder.add(TROPHY_REWARDS, worldSettings::isTrophyRewardsEnabled, worldSettings::setTrophyRewardsEnabled).tooltip(TROPHY_REWARDS_INFO);
        WorldScreenOptionGrid worldScreenOptionGrid = builder.build(widget -> adder.add(widget, 3));
        worldCreator.addListener(creator -> worldScreenOptionGrid.refresh());
    }
}
