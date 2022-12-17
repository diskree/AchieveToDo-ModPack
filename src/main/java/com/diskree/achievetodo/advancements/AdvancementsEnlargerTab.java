package com.diskree.achievetodo.advancements;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class AdvancementsEnlargerTab extends DrawableHelper {
    private final MinecraftClient client;
    private final AdvancementsEnlargerScreen screen;
    private final AdvancementsEnlargerTabTypeHooks type;
    private final int index;
    private final Advancement root;
    private final AdvancementDisplay display;
    private final ItemStack icon;
    private final Text title;
    private final AdvancementsEnlargerWidget rootWidget;
    private final Map<Advancement, AdvancementsEnlargerWidget> widgets = Maps.newLinkedHashMap();
    private double originX;
    private double originY;
    private int minPanX = 2147483647;
    private int minPanY = 2147483647;
    private int maxPanX = -2147483648;
    private int maxPanY = -2147483648;
    private float alpha;
    private boolean initialized;

    public AdvancementsEnlargerTab(MinecraftClient client, AdvancementsEnlargerScreen screen, AdvancementsEnlargerTabTypeHooks type, int index, Advancement root, AdvancementDisplay display) {
        this.client = client;
        this.screen = screen;
        this.type = type;
        this.index = index;
        this.root = root;
        this.display = display;
        this.icon = display.getIcon();
        this.title = display.getTitle();
        this.rootWidget = new AdvancementsEnlargerWidget(this, client, root, display);
        this.addWidget(this.rootWidget, root);
    }

    public static AdvancementsEnlargerTab create(MinecraftClient minecraft, AdvancementsEnlargerScreen screen, int index, Advancement root)
            throws ClassNotFoundException {
        if (root.getDisplay() != null) {
            Object[] var4 = Class.forName(FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_453")).getEnumConstants();
            int var5 = var4.length;

            for (Object o : var4) {
                AdvancementsEnlargerTabTypeHooks advancementTabType = (AdvancementsEnlargerTabTypeHooks) o;
                return new AdvancementsEnlargerTab(minecraft, screen, advancementTabType, index, root, root.getDisplay());
            }
        }
        return null;
    }

    public Advancement getRoot() {
        return this.root;
    }

    public Text getTitle() {
        return this.title;
    }

    public void drawBackground(MatrixStack matrices, int x, int y, boolean selected) {
        this.type.drawBackground(matrices, this, x, y, selected, this.index);
    }

    public void drawIcon(MatrixStack matrices, int x, int y, ItemRenderer itemRenderer) {
        this.type.drawIcon(matrices, x, y, this.index, itemRenderer, this.icon);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    public void render(MatrixStack matrices) {
        int width = screen.width - 34;
        int height = screen.height - 68;
        if (!this.initialized) {
            this.originX = width / 2 - (this.maxPanX + this.minPanX) / 2;
            this.originY = height / 2 - (this.maxPanY + this.minPanY) / 2;
            this.initialized = true;
        }

        matrices.push();
        RenderSystem.enableDepthTest();
        matrices.translate(0.0F, 0.0F, 950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrices, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        matrices.translate(0.0F, 0.0F, -950.0F);
        RenderSystem.depthFunc(518);
        fill(matrices, width, height, 0, 0, -16777216);
        RenderSystem.depthFunc(515);
        Identifier identifier = this.display.getBackground();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        if (identifier != null) {
            RenderSystem.setShaderTexture(0, identifier);
        } else {
            RenderSystem.setShaderTexture(0, TextureManager.MISSING_IDENTIFIER);
        }

        int i = MathHelper.floor(this.originX);
        int j = MathHelper.floor(this.originY);
        int k = i % 16;
        int l = j % 16;

        for (int m = -1; m <= MathHelper.ceil(width / 16f) + 1; ++m) {
            for (int n = -1; n <= MathHelper.ceil(height / 16f) + 1; ++n) {
                drawTexture(matrices, k + 16 * m, l + 16 * n, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }

        this.rootWidget.renderLines(matrices, i, j, true);
        this.rootWidget.renderLines(matrices, i, j, false);
        this.rootWidget.renderWidgets(matrices, i, j);
        RenderSystem.depthFunc(518);
        matrices.translate(0.0F, 0.0F, -950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrices, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        matrices.translate(0.0F, 0.0F, 950.0F);
        RenderSystem.depthFunc(515);
        matrices.pop();
    }

    public void drawWidgetTooltip(MatrixStack matrices, int mouseX, int mouseY, int x, int y) {
        int width = screen.width - 34;
        int height = screen.height - 68;
        matrices.push();
        matrices.translate(0.0F, 0.0F, 200.0F);
        fill(matrices, 0, 0, width, height, MathHelper.floor(this.alpha * 255.0F) << 24);
        boolean bl = false;
        int i = MathHelper.floor(this.originX);
        int j = MathHelper.floor(this.originY);
        if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height) {
            for (AdvancementsEnlargerWidget advancementWidget : this.widgets.values()) {
                if (advancementWidget.shouldRender(i, j, mouseX, mouseY)) {
                    bl = true;
                    advancementWidget.drawTooltip(matrices, i, j, this.alpha, x, y);
                    break;
                }
            }
        }

        matrices.pop();
        if (bl) {
            this.alpha = MathHelper.clamp(this.alpha + 0.02F, 0.0F, 0.3F);
        } else {
            this.alpha = MathHelper.clamp(this.alpha - 0.04F, 0.0F, 1.0F);
        }

    }

    public boolean isClickOnTab(int screenX, int screenY, double mouseX, double mouseY) {
        return this.type.isClickOnTab(screenX, screenY, this.index, mouseX, mouseY);
    }

    public void scroll(double amount) {
        int width = screen.width - 34;
        int height = screen.height - 68;
        if (this.maxPanX - this.minPanX > width) {
            move(amount * 10, 0);
            return;
        }
        if (this.maxPanY - this.minPanY > height) {
            move(0, amount * 10);
        }
    }

    public void move(double offsetX, double offsetY) {
        int width = screen.width - 34;
        int height = screen.height - 68;
        if (this.maxPanX - this.minPanX > width) {
            this.originX = MathHelper.clamp(this.originX + offsetX, -(this.maxPanX - width), 0.0D);
        }

        if (this.maxPanY - this.minPanY > height) {
            this.originY = MathHelper.clamp(this.originY + offsetY, -(this.maxPanY - height), 0.0D);
        }

    }

    public void addAdvancement(Advancement advancement) {
        if (advancement.getDisplay() != null) {
            AdvancementsEnlargerWidget advancementWidget = new AdvancementsEnlargerWidget(this, this.client, advancement, advancement.getDisplay());
            this.addWidget(advancementWidget, advancement);
        }
    }

    private void addWidget(AdvancementsEnlargerWidget widget, Advancement advancement) {
        this.widgets.put(advancement, widget);
        int i = widget.getX();
        int j = i + 28;
        int k = widget.getY();
        int l = k + 27;
        this.minPanX = Math.min(this.minPanX, i);
        this.maxPanX = Math.max(this.maxPanX, j);
        this.minPanY = Math.min(this.minPanY, k);
        this.maxPanY = Math.max(this.maxPanY, l);

        for (AdvancementsEnlargerWidget advancementWidget : this.widgets.values()) {
            advancementWidget.addToTree();
        }
    }

    public AdvancementsEnlargerWidget getWidget(Advancement advancement) {
        return this.widgets.get(advancement);
    }

    public AdvancementsEnlargerScreen getScreen() {
        return this.screen;
    }
}
