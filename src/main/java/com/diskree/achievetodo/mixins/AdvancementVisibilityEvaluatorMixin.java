package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import it.unimi.dsi.fastutil.Stack;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementVisibilityEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(AdvancementVisibilityEvaluator.class)
public abstract class AdvancementVisibilityEvaluatorMixin {

    @Shadow
    private static boolean method_48030(Advancement advancement, Stack<AdvancementVisibilityEvaluator.C_bfuhkkvt> stack, Predicate<Advancement> predicate, AdvancementVisibilityEvaluator.C_laxhphom c_laxhphom) {
        return false;
    }

    @Inject(method = "method_48030", at = @At("HEAD"), cancellable = true)
    private static void calculateDisplayInject(Advancement advancement, Stack<AdvancementVisibilityEvaluator.C_bfuhkkvt> stack, Predicate<Advancement> predicate, AdvancementVisibilityEvaluator.C_laxhphom c_laxhphom, CallbackInfoReturnable<Boolean> cir) {
        if (AchieveToDoMod.getBlockedActionFromAdvancement(advancement) != null) {
            stack.push(AdvancementVisibilityEvaluator.C_bfuhkkvt.SHOW);
            for (Advancement child : advancement.getChildren()) {
                //noinspection ResultOfMethodCallIgnored
                method_48030(child, stack, predicate, c_laxhphom);
            }
            c_laxhphom.accept(advancement, true);
            cir.setReturnValue(true);
        }
    }
}
