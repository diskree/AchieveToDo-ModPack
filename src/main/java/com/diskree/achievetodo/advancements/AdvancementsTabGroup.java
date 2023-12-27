package com.diskree.achievetodo.advancements;

import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.diskree.achievetodo.advancements.AdvancementsTab.*;

public enum AdvancementsTabGroup {
    LEFT(
            AdvancementTabType.LEFT,
            List.of(
                    BIOMES,
                    ADVENTURE,
                    WEAPONRY,
                    HUSBANDRY,
                    MONSTERS
            )
    ),
    TOP(
            AdvancementTabType.ABOVE,
            List.of(
                    MINING,
                    BUILDING,
                    FARMING,
                    NETHER,
                    END
            )
    ),
    RIGHT(
            AdvancementTabType.RIGHT,
            List.of(
                    BLOCKED_ACTIONS,
                    HINTS,
                    STATISTICS,
                    BACAP
            )
    ),
    BOTTOM(
            AdvancementTabType.BELOW,
            List.of(
                    REDSTONE,
                    POTION,
                    ENCHANTING,
                    CHALLENGES
            ));

    private final AdvancementTabType tabType;
    private final List<AdvancementsTab> tabs;

    AdvancementsTabGroup(AdvancementTabType tabType, List<AdvancementsTab> tabs) {
        this.tabType = tabType;
        this.tabs = tabs;
    }

    public static AdvancementTabType getTabType(@NotNull AdvancementsTab tab) {
        for (AdvancementsTabGroup location : AdvancementsTabGroup.values()) {
            for (AdvancementsTab advancementsTab : location.tabs) {
                if (advancementsTab == tab) {
                    return location.tabType;
                }
            }
        }
        throw new IllegalArgumentException("Advancements group not found for " + tab.name());
    }

    public static int getTabOrder(@NotNull AdvancementsTab tab) {
        for (AdvancementsTabGroup location : AdvancementsTabGroup.values()) {
            List<AdvancementsTab> advancementsTabs = location.tabs;
            for (int i = 0; i < advancementsTabs.size(); i++) {
                if (advancementsTabs.get(i) == tab) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Advancements group not found for " + tab.name());
    }
}
