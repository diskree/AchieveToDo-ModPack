package com.diskree.achievetodo;

import com.diskree.achievetodo.advancements.AdvancementGenerator;
import com.diskree.achievetodo.advancements.UnblockActionToast;
import com.diskree.achievetodo.ancient_city_portal.*;
import com.mojang.brigadier.Command;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.item.UnclampedModelPredicateProvider;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;
import org.quiltmc.qsl.command.api.CommandRegistrationCallback;
import org.quiltmc.qsl.entity.api.QuiltEntityTypeBuilder;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.quiltmc.qsl.resource.loader.api.ResourcePackActivationType;

public class AchieveToDoMod implements ModInitializer {

    public static final String MOD_ID = "achievetodo";

    public static final String BACAP_DATA_PACK_NAME = "bacap";
    public static final String BACAP_HARDCORE_DATA_PACK_NAME = "bacap_hardcore";
    public static final String ACHIEVETODO_DATA_PACK_NAME = "achievetodo";
    public static final String ACHIEVETODO_HARDCORE_DATA_PACK_NAME = "achievetodo_hardcore";
    public static final String REWARDS_ITEM_DATA_PACK_NAME = "rewards_item";
    public static final String REWARDS_EXPERIENCE_DATA_PACK_NAME = "rewards_experience";
    public static final String REWARDS_TROPHY_DATA_PACK_NAME = "rewards_trophy";
    public static final String TERRALITH_DATA_PACK_NAME = "terralith";
    public static final String BACAP_TERRALITH_DATA_PACK_NAME = "bacap_terralith";
    public static final String AMPLIFIED_NETHER_DATA_PACK_NAME = "amplified_nether";
    public static final String BACAP_AMPLIFIED_NETHER_DATA_PACK_NAME = "bacap_amplified_nether";
    public static final String NULLSCAPE_DATA_PACK_NAME = "nullscape";
    public static final String BACAP_NULLSCAPE_DATA_PACK_NAME = "bacap_nullscape";

    public static final Identifier BACAP_LANGUAGE_RESOURCE_PACK_ID = new Identifier(MOD_ID, "bacap_lp");
    public static final Identifier VISUAL_FISH_BUCKETS_RESOURCE_PACK_ID = new Identifier(MOD_ID, "visual_fish_buckets");
    public static final Identifier GOAT_HORNS_RESOURCE_PACK_ID = new Identifier(MOD_ID, "goat_horns");
    public static final Identifier ENCHANTED_BOOKS_RESOURCE_PACK_ID = new Identifier(MOD_ID, "enchanted_books");
    public static final Identifier WAXED_COPPER_RESOURCE_PACK_ID = new Identifier(MOD_ID, "waxed_copper");

    public static final Identifier ANCIENT_CITY_PORTAL_BLOCK_ID = new Identifier(MOD_ID, "ancient_city_portal");
    public static final AncientCityPortalBlock ANCIENT_CITY_PORTAL_BLOCK = new AncientCityPortalBlock(AbstractBlock.Settings.create()
            .noCollision()
            .ticksRandomly()
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(-1.0f, 3600000.0f)
            .dropsNothing()
            .sounds(BlockSoundGroup.GLASS)
            .luminance((blockState) -> 11)
            .pistonBehavior(PistonBehavior.BLOCK));

    public static final Identifier REINFORCED_DEEPSLATE_CHARGED_BLOCK_ID = new Identifier(MOD_ID, "reinforced_deepslate_charged");
    public static final Block REINFORCED_DEEPSLATE_CHARGED_BLOCK = new Block(QuiltBlockSettings.create());

    public static final Identifier REINFORCED_DEEPSLATE_BROKEN_BLOCK_ID = new Identifier(MOD_ID, "reinforced_deepslate_broken");
    public static final Block REINFORCED_DEEPSLATE_BROKEN_BLOCK = new Block(QuiltBlockSettings.create());

    public static final Identifier LOCKED_ACTION_ITEM_ID = new Identifier(MOD_ID, "locked_action");
    public static final Item LOCKED_ACTION_ITEM = new Item(new QuiltItemSettings());

