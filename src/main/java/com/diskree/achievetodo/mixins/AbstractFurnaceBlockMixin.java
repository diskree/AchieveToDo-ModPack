package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.injection.UsableBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractFurnaceBlock.class)
public class AbstractFurnaceBlockMixin implements UsableBlock {

    @Override
    public boolean achieveToDo$canUse(Item item) {
        return false;
    }
}
