package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.ancient_city_portal.AncientCityPortalAdvancementEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class ReinforcedDeepslateMixin {

    @Shadow
    protected abstract void setDefaultState(BlockState state);

    @Shadow
    public abstract BlockState getDefaultState();

    @Inject(method = "<init>", at = @At("RETURN"))
    public void initReturnInject(AbstractBlock.Settings settings, CallbackInfo ci) {
        if (isReinforcedDeepslate()) {
            setDefaultState(getDefaultState().with(AncientCityPortalAdvancementEntity.REINFORCED_DEEPSLATE_CHARGED_STATE, false));
        }
    }

    @Inject(method = "appendProperties", at = @At("RETURN"))
    protected void appendPropertiesInject(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        if (isReinforcedDeepslate()) {
            builder.add(AncientCityPortalAdvancementEntity.REINFORCED_DEEPSLATE_CHARGED_STATE);
        }
    }

    private boolean isReinforcedDeepslate() {
        Block block = ((Block) (Object) this);
        return block.getHardness() == 55.0f && block.getBlastResistance() == 1200.0f;
    }
}
