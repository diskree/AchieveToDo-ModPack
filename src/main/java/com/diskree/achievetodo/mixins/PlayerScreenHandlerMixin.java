package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin {

    @Inject(method = "quickMove", at = @At("HEAD"), cancellable = true)
    public void quickMoveInject(PlayerEntity player, int slot, CallbackInfoReturnable<ItemStack> cir) {
        Slot slot2 = ((ScreenHandler) (Object) this).slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack stack = slot2.getStack();
            if (stack != null && AchieveToDoMod.isEquipmentBlocked(stack.getItem())) {
                cir.setReturnValue(ItemStack.EMPTY);
            }
        }
    }
}
