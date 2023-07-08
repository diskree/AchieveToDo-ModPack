package com.diskree.achievetodo.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mouse.class)
public class MouseMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Redirect(method = "updateLookDirection", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"))
    private boolean updateMouseRedirect(ClientPlayerEntity instance) {
        if (client.player != null && client.player.isUsingSpyglass()) {
            NbtCompound nbt = client.player.getActiveItem().getNbt();
            return nbt == null || !nbt.contains("Panorama");
        }
        return false;
    }
}
