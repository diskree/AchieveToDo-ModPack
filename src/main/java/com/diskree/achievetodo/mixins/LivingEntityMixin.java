package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.RaiderEntityImpl;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "getLootTable", at = @At("HEAD"), cancellable = true)
    private void getLootTableInject(CallbackInfoReturnable<Identifier> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self.getType() == EntityType.EVOKER && self instanceof RaiderEntityImpl && ((RaiderEntityImpl) self).isSpawnedAsRaider()) {
            cir.setReturnValue(AchieveToDoMod.EVOKER_NO_TOTEM_OF_UNDYING_LOOT_TABLE);
        }
    }
}
