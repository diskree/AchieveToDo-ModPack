package com.diskree.achievetodo

import net.minecraft.text.Text

enum class BlockedActionType(private val unblockPopupTitle: String) {
    FOOD("unblock.food"),
    ACTION("unblock.action"),
    BLOCK("unblock.block"),
    TOOLS("unblock.tools"),
    ARMOR("unblock.armor"),
    PORTAL("unblock.portal"),
    VILLAGER("unblock.villager");

    fun getUnblockPopupTitle(): String {
        return Text.translatable(unblockPopupTitle).string
    }
}
