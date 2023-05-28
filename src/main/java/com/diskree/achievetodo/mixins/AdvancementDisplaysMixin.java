package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import it.unimi.dsi.fastutil.Stack;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplays;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(AdvancementDisplays.class)
public class AdvancementDisplaysMixin {

    @Inject(method = "shouldDisplay(Lnet/minecraft/advancement/Advancement;Lit/unimi/dsi/fastutil/Stack;Ljava/util/function/Predicate;Lnet/minecraft/advancement/AdvancementDisplays$ResultConsumer;)Z", at = @At("HEAD"), cancellable = true)
    private static void calculateDisplayInject(Advancement advancement, Stack<AdvancementDisplays.Status> statuses, Predicate<Advancement> donePredicate, AdvancementDisplays.ResultConsumer consumer, CallbackInfoReturnable<Boolean> cir) {
        if (AchieveToDoMod.getBlockedActionFromAdvancement(advancement) != null) {
            statuses.push(AdvancementDisplays.Status.SHOW);
            for (Advancement child : advancement.getChildren()) {
                AdvancementDisplays.shouldDisplay(child, statuses, donePredicate, consumer);
            }
            consumer.accept(advancement, true);
            cir.setReturnValue(true);
        }
    }
}
