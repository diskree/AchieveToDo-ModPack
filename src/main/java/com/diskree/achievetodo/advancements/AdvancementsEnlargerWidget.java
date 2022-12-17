package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextHandler;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.advancement.AdvancementObtainedStatus;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector4f;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public class AdvancementsEnlargerWidget extends DrawableHelper {
    private static final int[] field_24262 = new int[]{0, 10, -10, 25, -25};
    private static final Identifier WIDGETS_TEX = new Identifier("textures/gui/advancements/widgets.png");
    private static final Pattern BACKSLASH_S_PATTERN = Pattern.compile("(.+) \\S+");
    private final AdvancementsEnlargerTab tab;
    private final Advancement advancement;
    private final AdvancementDisplay display;
    private final OrderedText title;
    private final int width;
    private final List<OrderedText> description;
    private final MinecraftClient client;
    private final List<AdvancementsEnlargerWidget> children = Lists.newArrayList();
    private final int xPos;
    private final int yPos;
    private AdvancementsEnlargerWidget parent;
    private AdvancementProgress progress;

    public AdvancementsEnlargerWidget(AdvancementsEnlargerTab tab, MinecraftClient client, Advancement advancement, AdvancementDisplay display) {
        this.tab = tab;
        this.advancement = advancement;
        this.display = display;
        this.client = client;
        this.title = Language.getInstance().reorder(client.textRenderer.trimToWidth(display.getTitle(), 163));
        this.xPos = MathHelper.floor(display.getX() * 28.0F);
        this.yPos = MathHelper.floor(display.getY() * 27.0F);
        int i = advancement.getRequirementCount();
        // atd start
        BlockedAction action = AchieveToDoMod.getBlockedActionFromAdvancement(advancement);
        if (action != null) {
            i = action.getAchievementsCountToUnlock();
        }
        // atd end
        int j = String.valueOf(i).length();
        int k = i > 1 ? client.textRenderer.getWidth("  ") + client.textRenderer.getWidth("0") * j * 2 + client.textRenderer.getWidth("/") : 0;
        int l = 29 + client.textRenderer.getWidth(this.title) + k;
        Text description = display.getDescription();
        this.description = Language.getInstance().reorder(this.wrapDescription(description, l));

        OrderedText string2;
        for (Iterator<OrderedText> var10 = this.description.iterator(); var10.hasNext(); l = Math.max(l, client.textRenderer.getWidth(string2))) {
            string2 = var10.next();
        }

        this.width = l + 3 + 5;
    }

    private List<StringVisitable> wrapDescription(Text text, int width) {
        TextHandler textHandler = this.client.textRenderer.getTextHandler();
        List<StringVisitable> list = null;
        float f = Float.MAX_VALUE;
        int[] var6 = field_24262;
        int var7 = var6.length;

        for (int i : var6) {
            List<StringVisitable> list2 = textHandler.wrapLines(text, width - i, Style.EMPTY);
            float g = Math.abs(method_27572(textHandler, list2) - (float) width);
            if (g <= 10.0F) {
                return list2;
            }

            if (g < f) {
                f = g;
                list = list2;
            }
        }

        return list;
    }

    private static float method_27572(TextHandler textHandler, List<StringVisitable> list) {
        Stream<StringVisitable> var10000 = list.stream();
        return (float) var10000.mapToDouble(textHandler::getWidth).max().orElse(0.0D);
    }

    private AdvancementsEnlargerWidget getParent(Advancement advancement) {
        do {
            advancement = advancement.getParent();
        } while (advancement != null && advancement.getDisplay() == null);

        if (advancement != null && advancement.getDisplay() != null) {
            return this.tab.getWidget(advancement);
        } else {
            return null;
        }
    }

    public void renderLines(MatrixStack matrices, int x, int y, boolean firstPass) {
        if (this.parent != null) {
            int i = x + this.parent.xPos + 13;
            int j = x + this.parent.xPos + 26 + 4;
            int k = y + this.parent.yPos + 13;
            int l = x + this.xPos + 13;
            int m = y + this.yPos + 13;
            int n = firstPass ? -16777216 : -1;
            if (firstPass) {
                this.drawHorizontalLine(matrices, j, i, k - 1, n);
                this.drawHorizontalLine(matrices, j + 1, i, k, n);
                this.drawHorizontalLine(matrices, j, i, k + 1, n);
                this.drawHorizontalLine(matrices, l, j - 1, m - 1, n);
                this.drawHorizontalLine(matrices, l, j - 1, m, n);
                this.drawHorizontalLine(matrices, l, j - 1, m + 1, n);
                this.drawVerticalLine(matrices, j - 1, m, k, n);
                this.drawVerticalLine(matrices, j + 1, m, k, n);
            } else {
                this.drawHorizontalLine(matrices, j, i, k, n);
                this.drawHorizontalLine(matrices, l, j, m, n);
                this.drawVerticalLine(matrices, j, m, k, n);
            }
        }

        for (AdvancementsEnlargerWidget advancementWidget : this.children) {
            advancementWidget.renderLines(matrices, x, y, firstPass);
        }
    }

    public void renderWidgets(MatrixStack matrices, int x, int y) {
        if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
            float f = this.progress == null ? 0.0F : this.progress.getProgressBarPercentage();
            AdvancementObtainedStatus advancementObtainedStatus2;
            if (f >= 1.0F) {
                advancementObtainedStatus2 = AdvancementObtainedStatus.OBTAINED;
            } else {
                advancementObtainedStatus2 = AdvancementObtainedStatus.UNOBTAINED;
            }
            // atd start
            BlockedAction action = AchieveToDoMod.getBlockedActionFromAdvancement(advancement);
            if (action != null) {
                if (action.isUnlocked()) {
                    advancementObtainedStatus2 = AdvancementObtainedStatus.OBTAINED;
                } else {
                    advancementObtainedStatus2 = AdvancementObtainedStatus.UNOBTAINED;
                }
            }
            // atd end

            RenderSystem.setShaderTexture(0, WIDGETS_TEX);
            this.drawTexture(matrices, x + this.xPos + 3, y + this.yPos, this.display.getFrame().getTextureV(), 128 + advancementObtainedStatus2.getSpriteIndex() * 26, 26, 26);
            Vector4f vector4f = matrices.peek().getPositionMatrix().transform(new Vector4f(x + this.xPos + 8, y + this.yPos + 5, 0, 1.0F));
            this.client.getItemRenderer().zOffset += vector4f.z();
            // atd if
            if (action != null && f < 1.0F && !action.isUnlocked()) {
                this.client.getItemRenderer().renderInGui(new ItemStack(AchieveToDoMod.MYSTERY_MASK_ITEM), (int) vector4f.x(), (int) vector4f.y());
            } else
                this.client.getItemRenderer().renderInGui(this.display.getIcon(), (int) vector4f.x(), (int) vector4f.y());
            this.client.getItemRenderer().zOffset -= vector4f.z();
        }

        for (AdvancementsEnlargerWidget advancementWidget : this.children) {
            advancementWidget.renderWidgets(matrices, x, y);
        }
    }

    public void setProgress(AdvancementProgress progress) {
        this.progress = progress;
    }

    public void addChild(AdvancementsEnlargerWidget widget) {
        this.children.add(widget);
    }

    public void drawTooltip(MatrixStack matrices, int originX, int originY, float alpha, int x, int y) {
        boolean bl = x + originX + this.xPos + this.width + 26 >= this.tab.getScreen().width;
        String string = this.progress == null ? null : this.progress.getProgressBarFraction();
        // atd start
        BlockedAction action = AchieveToDoMod.getBlockedActionFromAdvancement(advancement);
        if (action != null) {
            int total = action.getAchievementsCountToUnlock();
            string = Math.min(total, AchieveToDoMod.lastAchievementsCount) + "/" + total;
        }
        // atd end
        int i = string == null ? 0 : this.client.textRenderer.getWidth(string);
        int var10000 = 113 - originY - this.yPos - 26;
        int var10002 = this.description.size();
        boolean bl2 = var10000 <= 6 + var10002 * 9;
        float f = this.progress == null ? 0.0F : this.progress.getProgressBarPercentage();
        // atd start
        if (action != null) {
            float total = (float) action.getAchievementsCountToUnlock();
            f = Math.min(total, AchieveToDoMod.lastAchievementsCount) / total;
        }
        // atd end
        int j = MathHelper.floor(f * (float) this.width);
        AdvancementObtainedStatus advancementObtainedStatus10;
        AdvancementObtainedStatus advancementObtainedStatus11;
        AdvancementObtainedStatus advancementObtainedStatus12;
        if (f >= 1.0F) {
            j = this.width / 2;
            advancementObtainedStatus10 = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus11 = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus12 = AdvancementObtainedStatus.OBTAINED;
        } else if (j < 2) {
            j = this.width / 2;
            advancementObtainedStatus10 = AdvancementObtainedStatus.UNOBTAINED;
            advancementObtainedStatus11 = AdvancementObtainedStatus.UNOBTAINED;
            advancementObtainedStatus12 = AdvancementObtainedStatus.UNOBTAINED;
        } else if (j > this.width - 2) {
            j = this.width / 2;
            advancementObtainedStatus10 = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus11 = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus12 = AdvancementObtainedStatus.UNOBTAINED;
        } else {
            advancementObtainedStatus10 = AdvancementObtainedStatus.OBTAINED;
            advancementObtainedStatus11 = AdvancementObtainedStatus.UNOBTAINED;
            advancementObtainedStatus12 = AdvancementObtainedStatus.UNOBTAINED;
        }

        int k = this.width - j;
        RenderSystem.setShaderTexture(0, WIDGETS_TEX);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        int l = originY + this.yPos;
        int n;
        if (bl) {
            n = originX + this.xPos - this.width + 26 + 6;
        } else {
            n = originX + this.xPos;
        }

        int var10001 = this.description.size();
        int o = 32 + var10001 * 9;
        if (!this.description.isEmpty()) {
            if (bl2) {
                this.method_2324(matrices, n, l + 26 - o, this.width, o, 10, 200, 26, 0, 52);
            } else {
                this.method_2324(matrices, n, l, this.width, o, 10, 200, 26, 0, 52);
            }
        }

        this.drawTexture(matrices, n, l, 0, advancementObtainedStatus10.getSpriteIndex() * 26, j, 26);
        this.drawTexture(matrices, n + j, l, 200 - k, advancementObtainedStatus11.getSpriteIndex() * 26, k, 26);
        this.drawTexture(matrices, originX + this.xPos + 3, originY + this.yPos, this.display.getFrame().getTextureV(), 128 + advancementObtainedStatus12.getSpriteIndex() * 26, 26, 26);
        if (bl) {
            this.client.textRenderer.drawWithShadow(matrices, this.title, (float) (n + 5), (float) (originY + this.yPos + 9), -1);
            if (string != null) {
                this.client.textRenderer.drawWithShadow(matrices, string, (float) (originX + this.xPos - i), (float) (originY + this.yPos + 9), -1);
            }
        } else {
            this.client.textRenderer.drawWithShadow(matrices, this.title, (float) (originX + this.xPos + 32), (float) (originY + this.yPos + 9), -1);
            if (string != null) {
                this.client.textRenderer.drawWithShadow(matrices, string, (float) (originX + this.xPos + this.width - i - 5), (float) (originY + this.yPos + 9), -1);
            }
        }

        int p;
        int var10003;
        TextRenderer var20;
        OrderedText var21;
        float var22;
        if (bl2) {
            for (p = 0; p < this.description.size(); ++p) {
                var20 = this.client.textRenderer;
                var21 = this.description.get(p);
                var22 = (float) (n + 5);
                var10003 = l + 26 - o + 7;
                var20.draw(matrices, var21, var22, (float) (var10003 + p * 9), -5592406);
            }
        } else {
            for (p = 0; p < this.description.size(); ++p) {
                var20 = this.client.textRenderer;
                var21 = this.description.get(p);
                var22 = (float) (n + 5);
                var10003 = originY + this.yPos + 9 + 17;
                var20.draw(matrices, var21, var22, (float) (var10003 + p * 9), -5592406);
            }
        }

        Vector4f vector4f = matrices.peek().getPositionMatrix().transform(new Vector4f(originX + this.xPos + 8, originY + this.yPos + 5, 0, 1.0F));
        this.client.getItemRenderer().zOffset += vector4f.z();
        this.client.getItemRenderer().renderInGui(this.display.getIcon(), (int) vector4f.x(), (int) vector4f.y());
        this.client.getItemRenderer().zOffset -= vector4f.z();
    }

    protected void method_2324(MatrixStack matrices, int i, int j, int k, int l, int m, int n, int o, int p, int q) {
        this.drawTexture(matrices, i, j, p, q, m, m);
        this.method_2321(matrices, i + m, j, k - m - m, m, p + m, q, n - m - m, o);
        this.drawTexture(matrices, i + k - m, j, p + n - m, q, m, m);
        this.drawTexture(matrices, i, j + l - m, p, q + o - m, m, m);
        this.method_2321(matrices, i + m, j + l - m, k - m - m, m, p + m, q + o - m, n - m - m, o);
        this.drawTexture(matrices, i + k - m, j + l - m, p + n - m, q + o - m, m, m);
        this.method_2321(matrices, i, j + m, m, l - m - m, p, q + m, n, o - m - m);
        this.method_2321(matrices, i + m, j + m, k - m - m, l - m - m, p + m, q + m, n - m - m, o - m - m);
        this.method_2321(matrices, i + k - m, j + m, m, l - m - m, p + n - m, q + m, n, o - m - m);
    }

    protected void method_2321(MatrixStack matrices, int i, int j, int k, int l, int m, int n, int o, int p) {
        for (int q = 0; q < k; q += o) {
            int r = i + q;
            int s = Math.min(o, k - q);

            for (int t = 0; t < l; t += p) {
                int u = j + t;
                int v = Math.min(p, l - t);
                this.drawTexture(matrices, r, u, m, n, s, v);
            }
        }

    }

    public boolean shouldRender(int originX, int originY, int mouseX, int mouseY) {
        if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
            int i = originX + this.xPos;
            int j = i + 26;
            int k = originY + this.yPos;
            int l = k + 26;
            if (mouseX >= i && mouseX <= j && mouseY >= k && mouseY <= l) {
                BlockedAction action = AchieveToDoMod.getBlockedActionFromAdvancement(advancement);
                return action == null || action.isUnlocked() || this.progress != null && this.progress.isDone();
            }
        }
        return false;
    }

    public void addToTree() {
        if (this.parent == null && this.advancement.getParent() != null) {
            this.parent = this.getParent(this.advancement);
            if (this.parent != null) {
                this.parent.addChild(this);
            }
        }

    }

    public int getY() {
        return this.yPos;
    }

    public int getX() {
        return this.xPos;
    }
}