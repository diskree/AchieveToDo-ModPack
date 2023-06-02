package com.diskree.achievetodo.mixins;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Consumer;

@Mixin(CreateWorldScreen.GameTab.class)
public class GameTabMixin {

    @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference", "rawtypes", "unchecked"})
    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget$Adder;add(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;"))
    private void initInject(Args args) {
        Widget widget = args.get(0);
        if (widget instanceof CyclingButtonWidget button && button.getValue() instanceof Boolean) {
            button.active = false;
            button.setValue(false);
            button.setTooltip(Tooltip.of(Text.translatable("menu.shareToLan.info")));
        }
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/world/WorldCreator;addListener(Ljava/util/function/Consumer;)V", ordinal = 3))
    public void initRedirect(WorldCreator instance, Consumer<WorldCreator> listener) {
    }

}
