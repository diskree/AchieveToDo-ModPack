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
    public boolean isDiskRelaxPartPlaying() {
        long currentTicks = tickCount - recordStartTick;
        return currentTicks > 739 && currentTicks < 1259;
    }
}
