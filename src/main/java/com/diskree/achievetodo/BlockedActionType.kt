package com.diskree.achievetodo

import net.minecraft.text.Text

enum class BlockedActionType(private val unlockPopupTitle: String) {
    FOOD("unblock.food"),
    ACTION("unblock.action"),
    BLOCK("unblock.block"),
    TOOLS("unblock.tools"),
    ARMOR("unblock.armor"),
    PORTAL("unblock.portal"),
    VILLAGER("unblock.villager");

    fun getUnlockPopupTitle(): String {
        return Text.translatable(unlockPopupTitle).string
    }
}
