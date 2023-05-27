package com.diskree.achievetodo;

import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

public interface ExperienceOrbEntityImpl {
    boolean isAncientCityPortal();

    void setAncientCityPortalTarget(BlockPos targetPos);

    void setHealth();
}
