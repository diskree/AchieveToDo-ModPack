package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.server.AchieveToDoServer;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntityMixin {

    @Inject(method = "canTeleport", at = @At("HEAD"), cancellable = true)
    private static void canTeleportInject(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity playerEntity && AchieveToDoServer.isActionBlocked(playerEntity, BlockedAction.END_GATE)) {
            cir.setReturnValue(false);
        }
    }
}
