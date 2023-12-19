package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.action.BlockedActionType;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndGatewayBlockEntity.class)
public class EndGatewayBlockEntityMixin {

    @Inject(method = "canTeleport", at = @At("HEAD"), cancellable = true)
    private static void canTeleportInject(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        Entity teleportEntity = entity;
        if (teleportEntity instanceof EnderPearlEntity enderPearl) {
            teleportEntity = enderPearl.getOwner();
        }
        if (teleportEntity instanceof PlayerEntity playerEntity && AchieveToDo.isActionBlocked(playerEntity, BlockedActionType.END_GATEWAY)) {
            cir.setReturnValue(false);
        }
    }
}
