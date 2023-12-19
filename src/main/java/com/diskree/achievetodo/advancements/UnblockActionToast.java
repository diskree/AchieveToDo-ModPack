package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.action.BlockedActionType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.List;

@Environment(EnvType.CLIENT)
public class UnblockActionToast implements Toast {

    public final BlockedActionType blockedAction;
    private final Identifier TEXTURE;

    private final AdvancementEntry advancement;

    public UnblockActionToast(AdvancementEntry advancement, BlockedActionType blockedAction) {
        this.advancement = advancement;
        this.blockedAction = blockedAction;
        TEXTURE = new Identifier(AchieveToDo.ID, "textures/gui/toasts_" + blockedAction.getCategory().name().toLowerCase() + ".png");
    }

    @Override
    public Visibility draw(DrawContext context, ToastManager manager, long startTime) {
        AdvancementDisplay advancementDisplay = advancement.value().display().orElse(null);
        context.drawGuiTexture(TEXTURE, 0, 0, getWidth(), getHeight());
        if (advancementDisplay != null) {
            int i = 0xFFFF00;
            List<OrderedText> list = manager.getClient().textRenderer.wrapLines(advancementDisplay.getTitle(), 125);
            if (list.size() == 1) {
                context.drawText(manager.getClient().textRenderer, Text.translatable(blockedAction.getCategory().getUnblockPopupTitle().getString()), 30, 7, i | 0xFF000000, false);
                context.drawText(manager.getClient().textRenderer, list.get(0), 30, 18, -1, false);
            } else {
                if (startTime < 1500L) {
                    int k = MathHelper.floor(MathHelper.clamp((float) (1500L - startTime) / 300.0f, 0.0f, 1.0f) * 255.0f) << 24 | 0x4000000;
                    context.drawText(manager.getClient().textRenderer, Text.translatable(blockedAction.getCategory().getUnblockPopupTitle().getString()), 30, 11, i | k, false);
                } else {
                    int k = MathHelper.floor(MathHelper.clamp((float) (startTime - 1500L) / 300.0f, 0.0f, 1.0f) * 252.0f) << 24 | 0x4000000;
                    int l = this.getHeight() / 2 - list.size() * manager.getClient().textRenderer.fontHeight / 2;
                    for (OrderedText orderedText : list) {
                        context.drawText(manager.getClient().textRenderer, orderedText, 30, l, 0xFFFFFF | k, false);
                        l += manager.getClient().textRenderer.fontHeight;
                    }
                }
            }
            context.drawItemWithoutEntity(advancementDisplay.getIcon(), 8, 8);
            return (double) startTime >= 5000.0 * manager.getNotificationDisplayTimeMultiplier() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
        }
        return Toast.Visibility.HIDE;
    }
}

