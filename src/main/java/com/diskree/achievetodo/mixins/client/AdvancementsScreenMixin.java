package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.advancements.AdvancementsTab;
import com.diskree.achievetodo.client.AchieveToDoClient;
import net.minecraft.advancement.*;
import net.minecraft.block.Blocks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.*;
import java.util.function.BiConsumer;

@Mixin(AdvancementsScreen.class)
public abstract class AdvancementsScreenMixin extends Screen {

    @Unique
    private static final int SEARCH_RESULT_COLUMNS = 10;

    @Unique
    private static final Text SEARCH_HINT = Text.translatable("gui.recipebook.search_hint").formatted(Formatting.GRAY);

    @Unique
    private TextFieldWidget searchField;

    @Unique
    private PlacedAdvancement searchRootAdvancement;

    @Unique
    private AdvancementTab searchTab;

    @Unique
    private boolean isSearchActive;

    @Unique
    private void refreshSearchResults() {
        if (client == null || client.player == null) {
            return;
        }
        String query = searchField.getText().toLowerCase(Locale.ROOT);
        ArrayList<AdvancementEntry> advancements = new ArrayList<>(client.player.networkHandler.getAdvancementHandler().advancementProgresses.keySet());
        for (AdvancementWidget widget : searchTab.widgets.values()) {
            widget.parent = null;
            widget.children.clear();
        }
        searchTab.widgets.clear();
        searchTab.minPanX = Integer.MAX_VALUE;
        searchTab.minPanY = Integer.MAX_VALUE;
        searchTab.maxPanX = Integer.MIN_VALUE;
        searchTab.maxPanY = Integer.MIN_VALUE;
        searchTab.initialized = false;

        searchTab.addWidget(searchTab.rootWidget, searchRootAdvancement.getAdvancementEntry());

        int rowIndex = 0;
        int columnIndex = 0;
        ArrayList<PlacedAdvancement> searchResults = new ArrayList<>();
        for (AdvancementEntry advancement : advancements) {
            if (advancement == null || advancement.value().isRoot()) {
                continue;
            }
            PlacedAdvancement placedAdvancement = advancementHandler.getManager().get(advancement);
            if (placedAdvancement == null) {
                continue;
            }
            PlacedAdvancement rootAdvancement = PlacedAdvancement.findRoot(placedAdvancement);
            if (rootAdvancement == null) {
                continue;
            }
            AdvancementsTab tab = null;
            for (AdvancementsTab advancementsTab : AdvancementsTab.values()) {
                if (advancementsTab.getRootAdvancementPath().equals(rootAdvancement.getAdvancementEntry().id().getPath())) {
                    tab = advancementsTab;
                }
            }
            if (tab == null || tab == AdvancementsTab.BLOCKED_ACTIONS || tab == AdvancementsTab.HINTS) {
                continue;
            }
            AdvancementDisplay display = advancement.value().display().orElse(null);
            if (display == null || display.isHidden()) {
                continue;
            }
            String title = display.getTitle().getString().toLowerCase(Locale.ROOT);
            String description = display.getDescription().getString().toLowerCase(Locale.ROOT);
            if (title.contains(query) || description.contains(query)) {
                searchResults.add(placedAdvancement);
            }
        }

        List<AdvancementFrame> frameOrder = Arrays.asList(AdvancementFrame.TASK, AdvancementFrame.GOAL, AdvancementFrame.CHALLENGE);
        searchResults.sort(Comparator.comparing((advancement) -> advancement.getAdvancementEntry().id()));
        searchResults.sort((advancement1, advancement2) -> {
            AdvancementDisplay display1 = advancement1.getAdvancement().display().orElse(null);
            AdvancementDisplay display2 = advancement2.getAdvancement().display().orElse(null);
            if (display1 == null || display2 == null) {
                return 0;
            }
            int index1 = frameOrder.indexOf(display1.getFrame());
            int index2 = frameOrder.indexOf(display2.getFrame());
            return Integer.compare(index1, index2);
        });

        PlacedAdvancement parentPlacedAdvancement = new PlacedAdvancement(searchRootAdvancement.getAdvancementEntry(), null);
        for (PlacedAdvancement searchResult : searchResults) {
            AdvancementDisplay searchResultDisplay = searchResult.getAdvancement().display().orElse(null);
            if (searchResultDisplay == null) {
                continue;
            }
            AdvancementDisplay searchResultAdvancementDisplay = new AdvancementDisplay(
                    searchResultDisplay.getIcon(),
                    searchResultDisplay.getTitle(),
                    searchResultDisplay.getDescription(),
                    searchResultDisplay.getBackground(),
                    searchResultDisplay.getFrame(),
                    searchResultDisplay.shouldShowToast(),
                    searchResultDisplay.shouldAnnounceToChat(),
                    searchResultDisplay.isHidden()
            );
            searchResultAdvancementDisplay.setPos(columnIndex, rowIndex);

            Advancement.Builder searchResultAdvancementBuilder = Advancement.Builder.create()
                    .parent(parentPlacedAdvancement.getAdvancementEntry())
                    .display(searchResultAdvancementDisplay)
                    .rewards(searchResult.getAdvancement().rewards())
                    .requirements(searchResult.getAdvancement().requirements());
            searchResult.getAdvancement().criteria().forEach(searchResultAdvancementBuilder::criterion);
            if (searchResult.getAdvancement().sendsTelemetryEvent()) {
                searchResultAdvancementBuilder = searchResultAdvancementBuilder.sendsTelemetryEvent();
            }
            AdvancementEntry searchResultAdvancementEntry = searchResultAdvancementBuilder.build(searchResult.getAdvancementEntry().id());
            PlacedAdvancement searchResultPlacedAdvancement = new PlacedAdvancement(searchResultAdvancementEntry, parentPlacedAdvancement);

            searchTab.addAdvancement(searchResultPlacedAdvancement);
            searchTab.widgets.get(searchResultAdvancementEntry).setProgress(client.player.networkHandler.getAdvancementHandler().advancementProgresses.get(searchResultAdvancementEntry));
            if (columnIndex == SEARCH_RESULT_COLUMNS - 1) {
                parentPlacedAdvancement = new PlacedAdvancement(searchRootAdvancement.getAdvancementEntry(), null);
                columnIndex = 0;
                rowIndex++;
            } else {
                parentPlacedAdvancement = new PlacedAdvancement(searchResultAdvancementEntry, searchResultPlacedAdvancement);
                columnIndex++;
            }
        }
    }

