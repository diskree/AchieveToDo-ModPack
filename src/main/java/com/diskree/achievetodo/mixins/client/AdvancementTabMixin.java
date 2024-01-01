package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.advancements.AdvancementsTab;
import com.diskree.achievetodo.advancements.AdvancementsTabGroup;
import com.diskree.achievetodo.client.AchieveToDoClient;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementTab;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementTab.class)
public class AdvancementTabMixin {

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
        AdvancementDisplay advancementDisplay = advancement.display().orElse(null);
        if (advancementDisplay == null) {
            cir.setReturnValue(null);
            return;
        }
        AdvancementsTab tab = null;
        for (AdvancementsTab advancementsTab : AdvancementsTab.values()) {
            if (advancementId.getPath().equalsIgnoreCase(advancementsTab.getRootAdvancementPath())) {
                tab = advancementsTab;
                break;
            }
        }
        if (tab == null) {
            cir.setReturnValue(null);
            return;
        }
        cir.setReturnValue(new AdvancementTab(
                client,
                screen,
                AdvancementsTabGroup.getTabType(tab),
                AdvancementsTabGroup.getTabOrder(tab),
                root,
                advancementDisplay
        ));
    }

    @Inject(method = "drawBackground", at = @At(value = "HEAD"), cancellable = true)
    public void drawBackgroundInject(DrawContext context, int x, int y, boolean selected, CallbackInfo ci) {
        if (root != null && AchieveToDoClient.ADVANCEMENTS_SEARCH_ID.equals(root.getAdvancementEntry().id())) {
            ci.cancel();
        }
    }

    @Inject(method = "drawIcon", at = @At(value = "HEAD"), cancellable = true)
    public void drawIconInject(DrawContext context, int x, int y, CallbackInfo ci) {
        if (root != null && AchieveToDoClient.ADVANCEMENTS_SEARCH_ID.equals(root.getAdvancementEntry().id())) {
            ci.cancel();
        }
    }

    @Inject(method = "isClickOnTab", at = @At(value = "HEAD"), cancellable = true)
    public void isClickOnTabInject(int screenX, int screenY, double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir) {
        if (root != null && AchieveToDoClient.ADVANCEMENTS_SEARCH_ID.equals(root.getAdvancementEntry().id())) {
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
