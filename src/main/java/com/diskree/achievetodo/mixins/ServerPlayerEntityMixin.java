package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Inject(method = "moveToWorld", at = @At("HEAD"), cancellable = true)
    public void moveToWorldInject(ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        ServerPlayerEntity playerEntity = ((ServerPlayerEntity) (Object) this);
        if (destination.getRegistryKey() == World.NETHER && AchieveToDo.isActionBlocked(playerEntity, BlockedAction.NETHER, true)) {
            cir.setReturnValue((Entity) (Object) this);
        }
        if (destination.getRegistryKey() == World.END && AchieveToDo.isActionBlocked(playerEntity, BlockedAction.END, true)) {
            cir.setReturnValue((Entity) (Object) this);
        }
    }
}
