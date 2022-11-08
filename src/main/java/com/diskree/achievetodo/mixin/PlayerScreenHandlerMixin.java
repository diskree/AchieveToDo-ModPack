package com.diskree.achievetodo.mixin;

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

    @Inject(method = "transferSlot", at = @At("HEAD"), cancellable = true)
    public void transferSlotInject(PlayerEntity player, int index, CallbackInfoReturnable<ItemStack> cir) {
        Slot slot = ((ScreenHandler) (Object) this).slots.get(index);
        if (slot.hasStack()) {
            ItemStack stack = slot.getStack();
            if (stack != null && AchieveToDoMod.isEquipmentBlocked(stack.getItem())) {
                cir.setReturnValue(ItemStack.EMPTY);
            }
        }
    }
}
