package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.advancements.hints.AncientCityPortalEntity;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow
    private @Nullable ClientWorld world;

    @Redirect(method = "playSong", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/PositionedSoundInstance;record(Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/client/sound/PositionedSoundInstance;"))
    private PositionedSoundInstance processWorldEventRedirect(SoundEvent sound, Vec3d pos) {
        if (sound == SoundEvents.MUSIC_DISC_5 && pos != null && AncientCityPortalEntity.isJukebox(world, new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ()))) {
            sound = AchieveToDo.MUSIC_DISC_5_ACTIVATOR;
        }
        return PositionedSoundInstance.record(sound, Objects.requireNonNull(pos));
    }
}
