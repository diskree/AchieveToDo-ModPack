package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.action.BlockedActionType;
import com.diskree.achievetodo.client.CreateWorldScreenImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void setScreenInject(Screen screen, CallbackInfo ci) {
        if (screen instanceof InventoryScreen && AchieveToDo.isActionBlocked(player, BlockedActionType.OPEN_INVENTORY)) {
            ci.cancel();
        } else if (screen instanceof CreateWorldScreen createWorldScreen && ((CreateWorldScreenImpl) createWorldScreen).achieveToDo$datapacksLoaded()) {
            ci.cancel();
        }
    }
}
