package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Inject(method = "setSprinting", at = @At("HEAD"), cancellable = true)
    public void setSprintingInject(boolean sprinting, CallbackInfo ci) {
        if (!((PlayerEntity) ((Object) this)).isTouchingWater() && AchieveToDoMod.isActionBlocked(BlockedAction.SPRINT)) {
            ci.cancel();
        }
    }
}
