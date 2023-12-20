package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.BuildConfig;
import com.diskree.achievetodo.client.WorldCreatorImpl;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.screen.world.WorldScreenOptionGrid;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CreateWorldTab extends GridScreenTab {

    private static final Text LAN_TITLE = Text.translatable("lanServer.title");
    private static final Text COOPERATIVE_MODE = Text.translatable("createWorld.lan.cooperative_mode");
    private static final Text COOPERATIVE_MODE_INFO = Text.translatable("createWorld.lan.cooperative_mode.info");
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
    private static final Text END_GENERATION = Text.translatable("createWorld.generation.end");
    private static final Text END_GENERATION_INFO = Text.translatable("createWorld.generation.end.info");

    public CreateWorldTab(CreateWorldScreen screen) {
        super(Text.of(BuildConfig.MOD_NAME));
        if (screen == null || screen.client == null) {
            return;
        }
        WorldCreator worldCreator = screen.getWorldCreator();
        WorldCreatorImpl worldSettings = (WorldCreatorImpl) worldCreator;

        this.grid.getMainPositioner().alignHorizontalCenter();

        GridWidget.Adder adder = this.grid.setColumnSpacing(10).setRowSpacing(8).createAdder(2);

        GridWidget.Adder rewardsTitleAdder = new GridWidget().setRowSpacing(4).createAdder(1);
        rewardsTitleAdder.add(new TextWidget(REWARDS_TITLE.copy().formatted(Formatting.YELLOW), screen.client.textRenderer));
        adder.add(rewardsTitleAdder.getGridWidget(), 2, grid.copyPositioner().marginTop(24));

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
        generationOptionsBuilder.add(END_GENERATION, worldSettings::achieveToDo$isNullscapeEnabled, worldSettings::achieveToDo$setNullscapeEnabled).tooltip(END_GENERATION_INFO);
        WorldScreenOptionGrid generationOptionGrid = generationOptionsBuilder.build(widget -> adder.add(widget, 2));

        GridWidget.Adder lanTitleAdder = new GridWidget().setRowSpacing(4).createAdder(1);
        lanTitleAdder.add(new TextWidget(LAN_TITLE.copy().formatted(Formatting.YELLOW), screen.client.textRenderer));
        adder.add(lanTitleAdder.getGridWidget(), 2);

        WorldScreenOptionGrid.Builder lanOptionsBuilder = WorldScreenOptionGrid.builder(170).marginLeft(1);
        lanOptionsBuilder.add(COOPERATIVE_MODE, worldSettings::achieveToDo$isCooperativeModeEnabled, worldSettings::achieveToDo$setCooperativeModeEnabled).tooltip(COOPERATIVE_MODE_INFO);
        WorldScreenOptionGrid lanOptionsGrid = lanOptionsBuilder.build(widget -> adder.add(widget, 2));

        worldCreator.addListener(creator -> {
            rewardsOptionsGrid.refresh();
            generationOptionGrid.refresh();
            lanOptionsGrid.refresh();
        });
    }
}
