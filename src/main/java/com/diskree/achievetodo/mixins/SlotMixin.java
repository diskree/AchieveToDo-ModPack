package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.entity.player.PlayerEntity;
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
        if (inventory instanceof PlayerInventory playerInventory && id >= 5 && id <= 8 && stack != null && AchieveToDo.isEquipmentBlocked(playerInventory.player, stack.getItem())) {
            cir.setReturnValue(stack);
        }
    }

    @Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
    public void canTakeItemsInject(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        if (playerEntity != null && inventory instanceof PlayerInventory && id >= 5 && id <= 8) {
            ItemStack stack = playerEntity.playerScreenHandler.getCursorStack();
            if (stack != null && AchieveToDo.isEquipmentBlocked(playerEntity, stack.getItem())) {
                cir.setReturnValue(false);
            }
        }
    }
}
