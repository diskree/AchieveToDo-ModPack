package com.diskree.achievetodo.mixins;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionUtil.class)
public class PotionUtilMixin {

    @Inject(method = "getColor(Lnet/minecraft/item/ItemStack;)I", at = @At("HEAD"), cancellable = true)
    private static void getColorInject(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        Potion potion = PotionUtil.getPotion(stack);
        if (potion == Potions.AWKWARD) {
            cir.setReturnValue(0xe0757e);
        } else if (potion == Potions.MUNDANE) {
            cir.setReturnValue(0xffffff);
        } else if (potion == Potions.THICK) {
            cir.setReturnValue(0xf9d49c);
        }
    }
}
