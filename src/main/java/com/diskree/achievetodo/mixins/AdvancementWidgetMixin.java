package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementWidget.class)
public abstract class AdvancementWidgetMixin {

    @Shadow
    @Final
    private Advancement advancement;

    @Shadow
    private @Nullable AdvancementProgress progress;

    @Shadow
    @Final
    private AdvancementTab tab;

    @ModifyArg(method = "renderWidgets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;II)V"), index = 0)
    private ItemStack renderWidgetsModifyIcon(ItemStack stack) {
        if (isActionLocked()) {
            return new ItemStack(AchieveToDoMod.LOCKED_ACTION_ITEM);
        }
        return stack;
    }

    @Inject(method = "shouldRender", at = @At("RETURN"), cancellable = true)
    private void shouldRenderInject(int originX, int originY, int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() && !isActionLocked());
    }

    @ModifyConstant(method = "drawTooltip", constant = @Constant(intValue = 200), require = 3)
    public int drawTooltipModifyTextureWidth(int constant) {
        return 256;
    }

    @ModifyConstant(method = "drawTooltip", constant = @Constant(intValue = 113), require = 1)
    public int drawTooltipModifyHeight(int constant) {
        return tab.getScreen().height - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN * 2 - 3 * 9;
    }

    private boolean isActionLocked() {
        BlockedAction action = AchieveToDoMod.getBlockedActionFromAdvancement(advancement);
        return action != null && !action.isUnblocked() && (progress == null || !progress.isDone());
    }
}
