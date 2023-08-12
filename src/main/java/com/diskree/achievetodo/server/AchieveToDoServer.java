package com.diskree.achievetodo.server;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.BlockedAction;
import com.diskree.achievetodo.advancements.AdvancementGenerator;
import com.mojang.brigadier.Command;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;

public class AchieveToDoServer implements DedicatedServerModInitializer {

    public static void grantHintsAdvancement(String pathName) {
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return;
        }
        ServerPlayerEntity player = server.getPlayerManager().getPlayerList().get(0);
        Advancement advancement = server.getAdvancementLoader().get(new Identifier(AchieveToDo.ID, "hints/" + pathName));
        AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
        for (String criterion : advancementProgress.getUnobtainedCriteria()) {
            player.getAdvancementTracker().grantCriterion(advancement, criterion);
        }
    }

//    public static void setAdvancementsCount(int count) {
//        if (count >= 0 && count <= currentAdvancementsCount) {
//            return;
//        }
//        int oldCount = currentAdvancementsCount;
//        currentAdvancementsCount = count;
//
//        if (oldCount != 0) {
//            IntegratedServer server = MinecraftClient.getInstance().getServer();
//            if (server != null) {
//                for (BlockedAction action : BlockedAction.values()) {
//                    if (action.isUnblocked() && oldCount < action.getUnblockAdvancementsCount()) {
//                        Advancement advancement = server.getAdvancementLoader().get(action.buildAdvancementId());
//                        MinecraftClient.getInstance().getToastManager().add(new UnblockActionToast(advancement, action));
//                    }
//                }
//            }
//        }
//    }

    public static BlockedAction getBlockedActionFromAdvancement(Advancement advancement) {
        String[] pathPieces = advancement.getId().getPath().split("/");
        return pathPieces.length == 2 ? BlockedAction.map(pathPieces[1]) : null;
    }

    public static boolean isActionBlocked(PlayerEntity player, BlockedAction action) {
        if (action == null || player != null && player.isCreative() || action.isUnblocked(player)) {
            return false;
        }
        if (player != null) {
            player.sendMessage(action.buildBlockedDescription(player), true);
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

    private static void grantActionAdvancement(ServerPlayerEntity player, BlockedAction action) {
        Advancement advancement = player.server.getAdvancementLoader().get(action.buildAdvancementId());
        AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
        for (String criterion : advancementProgress.getUnobtainedCriteria()) {
            player.getAdvancementTracker().grantCriterion(advancement, criterion);
        }
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
        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> dispatcher.register(CommandManager.literal("random").executes((context -> {
            Advancement randomAdvancement = AdvancementGenerator.generateForCommand();
            if (randomAdvancement != null) {
                AdvancementDisplay display = randomAdvancement.getDisplay();
                AdvancementDisplay rootDisplay = randomAdvancement.getRoot().getDisplay();
                Text displayTitle = display != null ? display.getTitle() : null;
                if (display == null || rootDisplay == null || displayTitle == null) {
                    context.getSource().sendSystemMessage(Text.of("Parsing error for advancement: " + randomAdvancement.getId()).copy().formatted(Formatting.RED));
                    return Command.SINGLE_SUCCESS;
                }
                final String separator = "----------";
                context.getSource().sendSystemMessage(Text.of(separator).copy().formatted(Formatting.GOLD));
                context.getSource().sendSystemMessage(rootDisplay.getTitle().copy().formatted(Formatting.BLUE, Formatting.BOLD));
                context.getSource().sendSystemMessage(display.getTitle().copy().formatted(Formatting.AQUA, Formatting.ITALIC));
                context.getSource().sendSystemMessage(display.getDescription().copy().formatted(Formatting.YELLOW));
                context.getSource().sendSystemMessage(Text.of(separator).copy().formatted(Formatting.GOLD));
            } else {
                context.getSource().sendSystemMessage(Text.translatable("commands.random.no_advancements"));
            }
            return Command.SINGLE_SUCCESS;
        }))));
    }
}
