package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "isAutoJumpEnabled", at = @At("HEAD"), cancellable = true)
    private void isAutoJumpEnabledInject(CallbackInfoReturnable<Boolean> cir) {
        ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) (Object) this;
        if (AchieveToDo.isActionBlocked(clientPlayerEntity, BlockedAction.JUMP, true)) {
            cir.setReturnValue(false);
        }
    }
}
