package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.BuildConfig;
import net.minecraft.client.gui.screen.OpenToLanScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(OpenToLanScreen.class)
public class OpenToLanScreenMixin {

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/OpenToLanScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;", ordinal = 0))
    private void disableGameModeButton(Args args) {
        if (!BuildConfig.DEBUG) {
            ClickableWidget buttonWidget = args.get(0);
            buttonWidget.active = false;
            buttonWidget.setTooltip(Tooltip.create(Text.translatable("feature.not_supported")));
        }
    }

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/OpenToLanScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;", ordinal = 1))
    private void disableAllowCheatsButton(Args args) {
        if (!BuildConfig.DEBUG) {
            ClickableWidget buttonWidget = args.get(0);
            buttonWidget.active = false;
            buttonWidget.setTooltip(Tooltip.create(Text.translatable("feature.not_supported")));
        }
    }
}
