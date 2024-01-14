package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.action.BlockedActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void jumpInject(CallbackInfo ci) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        if (!playerEntity.isTouchingWater() && AchieveToDo.isActionBlocked(playerEntity, BlockedActionType.JUMP)) {
            ci.cancel();
        }
    }

    @Inject(method = "canEquip", at = @At("HEAD"), cancellable = true)
    public void canEquipInject(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (AchieveToDo.isActionBlocked(player, BlockedActionType.findBlockedEquipment(stack.getItem()))) {
            cir.setReturnValue(false);
        }
    }
}
