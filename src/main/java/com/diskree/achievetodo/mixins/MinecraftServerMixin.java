package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.MinecraftServerImpl;
import com.diskree.achievetodo.SavePropertiesImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.SaveProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin implements MinecraftServerImpl {

    @Shadow
    @Final
    protected SaveProperties saveProperties;

    @Override
    public boolean isItemRewardsEnabled() {
        return ((SavePropertiesImpl) saveProperties).isItemRewardsEnabled();
    }

    @Override
    public boolean isExperienceRewardsEnabled() {
        return ((SavePropertiesImpl) saveProperties).isExperienceRewardsEnabled();
    }

    @Override
    public boolean isTrophyRewardsEnabled() {
        return ((SavePropertiesImpl) saveProperties).isTrophyRewardsEnabled();
    }
}
