package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.advancements.AchieveToDoToast;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Deque;

@Mixin(ToastManager.class)
public class ToastManagerMixin {

    @Shadow
    @Final
    private Deque<Toast> toastQueue;

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    public void addInject(Toast t, CallbackInfo ci) {
        if (t instanceof AchieveToDoToast toast) {
            for (Toast qt : toastQueue) {
                if (qt instanceof AchieveToDoToast queueToast) {
                    if (queueToast.blockedAction == toast.blockedAction) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}
