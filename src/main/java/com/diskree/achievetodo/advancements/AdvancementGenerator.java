package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class AdvancementGenerator {

    @Nullable
    public static Advancement generateForCommand() {
        return generateRandomAdvancement(true);
    }

    @Nullable
    public static AdvancementHint generateForHint() {
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return null;
        }
        ServerPlayerEntity serverPlayer = server.getPlayerManager().getPlayerList().get(0);
        if (serverPlayer == null) {
            return null;
        }
        Advancement advancement = generateRandomAdvancement(false);
        if (advancement == null) {
            return null;
        }
        AdvancementDisplay tabDisplay = advancement.getRoot().getDisplay();
        AdvancementDisplay advancementDisplay = advancement.getDisplay();
        if (tabDisplay == null || advancementDisplay == null) {
            return null;
        }
        ArrayList<String> incompleteCriteria = new ArrayList<>();
        AdvancementProgress progress = serverPlayer.getAdvancementTracker().getProgress(advancement);
        for (String[] requirement : advancement.getRequirements()) {
            boolean isRequirementCompleted = false;
            for (String criterion : requirement) {
                CriterionProgress criterionProgress = progress.getCriterionProgress(criterion);
                if (criterionProgress != null && criterionProgress.isObtained()) {
                    isRequirementCompleted = true;
                    break;
                }
            }
            if (!isRequirementCompleted && requirement.length > 0) {
                incompleteCriteria.add(requirement[0]);
            }
        }
        if (incompleteCriteria.isEmpty()) {
            return null;
        }
        String criterion = incompleteCriteria.get(serverPlayer.getRandom().nextBetween(0, incompleteCriteria.size() - 1));
        Item hintItem = null;
        NbtCompound nbt = new NbtCompound();
        switch (advancement.getId().toString()) {
            case "blazeandcave:adventure/chromatic_armory" -> {
                switch (criterion) {
                    case "amethyst" -> hintItem = Items.AMETHYST_SHARD;
                    case "copper" -> hintItem = Items.COPPER_INGOT;
                    case "gold" -> hintItem = Items.GOLD_INGOT;
                    case "iron" -> hintItem = Items.IRON_INGOT;
                    case "lapis" -> hintItem = Items.LAPIS_LAZULI;
                    case "netherite" -> hintItem = Items.NETHERITE_INGOT;
                }
            }
            case "blazeandcave:adventure/undying_fandom" -> {
                String tippedArrowPrefix = "tipped_arrow_";
                if (criterion.startsWith(tippedArrowPrefix)) {
                    hintItem = Items.TIPPED_ARROW;
                    nbt.putString("Potion", new Identifier(criterion.replace(tippedArrowPrefix, "")).toString());
                }
            }
            case "blazeandcave:animal/axolotl_of_them" -> {
                hintItem = Items.AXOLOTL_BUCKET;
                nbt.putInt("Variant", switch (criterion) {
                    case "leucistic" -> 0;
                    case "wild" -> 1;
                    case "gold" -> 2;
                    case "cyan" -> 3;
                    case "blue" -> 4;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:animal/caprymphony" -> {
                switch (criterion) {
                    case "ponder" -> hintItem = Items.BARRIER;
                    case "sing" -> hintItem = Items.BARRIER;
                    case "seek" -> hintItem = Items.BARRIER;
                    case "feel" -> hintItem = Items.BARRIER;
                    case "admire" -> hintItem = Items.BARRIER;
                    case "call" -> hintItem = Items.BARRIER;
                    case "yearn" -> hintItem = Items.BARRIER;
                    case "dream" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:animal/colorful_cavalry" -> {
                hintItem = Items.LEATHER_HORSE_ARMOR;
                NbtCompound color = new NbtCompound();
                color.putInt("color", switch (criterion) {
                    case "white" -> 16383998;
                    case "orange" -> 16351261;
                    case "magenta" -> 13061821;
                    case "light_blue" -> 3847130;
                    case "yellow" -> 16701501;
                    case "lime" -> 8439583;
                    case "pink" -> 15961002;
                    case "gray" -> 4673362;
                    case "light_gray" -> 10329495;
                    case "cyan" -> 1481884;
                    case "purple" -> 8991416;
                    case "blue" -> 3949738;
                    case "brown" -> 8606770;
                    case "green" -> 6192150;
                    case "red" -> 11546150;
                    case "black" -> 1908001;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
                nbt.put("display", color);
            }
            case "blazeandcave:animal/shoe_shed" -> {
                hintItem = Items.LEATHER_BOOTS;
                NbtCompound color = new NbtCompound();
                color.putInt("color", switch (criterion) {
                    case "white" -> 16383998;
                    case "orange" -> 16351261;
                    case "magenta" -> 13061821;
                    case "light_blue" -> 3847130;
                    case "yellow" -> 16701501;
                    case "lime" -> 8439583;
                    case "pink" -> 15961002;
                    case "gray" -> 4673362;
                    case "light_gray" -> 10329495;
                    case "cyan" -> 1481884;
                    case "purple" -> 8991416;
                    case "blue" -> 3949738;
                    case "brown" -> 8606770;
                    case "green" -> 6192150;
                    case "red" -> 11546150;
                    case "black" -> 1908001;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
                nbt.put("display", color);
            }
            case "blazeandcave:animal/tropical_collection" -> {
                hintItem = Items.TROPICAL_FISH_BUCKET;
                nbt.putInt("BucketVariantTag", switch (criterion) {
                    case "fish1" -> 65536;
                    case "fish2" -> 917504;
                    case "fish3" -> 918273;
                    case "fish4" -> 918529;
                    case "fish5" -> 16778497;
                    case "fish6" -> 50660352;
                    case "fish7" -> 50726144;
                    case "fish8" -> 67108865;
                    case "fish9" -> 67110144;
                    case "fish10" -> 67371009;
                    case "fish11" -> 67764993;
                    case "fish12" -> 101253888;
                    case "fish13" -> 117441025;
                    case "fish14" -> 67699456;
                    case "fish15" -> 117441793;
                    case "fish16" -> 117506305;
                    case "fish17" -> 117899265;
                    case "fish18" -> 118161664;
                    case "fish19" -> 459008;
                    case "fish20" -> 185008129;
                    case "fish21" -> 234882305;
                    case "fish22" -> 235340288;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:building/ah_my_old_enemy" -> {
                if (criterion.equals("prismarine_bricks_stairs")) {
                    hintItem = Items.PRISMARINE_BRICK_STAIRS;
                }
            }
            case "blazeandcave:building/armor_display" -> criterion += "_chestplate";
            case "blazeandcave:building/creepers_and_withers" -> criterion = "chiseled_" + criterion;
            case "blazeandcave:building/fake_fortress" -> {
                switch (criterion) {
                    case "red_nether_brick" -> hintItem = Items.RED_NETHER_BRICKS;
                    case "cracked_nether_brick" -> hintItem = Items.CRACKED_NETHER_BRICKS;
                }
            }
            case "blazeandcave:building/fake_stronghold" -> {
                switch (criterion) {
                    case "normal" -> hintItem = Items.STONE_BRICKS;
                    case "normal_slab" -> hintItem = Items.STONE_BRICK_SLAB;
                    case "normal_stairs" -> hintItem = Items.STONE_BRICK_STAIRS;
                    case "normal_wall" -> hintItem = Items.STONE_BRICK_WALL;
                    case "mossy" -> hintItem = Items.MOSSY_STONE_BRICKS;
                    case "mossy_slab" -> hintItem = Items.MOSSY_STONE_BRICK_SLAB;
                    case "mossy_stairs" -> hintItem = Items.MOSSY_STONE_BRICK_STAIRS;
                    case "mossy_wall" -> hintItem = Items.MOSSY_STONE_BRICK_WALL;
                    case "cracked" -> hintItem = Items.CRACKED_STONE_BRICKS;
                    case "chiseled" -> hintItem = Items.CHISELED_STONE_BRICKS;
                }
            }
            case "blazeandcave:building/greek_art_decor" -> {
                if (criterion.equals("quartz_chiseled")) {
                    hintItem = Items.CHISELED_QUARTZ_BLOCK;
                }
            }
            case "blazeandcave:building/washing_machine" -> {
                switch (criterion) {
                    case "clean_leather_armor" -> hintItem = Items.LEATHER_CHESTPLATE;
                    case "clean_banner" -> hintItem = Items.CYAN_BANNER;
                    case "clean_shulker_box" -> hintItem = Items.CYAN_SHULKER_BOX;
                }
            }
            case "blazeandcave:challenges/all_the_items" -> {
                if (criterion.equals("netherite_upgrade") || criterion.endsWith("_armor_trim")) {
                    criterion += "_smithing_template";
                }
            }
            case "blazeandcave:challenges/riddle_me_this" -> {
                switch (criterion) {
                    case "first_line" -> hintItem = Items.BARRIER;
                    case "second_line" -> hintItem = Items.BARRIER;
                    case "third_line" -> hintItem = Items.BARRIER;
                    case "fourth_line" -> hintItem = Items.BARRIER;
                    case "fifth_line" -> hintItem = Items.BARRIER;
                    case "sixth_line" -> hintItem = Items.BARRIER;
                    case "seventh_line" -> hintItem = Items.BARRIER;
                    case "eighth_line" -> hintItem = Items.BARRIER;
                    case "ninth_line" -> hintItem = Items.BARRIER;
                    case "tenth_line" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:challenges/stack_all_the_items" -> {
                if (criterion.equals("smithing_template")) {
                    hintItem = Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE;
                }
            }
            case "blazeandcave:enchanting/master_armorer" -> {
                switch (criterion) {
                    case "helmet_protection" -> hintItem = Items.DIAMOND_HELMET;
                    case "chestplate_protection" -> hintItem = Items.DIAMOND_CHESTPLATE;
                    case "leggings_protection" -> hintItem = Items.DIAMOND_LEGGINGS;
                    case "boots_protection_depth_strider" -> hintItem = Items.DIAMOND_BOOTS;
                }
            }
            case "blazeandcave:farming/a_gluttonous_diet", "blazeandcave:farming/meat_lovers", "minecraft:husbandry/balanced_diet" -> {
                String rawPrefix = "raw_";
                if (criterion.startsWith(rawPrefix)) {
                    criterion = criterion.replace(rawPrefix, "");
                }
            }
            case "blazeandcave:farming/combine_harvester" -> {
                switch (criterion) {
                    case "carrots" -> hintItem = Items.CARROT;
                    case "potatoes" -> hintItem = Items.POTATO;
                    case "beetroots" -> hintItem = Items.BEETROOT;
                    case "melon_stem" -> hintItem = Items.MELON;
                    case "pumpkin_stem" -> hintItem = Items.PUMPKIN;
                    case "tall_seagrass" -> hintItem = Items.SEAGRASS;
                    case "cocoa" -> hintItem = Items.COCOA_BEANS;
                    case "sweet_berry_bush" -> hintItem = Items.SWEET_BERRIES;
                    case "dripleaves" -> hintItem = Items.BIG_DRIPLEAF;
                    case "cave_vines" -> hintItem = Items.GLOW_BERRIES;
                }
            }
            case "blazeandcave:farming/come_to_the_countryside" -> {
                switch (criterion) {
                    case "pumpkin_stem" -> hintItem = Items.PUMPKIN;
                    case "melon_stem" -> hintItem = Items.MELON;
                    case "beetroots" -> hintItem = Items.BEETROOT;
                    case "carrots" -> hintItem = Items.CARROT;
                    case "potatoes" -> hintItem = Items.POTATO;
                    case "torchflower_crop" -> hintItem = Items.TORCHFLOWER;
                    case "pitcher_crop" -> hintItem = Items.PITCHER_PLANT;
                }
            }
            case "blazeandcave:farming/ecologist" -> {
                if (criterion.equals("bamboo_sapling")) {
                    hintItem = Items.BAMBOO;
                }
            }
            case "blazeandcave:farming/im_gonna_be_sick" -> {
                switch (criterion) {
                    case "oxeye_daisy_stew" -> hintItem = Items.OXEYE_DAISY;
                    case "cornflower_stew" -> hintItem = Items.CORNFLOWER;
                    case "lily_of_the_valley_stew" -> hintItem = Items.LILY_OF_THE_VALLEY;
                    case "wither_rose_stew" -> hintItem = Items.WITHER_ROSE;
                    case "tulip_stew" -> hintItem = Items.WHITE_TULIP;
                    case "azure_bluet_stew" -> hintItem = Items.AZURE_BLUET;
                    case "allium_stew" -> hintItem = Items.ALLIUM;
                    case "blue_orchid_dandelion_stew" -> hintItem = Items.BLUE_ORCHID;
                    case "poppy_stew" -> hintItem = Items.POPPY;
                }
            }
            case "blazeandcave:mining/bonfire_night" -> {
                if (criterion.equals("sweet_berry_bush")) {
                    hintItem = Items.SWEET_BERRIES;
                }
            }
            case "blazeandcave:mining/moar_broken_tools", "blazeandcave:mining/weve_broken_our_last_shovel" -> {
                if (criterion.startsWith("gold_")) {
                    criterion = criterion.replace("gold_", "golden_");
                }
            }
            case "blazeandcave:monsters/plane_walker", "blazeandcave:nether/lodes_of_applications" -> {
                switch (criterion) {
                    case "overworld" -> hintItem = Items.GRASS_BLOCK;
                    case "nether" -> hintItem = Items.NETHERRACK;
                    case "end" -> hintItem = Items.END_STONE;
                }
            }
            case "blazeandcave:nether/hung_drawn_and_bartered" -> {
                switch (criterion) {
                    case "splash_potion_fire_resistance" -> {
                        hintItem = Items.SPLASH_POTION;
                        nbt.putString("Potion", "minecraft:fire_resistance");
                    }
                    case "potion_fire_resistance" -> {
                        hintItem = Items.POTION;
                        nbt.putString("Potion", "minecraft:fire_resistance");
                    }
                    case "water_bottle" -> {
                        hintItem = Items.POTION;
                        nbt.putString("Potion", "minecraft:water");
                    }
                }
            }
            case "blazeandcave:nether/spreading_corruption", "blazeandcave:nether/the_struggle_nether_ends" -> {
                if (criterion.equals("soul_fire")) {
                    hintItem = Items.SOUL_CAMPFIRE;
                }
            }
            case "blazeandcave:potion/failed_concoctions" -> {
                switch (criterion) {
                    case "thick_potion" -> hintItem = Items.POTION;
                    case "mundane_potion" -> hintItem = Items.POTION;
                }
            }
            case "blazeandcave:potion/furious_ammunition" -> {
                hintItem = Items.TIPPED_ARROW;
                nbt.putString("Potion", new Identifier(criterion).toString());
            }
            case "blazeandcave:redstone/monstrous_music" -> {
                switch (criterion) {
                    case "zombie" -> hintItem = Items.ZOMBIE_HEAD;
                    case "skeleton" -> hintItem = Items.SKELETON_SKULL;
                    case "wither_skeleton" -> hintItem = Items.WITHER_SKELETON_SKULL;
                    case "creeper" -> hintItem = Items.CREEPER_HEAD;
                    case "piglin" -> hintItem = Items.PIGLIN_HEAD;
                    case "dragon" -> hintItem = Items.DRAGON_HEAD;
                }
            }
            case "blazeandcave:redstone/noteworthy" -> {
                switch (criterion) {
                    case "banjo" -> hintItem = Items.HAY_BLOCK;
                    case "basedrum" -> hintItem = Items.STONE;
                    case "bass" -> hintItem = Items.OAK_PLANKS;
                    case "bell" -> hintItem = Items.GOLD_BLOCK;
                    case "bit" -> hintItem = Items.EMERALD_BLOCK;
                    case "chime" -> hintItem = Items.PACKED_ICE;
                    case "cow_bell" -> hintItem = Items.SOUL_SAND;
                    case "didgeridoo" -> hintItem = Items.PUMPKIN;
                    case "flute" -> hintItem = Items.CLAY;
                    case "guitar" -> hintItem = Items.CYAN_WOOL;
                    case "harp" -> hintItem = Items.GRASS_BLOCK;
                    case "hat" -> hintItem = Items.CYAN_STAINED_GLASS;
                    case "iron_xylophone" -> hintItem = Items.IRON_BLOCK;
                    case "pling" -> hintItem = Items.GLOWSTONE;
                    case "snare" -> hintItem = Items.SAND;
                    case "xylophone" -> hintItem = Items.BONE_BLOCK;
                }
            }
            case "blazeandcave:redstone/target_practise" -> {
                if (criterion.equals("fishing_bobber")) {
                    hintItem = Items.FISHING_ROD;
                }
            }
            case "blazeandcave:weaponry/multiclassed" -> {
                switch (criterion) {
                    case "axe" -> hintItem = Items.DIAMOND_AXE;
                    case "shovel" -> hintItem = Items.DIAMOND_SHOVEL;
                    case "pickaxe" -> hintItem = Items.DIAMOND_PICKAXE;
                    case "hoe" -> hintItem = Items.DIAMOND_HOE;
                    case "sword" -> hintItem = Items.DIAMOND_SWORD;
                    case "trident_melee" -> hintItem = Items.TRIDENT;
                }
            }
            case "blazeandcave:weaponry/pyrotechnic" -> {
                switch (criterion) {
                    case "small_ball" -> hintItem = Items.GUNPOWDER;
                    case "large_ball" -> hintItem = Items.FIRE_CHARGE;
                    case "star" -> hintItem = Items.GOLD_NUGGET;
                    case "creeper" -> hintItem = Items.CREEPER_HEAD;
                    case "burst" -> hintItem = Items.FEATHER;
                    case "flicker" -> hintItem = Items.GLOWSTONE_DUST;
                    case "trail" -> hintItem = Items.DIAMOND;
                }
            }
            case "minecraft:adventure/trim_with_all_exclusive_armor_patterns" -> criterion += "_smithing_template";
            case "blazeandcave:adventure/feeling_ill",
                    "blazeandcave:animal/follow_the_leader",
                    "blazeandcave:challenges/biological_warfare",
                    "blazeandcave:challenges/death_from_all",
                    "blazeandcave:challenges/endergeddon",
                    "blazeandcave:challenges/highway_to_hell",
                    "blazeandcave:challenges/potion_master",
                    "blazeandcave:challenges/telescopic",
                    "blazeandcave:monsters/baby_baby_baby_noo",
                    "blazeandcave:monsters/dungeon_crawler",
                    "blazeandcave:monsters/hell_hunter",
                    "blazeandcave:monsters/night_runner",
                    "blazeandcave:monsters/void_ender",
                    "blazeandcave:redstone/monstrous_sacrifices",
                    "blazeandcave:weaponry/demolitions_expert",
                    "blazeandcave:weaponry/master_shieldsman",
                    "blazeandcave:weaponry/poseidon_vs_hades",
                    "blazeandcave:weaponry/the_aquatic_hunter",
                    "blazeandcave:weaponry/the_mighty_hunter",
                    "minecraft:adventure/kill_all_mobs",
                    "minecraft:husbandry/bred_all_animals" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion.replace("_mob", "").replace("minecraft:", "")) {
                    case "bat" -> 0;
                    case "chicken" -> 0;
                    case "cod" -> 0;
                    case "cow" -> 0;
                    case "mooshroom" -> 0;
                    case "pig" -> 0;
                    case "rabbit" -> 0;
                    case "salmon" -> 0;
                    case "sheep" -> 0;
                    case "squid" -> 0;
                    case "tropical_fish" -> 0;
                    case "turtle" -> 0;
                    case "villager" -> 0;
                    case "wandering_trader" -> 0;
                    case "pufferfish" -> 0;
                    case "cave_spider" -> 0;
                    case "enderman" -> 0;
                    case "panda" -> 0;
                    case "polar_bear" -> 0;
                    case "spider" -> 0;
                    case "zombified_piglin" -> 0;
                    case "blaze" -> 0;
                    case "creeper" -> 0;
                    case "drowned" -> 0;
                    case "endermite" -> 0;
                    case "evoker" -> 0;
                    case "ghast" -> 0;
                    case "guardian" -> 0;
                    case "elder_guardian" -> 0;
                    case "husk" -> 0;
                    case "magma_cube" -> 0;
                    case "phantom" -> 0;
                    case "pillager" -> 0;
                    case "ravager" -> 0;
                    case "shulker" -> 0;
                    case "silverfish" -> 0;
                    case "skeleton" -> 0;
                    case "slime" -> 0;
                    case "stray" -> 0;
                    case "vex" -> 0;
                    case "vindicator" -> 0;
                    case "witch" -> 0;
                    case "wither_skeleton" -> 0;
                    case "zombie_villager" -> 0;
                    case "zombie" -> 0;
                    case "cat" -> 0;
                    case "donkey" -> 0;
                    case "horse" -> 0;
                    case "llama" -> 0;
                    case "mule" -> 0;
                    case "parrot" -> 0;
                    case "skeleton_horse" -> 0;
                    case "trader_llama" -> 0;
                    case "wolf" -> 0;
                    case "dolphin" -> 0;
                    case "fox" -> 0;
                    case "ocelot" -> 19;
                    case "iron_golem" -> 0;
                    case "snow_golem" -> 0;
                    case "wither" -> 0;
                    case "ender_dragon" -> 0;
                    case "bee" -> 0;
                    case "piglin" -> 0;
                    case "hoglin" -> 0;
                    case "strider" -> 0;
                    case "zoglin" -> 0;
                    case "piglin_brute" -> 0;
                    case "axolotl" -> 0;
                    case "glow_squid" -> 0;
                    case "goat" -> 0;
                    case "frog" -> 0;
                    case "tadpole" -> 0;
                    case "allay" -> 0;
                    case "warden" -> 0;
                    case "camel" -> 0;
                    case "sniffer" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:animal/birdkeeper" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "red" -> 0;
                    case "blue" -> 0;
                    case "green" -> 0;
                    case "cyan" -> 0;
                    case "gray" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:animal/bunny_lover" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "brown" -> 0;
                    case "white" -> 0;
                    case "green" -> 0;
                    case "cyan" -> 0;
                    case "gray" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:animal/llama_llama_duck_king" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "creamy" -> 0;
                    case "white" -> 0;
                    case "brown" -> 0;
                    case "gray" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:animal/master_farrier" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "white_none" -> 0;
                    case "creamy_none" -> 0;
                    case "chestnut_none" -> 0;
                    case "brown_none" -> 0;
                    case "black_none" -> 0;
                    case "gray_none" -> 0;
                    case "dark_brown_none" -> 0;
                    case "white_white" -> 0;
                    case "creamy_white" -> 0;
                    case "chestnut_white" -> 0;
                    case "brown_white" -> 0;
                    case "black_white" -> 0;
                    case "gray_white" -> 0;
                    case "dark_brown_white" -> 0;
                    case "white_white_field" -> 0;
                    case "creamy_white_field" -> 0;
                    case "chestnut_white_field" -> 0;
                    case "brown_white_field" -> 0;
                    case "black_white_field" -> 0;
                    case "gray_white_field" -> 0;
                    case "dark_brown_white_field" -> 0;
                    case "white_white_dots" -> 0;
                    case "creamy_white_dots" -> 0;
                    case "chestnut_white_dots" -> 0;
                    case "brown_white_dots" -> 0;
                    case "black_white_dots" -> 0;
                    case "gray_white_dots" -> 0;
                    case "dark_brown_white_dots" -> 0;
                    case "white_black_dots" -> 0;
                    case "creamy_black_dots" -> 0;
                    case "chestnut_black_dots" -> 0;
                    case "brown_black_dots" -> 0;
                    case "black_black_dots" -> 0;
                    case "gray_black_dots" -> 0;
                    case "dark_brown_black_dots" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:biomes/pandamonium" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "normal" -> 12;
                    case "aggressive" -> 13;
                    case "lazy" -> 14;
                    case "worried" -> 15;
                    case "playful" -> 16;
                    case "weak" -> 17;
                    case "brown" -> 18;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "minecraft:husbandry/complete_catalogue" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "minecraft:black" -> 1;
                    case "minecraft:tabby" -> 2;
                    case "minecraft:ragdoll" -> 3;
                    case "minecraft:british_shorthair" -> 4;
                    case "minecraft:white" -> 5;
                    case "minecraft:persian" -> 6;
                    case "minecraft:calico" -> 7;
                    case "minecraft:siamese" -> 8;
                    case "minecraft:all_black" -> 9;
                    case "minecraft:jellie" -> 10;
                    case "minecraft:red" -> 11;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "minecraft:husbandry/leash_all_frog_variants" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "minecraft:temperate" -> 0;
                    case "minecraft:warm" -> 0;
                    case "minecraft:cold" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:adventure/businessman",
                    "blazeandcave:adventure/master_trader",
                    "blazeandcave:adventure/traveller",
                    "blazeandcave:adventure/you_are_the_pillager",
                    "blazeandcave:challenges/global_vaccination",
                    "blazeandcave:challenges/stockbroker",
                    "blazeandcave:potion/mad_scientist" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "farmer", "farmer_plains" -> 0;
                    case "fisherman", "fisherman_plains" -> 0;
                    case "shepherd", "shepherd_plains" -> 0;
                    case "fletcher", "fletcher_plains" -> 0;
                    case "librarian", "librarian_plains" -> 0;
                    case "cartographer", "cartographer_plains" -> 0;
                    case "cleric", "cleric_plains" -> 0;
                    case "armorer", "armorer_plains" -> 0;
                    case "weapon_smith", "weapon_smith_plains" -> 0;
                    case "tool_smith", "tool_smith_plains" -> 0;
                    case "butcher", "butcher_plains" -> 0;
                    case "leatherworker", "leatherworker_plains" -> 0;
                    case "mason", "mason_plains" -> 0;
                    case "nitwit", "nitwit_plains" -> 0;
                    case "none", "unemployed", "plains", "none_plains" -> 0;
                    case "farmer_taiga" -> 0;
                    case "fisherman_taiga" -> 0;
                    case "shepherd_taiga" -> 0;
                    case "fletcher_taiga" -> 0;
                    case "librarian_taiga" -> 0;
                    case "cartographer_taiga" -> 0;
                    case "cleric_taiga" -> 0;
                    case "armorer_taiga" -> 0;
                    case "weapon_smith_taiga" -> 0;
                    case "tool_smith_taiga" -> 0;
                    case "butcher_taiga" -> 0;
                    case "leatherworker_taiga" -> 0;
                    case "mason_taiga" -> 0;
                    case "nitwit_taiga" -> 0;
                    case "taiga", "none_taiga" -> 0;
                    case "farmer_desert" -> 0;
                    case "fisherman_desert" -> 0;
                    case "shepherd_desert" -> 0;
                    case "fletcher_desert" -> 0;
                    case "librarian_desert" -> 0;
                    case "cartographer_desert" -> 0;
                    case "cleric_desert" -> 0;
                    case "armorer_desert" -> 0;
                    case "weapon_smith_desert" -> 0;
                    case "tool_smith_desert" -> 0;
                    case "butcher_desert" -> 0;
                    case "leatherworker_desert" -> 0;
                    case "mason_desert" -> 0;
                    case "nitwit_desert" -> 0;
                    case "desert", "none_desert" -> 0;
                    case "farmer_savanna" -> 0;
                    case "fisherman_savanna" -> 0;
                    case "shepherd_savanna" -> 0;
                    case "fletcher_savanna" -> 0;
                    case "librarian_savanna" -> 0;
                    case "cartographer_savanna" -> 0;
                    case "cleric_savanna" -> 0;
                    case "armorer_savanna" -> 0;
                    case "weapon_smith_savanna" -> 0;
                    case "tool_smith_savanna" -> 0;
                    case "butcher_savanna" -> 0;
                    case "leatherworker_savanna" -> 0;
                    case "mason_savanna" -> 0;
                    case "nitwit_savanna" -> 0;
                    case "savanna", "none_savanna" -> 0;
                    case "farmer_snow" -> 0;
                    case "fisherman_snow" -> 0;
                    case "shepherd_snow" -> 0;
                    case "fletcher_snow" -> 0;
                    case "librarian_snow" -> 0;
                    case "cartographer_snow" -> 0;
                    case "cleric_snow" -> 0;
                    case "armorer_snow" -> 0;
                    case "weapon_smith_snow" -> 0;
                    case "tool_smith_snow" -> 0;
                    case "butcher_snow" -> 0;
                    case "leatherworker_snow" -> 0;
                    case "mason_snow" -> 0;
                    case "nitwit_snow" -> 0;
                    case "snow", "none_snow" -> 0;
                    case "farmer_jungle" -> 0;
                    case "fisherman_jungle" -> 0;
                    case "shepherd_jungle" -> 0;
                    case "fletcher_jungle" -> 0;
                    case "librarian_jungle" -> 0;
                    case "cartographer_jungle" -> 0;
                    case "cleric_jungle" -> 0;
                    case "armorer_jungle" -> 0;
                    case "weapon_smith_jungle" -> 0;
                    case "tool_smith_jungle" -> 0;
                    case "butcher_jungle" -> 0;
                    case "leatherworker_jungle" -> 0;
                    case "mason_jungle" -> 0;
                    case "nitwit_jungle" -> 0;
                    case "none_jungle" -> 0;
                    case "farmer_swamp" -> 0;
                    case "fisherman_swamp" -> 0;
                    case "shepherd_swamp" -> 0;
                    case "fletcher_swamp" -> 0;
                    case "librarian_swamp" -> 0;
                    case "cartographer_swamp" -> 0;
                    case "cleric_swamp" -> 0;
                    case "armorer_swamp" -> 0;
                    case "weapon_smith_swamp" -> 0;
                    case "tool_smith_swamp" -> 0;
                    case "butcher_swamp" -> 0;
                    case "leatherworker_swamp" -> 0;
                    case "mason_swamp" -> 0;
                    case "nitwit_swamp" -> 0;
                    case "none_swamp" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:challenges/ultimate_enchanter",
                    "blazeandcave:enchanting/armor_for_the_masses",
                    "blazeandcave:enchanting/complete_enchanter",
                    "blazeandcave:enchanting/curses",
                    "blazeandcave:enchanting/fiery",
                    "blazeandcave:enchanting/knocking_your_socks_off",
                    "blazeandcave:enchanting/master_enchanter",
                    "blazeandcave:enchanting/scuba_gear" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "protection" -> 0;
                    case "protection1" -> 0;
                    case "protection2" -> 0;
                    case "protection3" -> 0;
                    case "protection4" -> 0;
                    case "fire_protection" -> 0;
                    case "fire_protection1" -> 0;
                    case "fire_protection2" -> 0;
                    case "fire_protection3" -> 0;
                    case "fire_protection4" -> 0;
                    case "feather_falling" -> 0;
                    case "feather_falling1" -> 0;
                    case "feather_falling2" -> 0;
                    case "feather_falling3" -> 0;
                    case "feather_falling4" -> 0;
                    case "blast_protection" -> 0;
                    case "blast_protection1" -> 0;
                    case "blast_protection2" -> 0;
                    case "blast_protection3" -> 0;
                    case "blast_protection4" -> 0;
                    case "projectile_protection" -> 0;
                    case "projectile_protection1" -> 0;
                    case "projectile_protection2" -> 0;
                    case "projectile_protection3" -> 0;
                    case "projectile_protection4" -> 0;
                    case "respiration" -> 0;
                    case "respiration1" -> 0;
                    case "respiration2" -> 0;
                    case "respiration3" -> 0;
                    case "aqua_affinity" -> 0;
                    case "thorns" -> 0;
                    case "thorns1" -> 0;
                    case "thorns2" -> 0;
                    case "thorns3" -> 0;
                    case "depth_strider" -> 0;
                    case "depth_strider1" -> 0;
                    case "depth_strider2" -> 0;
                    case "depth_strider3" -> 0;
                    case "frost_walker" -> 0;
                    case "frost_walker1" -> 0;
                    case "frost_walker2" -> 0;
                    case "binding_curse" -> 0;
                    case "sharpness" -> 0;
                    case "sharpness1" -> 0;
                    case "sharpness2" -> 0;
                    case "sharpness3" -> 0;
                    case "sharpness4" -> 0;
                    case "sharpness5" -> 0;
                    case "smite" -> 0;
                    case "smite1" -> 0;
                    case "smite2" -> 0;
                    case "smite3" -> 0;
                    case "smite4" -> 0;
                    case "smite5" -> 0;
                    case "bane_of_arthropods" -> 0;
                    case "bane_of_arthropods1" -> 0;
                    case "bane_of_arthropods2" -> 0;
                    case "bane_of_arthropods3" -> 0;
                    case "bane_of_arthropods4" -> 0;
                    case "bane_of_arthropods5" -> 0;
                    case "knockback" -> 0;
                    case "knockback1" -> 0;
                    case "knockback2" -> 0;
                    case "fire_aspect" -> 0;
                    case "fire_aspect1" -> 0;
                    case "fire_aspect2" -> 0;
                    case "looting" -> 0;
                    case "looting1" -> 0;
                    case "looting2" -> 0;
                    case "looting3" -> 0;
                    case "sweeping" -> 0;
                    case "sweeping1" -> 0;
                    case "sweeping2" -> 0;
                    case "sweeping3" -> 0;
                    case "efficiency" -> 0;
                    case "efficiency1" -> 0;
                    case "efficiency2" -> 0;
                    case "efficiency3" -> 0;
                    case "efficiency4" -> 0;
                    case "efficiency5" -> 0;
                    case "silk_touch" -> 0;
                    case "unbreaking" -> 0;
                    case "unbreaking1" -> 0;
                    case "unbreaking2" -> 0;
                    case "unbreaking3" -> 0;
                    case "fortune" -> 0;
                    case "fortune1" -> 0;
                    case "fortune2" -> 0;
                    case "fortune3" -> 0;
                    case "power" -> 0;
                    case "power1" -> 0;
                    case "power2" -> 0;
                    case "power3" -> 0;
                    case "power4" -> 0;
                    case "power5" -> 0;
                    case "punch" -> 0;
                    case "punch1" -> 0;
                    case "punch2" -> 0;
                    case "flame" -> 0;
                    case "infinity" -> 0;
                    case "luck_of_the_sea" -> 0;
                    case "luck_of_the_sea1" -> 0;
                    case "luck_of_the_sea2" -> 0;
                    case "luck_of_the_sea3" -> 0;
                    case "lure" -> 0;
                    case "lure1" -> 0;
                    case "lure2" -> 0;
                    case "lure3" -> 0;
                    case "mending" -> 0;
                    case "vanishing_curse" -> 0;
                    case "loyalty" -> 0;
                    case "loyalty1" -> 0;
                    case "loyalty2" -> 0;
                    case "loyalty3" -> 0;
                    case "riptide" -> 0;
                    case "riptide1" -> 0;
                    case "riptide2" -> 0;
                    case "riptide3" -> 0;
                    case "impaling" -> 0;
                    case "impaling1" -> 0;
                    case "impaling2" -> 0;
                    case "impaling3" -> 0;
                    case "impaling4" -> 0;
                    case "impaling5" -> 0;
                    case "channeling" -> 0;
                    case "quick_charge" -> 0;
                    case "quick_charge1" -> 0;
                    case "quick_charge2" -> 0;
                    case "quick_charge3" -> 0;
                    case "piercing" -> 0;
                    case "piercing1" -> 0;
                    case "piercing2" -> 0;
                    case "piercing3" -> 0;
                    case "piercing4" -> 0;
                    case "multishot" -> 0;
                    case "soul_speed" -> 0;
                    case "soul_speed1" -> 0;
                    case "soul_speed2" -> 0;
                    case "soul_speed3" -> 0;
                    case "swift_sneak" -> 0;
                    case "swift_sneak1" -> 0;
                    case "swift_sneak2" -> 0;
                    case "swift_sneak3" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:potion/a_much_more_doable_challenge",
                    "blazeandcave:potion/gas_bomb" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "speed" -> 0;
                    case "slowness" -> 0;
                    case "strength" -> 0;
                    case "jump_boost" -> 0;
                    case "regeneration" -> 0;
                    case "fire_resistance" -> 0;
                    case "water_breathing" -> 0;
                    case "invisibility" -> 0;
                    case "night_vision" -> 0;
                    case "weakness" -> 0;
                    case "poison" -> 0;
                    case "wither" -> 0;
                    case "haste" -> 0;
                    case "mining_fatigue" -> 0;
                    case "levitation" -> 0;
                    case "glowing" -> 0;
                    case "absorption" -> 0;
                    case "hunger" -> 0;
                    case "nausea" -> 0;
                    case "resistance" -> 0;
                    case "slow_falling" -> 0;
                    case "conduit_power" -> 0;
                    case "dolphins_grace" -> 0;
                    case "blindness" -> 0;
                    case "saturation" -> 0;
                    case "bad_omen" -> 0;
                    case "hero_of_the_village" -> 0;
                    case "darkness" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:biomes/cold_feet",
                    "blazeandcave:biomes/high_feet",
                    "blazeandcave:biomes/one_with_the_forest",
                    "blazeandcave:biomes/overgrown",
                    "blazeandcave:biomes/warm_feet",
                    "blazeandcave:biomes/wet_feet",
                    "blazeandcave:challenges/explorer_of_worlds",
                    "blazeandcave:end/void_walker",
                    "blazeandcave:mining/spelunker",
                    "blazeandcave:redstone/travelling_bard",
                    "minecraft:adventure/adventuring_time",
                    "minecraft:nether/explore_nether" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "river" -> 0;
                    case "swamp" -> 0;
                    case "desert" -> 0;
                    case "snowy_taiga" -> 0;
                    case "badlands" -> 0;
                    case "forest" -> 0;
                    case "stony_shore" -> 0;
                    case "snowy_plains" -> 0;
                    case "wooded_badlands" -> 0;
                    case "savanna" -> 0;
                    case "plains" -> 0;
                    case "frozen_river" -> 0;
                    case "old_growth_pine_taiga" -> 0;
                    case "snowy_beach" -> 0;
                    case "deep_ocean" -> 0;
                    case "sparse_jungle" -> 0;
                    case "ocean" -> 0;
                    case "windswept_hills" -> 0;
                    case "jungle" -> 0;
                    case "beach" -> 0;
                    case "savanna_plateau" -> 0;
                    case "dark_forest" -> 0;
                    case "taiga" -> 0;
                    case "birch_forest" -> 0;
                    case "mushroom_fields" -> 0;
                    case "windswept_forest" -> 0;
                    case "cold_ocean" -> 0;
                    case "warm_ocean" -> 0;
                    case "lukewarm_ocean" -> 0;
                    case "frozen_ocean" -> 0;
                    case "deep_frozen_ocean" -> 0;
                    case "deep_lukewarm_ocean" -> 0;
                    case "deep_cold_ocean" -> 0;
                    case "bamboo_jungle" -> 0;
                    case "sunflower_plains" -> 0;
                    case "windswept_gravelly_hills" -> 0;
                    case "flower_forest" -> 0;
                    case "ice_spikes" -> 0;
                    case "old_growth_birch_forest" -> 0;
                    case "old_growth_taiga", "old_growth_spruce_taiga" -> 0;
                    case "windswept_savanna" -> 0;
                    case "eroded_badlands" -> 0;
                    case "meadow" -> 0;
                    case "snowy_slopes_or_grove", "grove" -> 0;
                    case "snowy_slopes" -> 0;
                    case "peaks", "frozen_peaks" -> 0;
                    case "jagged_peaks" -> 0;
                    case "stony_peaks" -> 0;
                    case "lush_caves" -> 0;
                    case "dripstone_caves" -> 0;
                    case "nether_wastes" -> 0;
                    case "crimson_forest" -> 0;
                    case "warped_forest" -> 0;
                    case "soul_sand_valley" -> 0;
                    case "basalt_deltas" -> 0;
                    case "the_end" -> 0;
                    case "small_end_islands" -> 0;
                    case "end_highlands" -> 0;
                    case "end_midlands" -> 0;
                    case "end_barrens" -> 0;
                    case "mangrove_swamp" -> 0;
                    case "deep_dark" -> 0;
                    case "cherry_grove" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:adventure/raidin_master" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "dungeon" -> 0;
                    case "igloo" -> 0;
                    case "desert_pyramid" -> 0;
                    case "jungle_pyramid" -> 0;
                    case "swamp_hut" -> 0;
                    case "village_desert" -> 0;
                    case "village_plains" -> 0;
                    case "village_savanna" -> 0;
                    case "village_snowy" -> 0;
                    case "village_taiga" -> 0;
                    case "mineshaft" -> 0;
                    case "mineshaft_mesa" -> 0;
                    case "stronghold" -> 0;
                    case "fortress" -> 0;
                    case "end_city" -> 0;
                    case "monument" -> 0;
                    case "mansion" -> 0;
                    case "ocean_ruin_cold" -> 0;
                    case "ocean_ruin_warm" -> 0;
                    case "shipwreck" -> 0;
                    case "buried_treasure" -> 0;
                    case "pillager_outpost" -> 0;
                    case "ruined_portal" -> 0;
                    case "bastion_remnant" -> 0;
                    case "ancient_city" -> 0;
                    case "trail_ruins" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:challenges/i_am_loot" -> {
                hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "abandoned_mineshaft" -> 0;
                    case "bastion_bridge" -> 0;
                    case "bastion_hoglin_stable" -> 0;
                    case "bastion_other" -> 0;
                    case "bastion_treasure" -> 0;
                    case "buried_treasure" -> 0;
                    case "desert_pyramid" -> 0;
                    case "end_city_treasure" -> 0;
                    case "igloo_chest" -> 0;
                    case "jungle_temple" -> 0;
                    case "jungle_temple_dispenser" -> 0;
                    case "nether_bridge" -> 0;
                    case "pillager_outpost" -> 0;
                    case "ruined_portal" -> 0;
                    case "shipwreck_map" -> 0;
                    case "shipwreck_supply" -> 0;
                    case "shipwreck_treasure" -> 0;
                    case "simple_dungeon" -> 0;
                    case "stronghold_corridor" -> 0;
                    case "stronghold_crossing" -> 0;
                    case "stronghold_library" -> 0;
                    case "underwater_ruin_big" -> 0;
                    case "underwater_ruin_small" -> 0;
                    case "woodland_mansion" -> 0;
                    case "village_armorer" -> 0;
                    case "village_butcher" -> 0;
                    case "village_cartographer" -> 0;
                    case "village_desert_house" -> 0;
                    case "village_fisher" -> 0;
                    case "village_fletcher" -> 0;
                    case "village_mason" -> 0;
                    case "village_plains_house" -> 0;
                    case "village_savanna_house" -> 0;
                    case "village_snowy_house" -> 0;
                    case "village_taiga_house" -> 0;
                    case "village_tannery" -> 0;
                    case "village_temple" -> 0;
                    case "village_toolsmith" -> 0;
                    case "village_weaponsmith" -> 0;
                    case "ancient_city" -> 0;
                    case "ancient_city_ice_box" -> 0;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
        }
        if (hintItem == null) {
            hintItem = Registries.ITEM.get(new Identifier(criterion));
        }
        if (hintItem == Items.AIR) {
            throw new IllegalStateException("Unexpected value: " + criterion);
        }
        ItemStack hint = new ItemStack(hintItem);
        hint.setNbt(nbt);
        return new AdvancementHint(tabDisplay.getIcon(), advancementDisplay.getIcon(), hint);
    }

    private static Advancement generateRandomAdvancement(boolean withSingleRequirement) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return null;
        }
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return null;
        }
        ServerPlayerEntity serverPlayer = server.getPlayerManager().getPlayerList().get(0);
        if (serverPlayer == null) {
            return null;
        }
        ArrayList<Advancement> advancements = new ArrayList<>();
        for (Advancement advancement : player.networkHandler.getAdvancementHandler().getManager().getAdvancements()) {
            Identifier identifier = advancement.getId();
            String namespace = identifier.getNamespace();
            String tab = identifier.getPath().split("/")[0];
            String name = identifier.getPath().split("/")[1];
            AdvancementDisplay display = advancement.getDisplay();
            int requirementsCount = advancement.getRequirementCount();
            if (display == null || display.isHidden()) {
                continue;
            }
            if (namespace.equals(AchieveToDoMod.ID)) {
                continue;
            }
            if (name.equals("root")) {
                continue;
            }
            if (tab.equals(AdvancementRoot.STATISTICS.name().toLowerCase())) {
                continue;
            }
            if (withSingleRequirement) {
                if (requirementsCount > 1) {
                    continue;
                }
            }
            if (!withSingleRequirement) {
                if (requirementsCount == 1) {
                    continue;
                }
                if (tab.equals(AdvancementRoot.BACAP.name().toLowerCase())) {
                    continue;
                }
                String id = identifier.toString();
                if (id.equals("blazeandcave:challenges/the_perfect_run") || id.equals("blazeandcave:challenges/were_in_the_endgame_now") || id.equals("blazeandcave:nether/this_ones_mine") || id.equals("blazeandcave:redstone/take_notes") || id.equals("blazeandcave:building/art_gallery")) {
                    continue;
                }
            }
            AdvancementProgress progress = serverPlayer.getAdvancementTracker().getProgress(advancement);
            if (progress != null && progress.isDone()) {
                continue;
            }
            advancements.add(advancement);
        }
        if (advancements.isEmpty()) {
            return null;
        }
        return advancements.get(player.getRandom().nextBetween(0, advancements.size() - 1));
    }
}
