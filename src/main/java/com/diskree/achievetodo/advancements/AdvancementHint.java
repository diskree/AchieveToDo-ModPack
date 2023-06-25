package com.diskree.achievetodo.advancements;

import net.minecraft.item.ItemStack;

public record AdvancementHint(ItemStack tab, ItemStack advancement, ItemStack hint, boolean dropHint) {
}
