package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.client.util.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Session.class)
public class SessionMixin {

    @Inject(method = "getUsername", at = @At("HEAD"), cancellable = true)
    private void getLootTableInject(CallbackInfoReturnable<String> cir) {
        if (AchieveToDoMod.DEBUG) {
            cir.setReturnValue("diskree");
        }
    }

}