    public static final Identifier ANCIENT_CITY_PORTAL_HINT_ITEM_ID = new Identifier(MOD_ID, "ancient_city_portal_hint");
    public static final Item ANCIENT_CITY_PORTAL_HINT_ITEM = new Item(new QuiltItemSettings().maxDamage(1000));

    public static final DefaultParticleType ANCIENT_CITY_PORTAL_PARTICLES = FabricParticleTypes.simple();

    public static final Identifier JUKEBOX_PLAY_EVENT_ID = new Identifier(MOD_ID, "jukebox_play");
    public static final GameEvent JUKEBOX_PLAY = new GameEvent(JUKEBOX_PLAY_EVENT_ID.toString(), AncientCityPortalEntity.RITUAL_RADIUS);
    public static final EntityType<AncientCityPortalEntity> ANCIENT_CITY_PORTAL_ADVANCEMENT = QuiltEntityTypeBuilder.create(SpawnGroup.MISC, AncientCityPortalEntity::new)
            .setDimensions(EntityDimensions.changing(0.0f, 0.0f))
            .maxChunkTrackingRange(10)
            .trackingTickInterval(1)
            .build();

    public static final Identifier JUKEBOX_STOP_PLAY_EVENT_ID = new Identifier(MOD_ID, "jukebox_stop_play");
    public static final GameEvent JUKEBOX_STOP_PLAY = new GameEvent(JUKEBOX_STOP_PLAY_EVENT_ID.toString(), AncientCityPortalEntity.RITUAL_RADIUS);
    public static final Identifier ANCIENT_CITY_PORTAL_TAB_ENTITY_ID = new Identifier(MOD_ID, "ancient_city_portal_tab_entity");
    public static final EntityType<AncientCityPortalTabEntity> ANCIENT_CITY_PORTAL_TAB = QuiltEntityTypeBuilder.create(SpawnGroup.MISC, AncientCityPortalTabEntity::new)
            .setDimensions(EntityDimensions.changing(0.0f, 0.0f))
            .maxChunkTrackingRange(10)
            .trackingTickInterval(1)
            .build();

    public static final Identifier ANCIENT_CITY_PORTAL_ADVANCEMENT_ENTITY_ID = new Identifier(MOD_ID, "ancient_city_portal_advancement_entity");
    public static final Identifier ANCIENT_CITY_PORTAL_HINT_ENTITY_ID = new Identifier(MOD_ID, "ancient_city_portal_prompt_entity");
    public static final EntityType<AncientCityPortalPromptEntity> ANCIENT_CITY_PORTAL_HINT = QuiltEntityTypeBuilder.create(SpawnGroup.MISC, AncientCityPortalPromptEntity::new)
            .setDimensions(EntityDimensions.changing(0.0f, 0.0f))
            .maxChunkTrackingRange(10)
            .trackingTickInterval(1)
            .build();

    public static final Identifier ANCIENT_CITY_PORTAL_EXPERIENCE_ORB_ENTITY_ID = new Identifier(MOD_ID, "ancient_city_portal_experience_orb");
    public static final EntityType<AncientCityPortalExperienceOrbEntity> ANCIENT_CITY_PORTAL_EXPERIENCE_ORB = QuiltEntityTypeBuilder.<AncientCityPortalExperienceOrbEntity>create(SpawnGroup.MISC, AncientCityPortalExperienceOrbEntity::new)
            .setDimensions(EntityDimensions.changing(0.5f, 0.5f))
            .maxChunkTrackingRange(6)
            .trackingTickInterval(20)
            .build();

    public static final SoundEvent MUSIC_DISC_5_ACTIVATOR = SoundEvent.createVariableRangeEvent(new Identifier(MOD_ID, "music_disc_5_activator"));
    public static final Identifier EVOKER_NO_TOTEM_OF_UNDYING_LOOT_TABLE_ID = new Identifier(MOD_ID, "entities/evoker_no_totem_of_undying");

    public static final int ADVANCEMENTS_SCREEN_MARGIN = 30;

