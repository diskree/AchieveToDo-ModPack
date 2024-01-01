package com.diskree.achievetodo.action;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public interface IGeneratedAdvancement {
    String getName();

    ItemStack getIcon();

    Text getTitle();

    Text getDescription();
}
