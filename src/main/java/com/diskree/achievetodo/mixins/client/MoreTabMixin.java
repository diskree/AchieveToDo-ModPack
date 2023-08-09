package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.BuildConfig;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(CreateWorldScreen.MoreTab.class)
public class MoreTabMixin {

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget$AdditionHelper;add(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;", ordinal = 2))
    private void initInject(Args args) {
        if (!BuildConfig.DEBUG) {
            ButtonWidget buttonWidget = args.get(0);
            buttonWidget.active = false;
            buttonWidget.setTooltip(Tooltip.create(Text.translatable("feature.not_supported")));
        }
    }
}
