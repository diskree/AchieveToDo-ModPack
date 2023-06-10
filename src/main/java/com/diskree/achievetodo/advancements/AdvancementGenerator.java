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
            case "blazeandcave:adventure/businessman", "blazeandcave:adventure/master_trader" -> {
                switch (criterion) {
                    case "farmer" -> hintItem = Items.BARRIER;
                    case "fisherman" -> hintItem = Items.BARRIER;
                    case "shepherd" -> hintItem = Items.BARRIER;
                    case "fletcher" -> hintItem = Items.BARRIER;
                    case "librarian" -> hintItem = Items.BARRIER;
                    case "cartographer" -> hintItem = Items.BARRIER;
                    case "cleric" -> hintItem = Items.BARRIER;
                    case "armorer" -> hintItem = Items.BARRIER;
                    case "weapon_smith" -> hintItem = Items.BARRIER;
                    case "tool_smith" -> hintItem = Items.BARRIER;
                    case "butcher" -> hintItem = Items.BARRIER;
                    case "leatherworker" -> hintItem = Items.BARRIER;
                    case "mason" -> hintItem = Items.BARRIER;
                }
            }
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
            case "blazeandcave:adventure/feeling_ill" -> {
                switch (criterion) {
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "ravager" -> hintItem = Items.BARRIER;
                    case "witch" -> hintItem = Items.BARRIER;
                    case "evoker" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:adventure/raidin_master" -> {
                switch (criterion) {
                    case "dungeon" -> hintItem = Items.BARRIER;
                    case "igloo" -> hintItem = Items.BARRIER;
                    case "desert_pyramid" -> hintItem = Items.BARRIER;
                    case "jungle_pyramid" -> hintItem = Items.BARRIER;
                    case "swamp_hut" -> hintItem = Items.BARRIER;
                    case "village_desert" -> hintItem = Items.BARRIER;
                    case "village_plains" -> hintItem = Items.BARRIER;
                    case "village_savanna" -> hintItem = Items.BARRIER;
                    case "village_snowy" -> hintItem = Items.BARRIER;
                    case "village_taiga" -> hintItem = Items.BARRIER;
                    case "mineshaft" -> hintItem = Items.BARRIER;
                    case "mineshaft_mesa" -> hintItem = Items.BARRIER;
                    case "stronghold" -> hintItem = Items.BARRIER;
                    case "fortress" -> hintItem = Items.BARRIER;
                    case "end_city" -> hintItem = Items.BARRIER;
                    case "monument" -> hintItem = Items.BARRIER;
                    case "mansion" -> hintItem = Items.BARRIER;
                    case "ocean_ruin_cold" -> hintItem = Items.BARRIER;
                    case "ocean_ruin_warm" -> hintItem = Items.BARRIER;
                    case "shipwreck" -> hintItem = Items.BARRIER;
                    case "buried_treasure" -> hintItem = Items.BARRIER;
                    case "pillager_outpost" -> hintItem = Items.BARRIER;
                    case "ruined_portal" -> hintItem = Items.BARRIER;
                    case "bastion_remnant" -> hintItem = Items.BARRIER;
                    case "ancient_city" -> hintItem = Items.BARRIER;
                    case "trail_ruins" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:adventure/traveller" -> {
                switch (criterion) {
                    case "plains" -> hintItem = Items.BARRIER;
                    case "desert" -> hintItem = Items.BARRIER;
                    case "savanna" -> hintItem = Items.BARRIER;
                    case "taiga" -> hintItem = Items.BARRIER;
                    case "snow" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:adventure/undying_fandom" -> {
                String tippedArrowPrefix = "tipped_arrow_";
                if (criterion.startsWith(tippedArrowPrefix)) {
                    hintItem = Items.TIPPED_ARROW;
                    nbt.putString("Potion", new Identifier(criterion.replace(tippedArrowPrefix, "")).toString());
                }
            }
            case "blazeandcave:adventure/you_are_the_pillager" -> {
                switch (criterion) {
                    case "farmer" -> hintItem = Items.BARRIER;
                    case "fisherman" -> hintItem = Items.BARRIER;
                    case "shepherd" -> hintItem = Items.BARRIER;
                    case "fletcher" -> hintItem = Items.BARRIER;
                    case "librarian" -> hintItem = Items.BARRIER;
                    case "cartographer" -> hintItem = Items.BARRIER;
                    case "cleric" -> hintItem = Items.BARRIER;
                    case "armorer" -> hintItem = Items.BARRIER;
                    case "weapon_smith" -> hintItem = Items.BARRIER;
                    case "tool_smith" -> hintItem = Items.BARRIER;
                    case "butcher" -> hintItem = Items.BARRIER;
                    case "leatherworker" -> hintItem = Items.BARRIER;
                    case "mason" -> hintItem = Items.BARRIER;
                    case "nitwit" -> hintItem = Items.BARRIER;
                    case "unemployed" -> hintItem = Items.BARRIER;
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
            case "blazeandcave:animal/birdkeeper" -> {
                switch (criterion) {
                    case "red" -> hintItem = Items.BARRIER;
                    case "blue" -> hintItem = Items.BARRIER;
                    case "green" -> hintItem = Items.BARRIER;
                    case "cyan" -> hintItem = Items.BARRIER;
                    case "gray" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:animal/bunny_lover" -> {
                switch (criterion) {
                    case "brown" -> hintItem = Items.BARRIER;
                    case "white" -> hintItem = Items.BARRIER;
                    case "green" -> hintItem = Items.BARRIER;
                    case "cyan" -> hintItem = Items.BARRIER;
                    case "gray" -> hintItem = Items.BARRIER;
                }
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
            case "blazeandcave:animal/follow_the_leader" -> {
                switch (criterion) {
                    case "bee" -> hintItem = Items.BARRIER;
                    case "cat" -> hintItem = Items.BARRIER;
                    case "chicken" -> hintItem = Items.BARRIER;
                    case "cow" -> hintItem = Items.BARRIER;
                    case "dolphin" -> hintItem = Items.BARRIER;
                    case "donkey" -> hintItem = Items.BARRIER;
                    case "fox" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "horse" -> hintItem = Items.BARRIER;
                    case "skeleton_horse" -> hintItem = Items.BARRIER;
                    case "iron_golem" -> hintItem = Items.BARRIER;
                    case "llama" -> hintItem = Items.BARRIER;
                    case "trader_llama" -> hintItem = Items.BARRIER;
                    case "mooshroom" -> hintItem = Items.BARRIER;
                    case "mule" -> hintItem = Items.BARRIER;
                    case "ocelot" -> hintItem = Items.BARRIER;
                    case "parrot" -> hintItem = Items.BARRIER;
                    case "pig" -> hintItem = Items.BARRIER;
                    case "polar_bear" -> hintItem = Items.BARRIER;
                    case "rabbit" -> hintItem = Items.BARRIER;
                    case "sheep" -> hintItem = Items.BARRIER;
                    case "snow_golem" -> hintItem = Items.BARRIER;
                    case "strider" -> hintItem = Items.BARRIER;
                    case "wolf" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "axolotl" -> hintItem = Items.BARRIER;
                    case "goat" -> hintItem = Items.BARRIER;
                    case "squid" -> hintItem = Items.BARRIER;
                    case "glow_squid" -> hintItem = Items.BARRIER;
                    case "frog" -> hintItem = Items.BARRIER;
                    case "allay" -> hintItem = Items.BARRIER;
                    case "camel" -> hintItem = Items.BARRIER;
                    case "sniffer" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:animal/llama_llama_duck_king" -> {
                switch (criterion) {
                    case "creamy" -> hintItem = Items.BARRIER;
                    case "white" -> hintItem = Items.BARRIER;
                    case "brown" -> hintItem = Items.BARRIER;
                    case "gray" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:animal/master_farrier" -> {
                switch (criterion) {
                    case "white_none" -> hintItem = Items.BARRIER;
                    case "creamy_none" -> hintItem = Items.BARRIER;
                    case "chestnut_none" -> hintItem = Items.BARRIER;
                    case "brown_none" -> hintItem = Items.BARRIER;
                    case "black_none" -> hintItem = Items.BARRIER;
                    case "gray_none" -> hintItem = Items.BARRIER;
                    case "dark_brown_none" -> hintItem = Items.BARRIER;
                    case "white_white" -> hintItem = Items.BARRIER;
                    case "creamy_white" -> hintItem = Items.BARRIER;
                    case "chestnut_white" -> hintItem = Items.BARRIER;
                    case "brown_white" -> hintItem = Items.BARRIER;
                    case "black_white" -> hintItem = Items.BARRIER;
                    case "gray_white" -> hintItem = Items.BARRIER;
                    case "dark_brown_white" -> hintItem = Items.BARRIER;
                    case "white_white_field" -> hintItem = Items.BARRIER;
                    case "creamy_white_field" -> hintItem = Items.BARRIER;
                    case "chestnut_white_field" -> hintItem = Items.BARRIER;
                    case "brown_white_field" -> hintItem = Items.BARRIER;
                    case "black_white_field" -> hintItem = Items.BARRIER;
                    case "gray_white_field" -> hintItem = Items.BARRIER;
                    case "dark_brown_white_field" -> hintItem = Items.BARRIER;
                    case "white_white_dots" -> hintItem = Items.BARRIER;
                    case "creamy_white_dots" -> hintItem = Items.BARRIER;
                    case "chestnut_white_dots" -> hintItem = Items.BARRIER;
                    case "brown_white_dots" -> hintItem = Items.BARRIER;
                    case "black_white_dots" -> hintItem = Items.BARRIER;
                    case "gray_white_dots" -> hintItem = Items.BARRIER;
                    case "dark_brown_white_dots" -> hintItem = Items.BARRIER;
                    case "white_black_dots" -> hintItem = Items.BARRIER;
                    case "creamy_black_dots" -> hintItem = Items.BARRIER;
                    case "chestnut_black_dots" -> hintItem = Items.BARRIER;
                    case "brown_black_dots" -> hintItem = Items.BARRIER;
                    case "black_black_dots" -> hintItem = Items.BARRIER;
                    case "gray_black_dots" -> hintItem = Items.BARRIER;
                    case "dark_brown_black_dots" -> hintItem = Items.BARRIER;
                }
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
            case "blazeandcave:biomes/cold_feet" -> {
                switch (criterion) {
                    case "snowy_taiga" -> hintItem = Items.BARRIER;
                    case "snowy_plains" -> hintItem = Items.BARRIER;
                    case "ice_spikes" -> hintItem = Items.BARRIER;
                    case "frozen_river" -> hintItem = Items.BARRIER;
                    case "snowy_beach" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:biomes/high_feet" -> {
                switch (criterion) {
                    case "stony_shore" -> hintItem = Items.BARRIER;
                    case "windswept_hills" -> hintItem = Items.BARRIER;
                    case "windswept_forest" -> hintItem = Items.BARRIER;
                    case "windswept_gravelly_hills" -> hintItem = Items.BARRIER;
                    case "meadow" -> hintItem = Items.BARRIER;
                    case "grove" -> hintItem = Items.BARRIER;
                    case "snowy_slopes" -> hintItem = Items.BARRIER;
                    case "frozen_peaks" -> hintItem = Items.BARRIER;
                    case "jagged_peaks" -> hintItem = Items.BARRIER;
                    case "stony_peaks" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:biomes/one_with_the_forest" -> {
                switch (criterion) {
                    case "forest" -> hintItem = Items.BARRIER;
                    case "flower_forest" -> hintItem = Items.BARRIER;
                    case "plains" -> hintItem = Items.BARRIER;
                    case "sunflower_plains" -> hintItem = Items.BARRIER;
                    case "dark_forest" -> hintItem = Items.BARRIER;
                    case "birch_forest" -> hintItem = Items.BARRIER;
                    case "old_growth_birch_forest" -> hintItem = Items.BARRIER;
                    case "old_growth_pine_taiga" -> hintItem = Items.BARRIER;
                    case "old_growth_spruce_taiga" -> hintItem = Items.BARRIER;
                    case "taiga" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:biomes/overgrown" -> {
                switch (criterion) {
                    case "swamp" -> hintItem = Items.BARRIER;
                    case "mangrove_swamp" -> hintItem = Items.BARRIER;
                    case "mushroom_fields" -> hintItem = Items.BARRIER;
                    case "sparse_jungle" -> hintItem = Items.BARRIER;
                    case "jungle" -> hintItem = Items.BARRIER;
                    case "bamboo_jungle" -> hintItem = Items.BARRIER;
                    case "cherry_grove" -> hintItem = Items.BARRIER;
                }
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
            case "blazeandcave:biomes/warm_feet" -> {
                switch (criterion) {
                    case "desert" -> hintItem = Items.BARRIER;
                    case "badlands" -> hintItem = Items.BARRIER;
                    case "wooded_badlands" -> hintItem = Items.BARRIER;
                    case "eroded_badlands" -> hintItem = Items.BARRIER;
                    case "savanna" -> hintItem = Items.BARRIER;
                    case "savanna_plateau" -> hintItem = Items.BARRIER;
                    case "windswept_savanna" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:biomes/wet_feet" -> {
                switch (criterion) {
                    case "river" -> hintItem = Items.BARRIER;
                    case "deep_ocean" -> hintItem = Items.BARRIER;
                    case "ocean" -> hintItem = Items.BARRIER;
                    case "beach" -> hintItem = Items.BARRIER;
                    case "deep_frozen_ocean" -> hintItem = Items.BARRIER;
                    case "frozen_ocean" -> hintItem = Items.BARRIER;
                    case "cold_ocean" -> hintItem = Items.BARRIER;
                    case "deep_cold_ocean" -> hintItem = Items.BARRIER;
                    case "lukewarm_ocean" -> hintItem = Items.BARRIER;
                    case "deep_lukewarm_ocean" -> hintItem = Items.BARRIER;
                    case "warm_ocean" -> hintItem = Items.BARRIER;
                }
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
            case "blazeandcave:challenges/biological_warfare", "blazeandcave:challenges/potion_master" -> {
                switch (criterion) {
                    case "bat" -> hintItem = Items.BARRIER;
                    case "chicken" -> hintItem = Items.BARRIER;
                    case "cod" -> hintItem = Items.BARRIER;
                    case "cow" -> hintItem = Items.BARRIER;
                    case "mooshroom" -> hintItem = Items.BARRIER;
                    case "pig" -> hintItem = Items.BARRIER;
                    case "rabbit" -> hintItem = Items.BARRIER;
                    case "salmon" -> hintItem = Items.BARRIER;
                    case "sheep" -> hintItem = Items.BARRIER;
                    case "squid" -> hintItem = Items.BARRIER;
                    case "tropical_fish" -> hintItem = Items.BARRIER;
                    case "turtle" -> hintItem = Items.BARRIER;
                    case "villager" -> hintItem = Items.BARRIER;
                    case "wandering_trader" -> hintItem = Items.BARRIER;
                    case "pufferfish" -> hintItem = Items.BARRIER;
                    case "cave_spider" -> hintItem = Items.BARRIER;
                    case "panda" -> hintItem = Items.BARRIER;
                    case "polar_bear" -> hintItem = Items.BARRIER;
                    case "spider" -> hintItem = Items.BARRIER;
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "creeper" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                    case "endermite" -> hintItem = Items.BARRIER;
                    case "evoker" -> hintItem = Items.BARRIER;
                    case "ghast" -> hintItem = Items.BARRIER;
                    case "guardian" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "magma_cube" -> hintItem = Items.BARRIER;
                    case "phantom" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "ravager" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "silverfish" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "slime" -> hintItem = Items.BARRIER;
                    case "stray" -> hintItem = Items.BARRIER;
                    case "vex" -> hintItem = Items.BARRIER;
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "witch" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "cat" -> hintItem = Items.BARRIER;
                    case "donkey" -> hintItem = Items.BARRIER;
                    case "horse" -> hintItem = Items.BARRIER;
                    case "llama" -> hintItem = Items.BARRIER;
                    case "mule" -> hintItem = Items.BARRIER;
                    case "parrot" -> hintItem = Items.BARRIER;
                    case "skeleton_horse" -> hintItem = Items.BARRIER;
                    case "trader_llama" -> hintItem = Items.BARRIER;
                    case "wolf" -> hintItem = Items.BARRIER;
                    case "dolphin" -> hintItem = Items.BARRIER;
                    case "fox" -> hintItem = Items.BARRIER;
                    case "ocelot" -> hintItem = Items.BARRIER;
                    case "iron_golem" -> hintItem = Items.BARRIER;
                    case "snow_golem" -> hintItem = Items.BARRIER;
                    case "wither" -> hintItem = Items.BARRIER;
                    case "bee" -> hintItem = Items.BARRIER;
                    case "piglin" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "strider" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
                    case "axolotl" -> hintItem = Items.BARRIER;
                    case "glow_squid" -> hintItem = Items.BARRIER;
                    case "goat" -> hintItem = Items.BARRIER;
                    case "frog" -> hintItem = Items.BARRIER;
                    case "tadpole" -> hintItem = Items.BARRIER;
                    case "allay" -> hintItem = Items.BARRIER;
                    case "warden" -> hintItem = Items.BARRIER;
                    case "camel" -> hintItem = Items.BARRIER;
                    case "sniffer" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:challenges/death_from_all" -> {
                switch (criterion) {
                    case "pufferfish" -> hintItem = Items.BARRIER;
                    case "cave_spider" -> hintItem = Items.BARRIER;
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "panda" -> hintItem = Items.BARRIER;
                    case "polar_bear" -> hintItem = Items.BARRIER;
                    case "spider" -> hintItem = Items.BARRIER;
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "creeper" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                    case "elder_guardian" -> hintItem = Items.BARRIER;
                    case "endermite" -> hintItem = Items.BARRIER;
                    case "evoker" -> hintItem = Items.BARRIER;
                    case "ghast" -> hintItem = Items.BARRIER;
                    case "guardian" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "magma_cube" -> hintItem = Items.BARRIER;
                    case "phantom" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "ravager" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "silverfish" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "slime" -> hintItem = Items.BARRIER;
                    case "stray" -> hintItem = Items.BARRIER;
                    case "vex" -> hintItem = Items.BARRIER;
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "witch" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "llama" -> hintItem = Items.BARRIER;
                    case "trader_llama" -> hintItem = Items.BARRIER;
                    case "wolf" -> hintItem = Items.BARRIER;
                    case "dolphin" -> hintItem = Items.BARRIER;
                    case "iron_golem" -> hintItem = Items.BARRIER;
                    case "ender_dragon" -> hintItem = Items.BARRIER;
                    case "wither" -> hintItem = Items.BARRIER;
                    case "bee" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "piglin" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
                    case "goat" -> hintItem = Items.BARRIER;
                    case "warden" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:challenges/endergeddon" -> {
                switch (criterion) {
                    case "bat" -> hintItem = Items.BARRIER;
                    case "chicken" -> hintItem = Items.BARRIER;
                    case "cod" -> hintItem = Items.BARRIER;
                    case "cow" -> hintItem = Items.BARRIER;
                    case "mooshroom" -> hintItem = Items.BARRIER;
                    case "pig" -> hintItem = Items.BARRIER;
                    case "rabbit" -> hintItem = Items.BARRIER;
                    case "salmon" -> hintItem = Items.BARRIER;
                    case "sheep" -> hintItem = Items.BARRIER;
                    case "squid" -> hintItem = Items.BARRIER;
                    case "tropical_fish" -> hintItem = Items.BARRIER;
                    case "turtle" -> hintItem = Items.BARRIER;
                    case "villager" -> hintItem = Items.BARRIER;
                    case "wandering_trader" -> hintItem = Items.BARRIER;
                    case "pufferfish" -> hintItem = Items.BARRIER;
                    case "cave_spider" -> hintItem = Items.BARRIER;
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "panda" -> hintItem = Items.BARRIER;
                    case "polar_bear" -> hintItem = Items.BARRIER;
                    case "spider" -> hintItem = Items.BARRIER;
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "creeper" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                    case "endermite" -> hintItem = Items.BARRIER;
                    case "evoker" -> hintItem = Items.BARRIER;
                    case "ghast" -> hintItem = Items.BARRIER;
                    case "guardian" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "magma_cube" -> hintItem = Items.BARRIER;
                    case "phantom" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "ravager" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "silverfish" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "slime" -> hintItem = Items.BARRIER;
                    case "stray" -> hintItem = Items.BARRIER;
                    case "vex" -> hintItem = Items.BARRIER;
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "witch" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "cat" -> hintItem = Items.BARRIER;
                    case "donkey" -> hintItem = Items.BARRIER;
                    case "horse" -> hintItem = Items.BARRIER;
                    case "llama" -> hintItem = Items.BARRIER;
                    case "mule" -> hintItem = Items.BARRIER;
                    case "parrot" -> hintItem = Items.BARRIER;
                    case "skeleton_horse" -> hintItem = Items.BARRIER;
                    case "trader_llama" -> hintItem = Items.BARRIER;
                    case "wolf" -> hintItem = Items.BARRIER;
                    case "dolphin" -> hintItem = Items.BARRIER;
                    case "fox" -> hintItem = Items.BARRIER;
                    case "ocelot" -> hintItem = Items.BARRIER;
                    case "iron_golem" -> hintItem = Items.BARRIER;
                    case "snow_golem" -> hintItem = Items.BARRIER;
                    case "wither" -> hintItem = Items.BARRIER;
                    case "ender_dragon" -> hintItem = Items.BARRIER;
                    case "bee" -> hintItem = Items.BARRIER;
                    case "piglin" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "strider" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
                    case "axolotl" -> hintItem = Items.BARRIER;
                    case "glow_squid" -> hintItem = Items.BARRIER;
                    case "goat" -> hintItem = Items.BARRIER;
                    case "frog" -> hintItem = Items.BARRIER;
                    case "tadpole" -> hintItem = Items.BARRIER;
                    case "allay" -> hintItem = Items.BARRIER;
                    case "warden" -> hintItem = Items.BARRIER;
                    case "camel" -> hintItem = Items.BARRIER;
                    case "sniffer" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:challenges/explorer_of_worlds" -> {
                switch (criterion) {
                    case "river" -> hintItem = Items.BARRIER;
                    case "swamp" -> hintItem = Items.BARRIER;
                    case "desert" -> hintItem = Items.BARRIER;
                    case "snowy_taiga" -> hintItem = Items.BARRIER;
                    case "badlands" -> hintItem = Items.BARRIER;
                    case "forest" -> hintItem = Items.BARRIER;
                    case "stony_shore" -> hintItem = Items.BARRIER;
                    case "snowy_plains" -> hintItem = Items.BARRIER;
                    case "wooded_badlands" -> hintItem = Items.BARRIER;
                    case "savanna" -> hintItem = Items.BARRIER;
                    case "plains" -> hintItem = Items.BARRIER;
                    case "frozen_river" -> hintItem = Items.BARRIER;
                    case "old_growth_pine_taiga" -> hintItem = Items.BARRIER;
                    case "snowy_beach" -> hintItem = Items.BARRIER;
                    case "deep_ocean" -> hintItem = Items.BARRIER;
                    case "sparse_jungle" -> hintItem = Items.BARRIER;
                    case "ocean" -> hintItem = Items.BARRIER;
                    case "windswept_hills" -> hintItem = Items.BARRIER;
                    case "jungle" -> hintItem = Items.BARRIER;
                    case "beach" -> hintItem = Items.BARRIER;
                    case "savanna_plateau" -> hintItem = Items.BARRIER;
                    case "dark_forest" -> hintItem = Items.BARRIER;
                    case "taiga" -> hintItem = Items.BARRIER;
                    case "birch_forest" -> hintItem = Items.BARRIER;
                    case "mushroom_fields" -> hintItem = Items.BARRIER;
                    case "windswept_forest" -> hintItem = Items.BARRIER;
                    case "cold_ocean" -> hintItem = Items.BARRIER;
                    case "warm_ocean" -> hintItem = Items.BARRIER;
                    case "lukewarm_ocean" -> hintItem = Items.BARRIER;
                    case "frozen_ocean" -> hintItem = Items.BARRIER;
                    case "deep_frozen_ocean" -> hintItem = Items.BARRIER;
                    case "deep_lukewarm_ocean" -> hintItem = Items.BARRIER;
                    case "deep_cold_ocean" -> hintItem = Items.BARRIER;
                    case "bamboo_jungle" -> hintItem = Items.BARRIER;
                    case "sunflower_plains" -> hintItem = Items.BARRIER;
                    case "windswept_gravelly_hills" -> hintItem = Items.BARRIER;
                    case "flower_forest" -> hintItem = Items.BARRIER;
                    case "ice_spikes" -> hintItem = Items.BARRIER;
                    case "old_growth_birch_forest" -> hintItem = Items.BARRIER;
                    case "old_growth_spruce_taiga" -> hintItem = Items.BARRIER;
                    case "windswept_savanna" -> hintItem = Items.BARRIER;
                    case "eroded_badlands" -> hintItem = Items.BARRIER;
                    case "meadow" -> hintItem = Items.BARRIER;
                    case "grove" -> hintItem = Items.BARRIER;
                    case "snowy_slopes" -> hintItem = Items.BARRIER;
                    case "frozen_peaks" -> hintItem = Items.BARRIER;
                    case "jagged_peaks" -> hintItem = Items.BARRIER;
                    case "stony_peaks" -> hintItem = Items.BARRIER;
                    case "lush_caves" -> hintItem = Items.BARRIER;
                    case "dripstone_caves" -> hintItem = Items.BARRIER;
                    case "nether_wastes" -> hintItem = Items.BARRIER;
                    case "crimson_forest" -> hintItem = Items.BARRIER;
                    case "warped_forest" -> hintItem = Items.BARRIER;
                    case "soul_sand_valley" -> hintItem = Items.BARRIER;
                    case "basalt_deltas" -> hintItem = Items.BARRIER;
                    case "the_end" -> hintItem = Items.BARRIER;
                    case "small_end_islands" -> hintItem = Items.BARRIER;
                    case "end_highlands" -> hintItem = Items.BARRIER;
                    case "end_midlands" -> hintItem = Items.BARRIER;
                    case "end_barrens" -> hintItem = Items.BARRIER;
                    case "mangrove_swamp" -> hintItem = Items.BARRIER;
                    case "deep_dark" -> hintItem = Items.BARRIER;
                    case "cherry_grove" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:challenges/global_vaccination" -> {
                switch (criterion) {
                    case "farmer_plains" -> hintItem = Items.BARRIER;
                    case "fisherman_plains" -> hintItem = Items.BARRIER;
                    case "shepherd_plains" -> hintItem = Items.BARRIER;
                    case "fletcher_plains" -> hintItem = Items.BARRIER;
                    case "librarian_plains" -> hintItem = Items.BARRIER;
                    case "cartographer_plains" -> hintItem = Items.BARRIER;
                    case "cleric_plains" -> hintItem = Items.BARRIER;
                    case "armorer_plains" -> hintItem = Items.BARRIER;
                    case "weapon_smith_plains" -> hintItem = Items.BARRIER;
                    case "tool_smith_plains" -> hintItem = Items.BARRIER;
                    case "butcher_plains" -> hintItem = Items.BARRIER;
                    case "leatherworker_plains" -> hintItem = Items.BARRIER;
                    case "mason_plains" -> hintItem = Items.BARRIER;
                    case "nitwit_plains" -> hintItem = Items.BARRIER;
                    case "none_plains" -> hintItem = Items.BARRIER;
                    case "farmer_taiga" -> hintItem = Items.BARRIER;
                    case "fisherman_taiga" -> hintItem = Items.BARRIER;
                    case "shepherd_taiga" -> hintItem = Items.BARRIER;
                    case "fletcher_taiga" -> hintItem = Items.BARRIER;
                    case "librarian_taiga" -> hintItem = Items.BARRIER;
                    case "cartographer_taiga" -> hintItem = Items.BARRIER;
                    case "cleric_taiga" -> hintItem = Items.BARRIER;
                    case "armorer_taiga" -> hintItem = Items.BARRIER;
                    case "weapon_smith_taiga" -> hintItem = Items.BARRIER;
                    case "tool_smith_taiga" -> hintItem = Items.BARRIER;
                    case "butcher_taiga" -> hintItem = Items.BARRIER;
                    case "leatherworker_taiga" -> hintItem = Items.BARRIER;
                    case "mason_taiga" -> hintItem = Items.BARRIER;
                    case "nitwit_taiga" -> hintItem = Items.BARRIER;
                    case "none_taiga" -> hintItem = Items.BARRIER;
                    case "farmer_desert" -> hintItem = Items.BARRIER;
                    case "fisherman_desert" -> hintItem = Items.BARRIER;
                    case "shepherd_desert" -> hintItem = Items.BARRIER;
                    case "fletcher_desert" -> hintItem = Items.BARRIER;
                    case "librarian_desert" -> hintItem = Items.BARRIER;
                    case "cartographer_desert" -> hintItem = Items.BARRIER;
                    case "cleric_desert" -> hintItem = Items.BARRIER;
                    case "armorer_desert" -> hintItem = Items.BARRIER;
                    case "weapon_smith_desert" -> hintItem = Items.BARRIER;
                    case "tool_smith_desert" -> hintItem = Items.BARRIER;
                    case "butcher_desert" -> hintItem = Items.BARRIER;
                    case "leatherworker_desert" -> hintItem = Items.BARRIER;
                    case "mason_desert" -> hintItem = Items.BARRIER;
                    case "nitwit_desert" -> hintItem = Items.BARRIER;
                    case "none_desert" -> hintItem = Items.BARRIER;
                    case "farmer_savanna" -> hintItem = Items.BARRIER;
                    case "fisherman_savanna" -> hintItem = Items.BARRIER;
                    case "shepherd_savanna" -> hintItem = Items.BARRIER;
                    case "fletcher_savanna" -> hintItem = Items.BARRIER;
                    case "librarian_savanna" -> hintItem = Items.BARRIER;
                    case "cartographer_savanna" -> hintItem = Items.BARRIER;
                    case "cleric_savanna" -> hintItem = Items.BARRIER;
                    case "armorer_savanna" -> hintItem = Items.BARRIER;
                    case "weapon_smith_savanna" -> hintItem = Items.BARRIER;
                    case "tool_smith_savanna" -> hintItem = Items.BARRIER;
                    case "butcher_savanna" -> hintItem = Items.BARRIER;
                    case "leatherworker_savanna" -> hintItem = Items.BARRIER;
                    case "mason_savanna" -> hintItem = Items.BARRIER;
                    case "nitwit_savanna" -> hintItem = Items.BARRIER;
                    case "none_savanna" -> hintItem = Items.BARRIER;
                    case "farmer_snow" -> hintItem = Items.BARRIER;
                    case "fisherman_snow" -> hintItem = Items.BARRIER;
                    case "shepherd_snow" -> hintItem = Items.BARRIER;
                    case "fletcher_snow" -> hintItem = Items.BARRIER;
                    case "librarian_snow" -> hintItem = Items.BARRIER;
                    case "cartographer_snow" -> hintItem = Items.BARRIER;
                    case "cleric_snow" -> hintItem = Items.BARRIER;
                    case "armorer_snow" -> hintItem = Items.BARRIER;
                    case "weapon_smith_snow" -> hintItem = Items.BARRIER;
                    case "tool_smith_snow" -> hintItem = Items.BARRIER;
                    case "butcher_snow" -> hintItem = Items.BARRIER;
                    case "leatherworker_snow" -> hintItem = Items.BARRIER;
                    case "mason_snow" -> hintItem = Items.BARRIER;
                    case "nitwit_snow" -> hintItem = Items.BARRIER;
                    case "none_snow" -> hintItem = Items.BARRIER;
                    case "farmer_jungle" -> hintItem = Items.BARRIER;
                    case "fisherman_jungle" -> hintItem = Items.BARRIER;
                    case "shepherd_jungle" -> hintItem = Items.BARRIER;
                    case "fletcher_jungle" -> hintItem = Items.BARRIER;
                    case "librarian_jungle" -> hintItem = Items.BARRIER;
                    case "cartographer_jungle" -> hintItem = Items.BARRIER;
                    case "cleric_jungle" -> hintItem = Items.BARRIER;
                    case "armorer_jungle" -> hintItem = Items.BARRIER;
                    case "weapon_smith_jungle" -> hintItem = Items.BARRIER;
                    case "tool_smith_jungle" -> hintItem = Items.BARRIER;
                    case "butcher_jungle" -> hintItem = Items.BARRIER;
                    case "leatherworker_jungle" -> hintItem = Items.BARRIER;
                    case "mason_jungle" -> hintItem = Items.BARRIER;
                    case "nitwit_jungle" -> hintItem = Items.BARRIER;
                    case "none_jungle" -> hintItem = Items.BARRIER;
                    case "farmer_swamp" -> hintItem = Items.BARRIER;
                    case "fisherman_swamp" -> hintItem = Items.BARRIER;
                    case "shepherd_swamp" -> hintItem = Items.BARRIER;
                    case "fletcher_swamp" -> hintItem = Items.BARRIER;
                    case "librarian_swamp" -> hintItem = Items.BARRIER;
                    case "cartographer_swamp" -> hintItem = Items.BARRIER;
                    case "cleric_swamp" -> hintItem = Items.BARRIER;
                    case "armorer_swamp" -> hintItem = Items.BARRIER;
                    case "weapon_smith_swamp" -> hintItem = Items.BARRIER;
                    case "tool_smith_swamp" -> hintItem = Items.BARRIER;
                    case "butcher_swamp" -> hintItem = Items.BARRIER;
                    case "leatherworker_swamp" -> hintItem = Items.BARRIER;
                    case "mason_swamp" -> hintItem = Items.BARRIER;
                    case "nitwit_swamp" -> hintItem = Items.BARRIER;
                    case "none_swamp" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:challenges/highway_to_hell" -> {
                switch (criterion) {
                    case "bat" -> hintItem = Items.BARRIER;
                    case "chicken" -> hintItem = Items.BARRIER;
                    case "cod" -> hintItem = Items.BARRIER;
                    case "cow" -> hintItem = Items.BARRIER;
                    case "mooshroom" -> hintItem = Items.BARRIER;
                    case "pig" -> hintItem = Items.BARRIER;
                    case "rabbit" -> hintItem = Items.BARRIER;
                    case "salmon" -> hintItem = Items.BARRIER;
                    case "sheep" -> hintItem = Items.BARRIER;
                    case "squid" -> hintItem = Items.BARRIER;
                    case "tropical_fish" -> hintItem = Items.BARRIER;
                    case "turtle" -> hintItem = Items.BARRIER;
                    case "villager" -> hintItem = Items.BARRIER;
                    case "wandering_trader" -> hintItem = Items.BARRIER;
                    case "pufferfish" -> hintItem = Items.BARRIER;
                    case "cave_spider" -> hintItem = Items.BARRIER;
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "panda" -> hintItem = Items.BARRIER;
                    case "polar_bear" -> hintItem = Items.BARRIER;
                    case "spider" -> hintItem = Items.BARRIER;
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "creeper" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                    case "endermite" -> hintItem = Items.BARRIER;
                    case "evoker" -> hintItem = Items.BARRIER;
                    case "ghast" -> hintItem = Items.BARRIER;
                    case "guardian" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "magma_cube" -> hintItem = Items.BARRIER;
                    case "phantom" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "ravager" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "silverfish" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "slime" -> hintItem = Items.BARRIER;
                    case "stray" -> hintItem = Items.BARRIER;
                    case "vex" -> hintItem = Items.BARRIER;
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "witch" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "cat" -> hintItem = Items.BARRIER;
                    case "donkey" -> hintItem = Items.BARRIER;
                    case "horse" -> hintItem = Items.BARRIER;
                    case "llama" -> hintItem = Items.BARRIER;
                    case "mule" -> hintItem = Items.BARRIER;
                    case "parrot" -> hintItem = Items.BARRIER;
                    case "skeleton_horse" -> hintItem = Items.BARRIER;
                    case "trader_llama" -> hintItem = Items.BARRIER;
                    case "wolf" -> hintItem = Items.BARRIER;
                    case "dolphin" -> hintItem = Items.BARRIER;
                    case "fox" -> hintItem = Items.BARRIER;
                    case "ocelot" -> hintItem = Items.BARRIER;
                    case "iron_golem" -> hintItem = Items.BARRIER;
                    case "snow_golem" -> hintItem = Items.BARRIER;
                    case "wither" -> hintItem = Items.BARRIER;
                    case "bee" -> hintItem = Items.BARRIER;
                    case "piglin" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "strider" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
                    case "axolotl" -> hintItem = Items.BARRIER;
                    case "glow_squid" -> hintItem = Items.BARRIER;
                    case "goat" -> hintItem = Items.BARRIER;
                    case "frog" -> hintItem = Items.BARRIER;
                    case "tadpole" -> hintItem = Items.BARRIER;
                    case "allay" -> hintItem = Items.BARRIER;
                    case "warden" -> hintItem = Items.BARRIER;
                    case "camel" -> hintItem = Items.BARRIER;
                    case "sniffer" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:challenges/i_am_loot" -> {
                switch (criterion) {
                    case "abandoned_mineshaft" -> hintItem = Items.BARRIER;
                    case "bastion_bridge" -> hintItem = Items.BARRIER;
                    case "bastion_hoglin_stable" -> hintItem = Items.BARRIER;
                    case "bastion_other" -> hintItem = Items.BARRIER;
                    case "bastion_treasure" -> hintItem = Items.BARRIER;
                    case "buried_treasure" -> hintItem = Items.BARRIER;
                    case "desert_pyramid" -> hintItem = Items.BARRIER;
                    case "end_city_treasure" -> hintItem = Items.BARRIER;
                    case "igloo_chest" -> hintItem = Items.BARRIER;
                    case "jungle_temple" -> hintItem = Items.BARRIER;
                    case "jungle_temple_dispenser" -> hintItem = Items.BARRIER;
                    case "nether_bridge" -> hintItem = Items.BARRIER;
                    case "pillager_outpost" -> hintItem = Items.BARRIER;
                    case "ruined_portal" -> hintItem = Items.BARRIER;
                    case "shipwreck_map" -> hintItem = Items.BARRIER;
                    case "shipwreck_supply" -> hintItem = Items.BARRIER;
                    case "shipwreck_treasure" -> hintItem = Items.BARRIER;
                    case "simple_dungeon" -> hintItem = Items.BARRIER;
                    case "stronghold_corridor" -> hintItem = Items.BARRIER;
                    case "stronghold_crossing" -> hintItem = Items.BARRIER;
                    case "stronghold_library" -> hintItem = Items.BARRIER;
                    case "underwater_ruin_big" -> hintItem = Items.BARRIER;
                    case "underwater_ruin_small" -> hintItem = Items.BARRIER;
                    case "woodland_mansion" -> hintItem = Items.BARRIER;
                    case "village_armorer" -> hintItem = Items.BARRIER;
                    case "village_butcher" -> hintItem = Items.BARRIER;
                    case "village_cartographer" -> hintItem = Items.BARRIER;
                    case "village_desert_house" -> hintItem = Items.BARRIER;
                    case "village_fisher" -> hintItem = Items.BARRIER;
                    case "village_fletcher" -> hintItem = Items.BARRIER;
                    case "village_mason" -> hintItem = Items.BARRIER;
                    case "village_plains_house" -> hintItem = Items.BARRIER;
                    case "village_savanna_house" -> hintItem = Items.BARRIER;
                    case "village_snowy_house" -> hintItem = Items.BARRIER;
                    case "village_taiga_house" -> hintItem = Items.BARRIER;
                    case "village_tannery" -> hintItem = Items.BARRIER;
                    case "village_temple" -> hintItem = Items.BARRIER;
                    case "village_toolsmith" -> hintItem = Items.BARRIER;
                    case "village_weaponsmith" -> hintItem = Items.BARRIER;
                    case "ancient_city" -> hintItem = Items.BARRIER;
                    case "ancient_city_ice_box" -> hintItem = Items.BARRIER;
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
            case "blazeandcave:challenges/stockbroker" -> {
                switch (criterion) {
                    case "farmer_plains" -> hintItem = Items.BARRIER;
                    case "fisherman_plains" -> hintItem = Items.BARRIER;
                    case "shepherd_plains" -> hintItem = Items.BARRIER;
                    case "fletcher_plains" -> hintItem = Items.BARRIER;
                    case "librarian_plains" -> hintItem = Items.BARRIER;
                    case "cartographer_plains" -> hintItem = Items.BARRIER;
                    case "cleric_plains" -> hintItem = Items.BARRIER;
                    case "armorer_plains" -> hintItem = Items.BARRIER;
                    case "weapon_smith_plains" -> hintItem = Items.BARRIER;
                    case "tool_smith_plains" -> hintItem = Items.BARRIER;
                    case "butcher_plains" -> hintItem = Items.BARRIER;
                    case "leatherworker_plains" -> hintItem = Items.BARRIER;
                    case "mason_plains" -> hintItem = Items.BARRIER;
                    case "farmer_taiga" -> hintItem = Items.BARRIER;
                    case "fisherman_taiga" -> hintItem = Items.BARRIER;
                    case "shepherd_taiga" -> hintItem = Items.BARRIER;
                    case "fletcher_taiga" -> hintItem = Items.BARRIER;
                    case "librarian_taiga" -> hintItem = Items.BARRIER;
                    case "cartographer_taiga" -> hintItem = Items.BARRIER;
                    case "cleric_taiga" -> hintItem = Items.BARRIER;
                    case "armorer_taiga" -> hintItem = Items.BARRIER;
                    case "weapon_smith_taiga" -> hintItem = Items.BARRIER;
                    case "tool_smith_taiga" -> hintItem = Items.BARRIER;
                    case "butcher_taiga" -> hintItem = Items.BARRIER;
                    case "leatherworker_taiga" -> hintItem = Items.BARRIER;
                    case "mason_taiga" -> hintItem = Items.BARRIER;
                    case "farmer_desert" -> hintItem = Items.BARRIER;
                    case "fisherman_desert" -> hintItem = Items.BARRIER;
                    case "shepherd_desert" -> hintItem = Items.BARRIER;
                    case "fletcher_desert" -> hintItem = Items.BARRIER;
                    case "librarian_desert" -> hintItem = Items.BARRIER;
                    case "cartographer_desert" -> hintItem = Items.BARRIER;
                    case "cleric_desert" -> hintItem = Items.BARRIER;
                    case "armorer_desert" -> hintItem = Items.BARRIER;
                    case "weapon_smith_desert" -> hintItem = Items.BARRIER;
                    case "tool_smith_desert" -> hintItem = Items.BARRIER;
                    case "butcher_desert" -> hintItem = Items.BARRIER;
                    case "leatherworker_desert" -> hintItem = Items.BARRIER;
                    case "mason_desert" -> hintItem = Items.BARRIER;
                    case "farmer_savanna" -> hintItem = Items.BARRIER;
                    case "fisherman_savanna" -> hintItem = Items.BARRIER;
                    case "shepherd_savanna" -> hintItem = Items.BARRIER;
                    case "fletcher_savanna" -> hintItem = Items.BARRIER;
                    case "librarian_savanna" -> hintItem = Items.BARRIER;
                    case "cartographer_savanna" -> hintItem = Items.BARRIER;
                    case "cleric_savanna" -> hintItem = Items.BARRIER;
                    case "armorer_savanna" -> hintItem = Items.BARRIER;
                    case "weapon_smith_savanna" -> hintItem = Items.BARRIER;
                    case "tool_smith_savanna" -> hintItem = Items.BARRIER;
                    case "butcher_savanna" -> hintItem = Items.BARRIER;
                    case "leatherworker_savanna" -> hintItem = Items.BARRIER;
                    case "mason_savanna" -> hintItem = Items.BARRIER;
                    case "farmer_snow" -> hintItem = Items.BARRIER;
                    case "fisherman_snow" -> hintItem = Items.BARRIER;
                    case "shepherd_snow" -> hintItem = Items.BARRIER;
                    case "fletcher_snow" -> hintItem = Items.BARRIER;
                    case "librarian_snow" -> hintItem = Items.BARRIER;
                    case "cartographer_snow" -> hintItem = Items.BARRIER;
                    case "cleric_snow" -> hintItem = Items.BARRIER;
                    case "armorer_snow" -> hintItem = Items.BARRIER;
                    case "weapon_smith_snow" -> hintItem = Items.BARRIER;
                    case "tool_smith_snow" -> hintItem = Items.BARRIER;
                    case "butcher_snow" -> hintItem = Items.BARRIER;
                    case "leatherworker_snow" -> hintItem = Items.BARRIER;
                    case "mason_snow" -> hintItem = Items.BARRIER;
                    case "farmer_jungle" -> hintItem = Items.BARRIER;
                    case "fisherman_jungle" -> hintItem = Items.BARRIER;
                    case "shepherd_jungle" -> hintItem = Items.BARRIER;
                    case "fletcher_jungle" -> hintItem = Items.BARRIER;
                    case "librarian_jungle" -> hintItem = Items.BARRIER;
                    case "cartographer_jungle" -> hintItem = Items.BARRIER;
                    case "cleric_jungle" -> hintItem = Items.BARRIER;
                    case "armorer_jungle" -> hintItem = Items.BARRIER;
                    case "weapon_smith_jungle" -> hintItem = Items.BARRIER;
                    case "tool_smith_jungle" -> hintItem = Items.BARRIER;
                    case "butcher_jungle" -> hintItem = Items.BARRIER;
                    case "leatherworker_jungle" -> hintItem = Items.BARRIER;
                    case "mason_jungle" -> hintItem = Items.BARRIER;
                    case "farmer_swamp" -> hintItem = Items.BARRIER;
                    case "fisherman_swamp" -> hintItem = Items.BARRIER;
                    case "shepherd_swamp" -> hintItem = Items.BARRIER;
                    case "fletcher_swamp" -> hintItem = Items.BARRIER;
                    case "librarian_swamp" -> hintItem = Items.BARRIER;
                    case "cartographer_swamp" -> hintItem = Items.BARRIER;
                    case "cleric_swamp" -> hintItem = Items.BARRIER;
                    case "armorer_swamp" -> hintItem = Items.BARRIER;
                    case "weapon_smith_swamp" -> hintItem = Items.BARRIER;
                    case "tool_smith_swamp" -> hintItem = Items.BARRIER;
                    case "butcher_swamp" -> hintItem = Items.BARRIER;
                    case "leatherworker_swamp" -> hintItem = Items.BARRIER;
                    case "mason_swamp" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:challenges/telescopic" -> {
                switch (criterion) {
                    case "bat" -> hintItem = Items.BARRIER;
                    case "chicken" -> hintItem = Items.BARRIER;
                    case "cod" -> hintItem = Items.BARRIER;
                    case "cow" -> hintItem = Items.BARRIER;
                    case "mooshroom" -> hintItem = Items.BARRIER;
                    case "pig" -> hintItem = Items.BARRIER;
                    case "rabbit" -> hintItem = Items.BARRIER;
                    case "salmon" -> hintItem = Items.BARRIER;
                    case "sheep" -> hintItem = Items.BARRIER;
                    case "squid" -> hintItem = Items.BARRIER;
                    case "tropical_fish" -> hintItem = Items.BARRIER;
                    case "turtle" -> hintItem = Items.BARRIER;
                    case "villager" -> hintItem = Items.BARRIER;
                    case "wandering_trader" -> hintItem = Items.BARRIER;
                    case "pufferfish" -> hintItem = Items.BARRIER;
                    case "cave_spider" -> hintItem = Items.BARRIER;
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "panda" -> hintItem = Items.BARRIER;
                    case "polar_bear" -> hintItem = Items.BARRIER;
                    case "spider" -> hintItem = Items.BARRIER;
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "creeper" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                    case "endermite" -> hintItem = Items.BARRIER;
                    case "evoker" -> hintItem = Items.BARRIER;
                    case "ghast" -> hintItem = Items.BARRIER;
                    case "guardian" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "magma_cube" -> hintItem = Items.BARRIER;
                    case "phantom" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "ravager" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "silverfish" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "slime" -> hintItem = Items.BARRIER;
                    case "stray" -> hintItem = Items.BARRIER;
                    case "vex" -> hintItem = Items.BARRIER;
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "witch" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "cat" -> hintItem = Items.BARRIER;
                    case "donkey" -> hintItem = Items.BARRIER;
                    case "horse" -> hintItem = Items.BARRIER;
                    case "llama" -> hintItem = Items.BARRIER;
                    case "mule" -> hintItem = Items.BARRIER;
                    case "parrot" -> hintItem = Items.BARRIER;
                    case "skeleton_horse" -> hintItem = Items.BARRIER;
                    case "trader_llama" -> hintItem = Items.BARRIER;
                    case "wolf" -> hintItem = Items.BARRIER;
                    case "dolphin" -> hintItem = Items.BARRIER;
                    case "fox" -> hintItem = Items.BARRIER;
                    case "ocelot" -> hintItem = Items.BARRIER;
                    case "iron_golem" -> hintItem = Items.BARRIER;
                    case "snow_golem" -> hintItem = Items.BARRIER;
                    case "wither" -> hintItem = Items.BARRIER;
                    case "bee" -> hintItem = Items.BARRIER;
                    case "piglin" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "strider" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
                    case "axolotl" -> hintItem = Items.BARRIER;
                    case "glow_squid" -> hintItem = Items.BARRIER;
                    case "goat" -> hintItem = Items.BARRIER;
                    case "ender_dragon" -> hintItem = Items.BARRIER;
                    case "elder_guardian" -> hintItem = Items.BARRIER;
                    case "frog" -> hintItem = Items.BARRIER;
                    case "tadpole" -> hintItem = Items.BARRIER;
                    case "allay" -> hintItem = Items.BARRIER;
                    case "warden" -> hintItem = Items.BARRIER;
                    case "camel" -> hintItem = Items.BARRIER;
                    case "sniffer" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:challenges/ultimate_enchanter" -> {
                switch (criterion) {
                    case "protection1" -> hintItem = Items.BARRIER;
                    case "protection2" -> hintItem = Items.BARRIER;
                    case "protection3" -> hintItem = Items.BARRIER;
                    case "protection4" -> hintItem = Items.BARRIER;
                    case "fire_protection1" -> hintItem = Items.BARRIER;
                    case "fire_protection2" -> hintItem = Items.BARRIER;
                    case "fire_protection3" -> hintItem = Items.BARRIER;
                    case "fire_protection4" -> hintItem = Items.BARRIER;
                    case "feather_falling1" -> hintItem = Items.BARRIER;
                    case "feather_falling2" -> hintItem = Items.BARRIER;
                    case "feather_falling3" -> hintItem = Items.BARRIER;
                    case "feather_falling4" -> hintItem = Items.BARRIER;
                    case "blast_protection1" -> hintItem = Items.BARRIER;
                    case "blast_protection2" -> hintItem = Items.BARRIER;
                    case "blast_protection3" -> hintItem = Items.BARRIER;
                    case "blast_protection4" -> hintItem = Items.BARRIER;
                    case "projectile_protection1" -> hintItem = Items.BARRIER;
                    case "projectile_protection2" -> hintItem = Items.BARRIER;
                    case "projectile_protection3" -> hintItem = Items.BARRIER;
                    case "projectile_protection4" -> hintItem = Items.BARRIER;
                    case "respiration1" -> hintItem = Items.BARRIER;
                    case "respiration2" -> hintItem = Items.BARRIER;
                    case "respiration3" -> hintItem = Items.BARRIER;
                    case "aqua_affinity" -> hintItem = Items.BARRIER;
                    case "thorns1" -> hintItem = Items.BARRIER;
                    case "thorns2" -> hintItem = Items.BARRIER;
                    case "thorns3" -> hintItem = Items.BARRIER;
                    case "depth_strider1" -> hintItem = Items.BARRIER;
                    case "depth_strider2" -> hintItem = Items.BARRIER;
                    case "depth_strider3" -> hintItem = Items.BARRIER;
                    case "frost_walker1" -> hintItem = Items.BARRIER;
                    case "frost_walker2" -> hintItem = Items.BARRIER;
                    case "binding_curse" -> hintItem = Items.BARRIER;
                    case "sharpness1" -> hintItem = Items.BARRIER;
                    case "sharpness2" -> hintItem = Items.BARRIER;
                    case "sharpness3" -> hintItem = Items.BARRIER;
                    case "sharpness4" -> hintItem = Items.BARRIER;
                    case "sharpness5" -> hintItem = Items.BARRIER;
                    case "smite1" -> hintItem = Items.BARRIER;
                    case "smite2" -> hintItem = Items.BARRIER;
                    case "smite3" -> hintItem = Items.BARRIER;
                    case "smite4" -> hintItem = Items.BARRIER;
                    case "smite5" -> hintItem = Items.BARRIER;
                    case "bane_of_arthropods1" -> hintItem = Items.BARRIER;
                    case "bane_of_arthropods2" -> hintItem = Items.BARRIER;
                    case "bane_of_arthropods3" -> hintItem = Items.BARRIER;
                    case "bane_of_arthropods4" -> hintItem = Items.BARRIER;
                    case "bane_of_arthropods5" -> hintItem = Items.BARRIER;
                    case "knockback1" -> hintItem = Items.BARRIER;
                    case "knockback2" -> hintItem = Items.BARRIER;
                    case "fire_aspect1" -> hintItem = Items.BARRIER;
                    case "fire_aspect2" -> hintItem = Items.BARRIER;
                    case "looting1" -> hintItem = Items.BARRIER;
                    case "looting2" -> hintItem = Items.BARRIER;
                    case "looting3" -> hintItem = Items.BARRIER;
                    case "sweeping1" -> hintItem = Items.BARRIER;
                    case "sweeping2" -> hintItem = Items.BARRIER;
                    case "sweeping3" -> hintItem = Items.BARRIER;
                    case "efficiency1" -> hintItem = Items.BARRIER;
                    case "efficiency2" -> hintItem = Items.BARRIER;
                    case "efficiency3" -> hintItem = Items.BARRIER;
                    case "efficiency4" -> hintItem = Items.BARRIER;
                    case "efficiency5" -> hintItem = Items.BARRIER;
                    case "silk_touch" -> hintItem = Items.BARRIER;
                    case "unbreaking1" -> hintItem = Items.BARRIER;
                    case "unbreaking2" -> hintItem = Items.BARRIER;
                    case "unbreaking3" -> hintItem = Items.BARRIER;
                    case "fortune1" -> hintItem = Items.BARRIER;
                    case "fortune2" -> hintItem = Items.BARRIER;
                    case "fortune3" -> hintItem = Items.BARRIER;
                    case "power1" -> hintItem = Items.BARRIER;
                    case "power2" -> hintItem = Items.BARRIER;
                    case "power3" -> hintItem = Items.BARRIER;
                    case "power4" -> hintItem = Items.BARRIER;
                    case "power5" -> hintItem = Items.BARRIER;
                    case "punch1" -> hintItem = Items.BARRIER;
                    case "punch2" -> hintItem = Items.BARRIER;
                    case "flame" -> hintItem = Items.BARRIER;
                    case "infinity" -> hintItem = Items.BARRIER;
                    case "luck_of_the_sea1" -> hintItem = Items.BARRIER;
                    case "luck_of_the_sea2" -> hintItem = Items.BARRIER;
                    case "luck_of_the_sea3" -> hintItem = Items.BARRIER;
                    case "lure1" -> hintItem = Items.BARRIER;
                    case "lure2" -> hintItem = Items.BARRIER;
                    case "lure3" -> hintItem = Items.BARRIER;
                    case "mending" -> hintItem = Items.BARRIER;
                    case "vanishing_curse" -> hintItem = Items.BARRIER;
                    case "loyalty1" -> hintItem = Items.BARRIER;
                    case "loyalty2" -> hintItem = Items.BARRIER;
                    case "loyalty3" -> hintItem = Items.BARRIER;
                    case "riptide1" -> hintItem = Items.BARRIER;
                    case "riptide2" -> hintItem = Items.BARRIER;
                    case "riptide3" -> hintItem = Items.BARRIER;
                    case "impaling1" -> hintItem = Items.BARRIER;
                    case "impaling2" -> hintItem = Items.BARRIER;
                    case "impaling3" -> hintItem = Items.BARRIER;
                    case "impaling4" -> hintItem = Items.BARRIER;
                    case "impaling5" -> hintItem = Items.BARRIER;
                    case "channeling" -> hintItem = Items.BARRIER;
                    case "quick_charge1" -> hintItem = Items.BARRIER;
                    case "quick_charge2" -> hintItem = Items.BARRIER;
                    case "quick_charge3" -> hintItem = Items.BARRIER;
                    case "piercing1" -> hintItem = Items.BARRIER;
                    case "piercing2" -> hintItem = Items.BARRIER;
                    case "piercing3" -> hintItem = Items.BARRIER;
                    case "piercing4" -> hintItem = Items.BARRIER;
                    case "multishot" -> hintItem = Items.BARRIER;
                    case "soul_speed1" -> hintItem = Items.BARRIER;
                    case "soul_speed2" -> hintItem = Items.BARRIER;
                    case "soul_speed3" -> hintItem = Items.BARRIER;
                    case "swift_sneak1" -> hintItem = Items.BARRIER;
                    case "swift_sneak2" -> hintItem = Items.BARRIER;
                    case "swift_sneak3" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:enchanting/armor_for_the_masses" -> {
                switch (criterion) {
                    case "protection" -> hintItem = Items.BARRIER;
                    case "fire_protection" -> hintItem = Items.BARRIER;
                    case "blast_protection" -> hintItem = Items.BARRIER;
                    case "projectile_protection" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:enchanting/complete_enchanter", "blazeandcave:enchanting/master_enchanter" -> {
                switch (criterion) {
                    case "protection" -> hintItem = Items.BARRIER;
                    case "fire_protection" -> hintItem = Items.BARRIER;
                    case "feather_falling" -> hintItem = Items.BARRIER;
                    case "blast_protection" -> hintItem = Items.BARRIER;
                    case "projectile_protection" -> hintItem = Items.BARRIER;
                    case "respiration" -> hintItem = Items.BARRIER;
                    case "aqua_affinity" -> hintItem = Items.BARRIER;
                    case "thorns" -> hintItem = Items.BARRIER;
                    case "depth_strider" -> hintItem = Items.BARRIER;
                    case "frost_walker" -> hintItem = Items.BARRIER;
                    case "binding_curse" -> hintItem = Items.BARRIER;
                    case "sharpness" -> hintItem = Items.BARRIER;
                    case "smite" -> hintItem = Items.BARRIER;
                    case "bane_of_arthropods" -> hintItem = Items.BARRIER;
                    case "knockback" -> hintItem = Items.BARRIER;
                    case "fire_aspect" -> hintItem = Items.BARRIER;
                    case "looting" -> hintItem = Items.BARRIER;
                    case "sweeping" -> hintItem = Items.BARRIER;
                    case "efficiency" -> hintItem = Items.BARRIER;
                    case "silk_touch" -> hintItem = Items.BARRIER;
                    case "unbreaking" -> hintItem = Items.BARRIER;
                    case "fortune" -> hintItem = Items.BARRIER;
                    case "power" -> hintItem = Items.BARRIER;
                    case "punch" -> hintItem = Items.BARRIER;
                    case "flame" -> hintItem = Items.BARRIER;
                    case "infinity" -> hintItem = Items.BARRIER;
                    case "luck_of_the_sea" -> hintItem = Items.BARRIER;
                    case "lure" -> hintItem = Items.BARRIER;
                    case "mending" -> hintItem = Items.BARRIER;
                    case "vanishing_curse" -> hintItem = Items.BARRIER;
                    case "loyalty" -> hintItem = Items.BARRIER;
                    case "riptide" -> hintItem = Items.BARRIER;
                    case "impaling" -> hintItem = Items.BARRIER;
                    case "channeling" -> hintItem = Items.BARRIER;
                    case "quick_charge" -> hintItem = Items.BARRIER;
                    case "piercing" -> hintItem = Items.BARRIER;
                    case "multishot" -> hintItem = Items.BARRIER;
                    case "soul_speed" -> hintItem = Items.BARRIER;
                    case "swift_sneak" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:enchanting/curses" -> {
                switch (criterion) {
                    case "binding_curse" -> hintItem = Items.BARRIER;
                    case "vanishing_curse" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:enchanting/fiery" -> {
                switch (criterion) {
                    case "fire_protection" -> hintItem = Items.BARRIER;
                    case "fire_aspect" -> hintItem = Items.BARRIER;
                    case "flame" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:enchanting/knocking_your_socks_off" -> {
                switch (criterion) {
                    case "knockback" -> hintItem = Items.BARRIER;
                    case "punch" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:enchanting/master_armorer" -> {
                switch (criterion) {
                    case "helmet_protection" -> hintItem = Items.BARRIER;
                    case "chestplate_protection" -> hintItem = Items.BARRIER;
                    case "leggings_protection" -> hintItem = Items.BARRIER;
                    case "boots_protection_depth_strider" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:enchanting/scuba_gear" -> {
                switch (criterion) {
                    case "respiration" -> hintItem = Items.BARRIER;
                    case "aqua_affinity" -> hintItem = Items.BARRIER;
                    case "depth_strider" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:end/void_walker" -> {
                switch (criterion) {
                    case "the_end" -> hintItem = Items.BARRIER;
                    case "small_end_islands" -> hintItem = Items.BARRIER;
                    case "end_highlands" -> hintItem = Items.BARRIER;
                    case "end_midlands" -> hintItem = Items.BARRIER;
                    case "end_barrens" -> hintItem = Items.BARRIER;
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
            case "blazeandcave:mining/spelunker" -> {
                switch (criterion) {
                    case "lush_caves" -> hintItem = Items.BARRIER;
                    case "dripstone_caves" -> hintItem = Items.BARRIER;
                    case "deep_dark" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:monsters/baby_baby_baby_noo" -> {
                switch (criterion) {
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:monsters/dungeon_crawler" -> {
                switch (criterion) {
                    case "cave_spider" -> hintItem = Items.BARRIER;
                    case "silverfish" -> hintItem = Items.BARRIER;
                    case "guardian" -> hintItem = Items.BARRIER;
                    case "elder_guardian" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "evoker" -> hintItem = Items.BARRIER;
                    case "vex" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
                    case "warden" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:monsters/hell_hunter" -> {
                switch (criterion) {
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "ghast" -> hintItem = Items.BARRIER;
                    case "magma_cube" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "piglin" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:monsters/night_runner" -> {
                switch (criterion) {
                    case "spider" -> hintItem = Items.BARRIER;
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "creeper" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "slime" -> hintItem = Items.BARRIER;
                    case "stray" -> hintItem = Items.BARRIER;
                    case "witch" -> hintItem = Items.BARRIER;
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "phantom" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:monsters/plane_walker", "blazeandcave:nether/lodes_of_applications" -> {
                switch (criterion) {
                    case "overworld" -> hintItem = Items.GRASS_BLOCK;
                    case "nether" -> hintItem = Items.NETHERRACK;
                    case "end" -> hintItem = Items.END_STONE;
                }
            }
            case "blazeandcave:monsters/void_ender" -> {
                switch (criterion) {
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "endermite" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "ender_dragon" -> hintItem = Items.BARRIER;
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
                    hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:potion/a_much_more_doable_challenge" -> {
                switch (criterion) {
                    case "speed" -> hintItem = Items.BARRIER;
                    case "slowness" -> hintItem = Items.BARRIER;
                    case "strength" -> hintItem = Items.BARRIER;
                    case "jump_boost" -> hintItem = Items.BARRIER;
                    case "regeneration" -> hintItem = Items.BARRIER;
                    case "fire_resistance" -> hintItem = Items.BARRIER;
                    case "water_breathing" -> hintItem = Items.BARRIER;
                    case "invisibility" -> hintItem = Items.BARRIER;
                    case "night_vision" -> hintItem = Items.BARRIER;
                    case "weakness" -> hintItem = Items.BARRIER;
                    case "poison" -> hintItem = Items.BARRIER;
                    case "wither" -> hintItem = Items.BARRIER;
                    case "haste" -> hintItem = Items.BARRIER;
                    case "mining_fatigue" -> hintItem = Items.BARRIER;
                    case "levitation" -> hintItem = Items.BARRIER;
                    case "glowing" -> hintItem = Items.BARRIER;
                    case "absorption" -> hintItem = Items.BARRIER;
                    case "hunger" -> hintItem = Items.BARRIER;
                    case "nausea" -> hintItem = Items.BARRIER;
                    case "resistance" -> hintItem = Items.BARRIER;
                    case "slow_falling" -> hintItem = Items.BARRIER;
                    case "conduit_power" -> hintItem = Items.BARRIER;
                    case "dolphins_grace" -> hintItem = Items.BARRIER;
                    case "blindness" -> hintItem = Items.BARRIER;
                    case "saturation" -> hintItem = Items.BARRIER;
                    case "bad_omen" -> hintItem = Items.BARRIER;
                    case "hero_of_the_village" -> hintItem = Items.BARRIER;
                    case "darkness" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:potion/failed_concoctions" -> {
                switch (criterion) {
                    case "thick_potion" -> hintItem = Items.BARRIER;
                    case "mundane_potion" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:potion/furious_ammunition" -> {
                hintItem = Items.TIPPED_ARROW;
                nbt.putString("Potion", new Identifier(criterion).toString());
            }
            case "blazeandcave:potion/gas_bomb" -> {
                switch (criterion) {
                    case "speed" -> hintItem = Items.BARRIER;
                    case "slowness" -> hintItem = Items.BARRIER;
                    case "strength" -> hintItem = Items.BARRIER;
                    case "weakness" -> hintItem = Items.BARRIER;
                    case "jump_boost" -> hintItem = Items.BARRIER;
                    case "regeneration" -> hintItem = Items.BARRIER;
                    case "resistance" -> hintItem = Items.BARRIER;
                    case "fire_resistance" -> hintItem = Items.BARRIER;
                    case "water_breathing" -> hintItem = Items.BARRIER;
                    case "invisibility" -> hintItem = Items.BARRIER;
                    case "night_vision" -> hintItem = Items.BARRIER;
                    case "poison" -> hintItem = Items.BARRIER;
                    case "wither" -> hintItem = Items.BARRIER;
                    case "glowing" -> hintItem = Items.BARRIER;
                    case "levitation" -> hintItem = Items.BARRIER;
                    case "slow_falling" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:potion/mad_scientist" -> {
                switch (criterion) {
                    case "farmer" -> hintItem = Items.BARRIER;
                    case "fisherman" -> hintItem = Items.BARRIER;
                    case "shepherd" -> hintItem = Items.BARRIER;
                    case "fletcher" -> hintItem = Items.BARRIER;
                    case "librarian" -> hintItem = Items.BARRIER;
                    case "cartographer" -> hintItem = Items.BARRIER;
                    case "cleric" -> hintItem = Items.BARRIER;
                    case "armorer" -> hintItem = Items.BARRIER;
                    case "weapon_smith" -> hintItem = Items.BARRIER;
                    case "tool_smith" -> hintItem = Items.BARRIER;
                    case "butcher" -> hintItem = Items.BARRIER;
                    case "leatherworker" -> hintItem = Items.BARRIER;
                    case "mason" -> hintItem = Items.BARRIER;
                    case "nitwit" -> hintItem = Items.BARRIER;
                    case "none" -> hintItem = Items.BARRIER;
                }
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
            case "blazeandcave:redstone/monstrous_sacrifices" -> {
                switch (criterion) {
                    case "minecraft:cave_spider" -> hintItem = Items.BARRIER;
                    case "minecraft:spider" -> hintItem = Items.BARRIER;
                    case "minecraft:zombified_piglin" -> hintItem = Items.BARRIER;
                    case "minecraft:enderman" -> hintItem = Items.BARRIER;
                    case "minecraft:blaze" -> hintItem = Items.BARRIER;
                    case "minecraft:creeper" -> hintItem = Items.BARRIER;
                    case "minecraft:evoker" -> hintItem = Items.BARRIER;
                    case "minecraft:ghast" -> hintItem = Items.BARRIER;
                    case "minecraft:guardian" -> hintItem = Items.BARRIER;
                    case "minecraft:husk" -> hintItem = Items.BARRIER;
                    case "minecraft:magma_cube" -> hintItem = Items.BARRIER;
                    case "minecraft:shulker" -> hintItem = Items.BARRIER;
                    case "minecraft:silverfish" -> hintItem = Items.BARRIER;
                    case "minecraft:skeleton" -> hintItem = Items.BARRIER;
                    case "minecraft:slime" -> hintItem = Items.BARRIER;
                    case "minecraft:stray" -> hintItem = Items.BARRIER;
                    case "minecraft:vindicator" -> hintItem = Items.BARRIER;
                    case "minecraft:witch" -> hintItem = Items.BARRIER;
                    case "minecraft:wither_skeleton" -> hintItem = Items.BARRIER;
                    case "minecraft:zombie" -> hintItem = Items.BARRIER;
                    case "minecraft:zombie_villager" -> hintItem = Items.BARRIER;
                    case "minecraft:phantom" -> hintItem = Items.BARRIER;
                    case "minecraft:drowned" -> hintItem = Items.BARRIER;
                    case "minecraft:pillager" -> hintItem = Items.BARRIER;
                    case "minecraft:ravager" -> hintItem = Items.BARRIER;
                    case "minecraft:endermite" -> hintItem = Items.BARRIER;
                    case "minecraft:vex" -> hintItem = Items.BARRIER;
                    case "minecraft:piglin" -> hintItem = Items.BARRIER;
                    case "minecraft:hoglin" -> hintItem = Items.BARRIER;
                    case "minecraft:zoglin" -> hintItem = Items.BARRIER;
                    case "minecraft:wither" -> hintItem = Items.BARRIER;
                    case "minecraft:piglin_brute" -> hintItem = Items.BARRIER;
                    case "minecraft:warden" -> hintItem = Items.BARRIER;
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
            case "blazeandcave:redstone/travelling_bard" -> {
                switch (criterion) {
                    case "snowy_plains" -> hintItem = Items.BARRIER;
                    case "snowy_taiga" -> hintItem = Items.BARRIER;
                    case "windswept_hills" -> hintItem = Items.BARRIER;
                    case "taiga" -> hintItem = Items.BARRIER;
                    case "old_growth_taiga" -> hintItem = Items.BARRIER;
                    case "plains" -> hintItem = Items.BARRIER;
                    case "forest" -> hintItem = Items.BARRIER;
                    case "birch_forest" -> hintItem = Items.BARRIER;
                    case "dark_forest" -> hintItem = Items.BARRIER;
                    case "swamp" -> hintItem = Items.BARRIER;
                    case "jungle" -> hintItem = Items.BARRIER;
                    case "river" -> hintItem = Items.BARRIER;
                    case "beach" -> hintItem = Items.BARRIER;
                    case "mushroom_fields" -> hintItem = Items.BARRIER;
                    case "desert" -> hintItem = Items.BARRIER;
                    case "savanna" -> hintItem = Items.BARRIER;
                    case "badlands" -> hintItem = Items.BARRIER;
                    case "warm_ocean" -> hintItem = Items.BARRIER;
                    case "lukewarm_ocean" -> hintItem = Items.BARRIER;
                    case "ocean" -> hintItem = Items.BARRIER;
                    case "cold_ocean" -> hintItem = Items.BARRIER;
                    case "frozen_ocean" -> hintItem = Items.BARRIER;
                    case "meadow" -> hintItem = Items.BARRIER;
                    case "snowy_slopes_or_grove" -> hintItem = Items.BARRIER;
                    case "peaks" -> hintItem = Items.BARRIER;
                    case "nether_wastes" -> hintItem = Items.BARRIER;
                    case "soul_sand_valley" -> hintItem = Items.BARRIER;
                    case "crimson_forest" -> hintItem = Items.BARRIER;
                    case "warped_forest" -> hintItem = Items.BARRIER;
                    case "basalt_deltas" -> hintItem = Items.BARRIER;
                    case "the_end" -> hintItem = Items.BARRIER;
                    case "lush_caves" -> hintItem = Items.BARRIER;
                    case "dripstone_caves" -> hintItem = Items.BARRIER;
                    case "deep_dark" -> hintItem = Items.BARRIER;
                    case "cherry_grove" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:weaponry/demolitions_expert" -> {
                switch (criterion) {
                    case "cave_spider" -> hintItem = Items.BARRIER;
                    case "spider" -> hintItem = Items.BARRIER;
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "creeper" -> hintItem = Items.BARRIER;
                    case "ghast" -> hintItem = Items.BARRIER;
                    case "guardian" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "magma_cube" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "silverfish" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "slime" -> hintItem = Items.BARRIER;
                    case "stray" -> hintItem = Items.BARRIER;
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "witch" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "phantom" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                    case "endermite" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "ravager" -> hintItem = Items.BARRIER;
                    case "evoker" -> hintItem = Items.BARRIER;
                    case "vex" -> hintItem = Items.BARRIER;
                    case "piglin" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
                    case "warden" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:weaponry/master_shieldsman" -> {
                switch (criterion) {
                    case "cave_spider" -> hintItem = Items.BARRIER;
                    case "spider" -> hintItem = Items.BARRIER;
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "creeper" -> hintItem = Items.BARRIER;
                    case "ghast" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "magma_cube" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "silverfish" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "slime" -> hintItem = Items.BARRIER;
                    case "stray" -> hintItem = Items.BARRIER;
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "phantom" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "ravager" -> hintItem = Items.BARRIER;
                    case "endermite" -> hintItem = Items.BARRIER;
                    case "vex" -> hintItem = Items.BARRIER;
                    case "piglin" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
                    case "ender_dragon" -> hintItem = Items.BARRIER;
                    case "warden" -> hintItem = Items.BARRIER;
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
            case "blazeandcave:weaponry/poseidon_vs_hades" -> {
                switch (criterion) {
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "ghast" -> hintItem = Items.BARRIER;
                    case "magma_cube" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "wither_skeleton" -> hintItem = Items.BARRIER;
                    case "blaze" -> hintItem = Items.BARRIER;
                    case "piglin" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "strider" -> hintItem = Items.BARRIER;
                    case "zoglin" -> hintItem = Items.BARRIER;
                    case "piglin_brute" -> hintItem = Items.BARRIER;
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
            case "blazeandcave:weaponry/the_aquatic_hunter" -> {
                switch (criterion) {
                    case "guardian" -> hintItem = Items.BARRIER;
                    case "squid" -> hintItem = Items.BARRIER;
                    case "turtle" -> hintItem = Items.BARRIER;
                    case "dolphin" -> hintItem = Items.BARRIER;
                    case "cod_mob" -> hintItem = Items.BARRIER;
                    case "salmon_mob" -> hintItem = Items.BARRIER;
                    case "pufferfish" -> hintItem = Items.BARRIER;
                    case "tropical_fish" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                    case "axolotl" -> hintItem = Items.BARRIER;
                    case "glow_squid" -> hintItem = Items.BARRIER;
                    case "frog" -> hintItem = Items.BARRIER;
                    case "tadpole" -> hintItem = Items.BARRIER;
                }
            }
            case "blazeandcave:weaponry/the_mighty_hunter" -> {
                switch (criterion) {
                    case "chicken" -> hintItem = Items.BARRIER;
                    case "cow" -> hintItem = Items.BARRIER;
                    case "pig" -> hintItem = Items.BARRIER;
                    case "sheep" -> hintItem = Items.BARRIER;
                    case "rabbit" -> hintItem = Items.BARRIER;
                    case "mooshroom" -> hintItem = Items.BARRIER;
                    case "polar_bear" -> hintItem = Items.BARRIER;
                    case "horse" -> hintItem = Items.BARRIER;
                    case "donkey" -> hintItem = Items.BARRIER;
                    case "mule" -> hintItem = Items.BARRIER;
                    case "llama" -> hintItem = Items.BARRIER;
                    case "wolf" -> hintItem = Items.BARRIER;
                    case "ocelot" -> hintItem = Items.BARRIER;
                    case "parrot" -> hintItem = Items.BARRIER;
                    case "cat" -> hintItem = Items.BARRIER;
                    case "panda" -> hintItem = Items.BARRIER;
                    case "fox" -> hintItem = Items.BARRIER;
                    case "bee" -> hintItem = Items.BARRIER;
                    case "hoglin" -> hintItem = Items.BARRIER;
                    case "strider" -> hintItem = Items.BARRIER;
                    case "bat" -> hintItem = Items.BARRIER;
                    case "trader_llama" -> hintItem = Items.BARRIER;
                    case "turtle" -> hintItem = Items.BARRIER;
                    case "goat" -> hintItem = Items.BARRIER;
                    case "frog" -> hintItem = Items.BARRIER;
                    case "camel" -> hintItem = Items.BARRIER;
                    case "sniffer" -> hintItem = Items.BARRIER;
                }
            }
            case "minecraft:adventure/adventuring_time" -> {
                switch (criterion) {
                    case "snowy_plains" -> hintItem = Items.BARRIER;
                    case "snowy_taiga" -> hintItem = Items.BARRIER;
                    case "windswept_hills" -> hintItem = Items.BARRIER;
                    case "taiga" -> hintItem = Items.BARRIER;
                    case "old_growth_taiga" -> hintItem = Items.BARRIER;
                    case "plains" -> hintItem = Items.BARRIER;
                    case "forest" -> hintItem = Items.BARRIER;
                    case "birch_forest" -> hintItem = Items.BARRIER;
                    case "dark_forest" -> hintItem = Items.BARRIER;
                    case "swamp" -> hintItem = Items.BARRIER;
                    case "jungle" -> hintItem = Items.BARRIER;
                    case "river" -> hintItem = Items.BARRIER;
                    case "beach" -> hintItem = Items.BARRIER;
                    case "mushroom_fields" -> hintItem = Items.BARRIER;
                    case "desert" -> hintItem = Items.BARRIER;
                    case "savanna" -> hintItem = Items.BARRIER;
                    case "badlands" -> hintItem = Items.BARRIER;
                    case "warm_ocean" -> hintItem = Items.BARRIER;
                    case "lukewarm_ocean" -> hintItem = Items.BARRIER;
                    case "ocean" -> hintItem = Items.BARRIER;
                    case "cold_ocean" -> hintItem = Items.BARRIER;
                    case "frozen_ocean" -> hintItem = Items.BARRIER;
                    case "meadow" -> hintItem = Items.BARRIER;
                    case "snowy_slopes_or_grove" -> hintItem = Items.BARRIER;
                    case "peaks" -> hintItem = Items.BARRIER;
                    case "cherry_grove" -> hintItem = Items.BARRIER;
                }
            }
            case "minecraft:adventure/kill_all_mobs" -> {
                switch (criterion) {
                    case "minecraft:cave_spider" -> hintItem = Items.BARRIER;
                    case "minecraft:spider" -> hintItem = Items.BARRIER;
                    case "minecraft:zombified_piglin" -> hintItem = Items.BARRIER;
                    case "minecraft:enderman" -> hintItem = Items.BARRIER;
                    case "minecraft:blaze" -> hintItem = Items.BARRIER;
                    case "minecraft:creeper" -> hintItem = Items.BARRIER;
                    case "minecraft:evoker" -> hintItem = Items.BARRIER;
                    case "minecraft:ghast" -> hintItem = Items.BARRIER;
                    case "minecraft:guardian" -> hintItem = Items.BARRIER;
                    case "minecraft:husk" -> hintItem = Items.BARRIER;
                    case "minecraft:magma_cube" -> hintItem = Items.BARRIER;
                    case "minecraft:shulker" -> hintItem = Items.BARRIER;
                    case "minecraft:silverfish" -> hintItem = Items.BARRIER;
                    case "minecraft:skeleton" -> hintItem = Items.BARRIER;
                    case "minecraft:slime" -> hintItem = Items.BARRIER;
                    case "minecraft:stray" -> hintItem = Items.BARRIER;
                    case "minecraft:vindicator" -> hintItem = Items.BARRIER;
                    case "minecraft:witch" -> hintItem = Items.BARRIER;
                    case "minecraft:wither_skeleton" -> hintItem = Items.BARRIER;
                    case "minecraft:zombie" -> hintItem = Items.BARRIER;
                    case "minecraft:zombie_villager" -> hintItem = Items.BARRIER;
                    case "minecraft:phantom" -> hintItem = Items.BARRIER;
                    case "minecraft:drowned" -> hintItem = Items.BARRIER;
                    case "minecraft:pillager" -> hintItem = Items.BARRIER;
                    case "minecraft:ravager" -> hintItem = Items.BARRIER;
                    case "minecraft:endermite" -> hintItem = Items.BARRIER;
                    case "minecraft:vex" -> hintItem = Items.BARRIER;
                    case "minecraft:piglin" -> hintItem = Items.BARRIER;
                    case "minecraft:hoglin" -> hintItem = Items.BARRIER;
                    case "minecraft:zoglin" -> hintItem = Items.BARRIER;
                    case "minecraft:elder_guardian" -> hintItem = Items.BARRIER;
                    case "minecraft:ender_dragon" -> hintItem = Items.BARRIER;
                    case "minecraft:wither" -> hintItem = Items.BARRIER;
                    case "minecraft:piglin_brute" -> hintItem = Items.BARRIER;
                    case "minecraft:warden" -> hintItem = Items.BARRIER;
                }
            }
            case "minecraft:adventure/trim_with_all_exclusive_armor_patterns" -> criterion += "_smithing_template";
            case "minecraft:husbandry/bred_all_animals" -> {
                switch (criterion) {
                    case "minecraft:horse" -> hintItem = Items.BARRIER;
                    case "minecraft:sheep" -> hintItem = Items.BARRIER;
                    case "minecraft:cow" -> hintItem = Items.BARRIER;
                    case "minecraft:mooshroom" -> hintItem = Items.BARRIER;
                    case "minecraft:pig" -> hintItem = Items.BARRIER;
                    case "minecraft:chicken" -> hintItem = Items.BARRIER;
                    case "minecraft:wolf" -> hintItem = Items.BARRIER;
                    case "minecraft:ocelot" -> hintItem = Items.BARRIER;
                    case "minecraft:rabbit" -> hintItem = Items.BARRIER;
                    case "minecraft:llama" -> hintItem = Items.BARRIER;
                    case "minecraft:turtle" -> hintItem = Items.BARRIER;
                    case "minecraft:cat" -> hintItem = Items.BARRIER;
                    case "minecraft:panda" -> hintItem = Items.BARRIER;
                    case "minecraft:fox" -> hintItem = Items.BARRIER;
                    case "minecraft:bee" -> hintItem = Items.BARRIER;
                    case "minecraft:hoglin" -> hintItem = Items.BARRIER;
                    case "minecraft:strider" -> hintItem = Items.BARRIER;
                    case "minecraft:axolotl" -> hintItem = Items.BARRIER;
                    case "minecraft:goat" -> hintItem = Items.BARRIER;
                    case "minecraft:trader_llama" -> hintItem = Items.BARRIER;
                    case "minecraft:donkey" -> hintItem = Items.BARRIER;
                    case "minecraft:mule" -> hintItem = Items.BARRIER;
                    case "minecraft:frog" -> hintItem = Items.BARRIER;
                    case "minecraft:camel" -> hintItem = Items.BARRIER;
                    case "minecraft:sniffer" -> hintItem = Items.BARRIER;
                }
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
                switch (criterion) {
                    case "minecraft:temperate" -> hintItem = Items.BARRIER;
                    case "minecraft:warm" -> hintItem = Items.BARRIER;
                    case "minecraft:cold" -> hintItem = Items.BARRIER;
                }
            }
            case "minecraft:nether/explore_nether" -> {
                switch (criterion) {
                    case "nether_wastes" -> hintItem = Items.BARRIER;
                    case "crimson_forest" -> hintItem = Items.BARRIER;
                    case "warped_forest" -> hintItem = Items.BARRIER;
                    case "soul_sand_valley" -> hintItem = Items.BARRIER;
                    case "basalt_deltas" -> hintItem = Items.BARRIER;
                }
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
