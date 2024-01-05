package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.advancements.hints.AncientCityPortalEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin {

    @Unique
    private boolean isAncientCityPortalActivator() {
        ItemStack stack = getStack();
        JukeboxBlockEntity jukeboxBlockEntity = (JukeboxBlockEntity) (Object) this;
        return stack != null && Items.MUSIC_DISC_5.equals(stack.getItem()) && AncientCityPortalEntity.isJukebox(jukeboxBlockEntity.getWorld(), jukeboxBlockEntity.getPos());
    }

    @Shadow
    private int ticksThisSecond;

    @Shadow
    protected abstract void stopPlaying();

    @Shadow
    public abstract ItemStack getStack();

    @Shadow
    public abstract void dropRecord();

    @Inject(method = "hasSecondPassed", at = @At("HEAD"), cancellable = true)
    private void hasSecondPassedInject(CallbackInfoReturnable<Boolean> cir) {
        if (isAncientCityPortalActivator()) {
            cir.setReturnValue(ticksThisSecond >= 3);
        }
    }

    @Redirect(method = "isSongFinished", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/MusicDiscItem;getSongLengthInTicks()I"))
    private int isSongFinishedInject(MusicDiscItem instance) {
        if (isAncientCityPortalActivator()) {
            return 35 * 20;
        }
        return instance.getSongLengthInTicks();
    }

    @Redirect(method = "tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;stopPlaying()V"))
    private void tickInject(JukeboxBlockEntity instance) {
        stopPlaying();
        if (isAncientCityPortalActivator()) {
            dropRecord();
        }
    }

    @Inject(method = "spawnNoteParticle", at = @At("HEAD"), cancellable = true)
    private void spawnNoteParticleInject(World world, BlockPos pos, CallbackInfo ci) {
        if (isAncientCityPortalActivator()) {
            ci.cancel();
        }
    }
}
