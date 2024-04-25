package com.diskree.achievetodo.mixin.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.blocked_actions.BlockedActionType;
import com.diskree.achievetodo.blocked_actions.datagen.AdvancementsGenerator;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementWidget.class)
public abstract class AdvancementWidgetMixin {

    @Unique
    private boolean isMystified() {
        BlockedActionType blockedAction = BlockedActionType.map(advancement);
        if (blockedAction == null || blockedAction.isUnblocked(client.player) || progress == null) {
            return false;
        }
        CriterionProgress demystifiedProgress = progress.getCriterionProgress(AdvancementsGenerator.BLOCKED_ACTION_DEMYSTIFIED_CRITERION_PREFIX + blockedAction.getName());
        return demystifiedProgress != null && !demystifiedProgress.isObtained();
    }

    @Shadow
    @Final
    private PlacedAdvancement advancement;

    @Shadow
    private @Nullable AdvancementProgress progress;

    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyArg(
            method = "renderWidgets",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;II)V"
            ),
            index = 0
    )
    private ItemStack renderWidgetsModifyIcon(ItemStack stack) {
        if (isMystified()) {
            return new ItemStack(AchieveToDo.MYSTIFIED_BLOCKED_ACTION_LABEL_ITEM);
        }
        return stack;
    }

    @Inject(
            method = "shouldRender",
            at = @At("RETURN"),
            cancellable = true
    )
    private void shouldRenderInject(int originX, int originY, int mouseX, int mouseY, @NotNull CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() && !isMystified());
    }

    @Redirect(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/advancement/AdvancementRequirements;getLength()I"
            )
    )
    public int initRedirect(AdvancementRequirements instance) {
        BlockedActionType blockedAction = BlockedActionType.map(advancement);
        if (blockedAction != null) {
            return blockedAction.getUnblockAdvancementsCount();
        }
        return advancement.getAdvancement().requirements().getLength();
    }
}
