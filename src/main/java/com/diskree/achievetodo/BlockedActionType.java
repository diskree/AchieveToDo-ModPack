package com.diskree.achievetodo;

public enum BlockedActionType {
    FOOD("Доступна еда"),
    ACTION("Доступно действие"),
    BLOCK("Доступен блок"),
    TOOLS("Доступны железные инструменты"),
    ARMOR("Доступна броня"),
    PORTAL("Доступен портал");

    private final String unlockPopupTitle;

    BlockedActionType(String unlockPopupTitle) {
        this.unlockPopupTitle = unlockPopupTitle;
    }

    public String getUnlockPopupTitle() {
        return unlockPopupTitle;
    }
}