    @Redirect(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientAdvancementManager;selectTab(Lnet/minecraft/advancement/AdvancementEntry;Z)V"))
    private void mouseClickedRedirect(ClientAdvancementManager instance, AdvancementEntry tab, boolean local) {
        isSearchActive = false;
        instance.selectTab(tab, true);
    }

    @Inject(method = "drawAdvancementTree", at = @At("HEAD"), cancellable = true)
    private void drawAdvancementTreeInject(DrawContext context, int mouseX, int mouseY, int x, int y, CallbackInfo ci) {
        if (isSearchActive && searchTab.widgets.size() > 1) {
            searchTab.render(context, x + 9, y + 18);
            ci.cancel();
        }
    }

    @ModifyArgs(method = "drawAdvancementTree", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V", ordinal = 0))
    private void drawAdvancementTreeModifyText(Args args) {
        if (isSearchActive) {
            args.set(1, Text.translatable("commands.random.no_advancements"));
        }
    }

    @ModifyArgs(method = "drawWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/advancement/AdvancementTab;drawBackground(Lnet/minecraft/client/gui/DrawContext;IIZ)V"))
    private void drawWindowModifyTabSelected(Args args) {
        if (isSearchActive) {
            args.set(3, false);
        }
    }

    @Redirect(method = "drawWidgetTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/advancement/AdvancementTab;drawWidgetTooltip(Lnet/minecraft/client/gui/DrawContext;IIII)V"))
    private void drawWidgetTooltipRedirectTab(AdvancementTab instance, DrawContext context, int mouseX, int mouseY, int x, int y) {
        if (isSearchActive) {
            instance = searchTab;
        }
        instance.drawWidgetTooltip(context, mouseX, mouseY, x, y);
    }

