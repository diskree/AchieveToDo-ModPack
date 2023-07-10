package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.BuildConfig;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectWorldScreen.class)
public class SelectWorldScreenMixin {

    @Shadow
    private ButtonWidget recreateButton;

    @Inject(method = "init", at = @At("RETURN"))
    private void initInject(CallbackInfo ci) {
        if (!BuildConfig.DEBUG) {
            recreateButton.setTooltip(Tooltip.create(Text.translatable("feature.not_supported")));
        }
    }

    @Inject(method = "worldSelected", at = @At("RETURN"))
    public void worldSelectedInject(boolean active, boolean deleteButtonActive, CallbackInfo ci) {
        if (!BuildConfig.DEBUG) {
            recreateButton.active = false;
        }
    }
}
