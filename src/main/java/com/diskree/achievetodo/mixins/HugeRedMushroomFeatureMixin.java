package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.feature.HugeRedMushroomFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HugeRedMushroomFeature.class)
public class HugeRedMushroomFeatureMixin {

    @Redirect(method = "generateCap", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOpaqueFullCube(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Z"))
    public boolean generateCapInject(BlockState instance, BlockView blockView, BlockPos blockPos) {
        return instance.isOpaqueFullCube(blockView, blockPos) || instance.isOf(AchieveToDo.ANCIENT_CITY_PORTAL_BLOCK);
    }
}
