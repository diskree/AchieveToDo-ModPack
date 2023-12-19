package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.action.BlockedActionType;
import com.diskree.achievetodo.client.AchieveToDoClient;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementWidget.class)
public abstract class AdvancementWidgetMixin {

    @Shadow
    @Final
    private PlacedAdvancement advancement;

    @Shadow
    private @Nullable AdvancementProgress progress;

    @Shadow
    @Final
    private AdvancementTab tab;

    @Shadow
    @Final
    private MinecraftClient client;

    @Unique
    private boolean isActionLocked() {
        BlockedActionType action = AchieveToDo.getBlockedActionFromAdvancement(advancement);
        return action != null && !action.isUnblocked(client.player) && (progress == null || !progress.isDone());
    }

    @ModifyArg(method = "renderWidgets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;II)V"), index = 0)
    private ItemStack renderWidgetsModifyIcon(ItemStack stack) {
        if (isActionLocked()) {
            return new ItemStack(AchieveToDo.LOCKED_ACTION_ITEM);
        }
        return stack;
    }

    @Inject(method = "shouldRender", at = @At("RETURN"), cancellable = true)
    private void shouldRenderInject(int originX, int originY, int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() && !isActionLocked());
    }

    @ModifyConstant(method = "drawTooltip", constant = @Constant(intValue = 113), require = 1)
    public int drawTooltipModifyHeight(int constant) {
        return tab.getScreen().height - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2 - 3 * 9;
    }

    @Inject(method = "renderLines", at = @At(value = "HEAD"), cancellable = true)
    public void renderLinesInject(DrawContext context, int x, int y, boolean border, CallbackInfo ci) {
        if (tab.getRoot() != null && AchieveToDo.ADVANCEMENTS_SEARCH.equals(tab.getRoot().getAdvancementEntry().id())) {
            ci.cancel();
        }
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/AdvancementRequirements;getLength()I"))
    public int initRedirect(AdvancementRequirements instance) {
        BlockedActionType action = AchieveToDo.getBlockedActionFromAdvancement(advancement);
        if (action != null) {
            return action.getUnblockAdvancementsCount();
        }
        return advancement.getAdvancement().requirements().getLength();
    }
}
