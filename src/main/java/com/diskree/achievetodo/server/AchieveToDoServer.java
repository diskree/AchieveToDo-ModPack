package com.diskree.achievetodo.server;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

public class AchieveToDoServer implements DedicatedServerModInitializer {

    public static void grantHintsAdvancement(ServerPlayerEntity player, String pathName) {
        if (player == null) {
            return;
        }
        Advancement advancement = player.server.getAdvancementLoader().get(new Identifier(AchieveToDo.ID, "hints/" + pathName));
        AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
        for (String criterion : advancementProgress.getUnobtainedCriteria()) {
            player.getAdvancementTracker().grantCriterion(advancement, criterion);
        }
    }

    public static void grantActionAdvancement(ServerPlayerEntity player, Identifier actionAdvancement) {
        if (player == null) {
            return;
        }
        Advancement advancement = player.server.getAdvancementLoader().get(actionAdvancement);
        AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
        for (String criterion : advancementProgress.getUnobtainedCriteria()) {
            player.getAdvancementTracker().grantCriterion(advancement, criterion);
        }
    }

    public static BlockedAction getBlockedActionFromAdvancement(Advancement advancement) {
        String[] pathPieces = advancement.getId().getPath().split("/");
        return pathPieces.length == 2 ? BlockedAction.map(pathPieces[1]) : null;
    }

    public static boolean isActionBlocked(PlayerEntity player, BlockedAction action) {
        if (action == null || player != null && player.isCreative() || action.isUnblocked(player)) {
            return false;
        }
        if (player != null && player.getWorld().isClient) {
            player.sendMessage(action.buildBlockedDescription(player), true);
            ClientPlayNetworking.send(AchieveToDo.DEMYSTIFY_LOCKED_ACTION_PACKET_ID, PacketByteBufs.create().writeIdentifier(action.buildAdvancementId()));
        }
        return true;
    }

    public static boolean isFoodBlocked(PlayerEntity player, FoodComponent food) {
        if (food == null) {
            return false;
        }
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getFoodComponent() == food) {
                return isActionBlocked(player, action);
            }
        }
        return false;
    }

    public static boolean isEquipmentBlocked(PlayerEntity player, Item item) {
        if (item == Items.ELYTRA && isActionBlocked(player, BlockedAction.EQUIP_ELYTRA)) {
            return true;
        }
        if (item instanceof ArmorItem) {
            ArmorMaterial armorMaterial = ((ArmorItem) item).getMaterial();
            return armorMaterial == ArmorMaterials.IRON && isActionBlocked(player, BlockedAction.EQUIP_IRON_ARMOR) ||
                    armorMaterial == ArmorMaterials.DIAMOND && isActionBlocked(player, BlockedAction.EQUIP_DIAMOND_ARMOR) ||
                    armorMaterial == ArmorMaterials.NETHERITE && isActionBlocked(player, BlockedAction.EQUIP_NETHERITE_ARMOR);
        }
        return false;
    }

    public static boolean isVillagerBlocked(PlayerEntity player, VillagerProfession profession) {
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getVillagerProfession() == profession) {
                return isActionBlocked(player, action);
            }
        }
        return false;
    }

    public static boolean isToolBlocked(PlayerEntity player, Hand hand) {
        ItemStack stack = hand == Hand.MAIN_HAND ? player.getMainHandStack() : player.getOffHandStack();
        if (!stack.isDamageable()) {
            return false;
        }
        Item item = stack.getItem();
        if (item instanceof ToolItem) {
            ToolMaterial toolMaterial = ((ToolItem) item).getMaterial();
            return toolMaterial == ToolMaterials.STONE && isActionBlocked(player, BlockedAction.USING_STONE_TOOLS) ||
                    toolMaterial == ToolMaterials.IRON && isActionBlocked(player, BlockedAction.USING_IRON_TOOLS) ||
                    toolMaterial == ToolMaterials.DIAMOND && isActionBlocked(player, BlockedAction.USING_DIAMOND_TOOLS) ||
                    toolMaterial == ToolMaterials.NETHERITE && isActionBlocked(player, BlockedAction.USING_NETHERITE_TOOLS);
        }
        return false;
    }

    @Override
    public void onInitializeServer(ModContainer mod) {

    }
}
