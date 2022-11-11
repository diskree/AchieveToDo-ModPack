package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.village.VillagerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {

    @Shadow
    protected abstract void sayNo();

    @Shadow
    public abstract VillagerData getVillagerData();

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void interactMobInject(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (AchieveToDoMod.isVillagerBlocked(getVillagerData().getProfession())) {
            sayNo();
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
}