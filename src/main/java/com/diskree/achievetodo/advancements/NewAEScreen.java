package com.diskree.achievetodo.advancements;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(value=EnvType.CLIENT)
public class NewAEScreen
        extends Screen
        implements ClientAdvancementManager.Listener {
    private static final Identifier WINDOW_TEXTURE = new Identifier("textures/gui/advancements/window.png");
    private static final Identifier TABS_TEXTURE = new Identifier("textures/gui/advancements/tabs.png");
    private final ClientAdvancementManager advancementHandler;
    private final Map<Advancement, AdvancementTab> tabs = Maps.newLinkedHashMap();
    private AdvancementTab selectedTab;
    private boolean movingTab;

    public NewAEScreen(ClientAdvancementManager clientAdvancementManager) {
        super(NarratorManager.EMPTY);
        this.advancementHandler = clientAdvancementManager;
    }

    @Override
    protected void init() {
        this.tabs.clear();
        this.selectedTab = null;
        this.advancementHandler.setListener(this);
        if (this.selectedTab == null && !this.tabs.isEmpty()) {
            this.advancementHandler.selectTab(this.tabs.values().iterator().next().getRoot(), true);
        } else {
            this.advancementHandler.selectTab(this.selectedTab == null ? null : this.selectedTab.getRoot(), true);
        }
    }

    @Override
    public void removed() {
        this.advancementHandler.setListener(null);
        ClientPlayNetworkHandler clientPlayNetworkHandler = this.minecraft.getNetworkHandler();
        if (clientPlayNetworkHandler != null) {
            clientPlayNetworkHandler.sendPacket(AdvancementTabC2SPacket.close());
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            int i = 8;
            int j = 33;
            for (AdvancementTab advancementTab : this.tabs.values()) {
                if (!advancementTab.isClickOnTab(i, j, mouseX, mouseY)) continue;
                this.advancementHandler.selectTab(advancementTab.getRoot(), true);
                break;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.minecraft.options.keyAdvancements.matchesKey(keyCode, scanCode)) {
            this.minecraft.openScreen(null);
            this.minecraft.mouse.lockCursor();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        int i = 8;
        int j = 33;
        this.renderBackground();
        this.drawAdvancementTree(mouseX, mouseY, i, j);
        this.drawWidgets(i, j);
        this.drawWidgetTooltip(mouseX, mouseY, i, j);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button != 0) {
            this.movingTab = false;
            return false;
        }
        if (!this.movingTab) {
            this.movingTab = true;
        } else if (this.selectedTab != null) {
            this.selectedTab.move(deltaX, deltaY);
        }
        return true;
    }

    private void drawAdvancementTree(int mouseX, int mouseY, int x, int i) {
        AdvancementTab advancementTab = this.selectedTab;
        if (advancementTab == null) {
            fill(x + 9, i + 18, width - 9, height - 17, -16777216);
            String string = I18n.translate("advancements.empty", new Object[0]);
            int j = this.font.getStringWidth(string);
            font.draw(string, (width - j) / 2, (height - 33) / 2 + 33 - 9 / 2, -1);
            font.draw(":(", (width - this.font.getStringWidth(":(")) / 2, (height - 33) / 2 + 33 + 9 + 9 / 2, -1);
            return;
        }
        RenderSystem.pushMatrix();
        RenderSystem.translatef(x + 9, i + 18, 0.0f);
        advancementTab.render();
        RenderSystem.popMatrix();
        RenderSystem.depthFunc(515);
        RenderSystem.disableDepthTest();
    }

    public void drawWidgets(int x, int i) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        this.minecraft.getTextureManager().bindTexture(WINDOW_TEXTURE);
        int width = this.width - 16;
        int height = this.height - 41;
        this.blit(x, i, 106, 124 + 66, 4, 4);
        this.blit(x + width - 4, i, 252, 124 + 66, 4, 4);
        this.blit(x, i + height - 4, 106, 186 + 66, 4, 4);
        this.blit(x + width - 4, i + height - 4, 252, 186 + 66, 4, 4);
        for(int xx = 4; xx < width - 4; xx += 128) {
            int thisWidth = Math.min(128, width - 4 - xx);
            this.blit(x + xx, i, 110, 124 + 66, thisWidth, 4);
            this.blit(x + xx, i + height - 4, 110, 186 + 66, thisWidth, 4);
        }
        for(int yy = 4; yy < height - 4; yy += 50) {
            int thisHeight = Math.min(50, height - 4 - yy);
            this.blit(x, i + yy, 106, 128 + 66, 4, thisHeight);
            this.blit(x + width - 4, i + yy, 252, 128 + 66, 4, thisHeight);
        }
        int color = -3750202;
        fillGradient(x + 4, i + 4, x + width - 4, i + 18, color, color);
        fillGradient(x + 4, i + 4, x + 9, i + height - 4, color, color);
        fillGradient(x + width - 9, i + 4, x + width - 4, i + height - 4, color, color);
        fillGradient(x + 4, i + height - 9, x + width - 4, i + height - 4, color, color);
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x + width - 9, i + 18, getBlitOffset()).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 9, i + 18, getBlitOffset()).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 9, i + 24, getBlitOffset()).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + width - 9, i + 24, getBlitOffset()).color(0, 0, 0, 0).next();
        tessellator.draw();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x + width - 9, i + height - 9, getBlitOffset()).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 9, i + height - 9, getBlitOffset()).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 9, i + height - 15, getBlitOffset()).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + width - 9, i + height - 15, getBlitOffset()).color(0, 0, 0, 0).next();
        tessellator.draw();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x + 15, i + 18, getBlitOffset()).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + 9, i + 18, getBlitOffset()).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 9, i + height - 9, getBlitOffset()).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + 15, i + height - 9, getBlitOffset()).color(0, 0, 0, 0).next();
        tessellator.draw();
        bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(x + width - 15, i + 18, getBlitOffset()).color(0, 0, 0, 0).next();
        bufferBuilder.vertex(x + width - 9, i + 18, getBlitOffset()).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + width - 9, i + height - 9, getBlitOffset()).color(0, 0, 0, 150).next();
        bufferBuilder.vertex(x + width - 15, i + height - 9, getBlitOffset()).color(0, 0, 0, 0).next();
        tessellator.draw();
        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableTexture();
        if (this.tabs.size() > 1) {
            this.minecraft.getTextureManager().bindTexture(TABS_TEXTURE);
            for (AdvancementTab advancementTab : this.tabs.values()) {
                advancementTab.drawBackground(x, i, advancementTab == this.selectedTab);
            }
            RenderSystem.enableRescaleNormal();
            RenderSystem.defaultBlendFunc();
            for (AdvancementTab advancementTab : this.tabs.values()) {
                advancementTab.drawIcon(x, i, this.itemRenderer);
            }
            RenderSystem.disableBlend();
        }
        this.font.draw(I18n.translate("gui.advancements", new Object[0]), x + 8, i + 6, 0x404040);
    }

    private void drawWidgetTooltip(int mouseX, int mouseY, int x, int y) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.selectedTab != null) {
            RenderSystem.pushMatrix();
            RenderSystem.enableDepthTest();
            RenderSystem.translatef(x + 9, y + 18, 400.0f);
            this.selectedTab.drawWidgetTooltip(mouseX - x - 9, mouseY - y - 18, x, y);
            RenderSystem.disableDepthTest();
            RenderSystem.popMatrix();
        }
        if (this.tabs.size() > 1) {
            for (AdvancementTab advancementTab : this.tabs.values()) {
                if (!advancementTab.isClickOnTab(x, y, mouseX, mouseY)) continue;
                this.renderTooltip(advancementTab.getTitle(), mouseX, mouseY);
            }
        }
    }

    @Override
    public void onRootAdded(Advancement root) {
        AdvancementTab advancementTab = AdvancementTab.create(this.minecraft, this, this.tabs.size(), root);
        if (advancementTab == null) {
            return;
        }
        this.tabs.put(root, advancementTab);
    }

    @Override
    public void onRootRemoved(Advancement root) {
    }

    @Override
    public void onDependentAdded(Advancement dependent) {
        AdvancementTab advancementTab = this.getTab(dependent);
        if (advancementTab != null) {
            advancementTab.addAdvancement(dependent);
        }
    }

    @Override
    public void onDependentRemoved(Advancement dependent) {
    }

    @Override
    public void setProgress(Advancement advancement, AdvancementProgress advancementProgress) {
        AdvancementWidget advancementWidget = this.getAdvancementWidget(advancement);
        if (advancementWidget != null) {
            advancementWidget.setProgress(advancementProgress);
        }
    }

    @Override
    public void selectTab(@Nullable Advancement advancement) {
        this.selectedTab = this.tabs.get(advancement);
    }

    @Override
    public void onClear() {
        this.tabs.clear();
        this.selectedTab = null;
    }

    @Nullable
    public AdvancementWidget getAdvancementWidget(Advancement advancement) {
        AdvancementTab advancementTab = this.getTab(advancement);
        return advancementTab == null ? null : advancementTab.getWidget(advancement);
    }

    @Nullable
    private AdvancementTab getTab(Advancement advancement) {
        while (advancement.getParent() != null) {
            advancement = advancement.getParent();
        }
        return this.tabs.get(advancement);
    }
}

