package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.injection.UsableItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AliasedBlockItem.class)
public class AliasedBlockItemMixin extends BlockItem implements UsableItem {

    public AliasedBlockItemMixin(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public boolean achieveToDo$canUse(PlayerEntity player, BlockHitResult blockHitResult) {
        BlockState aliasedBlockState = getBlock().getDefaultState();
        BlockState blockState = player.getWorld().getBlockState(blockHitResult.getBlockPos());
        return !aliasedBlockState.isIn(BlockTags.MAINTAINS_FARMLAND) || !blockState.isOf(Blocks.FARMLAND) || blockHitResult.getSide() != Direction.UP;
    }
}
