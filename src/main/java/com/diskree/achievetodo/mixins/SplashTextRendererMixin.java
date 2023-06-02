package com.diskree.achievetodo.mixins;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SplashTextRenderer.class)
public class SplashTextRendererMixin {

    @ModifyConstant(method = "render", constant = @Constant(floatValue = 123.0f))
    private float modifyWidth(float orig) {
        return 170.0f;
    }
}
