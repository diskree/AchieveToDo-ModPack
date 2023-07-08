package com.diskree.achievetodo;

public enum BlockedActionType {

    FOOD("unblock.food"),
    ACTION("unblock.action"),
    BLOCK("unblock.block"),
    TOOLS("unblock.tools"),
    ARMOR("unblock.armor"),
    PORTAL("unblock.portal"),
    VILLAGER("unblock.villager");

    final String unblockPopupTitle;

    BlockedActionType(String unblockPopupTitle) {
        this.unblockPopupTitle = unblockPopupTitle;
    }

    public String getUnblockPopupTitle() {
        return unblockPopupTitle;
    }
}
