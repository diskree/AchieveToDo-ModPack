package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(AdvancementsScreen.class)
public abstract class AdvancementsScreenMixin extends Screen {

    @Shadow
    @Final
    private static Identifier WINDOW_TEXTURE;

    public AdvancementsScreenMixin() {
        super(null);
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 252), require = 1)
    private int renderModifyWidth(int constant) {
        return width - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN * 2;
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 140), require = 1)
    private int renderModifyHeight(int constant) {
        return height - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN * 2;
    }

    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 252), require = 1)
    private int mouseClickedModifyWidth(int constant) {
        return width - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN * 2;
    }

    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 140), require = 1)
    private int mouseClickedModifyHeight(int constant) {
        return height - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN * 2;
    }

    @ModifyConstant(method = "drawAdvancementTree", constant = @Constant(intValue = 234), require = 1)
    private int drawAdvancementTreeModifyWidth(int constant) {
        return width - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN * 2;
    }

    @ModifyConstant(method = "drawAdvancementTree", constant = @Constant(intValue = 113), require = 1)
    private int drawAdvancementTreeModifyHeight(int constant) {
        return height - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN * 2 - 3 * 9;
    }

    @Redirect(method = "drawWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    public void drawWindowVanilla(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {
    }

    @Inject(method = "drawWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    public void drawWindowCustom(DrawContext context, int x, int y, CallbackInfo ci) {
        int screenWidth = 252;
        int screenHeight = 140;
        int actualWidth = width - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN - x;
        int actualHeight = width - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN - y;
        int halfOfWidth = screenWidth / 2;
        int halfOfHeight = screenHeight / 2;
        int clipTopX = (int) (Math.max(0, screenWidth - actualWidth) / 2. + 0.5);
        int clipLeftX = (int) (Math.max(0, screenWidth - actualWidth) / 2.);
        int clipTopY = (int) (Math.max(0, screenHeight - actualHeight) / 2. + 0.5);
        int clipLeftY = (int) (Math.max(0, screenHeight - actualHeight) / 2.);

        int rightX = width - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN - halfOfWidth + clipTopX;
        int bottomY = height - AchieveToDoMod.ADVANCEMENTS_SCREEN_MARGIN - halfOfHeight + clipTopY;

        context.drawTexture(WINDOW_TEXTURE, x, y, 0, 0, halfOfWidth - clipLeftX, halfOfHeight - clipLeftY);
        context.drawTexture(WINDOW_TEXTURE, rightX, y, halfOfWidth + clipTopX, 0, halfOfWidth - clipTopX, halfOfHeight - clipLeftY);
        context.drawTexture(WINDOW_TEXTURE, x, bottomY, 0, halfOfHeight + clipTopY, halfOfWidth - clipLeftX, halfOfHeight - clipTopY);
        context.drawTexture(WINDOW_TEXTURE, rightX, bottomY, halfOfWidth + clipTopX, halfOfHeight + clipTopY, halfOfWidth - clipTopX, halfOfHeight - clipTopY);

        iterate(x + halfOfWidth - clipLeftX, rightX, 200, (pos, len) -> {
            context.drawTexture(WINDOW_TEXTURE, pos, y, 15, 0, len, halfOfHeight);
            context.drawTexture(WINDOW_TEXTURE, pos, bottomY, 15, halfOfHeight + clipTopY, len, halfOfHeight - clipTopY);
        });
        iterate(y + halfOfHeight - clipLeftY, bottomY, 100, (pos, len) -> {
            context.drawTexture(WINDOW_TEXTURE, x, pos, 0, 25, halfOfWidth, len);
            context.drawTexture(WINDOW_TEXTURE, rightX, pos, halfOfWidth + clipTopX, 25, halfOfWidth - clipTopX, len);
        });
    }

    private void iterate(int start, int end, int maxStep, BiConsumer<Integer, Integer> func) {
        if (start >= end) {
            return;
        }
        int size;
        for (int i = start; i < end; i += maxStep) {
            size = maxStep;
            if (i + size > end) {
                size = end - i;
                if (size <= 0) {
                    return;
                }
            }
            func.accept(i, size);
        }
    }
}