    public static int currentAdvancementsCount = 0;

    public static void grantHintsAdvancement(String pathName) {
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return;
        }
        ServerPlayerEntity player = server.getPlayerManager().getPlayerList().get(0);
        Advancement advancement = server.getAdvancementLoader().get(new Identifier(MOD_ID, "hints/" + pathName));
        AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
        for (String criterion : advancementProgress.getUnobtainedCriteria()) {
            player.getAdvancementTracker().grantCriterion(advancement, criterion);
        }
    }

    public static void setAdvancementsCount(int count) {
        if (count >= 0 && count <= currentAdvancementsCount) {
            return;
        }
        int oldCount = currentAdvancementsCount;
        currentAdvancementsCount = count;

        if (oldCount != 0) {
            IntegratedServer server = MinecraftClient.getInstance().getServer();
            if (server != null) {
                for (BlockedAction action : BlockedAction.values()) {
                    if (action.isUnblocked() && oldCount < action.getUnblockAdvancementsCount()) {
                        Advancement advancement = server.getAdvancementLoader().get(action.buildAdvancementId());
                        MinecraftClient.getInstance().getToastManager().add(new UnblockActionToast(advancement, action));
                    }
                }
            }
        }
    }

    public static BlockedAction getBlockedActionFromAdvancement(Advancement advancement) {
        return BlockedAction.map(advancement.getId().getPath().split("/")[1]);
    }

    public static boolean isActionBlocked(BlockedAction action) {
        if (action == null || action.isUnblocked() || isModDisabled()) {
            return false;
        }
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.sendMessage(action.buildBlockedDescription(), true);
        }
        grantActionAdvancement(action);
        return true;
    }

    public static boolean isFoodBlocked(FoodComponent food) {
        if (food == null || isModDisabled()) {
            return false;
        }
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getFoodComponent() == food) {
                return isActionBlocked(action);
            }
        }
        return false;
    }

    public static boolean isEquipmentBlocked(Item item) {
        if (isModDisabled()) {
            return false;
        }
        if (item == Items.ELYTRA && isActionBlocked(BlockedAction.EQUIP_ELYTRA)) {
            return true;
        }
        if (item instanceof ArmorItem) {
            ArmorMaterial armorMaterial = ((ArmorItem) item).getMaterial();
            return armorMaterial == ArmorMaterials.IRON && isActionBlocked(BlockedAction.EQUIP_IRON_ARMOR) ||
                    armorMaterial == ArmorMaterials.DIAMOND && isActionBlocked(BlockedAction.EQUIP_DIAMOND_ARMOR) ||
                    armorMaterial == ArmorMaterials.NETHERITE && isActionBlocked(BlockedAction.EQUIP_NETHERITE_ARMOR);
        }
        return false;
    }

    public static boolean isVillagerBlocked(VillagerProfession profession) {
        if (isModDisabled()) {
            return false;
        }
        for (BlockedAction action : BlockedAction.values()) {
            if (action.getVillagerProfession() == profession) {
                return isActionBlocked(action);
            }
        }
        return false;
    }

    private static boolean isModDisabled() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        return player == null || player.isCreative() || player.getWorld().isClient && !MinecraftClient.getInstance().isInSingleplayer();
    }

    private static void grantActionAdvancement(BlockedAction action) {
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return;
        }
        ServerPlayerEntity player = server.getPlayerManager().getPlayerList().get(0);
        Advancement advancement = server.getAdvancementLoader().get(action.buildAdvancementId());
        AdvancementProgress advancementProgress = player.getAdvancementTracker().getProgress(advancement);
        for (String criterion : advancementProgress.getUnobtainedCriteria()) {
            player.getAdvancementTracker().grantCriterion(advancement, criterion);
        }
    }

    @Override
    public void onInitialize(ModContainer mod) {
        registerPacks();
        registerBlocks();
        registerItems();
        registerParticles();
        registerEvents();
        registerEntities();
        registerNetworking();

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
        ServerWorldLoadEvents.LOAD.register((server, world) -> currentAdvancementsCount = 0);
        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> dispatcher.register(CommandManager.literal("random").executes((context -> {
            Advancement randomAdvancement = isModDisabled() ? null : AdvancementGenerator.generateForCommand();
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
                context.getSource().sendSystemMessage(Text.of(Text.translatable("commands.random.no_advancements").toString()));
            }
            return Command.SINGLE_SUCCESS;
        }))));
    }

    private void registerPacks() {
        QuiltLoader.getModContainer(MOD_ID).ifPresent((modContainer) -> {
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, BACAP_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, BACAP_HARDCORE_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, ACHIEVETODO_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, ACHIEVETODO_HARDCORE_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, REWARDS_ITEM_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, REWARDS_EXPERIENCE_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, REWARDS_TROPHY_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, TERRALITH_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, BACAP_TERRALITH_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, AMPLIFIED_NETHER_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, BACAP_AMPLIFIED_NETHER_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, NULLSCAPE_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);
            ResourceLoader.registerBuiltinResourcePack(new Identifier(MOD_ID, BACAP_NULLSCAPE_DATA_PACK_NAME), modContainer, ResourcePackActivationType.NORMAL);

            ResourceLoader.registerBuiltinResourcePack(BACAP_LANGUAGE_RESOURCE_PACK_ID, modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
            ResourceLoader.registerBuiltinResourcePack(VISUAL_FISH_BUCKETS_RESOURCE_PACK_ID, modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
            ResourceLoader.registerBuiltinResourcePack(GOAT_HORNS_RESOURCE_PACK_ID, modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
            ResourceLoader.registerBuiltinResourcePack(ENCHANTED_BOOKS_RESOURCE_PACK_ID, modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
            ResourceLoader.registerBuiltinResourcePack(WAXED_COPPER_RESOURCE_PACK_ID, modContainer, ResourcePackActivationType.DEFAULT_ENABLED);
        });
    }

    private void registerBlocks() {
        Registry.register(Registries.BLOCK, ANCIENT_CITY_PORTAL_BLOCK_ID, ANCIENT_CITY_PORTAL_BLOCK);
        BlockRenderLayerMap.put(RenderLayer.getTranslucent(), ANCIENT_CITY_PORTAL_BLOCK);

        Registry.register(Registries.BLOCK, REINFORCED_DEEPSLATE_CHARGED_BLOCK_ID, REINFORCED_DEEPSLATE_CHARGED_BLOCK);
        Registry.register(Registries.BLOCK, REINFORCED_DEEPSLATE_BROKEN_BLOCK_ID, REINFORCED_DEEPSLATE_BROKEN_BLOCK);
    }

    private void registerItems() {
        Registry.register(Registries.ITEM, LOCKED_ACTION_ITEM_ID, LOCKED_ACTION_ITEM);
        Registry.register(Registries.ITEM, ANCIENT_CITY_PORTAL_HINT_ITEM_ID, ANCIENT_CITY_PORTAL_HINT_ITEM);
        Registry.register(Registries.ITEM, REINFORCED_DEEPSLATE_CHARGED_BLOCK_ID, new BlockItem(REINFORCED_DEEPSLATE_CHARGED_BLOCK, new QuiltItemSettings()));
        Registry.register(Registries.ITEM, REINFORCED_DEEPSLATE_BROKEN_BLOCK_ID, new BlockItem(REINFORCED_DEEPSLATE_BROKEN_BLOCK, new QuiltItemSettings()));

        ModelPredicateProviderRegistry.register(ANCIENT_CITY_PORTAL_HINT_ITEM, ModelPredicateProviderRegistry.DAMAGE_ID, ModelPredicateProviderRegistry.DAMAGE_PROVIDER);
        UnclampedModelPredicateProvider potionsPredicateProvider = (stack, world, entity, seed) -> {
            NbtCompound nbt = stack.getNbt();
            if (nbt == null) {
                return 0.0f;
            }
            String potion = nbt.getString("Potion");
            if (potion == null) {
                return 0.0f;
            }
            Identifier potionId = new Identifier(potion);
            if (potionId.getPath().startsWith("long_")) {
                return 0.5f;
            }
            if (potionId.getPath().startsWith("strong_")) {
                return 1.0f;
            }
            return 0.0f;
        };
        ModelPredicateProviderRegistry.register(Items.POTION, new Identifier("long_or_strong"), potionsPredicateProvider);
        ModelPredicateProviderRegistry.register(Items.SPLASH_POTION, new Identifier("long_or_strong"), potionsPredicateProvider);
        ModelPredicateProviderRegistry.register(Items.LINGERING_POTION, new Identifier("long_or_strong"), potionsPredicateProvider);
        ModelPredicateProviderRegistry.register(Items.TIPPED_ARROW, new Identifier("long_or_strong"), potionsPredicateProvider);
    }

    private void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "ancient_city_portal_particles"), ANCIENT_CITY_PORTAL_PARTICLES);
        ParticleFactoryRegistry.getInstance().register(ANCIENT_CITY_PORTAL_PARTICLES, AncientCityPortalParticleFactory::new);
    }

    private void registerEvents() {
        Registry.register(Registries.GAME_EVENT, JUKEBOX_PLAY_EVENT_ID, JUKEBOX_PLAY);
        Registry.register(Registries.GAME_EVENT, JUKEBOX_STOP_PLAY_EVENT_ID, JUKEBOX_STOP_PLAY);
    }

    private void registerEntities() {
        Registry.register(Registries.ENTITY_TYPE, ANCIENT_CITY_PORTAL_TAB_ENTITY_ID, ANCIENT_CITY_PORTAL_TAB);
        EntityRendererRegistry.register(ANCIENT_CITY_PORTAL_TAB, AncientCityPortalItemDisplayEntityRenderer::new);

        Registry.register(Registries.ENTITY_TYPE, ANCIENT_CITY_PORTAL_ADVANCEMENT_ENTITY_ID, ANCIENT_CITY_PORTAL_ADVANCEMENT);
        EntityRendererRegistry.register(ANCIENT_CITY_PORTAL_ADVANCEMENT, AncientCityPortalItemDisplayEntityRenderer::new);

        Registry.register(Registries.ENTITY_TYPE, ANCIENT_CITY_PORTAL_HINT_ENTITY_ID, ANCIENT_CITY_PORTAL_HINT);
        EntityRendererRegistry.register(ANCIENT_CITY_PORTAL_HINT, AncientCityPortalItemDisplayEntityRenderer::new);

        Registry.register(Registries.ENTITY_TYPE, ANCIENT_CITY_PORTAL_EXPERIENCE_ORB_ENTITY_ID, ANCIENT_CITY_PORTAL_EXPERIENCE_ORB);
        EntityRendererRegistry.register(ANCIENT_CITY_PORTAL_EXPERIENCE_ORB, AncientCityPortalExperienceOrbEntityRenderer::new);
    }

    private void registerNetworking() {
        ClientPlayNetworking.registerGlobalReceiver(new Identifier(MOD_ID, "ancient_city_portal_experience_c2s_packet"), (client, handler, buf, responseSender) -> new AncientCityPortalExperienceOrbSpawnS2CPacket(buf).apply(handler));
    }

    private boolean isToolBlocked(ItemStack stack) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null || player.isCreative() || !stack.isDamageable()) {
            return false;
        }
        Item item = stack.getItem();
        if (item instanceof ToolItem) {
            ToolMaterial toolMaterial = ((ToolItem) item).getMaterial();
            return toolMaterial == ToolMaterials.STONE && isActionBlocked(BlockedAction.USING_STONE_TOOLS) ||
                    toolMaterial == ToolMaterials.IRON && isActionBlocked(BlockedAction.USING_IRON_TOOLS) ||
                    toolMaterial == ToolMaterials.DIAMOND && isActionBlocked(BlockedAction.USING_DIAMOND_TOOLS) ||
                    toolMaterial == ToolMaterials.NETHERITE && isActionBlocked(BlockedAction.USING_NETHERITE_TOOLS);
        }
        return false;
    }
}
