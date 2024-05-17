package com.diskree.achievetodo.mixin.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.blocked_actions.BlockedActionType;
import com.diskree.achievetodo.blocked_actions.datagen.AdvancementsGenerator;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.text.Text;
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
        if (requirements.requirements().size() == 2) {
            List<String> criteria = requirements.requirements().get(0);
            if (criteria != null && !criteria.isEmpty()) {
                String maybeDemystifiedCriterion = criteria.get(0);
                String prefix = AdvancementsGenerator.BLOCKED_ACTION_DEMYSTIFIED_CRITERION_PREFIX;
                if (maybeDemystifiedCriterion != null &&
                        maybeDemystifiedCriterion.startsWith(prefix)) {
                    BlockedActionType blockedAction = BlockedActionType.map(maybeDemystifiedCriterion.split(prefix)[1]);
                    if (blockedAction != null) {
                        return blockedAction.getUnblockAdvancementsCount();
                    }
                }
            }
        }
        return -1;
    }

    @Inject(
            method = "getProgressBarPercentage",
            at = @At("HEAD"),
            cancellable = true
    )
    public void overrideBlockedActionProgress(CallbackInfoReturnable<Float> cir) {
        float actionUnblockAdvancementsCount = getActionUnblockAdvancementsCount();
        if (actionUnblockAdvancementsCount != -1) {
            cir.setReturnValue(
                    Math.min(actionUnblockAdvancementsCount, AchieveToDo.getScore() / actionUnblockAdvancementsCount)
            );
        }
    }

    @Inject(
            method = "getProgressBarFraction",
            at = @At("HEAD"),
            cancellable = true
    )
    public void overrideBlockedActionProgressBarFraction(CallbackInfoReturnable<Text> cir) {
        int actionUnblockAdvancementsCount = getActionUnblockAdvancementsCount();
        if (actionUnblockAdvancementsCount != -1) {
            cir.setReturnValue(Text.translatable(
                    "advancements.progress",
                    Math.min(actionUnblockAdvancementsCount, AchieveToDo.getScore()),
                    actionUnblockAdvancementsCount
            ));
        }
    }
}