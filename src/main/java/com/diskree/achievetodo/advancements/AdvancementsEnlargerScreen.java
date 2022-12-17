/*
 * Advancements Enlarger by shedaniel.
 * Licensed under the CC-BY-NC-4.0.
 */

package com.diskree.achievetodo.advancements;

import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.AdvancementTabC2SPacket;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;

import java.util.Iterator;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public class AdvancementsEnlargerScreen extends Screen implements ClientAdvancementManager.Listener {
    private static final Identifier WINDOW_TEXTURE = new Identifier("achievetodo:textures/gui/advancements/recipecontainer.png");
    private static final Identifier WINDOW_DARK_TEXTURE = new Identifier("achievetodo:textures/gui/advancements/recipecontainer_dark.png");
    private static final Identifier TABS_TEXTURE = new Identifier("textures/gui/advancements/tabs.png");
    private static final Identifier TABS_DARK_TEXTURE = new Identifier("achievetodo:textures/gui/advancements/tabs_dark.png");
    private final ClientAdvancementManager advancementHandler;
    private final Map<Advancement, AdvancementsEnlargerTab> tabs = Maps.newLinkedHashMap();
    private AdvancementsEnlargerTab selectedTab;
    private boolean movingTab;
    private Supplier<Boolean> reiExists = Suppliers.memoize(() -> FabricLoader.getInstance().isModLoaded("roughlyenoughitems"));
    private BooleanSupplier darkMode = () -> {
        if (!reiExists.get())
            return false;
        try {
            Object reiHelper = Class.forName("me.shedaniel.rei.api.REIHelper").getDeclaredMethod("getInstance").invoke(null);
            return (boolean) Class.forName("me.shedaniel.rei.api.REIHelper").getDeclaredMethod("isDarkThemeEnabled").invoke(reiHelper);
        } catch (Throwable ignored) {
        }
        return false;
    };

    public AdvancementsEnlargerScreen(ClientAdvancementManager clientAdvancementManager) {
        super(NarratorManager.EMPTY);
        this.advancementHandler = clientAdvancementManager;
    }

    private boolean isDarkMode() {
        try {
            return darkMode.getAsBoolean();
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    protected void init() {
        this.tabs.clear();
        this.selectedTab = null;
        this.advancementHandler.setListener(this);
        if (this.selectedTab == null && !this.tabs.isEmpty()) {
            this.advancementHandler.selectTab((this.tabs.values().iterator().next()).getRoot(), true);
        } else {
            this.advancementHandler.selectTab(this.selectedTab == null ? null : this.selectedTab.getRoot(), true);
        }
    }

    @Override
    public void removed() {
        this.advancementHandler.setListener(null);
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.getNetworkHandler();
        if (clientPlayNetworkHandler != null) {
            clientPlayNetworkHandler.sendPacket(AdvancementTabC2SPacket.close());
        }
    }

    @Override
    public boolean mouseScrolled(double d, double e, double amount) {
        if (selectedTab == null)
            return false;
        selectedTab.scroll(amount);
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int i = 8;
            int j = 33;

            for (AdvancementsEnlargerTab advancementTab : this.tabs.values()) {
                if (advancementTab.isClickOnTab(i, j, mouseX, mouseY)) {
                    this.advancementHandler.selectTab(advancementTab.getRoot(), true);
                    break;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.client.options.advancementsKey.matchesKey(keyCode, scanCode)) {
            this.client.setScreen(null);
            this.client.mouse.lockCursor();
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int i = 8;
        int j = 33;
        this.renderBackground(matrices);
        this.drawAdvancementTree(matrices, mouseX, mouseY, i, j);
        this.drawWidgets(matrices, i, j);
        this.drawWidgetTooltip(matrices, mouseX, mouseY, i, j);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) {
            this.movingTab = false;
            return false;
        } else {
            if (!this.movingTab) {
                this.movingTab = true;
            } else if (this.selectedTab != null) {
                this.selectedTab.move(deltaX, deltaY);
            }

            return true;
        }
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    private void drawAdvancementTree(MatrixStack matrices, int mouseX, int mouseY, int x, int i) {
        AdvancementsEnlargerTab advancementTab = this.selectedTab;
        if (advancementTab == null) {
            fill(matrices, x + 9, i + 18, width - 9, height - 17, -16777216);
            String string = I18n.translate("advancements.empty");
            int j = this.textRenderer.getWidth(string);
            textRenderer.draw(matrices, string, (width - j) / 2, (height - 33) / 2 + 33 - 9 / 2, -1);
            textRenderer.draw(matrices, ":(", (width - this.textRenderer.getWidth(":(")) / 2, (height - 33) / 2 + 33 + 9 + 9 / 2, -1);
        } else {
            matrices.push();
            matrices.translate((float) (x + 9), (float) (i + 18), 0.0F);
            advancementTab.render(matrices);
            matrices.pop();
            RenderSystem.depthFunc(515);
            RenderSystem.disableDepthTest();
        }
    }

    public void drawWidgets(MatrixStack matrices, int x, int i) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        drawWindow(matrices, x, i);
        if (this.tabs.size() > 1) {
            RenderSystem.setShaderTexture(0, isDarkMode() ? TABS_DARK_TEXTURE : TABS_TEXTURE);
            Iterator<AdvancementsEnlargerTab> var3 = this.tabs.values().iterator();

            AdvancementsEnlargerTab advancementTab2;
            while (var3.hasNext()) {
                advancementTab2 = var3.next();
                advancementTab2.drawBackground(matrices, x, i, advancementTab2 == this.selectedTab);
            }

            RenderSystem.defaultBlendFunc();
            var3 = this.tabs.values().iterator();

            while (var3.hasNext()) {
                advancementTab2 = var3.next();
                advancementTab2.drawIcon(matrices, x, i, this.itemRenderer);
            }

            RenderSystem.disableBlend();
        }

        this.textRenderer.draw(matrices, MutableText.of(new TranslatableTextContent("gui.advancements")), (float) (x + 8), (float) (i + 6), isDarkMode() ? -1 : 4210752);
    }

    private void drawWindow(MatrixStack matrices, int x, int y) {
        boolean darkMode = isDarkMode();
        RenderSystem.setShaderTexture(0, !darkMode ? WINDOW_TEXTURE : WINDOW_DARK_TEXTURE);
        int width = this.width - 16;
        int height = this.height - 41;
        //Four Corners
        this.drawTexture(matrices, x, y, 106, 124 + 66, 4, 4);
        this.drawTexture(matrices, x + width - 4, y, 252, 124 + 66, 4, 4);
        this.drawTexture(matrices, x, y + height - 4, 106, 186 + 66, 4, 4);
        this.drawTexture(matrices, x + width - 4, y + height - 4, 252, 186 + 66, 4, 4);

        //Sides
        for (int xx = 4; xx < width - 4; xx += 128) {
            int thisWidth = Math.min(128, width - 4 - xx);
            this.drawTexture(matrices, x + xx, y, 110, 124 + 66, thisWidth, 4);
            this.drawTexture(matrices, x + xx, y + height - 4, 110, 186 + 66, thisWidth, 4);
        }
        for (int yy = 4; yy < height - 4; yy += 50) {
            int thisHeight = Math.min(50, height - 4 - yy);
            this.drawTexture(matrices, x, y + yy, 106, 128 + 66, 4, thisHeight);
            this.drawTexture(matrices, x + width - 4, y + yy, 252, 128 + 66, 4, thisHeight);
        }
        int color = darkMode ? -13750738 : -3750202;
        fillGradient(matrices, x + 4, y + 4, x + width - 4, y + 18, color, color);
        fillGradient(matrices, x + 4, y + 4, x + 9, y + height - 4, color, color);
        fillGradient(matrices, x + width - 9, y + 4, x + width - 4, y + height - 4, color, color);
        fillGradient(matrices, x + 4, y + height - 9, x + width - 4, y + height - 4, color, color);
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        Tessellator tessellator = Tessellator.getInstance();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        int zOffset = getZOffset();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x + width - 9, y + 18, zOffset).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 9, y + 18, zOffset).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 9, y + 24, zOffset).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + width - 9, y + 24, zOffset).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + width - 9, y + height - 9 - 9, zOffset).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + 9, y + height - 9 - 9, zOffset).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + 9, y + height - 9, zOffset).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + width - 9, y + height - 9, zOffset).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 15, y + 18, zOffset).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + 9, y + 18, zOffset).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 9, y + height - 9, zOffset).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 15, y + height - 9, zOffset).color(0, 0, 0, 0).next();

        bufferBuilder.vertex(x + width - 9, y + 18, zOffset).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + width - 9 - 9, y + 18, zOffset).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + width - 9 - 9, y + height - 9, zOffset).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + width - 9, y + height - 9, zOffset).color(0, 0, 0, 150).next();
        tessellator.draw();
        RenderSystem.disableBlend();
        RenderSystem.enableTexture();
    }

    private void drawWidgetTooltip(MatrixStack matrices, int mouseX, int mouseY, int x, int y) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.selectedTab != null) {
            matrices.push();
            RenderSystem.enableDepthTest();
            matrices.translate((float) (x + 9), (float) (y + 18), 400.0F);
            this.selectedTab.drawWidgetTooltip(matrices, mouseX - x - 9, mouseY - y - 18, x, y);
            RenderSystem.disableDepthTest();
            matrices.pop();
        }

        if (this.tabs.size() > 1) {
            for (AdvancementsEnlargerTab advancementTab : this.tabs.values()) {
                if (advancementTab.isClickOnTab(x, y, mouseX, mouseY)) {
                    this.renderTooltip(matrices, advancementTab.getTitle(), mouseX, mouseY);
                }
            }
        }

    }

    public void onRootAdded(Advancement root) {
        try {
            AdvancementsEnlargerTab advancementTab = AdvancementsEnlargerTab.create(this.client, this, this.tabs.size(), root);
            if (advancementTab != null) {
                this.tabs.put(root, advancementTab);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onRootRemoved(Advancement root) {
    }

    public void onDependentAdded(Advancement dependent) {
        AdvancementsEnlargerTab advancementTab = this.getTab(dependent);
        if (advancementTab != null) {
            advancementTab.addAdvancement(dependent);
        }

    }

    public void onDependentRemoved(Advancement dependent) {
    }

    @Override
    public void setProgress(Advancement advancement, AdvancementProgress advancementProgress) {
        AdvancementsEnlargerWidget advancementWidget = this.getAdvancementWidget(advancement);
        if (advancementWidget != null) {
            advancementWidget.setProgress(advancementProgress);
        }

    }

    @Override
    public void selectTab(Advancement advancement) {
        this.selectedTab = this.tabs.get(advancement);
    }

    public void onClear() {
        this.tabs.clear();
        this.selectedTab = null;
    }

    public AdvancementsEnlargerWidget getAdvancementWidget(Advancement advancement) {
        AdvancementsEnlargerTab advancementTab = this.getTab(advancement);
        return advancementTab == null ? null : advancementTab.getWidget(advancement);
    }

    private AdvancementsEnlargerTab getTab(Advancement advancement) {
        while (advancement.getParent() != null) {
            advancement = advancement.getParent();
        }

        return this.tabs.get(advancement);
    }
}
