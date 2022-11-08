package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin {

    @Shadow
    @Final
    public Inventory inventory;

    @Shadow
    public int id;

    @Inject(method = "insertStack(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    public void insertStackInject(ItemStack stack, int count, CallbackInfoReturnable<ItemStack> cir) {
        if (inventory instanceof PlayerInventory && id >= 5 && id <= 8 && stack != null && AchieveToDoMod.isEquipmentBlocked(stack.getItem())) {
            cir.setReturnValue(stack);
        }
    }
}
