package com.diskree.achievetodo.action;

import net.minecraft.text.Text;

public enum BlockedActionCategory {
    ACTION,
    FOOD,
    ITEM,
    BLOCK,
    TOOL,
    EQUIPMENT,
    DIMENSION,
    VILLAGER;

    public Text getUnblockPopupTitle() {
        return Text.translatable("unblock." + getName());
    }

    public String getName() {
        return name().toLowerCase();
    }
}
