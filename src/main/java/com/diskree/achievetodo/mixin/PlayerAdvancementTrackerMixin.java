package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancementTracker.class)
public class PlayerAdvancementTrackerMixin {

    @Inject(method = "canSee", at = @At("HEAD"), cancellable = true)
    public void canSeeInject(Advancement advancement, CallbackInfoReturnable<Boolean> cir) {
        if (AchieveToDoMod.getBlockedActionFromAdvancement(advancement) != null) {
            cir.setReturnValue(true);
        }
    }
}
