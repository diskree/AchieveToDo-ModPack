package com.diskree.achievetodo.injection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

public interface UsableBlock {
    boolean achieveToDo$canUse(Item item, PlayerEntity player, Hand hand, BlockHitResult hitResult);
}