    @Redirect(method = "mouseScrolled", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/advancement/AdvancementsScreen;selectedTab:Lnet/minecraft/client/gui/screen/advancement/AdvancementTab;", opcode = Opcodes.GETFIELD))
    private AdvancementTab mouseScrolledRedirect(AdvancementsScreen instance) {
        return isSearchActive ? searchTab : selectedTab;
    }

    @Redirect(method = "mouseDragged", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/advancement/AdvancementsScreen;selectedTab:Lnet/minecraft/client/gui/screen/advancement/AdvancementTab;", opcode = Opcodes.GETFIELD))
    private AdvancementTab mouseDraggedRedirect(AdvancementsScreen instance) {
        return isSearchActive ? searchTab : selectedTab;
    }

    @Redirect(method = "drawAdvancementTree", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/advancement/AdvancementsScreen;selectedTab:Lnet/minecraft/client/gui/screen/advancement/AdvancementTab;", opcode = Opcodes.GETFIELD))
    private AdvancementTab drawAdvancementTreeRedirect(AdvancementsScreen instance) {
        return isSearchActive && searchTab.widgets.size() <= 1 ? null : selectedTab;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (searchField.charTyped(chr, modifiers)) {
            refreshSearchResults();
            return true;
        }
        return false;
    }

    @Shadow
    @Final
    private static Identifier WINDOW_TEXTURE;

    @Shadow
    @Final
    private ClientAdvancementManager advancementHandler;

    @Shadow private @Nullable AdvancementTab selectedTab;

    public AdvancementsScreenMixin() {
        super(null);
    }

    @Unique
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

    @ModifyConstant(method = "render", constant = @Constant(intValue = 252), require = 1)
    private int renderModifyWidth(int constant) {
        return width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2;
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 140), require = 1)
    private int renderModifyHeight(int constant) {
        return height - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2;
    }

    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 252), require = 1)
    private int mouseClickedModifyWidth(int constant) {
        return width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2;
    }

    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 140), require = 1)
    private int mouseClickedModifyHeight(int constant) {
        return height - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2;
    }

    @ModifyConstant(method = "drawAdvancementTree", constant = @Constant(intValue = 234), require = 1)
    private int drawAdvancementTreeModifyWidth(int constant) {
        return width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2 - 2 * 9;
    }

    @ModifyConstant(method = "drawAdvancementTree", constant = @Constant(intValue = 113), require = 1)
    private int drawAdvancementTreeModifyHeight(int constant) {
        return height - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2 - 3 * 9;
    }

    @ModifyConstant(method = "drawAdvancementTree", constant = @Constant(intValue = 117), require = 0)
    private int drawAdvancementTreeModifyEmptyTitleX(int constant) {
        return width / 2 - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN - 2 * 9 / 2;
    }

    @ModifyConstant(method = "drawAdvancementTree", constant = @Constant(intValue = 56), require = 0)
    private int drawAdvancementTreeModifyEmptyTitleY(int constant) {
        return height / 2 - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN - 3 * 9 / 2;
    }

    @Redirect(method = "drawAdvancementTree", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V", ordinal = 1))
    private void drawAdvancementTreeRedirectEmptySubtitleRender(DrawContext instance, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
    }

    @Redirect(method = "drawWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    public void drawWindowVanilla(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {
    }

    @Inject(method = "drawWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    public void drawWindowCustom(DrawContext context, int x, int y, CallbackInfo ci) {
        int screenWidth = 252;
        int screenHeight = 140;
        int actualWidth = width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN - x;
        int actualHeight = width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN - y;
        int halfOfWidth = screenWidth / 2;
        int halfOfHeight = screenHeight / 2;
        int clipTopX = (int) (Math.max(0, screenWidth - actualWidth) / 2. + 0.5);
        int clipLeftX = (int) (Math.max(0, screenWidth - actualWidth) / 2.);
        int clipTopY = (int) (Math.max(0, screenHeight - actualHeight) / 2. + 0.5);
        int clipLeftY = (int) (Math.max(0, screenHeight - actualHeight) / 2.);

        int rightX = width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN - halfOfWidth + clipTopX;
        int bottomY = height - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN - halfOfHeight + clipTopY;

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

    @Inject(method = "init", at = @At(value = "RETURN"))
    public void initInject(CallbackInfo ci) {
        searchField = new TextFieldWidget(textRenderer, width - width / 3 - 34, 8, width / 3, 22, ScreenTexts.EMPTY);
        searchField.setPlaceholder(SEARCH_HINT);
        searchField.setMaxLength(64);
        searchField.setChangedListener(query -> {
            if (query.isEmpty()) {
                isSearchActive = false;
            } else {
                isSearchActive = true;
                refreshSearchResults();
            }
        });
        AdvancementDisplay searchRootAdvancementDisplay = new AdvancementDisplay(
                ItemStack.EMPTY,
                Text.empty(),
                Text.empty(),
                Optional.of(new Identifier("textures/block/" + Registries.BLOCK.getId(Blocks.BLACK_CONCRETE).getPath() + ".png")),
                AdvancementFrame.TASK,
                false,
                false,
                true
        );
        searchRootAdvancement = new PlacedAdvancement(
                Advancement.Builder
                        .createUntelemetered()
                        .display(searchRootAdvancementDisplay)
                        .build(AchieveToDoClient.ADVANCEMENTS_SEARCH_ID),
                null
        );
        AdvancementsScreen advancementsScreen = (AdvancementsScreen) (Object) this;
        if (client != null) {
            searchTab = new AdvancementTab(client, advancementsScreen, AdvancementTabType.RIGHT, 0, searchRootAdvancement, searchRootAdvancementDisplay);
        }
    }

    @Inject(method = "render", at = @At(value = "RETURN"))
    public void renderInject(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        searchField.render(context, mouseX, mouseY, delta);
    }

    @Inject(method = "keyPressed", at = @At(value = "HEAD"), cancellable = true)
    public void keyPressedInject(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && searchField.isFocused() && !searchField.getText().isEmpty()) {
            searchField.setText("");
            searchField.setFocused(false);
            cir.setReturnValue(true);
        } else if (searchField.keyPressed(keyCode, scanCode, modifiers)) {
            cir.setReturnValue(true);
        } else if (searchField.isFocused() && keyCode != GLFW.GLFW_KEY_ESCAPE) {
            cir.setReturnValue(true);
        } else if (client != null && client.options.advancementsKey.matchesKey(keyCode, scanCode) && searchField.isFocused()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "mouseClicked", at = @At(value = "HEAD"), cancellable = true)
    public void mouseClickedInject(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (searchField.mouseClicked(mouseX, mouseY, button)) {
            searchField.setFocused(true);
            cir.setReturnValue(true);
        } else {
            searchField.setFocused(false);
        }
    }
}
