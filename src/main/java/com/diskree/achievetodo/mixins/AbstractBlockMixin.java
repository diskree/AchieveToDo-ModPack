package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.ancient_city_portal.AncientCityPortalAdvancementEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @Shadow
    public abstract float getHardness();

    @Shadow
    @Final
    protected float resistance;

    @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"), cancellable = true)
    public void getStateForNeighborUpdateInject(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if (getHardness() == 55.0f && resistance == 1200.0f) {
            if (neighborState.isOf(Blocks.REINFORCED_DEEPSLATE) && !neighborState.get(AncientCityPortalAdvancementEntity.REINFORCED_DEEPSLATE_CHARGED_STATE)) {
                cir.setReturnValue(Blocks.REINFORCED_DEEPSLATE.getDefaultState());
                return;
            }
            Box box = new Box(
                    pos.offset(Direction.Axis.X, -AncientCityPortalAdvancementEntity.PORTAL_WIDTH).offset(Direction.Axis.Z, -AncientCityPortalAdvancementEntity.PORTAL_WIDTH).down(AncientCityPortalAdvancementEntity.PORTAL_HEIGHT),
                    pos.offset(Direction.Axis.X, AncientCityPortalAdvancementEntity.PORTAL_WIDTH).offset(Direction.Axis.Z, AncientCityPortalAdvancementEntity.PORTAL_WIDTH).up(AncientCityPortalAdvancementEntity.PORTAL_HEIGHT)
            );
            List<AncientCityPortalAdvancementEntity> entities = world.getEntitiesByType(TypeFilter.instanceOf(AncientCityPortalAdvancementEntity.class), box, (portalEntity) -> portalEntity.isPortalFrameBlock(pos));
            if (entities.size() == 1 && !entities.get(0).isPortalActivated()) {
                cir.setReturnValue(Blocks.REINFORCED_DEEPSLATE.getDefaultState());
            }
        }
    }
}
