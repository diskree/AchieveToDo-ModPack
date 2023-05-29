package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.RaiderEntityImpl;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RaiderEntity.class)
public class RaiderEntityMixin implements RaiderEntityImpl {

    private boolean isSpawnedAsRaider;

    @Override
    public boolean isSpawnedAsRaider() {
        return isSpawnedAsRaider;
    }

    @Inject(method = "initialize", at = @At("HEAD"))
    private void initializeInject(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        isSpawnedAsRaider = spawnReason == SpawnReason.EVENT;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void writeCustomDataToNbtInject(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("SpawnedAsRaider", isSpawnedAsRaider);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readCustomDataFromNbtInject(NbtCompound nbt, CallbackInfo ci) {
        isSpawnedAsRaider = nbt.getBoolean("SpawnedAsRaider");
    }
}
