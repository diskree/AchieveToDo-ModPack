package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.WorldCreatorImpl;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.screen.world.WorldScreenOptionGrid;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CreateWorldAdvancementsTab extends GridScreenTab {

    private static final Text REWARDS_TITLE = Text.translatable("createWorld.rewards.title");
    private static final Text ITEM_REWARDS = Text.translatable("createWorld.rewards.item");
    private static final Text ITEM_REWARDS_INFO = Text.translatable("createWorld.rewards.item.info");
    private static final Text EXPERIENCE_REWARDS = Text.translatable("createWorld.rewards.experience");
    private static final Text EXPERIENCE_REWARDS_INFO = Text.translatable("createWorld.rewards.experience.info");
    private static final Text TROPHY_REWARDS = Text.translatable("createWorld.rewards.trophy");
    private static final Text TROPHY_REWARDS_INFO = Text.translatable("createWorld.rewards.trophy.info");
    private static final Text GENERATION_TITLE = Text.translatable("createWorld.generation.title");
    private static final Text OVERWORLD_GENERATION = Text.translatable("createWorld.generation.overworld");
    private static final Text OVERWORLD_GENERATION_INFO = Text.translatable("createWorld.generation.overworld.info");
    private static final Text NETHER_GENERATION = Text.translatable("createWorld.generation.nether");
    private static final Text NETHER_GENERATION_INFO = Text.translatable("createWorld.generation.nether.info");

    public CreateWorldAdvancementsTab(CreateWorldScreen screen) {
        super(Text.of("AchieveToDo"));
        if (screen == null || screen.client == null) {
            return;
        }
        WorldCreator worldCreator = screen.getWorldCreator();
        WorldCreatorImpl worldSettings = (WorldCreatorImpl) worldCreator;

        this.grid.getMainPositioner().alignHorizontalCenter();

        GridWidget.Adder adder = this.grid.setColumnSpacing(10).setRowSpacing(8).createAdder(2);

        GridWidget.Adder rewardsTitleAdder = new GridWidget().setRowSpacing(4).createAdder(1);
        rewardsTitleAdder.add(new TextWidget(REWARDS_TITLE.copy().formatted(Formatting.YELLOW), screen.client.textRenderer));
        adder.add(rewardsTitleAdder.getGridWidget(), 2);

        WorldScreenOptionGrid.Builder rewardsOptionsBuilder = WorldScreenOptionGrid.builder(170).marginLeft(1);
        rewardsOptionsBuilder.add(ITEM_REWARDS, worldSettings::achieveToDo$isItemRewardsEnabled, worldSettings::achieveToDo$setItemRewardsEnabled).tooltip(ITEM_REWARDS_INFO);
        rewardsOptionsBuilder.add(EXPERIENCE_REWARDS, worldSettings::achieveToDo$isExperienceRewardsEnabled, worldSettings::achieveToDo$setExperienceRewardsEnabled).tooltip(EXPERIENCE_REWARDS_INFO);
        rewardsOptionsBuilder.add(TROPHY_REWARDS, worldSettings::achieveToDo$isTrophyRewardsEnabled, worldSettings::achieveToDo$setTrophyRewardsEnabled).tooltip(TROPHY_REWARDS_INFO);
        WorldScreenOptionGrid rewardsOptionsGrid = rewardsOptionsBuilder.build(widget -> adder.add(widget, 2));

        GridWidget.Adder generationTitleAdder = new GridWidget().setRowSpacing(4).createAdder(1);
        generationTitleAdder.add(new TextWidget(GENERATION_TITLE.copy().formatted(Formatting.YELLOW), screen.client.textRenderer));
        adder.add(generationTitleAdder.getGridWidget(), 2);

        WorldScreenOptionGrid.Builder generationOptionsBuilder = WorldScreenOptionGrid.builder(170).marginLeft(1);
        generationOptionsBuilder.add(OVERWORLD_GENERATION, worldSettings::achieveToDo$isTerralithEnabled, worldSettings::achieveToDo$setTerralithEnabled).tooltip(OVERWORLD_GENERATION_INFO);
        generationOptionsBuilder.add(NETHER_GENERATION, worldSettings::achieveToDo$isAmplifiedNetherEnabled, worldSettings::achieveToDo$setAmplifiedNetherEnabled).tooltip(NETHER_GENERATION_INFO);
        WorldScreenOptionGrid generationOptionGrid = generationOptionsBuilder.build(widget -> adder.add(widget, 2));

        worldCreator.addListener(creator -> {
            rewardsOptionsGrid.refresh();
            generationOptionGrid.refresh();
        });
    }
}
