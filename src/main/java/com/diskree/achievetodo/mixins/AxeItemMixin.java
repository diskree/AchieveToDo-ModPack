package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.injection.UsableOnBlock;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin implements UsableOnBlock {

    @Unique
    private boolean isCanUseOnBlockChecking;

    @Override
    public boolean achieveToDo$canUseOnBlock(PlayerEntity player, BlockHitResult blockHitResult) {
        isCanUseOnBlockChecking = true;
        boolean canUseOnBlock = tryStrip(player.getWorld(), blockHitResult.getBlockPos(), player, player.getWorld().getBlockState(blockHitResult.getBlockPos())).isPresent();
        isCanUseOnBlockChecking = false;
        return canUseOnBlock;
    }

    @Shadow
    protected abstract Optional<BlockState> tryStrip(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state);

    @Inject(method = "tryStrip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    public void returnOnStrip(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @Local(ordinal = 0) Optional<BlockState> blockState) {
        if (isCanUseOnBlockChecking) {
            cir.setReturnValue(blockState);
        }
    }

    @Inject(method = "tryStrip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", ordinal = 1, shift = At.Shift.BEFORE), cancellable = true)
    public void returnOnDecreaseOxidationState(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @Local(ordinal = 1) Optional<BlockState> blockState) {
        if (isCanUseOnBlockChecking) {
            cir.setReturnValue(blockState);
        }
    }

    @Inject(method = "tryStrip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", ordinal = 2, shift = At.Shift.BEFORE), cancellable = true)
    public void returnOnWaxOff(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") @Local(ordinal = 2) Optional<BlockState> blockState) {
        if (isCanUseOnBlockChecking) {
            cir.setReturnValue(blockState);
        }
    }
}
