package com.diskree.achievetodo.gui;

import com.diskree.achievetodo.BuildConfig;
import com.diskree.achievetodo.injection.WorldCreatorImpl;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.screen.world.WorldScreenOptionGrid;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class CreateWorldTab extends GridScreenTab {

    private static final boolean ALLOW_WORLD_GENERATION = true;

    private static final Text REWARDS_TITLE = Text.translatable("create_world.rewards.title");
    private static final Text ITEM_REWARDS = Text.translatable("create_world.rewards.item");
    private static final Text ITEM_REWARDS_INFO = Text.translatable("create_world.rewards.item.info");
    private static final Text EXPERIENCE_REWARDS = Text.translatable("create_world.rewards.experience");
    private static final Text EXPERIENCE_REWARDS_INFO = Text.translatable("create_world.rewards.experience.info");
    private static final Text TROPHY_REWARDS = Text.translatable("create_world.rewards.trophy");
    private static final Text TROPHY_REWARDS_INFO = Text.translatable("create_world.rewards.trophy.info");
    private static final Text WORLD_GENERATION_TITLE = Text.translatable("create_world.world_generation.title");
    private static final Text OVERWORLD_GENERATION = Text.translatable("create_world.world_generation.overworld");
    private static final Text OVERWORLD_GENERATION_INFO = Text.translatable("create_world.world_generation.overworld.info");
    private static final Text NETHER_GENERATION = Text.translatable("create_world.world_generation.nether");
    private static final Text NETHER_GENERATION_INFO = Text.translatable("create_world.world_generation.nether.info");
    private static final Text END_GENERATION = Text.translatable("create_world.world_generation.end");
    private static final Text END_GENERATION_INFO = Text.translatable("create_world.world_generation.end.info");
    private static final Text LAN_TITLE = Text.translatable("lanServer.title");
    private static final Text COOPERATIVE_MODE = Text.translatable("create_world.lan.cooperative_mode");
    private static final Text COOPERATIVE_MODE_INFO = Text.translatable("create_world.lan.cooperative_mode.info");

    private WorldScreenOptionGrid rewardsGrid;
    private WorldScreenOptionGrid worldGenerationGrid;
    private WorldScreenOptionGrid lanGrid;

    public CreateWorldTab(CreateWorldScreen screen) {
        super(Text.of(BuildConfig.MOD_NAME));
        if (screen == null || screen.client == null) {
            return;
        }
        WorldCreator worldCreator = screen.getWorldCreator();
        WorldCreatorImpl worldSettings = (WorldCreatorImpl) worldCreator;

        grid.getMainPositioner().alignHorizontalCenter();

        GridWidget.Adder rootContainer = grid.setColumnSpacing(10).setRowSpacing(8).createAdder(2);

        GridWidget.Adder rewardsTitleContainer = new GridWidget().setRowSpacing(4).createAdder(1);
        rewardsTitleContainer.add(new TextWidget(
                REWARDS_TITLE.copy().formatted(Formatting.YELLOW), 
                screen.client.textRenderer
        ));
        rootContainer.add(rewardsTitleContainer.getGridWidget(), 2, grid.copyPositioner().marginTop(24));

        WorldScreenOptionGrid.Builder rewardsGridBuilder = WorldScreenOptionGrid.builder(170).marginLeft(1);
        rewardsGridBuilder.add(
                ITEM_REWARDS, 
                worldSettings::achievetodo$isItemRewardsEnabled,
                worldSettings::achievetodo$setItemRewardsEnabled
        ).tooltip(ITEM_REWARDS_INFO);
        rewardsGridBuilder.add(
                EXPERIENCE_REWARDS, 
                worldSettings::achievetodo$isExperienceRewardsEnabled, 
                worldSettings::achievetodo$setExperienceRewardsEnabled
        ).tooltip(EXPERIENCE_REWARDS_INFO);
        rewardsGridBuilder.add(
                TROPHY_REWARDS,
                worldSettings::achievetodo$isTrophyRewardsEnabled, 
                worldSettings::achievetodo$setTrophyRewardsEnabled
        ).tooltip(TROPHY_REWARDS_INFO);
        rewardsGrid = rewardsGridBuilder.build(widget -> rootContainer.add(widget, 2));

        if (ALLOW_WORLD_GENERATION) {
            GridWidget.Adder customGenerationTitleContainer = new GridWidget().setRowSpacing(4).createAdder(1);
            customGenerationTitleContainer.add(new TextWidget(
                    WORLD_GENERATION_TITLE.copy().formatted(Formatting.YELLOW),
                    screen.client.textRenderer
            ));
            rootContainer.add(customGenerationTitleContainer.getGridWidget(), 2);

            WorldScreenOptionGrid.Builder worldGenerationGridBuilder = WorldScreenOptionGrid.builder(170).marginLeft(1);
            worldGenerationGridBuilder.add(
                    OVERWORLD_GENERATION, 
                    worldSettings::achievetodo$isTerralithEnabled,
                    worldSettings::achievetodo$setTerralithEnabled
            ).tooltip(OVERWORLD_GENERATION_INFO);
            worldGenerationGridBuilder.add(
                    NETHER_GENERATION, 
                    worldSettings::achievetodo$isAmplifiedNetherEnabled,
                    worldSettings::achievetodo$setAmplifiedNetherEnabled
            ).tooltip(NETHER_GENERATION_INFO);
            worldGenerationGridBuilder.add(
                    END_GENERATION, 
                    worldSettings::achievetodo$isNullscapeEnabled, 
                    worldSettings::achievetodo$setNullscapeEnabled
            ).tooltip(END_GENERATION_INFO);
            worldGenerationGrid = worldGenerationGridBuilder.build(widget -> rootContainer.add(widget, 2));
        }

        GridWidget.Adder lanTitleContainer = new GridWidget().setRowSpacing(4).createAdder(1);
        lanTitleContainer.add(new TextWidget(
                LAN_TITLE.copy().formatted(Formatting.YELLOW),
                screen.client.textRenderer
        ));
        rootContainer.add(lanTitleContainer.getGridWidget(), 2);

        WorldScreenOptionGrid.Builder lanGridBuilder = WorldScreenOptionGrid.builder(170).marginLeft(1);
        lanGridBuilder.add(
                COOPERATIVE_MODE, 
                worldSettings::achievetodo$isCooperativeModeEnabled,
                worldSettings::achievetodo$setCooperativeModeEnabled
        ).tooltip(COOPERATIVE_MODE_INFO);
        lanGrid = lanGridBuilder.build(widget -> rootContainer.add(widget, 2));

        worldCreator.addListener(creator -> {
            if (rewardsGrid != null) {
                rewardsGrid.refresh();
            }
            if (worldGenerationGrid != null) {
                worldGenerationGrid.refresh();
            }
            if (lanGrid != null) {
                lanGrid.refresh();
            }
        });
    }
}
