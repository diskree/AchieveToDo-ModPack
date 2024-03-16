package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.advancements.hints.AncientCityPortalEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @Shadow
    @Final
    protected float resistance;

    @Shadow
    public abstract float getHardness();

    @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"))
    public void getStateForNeighborUpdateInject(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (AncientCityPortalEntity.isReinforcedDeepslate(getHardness(), resistance)) {
            AncientCityPortalEntity portalEntity = AncientCityPortalEntity.findForBlock(world, pos);
            if (portalEntity != null) {
                portalEntity.checkCharging(state);
            }
        }
    }
}
