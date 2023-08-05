package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.client.WorldCreatorImpl;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.SwitchGrid;
import net.minecraft.client.gui.tab.GridWidgetTab;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.world.WorldCreator;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CreateWorldAchieveToDoTab extends GridWidgetTab {

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

    public CreateWorldAchieveToDoTab(CreateWorldScreen screen) {
        super(Text.of("AchieveToDo"));
        if (screen == null || screen.getClient() == null) {
            return;
        }
        WorldCreator worldCreator = screen.getWorldCreator();
        WorldCreatorImpl worldSettings = (WorldCreatorImpl) worldCreator;

        this.grid.getDefaultSettings().alignHorizontallyCenter();

        GridWidget.AdditionHelper adder = this.grid.setColumnSpacing(10).setRowSpacing(8).createAdditionHelper(2);

        GridWidget.AdditionHelper rewardsTitleAdder = new GridWidget().setRowSpacing(4).createAdditionHelper(1);
        rewardsTitleAdder.add(new TextWidget(REWARDS_TITLE.copy().formatted(Formatting.YELLOW), screen.getClient().textRenderer));
        adder.add(rewardsTitleAdder.getWidget(), 2);

        SwitchGrid.Builder rewardsOptionsBuilder = SwitchGrid.builder(170).leftPadding(1);
        rewardsOptionsBuilder.button(ITEM_REWARDS, worldSettings::achieveToDo$isItemRewardsEnabled, worldSettings::achieveToDo$setItemRewardsEnabled).info(ITEM_REWARDS_INFO);
        rewardsOptionsBuilder.button(EXPERIENCE_REWARDS, worldSettings::achieveToDo$isExperienceRewardsEnabled, worldSettings::achieveToDo$setExperienceRewardsEnabled).info(EXPERIENCE_REWARDS_INFO);
        rewardsOptionsBuilder.button(TROPHY_REWARDS, worldSettings::achieveToDo$isTrophyRewardsEnabled, worldSettings::achieveToDo$setTrophyRewardsEnabled).info(TROPHY_REWARDS_INFO);
        SwitchGrid rewardsOptionsGrid = rewardsOptionsBuilder.build(widget -> adder.add(widget, 2));

        GridWidget.AdditionHelper generationTitleAdder = new GridWidget().setRowSpacing(4).createAdditionHelper(1);
        generationTitleAdder.add(new TextWidget(GENERATION_TITLE.copy().formatted(Formatting.YELLOW), screen.getClient().textRenderer));
        adder.add(generationTitleAdder.getWidget(), 2);

        SwitchGrid.Builder generationOptionsBuilder = SwitchGrid.builder(170).leftPadding(1);
        generationOptionsBuilder.button(OVERWORLD_GENERATION, worldSettings::achieveToDo$isTerralithEnabled, worldSettings::achieveToDo$setTerralithEnabled).info(OVERWORLD_GENERATION_INFO);
        generationOptionsBuilder.button(NETHER_GENERATION, worldSettings::achieveToDo$isAmplifiedNetherEnabled, worldSettings::achieveToDo$setAmplifiedNetherEnabled).info(NETHER_GENERATION_INFO);
        generationOptionsBuilder.button(END_GENERATION, worldSettings::achieveToDo$isNullscapeEnabled, worldSettings::achieveToDo$setNullscapeEnabled).info(END_GENERATION_INFO);
        SwitchGrid generationOptionGrid = generationOptionsBuilder.build(widget -> adder.add(widget, 2));

        worldCreator.method_48712(worldCreator1 -> {
            rewardsOptionsGrid.updateStates();
            generationOptionGrid.updateStates();
        });
    }
}
