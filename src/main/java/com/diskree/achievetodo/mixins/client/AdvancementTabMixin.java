package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.advancements.AdvancementRoot;
import com.diskree.achievetodo.client.AchieveToDoClient;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mixin(AdvancementTab.class)
public class AdvancementTabMixin {

    @Unique
    private static final Map<AdvancementTabType, List<AdvancementRoot>> tabLocations = new HashMap<>() {{
        put(AdvancementTabType.LEFT, List.of(AdvancementRoot.BIOMES, AdvancementRoot.ADVENTURE, AdvancementRoot.WEAPONRY, AdvancementRoot.HUSBANDRY, AdvancementRoot.MONSTERS));
        put(AdvancementTabType.ABOVE, List.of(AdvancementRoot.MINING, AdvancementRoot.BUILDING, AdvancementRoot.FARMING, AdvancementRoot.NETHER, AdvancementRoot.END));
        put(AdvancementTabType.RIGHT, List.of(AdvancementRoot.BLOCKED_ACTIONS, AdvancementRoot.STATISTICS, AdvancementRoot.BACAP, AdvancementRoot.ADVANCEMENTS_SEARCH));
        put(AdvancementTabType.BELOW, List.of(AdvancementRoot.REDSTONE, AdvancementRoot.POTION, AdvancementRoot.ENCHANTING, AdvancementRoot.CHALLENGES));
    }};

    @Shadow
    @Final
    private AdvancementsScreen screen;

    @Shadow
    @Final
    private PlacedAdvancement root;

    @Inject(method = "create", at = @At("HEAD"), cancellable = true)
    private static void createInject(MinecraftClient client, AdvancementsScreen screen, int index, PlacedAdvancement root, CallbackInfoReturnable<AdvancementTab> cir) {
        if (root == null) {
            cir.setReturnValue(null);
            return;
        }
        AdvancementEntry advancementEntry = root.getAdvancementEntry();
        if (advancementEntry == null) {
            cir.setReturnValue(null);
            return;
        }
        Identifier advancementId = advancementEntry.id();
        if (advancementId == null) {
            cir.setReturnValue(null);
            return;
        }
        Advancement advancement = root.getAdvancement();
        if (advancement == null) {
            cir.setReturnValue(null);
            return;
        }
        Optional<AdvancementDisplay> advancementDisplayOptional = advancement.display();
        if (advancementDisplayOptional.isEmpty()) {
            cir.setReturnValue(null);
            return;
        }
        String rootName = advancementId.getPath().split("/")[0];
        AdvancementRoot tabToAdd = null;
        for (AdvancementRoot tab : AdvancementRoot.values()) {
            if (rootName.toLowerCase().equals(tab.getNamespace())) {
                tabToAdd = tab;
                break;
            }
        }
        if (tabToAdd == null) {
            cir.setReturnValue(null);
            return;
        }

        AdvancementTabType tabGravity = null;
        int order = 0;

        for (Map.Entry<AdvancementTabType, List<AdvancementRoot>> entry : tabLocations.entrySet()) {
            List<AdvancementRoot> tabs = entry.getValue();
            for (int i = 0; i < tabs.size(); i++) {
                AdvancementRoot tab = tabs.get(i);
                if (tab == tabToAdd) {
                    tabGravity = entry.getKey();
                    order = i;
                    break;
                }
            }
            if (tabGravity != null) {
                break;
            }
        }

        cir.setReturnValue(new AdvancementTab(client, screen, tabGravity, order, root, advancementDisplayOptional.get()));
    }

    @Inject(method = "drawBackground", at = @At(value = "HEAD"), cancellable = true)
    public void drawBackgroundInject(DrawContext context, int x, int y, boolean selected, CallbackInfo ci) {
        if (root != null && AchieveToDo.ADVANCEMENTS_SEARCH.equals(root.getAdvancementEntry().id())) {
            ci.cancel();
        }
    }

    @Inject(method = "drawIcon", at = @At(value = "HEAD"), cancellable = true)
    public void drawIconInject(DrawContext context, int x, int y, CallbackInfo ci) {
        if (root != null && AchieveToDo.ADVANCEMENTS_SEARCH.equals(root.getAdvancementEntry().id())) {
            ci.cancel();
        }
    }

    @Inject(method = "isClickOnTab", at = @At(value = "HEAD"), cancellable = true)
    public void isClickOnTabInject(int screenX, int screenY, double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir) {
        if (root != null && AchieveToDo.ADVANCEMENTS_SEARCH.equals(root.getAdvancementEntry().id())) {
            cir.setReturnValue(false);
        }
    }

    @ModifyConstant(method = "move", constant = @Constant(intValue = 234), require = 2)
    private int moveModifyWidth(int orig) {
        return screen.width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2 - 2 * 9;
    }

    @ModifyConstant(method = "move", constant = @Constant(intValue = 113), require = 2)
    private int moveModifyHeight(int orig) {
        return screen.height - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2 - 3 * 9;
    }

    @ModifyConstant(method = "drawWidgetTooltip", constant = @Constant(intValue = 234), require = 2)
    private int drawWidgetTooltipModifyWidth(int orig) {
        return screen.width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2 - 2 * 9;
    }

    @ModifyConstant(method = "drawWidgetTooltip", constant = @Constant(intValue = 113), require = 2)
    private int drawWidgetTooltipModifyHeight(int orig) {
        return screen.height - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2 - 3 * 9;
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 234), require = 1)
    private int renderModifyWidth(int orig) {
        return screen.width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2 - 2 * 9;
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 113), require = 1)
    private int renderModifyHeight(int orig) {
        return screen.height - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2 - 3 * 9;
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 117), require = 1)
    private int renderModifyOriginX(int orig) {
        return screen.width / 2 - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN - 2 * 9 / 2;
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 56), require = 1)
    private int renderModifyOriginY(int orig) {
        return screen.height / 2 - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN - 3 * 9 / 2;
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 15), require = 1)
    private int renderModifyTextureX(int orig) {
        return (screen.width - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2) / 16 + 1;
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 8), require = 1)
    private int renderModifyTextureY(int orig) {
        return (screen.height - AchieveToDoClient.ADVANCEMENTS_SCREEN_MARGIN * 2) / 16 + 1;
    }
}
