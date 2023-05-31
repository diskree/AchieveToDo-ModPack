package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.LevelInfoImpl;
import com.diskree.achievetodo.SavePropertiesImpl;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelProperties.class)
public class LevelPropertiesMixin implements SavePropertiesImpl {

    @Override
    public boolean isItemRewardsEnabled() {
        return ((LevelInfoImpl) (Object) levelInfo).isItemRewardsEnabled();
    }

    @Override
    public boolean isExperienceRewardsEnabled() {
        return ((LevelInfoImpl) (Object) levelInfo).isExperienceRewardsEnabled();
    }

    @Override
    public boolean isTrophyRewardsEnabled() {
        return ((LevelInfoImpl) (Object) levelInfo).isTrophyRewardsEnabled();
    }

    @Shadow
    private LevelInfo levelInfo;

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "updateProperties", at = @At("RETURN"))
    private void updatePropertiesInject(DynamicRegistryManager registryManager, NbtCompound levelNbt, NbtCompound playerNbt, CallbackInfo ci) {
        LevelInfoImpl levelInfoImpl = (LevelInfoImpl) (Object) levelInfo;
        levelNbt.putBoolean("ItemRewardsEnabled", levelInfoImpl.isItemRewardsEnabled());
        levelNbt.putBoolean("ExperienceRewardsEnabled", levelInfoImpl.isExperienceRewardsEnabled());
        levelNbt.putBoolean("TrophyRewardsEnabled", levelInfoImpl.isTrophyRewardsEnabled());
    }
}
