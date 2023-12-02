package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tickInject(boolean slowDown, float movementMultiplier, CallbackInfo ci) {
        if (jumping && AchieveToDo.isActionBlocked(MinecraftClient.getInstance().player, BlockedAction.JUMP)) {
            jumping = false;
        }
    }
}
