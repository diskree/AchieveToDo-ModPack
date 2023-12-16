package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.client.SpyglassPanoramaDetails;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
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

    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingSpyglass()Z"))
    private boolean updateMouseRedirect(ClientPlayerEntity instance) {
        if (client.player != null && client.player.isUsingSpyglass()) {
            return SpyglassPanoramaDetails.from(client.player.getActiveItem()) == null;
        }
        return false;
    }
}
