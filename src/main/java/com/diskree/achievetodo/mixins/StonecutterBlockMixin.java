package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.injection.UsableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StonecutterBlock.class)
public abstract class StonecutterBlockMixin implements UsableBlock {

    @Shadow
    public abstract ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult);

    @Unique
    private boolean isCanUseChecking;

    @Override
    public boolean achieveToDo$canUse(Item item, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        isCanUseChecking = true;
        boolean canUse = onUse(player.getWorld().getBlockState(hitResult.getBlockPos()), player.getWorld(), hitResult.getBlockPos(), player, hand, hitResult) == null;
        isCanUseChecking = false;
        return canUse;
    }

    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;openHandledScreen(Lnet/minecraft/screen/NamedScreenHandlerFactory;)Ljava/util/OptionalInt;", shift = At.Shift.BEFORE), cancellable = true)
    public void returnOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (isCanUseChecking) {
            cir.setReturnValue(null);
        }
    }

    @Redirect(method = "onUse", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isClient:Z", opcode = Opcodes.GETFIELD))
    public boolean skipClientCheck(World instance) {
        if (isCanUseChecking) {
            return false;
        }
        return instance.isClient;
    }
}
