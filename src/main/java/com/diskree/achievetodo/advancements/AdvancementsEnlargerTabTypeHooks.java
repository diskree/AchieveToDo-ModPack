package com.diskree.achievetodo.advancements;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public interface AdvancementsEnlargerTabTypeHooks {
    void drawBackground(MatrixStack matrices, DrawableHelper drawable, int x, int y, boolean selected, int index);

    void drawIcon(MatrixStack matrices, int x, int y, int index, ItemRenderer itemRenderer, ItemStack icon);

    boolean isClickOnTab(int screenX, int screenY, int index, double mouseX, double mouseY);
}
