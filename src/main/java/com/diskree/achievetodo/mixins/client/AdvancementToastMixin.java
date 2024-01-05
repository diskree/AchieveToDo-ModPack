package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.action.BlockedActionCategory;
import com.diskree.achievetodo.action.BlockedActionType;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.AdvancementToast;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin {

    @Unique
    private BlockedActionCategory getBlockedActionCategory() {
        if (advancement == null) {
            return null;
        }
        BlockedActionType action = BlockedActionType.map(advancement.id());
        return action != null ? action.getCategory() : null;
    }

    @Shadow
    @Final
    private AdvancementEntry advancement;

    @Redirect(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"))
    private void redirectBackgroundTexture(DrawContext instance, Identifier texture, int x, int y, int width, int height) {
        BlockedActionCategory category = getBlockedActionCategory();
        if (category != null) {
            instance.drawTexture(new Identifier(AchieveToDo.ID, "textures/gui/toast/blocked_action/" + category.getName() + ".png"), 0, 0, 0, 0, width, height);
        } else {
            instance.drawGuiTexture(texture, x, y, width, height);
        }
    }

    @ModifyArgs(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I"))
    private void modifyTitle(Args args) {
        BlockedActionCategory category = getBlockedActionCategory();
        if (category != null) {
            args.set(1, Text.translatable(category.getUnblockPopupTitle().getString()));
        }
    }
}
