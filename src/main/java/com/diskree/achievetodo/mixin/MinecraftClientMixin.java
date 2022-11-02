package com.diskree.achievetodo.mixin;

import me.shedaniel.advancementsenlarger.gui.BiggerAdvancementsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void setScreenInject(Screen screen, CallbackInfo ci) {
    }

    @ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true)
    private Screen setScreenInject(Screen screen) {
        if (screen != null && AdvancementsScreen.class == screen.getClass())
            return new BiggerAdvancementsScreen(player.networkHandler.getAdvancementHandler());
        return screen;
    }
}
