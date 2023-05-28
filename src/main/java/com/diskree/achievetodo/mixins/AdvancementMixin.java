package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.advancement.Advancement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Advancement.class)
public class AdvancementMixin {

    @Inject(method = "getRequirementCount", at = @At("HEAD"), cancellable = true)
    public void getRequirementCountInject(CallbackInfoReturnable<Integer> cir) {
        BlockedAction action = AchieveToDoMod.getBlockedActionFromAdvancement((Advancement) (Object) this);
        if (action != null) {
            cir.setReturnValue(action.getUnblockAdvancementsCount());
        }
    }
}
