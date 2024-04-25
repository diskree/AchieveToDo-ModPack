package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.blocked_actions.BlockedActionType;
import com.diskree.achievetodo.injection.PlayerEntityImpl;
import com.mojang.datafixers.util.Either;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Unique
    private boolean isCanUseChecking() {
        return ((PlayerEntityImpl) this).achievetodo$isCanUseChecking();
    }

    @Inject(
            method = "moveToWorld",
            at = @At("HEAD"),
            cancellable = true
    )
    public void moveToWorldInject(@NotNull ServerWorld destination, CallbackInfoReturnable<Entity> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        if (AchieveToDo.isActionBlocked(player, BlockedActionType.findBlockedDimension(destination.getRegistryKey()))) {
            cir.setReturnValue(player);
        }
    }

    @Inject(
            method = "trySleep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;trySleep(Lnet/minecraft/util/math/BlockPos;)Lcom/mojang/datafixers/util/Either;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void returnOnUse(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
        if (isCanUseChecking()) {
            cir.setReturnValue(null);
        }
    }
}
