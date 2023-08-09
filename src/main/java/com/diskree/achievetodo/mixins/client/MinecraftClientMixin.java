package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.client.CreateWorldScreenImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void onUseInject(Screen screen, CallbackInfo ci) {
        if (screen instanceof CreateWorldScreen createWorldScreen && ((CreateWorldScreenImpl) createWorldScreen).achieveToDo$datapacksLoaded()) {
            ci.cancel();
        }
    }
}
