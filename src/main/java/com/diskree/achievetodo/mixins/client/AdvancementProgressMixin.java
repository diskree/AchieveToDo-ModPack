package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.BlockedAction;
import com.diskree.achievetodo.advancements.AdvancementRoot;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AdvancementProgress.class)
public abstract class AdvancementProgressMixin {

    @Shadow
    private AdvancementRequirements requirements;

    @Unique
    private int getActionUnblockAdvancementsCount() {
        if (requirements.requirements().size() == 1) {
            List<String> requirement = requirements.requirements().get(0);
            if (requirement != null && requirement.size() == 1) {
                String criteria = requirement.get(0);
                String actionPrefix = AdvancementRoot.ACTION.name().toLowerCase() + "_";
                if (criteria != null && criteria.startsWith(actionPrefix)) {
                    BlockedAction action = BlockedAction.map(criteria.split(actionPrefix)[1]);
                    if (action != null) {
                        return action.getUnblockAdvancementsCount();
                    }
                }
            }
        }
        return -1;
    }

    @Inject(method = "getProgressBarPercentage", at = @At("HEAD"), cancellable = true)
    public void getProgressBarPercentageInject(CallbackInfoReturnable<Float> cir) {
        float actionUnblockAdvancementsCount = getActionUnblockAdvancementsCount();
        if (actionUnblockAdvancementsCount != -1) {
            cir.setReturnValue(Math.min(actionUnblockAdvancementsCount, AchieveToDo.getScore(MinecraftClient.getInstance().player)) / actionUnblockAdvancementsCount);
        }
    }

    @Inject(method = "getProgressBarFraction", at = @At("HEAD"), cancellable = true)
    public void getProgressBarFractionInject(CallbackInfoReturnable<String> cir) {
        int actionUnblockAdvancementsCount = getActionUnblockAdvancementsCount();
        if (actionUnblockAdvancementsCount != -1) {
            cir.setReturnValue(Math.min(actionUnblockAdvancementsCount, AchieveToDo.getScore(MinecraftClient.getInstance().player)) + "/" + actionUnblockAdvancementsCount);
        }
    }
}