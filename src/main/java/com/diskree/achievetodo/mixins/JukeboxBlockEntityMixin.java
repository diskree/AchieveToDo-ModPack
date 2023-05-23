package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.JukeboxBlockEntityImpl;
import net.minecraft.block.entity.JukeboxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin implements JukeboxBlockEntityImpl {

    @Shadow
    private long tickCount;

    @Shadow
    private long recordStartTick;

    @Override
    public boolean isDiskRelaxPartFinished() {
        return tickCount >= recordStartTick + 1259;
    }

    @Override
    public boolean isDiskStartedJustNow() {
        return tickCount - recordStartTick <= 20L;
    }
}
