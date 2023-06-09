package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.ancient_city_portal.AncientCityPortalEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.ToIntFunction;

@Mixin(AbstractBlock.Settings.class)
public abstract class AbstractBlockSettingsMixin {

    @Shadow
    public abstract AbstractBlock.Settings luminance(ToIntFunction<BlockState> luminance);

    @Inject(method = "strength(FF)Lnet/minecraft/block/AbstractBlock$Settings;", at = @At("HEAD"))
    public void onUseInject(float hardness, float resistance, CallbackInfoReturnable<AbstractBlock.Settings> cir) {
        if (AncientCityPortalEntity.isReinforcedDeepslate(hardness, resistance)) {
            luminance(AncientCityPortalEntity::getPortalFrameLightLevel);
        }
    }
}
