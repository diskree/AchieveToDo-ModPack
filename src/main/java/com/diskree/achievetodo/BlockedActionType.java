package com.diskree.achievetodo;

import net.minecraft.text.Text;

public enum BlockedActionType {
    FOOD("unblock.food"),
    ACTION("unblock.action"),
    BLOCK("unblock.block"),
    TOOLS("unblock.tools"),
    ARMOR("unblock.armor"),
    PORTAL("unblock.portal"),
    VILLAGER("unblock.villager");

    private final String unlockPopupTitle;

    BlockedActionType(String unlockPopupTitle) {
        this.unlockPopupTitle = unlockPopupTitle;
    }

    public String getUnlockPopupTitle() {
        return Text.translatable(unlockPopupTitle).getString();
    }
}
