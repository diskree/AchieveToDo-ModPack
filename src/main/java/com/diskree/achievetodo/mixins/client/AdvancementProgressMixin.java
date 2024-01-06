package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.action.BlockedActionType;
import com.diskree.achievetodo.datagen.AdvancementsGenerator;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.client.MinecraftClient;
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

    @Unique
    private int getActionUnblockAdvancementsCount() {
        if (requirements.requirements().size() == 2) {
            List<String> criteria = requirements.requirements().get(0);
            if (criteria != null && !criteria.isEmpty()) {
                String maybeDemystifiedCriterion = criteria.get(0);
                if (maybeDemystifiedCriterion != null && maybeDemystifiedCriterion.startsWith(AdvancementsGenerator.BLOCKED_ACTION_DEMYSTIFIED_CRITERION_PREFIX)) {
                    BlockedActionType blockedAction = BlockedActionType.map(maybeDemystifiedCriterion.split(AdvancementsGenerator.BLOCKED_ACTION_DEMYSTIFIED_CRITERION_PREFIX)[1]);
                    if (blockedAction != null) {
                        return blockedAction.getUnblockAdvancementsCount();
                    }
                }
            }
        }
        return -1;
    }

    @Shadow
    private AdvancementRequirements requirements;

    @Inject(method = "getProgressBarPercentage", at = @At("HEAD"), cancellable = true)
    public void getProgressBarPercentageInject(CallbackInfoReturnable<Float> cir) {
        float actionUnblockAdvancementsCount = getActionUnblockAdvancementsCount();
        if (actionUnblockAdvancementsCount != -1) {
            cir.setReturnValue(Math.min(actionUnblockAdvancementsCount, AchieveToDo.getScore(MinecraftClient.getInstance().player)) / actionUnblockAdvancementsCount);
        }
    }

    @Inject(method = "getProgressBarFraction", at = @At("HEAD"), cancellable = true)
    public void getProgressBarFractionInject(CallbackInfoReturnable<Text> cir) {
        int actionUnblockAdvancementsCount = getActionUnblockAdvancementsCount();
        if (actionUnblockAdvancementsCount != -1) {
            cir.setReturnValue(Text.translatable(
                    "advancements.progress",
                    Math.min(actionUnblockAdvancementsCount, AchieveToDo.getScore(MinecraftClient.getInstance().player)),
                    actionUnblockAdvancementsCount
            ));
        }
    }
}