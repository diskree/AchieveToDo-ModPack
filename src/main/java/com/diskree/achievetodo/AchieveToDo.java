package com.diskree.achievetodo;

import com.diskree.achievetodo.blocked_actions.BlockedActionType;
import com.diskree.achievetodo.blocked_actions.datagen.AdvancementsGenerator;
import com.diskree.achievetodo.injection.UsableBlock;
import com.diskree.achievetodo.injection.UsableItem;
import com.diskree.achievetodo.injection.UsableItemOnBlock;
import com.diskree.achievetodo.networking.GrantBlockedActionPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.*;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class AchieveToDo implements ModInitializer {

    public static final String ID = BuildConfig.MOD_ID;

    public static final String BACAP_SCORE_OBJECTIVE = "bac_advancements";

    public static final Identifier MYSTIFIED_BLOCKED_ACTION_LABEL_ITEM_ID = new Identifier(ID, "mystified_label");
    public static final Item MYSTIFIED_BLOCKED_ACTION_LABEL_ITEM = new Item(new Item.Settings());

    private static int advancementsCount;

    @Environment(EnvType.CLIENT)
    public static int getScore() {
        return getScore(MinecraftClient.getInstance().player);
    }

    @Environment(EnvType.CLIENT)
    public static boolean isActionBlocked(BlockedActionType blockedAction) {
        return isActionBlocked(MinecraftClient.getInstance().player, blockedAction);
    }

    @Environment(EnvType.CLIENT)
    public static void setAdvancementsCount(int count) {
        if (MinecraftClient.getInstance().player == null || count >= 0 && count <= advancementsCount) {
            return;
        }
        int oldCount = advancementsCount;
        advancementsCount = count;
        if (oldCount != 0) {
            for (BlockedActionType blockedAction : BlockedActionType.values()) {
                if (advancementsCount >= blockedAction.getUnblockAdvancementsCount() &&
                        oldCount < blockedAction.getUnblockAdvancementsCount()
                ) {
                    ClientPlayNetworking.send(new GrantBlockedActionPayload(blockedAction, false));
                }
            }
        }
    }

    public static int getScore(PlayerEntity player) {
        if (player == null) {
            return 0;
        }
        Scoreboard scoreboard = player.getScoreboard();
        if (scoreboard == null) {
            return 0;
        }
        ScoreboardObjective scoreObjective = scoreboard.getNullableObjective(BACAP_SCORE_OBJECTIVE);
        if (scoreObjective == null) {
            return 0;
        }
        ReadableScoreboardScore playerScore = scoreboard.getScore(
                ScoreHolder.fromName(player.getNameForScoreboard()),
                scoreObjective
        );
        if (playerScore == null) {
            return 0;
        }
        return playerScore.getScore();
    }

    public static boolean isActionBlocked(PlayerEntity player, BlockedActionType blockedAction) {
        return isActionBlocked(player, blockedAction, false);
    }

    public static boolean isActionBlocked(PlayerEntity player, BlockedActionType blockedAction, boolean isCheckOnly) {
        if (blockedAction == null ||
                player == null ||
                player.isCreative() ||
                player.isSpectator() ||
                blockedAction.isUnblocked(player)) {
            return false;
        }
        if (!isCheckOnly) {
            player.sendMessage(blockedAction.buildBlockedDescription(player), true);
            if (player.getWorld().isClient) {
                ClientPlayNetworking.send(new GrantBlockedActionPayload(blockedAction, true));
            } else if (player instanceof ServerPlayerEntity serverPlayer) {
                grantBlockedAction(serverPlayer, blockedAction, true);
            }
        }
        return true;
    }

    private static void grantBlockedAction(
            @NotNull ServerPlayerEntity player,
            BlockedActionType blockedAction,
            boolean isDemystifyOnly
    ) {
        AdvancementEntry advancement = player.server.getAdvancementLoader()
                .get(AdvancementsGenerator.buildAdvancementId(blockedAction));
        if (isDemystifyOnly) {
            player.getAdvancementTracker().grantCriterion(
                    advancement,
                    AdvancementsGenerator.BLOCKED_ACTION_DEMYSTIFIED_CRITERION_PREFIX + blockedAction.getName()
            );
        } else {
            for (String criterion : player.getAdvancementTracker().getProgress(advancement).getUnobtainedCriteria()) {
                player.getAdvancementTracker().grantCriterion(advancement, criterion);
            }
        }
    }

    @Override
    public void onInitialize() {
        registerDataPacks();
        registerItems();

        PayloadTypeRegistry.playC2S().register(GrantBlockedActionPayload.ID, GrantBlockedActionPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(GrantBlockedActionPayload.ID, (payload, context) ->
                context.player().server.execute(() ->
                        grantBlockedAction(context.player(), payload.blockedAction(), payload.isDemystifyOnly())
                )
        );

        ServerWorldEvents.LOAD.register((server, world) -> advancementsCount = 0);

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getStackInHand(hand);
            Item item = stack.getItem();
            if (item == Items.SHEARS || item == Items.BRUSH) {
                return TypedActionResult.pass(ItemStack.EMPTY);
            }
            if (isActionBlocked(player, BlockedActionType.findBlockedItem(player, stack))) {
                return TypedActionResult.fail(ItemStack.EMPTY);
            }
            if (isActionBlocked(player, BlockedActionType.findBlockedEquipment(item))) {
                return TypedActionResult.fail(ItemStack.EMPTY);
            }
            return TypedActionResult.pass(ItemStack.EMPTY);
        });
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            ItemStack stack = player.getStackInHand(hand);
            Item item = stack.getItem();
            BlockState blockState = world.getBlockState(hit.getBlockPos());
            Block block = blockState.getBlock();
            if ((!player.shouldCancelInteraction() || player.getMainHandStack().isEmpty() &&
                    player.getOffHandStack().isEmpty()) &&
                    block instanceof UsableBlock usableBlock && usableBlock.achievetodo$canUse(player, hand, hit) &&
                    isActionBlocked(player, BlockedActionType.findBlockedBlock(blockState))
            ) {
                return ActionResult.FAIL;
            }
            if (item instanceof UsableItem usableItem && (usableItem.achievetodo$canUse(player, hit)) ||
                    block instanceof UsableItemOnBlock usableItemOnBlock &&
                            usableItemOnBlock.achievetodo$canUse(player, stack, hand, hit)) {
                if (isActionBlocked(player, BlockedActionType.findBlockedItem(player, stack))) {
                    return ActionResult.FAIL;
                }
                if (isActionBlocked(player, BlockedActionType.findBlockedTool(item))) {
                    return ActionResult.FAIL;
                }
            }
            return ActionResult.PASS;
        });
        UseEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            ItemStack stack = player.getStackInHand(hand);
            Item item = stack.getItem();
            if (entity instanceof ItemFrameEntity) {
                return ActionResult.PASS;
            }
            if (entity instanceof BoatEntity boatEntity &&
                    boatEntity.canAddPassenger(player) &&
                    isActionBlocked(player, BlockedActionType.USING_BOAT)
            ) {
                return ActionResult.FAIL;
            }
            if (entity instanceof VillagerEntity villagerEntity &&
                    !villagerEntity.isBaby() &&
                    !villagerEntity.getOffers().isEmpty() &&
                    isActionBlocked(player, BlockedActionType.findBlockedVillager(
                            villagerEntity.getVillagerData().getProfession()
                    ))
            ) {
                villagerEntity.sayNo();
                return ActionResult.FAIL;
            }
            if (item == Items.SHEARS &&
                    entity instanceof Shearable shearable &&
                    shearable.isShearable() &&
                    isActionBlocked(player, BlockedActionType.USING_SHEARS)
            ) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            ItemStack stack = player.getStackInHand(hand);
            Item item = stack.getItem();
            if (player.getWorld().getRegistryKey() == World.OVERWORLD && isActionBlocked(player,
                    pos.getY() >= 0 ? BlockedActionType.BREAK_BLOCKS_IN_POSITIVE_Y :
                            BlockedActionType.BREAK_BLOCKS_IN_NEGATIVE_Y)) {
                return ActionResult.FAIL;
            }
            if (isActionBlocked(player, BlockedActionType.findBlockedTool(item))) {
                return ActionResult.FAIL;
            }
            if (item == Items.SHEARS && isActionBlocked(player, BlockedActionType.USING_SHEARS)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            ItemStack stack = player.getStackInHand(hand);
            Item item = stack.getItem();
            if (isActionBlocked(player, BlockedActionType.findBlockedTool(item))) {
                return ActionResult.FAIL;
            }
            if (item == Items.SHEARS && isActionBlocked(player, BlockedActionType.USING_SHEARS)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }

    private void registerDataPacks() {
        FabricLoader.getInstance().getModContainer(ID).ifPresent(modContainer -> {
            for (InternalDatapack internalDatapack : InternalDatapack.values()) {
                ResourceManagerHelper.registerBuiltinResourcePack(
                        internalDatapack.toDatapackId(), modContainer, ResourcePackActivationType.NORMAL
                );
            }
        });
    }

    private void registerItems() {
        Registry.register(Registries.ITEM, MYSTIFIED_BLOCKED_ACTION_LABEL_ITEM_ID, MYSTIFIED_BLOCKED_ACTION_LABEL_ITEM);
    }
}
