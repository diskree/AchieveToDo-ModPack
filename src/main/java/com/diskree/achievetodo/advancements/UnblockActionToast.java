package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.server.AchieveToDoServer;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.List;

@ClientOnly
public class UnblockActionToast implements Toast {

    public final BlockedAction blockedAction;
    private final Identifier TEXTURE;

    private final Advancement advancement;

    public UnblockActionToast(Advancement advancement, BlockedAction blockedAction) {
        this.advancement = advancement;
        this.blockedAction = blockedAction;
        TEXTURE = new Identifier(AchieveToDo.ID, "textures/gui/toasts_" + blockedAction.getActionType().name().toLowerCase() + ".png");
    }

    @Override
    public Visibility draw(GuiGraphics graphics, ToastManager manager, long startTime) {
        AdvancementDisplay advancementDisplay = this.advancement.getDisplay();
        graphics.drawTexture(TEXTURE, 0, 0, 0, 0, this.getWidth(), this.getHeight());
        if (advancementDisplay != null) {
            int i = 0xFFFF00;
            List<OrderedText> list = manager.getGame().textRenderer.wrapLines(advancementDisplay.getTitle(), 125);
            if (list.size() == 1) {
                graphics.drawText(manager.getGame().textRenderer, Text.translatable(blockedAction.getActionType().getUnblockPopupTitle()), 30, 7, i | 0xFF000000, false);
                graphics.drawText(manager.getGame().textRenderer, list.get(0), 30, 18, -1, false);
            } else {
                if (startTime < 1500L) {
                    int k = MathHelper.floor(MathHelper.clamp((float) (1500L - startTime) / 300.0f, 0.0f, 1.0f) * 255.0f) << 24 | 0x4000000;
                    graphics.drawText(manager.getGame().textRenderer, Text.translatable(blockedAction.getActionType().getUnblockPopupTitle()), 30, 11, i | k, false);
                } else {
                    int k = MathHelper.floor(MathHelper.clamp((float) (startTime - 1500L) / 300.0f, 0.0f, 1.0f) * 252.0f) << 24 | 0x4000000;
                    int l = this.getHeight() / 2 - list.size() * manager.getGame().textRenderer.fontHeight / 2;
                    for (OrderedText orderedText : list) {
                        graphics.drawText(manager.getGame().textRenderer, orderedText, 30, l, 0xFFFFFF | k, false);
                        l += manager.getGame().textRenderer.fontHeight;
                    }
                }
            }
            graphics.drawItemWithoutEntity(advancementDisplay.getIcon(), 8, 8);
            return (double) startTime >= 5000.0 * manager.method_48221() ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
        }
        return Toast.Visibility.HIDE;
    }
}

