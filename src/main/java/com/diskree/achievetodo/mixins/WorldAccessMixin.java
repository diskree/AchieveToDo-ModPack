package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldAccess.class)
public interface WorldAccessMixin {

    @Shadow
    void emitGameEvent(GameEvent event, BlockPos pos, GameEvent.Emitter emitter);

    @Inject(method = "emitGameEvent(Lnet/minecraft/world/event/GameEvent;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/event/GameEvent$Emitter;)V", at = @At("RETURN"))
    default void emitGameEventInject(GameEvent event, BlockPos pos, GameEvent.Emitter emitter, CallbackInfo ci) {
        if (event == GameEvent.JUKEBOX_PLAY) {
            emitGameEvent(AchieveToDoMod.JUKEBOX_PLAY, pos, emitter);
        } else if (event == GameEvent.JUKEBOX_STOP_PLAY) {
            emitGameEvent(AchieveToDoMod.JUKEBOX_STOP_PLAY, pos, emitter);
        }
    }
}
