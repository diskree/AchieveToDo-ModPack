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

    @Shadow
    private int ticksSinceLastEvent;

    @Unique
    private boolean isAncientCityPortalActivator() {
        ItemStack stack = getStack(0);
        JukeboxBlockEntity self = (JukeboxBlockEntity) (Object) this;
        return stack != null && Items.MUSIC_DISC_5.equals(stack.getItem()) && AncientCityPortalEntity.isJukebox(self.getWorld(), self.getPos());
    }

    @Shadow
    public abstract ItemStack getStack(int slot);

    @Shadow
    protected abstract void stopPlaying();

    @Shadow
    public abstract void dropDisc();

    @Inject(method = "oneSecondWithoutEvents", at = @At("HEAD"), cancellable = true)
    private void hasSecondPassedInject(CallbackInfoReturnable<Boolean> cir) {
        if (isAncientCityPortalActivator()) {
            cir.setReturnValue(ticksSinceLastEvent >= 3);
        }
    }

    @Redirect(method = "isDiscFinishedPlaying", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/MusicDiscItem;getTicks()I"))
    private int isDiscFinishedPlayingInject(MusicDiscItem instance) {
        if (isAncientCityPortalActivator()) {
            return 35 * 20;
        }
        return instance.getTicks();
    }

    @Redirect(method = "tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;stopPlaying()V"))
    private void tickInject(JukeboxBlockEntity instance) {
        stopPlaying();
        if (isAncientCityPortalActivator()) {
            dropDisc();
        }
    }

    @Inject(method = "spawnMusicParticles", at = @At("HEAD"), cancellable = true)
    private void spawnNoteParticleInject(World world, BlockPos pos, CallbackInfo ci) {
        if (isAncientCityPortalActivator()) {
            ci.cancel();
        }
    }
}
