package com.diskree.achievetodo.injection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;

public interface BlockedItem {
    boolean achieveToDo$canUseOnBlock(PlayerEntity player, BlockHitResult blockHitResult);
}
