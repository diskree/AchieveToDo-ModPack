package com.diskree.achievetodo.mixins;

import net.minecraft.entity.boss.dragon.EnderDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin {

    @Shadow
    private boolean previouslyKilled;

    @Inject(method = "generateNewEndGateway", at = @At("HEAD"))
    public void generateNewEndGatewayInject(CallbackInfo ci) {
        previouslyKilled = true;
    }
}
