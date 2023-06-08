package com.diskree.achievetodo.mixins;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin {

    @Shadow
    @Final
    private static Text SHARE_TO_LAN_TEXT;

    @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference"})
    @ModifyArgs(method = "initWidgets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget$Adder;add(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;"))
    private void initWidgetsInject(Args args) {
        ButtonWidget lanButton = args.get(0);
        if (lanButton.getMessage() == SHARE_TO_LAN_TEXT) {
//            lanButton.active = false;
            lanButton.setTooltip(Tooltip.of(Text.translatable("menu.shareToLan.info")));
        }
    }
}
