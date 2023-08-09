package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.server.AchieveToDoServer;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

    @Shadow @Final private MinecraftClient client;

    @Unique
    private boolean isActionLocked() {
        BlockedAction action = AchieveToDoServer.getBlockedActionFromAdvancement(advancement);
        return action != null && !action.isUnblocked(client.player) && (progress == null || !progress.isDone());
    }

    @ModifyArg(method = "renderWidgets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;II)V"), index = 0)
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
        return tab.getScreen().height - AchieveToDoServer.ADVANCEMENTS_SCREEN_MARGIN * 2 - 3 * 9;
    }
}
