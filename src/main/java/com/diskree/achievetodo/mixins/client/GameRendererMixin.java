package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow
    @Final
    MinecraftClient client;
    @Unique
    private CubeMapRenderer cubeMap;

    @Inject(method = "renderWorld", at = @At("RETURN"))
    private void renderWorldInject(float tickDelta, long limitTime, MatrixStack matrices, CallbackInfo ci) {
        if (client.player != null && client.options.getPerspective().isFirstPerson()) {
            if (!client.player.isUsingSpyglass()) {
                cubeMap = null;
                return;
            }
            NbtCompound nbt = client.player.getActiveItem().getNbt();
            if (nbt == null) {
                return;
            }
            String panoramaId = nbt.getString("Panorama");
            if (panoramaId == null) {
                return;
            }
            if (cubeMap == null) {
                cubeMap = new CubeMapRenderer(new Identifier(AchieveToDo.ID, panoramaId));
            }
            float pitch = client.player.prevPitch;
            if (pitch > 180.0f) {
                pitch -= 360.0f;
            } else if (pitch < -180.0f) {
                pitch += 360.0f;
            }

            float yaw = client.player.prevYaw;
            if (yaw > 180.0f) {
                yaw -= 360.0f;
            } else if (yaw < -180.0f) {
                yaw += 360.0f;
            }

            this.cubeMap.draw(this.client, pitch, -yaw, 1.0f);
        }
    }
}
