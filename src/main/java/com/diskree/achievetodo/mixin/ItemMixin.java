package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.AchievementHardcoreMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "getMaxUseTime", at = @At("HEAD"), cancellable = true)
    private void getMaxUseTimeInject(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack != null && stack.getItem() != null && stack.getItem().isFood() && AchievementHardcoreMod.isFoodBlocked(stack.getItem().getFoodComponent())) {
            cir.setReturnValue(9);
        }
    }

    @Inject(method = "finishUsing", at = @At("HEAD"), cancellable = true)
    private void finishUsingInject(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (stack != null && stack.getItem() != null && stack.getItem().isFood() && user instanceof PlayerEntity && AchievementHardcoreMod.isFoodBlocked(stack.getItem().getFoodComponent())) {
            MinecraftClient.getInstance().options.useKey.setPressed(false);
            AchievementHardcoreMod.showFoodBlockedDescription(stack.getItem().getFoodComponent());
            cir.setReturnValue(stack);
        }
    }
}
