package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.BuildConfig;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.world.WorldCreator;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.function.Consumer;

@Mixin(CreateWorldScreen.GameTab.class)
public class GameTabMixin {

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/GridWidget$AdditionHelper;add(Lnet/minecraft/client/gui/widget/Widget;)Lnet/minecraft/client/gui/widget/Widget;", ordinal = 0))
    private void initInject(Args args) {
        if (!BuildConfig.DEBUG) {
            CyclingButtonWidget<Boolean> buttonWidget = args.get(0);
            buttonWidget.active = false;
            buttonWidget.setValue(false);
            buttonWidget.setTooltip(Tooltip.create(Text.translatable("feature.not_supported")));
        }
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/world/WorldCreator;method_48712(Ljava/util/function/Consumer;)V", ordinal = 3))
    public void initRedirect(WorldCreator instance, Consumer<WorldCreator> consumer) {
        if (BuildConfig.DEBUG) {
            instance.method_48712(consumer);
        }
    }
}
