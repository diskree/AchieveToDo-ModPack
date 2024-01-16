package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.injection.UsableItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShearsItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin implements UsableItem {

    @Unique
    private boolean isCanUseOnBlockChecking;

    @Override
    public boolean achieveToDo$canUse(PlayerEntity player, BlockHitResult blockHitResult) {
        isCanUseOnBlockChecking = true;
        boolean canUseOnBlock = useOnBlock(new ItemUsageContext(player.getWorld(), player, null, null, blockHitResult)) == null;
        isCanUseOnBlockChecking = false;
        return canUseOnBlock;
    }

    @Shadow
    public abstract ActionResult useOnBlock(ItemUsageContext context);

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemUsageContext;getPlayer()Lnet/minecraft/entity/player/PlayerEntity;", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    public void returnOnUse(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (isCanUseOnBlockChecking) {
            cir.setReturnValue(null);
        }
    }
}
