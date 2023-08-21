package com.diskree.achievetodo;

import com.diskree.achievetodo.advancements.AdvancementGenerator;
import com.diskree.achievetodo.advancements.UnblockActionToast;
import com.diskree.achievetodo.advancements.hints.*;
import com.diskree.achievetodo.client.SpyglassPanoramaDetails;
import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.event.player.*;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.quiltmc.qsl.resource.loader.api.ResourcePackActivationType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AchieveToDo implements ModInitializer {

    public static final String ID = "achievetodo";

    public static final String BACAP_DATA_PACK = "file/bacap.zip";
    public static final String BACAP_HARDCORE_DATA_PACK = "file/bacap_hardcore.zip";
    public static final String BACAP_TERRALITH_DATA_PACK = "file/bacap_terralith.zip";
    public static final String BACAP_AMPLIFIED_NETHER_DATA_PACK = "file/bacap_amplified_nether.zip";
    public static final String BACAP_NULLSCAPE_DATA_PACK = "file/bacap_nullscape.zip";

    public static final String TERRALITH_DATA_PACK = "file/terralith.zip";
    public static final String AMPLIFIED_NETHER_DATA_PACK = "file/amplified_nether.zip";
    public static final String NULLSCAPE_DATA_PACK = "file/nullscape.zip";

    public static final String BACAP_OVERRIDE_DATA_PACK = AchieveToDo.ID + "/" + "bacap_override";
    public static final String BACAP_OVERRIDE_HARDCORE_DATA_PACK = AchieveToDo.ID + "/" + "bacap_override_hardcore";
    public static final String BACAP_REWARDS_ITEM_DATA_PACK_NAME = AchieveToDo.ID + "/" + "bacap_rewards_item";
    public static final String BACAP_REWARDS_EXPERIENCE_DATA_PACK_NAME = AchieveToDo.ID + "/" + "bacap_rewards_experience";
    public static final String BACAP_REWARDS_TROPHY_DATA_PACK_NAME = AchieveToDo.ID + "/" + "bacap_rewards_trophy";
    public static final String BACAP_LANGUAGE_PACK = AchieveToDo.ID + "/" + "bacap_lp";

    public static final Identifier DEMYSTIFY_LOCKED_ACTION_PACKET_ID = new Identifier(ID, "demystify_locked_action");

    public static final Identifier ADVANCEMENTS_SEARCH = new Identifier(AchieveToDo.ID, "advancements_search");

    public static final Identifier ANCIENT_CITY_PORTAL_BLOCK_ID = new Identifier(ID, "ancient_city_portal");
    public static final AncientCityPortalBlock ANCIENT_CITY_PORTAL_BLOCK = new AncientCityPortalBlock(AbstractBlock.Settings.create()
            .noCollision()
            .ticksRandomly()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(-1.0f, 3600000.0f)
            .dropsNothing()
            .sounds(BlockSoundGroup.GLASS)
            .luminance((blockState) -> 11)
            .pistonBehavior(PistonBehavior.BLOCK));

    public static final Identifier REINFORCED_DEEPSLATE_CHARGED_BLOCK_ID = new Identifier(ID, "reinforced_deepslate_charged");
    public static final Block REINFORCED_DEEPSLATE_CHARGED_BLOCK = new Block(QuiltBlockSettings.create());

    public static final Identifier REINFORCED_DEEPSLATE_BROKEN_BLOCK_ID = new Identifier(ID, "reinforced_deepslate_broken");
    public static final Block REINFORCED_DEEPSLATE_BROKEN_BLOCK = new Block(QuiltBlockSettings.create());

    public static final Identifier LOCKED_ACTION_ITEM_ID = new Identifier(ID, "locked_action");
    public static final Item LOCKED_ACTION_ITEM = new Item(new QuiltItemSettings());

    public static final Identifier ANCIENT_CITY_PORTAL_HINT_ITEM_ID = new Identifier(ID, "ancient_city_portal_hint");
    public static final Item ANCIENT_CITY_PORTAL_HINT_ITEM = new Item(new QuiltItemSettings().maxDamage(1000));

    public static final Identifier ANCIENT_CITY_PORTAL_PARTICLES_ID = new Identifier(ID, "ancient_city_portal_particles");
    public static final DefaultParticleType ANCIENT_CITY_PORTAL_PARTICLES = FabricParticleTypes.simple();

    public static final EntityType<AncientCityPortalEntity> ANCIENT_CITY_PORTAL_ADVANCEMENT = QuiltEntityTypeBuilder.create(SpawnGroup.MISC, AncientCityPortalEntity::new)
            .setDimensions(EntityDimensions.changing(0.0f, 0.0f))
            .maxChunkTrackingRange(10)
            .trackingTickInterval(1)
            .build();
    public static final Identifier ANCIENT_CITY_PORTAL_TAB_ENTITY_ID = new Identifier(ID, "ancient_city_portal_tab_entity");
    public static final EntityType<AncientCityPortalTabEntity> ANCIENT_CITY_PORTAL_TAB = QuiltEntityTypeBuilder.create(SpawnGroup.MISC, AncientCityPortalTabEntity::new)
            .setDimensions(EntityDimensions.changing(0.0f, 0.0f))
            .maxChunkTrackingRange(10)
            .trackingTickInterval(1)
            .build();
    public static final Identifier ANCIENT_CITY_PORTAL_ADVANCEMENT_ENTITY_ID = new Identifier(ID, "ancient_city_portal_advancement_entity");
    public static final Identifier ANCIENT_CITY_PORTAL_HINT_ENTITY_ID = new Identifier(ID, "ancient_city_portal_prompt_entity");
    public static final EntityType<AncientCityPortalPromptEntity> ANCIENT_CITY_PORTAL_HINT = QuiltEntityTypeBuilder.create(SpawnGroup.MISC, AncientCityPortalPromptEntity::new)
            .setDimensions(EntityDimensions.changing(0.0f, 0.0f))
            .maxChunkTrackingRange(10)
            .trackingTickInterval(1)
            .build();

    public static final Identifier ANCIENT_CITY_PORTAL_EXPERIENCE_ORB_ENTITY_ID = new Identifier(ID, "ancient_city_portal_experience_orb");
    public static final EntityType<AncientCityPortalExperienceOrbEntity> ANCIENT_CITY_PORTAL_EXPERIENCE_ORB = QuiltEntityTypeBuilder.<AncientCityPortalExperienceOrbEntity>create(SpawnGroup.MISC, AncientCityPortalExperienceOrbEntity::new)
            .setDimensions(EntityDimensions.changing(0.5f, 0.5f))
            .maxChunkTrackingRange(6)
            .trackingTickInterval(20)
            .build();

    public static final SoundEvent MUSIC_DISC_5_ACTIVATOR = SoundEvent.createVariableRangeEvent(new Identifier(ID, "music_disc_5_activator"));
    public static final Identifier EVOKER_NO_TOTEM_OF_UNDYING_LOOT_TABLE_ID = new Identifier(ID, "entities/evoker_no_totem_of_undying");

    private boolean isPanoramaLoading;
    private static int advancementsCount;

    public static int getScore(PlayerEntity player) {
        if (player == null) {
            return 0;
        }
        Scoreboard scoreboard = player.getScoreboard();
        if (scoreboard == null) {
            return 0;
        }
        ScoreboardObjective scoreObjective = scoreboard.getObjective("bac_advancements");
        if (scoreObjective == null) {
            return 0;
        }
        ScoreboardPlayerScore playerScore = scoreboard.getPlayerScore(player.getEntityName(), scoreObjective);
        if (playerScore == null) {
            return 0;
        }
        return playerScore.getScore();
    }

    @Override
    public void onInitialize(ModContainer mod) {
        registerPacks();
        registerBlocks();
        registerItems();
        registerParticles();
        registerEntities();

        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> dispatcher.register(CommandManager.literal("random").executes((context -> {
            ServerCommandSource source = context.getSource();
            Advancement randomAdvancement = AdvancementGenerator.getRandomAdvancement(source.getPlayer());
            if (randomAdvancement != null) {
                AdvancementDisplay display = randomAdvancement.getDisplay();
                AdvancementDisplay rootDisplay = randomAdvancement.getRoot().getDisplay();
                Text displayTitle = display != null ? display.getTitle() : null;
                if (display == null || rootDisplay == null || displayTitle == null) {
                    source.sendSystemMessage(Text.of("Parsing error for advancement: " + randomAdvancement.getId()).copy().formatted(Formatting.RED));
                    return Command.SINGLE_SUCCESS;
                }
                final String separator = "----------";
                source.sendSystemMessage(Text.of(separator).copy().formatted(Formatting.GOLD));
                source.sendSystemMessage(rootDisplay.getTitle().copy().formatted(Formatting.BLUE, Formatting.BOLD));
                source.sendSystemMessage(display.getTitle().copy().formatted(Formatting.AQUA, Formatting.ITALIC));
                source.sendSystemMessage(display.getDescription().copy().formatted(Formatting.YELLOW));
                source.sendSystemMessage(Text.of(separator).copy().formatted(Formatting.GOLD));
            } else {
                source.sendSystemMessage(Text.translatable("commands.random.no_advancements"));
            }
            return Command.SINGLE_SUCCESS;
        }))));
        ServerPlayNetworking.registerGlobalReceiver(AchieveToDo.DEMYSTIFY_LOCKED_ACTION_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            Identifier actionAdvancementId = buf.readIdentifier();
            server.execute(() -> {
                Advancement advancement = player.server.getAdvancementLoader().get(actionAdvancementId);
                AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
                for (String criterion : advancementProgress.getUnobtainedCriteria()) {
                    player.getAdvancementTracker().grantCriterion(advancement, criterion);
                }
            });
        });
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (world != null && world.getRegistryKey() == World.OVERWORLD && pos != null) {
                if (pos.getY() >= 0 && isActionBlocked(player, BlockedAction.BREAK_BLOCKS_IN_POSITIVE_Y)) {
                    return ActionResult.FAIL;
                }
                if (pos.getY() < 0 && isActionBlocked(player, BlockedAction.BREAK_BLOCKS_IN_NEGATIVE_Y)) {
                    return ActionResult.FAIL;
                }
            }
            if (isToolBlocked(player, hand)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (isToolBlocked(player, hand)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = hand == Hand.MAIN_HAND ? player.getMainHandStack() : player.getOffHandStack();
            if (stack.isFood() && isFoodBlocked(player, stack.getItem().getFoodComponent())) {
                return TypedActionResult.fail(ItemStack.EMPTY);
            }
            if (stack.isOf(Items.SHIELD) && isActionBlocked(player, BlockedAction.USING_SHIELD)) {
                return TypedActionResult.fail(ItemStack.EMPTY);
            }
            if (stack.isOf(Items.BOW) && isActionBlocked(player, BlockedAction.USING_BOW)) {
                return TypedActionResult.fail(ItemStack.EMPTY);
            }
            if (stack.isOf(Items.FIREWORK_ROCKET) && player.isFallFlying() && isActionBlocked(player, BlockedAction.USING_FIREWORKS_WHILE_FLY)) {
                return TypedActionResult.fail(ItemStack.EMPTY);
            }
            if (stack.isOf(Items.ELYTRA) && isActionBlocked(player, BlockedAction.EQUIP_ELYTRA)) {
                return TypedActionResult.fail(ItemStack.EMPTY);
            }
            if (stack.getItem() instanceof ArmorItem armorItem && isEquipmentBlocked(player, armorItem)) {
                return TypedActionResult.fail(ItemStack.EMPTY);
            }
            if (world.isClient && stack.isOf(Items.SPYGLASS)) {
                SpyglassPanoramaDetails panoramaDetails = SpyglassPanoramaDetails.from(stack);
                if (panoramaDetails != null && !isPanoramaReady(panoramaDetails)) {
                    return TypedActionResult.fail(ItemStack.EMPTY);
                }
            }
            return TypedActionResult.pass(ItemStack.EMPTY);
        });
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            BlockState block = world.getBlockState(hitResult.getBlockPos());
            if (block.isOf(Blocks.CRAFTING_TABLE) && isActionBlocked(player, BlockedAction.USING_CRAFTING_TABLE)) {
                return ActionResult.FAIL;
            }
            if (block.isOf(Blocks.FURNACE) && isActionBlocked(player, BlockedAction.USING_FURNACE)) {
                return ActionResult.FAIL;
            }
            if (block.isOf(Blocks.ANVIL) && isActionBlocked(player, BlockedAction.USING_ANVIL)) {
                return ActionResult.FAIL;
            }
            if (block.isOf(Blocks.SMOKER) && isActionBlocked(player, BlockedAction.USING_SMOKER)) {
                return ActionResult.FAIL;
            }
            if (block.isOf(Blocks.BLAST_FURNACE) && isActionBlocked(player, BlockedAction.USING_BLAST_FURNACE)) {
                return ActionResult.FAIL;
            }
            if (block.isOf(Blocks.ENDER_CHEST) && isActionBlocked(player, BlockedAction.USING_ENDER_CHEST)) {
                return ActionResult.FAIL;
            }
            if (block.isOf(Blocks.BREWING_STAND) && isActionBlocked(player, BlockedAction.USING_BREWING_STAND)) {
                return ActionResult.FAIL;
            }
            if (block.isOf(Blocks.BEACON) && isActionBlocked(player, BlockedAction.USING_BEACON)) {
                return ActionResult.FAIL;
            }
            if (block.getBlock() instanceof ShulkerBoxBlock && isActionBlocked(player, BlockedAction.USING_SHULKER_BOX)) {
                return ActionResult.FAIL;
            }
            if (block.isOf(Blocks.SHULKER_BOX) && isActionBlocked(player, BlockedAction.USING_SHULKER_BOX)) {
                return ActionResult.FAIL;
            }
            if (block.isOf(Blocks.ENCHANTING_TABLE) && isActionBlocked(player, BlockedAction.USING_ENCHANTING_TABLE)) {
                return ActionResult.FAIL;
            }
            if (isToolBlocked(player, hand)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof BoatEntity && isActionBlocked(player, BlockedAction.USING_BOAT)) {
                return ActionResult.FAIL;
            }
            if (entity instanceof VillagerEntity villagerEntity && isVillagerBlocked(player, villagerEntity.getVillagerData().getProfession())) {
                villagerEntity.sayNo();
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
        ServerWorldLoadEvents.LOAD.register((server, world) -> advancementsCount = 0);
    }

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

    private boolean isFoodBlocked(PlayerEntity player, FoodComponent food) {
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

    private boolean isToolBlocked(PlayerEntity player, Hand hand) {
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

    private boolean isVillagerBlocked(PlayerEntity player, VillagerProfession profession) {
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getVillagerProfession() == profession) {
                return isActionBlocked(player, action);
            }
        }
        return false;
    }

    @ClientOnly
    public boolean isPanoramaReady(SpyglassPanoramaDetails panoramaDetails) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (isPanoramaLoading) {
            return false;
        }
        List<Integer> missingPanoramaFaces = new ArrayList<>();
        for (int face = 0; face < 6; face++) {
            if (client.getTextureManager().getOrDefault(panoramaDetails.generateFaceTextureId(face), MissingSprite.getMissingSpriteTexture()) == MissingSprite.getMissingSpriteTexture()) {
                missingPanoramaFaces.add(face);
            }
        }
        if (missingPanoramaFaces.isEmpty()) {
            return true;
        }
        File cacheDir = new File(client.runDirectory, "panorama");
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            return false;
        }
        isPanoramaLoading = true;
        boolean isPanoramaExists = true;
        for (int face : missingPanoramaFaces) {
            boolean isLastLoading = face == missingPanoramaFaces.get(missingPanoramaFaces.size() - 1);
            File cacheFile = panoramaDetails.generateCacheFile(cacheDir, face);
            boolean isFaceExists = cacheFile.exists();
            if (!isFaceExists) {
                isPanoramaExists = false;
            }
            String url = panoramaDetails.generateURL(face);
            SpyglassPanoramaTexture spyglassPanoramaTexture = new SpyglassPanoramaTexture(cacheFile, url, (success) -> {
                if (!isLastLoading || client.player == null) {
                    return;
                }
                isPanoramaLoading = false;
                if (!isFaceExists) {
                    if (success) {
                        client.player.sendMessage(Text.of(Text.translatable("spyglass.panorama.loading.success").getString()).copy().formatted(Formatting.GREEN), true);
                    } else {
                        client.player.sendMessage(Text.of(Text.translatable("spyglass.panorama.loading.error").getString()).copy().formatted(Formatting.RED), true);
                    }
                }
            });
            client.getTextureManager().registerTexture(panoramaDetails.generateFaceTextureId(face), spyglassPanoramaTexture);
        }
        if (!isPanoramaExists && client.player != null) {
            client.player.sendMessage(Text.of(Text.translatable("spyglass.panorama.loading").getString()).copy().formatted(Formatting.YELLOW), true);
        }
        return isPanoramaExists;
    }

    @ClientOnly
    public static void setAdvancementsCount(String playerName, int count) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || !client.player.getEntityName().equals(playerName)) {
            return;
        }
        if (count >= 0 && count <= advancementsCount) {
            return;
        }
        int oldCount = advancementsCount;
        advancementsCount = count;
        if (oldCount != 0) {
            for (BlockedAction action : BlockedAction.values()) {
                if (advancementsCount >= action.getUnblockAdvancementsCount() && oldCount < action.getUnblockAdvancementsCount()) {
                    Advancement advancement = client.player.networkHandler.getAdvancementHandler().getManager().get(action.buildAdvancementId());
                    client.getToastManager().add(new UnblockActionToast(advancement, action));
                }
            }
        }
    }

    private void registerPacks() {
        QuiltLoader.getModContainer(AchieveToDo.ID).ifPresent((modContainer) -> {
            ResourceLoader.registerBuiltinResourcePack(new Identifier(BACAP_OVERRIDE_DATA_PACK.replace("/", ":")), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(BACAP_OVERRIDE_HARDCORE_DATA_PACK.replace("/", ":")), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(BACAP_REWARDS_ITEM_DATA_PACK_NAME.replace("/", ":")), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(BACAP_REWARDS_EXPERIENCE_DATA_PACK_NAME.replace("/", ":")), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(BACAP_REWARDS_TROPHY_DATA_PACK_NAME.replace("/", ":")), modContainer, ResourcePackActivationType.NORMAL);

            ResourceLoader.registerBuiltinResourcePack(new Identifier(BACAP_LANGUAGE_PACK.replace("/", ":")), modContainer, ResourcePackActivationType.DEFAULT_ENABLED, Text.of("BACAP Language Pack"));
        });
    }

    private void registerBlocks() {
        Registry.register(Registries.BLOCK, ANCIENT_CITY_PORTAL_BLOCK_ID, ANCIENT_CITY_PORTAL_BLOCK);
        Registry.register(Registries.BLOCK, REINFORCED_DEEPSLATE_CHARGED_BLOCK_ID, REINFORCED_DEEPSLATE_CHARGED_BLOCK);
        Registry.register(Registries.BLOCK, REINFORCED_DEEPSLATE_BROKEN_BLOCK_ID, REINFORCED_DEEPSLATE_BROKEN_BLOCK);
    }

    private void registerItems() {
        Registry.register(Registries.ITEM, LOCKED_ACTION_ITEM_ID, LOCKED_ACTION_ITEM);
        Registry.register(Registries.ITEM, ANCIENT_CITY_PORTAL_HINT_ITEM_ID, ANCIENT_CITY_PORTAL_HINT_ITEM);
        Registry.register(Registries.ITEM, REINFORCED_DEEPSLATE_CHARGED_BLOCK_ID, new BlockItem(REINFORCED_DEEPSLATE_CHARGED_BLOCK, new QuiltItemSettings()));
        Registry.register(Registries.ITEM, REINFORCED_DEEPSLATE_BROKEN_BLOCK_ID, new BlockItem(REINFORCED_DEEPSLATE_BROKEN_BLOCK, new QuiltItemSettings()));
    }

    private void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, ANCIENT_CITY_PORTAL_PARTICLES_ID, ANCIENT_CITY_PORTAL_PARTICLES);
    }

    private void registerEntities() {
        Registry.register(Registries.ENTITY_TYPE, ANCIENT_CITY_PORTAL_TAB_ENTITY_ID, ANCIENT_CITY_PORTAL_TAB);
        Registry.register(Registries.ENTITY_TYPE, ANCIENT_CITY_PORTAL_ADVANCEMENT_ENTITY_ID, ANCIENT_CITY_PORTAL_ADVANCEMENT);
        Registry.register(Registries.ENTITY_TYPE, ANCIENT_CITY_PORTAL_HINT_ENTITY_ID, ANCIENT_CITY_PORTAL_HINT);
        Registry.register(Registries.ENTITY_TYPE, ANCIENT_CITY_PORTAL_EXPERIENCE_ORB_ENTITY_ID, ANCIENT_CITY_PORTAL_EXPERIENCE_ORB);
    }
}
