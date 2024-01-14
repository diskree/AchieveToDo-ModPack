package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.action.BlockedActionType;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "moveToWorld", at = @At("HEAD"), cancellable = true)
    public void moveToWorldInject(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (AchieveToDo.isActionBlocked(player, BlockedActionType.findBlockedDimension(destination.getRegistryKey()))) {
            cir.setReturnValue(player);
        }
    }
}
