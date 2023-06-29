package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.ancient_city_portal.AncientCityPortalEntity;
import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(JukeboxBlockEntity.class)
public abstract class JukeboxBlockEntityMixin {

    @Shadow
    public abstract ItemStack getStack(int slot);

    @Shadow
    private int ticksThisSecond;

    @Inject(method = "hasSecondPassed", at = @At("HEAD"), cancellable = true)
    private void hasSecondPassedInject(CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = getStack(0);
        JukeboxBlockEntity self = (JukeboxBlockEntity) (Object) this;
        if (stack != null && Items.MUSIC_DISC_5.equals(stack.getItem()) && AncientCityPortalEntity.isJukebox(self.getWorld(), self.getPos())) {
            cir.setReturnValue(ticksThisSecond >= 3);
        }
    }
}
