package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.injection.UsableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DoorBlock.class)
public abstract class DoorBlockMixin implements UsableBlock {

    @Shadow
    public abstract ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit);

    @Unique
    private boolean isCanUseChecking;

    @Override
    public boolean achieveToDo$canUse(PlayerEntity player, Hand hand, BlockHitResult hit) {
        isCanUseChecking = true;
        boolean canUse = onUse(player.getWorld().getBlockState(hit.getBlockPos()), player.getWorld(), hit.getBlockPos(), player, hand, hit) == null;
        isCanUseChecking = false;
        return canUse;
    }

    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;cycle(Lnet/minecraft/state/property/Property;)Ljava/lang/Object;", shift = At.Shift.BEFORE), cancellable = true)
    public void returnOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (isCanUseChecking) {
            cir.setReturnValue(null);
        }
    }
}
