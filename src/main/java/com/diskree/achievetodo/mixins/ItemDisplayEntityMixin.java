package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.ItemDisplayEntityImpl;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DisplayEntity.ItemDisplayEntity.class)
public abstract class ItemDisplayEntityMixin implements ItemDisplayEntityImpl {
    @Shadow
    abstract void setItemStack(ItemStack stack);

    @Override
    public void publicSetItemStack(ItemStack stack) {
        setItemStack(stack);
    }
}
