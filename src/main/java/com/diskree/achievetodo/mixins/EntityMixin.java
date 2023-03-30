package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "dropStack(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/ItemEntity;", at = @At("HEAD"), cancellable = true)
    public void dropStackInject(ItemStack itemStack, float yOffset, CallbackInfoReturnable<ItemEntity> cir) {
        if (itemStack != null && itemStack.getItem() == Items.TOTEM_OF_UNDYING && AchieveToDoMod.isActionBlocked(BlockedAction.DROP_TOTEM)) {
            cir.setReturnValue(null);
        }
    }
}
