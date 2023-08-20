package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.BuildConfig;
import net.minecraft.client.gui.widget.LockButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LockButtonWidget.class)
public class LockButtonWidgetMixin {

    @Shadow
    private boolean locked;

    @Inject(method = "setLocked", at = @At("RETURN"))
    public void setLockedInject(boolean locked, CallbackInfo ci) {
        if (!BuildConfig.DEBUG) {
            this.locked = true;
        }
    }
}
