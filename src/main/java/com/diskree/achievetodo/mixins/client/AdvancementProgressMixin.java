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

    @Shadow
    private AdvancementRequirements requirements;

    @Unique
    private int getActionUnblockAdvancementsCount() {
        if (requirements.requirements().size() == 1) {
            List<String> requirement = requirements.requirements().get(0);
            if (requirement != null && requirement.size() == 1) {
                String criteria = requirement.get(0);
                if (criteria != null && criteria.startsWith(AdvancementsGenerator.BLOCKED_ACTION_CRITERIA_NAME_PREFIX)) {
                    BlockedActionType action = BlockedActionType.map(criteria.split(AdvancementsGenerator.BLOCKED_ACTION_CRITERIA_NAME_PREFIX)[1]);
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