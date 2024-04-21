package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.injection.UsableBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CartographyTableBlock;
import net.minecraft.entity.player.PlayerEntity;
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

@Mixin(CartographyTableBlock.class)
public abstract class CartographyTableBlockMixin implements UsableBlock {

    @Unique
    private boolean isCanUseChecking;

    @Shadow
    public abstract ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit);

    @Override
    public boolean achieveToDo$canUse(PlayerEntity player, Hand hand, BlockHitResult hit) {
        isCanUseChecking = true;
        boolean canUse = onUse(player.getWorld().getBlockState(hit.getBlockPos()), player.getWorld(), hit.getBlockPos(), player, hand, hit) == null;
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
