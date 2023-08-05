package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.server.AchieveToDoServer;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlock.class)
public class AbstractFurnaceBlockMixin {

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    public void onUseInject(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        AbstractFurnaceBlock clazz = ((AbstractFurnaceBlock) (Object) this);
        if (clazz instanceof FurnaceBlock && AchieveToDoServer.isActionBlocked(player, BlockedAction.USING_FURNACE) ||
                clazz instanceof SmokerBlock && AchieveToDoServer.isActionBlocked(player, BlockedAction.USING_SMOKER) ||
                clazz instanceof BlastFurnaceBlock && AchieveToDoServer.isActionBlocked(player, BlockedAction.USING_BLAST_FURNACE)) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}
