package com.diskree.achievetodo.mixins;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.EndGatewayFeature;
import net.minecraft.world.gen.feature.EndGatewayFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndGatewayFeature.class)
public class EndGatewayFeatureMixin {

    @Inject(method = "place", at = @At("TAIL"))
    public void placeInject(FeatureContext<EndGatewayFeatureConfig> context, CallbackInfoReturnable<Boolean> cir) {
        if (context.getConfig().getExitPos().isEmpty()) {
            EndGatewayFeature endGatewayFeature = (EndGatewayFeature) (Object) this;
            endGatewayFeature.setBlockState(context.getWorld(), context.getOrigin().up(3), Blocks.DRAGON_EGG.getDefaultState());
        }
    }
}
