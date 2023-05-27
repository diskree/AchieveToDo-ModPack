package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.OrderedText;
import net.minecraft.util.math.MathHelper;

@Environment(value = EnvType.CLIENT)
public class AchieveToDoToast
        implements Toast {
    public final BlockedAction blockedAction;
    private final Identifier TEXTURE;

    public static final int DEFAULT_DURATION_MS = 5000;
    private final Advancement advancement;
    private boolean soundPlayed;

    public AchieveToDoToast(Advancement advancement, BlockedAction blockedAction) {
        this.advancement = advancement;
        this.blockedAction = blockedAction;
        TEXTURE = new Identifier(AchieveToDoMod.ID, "textures/gui/toasts_" + blockedAction.actionType.name().toLowerCase() + ".png");
    }

    @Override
    public Toast.Visibility draw(DrawContext context, ToastManager manager, long startTime) {
        AdvancementDisplay advancementDisplay = this.advancement.getDisplay();
        context.drawTexture(TEXTURE, 0, 0, 0, 0, this.getWidth(), this.getHeight());
        if (advancementDisplay != null) {
            int i;
            List<OrderedText> list = manager.getClient().textRenderer.wrapLines(advancementDisplay.getTitle(), 125);
            int n = i = advancementDisplay.getFrame() == AdvancementFrame.CHALLENGE ? 0xFF88FF : 0xFFFF00;
            if (list.size() == 1) {
                context.drawText(manager.getClient().textRenderer, Text.of(blockedAction.actionType.getUnlockPopupTitle() + "!"), 30, 7, i | 0xFF000000, false);
                context.drawText(manager.getClient().textRenderer, list.get(0), 30, 18, -1, false);
            } else {
                int j = 1500;
                float f = 300.0f;
                if (startTime < 1500L) {
                    int k = MathHelper.floor(MathHelper.clamp((float) (1500L - startTime) / 300.0f, 0.0f, 1.0f) * 255.0f) << 24 | 0x4000000;
                    context.drawText(manager.getClient().textRenderer, Text.of(blockedAction.actionType.getUnlockPopupTitle() + "!"), 30, 11, i | k, false);
                } else {
                    int k = MathHelper.floor(MathHelper.clamp((float) (startTime - 1500L) / 300.0f, 0.0f, 1.0f) * 252.0f) << 24 | 0x4000000;
                    int l = this.getHeight() / 2 - list.size() * manager.getClient().textRenderer.fontHeight / 2;
                    for (OrderedText orderedText : list) {
                        context.drawText(manager.getClient().textRenderer, orderedText, 30, l, 0xFFFFFF | k, false);
                        l += manager.getClient().textRenderer.fontHeight;
                    }
                }
            }
            if (!this.soundPlayed && startTime > 0L) {
                this.soundPlayed = true;
                if (advancementDisplay.getFrame() == AdvancementFrame.CHALLENGE) {
                    manager.getClient().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f));
                }
            }
            context.drawItemWithoutEntity(advancementDisplay.getIcon(), 8, 8);
            return (double) startTime >= 5000.0 * manager.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
        }
        return Toast.Visibility.HIDE;
    }
}

