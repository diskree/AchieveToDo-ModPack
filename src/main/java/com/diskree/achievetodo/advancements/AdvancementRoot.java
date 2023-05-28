package com.diskree.achievetodo.advancements;

import net.minecraft.client.gui.screen.advancement.AdvancementTabType;

public enum AdvancementRoot {
    BIOMES(AdvancementTabType.LEFT, 0),
    ADVENTURE(AdvancementTabType.LEFT, 1),
    WEAPONRY(AdvancementTabType.LEFT, 2),
    HUSBANDRY(AdvancementTabType.LEFT, 3),
    MONSTERS(AdvancementTabType.LEFT, 4),

    MINING(AdvancementTabType.ABOVE, 0),
    BUILDING(AdvancementTabType.ABOVE, 1),
    FARMING(AdvancementTabType.ABOVE, 2),
    NETHER(AdvancementTabType.ABOVE, 3),
    END(AdvancementTabType.ABOVE, 4),

    ACTION(AdvancementTabType.RIGHT, 0),
    STATISTICS(AdvancementTabType.RIGHT, 1),
    BACAP(AdvancementTabType.RIGHT, 2),

    REDSTONE(AdvancementTabType.BELOW, 0),
    POTION(AdvancementTabType.BELOW, 1),
    ENCHANTING(AdvancementTabType.BELOW, 2),
    CHALLENGES(AdvancementTabType.BELOW, 3);

    private final AdvancementTabType tabGravity;
    private final int order;

    AdvancementRoot(AdvancementTabType tabGravity, int order) {
        this.tabGravity = tabGravity;
        this.order = order;
    }

    public AdvancementTabType getTabType() {
        return tabGravity;
    }

    public int getOrder() {
        return order;
    }
}
