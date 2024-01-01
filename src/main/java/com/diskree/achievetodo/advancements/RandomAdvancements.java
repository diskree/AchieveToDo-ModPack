package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.client.SpyglassPanoramaDetails;
import net.minecraft.advancement.*;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RandomAdvancements {

    private static final String BACAP_NAMESPACE = "blazeandcave";
    private static final List<String> BLACK_LIST = List.of(
            "challenges/the_perfect_run",
            "challenges/were_in_the_endgame_now",
            "nether/this_ones_mine",
            "redstone/take_notes"
    );

    @Nullable
    public static PlacedAdvancement getAdvancement(ServerPlayerEntity player) {
        return getAdvancement(player, true);
    }

    @SuppressWarnings("DataFlowIssue")
    @Nullable
    public static AdvancementHint getHint(ServerPlayerEntity player) {
        PlacedAdvancement placedAdvancement = getAdvancement(player, false);
        if (placedAdvancement == null) {
            return null;
        }
        PlacedAdvancement root = PlacedAdvancement.findRoot(placedAdvancement);
        AdvancementDisplay advancementDisplay = placedAdvancement.getAdvancement().display().orElse(null);
        ArrayList<String> incompleteCriteria = new ArrayList<>();
        AdvancementProgress progress = player.getAdvancementTracker().getProgress(placedAdvancement.getAdvancementEntry());
        for (List<String> requirement : placedAdvancement.getAdvancement().requirements().requirements()) {
            boolean isRequirementCompleted = false;
            for (String criterion : requirement) {
                CriterionProgress criterionProgress = progress.getCriterionProgress(criterion);
                if (criterionProgress != null && criterionProgress.isObtained()) {
                    isRequirementCompleted = true;
                    break;
                }
            }
            if (!isRequirementCompleted && !requirement.isEmpty()) {
                incompleteCriteria.add(requirement.get(0));
            }
        }
        if (incompleteCriteria.isEmpty()) {
            return null;
        }
        String criterion = incompleteCriteria.get(player.getRandom().nextInt(incompleteCriteria.size()));
        Item hintItem = null;
        NbtCompound nbt = new NbtCompound();
        boolean dropHint = false;

        switch (placedAdvancement.getAdvancementEntry().id().toString()) {
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
                hintItem = Items.GOAT_HORN;
                nbt.putString("instrument", new Identifier(criterion + "_goat_horn").toString());
            }
            case "blazeandcave:animal/colorful_cavalry" -> {
                hintItem = Items.LEATHER_HORSE_ARMOR;
                nbt.put("display", getColorNbtByCriterion(criterion));
            }
            case "blazeandcave:animal/shoe_shed" -> {
                hintItem = Items.LEATHER_BOOTS;
                nbt.put("display", getColorNbtByCriterion(criterion));
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
            case "blazeandcave:building/art_gallery" -> {
                hintItem = Items.PAINTING;
                NbtCompound variantNbt = new NbtCompound();
                variantNbt.putString(PaintingEntity.VARIANT_NBT_KEY, new Identifier(criterion).toString());
                nbt.put(EntityType.ENTITY_TAG_KEY, variantNbt);
            }
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
                if (incompleteCriteria.contains("first_line")) {
                    hintItem = Items.ENCHANTED_BOOK;
                } else if (incompleteCriteria.contains("second_line")) {
                    hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                    nbt.putInt("Damage", 60);
                } else if (incompleteCriteria.contains("third_line")) {
                    hintItem = Items.NAME_TAG;
                } else if (incompleteCriteria.contains("fourth_line")) {
                    hintItem = Items.SHEARS;
                } else if (incompleteCriteria.contains("fifth_line")) {
                    hintItem = Items.SPECTRAL_ARROW;
                } else if (incompleteCriteria.contains("sixth_line")) {
                    hintItem = Items.EGG;
                } else if (incompleteCriteria.contains("seventh_line")) {
                    hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                    nbt.putInt("Damage", 53);
                } else if (incompleteCriteria.contains("eighth_line")) {
                    hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                    nbt.putInt("Damage", 92);
                } else if (incompleteCriteria.contains("ninth_line")) {
                    hintItem = Items.OAK_BOAT;
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
                    hintItem = Items.FLINT_AND_STEEL;
                }
            }
            case "blazeandcave:potion/failed_concoctions" -> {
                hintItem = Items.POTION;
                nbt.putString("Potion", new Identifier(criterion.replace("_potion", "")).toString());
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
                criterion = criterion.replace("_mob", "").replace("minecraft:", "");
                if (criterion.equals("cod") || criterion.equals("salmon") || criterion.equals("tropical_fish") || criterion.equals("pufferfish") || criterion.equals("axolotl") || criterion.equals("tadpole")) {
                    criterion += "_bucket";
                } else {
                    hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                    nbt.putInt("Damage", switch (criterion) {
                        case "bat" -> 39;
                        case "chicken" -> 40;
                        case "cow" -> 41;
                        case "mooshroom" -> 42;
                        case "pig" -> 38;
                        case "rabbit" -> 28;
                        case "sheep" -> 43;
                        case "squid" -> 44;
                        case "turtle" -> 46;
                        case "villager" -> 150;
                        case "wandering_trader" -> 48;
                        case "cave_spider" -> 50;
                        case "enderman" -> 51;
                        case "panda" -> 12;
                        case "polar_bear" -> 52;
                        case "spider" -> 49;
                        case "zombified_piglin" -> 55;
                        case "blaze" -> 56;
                        case "creeper" -> 57;
                        case "drowned" -> 60;
                        case "endermite" -> 61;
                        case "evoker" -> 64;
                        case "ghast" -> 65;
                        case "guardian" -> 66;
                        case "elder_guardian" -> 67;
                        case "husk" -> 59;
                        case "magma_cube" -> 69;
                        case "phantom" -> 70;
                        case "pillager" -> 62;
                        case "ravager" -> 71;
                        case "shulker" -> 72;
                        case "silverfish" -> 73;
                        case "skeleton" -> 74;
                        case "slime" -> 68;
                        case "stray" -> 75;
                        case "vex" -> 78;
                        case "vindicator" -> 63;
                        case "witch" -> 79;
                        case "wither_skeleton" -> 76;
                        case "zombie_villager" -> 80;
                        case "zombie" -> 58;
                        case "cat" -> 11;
                        case "donkey" -> 81;
                        case "horse" -> 82;
                        case "llama" -> 23;
                        case "mule" -> 84;
                        case "parrot" -> 33;
                        case "skeleton_horse" -> 85;
                        case "trader_llama" -> 83;
                        case "wolf" -> 86;
                        case "dolphin" -> 87;
                        case "fox" -> 88;
                        case "ocelot" -> 19;
                        case "iron_golem" -> 90;
                        case "snow_golem" -> 91;
                        case "wither" -> 92;
                        case "ender_dragon" -> 93;
                        case "bee" -> 94;
                        case "piglin" -> 53;
                        case "hoglin" -> 95;
                        case "strider" -> 97;
                        case "zoglin" -> 96;
                        case "piglin_brute" -> 54;
                        case "glow_squid" -> 45;
                        case "goat" -> 98;
                        case "frog" -> 21;
                        case "allay" -> 77;
                        case "warden" -> 99;
                        case "camel" -> 100;
                        case "sniffer" -> 101;
                        default -> throw new IllegalStateException("Unexpected value: " + criterion);
                    });
                }
            }
            case "blazeandcave:animal/birdkeeper" -> {
                hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "red" -> 33;
                    case "blue" -> 34;
                    case "green" -> 35;
                    case "cyan" -> 36;
                    case "gray" -> 37;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:animal/bunny_lover" -> {
                hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "brown" -> 28;
                    case "white" -> 31;
                    case "black" -> 27;
                    case "black_and_white" -> 32;
                    case "gold" -> 29;
                    case "salt_and_pepper" -> 30;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:animal/llama_llama_duck_king" -> {
                hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "creamy" -> 24;
                    case "white" -> 26;
                    case "brown" -> 23;
                    case "gray" -> 25;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:animal/master_farrier" -> {
                hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "white_none" -> 82;
                    case "creamy_none" -> 102;
                    case "chestnut_none" -> 103;
                    case "brown_none" -> 104;
                    case "black_none" -> 105;
                    case "gray_none" -> 106;
                    case "dark_brown_none" -> 107;
                    case "white_white" -> 108;
                    case "creamy_white" -> 109;
                    case "chestnut_white" -> 110;
                    case "brown_white" -> 111;
                    case "black_white" -> 112;
                    case "gray_white" -> 113;
                    case "dark_brown_white" -> 114;
                    case "white_white_field" -> 115;
                    case "creamy_white_field" -> 116;
                    case "chestnut_white_field" -> 117;
                    case "brown_white_field" -> 118;
                    case "black_white_field" -> 119;
                    case "gray_white_field" -> 120;
                    case "dark_brown_white_field" -> 121;
                    case "white_white_dots" -> 122;
                    case "creamy_white_dots" -> 123;
                    case "chestnut_white_dots" -> 124;
                    case "brown_white_dots" -> 125;
                    case "black_white_dots" -> 126;
                    case "gray_white_dots" -> 127;
                    case "dark_brown_white_dots" -> 128;
                    case "white_black_dots" -> 129;
                    case "creamy_black_dots" -> 130;
                    case "chestnut_black_dots" -> 131;
                    case "brown_black_dots" -> 132;
                    case "black_black_dots" -> 133;
                    case "gray_black_dots" -> 134;
                    case "dark_brown_black_dots" -> 135;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:biomes/pandamonium" -> {
                hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
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
                hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
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
                hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "minecraft:temperate" -> 21;
                    case "minecraft:warm" -> 22;
                    case "minecraft:cold" -> 20;
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
                hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "farmer", "farmer_plains" -> 136;
                    case "fisherman", "fisherman_plains" -> 137;
                    case "shepherd", "shepherd_plains" -> 138;
                    case "fletcher", "fletcher_plains" -> 139;
                    case "librarian", "librarian_plains" -> 140;
                    case "cartographer", "cartographer_plains" -> 141;
                    case "cleric", "cleric_plains" -> 142;
                    case "armorer", "armorer_plains" -> 143;
                    case "weapon_smith", "weapon_smith_plains" -> 144;
                    case "tool_smith", "tool_smith_plains" -> 145;
                    case "butcher", "butcher_plains" -> 146;
                    case "leatherworker", "leatherworker_plains" -> 147;
                    case "mason", "mason_plains" -> 148;
                    case "nitwit", "nitwit_plains" -> 149;
                    case "none", "unemployed", "plains", "none_plains" -> 150;
                    case "farmer_taiga" -> 151;
                    case "fisherman_taiga" -> 152;
                    case "shepherd_taiga" -> 153;
                    case "fletcher_taiga" -> 154;
                    case "librarian_taiga" -> 155;
                    case "cartographer_taiga" -> 156;
                    case "cleric_taiga" -> 157;
                    case "armorer_taiga" -> 158;
                    case "weapon_smith_taiga" -> 159;
                    case "tool_smith_taiga" -> 160;
                    case "butcher_taiga" -> 161;
                    case "leatherworker_taiga" -> 162;
                    case "mason_taiga" -> 163;
                    case "nitwit_taiga" -> 164;
                    case "taiga", "none_taiga" -> 165;
                    case "farmer_desert" -> 166;
                    case "fisherman_desert" -> 167;
                    case "shepherd_desert" -> 168;
                    case "fletcher_desert" -> 169;
                    case "librarian_desert" -> 170;
                    case "cartographer_desert" -> 171;
                    case "cleric_desert" -> 172;
                    case "armorer_desert" -> 173;
                    case "weapon_smith_desert" -> 174;
                    case "tool_smith_desert" -> 175;
                    case "butcher_desert" -> 176;
                    case "leatherworker_desert" -> 177;
                    case "mason_desert" -> 178;
                    case "nitwit_desert" -> 179;
                    case "desert", "none_desert" -> 180;
                    case "farmer_savanna" -> 181;
                    case "fisherman_savanna" -> 182;
                    case "shepherd_savanna" -> 183;
                    case "fletcher_savanna" -> 184;
                    case "librarian_savanna" -> 185;
                    case "cartographer_savanna" -> 186;
                    case "cleric_savanna" -> 187;
                    case "armorer_savanna" -> 188;
                    case "weapon_smith_savanna" -> 189;
                    case "tool_smith_savanna" -> 190;
                    case "butcher_savanna" -> 191;
                    case "leatherworker_savanna" -> 192;
                    case "mason_savanna" -> 193;
                    case "nitwit_savanna" -> 194;
                    case "savanna", "none_savanna" -> 195;
                    case "farmer_snow" -> 196;
                    case "fisherman_snow" -> 197;
                    case "shepherd_snow" -> 198;
                    case "fletcher_snow" -> 199;
                    case "librarian_snow" -> 200;
                    case "cartographer_snow" -> 201;
                    case "cleric_snow" -> 202;
                    case "armorer_snow" -> 203;
                    case "weapon_smith_snow" -> 204;
                    case "tool_smith_snow" -> 205;
                    case "butcher_snow" -> 206;
                    case "leatherworker_snow" -> 207;
                    case "mason_snow" -> 208;
                    case "nitwit_snow" -> 209;
                    case "snow", "none_snow" -> 210;
                    case "farmer_jungle" -> 211;
                    case "fisherman_jungle" -> 212;
                    case "shepherd_jungle" -> 213;
                    case "fletcher_jungle" -> 214;
                    case "librarian_jungle" -> 215;
                    case "cartographer_jungle" -> 216;
                    case "cleric_jungle" -> 217;
                    case "armorer_jungle" -> 218;
                    case "weapon_smith_jungle" -> 219;
                    case "tool_smith_jungle" -> 220;
                    case "butcher_jungle" -> 221;
                    case "leatherworker_jungle" -> 222;
                    case "mason_jungle" -> 223;
                    case "nitwit_jungle" -> 224;
                    case "none_jungle" -> 225;
                    case "farmer_swamp" -> 226;
                    case "fisherman_swamp" -> 227;
                    case "shepherd_swamp" -> 228;
                    case "fletcher_swamp" -> 229;
                    case "librarian_swamp" -> 230;
                    case "cartographer_swamp" -> 231;
                    case "cleric_swamp" -> 232;
                    case "armorer_swamp" -> 233;
                    case "weapon_smith_swamp" -> 234;
                    case "tool_smith_swamp" -> 235;
                    case "butcher_swamp" -> 236;
                    case "leatherworker_swamp" -> 237;
                    case "mason_swamp" -> 238;
                    case "nitwit_swamp" -> 239;
                    case "none_swamp" -> 240;
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
                if (Character.isLetter(criterion.charAt(criterion.length() - 1))) {
                    hintItem = Items.ENCHANTED_BOOK;
                    NbtList nbtList = new NbtList();
                    nbtList.add(EnchantmentHelper.createNbt(new Identifier(criterion), 1));
                    nbt.put(EnchantedBookItem.STORED_ENCHANTMENTS_KEY, nbtList);
                } else {
                    hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                    nbt.putInt("Damage", switch (criterion) {
                        case "protection1" -> 269;
                        case "protection2" -> 270;
                        case "protection3" -> 271;
                        case "protection4" -> 272;
                        case "fire_protection1" -> 273;
                        case "fire_protection2" -> 274;
                        case "fire_protection3" -> 275;
                        case "fire_protection4" -> 276;
                        case "feather_falling1" -> 277;
                        case "feather_falling2" -> 278;
                        case "feather_falling3" -> 279;
                        case "feather_falling4" -> 280;
                        case "blast_protection1" -> 281;
                        case "blast_protection2" -> 282;
                        case "blast_protection3" -> 283;
                        case "blast_protection4" -> 284;
                        case "projectile_protection1" -> 285;
                        case "projectile_protection2" -> 286;
                        case "projectile_protection3" -> 287;
                        case "projectile_protection4" -> 288;
                        case "respiration1" -> 289;
                        case "respiration2" -> 290;
                        case "respiration3" -> 291;
                        case "thorns1" -> 292;
                        case "thorns2" -> 293;
                        case "thorns3" -> 294;
                        case "depth_strider1" -> 295;
                        case "depth_strider2" -> 296;
                        case "depth_strider3" -> 297;
                        case "frost_walker1" -> 298;
                        case "frost_walker2" -> 299;
                        case "sharpness1" -> 300;
                        case "sharpness2" -> 301;
                        case "sharpness3" -> 302;
                        case "sharpness4" -> 303;
                        case "sharpness5" -> 304;
                        case "smite1" -> 305;
                        case "smite2" -> 306;
                        case "smite3" -> 307;
                        case "smite4" -> 308;
                        case "smite5" -> 309;
                        case "bane_of_arthropods1" -> 310;
                        case "bane_of_arthropods2" -> 311;
                        case "bane_of_arthropods3" -> 312;
                        case "bane_of_arthropods4" -> 313;
                        case "bane_of_arthropods5" -> 314;
                        case "knockback1" -> 315;
                        case "knockback2" -> 316;
                        case "fire_aspect1" -> 317;
                        case "fire_aspect2" -> 318;
                        case "looting1" -> 319;
                        case "looting2" -> 320;
                        case "looting3" -> 321;
                        case "sweeping1" -> 322;
                        case "sweeping2" -> 323;
                        case "sweeping3" -> 324;
                        case "efficiency1" -> 325;
                        case "efficiency2" -> 326;
                        case "efficiency3" -> 327;
                        case "efficiency4" -> 328;
                        case "efficiency5" -> 329;
                        case "unbreaking1" -> 330;
                        case "unbreaking2" -> 331;
                        case "unbreaking3" -> 332;
                        case "fortune1" -> 333;
                        case "fortune2" -> 334;
                        case "fortune3" -> 335;
                        case "power1" -> 336;
                        case "power2" -> 337;
                        case "power3" -> 338;
                        case "power4" -> 339;
                        case "power5" -> 340;
                        case "punch1" -> 341;
                        case "punch2" -> 342;
                        case "luck_of_the_sea1" -> 343;
                        case "luck_of_the_sea2" -> 344;
                        case "luck_of_the_sea3" -> 345;
                        case "lure1" -> 346;
                        case "lure2" -> 347;
                        case "lure3" -> 348;
                        case "loyalty1" -> 349;
                        case "loyalty2" -> 350;
                        case "loyalty3" -> 351;
                        case "riptide1" -> 352;
                        case "riptide2" -> 353;
                        case "riptide3" -> 354;
                        case "impaling1" -> 355;
                        case "impaling2" -> 356;
                        case "impaling3" -> 357;
                        case "impaling4" -> 358;
                        case "impaling5" -> 359;
                        case "quick_charge1" -> 360;
                        case "quick_charge2" -> 361;
                        case "quick_charge3" -> 362;
                        case "piercing1" -> 363;
                        case "piercing2" -> 364;
                        case "piercing3" -> 365;
                        case "piercing4" -> 366;
                        case "soul_speed1" -> 367;
                        case "soul_speed2" -> 368;
                        case "soul_speed3" -> 369;
                        case "swift_sneak1" -> 370;
                        case "swift_sneak2" -> 371;
                        case "swift_sneak3" -> 372;
                        default -> throw new IllegalStateException("Unexpected value: " + criterion);
                    });
                }
            }
            case "blazeandcave:potion/a_much_more_doable_challenge",
                    "blazeandcave:potion/gas_bomb" -> {
                hintItem = AchieveToDo.ANCIENT_CITY_PORTAL_HINT_ITEM;
                nbt.putInt("Damage", switch (criterion) {
                    case "speed" -> 241;
                    case "slowness" -> 242;
                    case "strength" -> 243;
                    case "jump_boost" -> 244;
                    case "regeneration" -> 245;
                    case "fire_resistance" -> 246;
                    case "water_breathing" -> 247;
                    case "invisibility" -> 248;
                    case "night_vision" -> 249;
                    case "weakness" -> 250;
                    case "poison" -> 251;
                    case "wither" -> 252;
                    case "haste" -> 253;
                    case "mining_fatigue" -> 254;
                    case "levitation" -> 255;
                    case "glowing" -> 256;
                    case "absorption" -> 257;
                    case "hunger" -> 258;
                    case "nausea" -> 259;
                    case "resistance" -> 260;
                    case "slow_falling" -> 261;
                    case "conduit_power" -> 262;
                    case "dolphins_grace" -> 263;
                    case "blindness" -> 264;
                    case "saturation" -> 265;
                    case "bad_omen" -> 266;
                    case "hero_of_the_village" -> 267;
                    case "darkness" -> 268;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                });
            }
            case "blazeandcave:biomes/cold_feet",
                    "blazeandcave:biomes/high_feet",
                    "blazeandcave:biomes/one_with_the_forest",
                    "blazeandcave:biomes/overgrown",
                    "blazeandcave:biomes/warm_feet",
                    "blazeandcave:biomes/wet_feet",
                    "blazeandcave:biomes/a_cliffhanger",
                    "blazeandcave:biomes/a_grassy_nature",
                    "blazeandcave:biomes/bushranger",
                    "blazeandcave:biomes/highlander",
                    "blazeandcave:biomes/master_spelunker",
                    "blazeandcave:biomes/pretty_in_purple",
                    "blazeandcave:biomes/terralithic",
                    "blazeandcave:challenges/explorer_of_worlds",
                    "blazeandcave:end/void_walker",
                    "blazeandcave:mining/spelunker",
                    "blazeandcave:redstone/travelling_bard",
                    "minecraft:adventure/adventuring_time",
                    "minecraft:nether/explore_nether" -> {
                criterion = switch (criterion) {
                    case "old_growth_taiga" -> "old_growth_spruce_taiga";
                    case "snowy_slopes_or_grove" -> "grove";
                    case "peaks" -> "frozen_peaks";
                    default -> criterion;
                };
                hintItem = Items.SPYGLASS;
                nbt.putString(SpyglassPanoramaDetails.PANORAMA_TYPE, "biome");
                nbt.putString(SpyglassPanoramaDetails.PANORAMA_NAME, criterion);
                dropHint = true;
            }
            case "blazeandcave:adventure/raidin_master" -> {
                hintItem = Items.SPYGLASS;
                nbt.putString(SpyglassPanoramaDetails.PANORAMA_TYPE, "structure");
                nbt.putString(SpyglassPanoramaDetails.PANORAMA_NAME, criterion);
                dropHint = true;
            }
            case "blazeandcave:challenges/i_am_loot" -> {
                hintItem = Items.SPYGLASS;
                nbt.putString(SpyglassPanoramaDetails.PANORAMA_TYPE, "chest");
                nbt.putString(SpyglassPanoramaDetails.PANORAMA_NAME, criterion);
                dropHint = true;
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
        if (dropHint) {
            hint.addEnchantment(Enchantments.UNBREAKING, 1);
            hint.addHideFlag(ItemStack.TooltipSection.ENCHANTMENTS);
        }
        return new AdvancementHint(root.getAdvancement().display().get().getIcon(), advancementDisplay.getIcon(), hint, dropHint);
    }

    private static NbtCompound getColorNbtByCriterion(String criterion) {
        NbtCompound colorNbt = new NbtCompound();
        colorNbt.putInt("color", switch (criterion) {
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
        return colorNbt;
    }

    private static PlacedAdvancement getAdvancement(ServerPlayerEntity player, boolean withSingleRequirement) {
        if (player == null) {
            return null;
        }
        PlayerAdvancementTracker playerAdvancementTracker = player.getAdvancementTracker();
        AdvancementManager advancementManager = playerAdvancementTracker.advancementManager;
        Map<AdvancementEntry, AdvancementProgress> progresses = playerAdvancementTracker.progress;
        ArrayList<AdvancementEntry> advancements = new ArrayList<>();
        for (AdvancementEntry advancementEntry : new ArrayList<>(progresses.keySet())) {
            if (advancementEntry == null) {
                continue;
            }
            Advancement advancement = advancementEntry.value();
            if (advancement.isRoot()) {
                continue;
            }
            AdvancementDisplay display = advancement.display().orElse(null);
            if (display == null || display.isHidden()) {
                continue;
            }
            PlacedAdvancement placedAdvancement = advancementManager.get(advancementEntry);
            if (placedAdvancement == null) {
                continue;
            }
            PlacedAdvancement rootAdvancement = placedAdvancement.getRoot();
            if (rootAdvancement == null) {
                continue;
            }
            AdvancementsTab tab = null;
            for (AdvancementsTab advancementsTab : AdvancementsTab.values()) {
                if (advancementsTab.getRootAdvancementPath().equals(rootAdvancement.getAdvancementEntry().id().getPath())) {
                    tab = advancementsTab;
                }
            }
            if (tab == null || tab == AdvancementsTab.BLOCKED_ACTIONS || tab == AdvancementsTab.HINTS || tab == AdvancementsTab.STATISTICS) {
                continue;
            }
            int requirementsCount = advancement.requirements().requirements().size();
            if (withSingleRequirement) {
                if (requirementsCount > 1) {
                    continue;
                }
            } else {
                if (requirementsCount == 1) {
                    continue;
                }
                if (tab == AdvancementsTab.BACAP) {
                    continue;
                }
                boolean isInBlackList = false;
                for (String blackListAdvancement : BLACK_LIST) {
                    if (advancementEntry.id().equals(new Identifier(BACAP_NAMESPACE, blackListAdvancement))) {
                        isInBlackList = true;
                        break;
                    }
                }
                if (isInBlackList) {
                    continue;
                }
            }
            AdvancementProgress progress = progresses.get(advancementEntry);
            if (progress != null && progress.isDone()) {
                continue;
            }
            advancements.add(advancementEntry);
        }
        if (advancements.isEmpty()) {
            return null;
        }
        return advancementManager.get(advancements.get(player.getRandom().nextInt(advancements.size())));
    }
}
