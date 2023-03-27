package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class AchieveToDoToast implements Toast {
    Identifier TEXTURE;

    private final Advancement advancement;
    public final BlockedAction blockedAction;
    private boolean soundPlayed;

    public AchieveToDoToast(Advancement advancement, BlockedAction blockedAction) {
        this.advancement = advancement;
        this.blockedAction = blockedAction;
        TEXTURE = new Identifier(AchieveToDoMod.ID, "textures/gui/toasts_" + blockedAction.getActionType().name().toLowerCase() + ".png");
    }

    public Toast.Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        AdvancementDisplay advancementDisplay = this.advancement.getDisplay();
        manager.drawTexture(matrices, 0, 0, 0, 0, this.getWidth(), this.getHeight());
        if (advancementDisplay != null) {
            List<OrderedText> list = manager.getClient().textRenderer.wrapLines(advancementDisplay.getTitle(), 125);
            int i = advancementDisplay.getFrame() == AdvancementFrame.CHALLENGE ? 16746751 : 16776960;
            if (list.size() == 1) {
                manager.getClient().textRenderer.draw(matrices, Text.of(blockedAction.getActionType().getUnlockPopupTitle() + "!"), 30.0F, 7.0F, i | -16777216);
                manager.getClient().textRenderer.draw(matrices, list.get(0), 30.0F, 18.0F, -1);
            } else {
                int k;
                if (startTime < 1500L) {
                    k = MathHelper.floor(MathHelper.clamp((float) (1500L - startTime) / 300.0F, 0.0F, 1.0F) * 255.0F) << 24 | 67108864;
                    manager.getClient().textRenderer.draw(matrices, Text.of(blockedAction.getActionType().getUnlockPopupTitle() + "!"), 30.0F, 11.0F, i | k);
                } else {
                    k = MathHelper.floor(MathHelper.clamp((float) (startTime - 1500L) / 300.0F, 0.0F, 1.0F) * 252.0F) << 24 | 67108864;
                    int var10000 = this.getHeight() / 2;
                    int var10001 = list.size();
                    Objects.requireNonNull(manager.getClient().textRenderer);
                    int l = var10000 - var10001 * 9 / 2;

                    for (Iterator<OrderedText> var12 = list.iterator(); var12.hasNext(); l += 9) {
                        OrderedText orderedText = var12.next();
                        manager.getClient().textRenderer.draw(matrices, orderedText, 30.0F, (float) l, 16777215 | k);
                        Objects.requireNonNull(manager.getClient().textRenderer);
                    }
                }
            }

            if (!this.soundPlayed && startTime > 0L) {
                this.soundPlayed = true;
                if (advancementDisplay.getFrame() == AdvancementFrame.CHALLENGE) {
                    manager.getClient().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
                }
            }

            manager.getClient().getItemRenderer().renderInGui(matrices, advancementDisplay.getIcon(), 8, 8);
            return startTime >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
        } else {
            return Toast.Visibility.HIDE;
        }
    }
}
