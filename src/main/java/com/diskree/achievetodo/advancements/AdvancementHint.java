package com.diskree.achievetodo.advancements;

import net.minecraft.advancement.Advancement;
import net.minecraft.item.ItemStack;

public record AdvancementHint(Advancement advancement, ItemStack hint) {
}
