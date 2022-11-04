package com.diskree.achievetodo;

import com.diskree.achievetodo.advancements.AchieveToDoToast;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class AchievementHardcoreMod implements ModInitializer {

    public static final Item MYSTERY_MASK_ITEM = new Item(new FabricItemSettings().group(ItemGroup.MISC));

    private static final EnumMap<BlockedAction, Boolean> blockedActions = new EnumMap<>(BlockedAction.class);
    public static int lastAchievementsCount;

    public static void showFoodBlockedDescription(FoodComponent food) {
        if (MinecraftClient.getInstance().player == null) {
            return;
        }
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getFoodComponent() == food) {
                int leftAchievementsCount = action.getAchievementsCountToUnlock() - lastAchievementsCount;
                MinecraftClient.getInstance().player.sendMessage(Text.of("Эта еда сейчас недоступна. Для разблокировки осталось выполнить достижений: " + leftAchievementsCount).copy().formatted(Formatting.YELLOW), true);
                break;
            }
        }
    }

    public static void setAchievementsCount(int count) {
        if (count == 0 || count < lastAchievementsCount) {
            lastAchievementsCount = 0;
            blockedActions.clear();
            for (BlockedAction action : BlockedAction.values()) {
                blockedActions.put(action, false);
            }
        }
        if (lastAchievementsCount == count) {
            return;
        }
        int oldCount = lastAchievementsCount;
        lastAchievementsCount = count;
        List<BlockedAction> actionsToUnlock = new ArrayList<>();
        for (BlockedAction action : BlockedAction.values()) {
            if (count >= action.getAchievementsCountToUnlock()) {
                if (oldCount < action.getAchievementsCountToUnlock() && oldCount != 0) {
                    actionsToUnlock.add(action);
                }
                blockedActions.put(action, true);
            }
        }
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return;
        }
        for (BlockedAction action : actionsToUnlock) {
            Advancement advancement = server.getAdvancementLoader().get(Identifier.of("achievetodo", "action/" + action.name().toLowerCase()));
            MinecraftClient.getInstance().getToastManager().add(new AchieveToDoToast(advancement, action));
        }
    }

    public static boolean isActionBlocked(BlockedAction action) {
        Boolean value = blockedActions.get(action);
        if (value != null && value) {
            return false;
        }
        if (MinecraftClient.getInstance().player != null) {
            int leftAchievementsCount = action.getAchievementsCountToUnlock() - lastAchievementsCount;
            MinecraftClient.getInstance().player.sendMessage(Text
                    .of(action.getDescription() + ". Для разблокировки осталось выполнить достижений: " + leftAchievementsCount)
                    .copy()
                    .formatted(Formatting.YELLOW), true
            );
            grantAHCAdvancement(action);
        }
        return true;
    }

    public static boolean isFoodBlocked(FoodComponent food) {
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getFoodComponent() == food) {
                grantAHCAdvancement(action);
                return action.getAchievementsCountToUnlock() > lastAchievementsCount;
            }
        }
        return false;
    }

    private static void grantAHCAdvancement(BlockedAction action) {
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return;
        }
        Advancement tab = server.getAdvancementLoader().get(Identifier.of("achievetodo", "action/" + action.name().toLowerCase()));
        if (server.getPlayerManager() != null && !server.getPlayerManager().getPlayerList().isEmpty()) {
            server.getPlayerManager().getPlayerList().get(0).getAdvancementTracker().grantCriterion(tab, "action_" + action.name().toLowerCase());
        }
    }

    private boolean isToolBlocked(ItemStack itemStack) {
        if (!itemStack.isDamageable()) {
            return false;
        }
        Item item = itemStack.getItem();
        if (item instanceof ToolItem) {
            ToolMaterial toolMaterial = ((ToolItem) item).getMaterial();
            return toolMaterial == ToolMaterials.IRON && AchievementHardcoreMod.isActionBlocked(BlockedAction.USING_IRON_TOOLS) ||
                    toolMaterial == ToolMaterials.DIAMOND && AchievementHardcoreMod.isActionBlocked(BlockedAction.USING_DIAMOND_TOOLS) ||
                    toolMaterial == ToolMaterials.NETHERITE && AchievementHardcoreMod.isActionBlocked(BlockedAction.USING_NETHERITE_TOOLS);
        }
        return false;
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("achievetodo", "unknown_action"), MYSTERY_MASK_ITEM);
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (world != null && world.getRegistryKey() == World.OVERWORLD && pos != null) {
                if (pos.getY() >= 0 && isActionBlocked(BlockedAction.BREAK_BLOCKS_IN_POSITIVE_Y) || pos.getY() < 0 && isActionBlocked(BlockedAction.BREAK_BLOCKS_IN_NEGATIVE_Y)) {
                    return ActionResult.FAIL;
                }
            }
            if (isToolBlocked(player.getInventory().getMainHandStack())) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (isToolBlocked(player.getInventory().getMainHandStack())) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            ItemStack itemStack = player.getInventory().getMainHandStack();
            if (isToolBlocked(itemStack)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }
}
