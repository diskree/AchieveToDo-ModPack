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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class AchieveToDoMod implements ModInitializer {

    public static final String ID = "achievetodo";
    public static final String ADVANCEMENT_PATH_PREFIX = "action/";
    public static final String ADVANCEMENT_CRITERIA_PREFIX = "action_";
    public static final Item MYSTERY_MASK_ITEM = new Item(new FabricItemSettings().group(ItemGroup.MISC));

    public static int lastAchievementsCount;

    public static void showFoodBlockedDescription(FoodComponent food) {
        if (MinecraftClient.getInstance().player == null) {
            return;
        }
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getFoodComponent() == food) {
                MinecraftClient.getInstance().player.sendMessage(action.getLockDescription(), true);
                break;
            }
        }
    }

    public static void setAchievementsCount(int count) {
        if (count == 0 || count < lastAchievementsCount) {
            lastAchievementsCount = 0;
        }
        if (lastAchievementsCount == count) {
            return;
        }
        int oldCount = lastAchievementsCount;
        lastAchievementsCount = count;
        List<BlockedAction> actionsToUnlock = new ArrayList<>();
        for (BlockedAction action : BlockedAction.values()) {
            if (action.isUnlocked() && oldCount < action.getAchievementsCountToUnlock() && oldCount != 0) {
                actionsToUnlock.add(action);
            }
        }
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return;
        }
        for (BlockedAction action : actionsToUnlock) {
            Advancement advancement = server.getAdvancementLoader().get(action.buildAdvancementId());
            MinecraftClient.getInstance().getToastManager().add(new AchieveToDoToast(advancement, action));
        }
    }

    public static boolean isActionBlocked(BlockedAction action) {
        if (MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().player.isCreative() || action.isUnlocked()) {
            return false;
        }
        MinecraftClient.getInstance().player.sendMessage(action.getLockDescription(), true);
        grantActionAdvancement(action);
        return true;
    }

    public static boolean isFoodBlocked(FoodComponent food) {
        if (MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().player.isCreative()) {
            return false;
        }
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getFoodComponent() == food) {
                grantActionAdvancement(action);
                return !action.isUnlocked();
            }
        }
        return false;
    }

    public static boolean isEquipmentBlocked(Item stack) {
        if (stack == Items.ELYTRA && AchieveToDoMod.isActionBlocked(BlockedAction.equip_elytra)) {
            return true;
        }
        if (stack instanceof ArmorItem) {
            ArmorMaterial armorMaterial = ((ArmorItem) stack).getMaterial();
            return armorMaterial == ArmorMaterials.IRON && AchieveToDoMod.isActionBlocked(BlockedAction.equip_iron_armor) ||
                    armorMaterial == ArmorMaterials.DIAMOND && AchieveToDoMod.isActionBlocked(BlockedAction.equip_diamond_armor) ||
                    armorMaterial == ArmorMaterials.NETHERITE && AchieveToDoMod.isActionBlocked(BlockedAction.equip_netherite_armor);
        }
        return false;
    }

    public static boolean isVillagerBlocked(VillagerProfession profession) {
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getVillagerProfession() == profession) {
                return isActionBlocked(action);
            }
        }
        return false;
    }

    public static BlockedAction getBlockedActionFromAdvancement(Advancement advancement) {
        if (advancement.getId().getNamespace().equals(AchieveToDoMod.ID) && advancement.getId().getPath().startsWith(AchieveToDoMod.ADVANCEMENT_PATH_PREFIX)) {
            String key = advancement.getId().getPath().split(AchieveToDoMod.ADVANCEMENT_PATH_PREFIX)[1];
            return BlockedAction.map(key);
        }
        return null;
    }

    private static void grantActionAdvancement(BlockedAction action) {
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return;
        }
        Advancement tab = server.getAdvancementLoader().get(action.buildAdvancementId());
        if (server.getPlayerManager() != null && !server.getPlayerManager().getPlayerList().isEmpty()) {
            server.getPlayerManager().getPlayerList().get(0).getAdvancementTracker().grantCriterion(tab, ADVANCEMENT_CRITERIA_PREFIX + action.name());
        }
    }

    private boolean isToolBlocked(ItemStack itemStack) {
        if (MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().player.isCreative()) {
            return false;
        }
        if (!itemStack.isDamageable()) {
            return false;
        }
        Item item = itemStack.getItem();
        if (item instanceof ToolItem) {
            ToolMaterial toolMaterial = ((ToolItem) item).getMaterial();
            return toolMaterial == ToolMaterials.IRON && AchieveToDoMod.isActionBlocked(BlockedAction.using_iron_tools) ||
                    toolMaterial == ToolMaterials.DIAMOND && AchieveToDoMod.isActionBlocked(BlockedAction.using_diamond_tools) ||
                    toolMaterial == ToolMaterials.NETHERITE && AchieveToDoMod.isActionBlocked(BlockedAction.using_netherite_tools);
        }
        return false;
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(AchieveToDoMod.ID, "locked_action"), MYSTERY_MASK_ITEM);
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (world != null && world.getRegistryKey() == World.OVERWORLD && pos != null) {
                if (pos.getY() >= 0 && isActionBlocked(BlockedAction.break_blocks_in_positive_y) || pos.getY() < 0 && isActionBlocked(BlockedAction.break_blocks_in_negative_y)) {
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
