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
            case "blazeandcave:adventure/businessman" -> {
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/chromatic_armory" -> {
                switch (criterion) {
                    case "amethyst" -> hintItem = Items.AMETHYST_SHARD;
                    case "copper" -> hintItem = Items.COPPER_INGOT;
                    case "diamond" -> hintItem = Items.DIAMOND;
                    case "emerald" -> hintItem = Items.EMERALD;
                    case "gold" -> hintItem = Items.GOLD_INGOT;
                    case "iron" -> hintItem = Items.IRON_INGOT;
                    case "lapis" -> hintItem = Items.LAPIS_LAZULI;
                    case "quartz" -> hintItem = Items.QUARTZ;
                    case "netherite" -> hintItem = Items.NETHERITE_INGOT;
                    case "redstone" -> hintItem = Items.REDSTONE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/disc_jockey" -> {
                switch (criterion) {
                    case "music_disc_cat" -> hintItem = Items.MUSIC_DISC_CAT;
                    case "music_disc_13" -> hintItem = Items.MUSIC_DISC_13;
                    case "music_disc_blocks" -> hintItem = Items.MUSIC_DISC_BLOCKS;
                    case "music_disc_chirp" -> hintItem = Items.MUSIC_DISC_CHIRP;
                    case "music_disc_far" -> hintItem = Items.MUSIC_DISC_FAR;
                    case "music_disc_mall" -> hintItem = Items.MUSIC_DISC_MALL;
                    case "music_disc_mellohi" -> hintItem = Items.MUSIC_DISC_MELLOHI;
                    case "music_disc_stal" -> hintItem = Items.MUSIC_DISC_STAL;
                    case "music_disc_strad" -> hintItem = Items.MUSIC_DISC_STRAD;
                    case "music_disc_ward" -> hintItem = Items.MUSIC_DISC_WARD;
                    case "music_disc_11" -> hintItem = Items.MUSIC_DISC_11;
                    case "music_disc_wait" -> hintItem = Items.MUSIC_DISC_WAIT;
                    case "music_disc_pigstep" -> hintItem = Items.MUSIC_DISC_PIGSTEP;
                    case "music_disc_otherside" -> hintItem = Items.MUSIC_DISC_OTHERSIDE;
                    case "music_disc_5" -> hintItem = Items.MUSIC_DISC_5;
                    case "music_disc_relic" -> hintItem = Items.MUSIC_DISC_RELIC;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/feeling_ill" -> {
                switch (criterion) {
                    case "vindicator" -> hintItem = Items.BARRIER;
                    case "pillager" -> hintItem = Items.BARRIER;
                    case "ravager" -> hintItem = Items.BARRIER;
                    case "witch" -> hintItem = Items.BARRIER;
                    case "evoker" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/guardian_destroyer" -> {
                switch (criterion) {
                    case "prismarine_shard" -> hintItem = Items.PRISMARINE_SHARD;
                    case "prismarine_crystals" -> hintItem = Items.PRISMARINE_CRYSTALS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/master_trader" -> {
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/pottery_exhibition" -> {
                switch (criterion) {
                    case "prize_pottery_sherd" -> hintItem = Items.PRIZE_POTTERY_SHERD;
                    case "skull_pottery_sherd" -> hintItem = Items.SKULL_POTTERY_SHERD;
                    case "archer_pottery_sherd" -> hintItem = Items.ARCHER_POTTERY_SHERD;
                    case "arms_up_pottery_sherd" -> hintItem = Items.ARMS_UP_POTTERY_SHERD;
                    case "angler_pottery_sherd" -> hintItem = Items.ANGLER_POTTERY_SHERD;
                    case "blade_pottery_sherd" -> hintItem = Items.BLADE_POTTERY_SHERD;
                    case "brewer_pottery_sherd" -> hintItem = Items.BREWER_POTTERY_SHERD;
                    case "burn_pottery_sherd" -> hintItem = Items.BURN_POTTERY_SHERD;
                    case "danger_pottery_sherd" -> hintItem = Items.DANGER_POTTERY_SHERD;
                    case "explorer_pottery_sherd" -> hintItem = Items.EXPLORER_POTTERY_SHERD;
                    case "friend_pottery_sherd" -> hintItem = Items.FRIEND_POTTERY_SHERD;
                    case "heart_pottery_sherd" -> hintItem = Items.HEART_POTTERY_SHERD;
                    case "heartbreak_pottery_sherd" -> hintItem = Items.HEARTBREAK_POTTERY_SHERD;
                    case "howl_pottery_sherd" -> hintItem = Items.HOWL_POTTERY_SHERD;
                    case "miner_pottery_sherd" -> hintItem = Items.MINER_POTTERY_SHERD;
                    case "mourner_pottery_sherd" -> hintItem = Items.MOURNER_POTTERY_SHERD;
                    case "plenty_pottery_sherd" -> hintItem = Items.PLENTY_POTTERY_SHERD;
                    case "sheaf_pottery_sherd" -> hintItem = Items.SHEAF_POTTERY_SHERD;
                    case "shelter_pottery_sherd" -> hintItem = Items.SHELTER_POTTERY_SHERD;
                    case "snort_pottery_sherd" -> hintItem = Items.SNORT_POTTERY_SHERD;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/retro_future_knight" -> {
                switch (criterion) {
                    case "chainmail_helmet" -> hintItem = Items.CHAINMAIL_HELMET;
                    case "chainmail_chestplate" -> hintItem = Items.CHAINMAIL_CHESTPLATE;
                    case "chainmail_leggings" -> hintItem = Items.CHAINMAIL_LEGGINGS;
                    case "chainmail_boots" -> hintItem = Items.CHAINMAIL_BOOTS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/town_planner" -> {
                switch (criterion) {
                    case "composter" -> hintItem = Items.COMPOSTER;
                    case "barrel" -> hintItem = Items.BARREL;
                    case "loom" -> hintItem = Items.LOOM;
                    case "fletching_table" -> hintItem = Items.FLETCHING_TABLE;
                    case "brewing_stand" -> hintItem = Items.BREWING_STAND;
                    case "smoker" -> hintItem = Items.SMOKER;
                    case "cauldron" -> hintItem = Items.CAULDRON;
                    case "grindstone" -> hintItem = Items.GRINDSTONE;
                    case "blast_furnace" -> hintItem = Items.BLAST_FURNACE;
                    case "smithing_table" -> hintItem = Items.SMITHING_TABLE;
                    case "lectern" -> hintItem = Items.LECTERN;
                    case "cartography_table" -> hintItem = Items.CARTOGRAPHY_TABLE;
                    case "stonecutter" -> hintItem = Items.STONECUTTER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/traveller" -> {
                switch (criterion) {
                    case "plains" -> hintItem = Items.BARRIER;
                    case "desert" -> hintItem = Items.BARRIER;
                    case "savanna" -> hintItem = Items.BARRIER;
                    case "taiga" -> hintItem = Items.BARRIER;
                    case "snow" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/undying_fandom" -> {
                switch (criterion) {
                    case "poppy" -> hintItem = Items.POPPY;
                    case "chainmail_helmet" -> hintItem = Items.CHAINMAIL_HELMET;
                    case "chainmail_chestplate" -> hintItem = Items.CHAINMAIL_CHESTPLATE;
                    case "chainmail_leggings" -> hintItem = Items.CHAINMAIL_LEGGINGS;
                    case "chainmail_boots" -> hintItem = Items.CHAINMAIL_BOOTS;
                    case "cooked_chicken" -> hintItem = Items.COOKED_CHICKEN;
                    case "cooked_mutton" -> hintItem = Items.COOKED_MUTTON;
                    case "cooked_porkchop" -> hintItem = Items.COOKED_PORKCHOP;
                    case "cooked_rabbit" -> hintItem = Items.COOKED_RABBIT;
                    case "cooked_beef" -> hintItem = Items.COOKED_BEEF;
                    case "map" -> hintItem = Items.MAP;
                    case "paper" -> hintItem = Items.PAPER;
                    case "lapis_lazuli" -> hintItem = Items.LAPIS_LAZULI;
                    case "redstone" -> hintItem = Items.REDSTONE;
                    case "bread" -> hintItem = Items.BREAD;
                    case "cookie" -> hintItem = Items.COOKIE;
                    case "pumpkin_pie" -> hintItem = Items.PUMPKIN_PIE;
                    case "cod" -> hintItem = Items.COD;
                    case "salmon" -> hintItem = Items.SALMON;
                    case "arrow" -> hintItem = Items.ARROW;
                    case "tipped_arrow_fire_resistance" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("fire_resistance").toString());
                    }
                    case "tipped_arrow_harming" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("harming").toString());
                    }
                    case "tipped_arrow_healing" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("healing").toString());
                    }
                    case "tipped_arrow_invisibility" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("invisibility").toString());
                    }
                    case "tipped_arrow_leaping" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("leaping").toString());
                    }
                    case "tipped_arrow_night_vision" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("night_vision").toString());
                    }
                    case "tipped_arrow_poison" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("poison").toString());
                    }
                    case "tipped_arrow_regeneration" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("regeneration").toString());
                    }
                    case "tipped_arrow_slowness" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("slowness").toString());
                    }
                    case "tipped_arrow_strength" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("strength").toString());
                    }
                    case "tipped_arrow_swiftness" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("swiftness").toString());
                    }
                    case "tipped_arrow_water_breathing" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("water_breathing").toString());
                    }
                    case "tipped_arrow_weakness" -> {
                        hintItem = Items.TIPPED_ARROW;
                        nbt.putString("Potion", new Identifier("weakness").toString());
                    }
                    case "leather" -> hintItem = Items.LEATHER;
                    case "book" -> hintItem = Items.BOOK;
                    case "clay" -> hintItem = Items.CLAY;
                    case "white_wool" -> hintItem = Items.WHITE_WOOL;
                    case "orange_wool" -> hintItem = Items.ORANGE_WOOL;
                    case "magenta_wool" -> hintItem = Items.MAGENTA_WOOL;
                    case "light_blue_wool" -> hintItem = Items.LIGHT_BLUE_WOOL;
                    case "yellow_wool" -> hintItem = Items.YELLOW_WOOL;
                    case "lime_wool" -> hintItem = Items.LIME_WOOL;
                    case "pink_wool" -> hintItem = Items.PINK_WOOL;
                    case "gray_wool" -> hintItem = Items.GRAY_WOOL;
                    case "light_gray_wool" -> hintItem = Items.LIGHT_GRAY_WOOL;
                    case "cyan_wool" -> hintItem = Items.CYAN_WOOL;
                    case "purple_wool" -> hintItem = Items.PURPLE_WOOL;
                    case "blue_wool" -> hintItem = Items.BLUE_WOOL;
                    case "brown_wool" -> hintItem = Items.BROWN_WOOL;
                    case "green_wool" -> hintItem = Items.GREEN_WOOL;
                    case "red_wool" -> hintItem = Items.RED_WOOL;
                    case "black_wool" -> hintItem = Items.BLACK_WOOL;
                    case "stone_axe" -> hintItem = Items.STONE_AXE;
                    case "stone_hoe" -> hintItem = Items.STONE_HOE;
                    case "stone_pickaxe" -> hintItem = Items.STONE_PICKAXE;
                    case "stone_shovel" -> hintItem = Items.STONE_SHOVEL;
                    case "golden_axe" -> hintItem = Items.GOLDEN_AXE;
                    case "iron_axe" -> hintItem = Items.IRON_AXE;
                    case "wheat_seeds" -> hintItem = Items.WHEAT_SEEDS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:adventure/workaholic" -> {
                switch (criterion) {
                    case "composter" -> hintItem = Items.COMPOSTER;
                    case "barrel" -> hintItem = Items.BARREL;
                    case "loom" -> hintItem = Items.LOOM;
                    case "fletching_table" -> hintItem = Items.FLETCHING_TABLE;
                    case "brewing_stand" -> hintItem = Items.BREWING_STAND;
                    case "smoker" -> hintItem = Items.SMOKER;
                    case "cauldron" -> hintItem = Items.CAULDRON;
                    case "grindstone" -> hintItem = Items.GRINDSTONE;
                    case "blast_furnace" -> hintItem = Items.BLAST_FURNACE;
                    case "smithing_table" -> hintItem = Items.SMITHING_TABLE;
                    case "lectern" -> hintItem = Items.LECTERN;
                    case "cartography_table" -> hintItem = Items.CARTOGRAPHY_TABLE;
                    case "stonecutter" -> hintItem = Items.STONECUTTER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/aquarium" -> {
                switch (criterion) {
                    case "cod_bucket" -> hintItem = Items.COD_BUCKET;
                    case "salmon_bucket" -> hintItem = Items.SALMON_BUCKET;
                    case "pufferfish_bucket" -> hintItem = Items.PUFFERFISH_BUCKET;
                    case "tropical_fish_bucket" -> hintItem = Items.TROPICAL_FISH_BUCKET;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/bunny_lover" -> {
                switch (criterion) {
                    case "brown" -> hintItem = Items.BARRIER;
                    case "white" -> hintItem = Items.BARRIER;
                    case "green" -> hintItem = Items.BARRIER;
                    case "cyan" -> hintItem = Items.BARRIER;
                    case "gray" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/chicken_cooper" -> {
                switch (criterion) {
                    case "chicken" -> hintItem = Items.CHICKEN;
                    case "feather" -> hintItem = Items.FEATHER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/fuzzy_feet", "blazeandcave:animal/llama_festival" -> {
                switch (criterion) {
                    case "white_carpet" -> hintItem = Items.WHITE_CARPET;
                    case "orange_carpet" -> hintItem = Items.ORANGE_CARPET;
                    case "magenta_carpet" -> hintItem = Items.MAGENTA_CARPET;
                    case "light_blue_carpet" -> hintItem = Items.LIGHT_BLUE_CARPET;
                    case "yellow_carpet" -> hintItem = Items.YELLOW_CARPET;
                    case "lime_carpet" -> hintItem = Items.LIME_CARPET;
                    case "pink_carpet" -> hintItem = Items.PINK_CARPET;
                    case "gray_carpet" -> hintItem = Items.GRAY_CARPET;
                    case "light_gray_carpet" -> hintItem = Items.LIGHT_GRAY_CARPET;
                    case "cyan_carpet" -> hintItem = Items.CYAN_CARPET;
                    case "purple_carpet" -> hintItem = Items.PURPLE_CARPET;
                    case "blue_carpet" -> hintItem = Items.BLUE_CARPET;
                    case "brown_carpet" -> hintItem = Items.BROWN_CARPET;
                    case "green_carpet" -> hintItem = Items.GREEN_CARPET;
                    case "red_carpet" -> hintItem = Items.RED_CARPET;
                    case "black_carpet" -> hintItem = Items.BLACK_CARPET;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/in_a_hole_there_lived_a_rabbit" -> {
                switch (criterion) {
                    case "rabbit" -> hintItem = Items.RABBIT;
                    case "rabbit_hide" -> hintItem = Items.RABBIT_HIDE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/live_and_let_dye" -> hintItem = Registries.ITEM.get(new Identifier(criterion));
            case "blazeandcave:animal/llama_llama_duck_king" -> {
                switch (criterion) {
                    case "creamy" -> hintItem = Items.BARRIER;
                    case "white" -> hintItem = Items.BARRIER;
                    case "brown" -> hintItem = Items.BARRIER;
                    case "gray" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/master_angler" -> {
                switch (criterion) {
                    case "cod" -> hintItem = Items.COD;
                    case "salmon" -> hintItem = Items.SALMON;
                    case "pufferfish" -> hintItem = Items.PUFFERFISH;
                    case "tropical_fish" -> hintItem = Items.TROPICAL_FISH;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/rainbow_collection" -> {
                switch (criterion) {
                    case "white_wool" -> hintItem = Items.WHITE_WOOL;
                    case "orange_wool" -> hintItem = Items.ORANGE_WOOL;
                    case "magenta_wool" -> hintItem = Items.MAGENTA_WOOL;
                    case "light_blue_wool" -> hintItem = Items.LIGHT_BLUE_WOOL;
                    case "yellow_wool" -> hintItem = Items.YELLOW_WOOL;
                    case "lime_wool" -> hintItem = Items.LIME_WOOL;
                    case "pink_wool" -> hintItem = Items.PINK_WOOL;
                    case "gray_wool" -> hintItem = Items.GRAY_WOOL;
                    case "light_gray_wool" -> hintItem = Items.LIGHT_GRAY_WOOL;
                    case "cyan_wool" -> hintItem = Items.CYAN_WOOL;
                    case "purple_wool" -> hintItem = Items.PURPLE_WOOL;
                    case "blue_wool" -> hintItem = Items.BLUE_WOOL;
                    case "brown_wool" -> hintItem = Items.BROWN_WOOL;
                    case "green_wool" -> hintItem = Items.GREEN_WOOL;
                    case "red_wool" -> hintItem = Items.RED_WOOL;
                    case "black_wool" -> hintItem = Items.BLACK_WOOL;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/shoe_shed" -> {
                switch (criterion) {
                    case "white" -> hintItem = Items.BARRIER;
                    case "orange" -> hintItem = Items.BARRIER;
                    case "magenta" -> hintItem = Items.BARRIER;
                    case "light_blue" -> hintItem = Items.BARRIER;
                    case "yellow" -> hintItem = Items.BARRIER;
                    case "lime" -> hintItem = Items.BARRIER;
                    case "pink" -> hintItem = Items.BARRIER;
                    case "gray" -> hintItem = Items.BARRIER;
                    case "light_gray" -> hintItem = Items.BARRIER;
                    case "cyan" -> hintItem = Items.BARRIER;
                    case "purple" -> hintItem = Items.BARRIER;
                    case "blue" -> hintItem = Items.BARRIER;
                    case "brown" -> hintItem = Items.BARRIER;
                    case "green" -> hintItem = Items.BARRIER;
                    case "red" -> hintItem = Items.BARRIER;
                    case "black" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/so_hungry_i_could_eat_a_horse" -> {
                switch (criterion) {
                    case "sugar" -> hintItem = Items.SUGAR;
                    case "wheat" -> hintItem = Items.WHEAT;
                    case "apple" -> hintItem = Items.APPLE;
                    case "golden_apple" -> hintItem = Items.GOLDEN_APPLE;
                    case "golden_carrot" -> hintItem = Items.GOLDEN_CARROT;
                    case "hay_block" -> hintItem = Items.HAY_BLOCK;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:animal/totally_not_an_afk_fisher" -> {
                switch (criterion) {
                    case "cod" -> hintItem = Items.COD;
                    case "salmon" -> hintItem = Items.SALMON;
                    case "pufferfish" -> hintItem = Items.PUFFERFISH;
                    case "tropical_fish" -> hintItem = Items.TROPICAL_FISH;
                    case "bowl" -> hintItem = Items.BOWL;
                    case "leather" -> hintItem = Items.LEATHER;
                    case "leather_boots" -> hintItem = Items.LEATHER_BOOTS;
                    case "rotten_flesh" -> hintItem = Items.ROTTEN_FLESH;
                    case "stick" -> hintItem = Items.STICK;
                    case "string" -> hintItem = Items.STRING;
                    case "potion" -> hintItem = Items.POTION;
                    case "bone" -> hintItem = Items.BONE;
                    case "ink_sac" -> hintItem = Items.INK_SAC;
                    case "tripwire_hook" -> hintItem = Items.TRIPWIRE_HOOK;
                    case "bamboo" -> hintItem = Items.BAMBOO;
                    case "bow" -> hintItem = Items.BOW;
                    case "enchanted_book" -> hintItem = Items.ENCHANTED_BOOK;
                    case "name_tag" -> hintItem = Items.NAME_TAG;
                    case "nautilus_shell" -> hintItem = Items.NAUTILUS_SHELL;
                    case "saddle" -> hintItem = Items.SADDLE;
                    case "lily_pad" -> hintItem = Items.LILY_PAD;
                    case "fishing_rod" -> hintItem = Items.FISHING_ROD;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
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
            case "blazeandcave:animal/true_cow_tipper" -> {
                switch (criterion) {
                    case "beef" -> hintItem = Items.BEEF;
                    case "leather" -> hintItem = Items.LEATHER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:biomes/cold_feet" -> {
                switch (criterion) {
                    case "snowy_taiga" -> hintItem = Items.BARRIER;
                    case "snowy_plains" -> hintItem = Items.BARRIER;
                    case "ice_spikes" -> hintItem = Items.BARRIER;
                    case "frozen_river" -> hintItem = Items.BARRIER;
                    case "snowy_beach" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:biomes/everybody_loves_ice" -> {
                switch (criterion) {
                    case "ice" -> hintItem = Items.ICE;
                    case "packed_ice" -> hintItem = Items.PACKED_ICE;
                    case "blue_ice" -> hintItem = Items.BLUE_ICE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:biomes/flower_power" -> {
                switch (criterion) {
                    case "dandelion" -> hintItem = Items.DANDELION;
                    case "poppy" -> hintItem = Items.POPPY;
                    case "blue_orchid" -> hintItem = Items.BLUE_ORCHID;
                    case "allium" -> hintItem = Items.ALLIUM;
                    case "azure_bluet" -> hintItem = Items.AZURE_BLUET;
                    case "red_tulip" -> hintItem = Items.RED_TULIP;
                    case "orange_tulip" -> hintItem = Items.ORANGE_TULIP;
                    case "white_tulip" -> hintItem = Items.WHITE_TULIP;
                    case "pink_tulip" -> hintItem = Items.PINK_TULIP;
                    case "oxeye_daisy" -> hintItem = Items.OXEYE_DAISY;
                    case "sunflower" -> hintItem = Items.SUNFLOWER;
                    case "lilac" -> hintItem = Items.LILAC;
                    case "rose_bush" -> hintItem = Items.ROSE_BUSH;
                    case "peony" -> hintItem = Items.PEONY;
                    case "cornflower" -> hintItem = Items.CORNFLOWER;
                    case "lily_of_the_valley" -> hintItem = Items.LILY_OF_THE_VALLEY;
                    case "wither_rose" -> hintItem = Items.WITHER_ROSE;
                    case "torchflower" -> hintItem = Items.TORCHFLOWER;
                    case "pitcher_plant" -> hintItem = Items.PITCHER_PLANT;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:biomes/no_reefing" -> {
                switch (criterion) {
                    case "tube_coral" -> hintItem = Items.TUBE_CORAL;
                    case "brain_coral" -> hintItem = Items.BRAIN_CORAL;
                    case "bubble_coral" -> hintItem = Items.BUBBLE_CORAL;
                    case "fire_coral" -> hintItem = Items.FIRE_CORAL;
                    case "horn_coral" -> hintItem = Items.HORN_CORAL;
                    case "dead_tube_coral" -> hintItem = Items.DEAD_TUBE_CORAL;
                    case "dead_brain_coral" -> hintItem = Items.DEAD_BRAIN_CORAL;
                    case "dead_bubble_coral" -> hintItem = Items.DEAD_BUBBLE_CORAL;
                    case "dead_fire_coral" -> hintItem = Items.DEAD_FIRE_CORAL;
                    case "dead_horn_coral" -> hintItem = Items.DEAD_HORN_CORAL;
                    case "tube_coral_fan" -> hintItem = Items.TUBE_CORAL_FAN;
                    case "brain_coral_fan" -> hintItem = Items.BRAIN_CORAL_FAN;
                    case "bubble_coral_fan" -> hintItem = Items.BUBBLE_CORAL_FAN;
                    case "fire_coral_fan" -> hintItem = Items.FIRE_CORAL_FAN;
                    case "horn_coral_fan" -> hintItem = Items.HORN_CORAL_FAN;
                    case "dead_tube_coral_fan" -> hintItem = Items.DEAD_TUBE_CORAL_FAN;
                    case "dead_brain_coral_fan" -> hintItem = Items.DEAD_BRAIN_CORAL_FAN;
                    case "dead_bubble_coral_fan" -> hintItem = Items.DEAD_BUBBLE_CORAL_FAN;
                    case "dead_fire_coral_fan" -> hintItem = Items.DEAD_FIRE_CORAL_FAN;
                    case "dead_horn_coral_fan" -> hintItem = Items.DEAD_HORN_CORAL_FAN;
                    case "tube_coral_block" -> hintItem = Items.TUBE_CORAL_BLOCK;
                    case "brain_coral_block" -> hintItem = Items.BRAIN_CORAL_BLOCK;
                    case "bubble_coral_block" -> hintItem = Items.BUBBLE_CORAL_BLOCK;
                    case "fire_coral_block" -> hintItem = Items.FIRE_CORAL_BLOCK;
                    case "horn_coral_block" -> hintItem = Items.HORN_CORAL_BLOCK;
                    case "dead_tube_coral_block" -> hintItem = Items.DEAD_TUBE_CORAL_BLOCK;
                    case "dead_brain_coral_block" -> hintItem = Items.DEAD_BRAIN_CORAL_BLOCK;
                    case "dead_bubble_coral_block" -> hintItem = Items.DEAD_BUBBLE_CORAL_BLOCK;
                    case "dead_fire_coral_block" -> hintItem = Items.DEAD_FIRE_CORAL_BLOCK;
                    case "dead_horn_coral_block" -> hintItem = Items.DEAD_HORN_CORAL_BLOCK;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/ah_my_old_enemy" -> {
                switch (criterion) {
                    case "sandstone_stairs" -> hintItem = Items.SANDSTONE_STAIRS;
                    case "cobblestone_stairs" -> hintItem = Items.COBBLESTONE_STAIRS;
                    case "brick_stairs" -> hintItem = Items.BRICK_STAIRS;
                    case "stone_brick_stairs" -> hintItem = Items.STONE_BRICK_STAIRS;
                    case "nether_brick_stairs" -> hintItem = Items.NETHER_BRICK_STAIRS;
                    case "quartz_stairs" -> hintItem = Items.QUARTZ_STAIRS;
                    case "oak_stairs" -> hintItem = Items.OAK_STAIRS;
                    case "spruce_stairs" -> hintItem = Items.SPRUCE_STAIRS;
                    case "birch_stairs" -> hintItem = Items.BIRCH_STAIRS;
                    case "jungle_stairs" -> hintItem = Items.JUNGLE_STAIRS;
                    case "acacia_stairs" -> hintItem = Items.ACACIA_STAIRS;
                    case "dark_oak_stairs" -> hintItem = Items.DARK_OAK_STAIRS;
                    case "red_sandstone_stairs" -> hintItem = Items.RED_SANDSTONE_STAIRS;
                    case "purpur_stairs" -> hintItem = Items.PURPUR_STAIRS;
                    case "prismarine_stairs" -> hintItem = Items.PRISMARINE_STAIRS;
                    case "prismarine_bricks_stairs" -> hintItem = Items.PRISMARINE_BRICK_STAIRS;
                    case "dark_prismarine_stairs" -> hintItem = Items.DARK_PRISMARINE_STAIRS;
                    case "stone_stairs" -> hintItem = Items.STONE_STAIRS;
                    case "polished_granite_stairs" -> hintItem = Items.POLISHED_GRANITE_STAIRS;
                    case "smooth_red_sandstone_stairs" -> hintItem = Items.SMOOTH_RED_SANDSTONE_STAIRS;
                    case "mossy_stone_brick_stairs" -> hintItem = Items.MOSSY_STONE_BRICK_STAIRS;
                    case "polished_diorite_stairs" -> hintItem = Items.POLISHED_DIORITE_STAIRS;
                    case "mossy_cobblestone_stairs" -> hintItem = Items.MOSSY_COBBLESTONE_STAIRS;
                    case "end_stone_brick_stairs" -> hintItem = Items.END_STONE_BRICK_STAIRS;
                    case "smooth_sandstone_stairs" -> hintItem = Items.SMOOTH_SANDSTONE_STAIRS;
                    case "smooth_quartz_stairs" -> hintItem = Items.SMOOTH_QUARTZ_STAIRS;
                    case "granite_stairs" -> hintItem = Items.GRANITE_STAIRS;
                    case "andesite_stairs" -> hintItem = Items.ANDESITE_STAIRS;
                    case "red_nether_brick_stairs" -> hintItem = Items.RED_NETHER_BRICK_STAIRS;
                    case "polished_andesite_stairs" -> hintItem = Items.POLISHED_ANDESITE_STAIRS;
                    case "diorite_stairs" -> hintItem = Items.DIORITE_STAIRS;
                    case "crimson_stairs" -> hintItem = Items.CRIMSON_STAIRS;
                    case "warped_stairs" -> hintItem = Items.WARPED_STAIRS;
                    case "blackstone_stairs" -> hintItem = Items.BLACKSTONE_STAIRS;
                    case "polished_blackstone_stairs" -> hintItem = Items.POLISHED_BLACKSTONE_STAIRS;
                    case "polished_blackstone_brick_stairs" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_STAIRS;
                    case "cut_copper_stairs" -> hintItem = Items.CUT_COPPER_STAIRS;
                    case "exposed_cut_copper_stairs" -> hintItem = Items.EXPOSED_CUT_COPPER_STAIRS;
                    case "weathered_cut_copper_stairs" -> hintItem = Items.WEATHERED_CUT_COPPER_STAIRS;
                    case "oxidized_cut_copper_stairs" -> hintItem = Items.OXIDIZED_CUT_COPPER_STAIRS;
                    case "waxed_cut_copper_stairs" -> hintItem = Items.WAXED_CUT_COPPER_STAIRS;
                    case "waxed_exposed_cut_copper_stairs" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER_STAIRS;
                    case "waxed_weathered_cut_copper_stairs" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER_STAIRS;
                    case "waxed_oxidized_cut_copper_stairs" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS;
                    case "cobbled_deepslate_stairs" -> hintItem = Items.COBBLED_DEEPSLATE_STAIRS;
                    case "polished_deepslate_stairs" -> hintItem = Items.POLISHED_DEEPSLATE_STAIRS;
                    case "deepslate_brick_stairs" -> hintItem = Items.DEEPSLATE_BRICK_STAIRS;
                    case "deepslate_tile_stairs" -> hintItem = Items.DEEPSLATE_TILE_STAIRS;
                    case "mangrove_stairs" -> hintItem = Items.MANGROVE_STAIRS;
                    case "mud_brick_stairs" -> hintItem = Items.MUD_BRICK_STAIRS;
                    case "bamboo_stairs" -> hintItem = Items.BAMBOO_STAIRS;
                    case "bamboo_mosaic_stairs" -> hintItem = Items.BAMBOO_MOSAIC_STAIRS;
                    case "cherry_stairs" -> hintItem = Items.CHERRY_STAIRS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/armor_display" -> {
                switch (criterion) {
                    case "leather" -> hintItem = Items.LEATHER_CHESTPLATE;
                    case "golden" -> hintItem = Items.GOLDEN_CHESTPLATE;
                    case "chainmail" -> hintItem = Items.CHAINMAIL_CHESTPLATE;
                    case "iron" -> hintItem = Items.IRON_CHESTPLATE;
                    case "diamond" -> hintItem = Items.DIAMOND_CHESTPLATE;
                    case "netherite" -> hintItem = Items.NETHERITE_CHESTPLATE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/art_gallery" -> {
                switch (criterion) {
                    case "kebab" -> hintItem = Items.BARRIER;
                    case "aztec" -> hintItem = Items.BARRIER;
                    case "alban" -> hintItem = Items.BARRIER;
                    case "aztec2" -> hintItem = Items.BARRIER;
                    case "bomb" -> hintItem = Items.BARRIER;
                    case "plant" -> hintItem = Items.BARRIER;
                    case "wasteland" -> hintItem = Items.BARRIER;
                    case "wanderer" -> hintItem = Items.BARRIER;
                    case "graham" -> hintItem = Items.BARRIER;
                    case "pool" -> hintItem = Items.BARRIER;
                    case "courbet" -> hintItem = Items.BARRIER;
                    case "sunset" -> hintItem = Items.BARRIER;
                    case "sea" -> hintItem = Items.BARRIER;
                    case "creebet" -> hintItem = Items.BARRIER;
                    case "match" -> hintItem = Items.BARRIER;
                    case "bust" -> hintItem = Items.BARRIER;
                    case "stage" -> hintItem = Items.BARRIER;
                    case "void" -> hintItem = Items.BARRIER;
                    case "skull_and_roses" -> hintItem = Items.BARRIER;
                    case "wither" -> hintItem = Items.BARRIER;
                    case "fighters" -> hintItem = Items.BARRIER;
                    case "skeleton" -> hintItem = Items.BARRIER;
                    case "donkey_kong" -> hintItem = Items.BARRIER;
                    case "pointer" -> hintItem = Items.BARRIER;
                    case "pigscene" -> hintItem = Items.BARRIER;
                    case "burning_skull" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/bamboozled" -> {
                switch (criterion) {
                    case "bamboo_planks" -> hintItem = Items.BAMBOO_PLANKS;
                    case "bamboo_mosaic" -> hintItem = Items.BAMBOO_MOSAIC;
                    case "bamboo_block" -> hintItem = Items.BAMBOO_BLOCK;
                    case "stripped_bamboo_block" -> hintItem = Items.STRIPPED_BAMBOO_BLOCK;
                    case "bamboo_slab" -> hintItem = Items.BAMBOO_SLAB;
                    case "bamboo_mosaic_slab" -> hintItem = Items.BAMBOO_MOSAIC_SLAB;
                    case "bamboo_pressure_plate" -> hintItem = Items.BAMBOO_PRESSURE_PLATE;
                    case "bamboo_fence" -> hintItem = Items.BAMBOO_FENCE;
                    case "bamboo_trapdoor" -> hintItem = Items.BAMBOO_TRAPDOOR;
                    case "bamboo_fence_gate" -> hintItem = Items.BAMBOO_FENCE_GATE;
                    case "bamboo_stairs" -> hintItem = Items.BAMBOO_STAIRS;
                    case "bamboo_mosaic_stairs" -> hintItem = Items.BAMBOO_MOSAIC_STAIRS;
                    case "bamboo_button" -> hintItem = Items.BAMBOO_BUTTON;
                    case "bamboo_door" -> hintItem = Items.BAMBOO_DOOR;
                    case "bamboo_raft" -> hintItem = Items.BAMBOO_RAFT;
                    case "bamboo_chest_raft" -> hintItem = Items.BAMBOO_CHEST_RAFT;
                    case "bamboo_sign" -> hintItem = Items.BAMBOO_SIGN;
                    case "bamboo_hanging_sign" -> hintItem = Items.BAMBOO_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/blackstonehenge" -> {
                switch (criterion) {
                    case "blackstone" -> hintItem = Items.BLACKSTONE;
                    case "blackstone_slab" -> hintItem = Items.BLACKSTONE_SLAB;
                    case "blackstone_stairs" -> hintItem = Items.BLACKSTONE_STAIRS;
                    case "blackstone_wall" -> hintItem = Items.BLACKSTONE_WALL;
                    case "polished_blackstone" -> hintItem = Items.POLISHED_BLACKSTONE;
                    case "polished_blackstone_slab" -> hintItem = Items.POLISHED_BLACKSTONE_SLAB;
                    case "polished_blackstone_stairs" -> hintItem = Items.POLISHED_BLACKSTONE_STAIRS;
                    case "polished_blackstone_wall" -> hintItem = Items.POLISHED_BLACKSTONE_WALL;
                    case "polished_blackstone_bricks" -> hintItem = Items.POLISHED_BLACKSTONE_BRICKS;
                    case "polished_blackstone_brick_slab" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_SLAB;
                    case "polished_blackstone_brick_stairs" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_STAIRS;
                    case "polished_blackstone_brick_wall" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_WALL;
                    case "cracked_polished_blackstone_bricks" -> hintItem = Items.CRACKED_POLISHED_BLACKSTONE_BRICKS;
                    case "chiseled_polished_blackstone" -> hintItem = Items.CHISELED_POLISHED_BLACKSTONE;
                    case "gilded_blackstone" -> hintItem = Items.GILDED_BLACKSTONE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/built_on_sand_set_in_stone" -> {
                switch (criterion) {
                    case "sandstone" -> hintItem = Items.SANDSTONE;
                    case "sandstone_slab" -> hintItem = Items.SANDSTONE_SLAB;
                    case "sandstone_stairs" -> hintItem = Items.SANDSTONE_STAIRS;
                    case "sandstone_wall" -> hintItem = Items.SANDSTONE_WALL;
                    case "cut_sandstone" -> hintItem = Items.CUT_SANDSTONE;
                    case "cut_sandstone_slab" -> hintItem = Items.CUT_SANDSTONE_SLAB;
                    case "smooth_sandstone" -> hintItem = Items.SMOOTH_SANDSTONE;
                    case "smooth_sandstone_slab" -> hintItem = Items.SMOOTH_SANDSTONE_SLAB;
                    case "smooth_sandstone_stairs" -> hintItem = Items.SMOOTH_SANDSTONE_STAIRS;
                    case "chiseled_sandstone" -> hintItem = Items.CHISELED_SANDSTONE;
                    case "red_sandstone" -> hintItem = Items.RED_SANDSTONE;
                    case "red_sandstone_slab" -> hintItem = Items.RED_SANDSTONE_SLAB;
                    case "red_sandstone_stairs" -> hintItem = Items.RED_SANDSTONE_STAIRS;
                    case "red_sandstone_wall" -> hintItem = Items.RED_SANDSTONE_WALL;
                    case "cut_red_sandstone" -> hintItem = Items.CUT_RED_SANDSTONE;
                    case "cut_red_sandstone_slab" -> hintItem = Items.CUT_RED_SANDSTONE_SLAB;
                    case "smooth_red_sandstone" -> hintItem = Items.SMOOTH_RED_SANDSTONE;
                    case "smooth_red_sandstone_slab" -> hintItem = Items.SMOOTH_RED_SANDSTONE_SLAB;
                    case "smooth_red_sandstone_stairs" -> hintItem = Items.SMOOTH_RED_SANDSTONE_STAIRS;
                    case "chiseled_red_sandstone" -> hintItem = Items.CHISELED_RED_SANDSTONE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/cerulean" -> {
                switch (criterion) {
                    case "soul_torch" -> hintItem = Items.SOUL_TORCH;
                    case "soul_lantern" -> hintItem = Items.SOUL_LANTERN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/cherry_bomb" -> {
                switch (criterion) {
                    case "cherry_planks" -> hintItem = Items.CHERRY_PLANKS;
                    case "cherry_log" -> hintItem = Items.CHERRY_LOG;
                    case "cherry_wood" -> hintItem = Items.CHERRY_WOOD;
                    case "stripped_cherry_log" -> hintItem = Items.STRIPPED_CHERRY_LOG;
                    case "stripped_cherry_wood" -> hintItem = Items.STRIPPED_CHERRY_WOOD;
                    case "cherry_slab" -> hintItem = Items.CHERRY_SLAB;
                    case "cherry_pressure_plate" -> hintItem = Items.CHERRY_PRESSURE_PLATE;
                    case "cherry_fence" -> hintItem = Items.CHERRY_FENCE;
                    case "cherry_trapdoor" -> hintItem = Items.CHERRY_TRAPDOOR;
                    case "cherry_fence_gate" -> hintItem = Items.CHERRY_FENCE_GATE;
                    case "cherry_stairs" -> hintItem = Items.CHERRY_STAIRS;
                    case "cherry_button" -> hintItem = Items.CHERRY_BUTTON;
                    case "cherry_door" -> hintItem = Items.CHERRY_DOOR;
                    case "cherry_boat" -> hintItem = Items.CHERRY_BOAT;
                    case "cherry_chest_boat" -> hintItem = Items.CHERRY_CHEST_BOAT;
                    case "cherry_sign" -> hintItem = Items.CHERRY_SIGN;
                    case "cherry_hanging_sign" -> hintItem = Items.CHERRY_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/colors_of_the_wind" -> {
                switch (criterion) {
                    case "white_dye" -> hintItem = Items.WHITE_DYE;
                    case "orange_dye" -> hintItem = Items.ORANGE_DYE;
                    case "magenta_dye" -> hintItem = Items.MAGENTA_DYE;
                    case "light_blue_dye" -> hintItem = Items.LIGHT_BLUE_DYE;
                    case "yellow_dye" -> hintItem = Items.YELLOW_DYE;
                    case "lime_dye" -> hintItem = Items.LIME_DYE;
                    case "pink_dye" -> hintItem = Items.PINK_DYE;
                    case "gray_dye" -> hintItem = Items.GRAY_DYE;
                    case "light_gray_dye" -> hintItem = Items.LIGHT_GRAY_DYE;
                    case "cyan_dye" -> hintItem = Items.CYAN_DYE;
                    case "purple_dye" -> hintItem = Items.PURPLE_DYE;
                    case "blue_dye" -> hintItem = Items.BLUE_DYE;
                    case "brown_dye" -> hintItem = Items.BROWN_DYE;
                    case "green_dye" -> hintItem = Items.GREEN_DYE;
                    case "red_dye" -> hintItem = Items.RED_DYE;
                    case "black_dye" -> hintItem = Items.BLACK_DYE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/crazy_walls" -> {
                switch (criterion) {
                    case "cobblestone_wall" -> hintItem = Items.COBBLESTONE_WALL;
                    case "mossy_cobblestone_wall" -> hintItem = Items.MOSSY_COBBLESTONE_WALL;
                    case "brick_wall" -> hintItem = Items.BRICK_WALL;
                    case "prismarine_wall" -> hintItem = Items.PRISMARINE_WALL;
                    case "red_sandstone_wall" -> hintItem = Items.RED_SANDSTONE_WALL;
                    case "mossy_stone_brick_wall" -> hintItem = Items.MOSSY_STONE_BRICK_WALL;
                    case "granite_wall" -> hintItem = Items.GRANITE_WALL;
                    case "stone_brick_wall" -> hintItem = Items.STONE_BRICK_WALL;
                    case "nether_brick_wall" -> hintItem = Items.NETHER_BRICK_WALL;
                    case "andesite_wall" -> hintItem = Items.ANDESITE_WALL;
                    case "red_nether_brick_wall" -> hintItem = Items.RED_NETHER_BRICK_WALL;
                    case "sandstone_wall" -> hintItem = Items.SANDSTONE_WALL;
                    case "end_stone_brick_wall" -> hintItem = Items.END_STONE_BRICK_WALL;
                    case "diorite_wall" -> hintItem = Items.DIORITE_WALL;
                    case "blackstone_wall" -> hintItem = Items.BLACKSTONE_WALL;
                    case "polished_blackstone_wall" -> hintItem = Items.POLISHED_BLACKSTONE_WALL;
                    case "polished_blackstone_brick_wall" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_WALL;
                    case "cobbled_deepslate_wall" -> hintItem = Items.COBBLED_DEEPSLATE_WALL;
                    case "polished_deepslate_wall" -> hintItem = Items.POLISHED_DEEPSLATE_WALL;
                    case "deepslate_brick_wall" -> hintItem = Items.DEEPSLATE_BRICK_WALL;
                    case "deepslate_tile_wall" -> hintItem = Items.DEEPSLATE_TILE_WALL;
                    case "mud_brick_wall" -> hintItem = Items.MUD_BRICK_WALL;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/creepers_and_withers" -> {
                switch (criterion) {
                    case "sandstone" -> hintItem = Items.CHISELED_SANDSTONE;
                    case "red_sandstone" -> hintItem = Items.CHISELED_RED_SANDSTONE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/deepslate_conspiracy" -> {
                switch (criterion) {
                    case "deepslate" -> hintItem = Items.DEEPSLATE;
                    case "cobbled_deepslate" -> hintItem = Items.COBBLED_DEEPSLATE;
                    case "cobbled_deepslate_slab" -> hintItem = Items.COBBLED_DEEPSLATE_SLAB;
                    case "cobbled_deepslate_stairs" -> hintItem = Items.COBBLED_DEEPSLATE_STAIRS;
                    case "cobbled_deepslate_wall" -> hintItem = Items.COBBLED_DEEPSLATE_WALL;
                    case "polished_deepslate" -> hintItem = Items.POLISHED_DEEPSLATE;
                    case "polished_deepslate_slab" -> hintItem = Items.POLISHED_DEEPSLATE_SLAB;
                    case "polished_deepslate_stairs" -> hintItem = Items.POLISHED_DEEPSLATE_STAIRS;
                    case "polished_deepslate_wall" -> hintItem = Items.POLISHED_DEEPSLATE_WALL;
                    case "deepslate_bricks" -> hintItem = Items.DEEPSLATE_BRICKS;
                    case "deepslate_brick_slab" -> hintItem = Items.DEEPSLATE_BRICK_SLAB;
                    case "deepslate_brick_stairs" -> hintItem = Items.DEEPSLATE_BRICK_STAIRS;
                    case "deepslate_brick_wall" -> hintItem = Items.DEEPSLATE_BRICK_WALL;
                    case "deepslate_tiles" -> hintItem = Items.DEEPSLATE_TILES;
                    case "deepslate_tile_slab" -> hintItem = Items.DEEPSLATE_TILE_SLAB;
                    case "deepslate_tile_stairs" -> hintItem = Items.DEEPSLATE_TILE_STAIRS;
                    case "deepslate_tile_wall" -> hintItem = Items.DEEPSLATE_TILE_WALL;
                    case "chiseled_deepslate" -> hintItem = Items.CHISELED_DEEPSLATE;
                    case "cracked_deepslate_bricks" -> hintItem = Items.CRACKED_DEEPSLATE_BRICKS;
                    case "cracked_deepslate_tiles" -> hintItem = Items.CRACKED_DEEPSLATE_TILES;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/delicious_hot_schmoes" -> {
                switch (criterion) {
                    case "porkchop" -> hintItem = Items.PORKCHOP;
                    case "beef" -> hintItem = Items.BEEF;
                    case "chicken" -> hintItem = Items.CHICKEN;
                    case "cod" -> hintItem = Items.COD;
                    case "salmon" -> hintItem = Items.SALMON;
                    case "potato" -> hintItem = Items.POTATO;
                    case "mutton" -> hintItem = Items.MUTTON;
                    case "rabbit" -> hintItem = Items.RABBIT;
                    case "kelp" -> hintItem = Items.KELP;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/elmer_mudd" -> {
                switch (criterion) {
                    case "mud" -> hintItem = Items.MUD;
                    case "packed_mud" -> hintItem = Items.PACKED_MUD;
                    case "mud_bricks" -> hintItem = Items.MUD_BRICKS;
                    case "mud_brick_slab" -> hintItem = Items.MUD_BRICK_SLAB;
                    case "mud_brick_stairs" -> hintItem = Items.MUD_BRICK_STAIRS;
                    case "mud_brick_wall" -> hintItem = Items.MUD_BRICK_WALL;
                    case "muddy_mangrove_roots" -> hintItem = Items.MUDDY_MANGROVE_ROOTS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/fake_fortress" -> {
                switch (criterion) {
                    case "nether_brick" -> hintItem = Items.NETHER_BRICK;
                    case "nether_brick_slab" -> hintItem = Items.NETHER_BRICK_SLAB;
                    case "nether_brick_stairs" -> hintItem = Items.NETHER_BRICK_STAIRS;
                    case "nether_brick_fence" -> hintItem = Items.NETHER_BRICK_FENCE;
                    case "nether_brick_wall" -> hintItem = Items.NETHER_BRICK_WALL;
                    case "red_nether_brick" -> hintItem = Items.RED_NETHER_BRICKS;
                    case "red_nether_brick_slab" -> hintItem = Items.RED_NETHER_BRICK_SLAB;
                    case "red_nether_brick_stairs" -> hintItem = Items.RED_NETHER_BRICK_STAIRS;
                    case "red_nether_brick_wall" -> hintItem = Items.RED_NETHER_BRICK_WALL;
                    case "cracked_nether_brick" -> hintItem = Items.CRACKED_NETHER_BRICKS;
                    case "chiseled_nether_bricks" -> hintItem = Items.CHISELED_NETHER_BRICKS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/fake_monument" -> {
                switch (criterion) {
                    case "prismarine" -> hintItem = Items.PRISMARINE;
                    case "prismarine_bricks" -> hintItem = Items.PRISMARINE_BRICKS;
                    case "dark_prismarine" -> hintItem = Items.DARK_PRISMARINE;
                    case "prismarine_slab" -> hintItem = Items.PRISMARINE_SLAB;
                    case "prismarine_brick_slab" -> hintItem = Items.PRISMARINE_BRICK_SLAB;
                    case "dark_prismarine_slab" -> hintItem = Items.DARK_PRISMARINE_SLAB;
                    case "prismarine_stairs" -> hintItem = Items.PRISMARINE_STAIRS;
                    case "prismarine_brick_stairs" -> hintItem = Items.PRISMARINE_BRICK_STAIRS;
                    case "dark_prismarine_stairs" -> hintItem = Items.DARK_PRISMARINE_STAIRS;
                    case "prismarine_wall" -> hintItem = Items.PRISMARINE_WALL;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/fruit_of_the_looms" -> {
                switch (criterion) {
                    case "flower_banner_pattern" -> hintItem = Items.FLOWER_BANNER_PATTERN;
                    case "creeper_banner_pattern" -> hintItem = Items.CREEPER_BANNER_PATTERN;
                    case "skull_banner_pattern" -> hintItem = Items.SKULL_BANNER_PATTERN;
                    case "mojang_banner_pattern" -> hintItem = Items.MOJANG_BANNER_PATTERN;
                    case "globe_banner_pattern" -> hintItem = Items.GLOBE_BANNER_PATTERN;
                    case "piglin_banner_pattern" -> hintItem = Items.PIGLIN_BANNER_PATTERN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/grass_type" -> {
                switch (criterion) {
                    case "grass_block" -> hintItem = Items.GRASS_BLOCK;
                    case "podzol" -> hintItem = Items.PODZOL;
                    case "mycelium" -> hintItem = Items.MYCELIUM;
                    case "crimson_nylium" -> hintItem = Items.CRIMSON_NYLIUM;
                    case "warped_nylium" -> hintItem = Items.WARPED_NYLIUM;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/greek_art_decor" -> {
                switch (criterion) {
                    case "quartz_block" -> hintItem = Items.QUARTZ_BLOCK;
                    case "quartz_chiseled" -> hintItem = Items.CHISELED_QUARTZ_BLOCK;
                    case "quartz_pillar" -> hintItem = Items.QUARTZ_PILLAR;
                    case "quartz_stairs" -> hintItem = Items.QUARTZ_STAIRS;
                    case "quartz_slab" -> hintItem = Items.QUARTZ_SLAB;
                    case "quartz_bricks" -> hintItem = Items.QUARTZ_BRICKS;
                    case "smooth_quartz" -> hintItem = Items.SMOOTH_QUARTZ;
                    case "smooth_quartz_stairs" -> hintItem = Items.SMOOTH_QUARTZ_STAIRS;
                    case "smooth_quartz_slab" -> hintItem = Items.SMOOTH_QUARTZ_SLAB;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/harry_potter" -> {
                switch (criterion) {
                    case "dandelion" -> hintItem = Items.DANDELION;
                    case "poppy" -> hintItem = Items.POPPY;
                    case "blue_orchid" -> hintItem = Items.BLUE_ORCHID;
                    case "allium" -> hintItem = Items.ALLIUM;
                    case "azure_bluet" -> hintItem = Items.AZURE_BLUET;
                    case "red_tulip" -> hintItem = Items.RED_TULIP;
                    case "orange_tulip" -> hintItem = Items.ORANGE_TULIP;
                    case "white_tulip" -> hintItem = Items.WHITE_TULIP;
                    case "pink_tulip" -> hintItem = Items.PINK_TULIP;
                    case "oxeye_daisy" -> hintItem = Items.OXEYE_DAISY;
                    case "cornflower" -> hintItem = Items.CORNFLOWER;
                    case "lily_of_the_valley" -> hintItem = Items.LILY_OF_THE_VALLEY;
                    case "wither_rose" -> hintItem = Items.WITHER_ROSE;
                    case "oak_sapling" -> hintItem = Items.OAK_SAPLING;
                    case "spruce_sapling" -> hintItem = Items.SPRUCE_SAPLING;
                    case "birch_sapling" -> hintItem = Items.BIRCH_SAPLING;
                    case "jungle_sapling" -> hintItem = Items.JUNGLE_SAPLING;
                    case "acacia_sapling" -> hintItem = Items.ACACIA_SAPLING;
                    case "dark_oak_sapling" -> hintItem = Items.DARK_OAK_SAPLING;
                    case "red_mushroom" -> hintItem = Items.RED_MUSHROOM;
                    case "brown_mushroom" -> hintItem = Items.BROWN_MUSHROOM;
                    case "fern" -> hintItem = Items.FERN;
                    case "dead_bush" -> hintItem = Items.DEAD_BUSH;
                    case "cactus" -> hintItem = Items.CACTUS;
                    case "bamboo" -> hintItem = Items.BAMBOO;
                    case "crimson_fungus" -> hintItem = Items.CRIMSON_FUNGUS;
                    case "warped_fungus" -> hintItem = Items.WARPED_FUNGUS;
                    case "crimson_roots" -> hintItem = Items.CRIMSON_ROOTS;
                    case "warped_roots" -> hintItem = Items.WARPED_ROOTS;
                    case "azalea" -> hintItem = Items.AZALEA;
                    case "flowering_azalea" -> hintItem = Items.FLOWERING_AZALEA;
                    case "mangrove_propagule" -> hintItem = Items.MANGROVE_PROPAGULE;
                    case "cherry_sapling" -> hintItem = Items.CHERRY_SAPLING;
                    case "torchflower" -> hintItem = Items.TORCHFLOWER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/let_there_be_light" -> {
                switch (criterion) {
                    case "glowstone" -> hintItem = Items.GLOWSTONE;
                    case "jack_o_lantern" -> hintItem = Items.JACK_O_LANTERN;
                    case "redstone_lamp" -> hintItem = Items.REDSTONE_LAMP;
                    case "sea_lantern" -> hintItem = Items.SEA_LANTERN;
                    case "sea_pickle" -> hintItem = Items.SEA_PICKLE;
                    case "shroomlight" -> hintItem = Items.SHROOMLIGHT;
                    case "lantern" -> hintItem = Items.LANTERN;
                    case "campfire" -> hintItem = Items.CAMPFIRE;
                    case "end_rod" -> hintItem = Items.END_ROD;
                    case "torch" -> hintItem = Items.TORCH;
                    case "soul_lantern" -> hintItem = Items.SOUL_LANTERN;
                    case "soul_torch" -> hintItem = Items.SOUL_TORCH;
                    case "ender_chest" -> hintItem = Items.ENDER_CHEST;
                    case "redstone_torch" -> hintItem = Items.REDSTONE_TORCH;
                    case "magma_block" -> hintItem = Items.MAGMA_BLOCK;
                    case "crying_obsidian" -> hintItem = Items.CRYING_OBSIDIAN;
                    case "soul_campfire" -> hintItem = Items.SOUL_CAMPFIRE;
                    case "amethyst_cluster" -> hintItem = Items.AMETHYST_CLUSTER;
                    case "small_amethyst_bud" -> hintItem = Items.SMALL_AMETHYST_BUD;
                    case "medium_amethyst_bud" -> hintItem = Items.MEDIUM_AMETHYST_BUD;
                    case "large_amethyst_bud" -> hintItem = Items.LARGE_AMETHYST_BUD;
                    case "glow_lichen" -> hintItem = Items.GLOW_LICHEN;
                    case "glow_berries" -> hintItem = Items.GLOW_BERRIES;
                    case "candle" -> hintItem = Items.CANDLE;
                    case "white_candle" -> hintItem = Items.WHITE_CANDLE;
                    case "orange_candle" -> hintItem = Items.ORANGE_CANDLE;
                    case "magenta_candle" -> hintItem = Items.MAGENTA_CANDLE;
                    case "light_blue_candle" -> hintItem = Items.LIGHT_BLUE_CANDLE;
                    case "yellow_candle" -> hintItem = Items.YELLOW_CANDLE;
                    case "lime_candle" -> hintItem = Items.LIME_CANDLE;
                    case "pink_candle" -> hintItem = Items.PINK_CANDLE;
                    case "gray_candle" -> hintItem = Items.GRAY_CANDLE;
                    case "light_gray_candle" -> hintItem = Items.LIGHT_GRAY_CANDLE;
                    case "cyan_candle" -> hintItem = Items.CYAN_CANDLE;
                    case "purple_candle" -> hintItem = Items.PURPLE_CANDLE;
                    case "blue_candle" -> hintItem = Items.BLUE_CANDLE;
                    case "brown_candle" -> hintItem = Items.BROWN_CANDLE;
                    case "green_candle" -> hintItem = Items.GREEN_CANDLE;
                    case "red_candle" -> hintItem = Items.RED_CANDLE;
                    case "black_candle" -> hintItem = Items.BLACK_CANDLE;
                    case "ochre_froglight" -> hintItem = Items.OCHRE_FROGLIGHT;
                    case "verdant_froglight" -> hintItem = Items.VERDANT_FROGLIGHT;
                    case "pearlescent_froglight" -> hintItem = Items.PEARLESCENT_FROGLIGHT;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/mangrove_master" -> {
                switch (criterion) {
                    case "mangrove_planks" -> hintItem = Items.MANGROVE_PLANKS;
                    case "mangrove_log" -> hintItem = Items.MANGROVE_LOG;
                    case "mangrove_wood" -> hintItem = Items.MANGROVE_WOOD;
                    case "stripped_mangrove_log" -> hintItem = Items.STRIPPED_MANGROVE_LOG;
                    case "stripped_mangrove_wood" -> hintItem = Items.STRIPPED_MANGROVE_WOOD;
                    case "mangrove_slab" -> hintItem = Items.MANGROVE_SLAB;
                    case "mangrove_pressure_plate" -> hintItem = Items.MANGROVE_PRESSURE_PLATE;
                    case "mangrove_fence" -> hintItem = Items.MANGROVE_FENCE;
                    case "mangrove_trapdoor" -> hintItem = Items.MANGROVE_TRAPDOOR;
                    case "mangrove_fence_gate" -> hintItem = Items.MANGROVE_FENCE_GATE;
                    case "mangrove_stairs" -> hintItem = Items.MANGROVE_STAIRS;
                    case "mangrove_button" -> hintItem = Items.MANGROVE_BUTTON;
                    case "mangrove_door" -> hintItem = Items.MANGROVE_DOOR;
                    case "mangrove_boat" -> hintItem = Items.MANGROVE_BOAT;
                    case "mangrove_chest_boat" -> hintItem = Items.MANGROVE_CHEST_BOAT;
                    case "mangrove_sign" -> hintItem = Items.MANGROVE_SIGN;
                    case "mangrove_hanging_sign" -> hintItem = Items.MANGROVE_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/master_logger" -> {
                switch (criterion) {
                    case "oak_log" -> hintItem = Items.OAK_LOG;
                    case "spruce_log" -> hintItem = Items.SPRUCE_LOG;
                    case "birch_log" -> hintItem = Items.BIRCH_LOG;
                    case "jungle_log" -> hintItem = Items.JUNGLE_LOG;
                    case "acacia_log" -> hintItem = Items.ACACIA_LOG;
                    case "dark_oak_log" -> hintItem = Items.DARK_OAK_LOG;
                    case "mangrove_log" -> hintItem = Items.MANGROVE_LOG;
                    case "bamboo_block" -> hintItem = Items.BAMBOO_BLOCK;
                    case "cherry_log" -> hintItem = Items.CHERRY_LOG;
                    case "crimson_stem" -> hintItem = Items.CRIMSON_STEM;
                    case "warped_stem" -> hintItem = Items.WARPED_STEM;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/no_banner_only_color" -> {
                switch (criterion) {
                    case "white_banner" -> hintItem = Items.WHITE_BANNER;
                    case "orange_banner" -> hintItem = Items.ORANGE_BANNER;
                    case "magenta_banner" -> hintItem = Items.MAGENTA_BANNER;
                    case "light_blue_banner" -> hintItem = Items.LIGHT_BLUE_BANNER;
                    case "yellow_banner" -> hintItem = Items.YELLOW_BANNER;
                    case "lime_banner" -> hintItem = Items.LIME_BANNER;
                    case "pink_banner" -> hintItem = Items.PINK_BANNER;
                    case "gray_banner" -> hintItem = Items.GRAY_BANNER;
                    case "light_gray_banner" -> hintItem = Items.LIGHT_GRAY_BANNER;
                    case "cyan_banner" -> hintItem = Items.CYAN_BANNER;
                    case "purple_banner" -> hintItem = Items.PURPLE_BANNER;
                    case "blue_banner" -> hintItem = Items.BLUE_BANNER;
                    case "brown_banner" -> hintItem = Items.BROWN_BANNER;
                    case "green_banner" -> hintItem = Items.GREEN_BANNER;
                    case "red_banner" -> hintItem = Items.RED_BANNER;
                    case "black_banner" -> hintItem = Items.BLACK_BANNER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/pane_in_the_glass" -> {
                switch (criterion) {
                    case "white_stained_glass_pane" -> hintItem = Items.WHITE_STAINED_GLASS_PANE;
                    case "orange_stained_glass_pane" -> hintItem = Items.ORANGE_STAINED_GLASS_PANE;
                    case "magenta_stained_glass_pane" -> hintItem = Items.MAGENTA_STAINED_GLASS_PANE;
                    case "light_blue_stained_glass_pane" -> hintItem = Items.LIGHT_BLUE_STAINED_GLASS_PANE;
                    case "yellow_stained_glass_pane" -> hintItem = Items.YELLOW_STAINED_GLASS_PANE;
                    case "lime_stained_glass_pane" -> hintItem = Items.LIME_STAINED_GLASS_PANE;
                    case "pink_stained_glass_pane" -> hintItem = Items.PINK_STAINED_GLASS_PANE;
                    case "gray_stained_glass_pane" -> hintItem = Items.GRAY_STAINED_GLASS_PANE;
                    case "light_gray_stained_glass_pane" -> hintItem = Items.LIGHT_GRAY_STAINED_GLASS_PANE;
                    case "cyan_stained_glass_pane" -> hintItem = Items.CYAN_STAINED_GLASS_PANE;
                    case "purple_stained_glass_pane" -> hintItem = Items.PURPLE_STAINED_GLASS_PANE;
                    case "blue_stained_glass_pane" -> hintItem = Items.BLUE_STAINED_GLASS_PANE;
                    case "brown_stained_glass_pane" -> hintItem = Items.BROWN_STAINED_GLASS_PANE;
                    case "green_stained_glass_pane" -> hintItem = Items.GREEN_STAINED_GLASS_PANE;
                    case "red_stained_glass_pane" -> hintItem = Items.RED_STAINED_GLASS_PANE;
                    case "black_stained_glass_pane" -> hintItem = Items.BLACK_STAINED_GLASS_PANE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/professor_birch" -> {
                switch (criterion) {
                    case "birch_planks" -> hintItem = Items.BIRCH_PLANKS;
                    case "birch_log" -> hintItem = Items.BIRCH_LOG;
                    case "birch_wood" -> hintItem = Items.BIRCH_WOOD;
                    case "stripped_birch_log" -> hintItem = Items.STRIPPED_BIRCH_LOG;
                    case "stripped_birch_wood" -> hintItem = Items.STRIPPED_BIRCH_WOOD;
                    case "birch_slab" -> hintItem = Items.BIRCH_SLAB;
                    case "birch_pressure_plate" -> hintItem = Items.BIRCH_PRESSURE_PLATE;
                    case "birch_fence" -> hintItem = Items.BIRCH_FENCE;
                    case "birch_trapdoor" -> hintItem = Items.BIRCH_TRAPDOOR;
                    case "birch_fence_gate" -> hintItem = Items.BIRCH_FENCE_GATE;
                    case "birch_stairs" -> hintItem = Items.BIRCH_STAIRS;
                    case "birch_button" -> hintItem = Items.BIRCH_BUTTON;
                    case "birch_door" -> hintItem = Items.BIRCH_DOOR;
                    case "birch_boat" -> hintItem = Items.BIRCH_BOAT;
                    case "birch_chest_boat" -> hintItem = Items.BIRCH_CHEST_BOAT;
                    case "birch_sign" -> hintItem = Items.BIRCH_SIGN;
                    case "birch_hanging_sign" -> hintItem = Items.BIRCH_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/professor_dark_oak" -> {
                switch (criterion) {
                    case "dark_oak_planks" -> hintItem = Items.DARK_OAK_PLANKS;
                    case "dark_oak_log" -> hintItem = Items.DARK_OAK_LOG;
                    case "dark_oak_wood" -> hintItem = Items.DARK_OAK_WOOD;
                    case "stripped_dark_oak_log" -> hintItem = Items.STRIPPED_DARK_OAK_LOG;
                    case "stripped_dark_oak_wood" -> hintItem = Items.STRIPPED_DARK_OAK_WOOD;
                    case "dark_oak_slab" -> hintItem = Items.DARK_OAK_SLAB;
                    case "dark_oak_pressure_plate" -> hintItem = Items.DARK_OAK_PRESSURE_PLATE;
                    case "dark_oak_fence" -> hintItem = Items.DARK_OAK_FENCE;
                    case "dark_oak_trapdoor" -> hintItem = Items.DARK_OAK_TRAPDOOR;
                    case "dark_oak_fence_gate" -> hintItem = Items.DARK_OAK_FENCE_GATE;
                    case "dark_oak_stairs" -> hintItem = Items.DARK_OAK_STAIRS;
                    case "dark_oak_button" -> hintItem = Items.DARK_OAK_BUTTON;
                    case "dark_oak_door" -> hintItem = Items.DARK_OAK_DOOR;
                    case "dark_oak_boat" -> hintItem = Items.DARK_OAK_BOAT;
                    case "dark_oak_chest_boat" -> hintItem = Items.DARK_OAK_CHEST_BOAT;
                    case "dark_oak_sign" -> hintItem = Items.DARK_OAK_SIGN;
                    case "dark_oak_hanging_sign" -> hintItem = Items.DARK_OAK_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/professor_oak" -> {
                switch (criterion) {
                    case "oak_planks" -> hintItem = Items.OAK_PLANKS;
                    case "oak_log" -> hintItem = Items.OAK_LOG;
                    case "oak_wood" -> hintItem = Items.OAK_WOOD;
                    case "stripped_oak_log" -> hintItem = Items.STRIPPED_OAK_LOG;
                    case "stripped_oak_wood" -> hintItem = Items.STRIPPED_OAK_WOOD;
                    case "oak_slab" -> hintItem = Items.OAK_SLAB;
                    case "oak_pressure_plate" -> hintItem = Items.OAK_PRESSURE_PLATE;
                    case "oak_fence" -> hintItem = Items.OAK_FENCE;
                    case "oak_trapdoor" -> hintItem = Items.OAK_TRAPDOOR;
                    case "oak_fence_gate" -> hintItem = Items.OAK_FENCE_GATE;
                    case "oak_stairs" -> hintItem = Items.OAK_STAIRS;
                    case "oak_button" -> hintItem = Items.OAK_BUTTON;
                    case "oak_door" -> hintItem = Items.OAK_DOOR;
                    case "oak_boat" -> hintItem = Items.OAK_BOAT;
                    case "oak_chest_boat" -> hintItem = Items.OAK_CHEST_BOAT;
                    case "oak_sign" -> hintItem = Items.OAK_SIGN;
                    case "oak_hanging_sign" -> hintItem = Items.OAK_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/rainbow_dreams" -> {
                switch (criterion) {
                    case "white_bed" -> hintItem = Items.WHITE_BED;
                    case "orange_bed" -> hintItem = Items.ORANGE_BED;
                    case "magenta_bed" -> hintItem = Items.MAGENTA_BED;
                    case "light_blue_bed" -> hintItem = Items.LIGHT_BLUE_BED;
                    case "yellow_bed" -> hintItem = Items.YELLOW_BED;
                    case "lime_bed" -> hintItem = Items.LIME_BED;
                    case "pink_bed" -> hintItem = Items.PINK_BED;
                    case "gray_bed" -> hintItem = Items.GRAY_BED;
                    case "light_gray_bed" -> hintItem = Items.LIGHT_GRAY_BED;
                    case "cyan_bed" -> hintItem = Items.CYAN_BED;
                    case "purple_bed" -> hintItem = Items.PURPLE_BED;
                    case "blue_bed" -> hintItem = Items.BLUE_BED;
                    case "brown_bed" -> hintItem = Items.BROWN_BED;
                    case "green_bed" -> hintItem = Items.GREEN_BED;
                    case "red_bed" -> hintItem = Items.RED_BED;
                    case "black_bed" -> hintItem = Items.BLACK_BED;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/rock_collection" -> {
                switch (criterion) {
                    case "cobblestone" -> hintItem = Items.COBBLESTONE;
                    case "granite" -> hintItem = Items.GRANITE;
                    case "diorite" -> hintItem = Items.DIORITE;
                    case "andesite" -> hintItem = Items.ANDESITE;
                    case "calcite" -> hintItem = Items.CALCITE;
                    case "tuff" -> hintItem = Items.TUFF;
                    case "dripstone_block" -> hintItem = Items.DRIPSTONE_BLOCK;
                    case "cobbled_deepslate" -> hintItem = Items.COBBLED_DEEPSLATE;
                    case "smooth_basalt" -> hintItem = Items.SMOOTH_BASALT;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/rock_polish" -> {
                switch (criterion) {
                    case "polished_granite" -> hintItem = Items.POLISHED_GRANITE;
                    case "polished_diorite" -> hintItem = Items.POLISHED_DIORITE;
                    case "polished_andesite" -> hintItem = Items.POLISHED_ANDESITE;
                    case "polished_deepslate" -> hintItem = Items.POLISHED_DEEPSLATE;
                    case "polished_basalt" -> hintItem = Items.POLISHED_BASALT;
                    case "polished_blackstone" -> hintItem = Items.POLISHED_BLACKSTONE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/slabs_for_days" -> {
                switch (criterion) {
                    case "stone_slab" -> hintItem = Items.STONE_SLAB;
                    case "sandstone_slab" -> hintItem = Items.SANDSTONE_SLAB;
                    case "cobblestone_slab" -> hintItem = Items.COBBLESTONE_SLAB;
                    case "brick_slab" -> hintItem = Items.BRICK_SLAB;
                    case "stone_brick_slab" -> hintItem = Items.STONE_BRICK_SLAB;
                    case "nether_brick_slab" -> hintItem = Items.NETHER_BRICK_SLAB;
                    case "quartz_slab" -> hintItem = Items.QUARTZ_SLAB;
                    case "oak_slab" -> hintItem = Items.OAK_SLAB;
                    case "spruce_slab" -> hintItem = Items.SPRUCE_SLAB;
                    case "birch_slab" -> hintItem = Items.BIRCH_SLAB;
                    case "jungle_slab" -> hintItem = Items.JUNGLE_SLAB;
                    case "acacia_slab" -> hintItem = Items.ACACIA_SLAB;
                    case "dark_oak_slab" -> hintItem = Items.DARK_OAK_SLAB;
                    case "red_sandstone_slab" -> hintItem = Items.RED_SANDSTONE_SLAB;
                    case "purpur_slab" -> hintItem = Items.PURPUR_SLAB;
                    case "prismarine_slab" -> hintItem = Items.PRISMARINE_SLAB;
                    case "prismarine_bricks_slab" -> hintItem = Items.PRISMARINE_BRICK_SLAB;
                    case "dark_prismarine_slab" -> hintItem = Items.DARK_PRISMARINE_SLAB;
                    case "smooth_stone_slab" -> hintItem = Items.SMOOTH_STONE_SLAB;
                    case "polished_granite_slab" -> hintItem = Items.POLISHED_GRANITE_SLAB;
                    case "smooth_red_sandstone_slab" -> hintItem = Items.SMOOTH_RED_SANDSTONE_SLAB;
                    case "mossy_stone_brick_slab" -> hintItem = Items.MOSSY_STONE_BRICK_SLAB;
                    case "polished_diorite_slab" -> hintItem = Items.POLISHED_DIORITE_SLAB;
                    case "mossy_cobblestone_slab" -> hintItem = Items.MOSSY_COBBLESTONE_SLAB;
                    case "end_stone_brick_slab" -> hintItem = Items.END_STONE_BRICK_SLAB;
                    case "smooth_sandstone_slab" -> hintItem = Items.SMOOTH_SANDSTONE_SLAB;
                    case "smooth_quartz_slab" -> hintItem = Items.SMOOTH_QUARTZ_SLAB;
                    case "granite_slab" -> hintItem = Items.GRANITE_SLAB;
                    case "andesite_slab" -> hintItem = Items.ANDESITE_SLAB;
                    case "red_nether_brick_slab" -> hintItem = Items.RED_NETHER_BRICK_SLAB;
                    case "polished_andesite_slab" -> hintItem = Items.POLISHED_ANDESITE_SLAB;
                    case "diorite_slab" -> hintItem = Items.DIORITE_SLAB;
                    case "cut_sandstone_slab" -> hintItem = Items.CUT_SANDSTONE_SLAB;
                    case "cut_red_sandstone_slab" -> hintItem = Items.CUT_RED_SANDSTONE_SLAB;
                    case "crimson_slab" -> hintItem = Items.CRIMSON_SLAB;
                    case "warped_slab" -> hintItem = Items.WARPED_SLAB;
                    case "blackstone_slab" -> hintItem = Items.BLACKSTONE_SLAB;
                    case "polished_blackstone_slab" -> hintItem = Items.POLISHED_BLACKSTONE_SLAB;
                    case "polished_blackstone_brick_slab" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_SLAB;
                    case "cut_copper_slab" -> hintItem = Items.CUT_COPPER_SLAB;
                    case "exposed_cut_copper_slab" -> hintItem = Items.EXPOSED_CUT_COPPER_SLAB;
                    case "weathered_cut_copper_slab" -> hintItem = Items.WEATHERED_CUT_COPPER_SLAB;
                    case "oxidized_cut_copper_slab" -> hintItem = Items.OXIDIZED_CUT_COPPER_SLAB;
                    case "waxed_cut_copper_slab" -> hintItem = Items.WAXED_CUT_COPPER_SLAB;
                    case "waxed_exposed_cut_copper_slab" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER_SLAB;
                    case "waxed_weathered_cut_copper_slab" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER_SLAB;
                    case "waxed_oxidized_cut_copper_slab" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER_SLAB;
                    case "cobbled_deepslate_slab" -> hintItem = Items.COBBLED_DEEPSLATE_SLAB;
                    case "polished_deepslate_slab" -> hintItem = Items.POLISHED_DEEPSLATE_SLAB;
                    case "deepslate_brick_slab" -> hintItem = Items.DEEPSLATE_BRICK_SLAB;
                    case "deepslate_tile_slab" -> hintItem = Items.DEEPSLATE_TILE_SLAB;
                    case "mangrove_slab" -> hintItem = Items.MANGROVE_SLAB;
                    case "mud_brick_slab" -> hintItem = Items.MUD_BRICK_SLAB;
                    case "bamboo_slab" -> hintItem = Items.BAMBOO_SLAB;
                    case "bamboo_mosaic_slab" -> hintItem = Items.BAMBOO_MOSAIC_SLAB;
                    case "cherry_slab" -> hintItem = Items.CHERRY_SLAB;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/spruce_lee" -> {
                switch (criterion) {
                    case "spruce_planks" -> hintItem = Items.SPRUCE_PLANKS;
                    case "spruce_log" -> hintItem = Items.SPRUCE_LOG;
                    case "spruce_wood" -> hintItem = Items.SPRUCE_WOOD;
                    case "stripped_spruce_log" -> hintItem = Items.STRIPPED_SPRUCE_LOG;
                    case "stripped_spruce_wood" -> hintItem = Items.STRIPPED_SPRUCE_WOOD;
                    case "spruce_slab" -> hintItem = Items.SPRUCE_SLAB;
                    case "spruce_pressure_plate" -> hintItem = Items.SPRUCE_PRESSURE_PLATE;
                    case "spruce_fence" -> hintItem = Items.SPRUCE_FENCE;
                    case "spruce_trapdoor" -> hintItem = Items.SPRUCE_TRAPDOOR;
                    case "spruce_fence_gate" -> hintItem = Items.SPRUCE_FENCE_GATE;
                    case "spruce_stairs" -> hintItem = Items.SPRUCE_STAIRS;
                    case "spruce_button" -> hintItem = Items.SPRUCE_BUTTON;
                    case "spruce_door" -> hintItem = Items.SPRUCE_DOOR;
                    case "spruce_boat" -> hintItem = Items.SPRUCE_BOAT;
                    case "spruce_chest_boat" -> hintItem = Items.SPRUCE_CHEST_BOAT;
                    case "spruce_sign" -> hintItem = Items.SPRUCE_SIGN;
                    case "spruce_hanging_sign" -> hintItem = Items.SPRUCE_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/stripper" -> {
                switch (criterion) {
                    case "stripped_oak_log" -> hintItem = Items.STRIPPED_OAK_LOG;
                    case "stripped_spruce_log" -> hintItem = Items.STRIPPED_SPRUCE_LOG;
                    case "stripped_birch_log" -> hintItem = Items.STRIPPED_BIRCH_LOG;
                    case "stripped_jungle_log" -> hintItem = Items.STRIPPED_JUNGLE_LOG;
                    case "stripped_acacia_log" -> hintItem = Items.STRIPPED_ACACIA_LOG;
                    case "stripped_dark_oak_log" -> hintItem = Items.STRIPPED_DARK_OAK_LOG;
                    case "stripped_mangrove_log" -> hintItem = Items.STRIPPED_MANGROVE_LOG;
                    case "stripped_bamboo_block" -> hintItem = Items.STRIPPED_BAMBOO_BLOCK;
                    case "stripped_cherry_log" -> hintItem = Items.STRIPPED_CHERRY_LOG;
                    case "stripped_crimson_stem" -> hintItem = Items.STRIPPED_CRIMSON_STEM;
                    case "stripped_warped_stem" -> hintItem = Items.STRIPPED_WARPED_STEM;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/the_acacia_king" -> {
                switch (criterion) {
                    case "acacia_planks" -> hintItem = Items.ACACIA_PLANKS;
                    case "acacia_log" -> hintItem = Items.ACACIA_LOG;
                    case "acacia_wood" -> hintItem = Items.ACACIA_WOOD;
                    case "stripped_acacia_log" -> hintItem = Items.STRIPPED_ACACIA_LOG;
                    case "stripped_acacia_wood" -> hintItem = Items.STRIPPED_ACACIA_WOOD;
                    case "acacia_slab" -> hintItem = Items.ACACIA_SLAB;
                    case "acacia_pressure_plate" -> hintItem = Items.ACACIA_PRESSURE_PLATE;
                    case "acacia_fence" -> hintItem = Items.ACACIA_FENCE;
                    case "acacia_trapdoor" -> hintItem = Items.ACACIA_TRAPDOOR;
                    case "acacia_fence_gate" -> hintItem = Items.ACACIA_FENCE_GATE;
                    case "acacia_stairs" -> hintItem = Items.ACACIA_STAIRS;
                    case "acacia_button" -> hintItem = Items.ACACIA_BUTTON;
                    case "acacia_door" -> hintItem = Items.ACACIA_DOOR;
                    case "acacia_boat" -> hintItem = Items.ACACIA_BOAT;
                    case "acacia_chest_boat" -> hintItem = Items.ACACIA_CHEST_BOAT;
                    case "acacia_sign" -> hintItem = Items.ACACIA_SIGN;
                    case "acacia_hanging_sign" -> hintItem = Items.ACACIA_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/the_glazed_terracotta_army" -> {
                switch (criterion) {
                    case "white_glazed_terracotta" -> hintItem = Items.WHITE_GLAZED_TERRACOTTA;
                    case "orange_glazed_terracotta" -> hintItem = Items.ORANGE_GLAZED_TERRACOTTA;
                    case "magenta_glazed_terracotta" -> hintItem = Items.MAGENTA_GLAZED_TERRACOTTA;
                    case "light_blue_glazed_terracotta" -> hintItem = Items.LIGHT_BLUE_GLAZED_TERRACOTTA;
                    case "yellow_glazed_terracotta" -> hintItem = Items.YELLOW_GLAZED_TERRACOTTA;
                    case "lime_glazed_terracotta" -> hintItem = Items.LIME_GLAZED_TERRACOTTA;
                    case "pink_glazed_terracotta" -> hintItem = Items.PINK_GLAZED_TERRACOTTA;
                    case "gray_glazed_terracotta" -> hintItem = Items.GRAY_GLAZED_TERRACOTTA;
                    case "light_gray_glazed_terracotta" -> hintItem = Items.LIGHT_GRAY_GLAZED_TERRACOTTA;
                    case "cyan_glazed_terracotta" -> hintItem = Items.CYAN_GLAZED_TERRACOTTA;
                    case "purple_glazed_terracotta" -> hintItem = Items.PURPLE_GLAZED_TERRACOTTA;
                    case "blue_glazed_terracotta" -> hintItem = Items.BLUE_GLAZED_TERRACOTTA;
                    case "brown_glazed_terracotta" -> hintItem = Items.BROWN_GLAZED_TERRACOTTA;
                    case "green_glazed_terracotta" -> hintItem = Items.GREEN_GLAZED_TERRACOTTA;
                    case "red_glazed_terracotta" -> hintItem = Items.RED_GLAZED_TERRACOTTA;
                    case "black_glazed_terracotta" -> hintItem = Items.BLACK_GLAZED_TERRACOTTA;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/the_jungler" -> {
                switch (criterion) {
                    case "jungle_planks" -> hintItem = Items.JUNGLE_PLANKS;
                    case "jungle_log" -> hintItem = Items.JUNGLE_LOG;
                    case "jungle_wood" -> hintItem = Items.JUNGLE_WOOD;
                    case "stripped_jungle_log" -> hintItem = Items.STRIPPED_JUNGLE_LOG;
                    case "stripped_jungle_wood" -> hintItem = Items.STRIPPED_JUNGLE_WOOD;
                    case "jungle_slab" -> hintItem = Items.JUNGLE_SLAB;
                    case "jungle_pressure_plate" -> hintItem = Items.JUNGLE_PRESSURE_PLATE;
                    case "jungle_fence" -> hintItem = Items.JUNGLE_FENCE;
                    case "jungle_trapdoor" -> hintItem = Items.JUNGLE_TRAPDOOR;
                    case "jungle_fence_gate" -> hintItem = Items.JUNGLE_FENCE_GATE;
                    case "jungle_stairs" -> hintItem = Items.JUNGLE_STAIRS;
                    case "jungle_button" -> hintItem = Items.JUNGLE_BUTTON;
                    case "jungle_door" -> hintItem = Items.JUNGLE_DOOR;
                    case "jungle_boat" -> hintItem = Items.JUNGLE_BOAT;
                    case "jungle_chest_boat" -> hintItem = Items.JUNGLE_CHEST_BOAT;
                    case "jungle_sign" -> hintItem = Items.JUNGLE_SIGN;
                    case "jungle_hanging_sign" -> hintItem = Items.JUNGLE_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/the_rainbow_you_always_wanted" -> {
                switch (criterion) {
                    case "white_concrete" -> hintItem = Items.WHITE_CONCRETE;
                    case "orange_concrete" -> hintItem = Items.ORANGE_CONCRETE;
                    case "magenta_concrete" -> hintItem = Items.MAGENTA_CONCRETE;
                    case "light_blue_concrete" -> hintItem = Items.LIGHT_BLUE_CONCRETE;
                    case "yellow_concrete" -> hintItem = Items.YELLOW_CONCRETE;
                    case "lime_concrete" -> hintItem = Items.LIME_CONCRETE;
                    case "pink_concrete" -> hintItem = Items.PINK_CONCRETE;
                    case "gray_concrete" -> hintItem = Items.GRAY_CONCRETE;
                    case "light_gray_concrete" -> hintItem = Items.LIGHT_GRAY_CONCRETE;
                    case "cyan_concrete" -> hintItem = Items.CYAN_CONCRETE;
                    case "purple_concrete" -> hintItem = Items.PURPLE_CONCRETE;
                    case "blue_concrete" -> hintItem = Items.BLUE_CONCRETE;
                    case "brown_concrete" -> hintItem = Items.BROWN_CONCRETE;
                    case "green_concrete" -> hintItem = Items.GREEN_CONCRETE;
                    case "red_concrete" -> hintItem = Items.RED_CONCRETE;
                    case "black_concrete" -> hintItem = Items.BLACK_CONCRETE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/the_terracotta_army" -> {
                switch (criterion) {
                    case "white_terracotta" -> hintItem = Items.WHITE_TERRACOTTA;
                    case "orange_terracotta" -> hintItem = Items.ORANGE_TERRACOTTA;
                    case "magenta_terracotta" -> hintItem = Items.MAGENTA_TERRACOTTA;
                    case "light_blue_terracotta" -> hintItem = Items.LIGHT_BLUE_TERRACOTTA;
                    case "yellow_terracotta" -> hintItem = Items.YELLOW_TERRACOTTA;
                    case "lime_terracotta" -> hintItem = Items.LIME_TERRACOTTA;
                    case "pink_terracotta" -> hintItem = Items.PINK_TERRACOTTA;
                    case "gray_terracotta" -> hintItem = Items.GRAY_TERRACOTTA;
                    case "light_gray_terracotta" -> hintItem = Items.LIGHT_GRAY_TERRACOTTA;
                    case "cyan_terracotta" -> hintItem = Items.CYAN_TERRACOTTA;
                    case "purple_terracotta" -> hintItem = Items.PURPLE_TERRACOTTA;
                    case "blue_terracotta" -> hintItem = Items.BLUE_TERRACOTTA;
                    case "brown_terracotta" -> hintItem = Items.BROWN_TERRACOTTA;
                    case "green_terracotta" -> hintItem = Items.GREEN_TERRACOTTA;
                    case "red_terracotta" -> hintItem = Items.RED_TERRACOTTA;
                    case "black_terracotta" -> hintItem = Items.BLACK_TERRACOTTA;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/translucence" -> {
                switch (criterion) {
                    case "white_stained_glass" -> hintItem = Items.WHITE_STAINED_GLASS;
                    case "orange_stained_glass" -> hintItem = Items.ORANGE_STAINED_GLASS;
                    case "magenta_stained_glass" -> hintItem = Items.MAGENTA_STAINED_GLASS;
                    case "light_blue_stained_glass" -> hintItem = Items.LIGHT_BLUE_STAINED_GLASS;
                    case "yellow_stained_glass" -> hintItem = Items.YELLOW_STAINED_GLASS;
                    case "lime_stained_glass" -> hintItem = Items.LIME_STAINED_GLASS;
                    case "pink_stained_glass" -> hintItem = Items.PINK_STAINED_GLASS;
                    case "gray_stained_glass" -> hintItem = Items.GRAY_STAINED_GLASS;
                    case "light_gray_stained_glass" -> hintItem = Items.LIGHT_GRAY_STAINED_GLASS;
                    case "cyan_stained_glass" -> hintItem = Items.CYAN_STAINED_GLASS;
                    case "purple_stained_glass" -> hintItem = Items.PURPLE_STAINED_GLASS;
                    case "blue_stained_glass" -> hintItem = Items.BLUE_STAINED_GLASS;
                    case "brown_stained_glass" -> hintItem = Items.BROWN_STAINED_GLASS;
                    case "green_stained_glass" -> hintItem = Items.GREEN_STAINED_GLASS;
                    case "red_stained_glass" -> hintItem = Items.RED_STAINED_GLASS;
                    case "black_stained_glass" -> hintItem = Items.BLACK_STAINED_GLASS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/washing_machine" -> {
                switch (criterion) {
                    case "clean_leather_armor" -> hintItem = Items.LEATHER_CHESTPLATE;
                    case "clean_banner" -> hintItem = Items.BLUE_BANNER;
                    case "clean_shulker_box" -> hintItem = Items.BLUE_SHULKER_BOX;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:building/yay_i_got_my_wood" -> {
                switch (criterion) {
                    case "oak_log" -> hintItem = Items.OAK_LOG;
                    case "spruce_log" -> hintItem = Items.SPRUCE_LOG;
                    case "birch_log" -> hintItem = Items.BIRCH_LOG;
                    case "jungle_log" -> hintItem = Items.JUNGLE_LOG;
                    case "acacia_log" -> hintItem = Items.ACACIA_LOG;
                    case "dark_oak_log" -> hintItem = Items.DARK_OAK_LOG;
                    case "mangrove_log" -> hintItem = Items.MANGROVE_LOG;
                    case "bamboo_block" -> hintItem = Items.BAMBOO_BLOCK;
                    case "cherry_log" -> hintItem = Items.CHERRY_LOG;
                    case "crimson_stem" -> hintItem = Items.CRIMSON_STEM;
                    case "warped_stem" -> hintItem = Items.WARPED_STEM;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:challenges/all_the_blocks" -> {
                switch (criterion) {
                    case "acacia_button" -> hintItem = Items.ACACIA_BUTTON;
                    case "acacia_door" -> hintItem = Items.ACACIA_DOOR;
                    case "acacia_fence" -> hintItem = Items.ACACIA_FENCE;
                    case "acacia_fence_gate" -> hintItem = Items.ACACIA_FENCE_GATE;
                    case "acacia_leaves" -> hintItem = Items.ACACIA_LEAVES;
                    case "acacia_log" -> hintItem = Items.ACACIA_LOG;
                    case "acacia_planks" -> hintItem = Items.ACACIA_PLANKS;
                    case "acacia_pressure_plate" -> hintItem = Items.ACACIA_PRESSURE_PLATE;
                    case "acacia_sapling" -> hintItem = Items.ACACIA_SAPLING;
                    case "acacia_sign" -> hintItem = Items.ACACIA_SIGN;
                    case "acacia_slab" -> hintItem = Items.ACACIA_SLAB;
                    case "acacia_stairs" -> hintItem = Items.ACACIA_STAIRS;
                    case "acacia_trapdoor" -> hintItem = Items.ACACIA_TRAPDOOR;
                    case "acacia_wood" -> hintItem = Items.ACACIA_WOOD;
                    case "activator_rail" -> hintItem = Items.ACTIVATOR_RAIL;
                    case "allium" -> hintItem = Items.ALLIUM;
                    case "andesite" -> hintItem = Items.ANDESITE;
                    case "andesite_slab" -> hintItem = Items.ANDESITE_SLAB;
                    case "andesite_stairs" -> hintItem = Items.ANDESITE_STAIRS;
                    case "andesite_wall" -> hintItem = Items.ANDESITE_WALL;
                    case "anvil" -> hintItem = Items.ANVIL;
                    case "azure_bluet" -> hintItem = Items.AZURE_BLUET;
                    case "bamboo" -> hintItem = Items.BAMBOO;
                    case "barrel" -> hintItem = Items.BARREL;
                    case "beacon" -> hintItem = Items.BEACON;
                    case "bell" -> hintItem = Items.BELL;
                    case "birch_button" -> hintItem = Items.BIRCH_BUTTON;
                    case "birch_door" -> hintItem = Items.BIRCH_DOOR;
                    case "birch_fence" -> hintItem = Items.BIRCH_FENCE;
                    case "birch_fence_gate" -> hintItem = Items.BIRCH_FENCE_GATE;
                    case "birch_leaves" -> hintItem = Items.BIRCH_LEAVES;
                    case "birch_log" -> hintItem = Items.BIRCH_LOG;
                    case "birch_planks" -> hintItem = Items.BIRCH_PLANKS;
                    case "birch_pressure_plate" -> hintItem = Items.BIRCH_PRESSURE_PLATE;
                    case "birch_sapling" -> hintItem = Items.BIRCH_SAPLING;
                    case "birch_sign" -> hintItem = Items.BIRCH_SIGN;
                    case "birch_slab" -> hintItem = Items.BIRCH_SLAB;
                    case "birch_stairs" -> hintItem = Items.BIRCH_STAIRS;
                    case "birch_trapdoor" -> hintItem = Items.BIRCH_TRAPDOOR;
                    case "birch_wood" -> hintItem = Items.BIRCH_WOOD;
                    case "black_banner" -> hintItem = Items.BLACK_BANNER;
                    case "black_bed" -> hintItem = Items.BLACK_BED;
                    case "black_carpet" -> hintItem = Items.BLACK_CARPET;
                    case "black_concrete" -> hintItem = Items.BLACK_CONCRETE;
                    case "black_concrete_powder" -> hintItem = Items.BLACK_CONCRETE_POWDER;
                    case "black_glazed_terracotta" -> hintItem = Items.BLACK_GLAZED_TERRACOTTA;
                    case "black_shulker_box" -> hintItem = Items.BLACK_SHULKER_BOX;
                    case "black_stained_glass" -> hintItem = Items.BLACK_STAINED_GLASS;
                    case "black_stained_glass_pane" -> hintItem = Items.BLACK_STAINED_GLASS_PANE;
                    case "black_terracotta" -> hintItem = Items.BLACK_TERRACOTTA;
                    case "black_wool" -> hintItem = Items.BLACK_WOOL;
                    case "blast_furnace" -> hintItem = Items.BLAST_FURNACE;
                    case "coal_block" -> hintItem = Items.COAL_BLOCK;
                    case "diamond_block" -> hintItem = Items.DIAMOND_BLOCK;
                    case "emerald_block" -> hintItem = Items.EMERALD_BLOCK;
                    case "gold_block" -> hintItem = Items.GOLD_BLOCK;
                    case "iron_block" -> hintItem = Items.IRON_BLOCK;
                    case "quartz_block" -> hintItem = Items.QUARTZ_BLOCK;
                    case "redstone_block" -> hintItem = Items.REDSTONE_BLOCK;
                    case "blue_banner" -> hintItem = Items.BLUE_BANNER;
                    case "blue_bed" -> hintItem = Items.BLUE_BED;
                    case "blue_carpet" -> hintItem = Items.BLUE_CARPET;
                    case "blue_concrete" -> hintItem = Items.BLUE_CONCRETE;
                    case "blue_concrete_powder" -> hintItem = Items.BLUE_CONCRETE_POWDER;
                    case "blue_glazed_terracotta" -> hintItem = Items.BLUE_GLAZED_TERRACOTTA;
                    case "blue_ice" -> hintItem = Items.BLUE_ICE;
                    case "blue_orchid" -> hintItem = Items.BLUE_ORCHID;
                    case "blue_shulker_box" -> hintItem = Items.BLUE_SHULKER_BOX;
                    case "blue_stained_glass" -> hintItem = Items.BLUE_STAINED_GLASS;
                    case "blue_stained_glass_pane" -> hintItem = Items.BLUE_STAINED_GLASS_PANE;
                    case "blue_terracotta" -> hintItem = Items.BLUE_TERRACOTTA;
                    case "blue_wool" -> hintItem = Items.BLUE_WOOL;
                    case "bone_block" -> hintItem = Items.BONE_BLOCK;
                    case "bookshelf" -> hintItem = Items.BOOKSHELF;
                    case "brain_coral" -> hintItem = Items.BRAIN_CORAL;
                    case "brain_coral_block" -> hintItem = Items.BRAIN_CORAL_BLOCK;
                    case "brain_coral_fan" -> hintItem = Items.BRAIN_CORAL_FAN;
                    case "brewing_stand" -> hintItem = Items.BREWING_STAND;
                    case "brick_slab" -> hintItem = Items.BRICK_SLAB;
                    case "brick_stairs" -> hintItem = Items.BRICK_STAIRS;
                    case "brick_wall" -> hintItem = Items.BRICK_WALL;
                    case "bricks" -> hintItem = Items.BRICKS;
                    case "brown_banner" -> hintItem = Items.BROWN_BANNER;
                    case "brown_bed" -> hintItem = Items.BROWN_BED;
                    case "brown_carpet" -> hintItem = Items.BROWN_CARPET;
                    case "brown_concrete" -> hintItem = Items.BROWN_CONCRETE;
                    case "brown_concrete_powder" -> hintItem = Items.BROWN_CONCRETE_POWDER;
                    case "brown_glazed_terracotta" -> hintItem = Items.BROWN_GLAZED_TERRACOTTA;
                    case "brown_mushroom" -> hintItem = Items.BROWN_MUSHROOM;
                    case "brown_mushroom_block" -> hintItem = Items.BROWN_MUSHROOM_BLOCK;
                    case "brown_shulker_box" -> hintItem = Items.BROWN_SHULKER_BOX;
                    case "brown_stained_glass" -> hintItem = Items.BROWN_STAINED_GLASS;
                    case "brown_stained_glass_pane" -> hintItem = Items.BROWN_STAINED_GLASS_PANE;
                    case "brown_terracotta" -> hintItem = Items.BROWN_TERRACOTTA;
                    case "brown_wool" -> hintItem = Items.BROWN_WOOL;
                    case "bubble_coral" -> hintItem = Items.BUBBLE_CORAL;
                    case "bubble_coral_block" -> hintItem = Items.BUBBLE_CORAL_BLOCK;
                    case "bubble_coral_fan" -> hintItem = Items.BUBBLE_CORAL_FAN;
                    case "cactus" -> hintItem = Items.CACTUS;
                    case "cake" -> hintItem = Items.CAKE;
                    case "campfire" -> hintItem = Items.CAMPFIRE;
                    case "cartography_table" -> hintItem = Items.CARTOGRAPHY_TABLE;
                    case "carved_pumpkin" -> hintItem = Items.CARVED_PUMPKIN;
                    case "cauldron" -> hintItem = Items.CAULDRON;
                    case "chest" -> hintItem = Items.CHEST;
                    case "chipped_anvil" -> hintItem = Items.CHIPPED_ANVIL;
                    case "chiseled_quartz_block" -> hintItem = Items.CHISELED_QUARTZ_BLOCK;
                    case "chiseled_red_sandstone" -> hintItem = Items.CHISELED_RED_SANDSTONE;
                    case "chiseled_sandstone" -> hintItem = Items.CHISELED_SANDSTONE;
                    case "chiseled_stone_bricks" -> hintItem = Items.CHISELED_STONE_BRICKS;
                    case "chorus_flower" -> hintItem = Items.CHORUS_FLOWER;
                    case "clay" -> hintItem = Items.CLAY;
                    case "coal_ore" -> hintItem = Items.COAL_ORE;
                    case "coarse_dirt" -> hintItem = Items.COARSE_DIRT;
                    case "cobblestone" -> hintItem = Items.COBBLESTONE;
                    case "cobblestone_stairs" -> hintItem = Items.COBBLESTONE_STAIRS;
                    case "cobblestone_slab" -> hintItem = Items.COBBLESTONE_SLAB;
                    case "cobblestone_wall" -> hintItem = Items.COBBLESTONE_WALL;
                    case "cobweb" -> hintItem = Items.COBWEB;
                    case "composter" -> hintItem = Items.COMPOSTER;
                    case "conduit" -> hintItem = Items.CONDUIT;
                    case "cornflower" -> hintItem = Items.CORNFLOWER;
                    case "cracked_stone_bricks" -> hintItem = Items.CRACKED_STONE_BRICKS;
                    case "crafting_table" -> hintItem = Items.CRAFTING_TABLE;
                    case "creeper_head" -> hintItem = Items.CREEPER_HEAD;
                    case "cut_red_sandstone" -> hintItem = Items.CUT_RED_SANDSTONE;
                    case "cut_red_sandstone_slab" -> hintItem = Items.CUT_RED_SANDSTONE_SLAB;
                    case "cut_sandstone" -> hintItem = Items.CUT_SANDSTONE;
                    case "cut_sandstone_slab" -> hintItem = Items.CUT_SANDSTONE_SLAB;
                    case "cyan_banner" -> hintItem = Items.CYAN_BANNER;
                    case "cyan_bed" -> hintItem = Items.CYAN_BED;
                    case "cyan_carpet" -> hintItem = Items.CYAN_CARPET;
                    case "cyan_concrete" -> hintItem = Items.CYAN_CONCRETE;
                    case "cyan_concrete_powder" -> hintItem = Items.CYAN_CONCRETE_POWDER;
                    case "cyan_glazed_terracotta" -> hintItem = Items.CYAN_GLAZED_TERRACOTTA;
                    case "cyan_shulker_box" -> hintItem = Items.CYAN_SHULKER_BOX;
                    case "cyan_stained_glass" -> hintItem = Items.CYAN_STAINED_GLASS;
                    case "cyan_stained_glass_pane" -> hintItem = Items.CYAN_STAINED_GLASS_PANE;
                    case "cyan_terracotta" -> hintItem = Items.CYAN_TERRACOTTA;
                    case "cyan_wool" -> hintItem = Items.CYAN_WOOL;
                    case "damaged_anvil" -> hintItem = Items.DAMAGED_ANVIL;
                    case "dandelion" -> hintItem = Items.DANDELION;
                    case "dark_oak_button" -> hintItem = Items.DARK_OAK_BUTTON;
                    case "dark_oak_door" -> hintItem = Items.DARK_OAK_DOOR;
                    case "dark_oak_fence" -> hintItem = Items.DARK_OAK_FENCE;
                    case "dark_oak_fence_gate" -> hintItem = Items.DARK_OAK_FENCE_GATE;
                    case "dark_oak_leaves" -> hintItem = Items.DARK_OAK_LEAVES;
                    case "dark_oak_log" -> hintItem = Items.DARK_OAK_LOG;
                    case "dark_oak_planks" -> hintItem = Items.DARK_OAK_PLANKS;
                    case "dark_oak_pressure_plate" -> hintItem = Items.DARK_OAK_PRESSURE_PLATE;
                    case "dark_oak_sapling" -> hintItem = Items.DARK_OAK_SAPLING;
                    case "dark_oak_sign" -> hintItem = Items.DARK_OAK_SIGN;
                    case "dark_oak_slab" -> hintItem = Items.DARK_OAK_SLAB;
                    case "dark_oak_stairs" -> hintItem = Items.DARK_OAK_STAIRS;
                    case "dark_oak_trapdoor" -> hintItem = Items.DARK_OAK_TRAPDOOR;
                    case "dark_oak_wood" -> hintItem = Items.DARK_OAK_WOOD;
                    case "dark_prismarine" -> hintItem = Items.DARK_PRISMARINE;
                    case "dark_prismarine_slab" -> hintItem = Items.DARK_PRISMARINE_SLAB;
                    case "dark_prismarine_stairs" -> hintItem = Items.DARK_PRISMARINE_STAIRS;
                    case "daylight_detector" -> hintItem = Items.DAYLIGHT_DETECTOR;
                    case "dead_brain_coral" -> hintItem = Items.DEAD_BRAIN_CORAL;
                    case "dead_brain_coral_block" -> hintItem = Items.DEAD_BRAIN_CORAL_BLOCK;
                    case "dead_brain_coral_fan" -> hintItem = Items.DEAD_BRAIN_CORAL_FAN;
                    case "dead_bubble_coral" -> hintItem = Items.DEAD_BUBBLE_CORAL;
                    case "dead_bubble_coral_block" -> hintItem = Items.DEAD_BUBBLE_CORAL_BLOCK;
                    case "dead_bubble_coral_fan" -> hintItem = Items.DEAD_BUBBLE_CORAL_FAN;
                    case "dead_bush" -> hintItem = Items.DEAD_BUSH;
                    case "dead_fire_coral" -> hintItem = Items.DEAD_FIRE_CORAL;
                    case "dead_fire_coral_block" -> hintItem = Items.DEAD_FIRE_CORAL_BLOCK;
                    case "dead_fire_coral_fan" -> hintItem = Items.DEAD_FIRE_CORAL_FAN;
                    case "dead_horn_coral" -> hintItem = Items.DEAD_HORN_CORAL;
                    case "dead_horn_coral_block" -> hintItem = Items.DEAD_HORN_CORAL_BLOCK;
                    case "dead_horn_coral_fan" -> hintItem = Items.DEAD_HORN_CORAL_FAN;
                    case "dead_tube_coral" -> hintItem = Items.DEAD_TUBE_CORAL;
                    case "dead_tube_coral_block" -> hintItem = Items.DEAD_TUBE_CORAL_BLOCK;
                    case "dead_tube_coral_fan" -> hintItem = Items.DEAD_TUBE_CORAL_FAN;
                    case "detector_rail" -> hintItem = Items.DETECTOR_RAIL;
                    case "diamond_ore" -> hintItem = Items.DIAMOND_ORE;
                    case "diorite" -> hintItem = Items.DIORITE;
                    case "diorite_slab" -> hintItem = Items.DIORITE_SLAB;
                    case "diorite_stairs" -> hintItem = Items.DIORITE_STAIRS;
                    case "diorite_wall" -> hintItem = Items.DIORITE_WALL;
                    case "dirt" -> hintItem = Items.DIRT;
                    case "dispenser" -> hintItem = Items.DISPENSER;
                    case "dragon_egg" -> hintItem = Items.DRAGON_EGG;
                    case "dragon_head" -> hintItem = Items.DRAGON_HEAD;
                    case "dried_kelp_block" -> hintItem = Items.DRIED_KELP_BLOCK;
                    case "dropper" -> hintItem = Items.DROPPER;
                    case "emerald_ore" -> hintItem = Items.EMERALD_ORE;
                    case "enchanting_table" -> hintItem = Items.ENCHANTING_TABLE;
                    case "end_rod" -> hintItem = Items.END_ROD;
                    case "end_stone" -> hintItem = Items.END_STONE;
                    case "end_stone_brick_slab" -> hintItem = Items.END_STONE_BRICK_SLAB;
                    case "end_stone_brick_stairs" -> hintItem = Items.END_STONE_BRICK_STAIRS;
                    case "end_stone_brick_wall" -> hintItem = Items.END_STONE_BRICK_WALL;
                    case "end_stone_bricks" -> hintItem = Items.END_STONE_BRICKS;
                    case "ender_chest" -> hintItem = Items.ENDER_CHEST;
                    case "fern" -> hintItem = Items.FERN;
                    case "fire_coral" -> hintItem = Items.FIRE_CORAL;
                    case "fire_coral_block" -> hintItem = Items.FIRE_CORAL_BLOCK;
                    case "fire_coral_fan" -> hintItem = Items.FIRE_CORAL_FAN;
                    case "fletching_table" -> hintItem = Items.FLETCHING_TABLE;
                    case "flower_pot" -> hintItem = Items.FLOWER_POT;
                    case "furnace" -> hintItem = Items.FURNACE;
                    case "glass" -> hintItem = Items.GLASS;
                    case "glass_pane" -> hintItem = Items.GLASS_PANE;
                    case "glowstone" -> hintItem = Items.GLOWSTONE;
                    case "gold_ore" -> hintItem = Items.GOLD_ORE;
                    case "granite" -> hintItem = Items.GRANITE;
                    case "granite_slab" -> hintItem = Items.GRANITE_SLAB;
                    case "granite_stairs" -> hintItem = Items.GRANITE_STAIRS;
                    case "granite_wall" -> hintItem = Items.GRANITE_WALL;
                    case "grass" -> hintItem = Items.GRASS;
                    case "grass_block" -> hintItem = Items.GRASS_BLOCK;
                    case "gravel" -> hintItem = Items.GRAVEL;
                    case "gray_banner" -> hintItem = Items.GRAY_BANNER;
                    case "gray_bed" -> hintItem = Items.GRAY_BED;
                    case "gray_carpet" -> hintItem = Items.GRAY_CARPET;
                    case "gray_concrete" -> hintItem = Items.GRAY_CONCRETE;
                    case "gray_concrete_powder" -> hintItem = Items.GRAY_CONCRETE_POWDER;
                    case "gray_glazed_terracotta" -> hintItem = Items.GRAY_GLAZED_TERRACOTTA;
                    case "gray_shulker_box" -> hintItem = Items.GRAY_SHULKER_BOX;
                    case "gray_stained_glass" -> hintItem = Items.GRAY_STAINED_GLASS;
                    case "gray_stained_glass_pane" -> hintItem = Items.GRAY_STAINED_GLASS_PANE;
                    case "gray_terracotta" -> hintItem = Items.GRAY_TERRACOTTA;
                    case "gray_wool" -> hintItem = Items.GRAY_WOOL;
                    case "green_banner" -> hintItem = Items.GREEN_BANNER;
                    case "green_bed" -> hintItem = Items.GREEN_BED;
                    case "green_carpet" -> hintItem = Items.GREEN_CARPET;
                    case "green_concrete" -> hintItem = Items.GREEN_CONCRETE;
                    case "green_concrete_powder" -> hintItem = Items.GREEN_CONCRETE_POWDER;
                    case "green_glazed_terracotta" -> hintItem = Items.GREEN_GLAZED_TERRACOTTA;
                    case "green_shulker_box" -> hintItem = Items.GREEN_SHULKER_BOX;
                    case "green_stained_glass" -> hintItem = Items.GREEN_STAINED_GLASS;
                    case "green_stained_glass_pane" -> hintItem = Items.GREEN_STAINED_GLASS_PANE;
                    case "green_terracotta" -> hintItem = Items.GREEN_TERRACOTTA;
                    case "green_wool" -> hintItem = Items.GREEN_WOOL;
                    case "grindstone" -> hintItem = Items.GRINDSTONE;
                    case "hay_block" -> hintItem = Items.HAY_BLOCK;
                    case "heavy_weighted_pressure_plate" -> hintItem = Items.HEAVY_WEIGHTED_PRESSURE_PLATE;
                    case "hopper" -> hintItem = Items.HOPPER;
                    case "horn_coral" -> hintItem = Items.HORN_CORAL;
                    case "horn_coral_block" -> hintItem = Items.HORN_CORAL_BLOCK;
                    case "horn_coral_fan" -> hintItem = Items.HORN_CORAL_FAN;
                    case "ice" -> hintItem = Items.ICE;
                    case "iron_bars" -> hintItem = Items.IRON_BARS;
                    case "iron_door" -> hintItem = Items.IRON_DOOR;
                    case "iron_ore" -> hintItem = Items.IRON_ORE;
                    case "iron_trapdoor" -> hintItem = Items.IRON_TRAPDOOR;
                    case "jack_o_lantern" -> hintItem = Items.JACK_O_LANTERN;
                    case "jukebox" -> hintItem = Items.JUKEBOX;
                    case "jungle_button" -> hintItem = Items.JUNGLE_BUTTON;
                    case "jungle_door" -> hintItem = Items.JUNGLE_DOOR;
                    case "jungle_fence" -> hintItem = Items.JUNGLE_FENCE;
                    case "jungle_fence_gate" -> hintItem = Items.JUNGLE_FENCE_GATE;
                    case "jungle_leaves" -> hintItem = Items.JUNGLE_LEAVES;
                    case "jungle_log" -> hintItem = Items.JUNGLE_LOG;
                    case "jungle_planks" -> hintItem = Items.JUNGLE_PLANKS;
                    case "jungle_pressure_plate" -> hintItem = Items.JUNGLE_PRESSURE_PLATE;
                    case "jungle_sapling" -> hintItem = Items.JUNGLE_SAPLING;
                    case "jungle_sign" -> hintItem = Items.JUNGLE_SIGN;
                    case "jungle_slab" -> hintItem = Items.JUNGLE_SLAB;
                    case "jungle_stairs" -> hintItem = Items.JUNGLE_STAIRS;
                    case "jungle_trapdoor" -> hintItem = Items.JUNGLE_TRAPDOOR;
                    case "jungle_wood" -> hintItem = Items.JUNGLE_WOOD;
                    case "ladder" -> hintItem = Items.LADDER;
                    case "lantern" -> hintItem = Items.LANTERN;
                    case "lapis_block" -> hintItem = Items.LAPIS_BLOCK;
                    case "lapis_ore" -> hintItem = Items.LAPIS_ORE;
                    case "large_fern" -> hintItem = Items.LARGE_FERN;
                    case "tall_grass" -> hintItem = Items.TALL_GRASS;
                    case "lectern" -> hintItem = Items.LECTERN;
                    case "lever" -> hintItem = Items.LEVER;
                    case "light_blue_banner" -> hintItem = Items.LIGHT_BLUE_BANNER;
                    case "light_blue_bed" -> hintItem = Items.LIGHT_BLUE_BED;
                    case "light_blue_carpet" -> hintItem = Items.LIGHT_BLUE_CARPET;
                    case "light_blue_concrete" -> hintItem = Items.LIGHT_BLUE_CONCRETE;
                    case "light_blue_concrete_powder" -> hintItem = Items.LIGHT_BLUE_CONCRETE_POWDER;
                    case "light_blue_glazed_terracotta" -> hintItem = Items.LIGHT_BLUE_GLAZED_TERRACOTTA;
                    case "light_blue_shulker_box" -> hintItem = Items.LIGHT_BLUE_SHULKER_BOX;
                    case "light_blue_stained_glass" -> hintItem = Items.LIGHT_BLUE_STAINED_GLASS;
                    case "light_blue_stained_glass_pane" -> hintItem = Items.LIGHT_BLUE_STAINED_GLASS_PANE;
                    case "light_blue_terracotta" -> hintItem = Items.LIGHT_BLUE_TERRACOTTA;
                    case "light_blue_wool" -> hintItem = Items.LIGHT_BLUE_WOOL;
                    case "light_gray_banner" -> hintItem = Items.LIGHT_GRAY_BANNER;
                    case "light_gray_bed" -> hintItem = Items.LIGHT_GRAY_BED;
                    case "light_gray_carpet" -> hintItem = Items.LIGHT_GRAY_CARPET;
                    case "light_gray_concrete" -> hintItem = Items.LIGHT_GRAY_CONCRETE;
                    case "light_gray_concrete_powder" -> hintItem = Items.LIGHT_GRAY_CONCRETE_POWDER;
                    case "light_gray_glazed_terracotta" -> hintItem = Items.LIGHT_GRAY_GLAZED_TERRACOTTA;
                    case "light_gray_shulker_box" -> hintItem = Items.LIGHT_GRAY_SHULKER_BOX;
                    case "light_gray_stained_glass" -> hintItem = Items.LIGHT_GRAY_STAINED_GLASS;
                    case "light_gray_stained_glass_pane" -> hintItem = Items.LIGHT_GRAY_STAINED_GLASS_PANE;
                    case "light_gray_terracotta" -> hintItem = Items.LIGHT_GRAY_TERRACOTTA;
                    case "light_gray_wool" -> hintItem = Items.LIGHT_GRAY_WOOL;
                    case "light_weighted_pressure_plate" -> hintItem = Items.LIGHT_WEIGHTED_PRESSURE_PLATE;
                    case "lilac" -> hintItem = Items.LILAC;
                    case "lily_pad" -> hintItem = Items.LILY_PAD;
                    case "lily_of_the_valley" -> hintItem = Items.LILY_OF_THE_VALLEY;
                    case "lime_banner" -> hintItem = Items.LIME_BANNER;
                    case "lime_bed" -> hintItem = Items.LIME_BED;
                    case "lime_carpet" -> hintItem = Items.LIME_CARPET;
                    case "lime_concrete" -> hintItem = Items.LIME_CONCRETE;
                    case "lime_concrete_powder" -> hintItem = Items.LIME_CONCRETE_POWDER;
                    case "lime_glazed_terracotta" -> hintItem = Items.LIME_GLAZED_TERRACOTTA;
                    case "lime_shulker_box" -> hintItem = Items.LIME_SHULKER_BOX;
                    case "lime_stained_glass" -> hintItem = Items.LIME_STAINED_GLASS;
                    case "lime_stained_glass_pane" -> hintItem = Items.LIME_STAINED_GLASS_PANE;
                    case "lime_terracotta" -> hintItem = Items.LIME_TERRACOTTA;
                    case "lime_wool" -> hintItem = Items.LIME_WOOL;
                    case "loom" -> hintItem = Items.LOOM;
                    case "magenta_banner" -> hintItem = Items.MAGENTA_BANNER;
                    case "magenta_bed" -> hintItem = Items.MAGENTA_BED;
                    case "magenta_carpet" -> hintItem = Items.MAGENTA_CARPET;
                    case "magenta_concrete" -> hintItem = Items.MAGENTA_CONCRETE;
                    case "magenta_concrete_powder" -> hintItem = Items.MAGENTA_CONCRETE_POWDER;
                    case "magenta_glazed_terracotta" -> hintItem = Items.MAGENTA_GLAZED_TERRACOTTA;
                    case "magenta_shulker_box" -> hintItem = Items.MAGENTA_SHULKER_BOX;
                    case "magenta_stained_glass" -> hintItem = Items.MAGENTA_STAINED_GLASS;
                    case "magenta_stained_glass_pane" -> hintItem = Items.MAGENTA_STAINED_GLASS_PANE;
                    case "magenta_terracotta" -> hintItem = Items.MAGENTA_TERRACOTTA;
                    case "magenta_wool" -> hintItem = Items.MAGENTA_WOOL;
                    case "magma_block" -> hintItem = Items.MAGMA_BLOCK;
                    case "melon" -> hintItem = Items.MELON;
                    case "mossy_cobblestone" -> hintItem = Items.MOSSY_COBBLESTONE;
                    case "mossy_cobblestone_slab" -> hintItem = Items.MOSSY_COBBLESTONE_SLAB;
                    case "mossy_cobblestone_stairs" -> hintItem = Items.MOSSY_COBBLESTONE_STAIRS;
                    case "mossy_cobblestone_wall" -> hintItem = Items.MOSSY_COBBLESTONE_WALL;
                    case "mossy_stone_brick_slab" -> hintItem = Items.MOSSY_STONE_BRICK_SLAB;
                    case "mossy_stone_brick_stairs" -> hintItem = Items.MOSSY_STONE_BRICK_STAIRS;
                    case "mossy_stone_brick_wall" -> hintItem = Items.MOSSY_STONE_BRICK_WALL;
                    case "mossy_stone_bricks" -> hintItem = Items.MOSSY_STONE_BRICKS;
                    case "mushroom_stem" -> hintItem = Items.MUSHROOM_STEM;
                    case "mycelium" -> hintItem = Items.MYCELIUM;
                    case "nether_brick_fence" -> hintItem = Items.NETHER_BRICK_FENCE;
                    case "nether_brick_slab" -> hintItem = Items.NETHER_BRICK_SLAB;
                    case "nether_brick_stairs" -> hintItem = Items.NETHER_BRICK_STAIRS;
                    case "nether_brick_wall" -> hintItem = Items.NETHER_BRICK_WALL;
                    case "nether_bricks" -> hintItem = Items.NETHER_BRICKS;
                    case "nether_quartz_ore" -> hintItem = Items.NETHER_QUARTZ_ORE;
                    case "nether_wart_block" -> hintItem = Items.NETHER_WART_BLOCK;
                    case "netherrack" -> hintItem = Items.NETHERRACK;
                    case "note_block" -> hintItem = Items.NOTE_BLOCK;
                    case "oak_button" -> hintItem = Items.OAK_BUTTON;
                    case "oak_door" -> hintItem = Items.OAK_DOOR;
                    case "oak_fence" -> hintItem = Items.OAK_FENCE;
                    case "oak_fence_gate" -> hintItem = Items.OAK_FENCE_GATE;
                    case "oak_leaves" -> hintItem = Items.OAK_LEAVES;
                    case "oak_log" -> hintItem = Items.OAK_LOG;
                    case "oak_planks" -> hintItem = Items.OAK_PLANKS;
                    case "oak_pressure_plate" -> hintItem = Items.OAK_PRESSURE_PLATE;
                    case "oak_sapling" -> hintItem = Items.OAK_SAPLING;
                    case "oak_sign" -> hintItem = Items.OAK_SIGN;
                    case "oak_slab" -> hintItem = Items.OAK_SLAB;
                    case "oak_stairs" -> hintItem = Items.OAK_STAIRS;
                    case "oak_trapdoor" -> hintItem = Items.OAK_TRAPDOOR;
                    case "oak_wood" -> hintItem = Items.OAK_WOOD;
                    case "observer" -> hintItem = Items.OBSERVER;
                    case "obsidian" -> hintItem = Items.OBSIDIAN;
                    case "orange_banner" -> hintItem = Items.ORANGE_BANNER;
                    case "orange_bed" -> hintItem = Items.ORANGE_BED;
                    case "orange_carpet" -> hintItem = Items.ORANGE_CARPET;
                    case "orange_concrete" -> hintItem = Items.ORANGE_CONCRETE;
                    case "orange_concrete_powder" -> hintItem = Items.ORANGE_CONCRETE_POWDER;
                    case "orange_glazed_terracotta" -> hintItem = Items.ORANGE_GLAZED_TERRACOTTA;
                    case "orange_shulker_box" -> hintItem = Items.ORANGE_SHULKER_BOX;
                    case "orange_stained_glass" -> hintItem = Items.ORANGE_STAINED_GLASS;
                    case "orange_stained_glass_pane" -> hintItem = Items.ORANGE_STAINED_GLASS_PANE;
                    case "orange_terracotta" -> hintItem = Items.ORANGE_TERRACOTTA;
                    case "orange_tulip" -> hintItem = Items.ORANGE_TULIP;
                    case "orange_wool" -> hintItem = Items.ORANGE_WOOL;
                    case "oxeye_daisy" -> hintItem = Items.OXEYE_DAISY;
                    case "packed_ice" -> hintItem = Items.PACKED_ICE;
                    case "peony" -> hintItem = Items.PEONY;
                    case "pink_banner" -> hintItem = Items.PINK_BANNER;
                    case "pink_bed" -> hintItem = Items.PINK_BED;
                    case "pink_carpet" -> hintItem = Items.PINK_CARPET;
                    case "pink_concrete" -> hintItem = Items.PINK_CONCRETE;
                    case "pink_concrete_powder" -> hintItem = Items.PINK_CONCRETE_POWDER;
                    case "pink_glazed_terracotta" -> hintItem = Items.PINK_GLAZED_TERRACOTTA;
                    case "pink_shulker_box" -> hintItem = Items.PINK_SHULKER_BOX;
                    case "pink_stained_glass" -> hintItem = Items.PINK_STAINED_GLASS;
                    case "pink_stained_glass_pane" -> hintItem = Items.PINK_STAINED_GLASS_PANE;
                    case "pink_terracotta" -> hintItem = Items.PINK_TERRACOTTA;
                    case "pink_tulip" -> hintItem = Items.PINK_TULIP;
                    case "pink_wool" -> hintItem = Items.PINK_WOOL;
                    case "piston" -> hintItem = Items.PISTON;
                    case "podzol" -> hintItem = Items.PODZOL;
                    case "polished_andesite" -> hintItem = Items.POLISHED_ANDESITE;
                    case "polished_andesite_slab" -> hintItem = Items.POLISHED_ANDESITE_SLAB;
                    case "polished_andesite_stairs" -> hintItem = Items.POLISHED_ANDESITE_STAIRS;
                    case "polished_diorite" -> hintItem = Items.POLISHED_DIORITE;
                    case "polished_diorite_slab" -> hintItem = Items.POLISHED_DIORITE_SLAB;
                    case "polished_diorite_stairs" -> hintItem = Items.POLISHED_DIORITE_STAIRS;
                    case "polished_granite" -> hintItem = Items.POLISHED_GRANITE;
                    case "polished_granite_slab" -> hintItem = Items.POLISHED_GRANITE_SLAB;
                    case "polished_granite_stairs" -> hintItem = Items.POLISHED_GRANITE_STAIRS;
                    case "poppy" -> hintItem = Items.POPPY;
                    case "powered_rail" -> hintItem = Items.POWERED_RAIL;
                    case "prismarine" -> hintItem = Items.PRISMARINE;
                    case "prismarine_brick_slab" -> hintItem = Items.PRISMARINE_BRICK_SLAB;
                    case "prismarine_brick_stairs" -> hintItem = Items.PRISMARINE_BRICK_STAIRS;
                    case "prismarine_bricks" -> hintItem = Items.PRISMARINE_BRICKS;
                    case "prismarine_slab" -> hintItem = Items.PRISMARINE_SLAB;
                    case "prismarine_stairs" -> hintItem = Items.PRISMARINE_STAIRS;
                    case "prismarine_wall" -> hintItem = Items.PRISMARINE_WALL;
                    case "pumpkin" -> hintItem = Items.PUMPKIN;
                    case "purple_banner" -> hintItem = Items.PURPLE_BANNER;
                    case "purple_bed" -> hintItem = Items.PURPLE_BED;
                    case "purple_carpet" -> hintItem = Items.PURPLE_CARPET;
                    case "purple_concrete" -> hintItem = Items.PURPLE_CONCRETE;
                    case "purple_concrete_powder" -> hintItem = Items.PURPLE_CONCRETE_POWDER;
                    case "purple_glazed_terracotta" -> hintItem = Items.PURPLE_GLAZED_TERRACOTTA;
                    case "purple_shulker_box" -> hintItem = Items.PURPLE_SHULKER_BOX;
                    case "purple_stained_glass" -> hintItem = Items.PURPLE_STAINED_GLASS;
                    case "purple_stained_glass_pane" -> hintItem = Items.PURPLE_STAINED_GLASS_PANE;
                    case "purple_terracotta" -> hintItem = Items.PURPLE_TERRACOTTA;
                    case "purple_wool" -> hintItem = Items.PURPLE_WOOL;
                    case "purpur_block" -> hintItem = Items.PURPUR_BLOCK;
                    case "purpur_pillar" -> hintItem = Items.PURPUR_PILLAR;
                    case "purpur_slab" -> hintItem = Items.PURPUR_SLAB;
                    case "purpur_stairs" -> hintItem = Items.PURPUR_STAIRS;
                    case "quartz_pillar" -> hintItem = Items.QUARTZ_PILLAR;
                    case "quartz_slab" -> hintItem = Items.QUARTZ_SLAB;
                    case "quartz_stairs" -> hintItem = Items.QUARTZ_STAIRS;
                    case "rail" -> hintItem = Items.RAIL;
                    case "red_banner" -> hintItem = Items.RED_BANNER;
                    case "red_bed" -> hintItem = Items.RED_BED;
                    case "red_carpet" -> hintItem = Items.RED_CARPET;
                    case "red_concrete" -> hintItem = Items.RED_CONCRETE;
                    case "red_concrete_powder" -> hintItem = Items.RED_CONCRETE_POWDER;
                    case "red_glazed_terracotta" -> hintItem = Items.RED_GLAZED_TERRACOTTA;
                    case "red_mushroom" -> hintItem = Items.RED_MUSHROOM;
                    case "red_mushroom_block" -> hintItem = Items.RED_MUSHROOM_BLOCK;
                    case "red_nether_brick_slab" -> hintItem = Items.RED_NETHER_BRICK_SLAB;
                    case "red_nether_brick_stairs" -> hintItem = Items.RED_NETHER_BRICK_STAIRS;
                    case "red_nether_brick_wall" -> hintItem = Items.RED_NETHER_BRICK_WALL;
                    case "red_nether_bricks" -> hintItem = Items.RED_NETHER_BRICKS;
                    case "red_sand" -> hintItem = Items.RED_SAND;
                    case "red_sandstone" -> hintItem = Items.RED_SANDSTONE;
                    case "red_sandstone_slab" -> hintItem = Items.RED_SANDSTONE_SLAB;
                    case "red_sandstone_stairs" -> hintItem = Items.RED_SANDSTONE_STAIRS;
                    case "red_sandstone_wall" -> hintItem = Items.RED_SANDSTONE_WALL;
                    case "red_shulker_box" -> hintItem = Items.RED_SHULKER_BOX;
                    case "red_stained_glass" -> hintItem = Items.RED_STAINED_GLASS;
                    case "red_stained_glass_pane" -> hintItem = Items.RED_STAINED_GLASS_PANE;
                    case "red_terracotta" -> hintItem = Items.RED_TERRACOTTA;
                    case "red_tulip" -> hintItem = Items.RED_TULIP;
                    case "red_wool" -> hintItem = Items.RED_WOOL;
                    case "comparator" -> hintItem = Items.COMPARATOR;
                    case "redstone_lamp" -> hintItem = Items.REDSTONE_LAMP;
                    case "redstone_ore" -> hintItem = Items.REDSTONE_ORE;
                    case "repeater" -> hintItem = Items.REPEATER;
                    case "redstone_torch" -> hintItem = Items.REDSTONE_TORCH;
                    case "rose_bush" -> hintItem = Items.ROSE_BUSH;
                    case "sand" -> hintItem = Items.SAND;
                    case "sandstone" -> hintItem = Items.SANDSTONE;
                    case "sandstone_slab" -> hintItem = Items.SANDSTONE_SLAB;
                    case "sandstone_stairs" -> hintItem = Items.SANDSTONE_STAIRS;
                    case "sandstone_wall" -> hintItem = Items.SANDSTONE_WALL;
                    case "scaffolding" -> hintItem = Items.SCAFFOLDING;
                    case "sea_lantern" -> hintItem = Items.SEA_LANTERN;
                    case "sea_pickle" -> hintItem = Items.SEA_PICKLE;
                    case "seagrass" -> hintItem = Items.SEAGRASS;
                    case "shulker_box" -> hintItem = Items.SHULKER_BOX;
                    case "skeleton_skull" -> hintItem = Items.SKELETON_SKULL;
                    case "slime_block" -> hintItem = Items.SLIME_BLOCK;
                    case "smithing_table" -> hintItem = Items.SMITHING_TABLE;
                    case "smoker" -> hintItem = Items.SMOKER;
                    case "smooth_quartz" -> hintItem = Items.SMOOTH_QUARTZ;
                    case "smooth_quartz_slab" -> hintItem = Items.SMOOTH_QUARTZ_SLAB;
                    case "smooth_quartz_stairs" -> hintItem = Items.SMOOTH_QUARTZ_STAIRS;
                    case "smooth_red_sandstone" -> hintItem = Items.SMOOTH_RED_SANDSTONE;
                    case "smooth_red_sandstone_slab" -> hintItem = Items.SMOOTH_RED_SANDSTONE_SLAB;
                    case "smooth_red_sandstone_stairs" -> hintItem = Items.SMOOTH_RED_SANDSTONE_STAIRS;
                    case "smooth_sandstone" -> hintItem = Items.SMOOTH_SANDSTONE;
                    case "smooth_sandstone_slab" -> hintItem = Items.SMOOTH_SANDSTONE_SLAB;
                    case "smooth_sandstone_stairs" -> hintItem = Items.SMOOTH_SANDSTONE_STAIRS;
                    case "smooth_stone" -> hintItem = Items.SMOOTH_STONE;
                    case "smooth_stone_slab" -> hintItem = Items.SMOOTH_STONE_SLAB;
                    case "snow" -> hintItem = Items.SNOW;
                    case "snow_block" -> hintItem = Items.SNOW_BLOCK;
                    case "soul_sand" -> hintItem = Items.SOUL_SAND;
                    case "sponge" -> hintItem = Items.SPONGE;
                    case "spruce_button" -> hintItem = Items.SPRUCE_BUTTON;
                    case "spruce_door" -> hintItem = Items.SPRUCE_DOOR;
                    case "spruce_fence" -> hintItem = Items.SPRUCE_FENCE;
                    case "spruce_fence_gate" -> hintItem = Items.SPRUCE_FENCE_GATE;
                    case "spruce_leaves" -> hintItem = Items.SPRUCE_LEAVES;
                    case "spruce_log" -> hintItem = Items.SPRUCE_LOG;
                    case "spruce_planks" -> hintItem = Items.SPRUCE_PLANKS;
                    case "spruce_pressure_plate" -> hintItem = Items.SPRUCE_PRESSURE_PLATE;
                    case "spruce_sapling" -> hintItem = Items.SPRUCE_SAPLING;
                    case "spruce_sign" -> hintItem = Items.SPRUCE_SIGN;
                    case "spruce_slab" -> hintItem = Items.SPRUCE_SLAB;
                    case "spruce_stairs" -> hintItem = Items.SPRUCE_STAIRS;
                    case "spruce_trapdoor" -> hintItem = Items.SPRUCE_TRAPDOOR;
                    case "spruce_wood" -> hintItem = Items.SPRUCE_WOOD;
                    case "sticky_piston" -> hintItem = Items.STICKY_PISTON;
                    case "stone" -> hintItem = Items.STONE;
                    case "stone_brick_slab" -> hintItem = Items.STONE_BRICK_SLAB;
                    case "stone_brick_stairs" -> hintItem = Items.STONE_BRICK_STAIRS;
                    case "stone_brick_wall" -> hintItem = Items.STONE_BRICK_WALL;
                    case "stone_bricks" -> hintItem = Items.STONE_BRICKS;
                    case "stone_button" -> hintItem = Items.STONE_BUTTON;
                    case "stone_pressure_plate" -> hintItem = Items.STONE_PRESSURE_PLATE;
                    case "stone_slab" -> hintItem = Items.STONE_SLAB;
                    case "stone_stairs" -> hintItem = Items.STONE_STAIRS;
                    case "stonecutter" -> hintItem = Items.STONECUTTER;
                    case "stripped_acacia_log" -> hintItem = Items.STRIPPED_ACACIA_LOG;
                    case "stripped_acacia_wood" -> hintItem = Items.STRIPPED_ACACIA_WOOD;
                    case "stripped_birch_log" -> hintItem = Items.STRIPPED_BIRCH_LOG;
                    case "stripped_birch_wood" -> hintItem = Items.STRIPPED_BIRCH_WOOD;
                    case "stripped_dark_oak_log" -> hintItem = Items.STRIPPED_DARK_OAK_LOG;
                    case "stripped_dark_oak_wood" -> hintItem = Items.STRIPPED_DARK_OAK_WOOD;
                    case "stripped_jungle_log" -> hintItem = Items.STRIPPED_JUNGLE_LOG;
                    case "stripped_jungle_wood" -> hintItem = Items.STRIPPED_JUNGLE_WOOD;
                    case "stripped_oak_log" -> hintItem = Items.STRIPPED_OAK_LOG;
                    case "stripped_oak_wood" -> hintItem = Items.STRIPPED_OAK_WOOD;
                    case "stripped_spruce_log" -> hintItem = Items.STRIPPED_SPRUCE_LOG;
                    case "stripped_spruce_wood" -> hintItem = Items.STRIPPED_SPRUCE_WOOD;
                    case "sunflower" -> hintItem = Items.SUNFLOWER;
                    case "tnt" -> hintItem = Items.TNT;
                    case "terracotta" -> hintItem = Items.TERRACOTTA;
                    case "torch" -> hintItem = Items.TORCH;
                    case "trapped_chest" -> hintItem = Items.TRAPPED_CHEST;
                    case "tripwire_hook" -> hintItem = Items.TRIPWIRE_HOOK;
                    case "tube_coral" -> hintItem = Items.TUBE_CORAL;
                    case "tube_coral_block" -> hintItem = Items.TUBE_CORAL_BLOCK;
                    case "tube_coral_fan" -> hintItem = Items.TUBE_CORAL_FAN;
                    case "turtle_egg" -> hintItem = Items.TURTLE_EGG;
                    case "vine" -> hintItem = Items.VINE;
                    case "wet_sponge" -> hintItem = Items.WET_SPONGE;
                    case "white_banner" -> hintItem = Items.WHITE_BANNER;
                    case "white_bed" -> hintItem = Items.WHITE_BED;
                    case "white_carpet" -> hintItem = Items.WHITE_CARPET;
                    case "white_concrete" -> hintItem = Items.WHITE_CONCRETE;
                    case "white_concrete_powder" -> hintItem = Items.WHITE_CONCRETE_POWDER;
                    case "white_glazed_terracotta" -> hintItem = Items.WHITE_GLAZED_TERRACOTTA;
                    case "white_shulker_box" -> hintItem = Items.WHITE_SHULKER_BOX;
                    case "white_stained_glass" -> hintItem = Items.WHITE_STAINED_GLASS;
                    case "white_stained_glass_pane" -> hintItem = Items.WHITE_STAINED_GLASS_PANE;
                    case "white_terracotta" -> hintItem = Items.WHITE_TERRACOTTA;
                    case "white_tulip" -> hintItem = Items.WHITE_TULIP;
                    case "white_wool" -> hintItem = Items.WHITE_WOOL;
                    case "wither_rose" -> hintItem = Items.WITHER_ROSE;
                    case "wither_skeleton_skull" -> hintItem = Items.WITHER_SKELETON_SKULL;
                    case "yellow_banner" -> hintItem = Items.YELLOW_BANNER;
                    case "yellow_bed" -> hintItem = Items.YELLOW_BED;
                    case "yellow_carpet" -> hintItem = Items.YELLOW_CARPET;
                    case "yellow_concrete" -> hintItem = Items.YELLOW_CONCRETE;
                    case "yellow_concrete_powder" -> hintItem = Items.YELLOW_CONCRETE_POWDER;
                    case "yellow_glazed_terracotta" -> hintItem = Items.YELLOW_GLAZED_TERRACOTTA;
                    case "yellow_shulker_box" -> hintItem = Items.YELLOW_SHULKER_BOX;
                    case "yellow_stained_glass" -> hintItem = Items.YELLOW_STAINED_GLASS;
                    case "yellow_stained_glass_pane" -> hintItem = Items.YELLOW_STAINED_GLASS_PANE;
                    case "yellow_terracotta" -> hintItem = Items.YELLOW_TERRACOTTA;
                    case "yellow_wool" -> hintItem = Items.YELLOW_WOOL;
                    case "zombie_head" -> hintItem = Items.ZOMBIE_HEAD;
                    case "beehive" -> hintItem = Items.BEEHIVE;
                    case "bee_nest" -> hintItem = Items.BEE_NEST;
                    case "honey_block" -> hintItem = Items.HONEY_BLOCK;
                    case "honeycomb_block" -> hintItem = Items.HONEYCOMB_BLOCK;
                    case "ancient_debris" -> hintItem = Items.ANCIENT_DEBRIS;
                    case "basalt" -> hintItem = Items.BASALT;
                    case "netherite_block" -> hintItem = Items.NETHERITE_BLOCK;
                    case "crimson_fungus" -> hintItem = Items.CRIMSON_FUNGUS;
                    case "warped_fungus" -> hintItem = Items.WARPED_FUNGUS;
                    case "crimson_nylium" -> hintItem = Items.CRIMSON_NYLIUM;
                    case "warped_nylium" -> hintItem = Items.WARPED_NYLIUM;
                    case "crimson_planks" -> hintItem = Items.CRIMSON_PLANKS;
                    case "crimson_slab" -> hintItem = Items.CRIMSON_SLAB;
                    case "crimson_stairs" -> hintItem = Items.CRIMSON_STAIRS;
                    case "crimson_pressure_plate" -> hintItem = Items.CRIMSON_PRESSURE_PLATE;
                    case "crimson_button" -> hintItem = Items.CRIMSON_BUTTON;
                    case "crimson_door" -> hintItem = Items.CRIMSON_DOOR;
                    case "crimson_trapdoor" -> hintItem = Items.CRIMSON_TRAPDOOR;
                    case "crimson_fence" -> hintItem = Items.CRIMSON_FENCE;
                    case "crimson_fence_gate" -> hintItem = Items.CRIMSON_FENCE_GATE;
                    case "crimson_sign" -> hintItem = Items.CRIMSON_SIGN;
                    case "warped_planks" -> hintItem = Items.WARPED_PLANKS;
                    case "warped_slab" -> hintItem = Items.WARPED_SLAB;
                    case "warped_stairs" -> hintItem = Items.WARPED_STAIRS;
                    case "warped_pressure_plate" -> hintItem = Items.WARPED_PRESSURE_PLATE;
                    case "warped_button" -> hintItem = Items.WARPED_BUTTON;
                    case "warped_door" -> hintItem = Items.WARPED_DOOR;
                    case "warped_trapdoor" -> hintItem = Items.WARPED_TRAPDOOR;
                    case "warped_fence" -> hintItem = Items.WARPED_FENCE;
                    case "warped_fence_gate" -> hintItem = Items.WARPED_FENCE_GATE;
                    case "warped_sign" -> hintItem = Items.WARPED_SIGN;
                    case "crimson_roots" -> hintItem = Items.CRIMSON_ROOTS;
                    case "warped_roots" -> hintItem = Items.WARPED_ROOTS;
                    case "crimson_stem" -> hintItem = Items.CRIMSON_STEM;
                    case "warped_stem" -> hintItem = Items.WARPED_STEM;
                    case "stripped_crimson_stem" -> hintItem = Items.STRIPPED_CRIMSON_STEM;
                    case "stripped_warped_stem" -> hintItem = Items.STRIPPED_WARPED_STEM;
                    case "crying_obsidian" -> hintItem = Items.CRYING_OBSIDIAN;
                    case "crimson_hyphae" -> hintItem = Items.CRIMSON_HYPHAE;
                    case "warped_hyphae" -> hintItem = Items.WARPED_HYPHAE;
                    case "stripped_crimson_hyphae" -> hintItem = Items.STRIPPED_CRIMSON_HYPHAE;
                    case "stripped_warped_hyphae" -> hintItem = Items.STRIPPED_WARPED_HYPHAE;
                    case "nether_sprouts" -> hintItem = Items.NETHER_SPROUTS;
                    case "shroomlight" -> hintItem = Items.SHROOMLIGHT;
                    case "soul_lantern" -> hintItem = Items.SOUL_LANTERN;
                    case "soul_torch" -> hintItem = Items.SOUL_TORCH;
                    case "soul_soil" -> hintItem = Items.SOUL_SOIL;
                    case "target" -> hintItem = Items.TARGET;
                    case "warped_wart_block" -> hintItem = Items.WARPED_WART_BLOCK;
                    case "weeping_vines" -> hintItem = Items.WEEPING_VINES;
                    case "twisting_vines" -> hintItem = Items.TWISTING_VINES;
                    case "nether_gold_ore" -> hintItem = Items.NETHER_GOLD_ORE;
                    case "polished_basalt" -> hintItem = Items.POLISHED_BASALT;
                    case "respawn_anchor" -> hintItem = Items.RESPAWN_ANCHOR;
                    case "lodestone" -> hintItem = Items.LODESTONE;
                    case "blackstone" -> hintItem = Items.BLACKSTONE;
                    case "blackstone_slab" -> hintItem = Items.BLACKSTONE_SLAB;
                    case "blackstone_stairs" -> hintItem = Items.BLACKSTONE_STAIRS;
                    case "blackstone_wall" -> hintItem = Items.BLACKSTONE_WALL;
                    case "polished_blackstone" -> hintItem = Items.POLISHED_BLACKSTONE;
                    case "polished_blackstone_slab" -> hintItem = Items.POLISHED_BLACKSTONE_SLAB;
                    case "polished_blackstone_stairs" -> hintItem = Items.POLISHED_BLACKSTONE_STAIRS;
                    case "polished_blackstone_wall" -> hintItem = Items.POLISHED_BLACKSTONE_WALL;
                    case "polished_blackstone_bricks" -> hintItem = Items.POLISHED_BLACKSTONE_BRICKS;
                    case "polished_blackstone_brick_slab" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_SLAB;
                    case "polished_blackstone_brick_stairs" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_STAIRS;
                    case "polished_blackstone_brick_wall" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_WALL;
                    case "polished_blackstone_button" -> hintItem = Items.POLISHED_BLACKSTONE_BUTTON;
                    case "polished_blackstone_pressure_plate" -> hintItem = Items.POLISHED_BLACKSTONE_PRESSURE_PLATE;
                    case "cracked_polished_blackstone_bricks" -> hintItem = Items.CRACKED_POLISHED_BLACKSTONE_BRICKS;
                    case "chiseled_polished_blackstone" -> hintItem = Items.CHISELED_POLISHED_BLACKSTONE;
                    case "cracked_nether_bricks" -> hintItem = Items.CRACKED_NETHER_BRICKS;
                    case "chiseled_nether_bricks" -> hintItem = Items.CHISELED_NETHER_BRICKS;
                    case "quartz_bricks" -> hintItem = Items.QUARTZ_BRICKS;
                    case "soul_campfire" -> hintItem = Items.SOUL_CAMPFIRE;
                    case "gilded_blackstone" -> hintItem = Items.GILDED_BLACKSTONE;
                    case "chain" -> hintItem = Items.CHAIN;
                    case "small_amethyst_bud" -> hintItem = Items.SMALL_AMETHYST_BUD;
                    case "medium_amethyst_bud" -> hintItem = Items.MEDIUM_AMETHYST_BUD;
                    case "large_amethyst_bud" -> hintItem = Items.LARGE_AMETHYST_BUD;
                    case "amethyst_cluster" -> hintItem = Items.AMETHYST_CLUSTER;
                    case "azalea" -> hintItem = Items.AZALEA;
                    case "amethyst_block" -> hintItem = Items.AMETHYST_BLOCK;
                    case "copper_block" -> hintItem = Items.COPPER_BLOCK;
                    case "exposed_copper" -> hintItem = Items.EXPOSED_COPPER;
                    case "weathered_copper" -> hintItem = Items.WEATHERED_COPPER;
                    case "oxidized_copper" -> hintItem = Items.OXIDIZED_COPPER;
                    case "waxed_copper_block" -> hintItem = Items.WAXED_COPPER_BLOCK;
                    case "waxed_exposed_copper" -> hintItem = Items.WAXED_EXPOSED_COPPER;
                    case "waxed_weathered_copper" -> hintItem = Items.WAXED_WEATHERED_COPPER;
                    case "calcite" -> hintItem = Items.CALCITE;
                    case "candle" -> hintItem = Items.CANDLE;
                    case "white_candle" -> hintItem = Items.WHITE_CANDLE;
                    case "orange_candle" -> hintItem = Items.ORANGE_CANDLE;
                    case "magenta_candle" -> hintItem = Items.MAGENTA_CANDLE;
                    case "light_blue_candle" -> hintItem = Items.LIGHT_BLUE_CANDLE;
                    case "yellow_candle" -> hintItem = Items.YELLOW_CANDLE;
                    case "lime_candle" -> hintItem = Items.LIME_CANDLE;
                    case "pink_candle" -> hintItem = Items.PINK_CANDLE;
                    case "gray_candle" -> hintItem = Items.GRAY_CANDLE;
                    case "light_gray_candle" -> hintItem = Items.LIGHT_GRAY_CANDLE;
                    case "cyan_candle" -> hintItem = Items.CYAN_CANDLE;
                    case "purple_candle" -> hintItem = Items.PURPLE_CANDLE;
                    case "blue_candle" -> hintItem = Items.BLUE_CANDLE;
                    case "brown_candle" -> hintItem = Items.BROWN_CANDLE;
                    case "green_candle" -> hintItem = Items.GREEN_CANDLE;
                    case "red_candle" -> hintItem = Items.RED_CANDLE;
                    case "black_candle" -> hintItem = Items.BLACK_CANDLE;
                    case "copper_ore" -> hintItem = Items.COPPER_ORE;
                    case "cut_copper" -> hintItem = Items.CUT_COPPER;
                    case "exposed_cut_copper" -> hintItem = Items.EXPOSED_CUT_COPPER;
                    case "weathered_cut_copper" -> hintItem = Items.WEATHERED_CUT_COPPER;
                    case "oxidized_cut_copper" -> hintItem = Items.OXIDIZED_CUT_COPPER;
                    case "waxed_cut_copper" -> hintItem = Items.WAXED_CUT_COPPER;
                    case "waxed_exposed_cut_copper" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER;
                    case "waxed_weathered_cut_copper" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER;
                    case "cut_copper_slab" -> hintItem = Items.CUT_COPPER_SLAB;
                    case "exposed_cut_copper_slab" -> hintItem = Items.EXPOSED_CUT_COPPER_SLAB;
                    case "weathered_cut_copper_slab" -> hintItem = Items.WEATHERED_CUT_COPPER_SLAB;
                    case "oxidized_cut_copper_slab" -> hintItem = Items.OXIDIZED_CUT_COPPER_SLAB;
                    case "waxed_cut_copper_slab" -> hintItem = Items.WAXED_CUT_COPPER_SLAB;
                    case "waxed_exposed_cut_copper_slab" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER_SLAB;
                    case "waxed_weathered_cut_copper_slab" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER_SLAB;
                    case "cut_copper_stairs" -> hintItem = Items.CUT_COPPER_STAIRS;
                    case "exposed_cut_copper_stairs" -> hintItem = Items.EXPOSED_CUT_COPPER_STAIRS;
                    case "weathered_cut_copper_stairs" -> hintItem = Items.WEATHERED_CUT_COPPER_STAIRS;
                    case "oxidized_cut_copper_stairs" -> hintItem = Items.OXIDIZED_CUT_COPPER_STAIRS;
                    case "waxed_cut_copper_stairs" -> hintItem = Items.WAXED_CUT_COPPER_STAIRS;
                    case "waxed_exposed_cut_copper_stairs" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER_STAIRS;
                    case "waxed_weathered_cut_copper_stairs" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER_STAIRS;
                    case "azalea_leaves" -> hintItem = Items.AZALEA_LEAVES;
                    case "flowering_azalea_leaves" -> hintItem = Items.FLOWERING_AZALEA_LEAVES;
                    case "small_dripleaf" -> hintItem = Items.SMALL_DRIPLEAF;
                    case "big_dripleaf" -> hintItem = Items.BIG_DRIPLEAF;
                    case "dripstone_block" -> hintItem = Items.DRIPSTONE_BLOCK;
                    case "flowering_azalea" -> hintItem = Items.FLOWERING_AZALEA;
                    case "glow_lichen" -> hintItem = Items.GLOW_LICHEN;
                    case "hanging_roots" -> hintItem = Items.HANGING_ROOTS;
                    case "lightning_rod" -> hintItem = Items.LIGHTNING_ROD;
                    case "moss_block" -> hintItem = Items.MOSS_BLOCK;
                    case "moss_carpet" -> hintItem = Items.MOSS_CARPET;
                    case "pointed_dripstone" -> hintItem = Items.POINTED_DRIPSTONE;
                    case "rooted_dirt" -> hintItem = Items.ROOTED_DIRT;
                    case "tinted_glass" -> hintItem = Items.TINTED_GLASS;
                    case "tuff" -> hintItem = Items.TUFF;
                    case "deepslate" -> hintItem = Items.DEEPSLATE;
                    case "cobbled_deepslate" -> hintItem = Items.COBBLED_DEEPSLATE;
                    case "cobbled_deepslate_slab" -> hintItem = Items.COBBLED_DEEPSLATE_SLAB;
                    case "cobbled_deepslate_stairs" -> hintItem = Items.COBBLED_DEEPSLATE_STAIRS;
                    case "cobbled_deepslate_wall" -> hintItem = Items.COBBLED_DEEPSLATE_WALL;
                    case "polished_deepslate" -> hintItem = Items.POLISHED_DEEPSLATE;
                    case "polished_deepslate_slab" -> hintItem = Items.POLISHED_DEEPSLATE_SLAB;
                    case "polished_deepslate_stairs" -> hintItem = Items.POLISHED_DEEPSLATE_STAIRS;
                    case "polished_deepslate_wall" -> hintItem = Items.POLISHED_DEEPSLATE_WALL;
                    case "deepslate_bricks" -> hintItem = Items.DEEPSLATE_BRICKS;
                    case "deepslate_brick_slab" -> hintItem = Items.DEEPSLATE_BRICK_SLAB;
                    case "deepslate_brick_stairs" -> hintItem = Items.DEEPSLATE_BRICK_STAIRS;
                    case "deepslate_brick_wall" -> hintItem = Items.DEEPSLATE_BRICK_WALL;
                    case "deepslate_tiles" -> hintItem = Items.DEEPSLATE_TILES;
                    case "deepslate_tile_slab" -> hintItem = Items.DEEPSLATE_TILE_SLAB;
                    case "deepslate_tile_stairs" -> hintItem = Items.DEEPSLATE_TILE_STAIRS;
                    case "deepslate_tile_wall" -> hintItem = Items.DEEPSLATE_TILE_WALL;
                    case "chiseled_deepslate" -> hintItem = Items.CHISELED_DEEPSLATE;
                    case "smooth_basalt" -> hintItem = Items.SMOOTH_BASALT;
                    case "deepslate_gold_ore" -> hintItem = Items.DEEPSLATE_GOLD_ORE;
                    case "deepslate_iron_ore" -> hintItem = Items.DEEPSLATE_IRON_ORE;
                    case "deepslate_lapis_ore" -> hintItem = Items.DEEPSLATE_LAPIS_ORE;
                    case "deepslate_diamond_ore" -> hintItem = Items.DEEPSLATE_DIAMOND_ORE;
                    case "deepslate_redstone_ore" -> hintItem = Items.DEEPSLATE_REDSTONE_ORE;
                    case "cracked_deepslate_bricks" -> hintItem = Items.CRACKED_DEEPSLATE_BRICKS;
                    case "cracked_deepslate_tiles" -> hintItem = Items.CRACKED_DEEPSLATE_TILES;
                    case "waxed_oxidized_copper" -> hintItem = Items.WAXED_OXIDIZED_COPPER;
                    case "waxed_oxidized_cut_copper" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER;
                    case "waxed_oxidized_cut_copper_stairs" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS;
                    case "waxed_oxidized_cut_copper_slab" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER_SLAB;
                    case "raw_copper_block" -> hintItem = Items.RAW_COPPER_BLOCK;
                    case "raw_iron_block" -> hintItem = Items.RAW_IRON_BLOCK;
                    case "raw_gold_block" -> hintItem = Items.RAW_GOLD_BLOCK;
                    case "spore_blossom" -> hintItem = Items.SPORE_BLOSSOM;
                    case "deepslate_emerald_ore" -> hintItem = Items.DEEPSLATE_EMERALD_ORE;
                    case "deepslate_coal_ore" -> hintItem = Items.DEEPSLATE_COAL_ORE;
                    case "deepslate_copper_ore" -> hintItem = Items.DEEPSLATE_COPPER_ORE;
                    case "ochre_froglight" -> hintItem = Items.OCHRE_FROGLIGHT;
                    case "verdant_froglight" -> hintItem = Items.VERDANT_FROGLIGHT;
                    case "pearlescent_froglight" -> hintItem = Items.PEARLESCENT_FROGLIGHT;
                    case "mangrove_leaves" -> hintItem = Items.MANGROVE_LEAVES;
                    case "mangrove_log" -> hintItem = Items.MANGROVE_LOG;
                    case "stripped_mangrove_log" -> hintItem = Items.STRIPPED_MANGROVE_LOG;
                    case "mangrove_planks" -> hintItem = Items.MANGROVE_PLANKS;
                    case "mangrove_slab" -> hintItem = Items.MANGROVE_SLAB;
                    case "mangrove_pressure_plate" -> hintItem = Items.MANGROVE_PRESSURE_PLATE;
                    case "mangrove_fence" -> hintItem = Items.MANGROVE_FENCE;
                    case "mangrove_trapdoor" -> hintItem = Items.MANGROVE_TRAPDOOR;
                    case "mangrove_fence_gate" -> hintItem = Items.MANGROVE_FENCE_GATE;
                    case "mangrove_stairs" -> hintItem = Items.MANGROVE_STAIRS;
                    case "mangrove_button" -> hintItem = Items.MANGROVE_BUTTON;
                    case "mangrove_door" -> hintItem = Items.MANGROVE_DOOR;
                    case "mangrove_sign" -> hintItem = Items.MANGROVE_SIGN;
                    case "mangrove_propagule" -> hintItem = Items.MANGROVE_PROPAGULE;
                    case "mangrove_roots" -> hintItem = Items.MANGROVE_ROOTS;
                    case "mangrove_wood" -> hintItem = Items.MANGROVE_WOOD;
                    case "stripped_mangrove_wood" -> hintItem = Items.STRIPPED_MANGROVE_WOOD;
                    case "mud" -> hintItem = Items.MUD;
                    case "packed_mud" -> hintItem = Items.PACKED_MUD;
                    case "mud_bricks" -> hintItem = Items.MUD_BRICKS;
                    case "mud_brick_slab" -> hintItem = Items.MUD_BRICK_SLAB;
                    case "mud_brick_stairs" -> hintItem = Items.MUD_BRICK_STAIRS;
                    case "mud_brick_wall" -> hintItem = Items.MUD_BRICK_WALL;
                    case "muddy_mangrove_roots" -> hintItem = Items.MUDDY_MANGROVE_ROOTS;
                    case "sculk" -> hintItem = Items.SCULK;
                    case "sculk_catalyst" -> hintItem = Items.SCULK_CATALYST;
                    case "sculk_sensor" -> hintItem = Items.SCULK_SENSOR;
                    case "sculk_shrieker" -> hintItem = Items.SCULK_SHRIEKER;
                    case "sculk_vein" -> hintItem = Items.SCULK_VEIN;
                    case "bamboo_mosaic" -> hintItem = Items.BAMBOO_MOSAIC;
                    case "bamboo_mosaic_stairs" -> hintItem = Items.BAMBOO_MOSAIC_STAIRS;
                    case "bamboo_mosaic_slab" -> hintItem = Items.BAMBOO_MOSAIC_SLAB;
                    case "bamboo_planks" -> hintItem = Items.BAMBOO_PLANKS;
                    case "bamboo_button" -> hintItem = Items.BAMBOO_BUTTON;
                    case "bamboo_door" -> hintItem = Items.BAMBOO_DOOR;
                    case "bamboo_fence" -> hintItem = Items.BAMBOO_FENCE;
                    case "bamboo_fence_gate" -> hintItem = Items.BAMBOO_FENCE_GATE;
                    case "bamboo_pressure_plate" -> hintItem = Items.BAMBOO_PRESSURE_PLATE;
                    case "bamboo_sign" -> hintItem = Items.BAMBOO_SIGN;
                    case "bamboo_slab" -> hintItem = Items.BAMBOO_SLAB;
                    case "bamboo_stairs" -> hintItem = Items.BAMBOO_STAIRS;
                    case "bamboo_trapdoor" -> hintItem = Items.BAMBOO_TRAPDOOR;
                    case "bamboo_block" -> hintItem = Items.BAMBOO_BLOCK;
                    case "stripped_bamboo_block" -> hintItem = Items.STRIPPED_BAMBOO_BLOCK;
                    case "calibrated_sculk_sensor" -> hintItem = Items.CALIBRATED_SCULK_SENSOR;
                    case "cherry_leaves" -> hintItem = Items.CHERRY_LEAVES;
                    case "cherry_log" -> hintItem = Items.CHERRY_LOG;
                    case "stripped_cherry_log" -> hintItem = Items.STRIPPED_CHERRY_LOG;
                    case "cherry_planks" -> hintItem = Items.CHERRY_PLANKS;
                    case "cherry_slab" -> hintItem = Items.CHERRY_SLAB;
                    case "cherry_pressure_plate" -> hintItem = Items.CHERRY_PRESSURE_PLATE;
                    case "cherry_fence" -> hintItem = Items.CHERRY_FENCE;
                    case "cherry_trapdoor" -> hintItem = Items.CHERRY_TRAPDOOR;
                    case "cherry_fence_gate" -> hintItem = Items.CHERRY_FENCE_GATE;
                    case "cherry_stairs" -> hintItem = Items.CHERRY_STAIRS;
                    case "cherry_button" -> hintItem = Items.CHERRY_BUTTON;
                    case "cherry_door" -> hintItem = Items.CHERRY_DOOR;
                    case "cherry_sign" -> hintItem = Items.CHERRY_SIGN;
                    case "cherry_sapling" -> hintItem = Items.CHERRY_SAPLING;
                    case "cherry_wood" -> hintItem = Items.CHERRY_WOOD;
                    case "stripped_cherry_wood" -> hintItem = Items.STRIPPED_CHERRY_WOOD;
                    case "chiseled_bookshelf" -> hintItem = Items.CHISELED_BOOKSHELF;
                    case "decorated_pot" -> hintItem = Items.DECORATED_POT;
                    case "oak_hanging_sign" -> hintItem = Items.OAK_HANGING_SIGN;
                    case "spruce_hanging_sign" -> hintItem = Items.SPRUCE_HANGING_SIGN;
                    case "birch_hanging_sign" -> hintItem = Items.BIRCH_HANGING_SIGN;
                    case "jungle_hanging_sign" -> hintItem = Items.JUNGLE_HANGING_SIGN;
                    case "acacia_hanging_sign" -> hintItem = Items.ACACIA_HANGING_SIGN;
                    case "dark_oak_hanging_sign" -> hintItem = Items.DARK_OAK_HANGING_SIGN;
                    case "mangrove_hanging_sign" -> hintItem = Items.MANGROVE_HANGING_SIGN;
                    case "crimson_hanging_sign" -> hintItem = Items.CRIMSON_HANGING_SIGN;
                    case "warped_hanging_sign" -> hintItem = Items.WARPED_HANGING_SIGN;
                    case "bamboo_hanging_sign" -> hintItem = Items.BAMBOO_HANGING_SIGN;
                    case "cherry_hanging_sign" -> hintItem = Items.CHERRY_HANGING_SIGN;
                    case "piglin_head" -> hintItem = Items.PIGLIN_HEAD;
                    case "pink_petals" -> hintItem = Items.PINK_PETALS;
                    case "pitcher_plant" -> hintItem = Items.PITCHER_PLANT;
                    case "sniffer_egg" -> hintItem = Items.SNIFFER_EGG;
                    case "torchflower" -> hintItem = Items.TORCHFLOWER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:challenges/all_the_items" -> {
                switch (criterion) {
                    case "acacia_boat" -> hintItem = Items.ACACIA_BOAT;
                    case "armor_stand" -> hintItem = Items.ARMOR_STAND;
                    case "beetroot_seeds" -> hintItem = Items.BEETROOT_SEEDS;
                    case "birch_boat" -> hintItem = Items.BIRCH_BOAT;
                    case "experience_bottle" -> hintItem = Items.EXPERIENCE_BOTTLE;
                    case "bucket" -> hintItem = Items.BUCKET;
                    case "cod_bucket" -> hintItem = Items.COD_BUCKET;
                    case "pufferfish_bucket" -> hintItem = Items.PUFFERFISH_BUCKET;
                    case "salmon_bucket" -> hintItem = Items.SALMON_BUCKET;
                    case "tropical_fish_bucket" -> hintItem = Items.TROPICAL_FISH_BUCKET;
                    case "carrot" -> hintItem = Items.CARROT;
                    case "cocoa_beans" -> hintItem = Items.COCOA_BEANS;
                    case "dark_oak_boat" -> hintItem = Items.DARK_OAK_BOAT;
                    case "egg" -> hintItem = Items.EGG;
                    case "ender_pearl" -> hintItem = Items.ENDER_PEARL;
                    case "end_crystal" -> hintItem = Items.END_CRYSTAL;
                    case "ender_eye" -> hintItem = Items.ENDER_EYE;
                    case "firework_rocket" -> hintItem = Items.FIREWORK_ROCKET;
                    case "fire_charge" -> hintItem = Items.FIRE_CHARGE;
                    case "item_frame" -> hintItem = Items.ITEM_FRAME;
                    case "jungle_boat" -> hintItem = Items.JUNGLE_BOAT;
                    case "kelp" -> hintItem = Items.KELP;
                    case "lava_bucket" -> hintItem = Items.LAVA_BUCKET;
                    case "lead" -> hintItem = Items.LEAD;
                    case "lingering_potion" -> hintItem = Items.LINGERING_POTION;
                    case "melon_seeds" -> hintItem = Items.MELON_SEEDS;
                    case "minecart" -> hintItem = Items.MINECART;
                    case "chest_minecart" -> hintItem = Items.CHEST_MINECART;
                    case "furnace_minecart" -> hintItem = Items.FURNACE_MINECART;
                    case "hopper_minecart" -> hintItem = Items.HOPPER_MINECART;
                    case "tnt_minecart" -> hintItem = Items.TNT_MINECART;
                    case "nether_wart" -> hintItem = Items.NETHER_WART;
                    case "oak_boat" -> hintItem = Items.OAK_BOAT;
                    case "painting" -> hintItem = Items.PAINTING;
                    case "potato" -> hintItem = Items.POTATO;
                    case "pumpkin_seeds" -> hintItem = Items.PUMPKIN_SEEDS;
                    case "redstone" -> hintItem = Items.REDSTONE;
                    case "snowball" -> hintItem = Items.SNOWBALL;
                    case "splash_potion" -> hintItem = Items.SPLASH_POTION;
                    case "spruce_boat" -> hintItem = Items.SPRUCE_BOAT;
                    case "string" -> hintItem = Items.STRING;
                    case "sugar_cane" -> hintItem = Items.SUGAR_CANE;
                    case "sweet_berries" -> hintItem = Items.SWEET_BERRIES;
                    case "trident" -> hintItem = Items.TRIDENT;
                    case "water_bucket" -> hintItem = Items.WATER_BUCKET;
                    case "wheat_seeds" -> hintItem = Items.WHEAT_SEEDS;
                    case "apple" -> hintItem = Items.APPLE;
                    case "arrow" -> hintItem = Items.ARROW;
                    case "baked_potato" -> hintItem = Items.BAKED_POTATO;
                    case "beetroot_soup" -> hintItem = Items.BEETROOT_SOUP;
                    case "beetroot" -> hintItem = Items.BEETROOT;
                    case "black_dye" -> hintItem = Items.BLACK_DYE;
                    case "blue_dye" -> hintItem = Items.BLUE_DYE;
                    case "bone" -> hintItem = Items.BONE;
                    case "bone_meal" -> hintItem = Items.BONE_MEAL;
                    case "writable_book" -> hintItem = Items.WRITABLE_BOOK;
                    case "bow" -> hintItem = Items.BOW;
                    case "bowl" -> hintItem = Items.BOWL;
                    case "bread" -> hintItem = Items.BREAD;
                    case "brown_dye" -> hintItem = Items.BROWN_DYE;
                    case "carrot_on_a_stick" -> hintItem = Items.CARROT_ON_A_STICK;
                    case "chainmail_boots" -> hintItem = Items.CHAINMAIL_BOOTS;
                    case "chainmail_chestplate" -> hintItem = Items.CHAINMAIL_CHESTPLATE;
                    case "chainmail_helmet" -> hintItem = Items.CHAINMAIL_HELMET;
                    case "chainmail_leggings" -> hintItem = Items.CHAINMAIL_LEGGINGS;
                    case "charcoal" -> hintItem = Items.CHARCOAL;
                    case "chorus_fruit" -> hintItem = Items.CHORUS_FRUIT;
                    case "coal" -> hintItem = Items.COAL;
                    case "cooked_chicken" -> hintItem = Items.COOKED_CHICKEN;
                    case "cooked_cod" -> hintItem = Items.COOKED_COD;
                    case "cooked_mutton" -> hintItem = Items.COOKED_MUTTON;
                    case "cooked_porkchop" -> hintItem = Items.COOKED_PORKCHOP;
                    case "cooked_rabbit" -> hintItem = Items.COOKED_RABBIT;
                    case "cooked_salmon" -> hintItem = Items.COOKED_SALMON;
                    case "cookie" -> hintItem = Items.COOKIE;
                    case "crossbow" -> hintItem = Items.CROSSBOW;
                    case "cyan_dye" -> hintItem = Items.CYAN_DYE;
                    case "diamond_axe" -> hintItem = Items.DIAMOND_AXE;
                    case "diamond_boots" -> hintItem = Items.DIAMOND_BOOTS;
                    case "diamond_chestplate" -> hintItem = Items.DIAMOND_CHESTPLATE;
                    case "diamond_helmet" -> hintItem = Items.DIAMOND_HELMET;
                    case "diamond_hoe" -> hintItem = Items.DIAMOND_HOE;
                    case "diamond_horse_armor" -> hintItem = Items.DIAMOND_HORSE_ARMOR;
                    case "diamond_leggings" -> hintItem = Items.DIAMOND_LEGGINGS;
                    case "diamond_pickaxe" -> hintItem = Items.DIAMOND_PICKAXE;
                    case "diamond_shovel" -> hintItem = Items.DIAMOND_SHOVEL;
                    case "diamond_sword" -> hintItem = Items.DIAMOND_SWORD;
                    case "dried_kelp" -> hintItem = Items.DRIED_KELP;
                    case "map" -> hintItem = Items.MAP;
                    case "enchanted_book" -> hintItem = Items.ENCHANTED_BOOK;
                    case "enchanted_golden_apple" -> hintItem = Items.ENCHANTED_GOLDEN_APPLE;
                    case "filled_map" -> hintItem = Items.FILLED_MAP;
                    case "fishing_rod" -> hintItem = Items.FISHING_ROD;
                    case "glass_bottle" -> hintItem = Items.GLASS_BOTTLE;
                    case "golden_apple" -> hintItem = Items.GOLDEN_APPLE;
                    case "golden_axe" -> hintItem = Items.GOLDEN_AXE;
                    case "golden_boots" -> hintItem = Items.GOLDEN_BOOTS;
                    case "golden_carrot" -> hintItem = Items.GOLDEN_CARROT;
                    case "golden_chestplate" -> hintItem = Items.GOLDEN_CHESTPLATE;
                    case "golden_helmet" -> hintItem = Items.GOLDEN_HELMET;
                    case "golden_hoe" -> hintItem = Items.GOLDEN_HOE;
                    case "golden_horse_armor" -> hintItem = Items.GOLDEN_HORSE_ARMOR;
                    case "golden_leggings" -> hintItem = Items.GOLDEN_LEGGINGS;
                    case "golden_pickaxe" -> hintItem = Items.GOLDEN_PICKAXE;
                    case "golden_shovel" -> hintItem = Items.GOLDEN_SHOVEL;
                    case "golden_sword" -> hintItem = Items.GOLDEN_SWORD;
                    case "gray_dye" -> hintItem = Items.GRAY_DYE;
                    case "green_dye" -> hintItem = Items.GREEN_DYE;
                    case "ink_sac" -> hintItem = Items.INK_SAC;
                    case "iron_axe" -> hintItem = Items.IRON_AXE;
                    case "iron_boots" -> hintItem = Items.IRON_BOOTS;
                    case "iron_chestplate" -> hintItem = Items.IRON_CHESTPLATE;
                    case "iron_helmet" -> hintItem = Items.IRON_HELMET;
                    case "iron_hoe" -> hintItem = Items.IRON_HOE;
                    case "iron_horse_armor" -> hintItem = Items.IRON_HORSE_ARMOR;
                    case "iron_leggings" -> hintItem = Items.IRON_LEGGINGS;
                    case "iron_nugget" -> hintItem = Items.IRON_NUGGET;
                    case "iron_pickaxe" -> hintItem = Items.IRON_PICKAXE;
                    case "iron_shovel" -> hintItem = Items.IRON_SHOVEL;
                    case "iron_sword" -> hintItem = Items.IRON_SWORD;
                    case "lapis_lazuli" -> hintItem = Items.LAPIS_LAZULI;
                    case "leather_boots" -> hintItem = Items.LEATHER_BOOTS;
                    case "leather_helmet" -> hintItem = Items.LEATHER_HELMET;
                    case "leather_horse_armor" -> hintItem = Items.LEATHER_HORSE_ARMOR;
                    case "leather_leggings" -> hintItem = Items.LEATHER_LEGGINGS;
                    case "leather_chestplate" -> hintItem = Items.LEATHER_CHESTPLATE;
                    case "light_blue_dye" -> hintItem = Items.LIGHT_BLUE_DYE;
                    case "light_gray_dye" -> hintItem = Items.LIGHT_GRAY_DYE;
                    case "lime_dye" -> hintItem = Items.LIME_DYE;
                    case "magenta_dye" -> hintItem = Items.MAGENTA_DYE;
                    case "melon_slice" -> hintItem = Items.MELON_SLICE;
                    case "milk_bucket" -> hintItem = Items.MILK_BUCKET;
                    case "mushroom_stew" -> hintItem = Items.MUSHROOM_STEW;
                    case "music_disc_ward" -> hintItem = Items.MUSIC_DISC_WARD;
                    case "music_disc_wait" -> hintItem = Items.MUSIC_DISC_WAIT;
                    case "music_disc_stal" -> hintItem = Items.MUSIC_DISC_STAL;
                    case "music_disc_mellohi" -> hintItem = Items.MUSIC_DISC_MELLOHI;
                    case "music_disc_mall" -> hintItem = Items.MUSIC_DISC_MALL;
                    case "music_disc_far" -> hintItem = Items.MUSIC_DISC_FAR;
                    case "music_disc_chirp" -> hintItem = Items.MUSIC_DISC_CHIRP;
                    case "music_disc_cat" -> hintItem = Items.MUSIC_DISC_CAT;
                    case "music_disc_blocks" -> hintItem = Items.MUSIC_DISC_BLOCKS;
                    case "music_disc_13" -> hintItem = Items.MUSIC_DISC_13;
                    case "music_disc_11" -> hintItem = Items.MUSIC_DISC_11;
                    case "music_disc_strad" -> hintItem = Items.MUSIC_DISC_STRAD;
                    case "name_tag" -> hintItem = Items.NAME_TAG;
                    case "orange_dye" -> hintItem = Items.ORANGE_DYE;
                    case "pink_dye" -> hintItem = Items.PINK_DYE;
                    case "poisonous_potato" -> hintItem = Items.POISONOUS_POTATO;
                    case "potion" -> hintItem = Items.POTION;
                    case "pufferfish" -> hintItem = Items.PUFFERFISH;
                    case "pumpkin_pie" -> hintItem = Items.PUMPKIN_PIE;
                    case "purple_dye" -> hintItem = Items.PURPLE_DYE;
                    case "rabbit_stew" -> hintItem = Items.RABBIT_STEW;
                    case "beef" -> hintItem = Items.BEEF;
                    case "chicken" -> hintItem = Items.CHICKEN;
                    case "cod" -> hintItem = Items.COD;
                    case "mutton" -> hintItem = Items.MUTTON;
                    case "porkchop" -> hintItem = Items.PORKCHOP;
                    case "rabbit" -> hintItem = Items.RABBIT;
                    case "salmon" -> hintItem = Items.SALMON;
                    case "red_dye" -> hintItem = Items.RED_DYE;
                    case "rotten_flesh" -> hintItem = Items.ROTTEN_FLESH;
                    case "saddle" -> hintItem = Items.SADDLE;
                    case "shears" -> hintItem = Items.SHEARS;
                    case "shield" -> hintItem = Items.SHIELD;
                    case "spectral_arrow" -> hintItem = Items.SPECTRAL_ARROW;
                    case "spider_eye" -> hintItem = Items.SPIDER_EYE;
                    case "cooked_beef" -> hintItem = Items.COOKED_BEEF;
                    case "stone_axe" -> hintItem = Items.STONE_AXE;
                    case "stone_hoe" -> hintItem = Items.STONE_HOE;
                    case "stone_pickaxe" -> hintItem = Items.STONE_PICKAXE;
                    case "stone_shovel" -> hintItem = Items.STONE_SHOVEL;
                    case "stone_sword" -> hintItem = Items.STONE_SWORD;
                    case "sugar" -> hintItem = Items.SUGAR;
                    case "suspicious_stew" -> hintItem = Items.SUSPICIOUS_STEW;
                    case "tipped_arrow" -> hintItem = Items.TIPPED_ARROW;
                    case "totem_of_undying" -> hintItem = Items.TOTEM_OF_UNDYING;
                    case "tropical_fish" -> hintItem = Items.TROPICAL_FISH;
                    case "turtle_helmet" -> hintItem = Items.TURTLE_HELMET;
                    case "wheat" -> hintItem = Items.WHEAT;
                    case "white_dye" -> hintItem = Items.WHITE_DYE;
                    case "wooden_axe" -> hintItem = Items.WOODEN_AXE;
                    case "wooden_hoe" -> hintItem = Items.WOODEN_HOE;
                    case "wooden_pickaxe" -> hintItem = Items.WOODEN_PICKAXE;
                    case "wooden_shovel" -> hintItem = Items.WOODEN_SHOVEL;
                    case "wooden_sword" -> hintItem = Items.WOODEN_SWORD;
                    case "written_book" -> hintItem = Items.WRITTEN_BOOK;
                    case "yellow_dye" -> hintItem = Items.YELLOW_DYE;
                    case "mojang_banner_pattern" -> hintItem = Items.MOJANG_BANNER_PATTERN;
                    case "skull_banner_pattern" -> hintItem = Items.SKULL_BANNER_PATTERN;
                    case "creeper_banner_pattern" -> hintItem = Items.CREEPER_BANNER_PATTERN;
                    case "globe_banner_pattern" -> hintItem = Items.GLOBE_BANNER_PATTERN;
                    case "flower_banner_pattern" -> hintItem = Items.FLOWER_BANNER_PATTERN;
                    case "blaze_powder" -> hintItem = Items.BLAZE_POWDER;
                    case "blaze_rod" -> hintItem = Items.BLAZE_ROD;
                    case "book" -> hintItem = Items.BOOK;
                    case "brick" -> hintItem = Items.BRICK;
                    case "clay_ball" -> hintItem = Items.CLAY_BALL;
                    case "clock" -> hintItem = Items.CLOCK;
                    case "compass" -> hintItem = Items.COMPASS;
                    case "diamond" -> hintItem = Items.DIAMOND;
                    case "dragon_breath" -> hintItem = Items.DRAGON_BREATH;
                    case "emerald" -> hintItem = Items.EMERALD;
                    case "feather" -> hintItem = Items.FEATHER;
                    case "fermented_spider_eye" -> hintItem = Items.FERMENTED_SPIDER_EYE;
                    case "firework_star" -> hintItem = Items.FIREWORK_STAR;
                    case "flint" -> hintItem = Items.FLINT;
                    case "flint_and_steel" -> hintItem = Items.FLINT_AND_STEEL;
                    case "glistering_melon_slice" -> hintItem = Items.GLISTERING_MELON_SLICE;
                    case "glowstone_dust" -> hintItem = Items.GLOWSTONE_DUST;
                    case "gold_ingot" -> hintItem = Items.GOLD_INGOT;
                    case "gold_nugget" -> hintItem = Items.GOLD_NUGGET;
                    case "ghast_tear" -> hintItem = Items.GHAST_TEAR;
                    case "gunpowder" -> hintItem = Items.GUNPOWDER;
                    case "heart_of_the_sea" -> hintItem = Items.HEART_OF_THE_SEA;
                    case "iron_ingot" -> hintItem = Items.IRON_INGOT;
                    case "leather" -> hintItem = Items.LEATHER;
                    case "magma_cream" -> hintItem = Items.MAGMA_CREAM;
                    case "nautilus_shell" -> hintItem = Items.NAUTILUS_SHELL;
                    case "nether_brick" -> hintItem = Items.NETHER_BRICK;
                    case "quartz" -> hintItem = Items.QUARTZ;
                    case "nether_star" -> hintItem = Items.NETHER_STAR;
                    case "paper" -> hintItem = Items.PAPER;
                    case "phantom_membrane" -> hintItem = Items.PHANTOM_MEMBRANE;
                    case "popped_chorus_fruit" -> hintItem = Items.POPPED_CHORUS_FRUIT;
                    case "prismarine_crystals" -> hintItem = Items.PRISMARINE_CRYSTALS;
                    case "prismarine_shard" -> hintItem = Items.PRISMARINE_SHARD;
                    case "rabbit_hide" -> hintItem = Items.RABBIT_HIDE;
                    case "rabbit_foot" -> hintItem = Items.RABBIT_FOOT;
                    case "scute" -> hintItem = Items.SCUTE;
                    case "shulker_shell" -> hintItem = Items.SHULKER_SHELL;
                    case "slime_ball" -> hintItem = Items.SLIME_BALL;
                    case "stick" -> hintItem = Items.STICK;
                    case "honey_bottle" -> hintItem = Items.HONEY_BOTTLE;
                    case "honeycomb" -> hintItem = Items.HONEYCOMB;
                    case "netherite_helmet" -> hintItem = Items.NETHERITE_HELMET;
                    case "netherite_chestplate" -> hintItem = Items.NETHERITE_CHESTPLATE;
                    case "netherite_leggings" -> hintItem = Items.NETHERITE_LEGGINGS;
                    case "netherite_boots" -> hintItem = Items.NETHERITE_BOOTS;
                    case "netherite_axe" -> hintItem = Items.NETHERITE_AXE;
                    case "netherite_hoe" -> hintItem = Items.NETHERITE_HOE;
                    case "netherite_sword" -> hintItem = Items.NETHERITE_SWORD;
                    case "netherite_pickaxe" -> hintItem = Items.NETHERITE_PICKAXE;
                    case "netherite_shovel" -> hintItem = Items.NETHERITE_SHOVEL;
                    case "netherite_ingot" -> hintItem = Items.NETHERITE_INGOT;
                    case "netherite_scrap" -> hintItem = Items.NETHERITE_SCRAP;
                    case "warped_fungus_on_a_stick" -> hintItem = Items.WARPED_FUNGUS_ON_A_STICK;
                    case "piglin_banner_pattern" -> hintItem = Items.PIGLIN_BANNER_PATTERN;
                    case "elytra" -> hintItem = Items.ELYTRA;
                    case "music_disc_pigstep" -> hintItem = Items.MUSIC_DISC_PIGSTEP;
                    case "amethyst_shard" -> hintItem = Items.AMETHYST_SHARD;
                    case "axolotl_bucket" -> hintItem = Items.AXOLOTL_BUCKET;
                    case "copper_ingot" -> hintItem = Items.COPPER_INGOT;
                    case "glow_berries" -> hintItem = Items.GLOW_BERRIES;
                    case "glow_ink_sac" -> hintItem = Items.GLOW_INK_SAC;
                    case "glow_item_frame" -> hintItem = Items.GLOW_ITEM_FRAME;
                    case "powder_snow_bucket" -> hintItem = Items.POWDER_SNOW_BUCKET;
                    case "spyglass" -> hintItem = Items.SPYGLASS;
                    case "raw_iron" -> hintItem = Items.RAW_IRON;
                    case "raw_copper" -> hintItem = Items.RAW_COPPER;
                    case "raw_gold" -> hintItem = Items.RAW_GOLD;
                    case "music_disc_otherside" -> hintItem = Items.MUSIC_DISC_OTHERSIDE;
                    case "tadpole_bucket" -> hintItem = Items.TADPOLE_BUCKET;
                    case "disc_fragment_5" -> hintItem = Items.DISC_FRAGMENT_5;
                    case "echo_shard" -> hintItem = Items.ECHO_SHARD;
                    case "goat_horn" -> hintItem = Items.GOAT_HORN;
                    case "mangrove_boat" -> hintItem = Items.MANGROVE_BOAT;
                    case "music_disc_5" -> hintItem = Items.MUSIC_DISC_5;
                    case "recovery_compass" -> hintItem = Items.RECOVERY_COMPASS;
                    case "oak_chest_boat" -> hintItem = Items.OAK_CHEST_BOAT;
                    case "spruce_chest_boat" -> hintItem = Items.SPRUCE_CHEST_BOAT;
                    case "birch_chest_boat" -> hintItem = Items.BIRCH_CHEST_BOAT;
                    case "jungle_chest_boat" -> hintItem = Items.JUNGLE_CHEST_BOAT;
                    case "acacia_chest_boat" -> hintItem = Items.ACACIA_CHEST_BOAT;
                    case "dark_oak_chest_boat" -> hintItem = Items.DARK_OAK_CHEST_BOAT;
                    case "mangrove_chest_boat" -> hintItem = Items.MANGROVE_CHEST_BOAT;
                    case "brush" -> hintItem = Items.BRUSH;
                    case "music_disc_relic" -> hintItem = Items.MUSIC_DISC_RELIC;
                    case "pitcher_pod" -> hintItem = Items.PITCHER_POD;
                    case "torchflower_seeds" -> hintItem = Items.TORCHFLOWER_SEEDS;
                    case "bamboo_raft" -> hintItem = Items.BAMBOO_RAFT;
                    case "bamboo_chest_raft" -> hintItem = Items.BAMBOO_CHEST_RAFT;
                    case "cherry_boat" -> hintItem = Items.CHERRY_BOAT;
                    case "cherry_chest_boat" -> hintItem = Items.CHERRY_CHEST_BOAT;
                    case "prize_pottery_sherd" -> hintItem = Items.PRIZE_POTTERY_SHERD;
                    case "skull_pottery_sherd" -> hintItem = Items.SKULL_POTTERY_SHERD;
                    case "archer_pottery_sherd" -> hintItem = Items.ARCHER_POTTERY_SHERD;
                    case "arms_up_pottery_sherd" -> hintItem = Items.ARMS_UP_POTTERY_SHERD;
                    case "angler_pottery_sherd" -> hintItem = Items.ANGLER_POTTERY_SHERD;
                    case "blade_pottery_sherd" -> hintItem = Items.BLADE_POTTERY_SHERD;
                    case "brewer_pottery_sherd" -> hintItem = Items.BREWER_POTTERY_SHERD;
                    case "burn_pottery_sherd" -> hintItem = Items.BURN_POTTERY_SHERD;
                    case "danger_pottery_sherd" -> hintItem = Items.DANGER_POTTERY_SHERD;
                    case "explorer_pottery_sherd" -> hintItem = Items.EXPLORER_POTTERY_SHERD;
                    case "friend_pottery_sherd" -> hintItem = Items.FRIEND_POTTERY_SHERD;
                    case "heart_pottery_sherd" -> hintItem = Items.HEART_POTTERY_SHERD;
                    case "heartbreak_pottery_sherd" -> hintItem = Items.HEARTBREAK_POTTERY_SHERD;
                    case "howl_pottery_sherd" -> hintItem = Items.HOWL_POTTERY_SHERD;
                    case "miner_pottery_sherd" -> hintItem = Items.MINER_POTTERY_SHERD;
                    case "mourner_pottery_sherd" -> hintItem = Items.MOURNER_POTTERY_SHERD;
                    case "plenty_pottery_sherd" -> hintItem = Items.PLENTY_POTTERY_SHERD;
                    case "sheaf_pottery_sherd" -> hintItem = Items.SHEAF_POTTERY_SHERD;
                    case "shelter_pottery_sherd" -> hintItem = Items.SHELTER_POTTERY_SHERD;
                    case "snort_pottery_sherd" -> hintItem = Items.SNORT_POTTERY_SHERD;
                    case "netherite_upgrade" -> hintItem = Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE;
                    case "sentry_armor_trim" -> hintItem = Items.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "dune_armor_trim" -> hintItem = Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "coast_armor_trim" -> hintItem = Items.COAST_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "wild_armor_trim" -> hintItem = Items.WILD_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "wayfinder_armor_trim" -> hintItem = Items.WAYFINDER_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "raiser_armor_trim" -> hintItem = Items.RAISER_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "shaper_armor_trim" -> hintItem = Items.SHAPER_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "host_armor_trim" -> hintItem = Items.HOST_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "ward_armor_trim" -> hintItem = Items.WARD_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "silence_armor_trim" -> hintItem = Items.SILENCE_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "eye_armor_trim" -> hintItem = Items.EYE_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "vex_armor_trim" -> hintItem = Items.VEX_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "tide_armor_trim" -> hintItem = Items.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "snout_armor_trim" -> hintItem = Items.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "rib_armor_trim" -> hintItem = Items.RIB_ARMOR_TRIM_SMITHING_TEMPLATE;
                    case "spire_armor_trim" -> hintItem = Items.SPIRE_ARMOR_TRIM_SMITHING_TEMPLATE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:challenges/biological_warfare" -> {
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:challenges/potion_master" -> {
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:challenges/stack_all_the_blocks" -> {
                switch (criterion) {
                    case "acacia_button" -> hintItem = Items.ACACIA_BUTTON;
                    case "acacia_door" -> hintItem = Items.ACACIA_DOOR;
                    case "acacia_fence" -> hintItem = Items.ACACIA_FENCE;
                    case "acacia_fence_gate" -> hintItem = Items.ACACIA_FENCE_GATE;
                    case "acacia_leaves" -> hintItem = Items.ACACIA_LEAVES;
                    case "acacia_log" -> hintItem = Items.ACACIA_LOG;
                    case "acacia_planks" -> hintItem = Items.ACACIA_PLANKS;
                    case "acacia_pressure_plate" -> hintItem = Items.ACACIA_PRESSURE_PLATE;
                    case "acacia_sapling" -> hintItem = Items.ACACIA_SAPLING;
                    case "acacia_sign" -> hintItem = Items.ACACIA_SIGN;
                    case "acacia_slab" -> hintItem = Items.ACACIA_SLAB;
                    case "acacia_stairs" -> hintItem = Items.ACACIA_STAIRS;
                    case "acacia_trapdoor" -> hintItem = Items.ACACIA_TRAPDOOR;
                    case "acacia_wood" -> hintItem = Items.ACACIA_WOOD;
                    case "activator_rail" -> hintItem = Items.ACTIVATOR_RAIL;
                    case "allium" -> hintItem = Items.ALLIUM;
                    case "andesite" -> hintItem = Items.ANDESITE;
                    case "andesite_slab" -> hintItem = Items.ANDESITE_SLAB;
                    case "andesite_stairs" -> hintItem = Items.ANDESITE_STAIRS;
                    case "andesite_wall" -> hintItem = Items.ANDESITE_WALL;
                    case "anvil" -> hintItem = Items.ANVIL;
                    case "azure_bluet" -> hintItem = Items.AZURE_BLUET;
                    case "bamboo" -> hintItem = Items.BAMBOO;
                    case "barrel" -> hintItem = Items.BARREL;
                    case "beacon" -> hintItem = Items.BEACON;
                    case "bell" -> hintItem = Items.BELL;
                    case "birch_button" -> hintItem = Items.BIRCH_BUTTON;
                    case "birch_door" -> hintItem = Items.BIRCH_DOOR;
                    case "birch_fence" -> hintItem = Items.BIRCH_FENCE;
                    case "birch_fence_gate" -> hintItem = Items.BIRCH_FENCE_GATE;
                    case "birch_leaves" -> hintItem = Items.BIRCH_LEAVES;
                    case "birch_log" -> hintItem = Items.BIRCH_LOG;
                    case "birch_planks" -> hintItem = Items.BIRCH_PLANKS;
                    case "birch_pressure_plate" -> hintItem = Items.BIRCH_PRESSURE_PLATE;
                    case "birch_sapling" -> hintItem = Items.BIRCH_SAPLING;
                    case "birch_sign" -> hintItem = Items.BIRCH_SIGN;
                    case "birch_slab" -> hintItem = Items.BIRCH_SLAB;
                    case "birch_stairs" -> hintItem = Items.BIRCH_STAIRS;
                    case "birch_trapdoor" -> hintItem = Items.BIRCH_TRAPDOOR;
                    case "birch_wood" -> hintItem = Items.BIRCH_WOOD;
                    case "black_banner" -> hintItem = Items.BLACK_BANNER;
                    case "black_bed" -> hintItem = Items.BLACK_BED;
                    case "black_carpet" -> hintItem = Items.BLACK_CARPET;
                    case "black_concrete" -> hintItem = Items.BLACK_CONCRETE;
                    case "black_concrete_powder" -> hintItem = Items.BLACK_CONCRETE_POWDER;
                    case "black_glazed_terracotta" -> hintItem = Items.BLACK_GLAZED_TERRACOTTA;
                    case "black_shulker_box" -> hintItem = Items.BLACK_SHULKER_BOX;
                    case "black_stained_glass" -> hintItem = Items.BLACK_STAINED_GLASS;
                    case "black_stained_glass_pane" -> hintItem = Items.BLACK_STAINED_GLASS_PANE;
                    case "black_terracotta" -> hintItem = Items.BLACK_TERRACOTTA;
                    case "black_wool" -> hintItem = Items.BLACK_WOOL;
                    case "blast_furnace" -> hintItem = Items.BLAST_FURNACE;
                    case "coal_block" -> hintItem = Items.COAL_BLOCK;
                    case "diamond_block" -> hintItem = Items.DIAMOND_BLOCK;
                    case "emerald_block" -> hintItem = Items.EMERALD_BLOCK;
                    case "gold_block" -> hintItem = Items.GOLD_BLOCK;
                    case "iron_block" -> hintItem = Items.IRON_BLOCK;
                    case "quartz_block" -> hintItem = Items.QUARTZ_BLOCK;
                    case "redstone_block" -> hintItem = Items.REDSTONE_BLOCK;
                    case "blue_banner" -> hintItem = Items.BLUE_BANNER;
                    case "blue_bed" -> hintItem = Items.BLUE_BED;
                    case "blue_carpet" -> hintItem = Items.BLUE_CARPET;
                    case "blue_concrete" -> hintItem = Items.BLUE_CONCRETE;
                    case "blue_concrete_powder" -> hintItem = Items.BLUE_CONCRETE_POWDER;
                    case "blue_glazed_terracotta" -> hintItem = Items.BLUE_GLAZED_TERRACOTTA;
                    case "blue_ice" -> hintItem = Items.BLUE_ICE;
                    case "blue_orchid" -> hintItem = Items.BLUE_ORCHID;
                    case "blue_shulker_box" -> hintItem = Items.BLUE_SHULKER_BOX;
                    case "blue_stained_glass" -> hintItem = Items.BLUE_STAINED_GLASS;
                    case "blue_stained_glass_pane" -> hintItem = Items.BLUE_STAINED_GLASS_PANE;
                    case "blue_terracotta" -> hintItem = Items.BLUE_TERRACOTTA;
                    case "blue_wool" -> hintItem = Items.BLUE_WOOL;
                    case "bone_block" -> hintItem = Items.BONE_BLOCK;
                    case "bookshelf" -> hintItem = Items.BOOKSHELF;
                    case "brain_coral" -> hintItem = Items.BRAIN_CORAL;
                    case "brain_coral_block" -> hintItem = Items.BRAIN_CORAL_BLOCK;
                    case "brain_coral_fan" -> hintItem = Items.BRAIN_CORAL_FAN;
                    case "brewing_stand" -> hintItem = Items.BREWING_STAND;
                    case "brick_slab" -> hintItem = Items.BRICK_SLAB;
                    case "brick_stairs" -> hintItem = Items.BRICK_STAIRS;
                    case "brick_wall" -> hintItem = Items.BRICK_WALL;
                    case "bricks" -> hintItem = Items.BRICKS;
                    case "brown_banner" -> hintItem = Items.BROWN_BANNER;
                    case "brown_bed" -> hintItem = Items.BROWN_BED;
                    case "brown_carpet" -> hintItem = Items.BROWN_CARPET;
                    case "brown_concrete" -> hintItem = Items.BROWN_CONCRETE;
                    case "brown_concrete_powder" -> hintItem = Items.BROWN_CONCRETE_POWDER;
                    case "brown_glazed_terracotta" -> hintItem = Items.BROWN_GLAZED_TERRACOTTA;
                    case "brown_mushroom" -> hintItem = Items.BROWN_MUSHROOM;
                    case "brown_mushroom_block" -> hintItem = Items.BROWN_MUSHROOM_BLOCK;
                    case "brown_shulker_box" -> hintItem = Items.BROWN_SHULKER_BOX;
                    case "brown_stained_glass" -> hintItem = Items.BROWN_STAINED_GLASS;
                    case "brown_stained_glass_pane" -> hintItem = Items.BROWN_STAINED_GLASS_PANE;
                    case "brown_terracotta" -> hintItem = Items.BROWN_TERRACOTTA;
                    case "brown_wool" -> hintItem = Items.BROWN_WOOL;
                    case "bubble_coral" -> hintItem = Items.BUBBLE_CORAL;
                    case "bubble_coral_block" -> hintItem = Items.BUBBLE_CORAL_BLOCK;
                    case "bubble_coral_fan" -> hintItem = Items.BUBBLE_CORAL_FAN;
                    case "cactus" -> hintItem = Items.CACTUS;
                    case "cake" -> hintItem = Items.CAKE;
                    case "campfire" -> hintItem = Items.CAMPFIRE;
                    case "cartography_table" -> hintItem = Items.CARTOGRAPHY_TABLE;
                    case "carved_pumpkin" -> hintItem = Items.CARVED_PUMPKIN;
                    case "cauldron" -> hintItem = Items.CAULDRON;
                    case "chest" -> hintItem = Items.CHEST;
                    case "chipped_anvil" -> hintItem = Items.CHIPPED_ANVIL;
                    case "chiseled_quartz_block" -> hintItem = Items.CHISELED_QUARTZ_BLOCK;
                    case "chiseled_red_sandstone" -> hintItem = Items.CHISELED_RED_SANDSTONE;
                    case "chiseled_sandstone" -> hintItem = Items.CHISELED_SANDSTONE;
                    case "chiseled_stone_bricks" -> hintItem = Items.CHISELED_STONE_BRICKS;
                    case "chorus_flower" -> hintItem = Items.CHORUS_FLOWER;
                    case "clay" -> hintItem = Items.CLAY;
                    case "coal_ore" -> hintItem = Items.COAL_ORE;
                    case "coarse_dirt" -> hintItem = Items.COARSE_DIRT;
                    case "cobblestone" -> hintItem = Items.COBBLESTONE;
                    case "cobblestone_stairs" -> hintItem = Items.COBBLESTONE_STAIRS;
                    case "cobblestone_slab" -> hintItem = Items.COBBLESTONE_SLAB;
                    case "cobblestone_wall" -> hintItem = Items.COBBLESTONE_WALL;
                    case "cobweb" -> hintItem = Items.COBWEB;
                    case "composter" -> hintItem = Items.COMPOSTER;
                    case "conduit" -> hintItem = Items.CONDUIT;
                    case "cornflower" -> hintItem = Items.CORNFLOWER;
                    case "cracked_stone_bricks" -> hintItem = Items.CRACKED_STONE_BRICKS;
                    case "crafting_table" -> hintItem = Items.CRAFTING_TABLE;
                    case "creeper_head" -> hintItem = Items.CREEPER_HEAD;
                    case "cut_red_sandstone" -> hintItem = Items.CUT_RED_SANDSTONE;
                    case "cut_red_sandstone_slab" -> hintItem = Items.CUT_RED_SANDSTONE_SLAB;
                    case "cut_sandstone" -> hintItem = Items.CUT_SANDSTONE;
                    case "cut_sandstone_slab" -> hintItem = Items.CUT_SANDSTONE_SLAB;
                    case "cyan_banner" -> hintItem = Items.CYAN_BANNER;
                    case "cyan_bed" -> hintItem = Items.CYAN_BED;
                    case "cyan_carpet" -> hintItem = Items.CYAN_CARPET;
                    case "cyan_concrete" -> hintItem = Items.CYAN_CONCRETE;
                    case "cyan_concrete_powder" -> hintItem = Items.CYAN_CONCRETE_POWDER;
                    case "cyan_glazed_terracotta" -> hintItem = Items.CYAN_GLAZED_TERRACOTTA;
                    case "cyan_shulker_box" -> hintItem = Items.CYAN_SHULKER_BOX;
                    case "cyan_stained_glass" -> hintItem = Items.CYAN_STAINED_GLASS;
                    case "cyan_stained_glass_pane" -> hintItem = Items.CYAN_STAINED_GLASS_PANE;
                    case "cyan_terracotta" -> hintItem = Items.CYAN_TERRACOTTA;
                    case "cyan_wool" -> hintItem = Items.CYAN_WOOL;
                    case "damaged_anvil" -> hintItem = Items.DAMAGED_ANVIL;
                    case "dandelion" -> hintItem = Items.DANDELION;
                    case "dark_oak_button" -> hintItem = Items.DARK_OAK_BUTTON;
                    case "dark_oak_door" -> hintItem = Items.DARK_OAK_DOOR;
                    case "dark_oak_fence" -> hintItem = Items.DARK_OAK_FENCE;
                    case "dark_oak_fence_gate" -> hintItem = Items.DARK_OAK_FENCE_GATE;
                    case "dark_oak_leaves" -> hintItem = Items.DARK_OAK_LEAVES;
                    case "dark_oak_log" -> hintItem = Items.DARK_OAK_LOG;
                    case "dark_oak_planks" -> hintItem = Items.DARK_OAK_PLANKS;
                    case "dark_oak_pressure_plate" -> hintItem = Items.DARK_OAK_PRESSURE_PLATE;
                    case "dark_oak_sapling" -> hintItem = Items.DARK_OAK_SAPLING;
                    case "dark_oak_sign" -> hintItem = Items.DARK_OAK_SIGN;
                    case "dark_oak_slab" -> hintItem = Items.DARK_OAK_SLAB;
                    case "dark_oak_stairs" -> hintItem = Items.DARK_OAK_STAIRS;
                    case "dark_oak_trapdoor" -> hintItem = Items.DARK_OAK_TRAPDOOR;
                    case "dark_oak_wood" -> hintItem = Items.DARK_OAK_WOOD;
                    case "dark_prismarine" -> hintItem = Items.DARK_PRISMARINE;
                    case "dark_prismarine_slab" -> hintItem = Items.DARK_PRISMARINE_SLAB;
                    case "dark_prismarine_stairs" -> hintItem = Items.DARK_PRISMARINE_STAIRS;
                    case "daylight_detector" -> hintItem = Items.DAYLIGHT_DETECTOR;
                    case "dead_brain_coral" -> hintItem = Items.DEAD_BRAIN_CORAL;
                    case "dead_brain_coral_block" -> hintItem = Items.DEAD_BRAIN_CORAL_BLOCK;
                    case "dead_brain_coral_fan" -> hintItem = Items.DEAD_BRAIN_CORAL_FAN;
                    case "dead_bubble_coral" -> hintItem = Items.DEAD_BUBBLE_CORAL;
                    case "dead_bubble_coral_block" -> hintItem = Items.DEAD_BUBBLE_CORAL_BLOCK;
                    case "dead_bubble_coral_fan" -> hintItem = Items.DEAD_BUBBLE_CORAL_FAN;
                    case "dead_bush" -> hintItem = Items.DEAD_BUSH;
                    case "dead_fire_coral" -> hintItem = Items.DEAD_FIRE_CORAL;
                    case "dead_fire_coral_block" -> hintItem = Items.DEAD_FIRE_CORAL_BLOCK;
                    case "dead_fire_coral_fan" -> hintItem = Items.DEAD_FIRE_CORAL_FAN;
                    case "dead_horn_coral" -> hintItem = Items.DEAD_HORN_CORAL;
                    case "dead_horn_coral_block" -> hintItem = Items.DEAD_HORN_CORAL_BLOCK;
                    case "dead_horn_coral_fan" -> hintItem = Items.DEAD_HORN_CORAL_FAN;
                    case "dead_tube_coral" -> hintItem = Items.DEAD_TUBE_CORAL;
                    case "dead_tube_coral_block" -> hintItem = Items.DEAD_TUBE_CORAL_BLOCK;
                    case "dead_tube_coral_fan" -> hintItem = Items.DEAD_TUBE_CORAL_FAN;
                    case "detector_rail" -> hintItem = Items.DETECTOR_RAIL;
                    case "diamond_ore" -> hintItem = Items.DIAMOND_ORE;
                    case "diorite" -> hintItem = Items.DIORITE;
                    case "diorite_slab" -> hintItem = Items.DIORITE_SLAB;
                    case "diorite_stairs" -> hintItem = Items.DIORITE_STAIRS;
                    case "diorite_wall" -> hintItem = Items.DIORITE_WALL;
                    case "dirt" -> hintItem = Items.DIRT;
                    case "dispenser" -> hintItem = Items.DISPENSER;
                    case "dragon_head" -> hintItem = Items.DRAGON_HEAD;
                    case "dried_kelp_block" -> hintItem = Items.DRIED_KELP_BLOCK;
                    case "dropper" -> hintItem = Items.DROPPER;
                    case "emerald_ore" -> hintItem = Items.EMERALD_ORE;
                    case "enchanting_table" -> hintItem = Items.ENCHANTING_TABLE;
                    case "end_rod" -> hintItem = Items.END_ROD;
                    case "end_stone" -> hintItem = Items.END_STONE;
                    case "end_stone_brick_slab" -> hintItem = Items.END_STONE_BRICK_SLAB;
                    case "end_stone_brick_stairs" -> hintItem = Items.END_STONE_BRICK_STAIRS;
                    case "end_stone_brick_wall" -> hintItem = Items.END_STONE_BRICK_WALL;
                    case "end_stone_bricks" -> hintItem = Items.END_STONE_BRICKS;
                    case "ender_chest" -> hintItem = Items.ENDER_CHEST;
                    case "fern" -> hintItem = Items.FERN;
                    case "fire_coral" -> hintItem = Items.FIRE_CORAL;
                    case "fire_coral_block" -> hintItem = Items.FIRE_CORAL_BLOCK;
                    case "fire_coral_fan" -> hintItem = Items.FIRE_CORAL_FAN;
                    case "fletching_table" -> hintItem = Items.FLETCHING_TABLE;
                    case "flower_pot" -> hintItem = Items.FLOWER_POT;
                    case "furnace" -> hintItem = Items.FURNACE;
                    case "glass" -> hintItem = Items.GLASS;
                    case "glass_pane" -> hintItem = Items.GLASS_PANE;
                    case "glowstone" -> hintItem = Items.GLOWSTONE;
                    case "gold_ore" -> hintItem = Items.GOLD_ORE;
                    case "granite" -> hintItem = Items.GRANITE;
                    case "granite_slab" -> hintItem = Items.GRANITE_SLAB;
                    case "granite_stairs" -> hintItem = Items.GRANITE_STAIRS;
                    case "granite_wall" -> hintItem = Items.GRANITE_WALL;
                    case "grass" -> hintItem = Items.GRASS;
                    case "grass_block" -> hintItem = Items.GRASS_BLOCK;
                    case "gravel" -> hintItem = Items.GRAVEL;
                    case "gray_banner" -> hintItem = Items.GRAY_BANNER;
                    case "gray_bed" -> hintItem = Items.GRAY_BED;
                    case "gray_carpet" -> hintItem = Items.GRAY_CARPET;
                    case "gray_concrete" -> hintItem = Items.GRAY_CONCRETE;
                    case "gray_concrete_powder" -> hintItem = Items.GRAY_CONCRETE_POWDER;
                    case "gray_glazed_terracotta" -> hintItem = Items.GRAY_GLAZED_TERRACOTTA;
                    case "gray_shulker_box" -> hintItem = Items.GRAY_SHULKER_BOX;
                    case "gray_stained_glass" -> hintItem = Items.GRAY_STAINED_GLASS;
                    case "gray_stained_glass_pane" -> hintItem = Items.GRAY_STAINED_GLASS_PANE;
                    case "gray_terracotta" -> hintItem = Items.GRAY_TERRACOTTA;
                    case "gray_wool" -> hintItem = Items.GRAY_WOOL;
                    case "green_banner" -> hintItem = Items.GREEN_BANNER;
                    case "green_bed" -> hintItem = Items.GREEN_BED;
                    case "green_carpet" -> hintItem = Items.GREEN_CARPET;
                    case "green_concrete" -> hintItem = Items.GREEN_CONCRETE;
                    case "green_concrete_powder" -> hintItem = Items.GREEN_CONCRETE_POWDER;
                    case "green_glazed_terracotta" -> hintItem = Items.GREEN_GLAZED_TERRACOTTA;
                    case "green_shulker_box" -> hintItem = Items.GREEN_SHULKER_BOX;
                    case "green_stained_glass" -> hintItem = Items.GREEN_STAINED_GLASS;
                    case "green_stained_glass_pane" -> hintItem = Items.GREEN_STAINED_GLASS_PANE;
                    case "green_terracotta" -> hintItem = Items.GREEN_TERRACOTTA;
                    case "green_wool" -> hintItem = Items.GREEN_WOOL;
                    case "grindstone" -> hintItem = Items.GRINDSTONE;
                    case "hay_block" -> hintItem = Items.HAY_BLOCK;
                    case "heavy_weighted_pressure_plate" -> hintItem = Items.HEAVY_WEIGHTED_PRESSURE_PLATE;
                    case "hopper" -> hintItem = Items.HOPPER;
                    case "horn_coral" -> hintItem = Items.HORN_CORAL;
                    case "horn_coral_block" -> hintItem = Items.HORN_CORAL_BLOCK;
                    case "horn_coral_fan" -> hintItem = Items.HORN_CORAL_FAN;
                    case "ice" -> hintItem = Items.ICE;
                    case "iron_bars" -> hintItem = Items.IRON_BARS;
                    case "iron_door" -> hintItem = Items.IRON_DOOR;
                    case "iron_ore" -> hintItem = Items.IRON_ORE;
                    case "iron_trapdoor" -> hintItem = Items.IRON_TRAPDOOR;
                    case "jack_o_lantern" -> hintItem = Items.JACK_O_LANTERN;
                    case "jukebox" -> hintItem = Items.JUKEBOX;
                    case "jungle_button" -> hintItem = Items.JUNGLE_BUTTON;
                    case "jungle_door" -> hintItem = Items.JUNGLE_DOOR;
                    case "jungle_fence" -> hintItem = Items.JUNGLE_FENCE;
                    case "jungle_fence_gate" -> hintItem = Items.JUNGLE_FENCE_GATE;
                    case "jungle_leaves" -> hintItem = Items.JUNGLE_LEAVES;
                    case "jungle_log" -> hintItem = Items.JUNGLE_LOG;
                    case "jungle_planks" -> hintItem = Items.JUNGLE_PLANKS;
                    case "jungle_pressure_plate" -> hintItem = Items.JUNGLE_PRESSURE_PLATE;
                    case "jungle_sapling" -> hintItem = Items.JUNGLE_SAPLING;
                    case "jungle_sign" -> hintItem = Items.JUNGLE_SIGN;
                    case "jungle_slab" -> hintItem = Items.JUNGLE_SLAB;
                    case "jungle_stairs" -> hintItem = Items.JUNGLE_STAIRS;
                    case "jungle_trapdoor" -> hintItem = Items.JUNGLE_TRAPDOOR;
                    case "jungle_wood" -> hintItem = Items.JUNGLE_WOOD;
                    case "ladder" -> hintItem = Items.LADDER;
                    case "lantern" -> hintItem = Items.LANTERN;
                    case "lapis_block" -> hintItem = Items.LAPIS_BLOCK;
                    case "lapis_ore" -> hintItem = Items.LAPIS_ORE;
                    case "lectern" -> hintItem = Items.LECTERN;
                    case "lever" -> hintItem = Items.LEVER;
                    case "light_blue_banner" -> hintItem = Items.LIGHT_BLUE_BANNER;
                    case "light_blue_bed" -> hintItem = Items.LIGHT_BLUE_BED;
                    case "light_blue_carpet" -> hintItem = Items.LIGHT_BLUE_CARPET;
                    case "light_blue_concrete" -> hintItem = Items.LIGHT_BLUE_CONCRETE;
                    case "light_blue_concrete_powder" -> hintItem = Items.LIGHT_BLUE_CONCRETE_POWDER;
                    case "light_blue_glazed_terracotta" -> hintItem = Items.LIGHT_BLUE_GLAZED_TERRACOTTA;
                    case "light_blue_shulker_box" -> hintItem = Items.LIGHT_BLUE_SHULKER_BOX;
                    case "light_blue_stained_glass" -> hintItem = Items.LIGHT_BLUE_STAINED_GLASS;
                    case "light_blue_stained_glass_pane" -> hintItem = Items.LIGHT_BLUE_STAINED_GLASS_PANE;
                    case "light_blue_terracotta" -> hintItem = Items.LIGHT_BLUE_TERRACOTTA;
                    case "light_blue_wool" -> hintItem = Items.LIGHT_BLUE_WOOL;
                    case "light_gray_banner" -> hintItem = Items.LIGHT_GRAY_BANNER;
                    case "light_gray_bed" -> hintItem = Items.LIGHT_GRAY_BED;
                    case "light_gray_carpet" -> hintItem = Items.LIGHT_GRAY_CARPET;
                    case "light_gray_concrete" -> hintItem = Items.LIGHT_GRAY_CONCRETE;
                    case "light_gray_concrete_powder" -> hintItem = Items.LIGHT_GRAY_CONCRETE_POWDER;
                    case "light_gray_glazed_terracotta" -> hintItem = Items.LIGHT_GRAY_GLAZED_TERRACOTTA;
                    case "light_gray_shulker_box" -> hintItem = Items.LIGHT_GRAY_SHULKER_BOX;
                    case "light_gray_stained_glass" -> hintItem = Items.LIGHT_GRAY_STAINED_GLASS;
                    case "light_gray_stained_glass_pane" -> hintItem = Items.LIGHT_GRAY_STAINED_GLASS_PANE;
                    case "light_gray_terracotta" -> hintItem = Items.LIGHT_GRAY_TERRACOTTA;
                    case "light_gray_wool" -> hintItem = Items.LIGHT_GRAY_WOOL;
                    case "light_weighted_pressure_plate" -> hintItem = Items.LIGHT_WEIGHTED_PRESSURE_PLATE;
                    case "lilac" -> hintItem = Items.LILAC;
                    case "lily_pad" -> hintItem = Items.LILY_PAD;
                    case "lily_of_the_valley" -> hintItem = Items.LILY_OF_THE_VALLEY;
                    case "lime_banner" -> hintItem = Items.LIME_BANNER;
                    case "lime_bed" -> hintItem = Items.LIME_BED;
                    case "lime_carpet" -> hintItem = Items.LIME_CARPET;
                    case "lime_concrete" -> hintItem = Items.LIME_CONCRETE;
                    case "lime_concrete_powder" -> hintItem = Items.LIME_CONCRETE_POWDER;
                    case "lime_glazed_terracotta" -> hintItem = Items.LIME_GLAZED_TERRACOTTA;
                    case "lime_shulker_box" -> hintItem = Items.LIME_SHULKER_BOX;
                    case "lime_stained_glass" -> hintItem = Items.LIME_STAINED_GLASS;
                    case "lime_stained_glass_pane" -> hintItem = Items.LIME_STAINED_GLASS_PANE;
                    case "lime_terracotta" -> hintItem = Items.LIME_TERRACOTTA;
                    case "lime_wool" -> hintItem = Items.LIME_WOOL;
                    case "loom" -> hintItem = Items.LOOM;
                    case "magenta_banner" -> hintItem = Items.MAGENTA_BANNER;
                    case "magenta_bed" -> hintItem = Items.MAGENTA_BED;
                    case "magenta_carpet" -> hintItem = Items.MAGENTA_CARPET;
                    case "magenta_concrete" -> hintItem = Items.MAGENTA_CONCRETE;
                    case "magenta_concrete_powder" -> hintItem = Items.MAGENTA_CONCRETE_POWDER;
                    case "magenta_glazed_terracotta" -> hintItem = Items.MAGENTA_GLAZED_TERRACOTTA;
                    case "magenta_shulker_box" -> hintItem = Items.MAGENTA_SHULKER_BOX;
                    case "magenta_stained_glass" -> hintItem = Items.MAGENTA_STAINED_GLASS;
                    case "magenta_stained_glass_pane" -> hintItem = Items.MAGENTA_STAINED_GLASS_PANE;
                    case "magenta_terracotta" -> hintItem = Items.MAGENTA_TERRACOTTA;
                    case "magenta_wool" -> hintItem = Items.MAGENTA_WOOL;
                    case "magma_block" -> hintItem = Items.MAGMA_BLOCK;
                    case "melon" -> hintItem = Items.MELON;
                    case "mossy_cobblestone" -> hintItem = Items.MOSSY_COBBLESTONE;
                    case "mossy_cobblestone_slab" -> hintItem = Items.MOSSY_COBBLESTONE_SLAB;
                    case "mossy_cobblestone_stairs" -> hintItem = Items.MOSSY_COBBLESTONE_STAIRS;
                    case "mossy_cobblestone_wall" -> hintItem = Items.MOSSY_COBBLESTONE_WALL;
                    case "mossy_stone_brick_slab" -> hintItem = Items.MOSSY_STONE_BRICK_SLAB;
                    case "mossy_stone_brick_stairs" -> hintItem = Items.MOSSY_STONE_BRICK_STAIRS;
                    case "mossy_stone_brick_wall" -> hintItem = Items.MOSSY_STONE_BRICK_WALL;
                    case "mossy_stone_bricks" -> hintItem = Items.MOSSY_STONE_BRICKS;
                    case "mushroom_stem" -> hintItem = Items.MUSHROOM_STEM;
                    case "mycelium" -> hintItem = Items.MYCELIUM;
                    case "nether_brick_fence" -> hintItem = Items.NETHER_BRICK_FENCE;
                    case "nether_brick_slab" -> hintItem = Items.NETHER_BRICK_SLAB;
                    case "nether_brick_stairs" -> hintItem = Items.NETHER_BRICK_STAIRS;
                    case "nether_brick_wall" -> hintItem = Items.NETHER_BRICK_WALL;
                    case "nether_bricks" -> hintItem = Items.NETHER_BRICKS;
                    case "nether_quartz_ore" -> hintItem = Items.NETHER_QUARTZ_ORE;
                    case "nether_wart_block" -> hintItem = Items.NETHER_WART_BLOCK;
                    case "netherrack" -> hintItem = Items.NETHERRACK;
                    case "note_block" -> hintItem = Items.NOTE_BLOCK;
                    case "oak_button" -> hintItem = Items.OAK_BUTTON;
                    case "oak_door" -> hintItem = Items.OAK_DOOR;
                    case "oak_fence" -> hintItem = Items.OAK_FENCE;
                    case "oak_fence_gate" -> hintItem = Items.OAK_FENCE_GATE;
                    case "oak_leaves" -> hintItem = Items.OAK_LEAVES;
                    case "oak_log" -> hintItem = Items.OAK_LOG;
                    case "oak_planks" -> hintItem = Items.OAK_PLANKS;
                    case "oak_pressure_plate" -> hintItem = Items.OAK_PRESSURE_PLATE;
                    case "oak_sapling" -> hintItem = Items.OAK_SAPLING;
                    case "oak_sign" -> hintItem = Items.OAK_SIGN;
                    case "oak_slab" -> hintItem = Items.OAK_SLAB;
                    case "oak_stairs" -> hintItem = Items.OAK_STAIRS;
                    case "oak_trapdoor" -> hintItem = Items.OAK_TRAPDOOR;
                    case "oak_wood" -> hintItem = Items.OAK_WOOD;
                    case "observer" -> hintItem = Items.OBSERVER;
                    case "obsidian" -> hintItem = Items.OBSIDIAN;
                    case "orange_banner" -> hintItem = Items.ORANGE_BANNER;
                    case "orange_bed" -> hintItem = Items.ORANGE_BED;
                    case "orange_carpet" -> hintItem = Items.ORANGE_CARPET;
                    case "orange_concrete" -> hintItem = Items.ORANGE_CONCRETE;
                    case "orange_concrete_powder" -> hintItem = Items.ORANGE_CONCRETE_POWDER;
                    case "orange_glazed_terracotta" -> hintItem = Items.ORANGE_GLAZED_TERRACOTTA;
                    case "orange_shulker_box" -> hintItem = Items.ORANGE_SHULKER_BOX;
                    case "orange_stained_glass" -> hintItem = Items.ORANGE_STAINED_GLASS;
                    case "orange_stained_glass_pane" -> hintItem = Items.ORANGE_STAINED_GLASS_PANE;
                    case "orange_terracotta" -> hintItem = Items.ORANGE_TERRACOTTA;
                    case "orange_tulip" -> hintItem = Items.ORANGE_TULIP;
                    case "orange_wool" -> hintItem = Items.ORANGE_WOOL;
                    case "oxeye_daisy" -> hintItem = Items.OXEYE_DAISY;
                    case "packed_ice" -> hintItem = Items.PACKED_ICE;
                    case "peony" -> hintItem = Items.PEONY;
                    case "pink_banner" -> hintItem = Items.PINK_BANNER;
                    case "pink_bed" -> hintItem = Items.PINK_BED;
                    case "pink_carpet" -> hintItem = Items.PINK_CARPET;
                    case "pink_concrete" -> hintItem = Items.PINK_CONCRETE;
                    case "pink_concrete_powder" -> hintItem = Items.PINK_CONCRETE_POWDER;
                    case "pink_glazed_terracotta" -> hintItem = Items.PINK_GLAZED_TERRACOTTA;
                    case "pink_shulker_box" -> hintItem = Items.PINK_SHULKER_BOX;
                    case "pink_stained_glass" -> hintItem = Items.PINK_STAINED_GLASS;
                    case "pink_stained_glass_pane" -> hintItem = Items.PINK_STAINED_GLASS_PANE;
                    case "pink_terracotta" -> hintItem = Items.PINK_TERRACOTTA;
                    case "pink_tulip" -> hintItem = Items.PINK_TULIP;
                    case "pink_wool" -> hintItem = Items.PINK_WOOL;
                    case "piston" -> hintItem = Items.PISTON;
                    case "podzol" -> hintItem = Items.PODZOL;
                    case "polished_andesite" -> hintItem = Items.POLISHED_ANDESITE;
                    case "polished_andesite_slab" -> hintItem = Items.POLISHED_ANDESITE_SLAB;
                    case "polished_andesite_stairs" -> hintItem = Items.POLISHED_ANDESITE_STAIRS;
                    case "polished_diorite" -> hintItem = Items.POLISHED_DIORITE;
                    case "polished_diorite_slab" -> hintItem = Items.POLISHED_DIORITE_SLAB;
                    case "polished_diorite_stairs" -> hintItem = Items.POLISHED_DIORITE_STAIRS;
                    case "polished_granite" -> hintItem = Items.POLISHED_GRANITE;
                    case "polished_granite_slab" -> hintItem = Items.POLISHED_GRANITE_SLAB;
                    case "polished_granite_stairs" -> hintItem = Items.POLISHED_GRANITE_STAIRS;
                    case "poppy" -> hintItem = Items.POPPY;
                    case "powered_rail" -> hintItem = Items.POWERED_RAIL;
                    case "prismarine" -> hintItem = Items.PRISMARINE;
                    case "prismarine_brick_slab" -> hintItem = Items.PRISMARINE_BRICK_SLAB;
                    case "prismarine_brick_stairs" -> hintItem = Items.PRISMARINE_BRICK_STAIRS;
                    case "prismarine_bricks" -> hintItem = Items.PRISMARINE_BRICKS;
                    case "prismarine_slab" -> hintItem = Items.PRISMARINE_SLAB;
                    case "prismarine_stairs" -> hintItem = Items.PRISMARINE_STAIRS;
                    case "prismarine_wall" -> hintItem = Items.PRISMARINE_WALL;
                    case "pumpkin" -> hintItem = Items.PUMPKIN;
                    case "purple_banner" -> hintItem = Items.PURPLE_BANNER;
                    case "purple_bed" -> hintItem = Items.PURPLE_BED;
                    case "purple_carpet" -> hintItem = Items.PURPLE_CARPET;
                    case "purple_concrete" -> hintItem = Items.PURPLE_CONCRETE;
                    case "purple_concrete_powder" -> hintItem = Items.PURPLE_CONCRETE_POWDER;
                    case "purple_glazed_terracotta" -> hintItem = Items.PURPLE_GLAZED_TERRACOTTA;
                    case "purple_shulker_box" -> hintItem = Items.PURPLE_SHULKER_BOX;
                    case "purple_stained_glass" -> hintItem = Items.PURPLE_STAINED_GLASS;
                    case "purple_stained_glass_pane" -> hintItem = Items.PURPLE_STAINED_GLASS_PANE;
                    case "purple_terracotta" -> hintItem = Items.PURPLE_TERRACOTTA;
                    case "purple_wool" -> hintItem = Items.PURPLE_WOOL;
                    case "purpur_block" -> hintItem = Items.PURPUR_BLOCK;
                    case "purpur_pillar" -> hintItem = Items.PURPUR_PILLAR;
                    case "purpur_slab" -> hintItem = Items.PURPUR_SLAB;
                    case "purpur_stairs" -> hintItem = Items.PURPUR_STAIRS;
                    case "quartz_pillar" -> hintItem = Items.QUARTZ_PILLAR;
                    case "quartz_slab" -> hintItem = Items.QUARTZ_SLAB;
                    case "quartz_stairs" -> hintItem = Items.QUARTZ_STAIRS;
                    case "rail" -> hintItem = Items.RAIL;
                    case "red_banner" -> hintItem = Items.RED_BANNER;
                    case "red_bed" -> hintItem = Items.RED_BED;
                    case "red_carpet" -> hintItem = Items.RED_CARPET;
                    case "red_concrete" -> hintItem = Items.RED_CONCRETE;
                    case "red_concrete_powder" -> hintItem = Items.RED_CONCRETE_POWDER;
                    case "red_glazed_terracotta" -> hintItem = Items.RED_GLAZED_TERRACOTTA;
                    case "red_mushroom" -> hintItem = Items.RED_MUSHROOM;
                    case "red_mushroom_block" -> hintItem = Items.RED_MUSHROOM_BLOCK;
                    case "red_nether_brick_slab" -> hintItem = Items.RED_NETHER_BRICK_SLAB;
                    case "red_nether_brick_stairs" -> hintItem = Items.RED_NETHER_BRICK_STAIRS;
                    case "red_nether_brick_wall" -> hintItem = Items.RED_NETHER_BRICK_WALL;
                    case "red_nether_bricks" -> hintItem = Items.RED_NETHER_BRICKS;
                    case "red_sand" -> hintItem = Items.RED_SAND;
                    case "red_sandstone" -> hintItem = Items.RED_SANDSTONE;
                    case "red_sandstone_slab" -> hintItem = Items.RED_SANDSTONE_SLAB;
                    case "red_sandstone_stairs" -> hintItem = Items.RED_SANDSTONE_STAIRS;
                    case "red_sandstone_wall" -> hintItem = Items.RED_SANDSTONE_WALL;
                    case "red_shulker_box" -> hintItem = Items.RED_SHULKER_BOX;
                    case "red_stained_glass" -> hintItem = Items.RED_STAINED_GLASS;
                    case "red_stained_glass_pane" -> hintItem = Items.RED_STAINED_GLASS_PANE;
                    case "red_terracotta" -> hintItem = Items.RED_TERRACOTTA;
                    case "red_tulip" -> hintItem = Items.RED_TULIP;
                    case "red_wool" -> hintItem = Items.RED_WOOL;
                    case "comparator" -> hintItem = Items.COMPARATOR;
                    case "redstone_lamp" -> hintItem = Items.REDSTONE_LAMP;
                    case "redstone_ore" -> hintItem = Items.REDSTONE_ORE;
                    case "repeater" -> hintItem = Items.REPEATER;
                    case "redstone_torch" -> hintItem = Items.REDSTONE_TORCH;
                    case "rose_bush" -> hintItem = Items.ROSE_BUSH;
                    case "sand" -> hintItem = Items.SAND;
                    case "sandstone" -> hintItem = Items.SANDSTONE;
                    case "sandstone_slab" -> hintItem = Items.SANDSTONE_SLAB;
                    case "sandstone_stairs" -> hintItem = Items.SANDSTONE_STAIRS;
                    case "sandstone_wall" -> hintItem = Items.SANDSTONE_WALL;
                    case "scaffolding" -> hintItem = Items.SCAFFOLDING;
                    case "sea_lantern" -> hintItem = Items.SEA_LANTERN;
                    case "sea_pickle" -> hintItem = Items.SEA_PICKLE;
                    case "seagrass" -> hintItem = Items.SEAGRASS;
                    case "shulker_box" -> hintItem = Items.SHULKER_BOX;
                    case "skeleton_skull" -> hintItem = Items.SKELETON_SKULL;
                    case "slime_block" -> hintItem = Items.SLIME_BLOCK;
                    case "smithing_table" -> hintItem = Items.SMITHING_TABLE;
                    case "smoker" -> hintItem = Items.SMOKER;
                    case "smooth_quartz" -> hintItem = Items.SMOOTH_QUARTZ;
                    case "smooth_quartz_slab" -> hintItem = Items.SMOOTH_QUARTZ_SLAB;
                    case "smooth_quartz_stairs" -> hintItem = Items.SMOOTH_QUARTZ_STAIRS;
                    case "smooth_red_sandstone" -> hintItem = Items.SMOOTH_RED_SANDSTONE;
                    case "smooth_red_sandstone_slab" -> hintItem = Items.SMOOTH_RED_SANDSTONE_SLAB;
                    case "smooth_red_sandstone_stairs" -> hintItem = Items.SMOOTH_RED_SANDSTONE_STAIRS;
                    case "smooth_sandstone" -> hintItem = Items.SMOOTH_SANDSTONE;
                    case "smooth_sandstone_slab" -> hintItem = Items.SMOOTH_SANDSTONE_SLAB;
                    case "smooth_sandstone_stairs" -> hintItem = Items.SMOOTH_SANDSTONE_STAIRS;
                    case "smooth_stone" -> hintItem = Items.SMOOTH_STONE;
                    case "smooth_stone_slab" -> hintItem = Items.SMOOTH_STONE_SLAB;
                    case "snow" -> hintItem = Items.SNOW;
                    case "snow_block" -> hintItem = Items.SNOW_BLOCK;
                    case "soul_sand" -> hintItem = Items.SOUL_SAND;
                    case "sponge" -> hintItem = Items.SPONGE;
                    case "spruce_button" -> hintItem = Items.SPRUCE_BUTTON;
                    case "spruce_door" -> hintItem = Items.SPRUCE_DOOR;
                    case "spruce_fence" -> hintItem = Items.SPRUCE_FENCE;
                    case "spruce_fence_gate" -> hintItem = Items.SPRUCE_FENCE_GATE;
                    case "spruce_leaves" -> hintItem = Items.SPRUCE_LEAVES;
                    case "spruce_log" -> hintItem = Items.SPRUCE_LOG;
                    case "spruce_planks" -> hintItem = Items.SPRUCE_PLANKS;
                    case "spruce_pressure_plate" -> hintItem = Items.SPRUCE_PRESSURE_PLATE;
                    case "spruce_sapling" -> hintItem = Items.SPRUCE_SAPLING;
                    case "spruce_sign" -> hintItem = Items.SPRUCE_SIGN;
                    case "spruce_slab" -> hintItem = Items.SPRUCE_SLAB;
                    case "spruce_stairs" -> hintItem = Items.SPRUCE_STAIRS;
                    case "spruce_trapdoor" -> hintItem = Items.SPRUCE_TRAPDOOR;
                    case "spruce_wood" -> hintItem = Items.SPRUCE_WOOD;
                    case "sticky_piston" -> hintItem = Items.STICKY_PISTON;
                    case "stone" -> hintItem = Items.STONE;
                    case "stone_brick_slab" -> hintItem = Items.STONE_BRICK_SLAB;
                    case "stone_brick_stairs" -> hintItem = Items.STONE_BRICK_STAIRS;
                    case "stone_brick_wall" -> hintItem = Items.STONE_BRICK_WALL;
                    case "stone_bricks" -> hintItem = Items.STONE_BRICKS;
                    case "stone_button" -> hintItem = Items.STONE_BUTTON;
                    case "stone_pressure_plate" -> hintItem = Items.STONE_PRESSURE_PLATE;
                    case "stone_slab" -> hintItem = Items.STONE_SLAB;
                    case "stone_stairs" -> hintItem = Items.STONE_STAIRS;
                    case "stonecutter" -> hintItem = Items.STONECUTTER;
                    case "stripped_acacia_log" -> hintItem = Items.STRIPPED_ACACIA_LOG;
                    case "stripped_acacia_wood" -> hintItem = Items.STRIPPED_ACACIA_WOOD;
                    case "stripped_birch_log" -> hintItem = Items.STRIPPED_BIRCH_LOG;
                    case "stripped_birch_wood" -> hintItem = Items.STRIPPED_BIRCH_WOOD;
                    case "stripped_dark_oak_log" -> hintItem = Items.STRIPPED_DARK_OAK_LOG;
                    case "stripped_dark_oak_wood" -> hintItem = Items.STRIPPED_DARK_OAK_WOOD;
                    case "stripped_jungle_log" -> hintItem = Items.STRIPPED_JUNGLE_LOG;
                    case "stripped_jungle_wood" -> hintItem = Items.STRIPPED_JUNGLE_WOOD;
                    case "stripped_oak_log" -> hintItem = Items.STRIPPED_OAK_LOG;
                    case "stripped_oak_wood" -> hintItem = Items.STRIPPED_OAK_WOOD;
                    case "stripped_spruce_log" -> hintItem = Items.STRIPPED_SPRUCE_LOG;
                    case "stripped_spruce_wood" -> hintItem = Items.STRIPPED_SPRUCE_WOOD;
                    case "sunflower" -> hintItem = Items.SUNFLOWER;
                    case "tnt" -> hintItem = Items.TNT;
                    case "terracotta" -> hintItem = Items.TERRACOTTA;
                    case "torch" -> hintItem = Items.TORCH;
                    case "trapped_chest" -> hintItem = Items.TRAPPED_CHEST;
                    case "tripwire_hook" -> hintItem = Items.TRIPWIRE_HOOK;
                    case "tube_coral" -> hintItem = Items.TUBE_CORAL;
                    case "tube_coral_block" -> hintItem = Items.TUBE_CORAL_BLOCK;
                    case "tube_coral_fan" -> hintItem = Items.TUBE_CORAL_FAN;
                    case "turtle_egg" -> hintItem = Items.TURTLE_EGG;
                    case "vine" -> hintItem = Items.VINE;
                    case "wet_sponge" -> hintItem = Items.WET_SPONGE;
                    case "white_banner" -> hintItem = Items.WHITE_BANNER;
                    case "white_bed" -> hintItem = Items.WHITE_BED;
                    case "white_carpet" -> hintItem = Items.WHITE_CARPET;
                    case "white_concrete" -> hintItem = Items.WHITE_CONCRETE;
                    case "white_concrete_powder" -> hintItem = Items.WHITE_CONCRETE_POWDER;
                    case "white_glazed_terracotta" -> hintItem = Items.WHITE_GLAZED_TERRACOTTA;
                    case "white_shulker_box" -> hintItem = Items.WHITE_SHULKER_BOX;
                    case "white_stained_glass" -> hintItem = Items.WHITE_STAINED_GLASS;
                    case "white_stained_glass_pane" -> hintItem = Items.WHITE_STAINED_GLASS_PANE;
                    case "white_terracotta" -> hintItem = Items.WHITE_TERRACOTTA;
                    case "white_tulip" -> hintItem = Items.WHITE_TULIP;
                    case "white_wool" -> hintItem = Items.WHITE_WOOL;
                    case "wither_rose" -> hintItem = Items.WITHER_ROSE;
                    case "wither_skeleton_skull" -> hintItem = Items.WITHER_SKELETON_SKULL;
                    case "yellow_banner" -> hintItem = Items.YELLOW_BANNER;
                    case "yellow_bed" -> hintItem = Items.YELLOW_BED;
                    case "yellow_carpet" -> hintItem = Items.YELLOW_CARPET;
                    case "yellow_concrete" -> hintItem = Items.YELLOW_CONCRETE;
                    case "yellow_concrete_powder" -> hintItem = Items.YELLOW_CONCRETE_POWDER;
                    case "yellow_glazed_terracotta" -> hintItem = Items.YELLOW_GLAZED_TERRACOTTA;
                    case "yellow_shulker_box" -> hintItem = Items.YELLOW_SHULKER_BOX;
                    case "yellow_stained_glass" -> hintItem = Items.YELLOW_STAINED_GLASS;
                    case "yellow_stained_glass_pane" -> hintItem = Items.YELLOW_STAINED_GLASS_PANE;
                    case "yellow_terracotta" -> hintItem = Items.YELLOW_TERRACOTTA;
                    case "yellow_wool" -> hintItem = Items.YELLOW_WOOL;
                    case "zombie_head" -> hintItem = Items.ZOMBIE_HEAD;
                    case "beehive" -> hintItem = Items.BEEHIVE;
                    case "bee_nest" -> hintItem = Items.BEE_NEST;
                    case "honey_block" -> hintItem = Items.HONEY_BLOCK;
                    case "honeycomb_block" -> hintItem = Items.HONEYCOMB_BLOCK;
                    case "ancient_debris" -> hintItem = Items.ANCIENT_DEBRIS;
                    case "basalt" -> hintItem = Items.BASALT;
                    case "crimson_fungus" -> hintItem = Items.CRIMSON_FUNGUS;
                    case "warped_fungus" -> hintItem = Items.WARPED_FUNGUS;
                    case "crimson_nylium" -> hintItem = Items.CRIMSON_NYLIUM;
                    case "warped_nylium" -> hintItem = Items.WARPED_NYLIUM;
                    case "crimson_planks" -> hintItem = Items.CRIMSON_PLANKS;
                    case "crimson_slab" -> hintItem = Items.CRIMSON_SLAB;
                    case "crimson_stairs" -> hintItem = Items.CRIMSON_STAIRS;
                    case "crimson_pressure_plate" -> hintItem = Items.CRIMSON_PRESSURE_PLATE;
                    case "crimson_button" -> hintItem = Items.CRIMSON_BUTTON;
                    case "crimson_door" -> hintItem = Items.CRIMSON_DOOR;
                    case "crimson_trapdoor" -> hintItem = Items.CRIMSON_TRAPDOOR;
                    case "crimson_fence" -> hintItem = Items.CRIMSON_FENCE;
                    case "crimson_fence_gate" -> hintItem = Items.CRIMSON_FENCE_GATE;
                    case "crimson_sign" -> hintItem = Items.CRIMSON_SIGN;
                    case "warped_planks" -> hintItem = Items.WARPED_PLANKS;
                    case "warped_slab" -> hintItem = Items.WARPED_SLAB;
                    case "warped_stairs" -> hintItem = Items.WARPED_STAIRS;
                    case "warped_pressure_plate" -> hintItem = Items.WARPED_PRESSURE_PLATE;
                    case "warped_button" -> hintItem = Items.WARPED_BUTTON;
                    case "warped_door" -> hintItem = Items.WARPED_DOOR;
                    case "warped_trapdoor" -> hintItem = Items.WARPED_TRAPDOOR;
                    case "warped_fence" -> hintItem = Items.WARPED_FENCE;
                    case "warped_fence_gate" -> hintItem = Items.WARPED_FENCE_GATE;
                    case "warped_sign" -> hintItem = Items.WARPED_SIGN;
                    case "crimson_roots" -> hintItem = Items.CRIMSON_ROOTS;
                    case "warped_roots" -> hintItem = Items.WARPED_ROOTS;
                    case "crimson_stem" -> hintItem = Items.CRIMSON_STEM;
                    case "warped_stem" -> hintItem = Items.WARPED_STEM;
                    case "stripped_crimson_stem" -> hintItem = Items.STRIPPED_CRIMSON_STEM;
                    case "stripped_warped_stem" -> hintItem = Items.STRIPPED_WARPED_STEM;
                    case "crying_obsidian" -> hintItem = Items.CRYING_OBSIDIAN;
                    case "crimson_hyphae" -> hintItem = Items.CRIMSON_HYPHAE;
                    case "warped_hyphae" -> hintItem = Items.WARPED_HYPHAE;
                    case "stripped_crimson_hyphae" -> hintItem = Items.STRIPPED_CRIMSON_HYPHAE;
                    case "stripped_warped_hyphae" -> hintItem = Items.STRIPPED_WARPED_HYPHAE;
                    case "nether_sprouts" -> hintItem = Items.NETHER_SPROUTS;
                    case "shroomlight" -> hintItem = Items.SHROOMLIGHT;
                    case "soul_lantern" -> hintItem = Items.SOUL_LANTERN;
                    case "soul_torch" -> hintItem = Items.SOUL_TORCH;
                    case "soul_soil" -> hintItem = Items.SOUL_SOIL;
                    case "target" -> hintItem = Items.TARGET;
                    case "warped_wart_block" -> hintItem = Items.WARPED_WART_BLOCK;
                    case "weeping_vines" -> hintItem = Items.WEEPING_VINES;
                    case "twisting_vines" -> hintItem = Items.TWISTING_VINES;
                    case "nether_gold_ore" -> hintItem = Items.NETHER_GOLD_ORE;
                    case "polished_basalt" -> hintItem = Items.POLISHED_BASALT;
                    case "respawn_anchor" -> hintItem = Items.RESPAWN_ANCHOR;
                    case "lodestone" -> hintItem = Items.LODESTONE;
                    case "blackstone" -> hintItem = Items.BLACKSTONE;
                    case "blackstone_slab" -> hintItem = Items.BLACKSTONE_SLAB;
                    case "blackstone_stairs" -> hintItem = Items.BLACKSTONE_STAIRS;
                    case "blackstone_wall" -> hintItem = Items.BLACKSTONE_WALL;
                    case "polished_blackstone" -> hintItem = Items.POLISHED_BLACKSTONE;
                    case "polished_blackstone_slab" -> hintItem = Items.POLISHED_BLACKSTONE_SLAB;
                    case "polished_blackstone_stairs" -> hintItem = Items.POLISHED_BLACKSTONE_STAIRS;
                    case "polished_blackstone_wall" -> hintItem = Items.POLISHED_BLACKSTONE_WALL;
                    case "polished_blackstone_bricks" -> hintItem = Items.POLISHED_BLACKSTONE_BRICKS;
                    case "polished_blackstone_brick_slab" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_SLAB;
                    case "polished_blackstone_brick_stairs" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_STAIRS;
                    case "polished_blackstone_brick_wall" -> hintItem = Items.POLISHED_BLACKSTONE_BRICK_WALL;
                    case "polished_blackstone_button" -> hintItem = Items.POLISHED_BLACKSTONE_BUTTON;
                    case "polished_blackstone_pressure_plate" -> hintItem = Items.POLISHED_BLACKSTONE_PRESSURE_PLATE;
                    case "cracked_polished_blackstone_bricks" -> hintItem = Items.CRACKED_POLISHED_BLACKSTONE_BRICKS;
                    case "chiseled_polished_blackstone" -> hintItem = Items.CHISELED_POLISHED_BLACKSTONE;
                    case "cracked_nether_bricks" -> hintItem = Items.CRACKED_NETHER_BRICKS;
                    case "chiseled_nether_bricks" -> hintItem = Items.CHISELED_NETHER_BRICKS;
                    case "quartz_bricks" -> hintItem = Items.QUARTZ_BRICKS;
                    case "soul_campfire" -> hintItem = Items.SOUL_CAMPFIRE;
                    case "gilded_blackstone" -> hintItem = Items.GILDED_BLACKSTONE;
                    case "chain" -> hintItem = Items.CHAIN;
                    case "small_amethyst_bud" -> hintItem = Items.SMALL_AMETHYST_BUD;
                    case "medium_amethyst_bud" -> hintItem = Items.MEDIUM_AMETHYST_BUD;
                    case "large_amethyst_bud" -> hintItem = Items.LARGE_AMETHYST_BUD;
                    case "amethyst_cluster" -> hintItem = Items.AMETHYST_CLUSTER;
                    case "azalea" -> hintItem = Items.AZALEA;
                    case "amethyst_block" -> hintItem = Items.AMETHYST_BLOCK;
                    case "copper_block" -> hintItem = Items.COPPER_BLOCK;
                    case "exposed_copper" -> hintItem = Items.EXPOSED_COPPER;
                    case "weathered_copper" -> hintItem = Items.WEATHERED_COPPER;
                    case "oxidized_copper" -> hintItem = Items.OXIDIZED_COPPER;
                    case "waxed_copper_block" -> hintItem = Items.WAXED_COPPER_BLOCK;
                    case "waxed_exposed_copper" -> hintItem = Items.WAXED_EXPOSED_COPPER;
                    case "waxed_weathered_copper" -> hintItem = Items.WAXED_WEATHERED_COPPER;
                    case "calcite" -> hintItem = Items.CALCITE;
                    case "candle" -> hintItem = Items.CANDLE;
                    case "white_candle" -> hintItem = Items.WHITE_CANDLE;
                    case "orange_candle" -> hintItem = Items.ORANGE_CANDLE;
                    case "magenta_candle" -> hintItem = Items.MAGENTA_CANDLE;
                    case "light_blue_candle" -> hintItem = Items.LIGHT_BLUE_CANDLE;
                    case "yellow_candle" -> hintItem = Items.YELLOW_CANDLE;
                    case "lime_candle" -> hintItem = Items.LIME_CANDLE;
                    case "pink_candle" -> hintItem = Items.PINK_CANDLE;
                    case "gray_candle" -> hintItem = Items.GRAY_CANDLE;
                    case "light_gray_candle" -> hintItem = Items.LIGHT_GRAY_CANDLE;
                    case "cyan_candle" -> hintItem = Items.CYAN_CANDLE;
                    case "purple_candle" -> hintItem = Items.PURPLE_CANDLE;
                    case "blue_candle" -> hintItem = Items.BLUE_CANDLE;
                    case "brown_candle" -> hintItem = Items.BROWN_CANDLE;
                    case "green_candle" -> hintItem = Items.GREEN_CANDLE;
                    case "red_candle" -> hintItem = Items.RED_CANDLE;
                    case "black_candle" -> hintItem = Items.BLACK_CANDLE;
                    case "copper_ore" -> hintItem = Items.COPPER_ORE;
                    case "cut_copper" -> hintItem = Items.CUT_COPPER;
                    case "exposed_cut_copper" -> hintItem = Items.EXPOSED_CUT_COPPER;
                    case "weathered_cut_copper" -> hintItem = Items.WEATHERED_CUT_COPPER;
                    case "oxidized_cut_copper" -> hintItem = Items.OXIDIZED_CUT_COPPER;
                    case "waxed_cut_copper" -> hintItem = Items.WAXED_CUT_COPPER;
                    case "waxed_exposed_cut_copper" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER;
                    case "waxed_weathered_cut_copper" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER;
                    case "cut_copper_slab" -> hintItem = Items.CUT_COPPER_SLAB;
                    case "exposed_cut_copper_slab" -> hintItem = Items.EXPOSED_CUT_COPPER_SLAB;
                    case "weathered_cut_copper_slab" -> hintItem = Items.WEATHERED_CUT_COPPER_SLAB;
                    case "oxidized_cut_copper_slab" -> hintItem = Items.OXIDIZED_CUT_COPPER_SLAB;
                    case "waxed_cut_copper_slab" -> hintItem = Items.WAXED_CUT_COPPER_SLAB;
                    case "waxed_exposed_cut_copper_slab" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER_SLAB;
                    case "waxed_weathered_cut_copper_slab" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER_SLAB;
                    case "cut_copper_stairs" -> hintItem = Items.CUT_COPPER_STAIRS;
                    case "exposed_cut_copper_stairs" -> hintItem = Items.EXPOSED_CUT_COPPER_STAIRS;
                    case "weathered_cut_copper_stairs" -> hintItem = Items.WEATHERED_CUT_COPPER_STAIRS;
                    case "oxidized_cut_copper_stairs" -> hintItem = Items.OXIDIZED_CUT_COPPER_STAIRS;
                    case "waxed_cut_copper_stairs" -> hintItem = Items.WAXED_CUT_COPPER_STAIRS;
                    case "waxed_exposed_cut_copper_stairs" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER_STAIRS;
                    case "waxed_weathered_cut_copper_stairs" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER_STAIRS;
                    case "azalea_leaves" -> hintItem = Items.AZALEA_LEAVES;
                    case "flowering_azalea_leaves" -> hintItem = Items.FLOWERING_AZALEA_LEAVES;
                    case "small_dripleaf" -> hintItem = Items.SMALL_DRIPLEAF;
                    case "big_dripleaf" -> hintItem = Items.BIG_DRIPLEAF;
                    case "dripstone_block" -> hintItem = Items.DRIPSTONE_BLOCK;
                    case "flowering_azalea" -> hintItem = Items.FLOWERING_AZALEA;
                    case "glow_lichen" -> hintItem = Items.GLOW_LICHEN;
                    case "hanging_roots" -> hintItem = Items.HANGING_ROOTS;
                    case "lightning_rod" -> hintItem = Items.LIGHTNING_ROD;
                    case "moss_block" -> hintItem = Items.MOSS_BLOCK;
                    case "moss_carpet" -> hintItem = Items.MOSS_CARPET;
                    case "pointed_dripstone" -> hintItem = Items.POINTED_DRIPSTONE;
                    case "rooted_dirt" -> hintItem = Items.ROOTED_DIRT;
                    case "tinted_glass" -> hintItem = Items.TINTED_GLASS;
                    case "tuff" -> hintItem = Items.TUFF;
                    case "deepslate" -> hintItem = Items.DEEPSLATE;
                    case "cobbled_deepslate" -> hintItem = Items.COBBLED_DEEPSLATE;
                    case "cobbled_deepslate_slab" -> hintItem = Items.COBBLED_DEEPSLATE_SLAB;
                    case "cobbled_deepslate_stairs" -> hintItem = Items.COBBLED_DEEPSLATE_STAIRS;
                    case "cobbled_deepslate_wall" -> hintItem = Items.COBBLED_DEEPSLATE_WALL;
                    case "polished_deepslate" -> hintItem = Items.POLISHED_DEEPSLATE;
                    case "polished_deepslate_slab" -> hintItem = Items.POLISHED_DEEPSLATE_SLAB;
                    case "polished_deepslate_stairs" -> hintItem = Items.POLISHED_DEEPSLATE_STAIRS;
                    case "polished_deepslate_wall" -> hintItem = Items.POLISHED_DEEPSLATE_WALL;
                    case "deepslate_bricks" -> hintItem = Items.DEEPSLATE_BRICKS;
                    case "deepslate_brick_slab" -> hintItem = Items.DEEPSLATE_BRICK_SLAB;
                    case "deepslate_brick_stairs" -> hintItem = Items.DEEPSLATE_BRICK_STAIRS;
                    case "deepslate_brick_wall" -> hintItem = Items.DEEPSLATE_BRICK_WALL;
                    case "deepslate_tiles" -> hintItem = Items.DEEPSLATE_TILES;
                    case "deepslate_tile_slab" -> hintItem = Items.DEEPSLATE_TILE_SLAB;
                    case "deepslate_tile_stairs" -> hintItem = Items.DEEPSLATE_TILE_STAIRS;
                    case "deepslate_tile_wall" -> hintItem = Items.DEEPSLATE_TILE_WALL;
                    case "chiseled_deepslate" -> hintItem = Items.CHISELED_DEEPSLATE;
                    case "smooth_basalt" -> hintItem = Items.SMOOTH_BASALT;
                    case "deepslate_gold_ore" -> hintItem = Items.DEEPSLATE_GOLD_ORE;
                    case "deepslate_iron_ore" -> hintItem = Items.DEEPSLATE_IRON_ORE;
                    case "deepslate_lapis_ore" -> hintItem = Items.DEEPSLATE_LAPIS_ORE;
                    case "deepslate_diamond_ore" -> hintItem = Items.DEEPSLATE_DIAMOND_ORE;
                    case "deepslate_redstone_ore" -> hintItem = Items.DEEPSLATE_REDSTONE_ORE;
                    case "cracked_deepslate_bricks" -> hintItem = Items.CRACKED_DEEPSLATE_BRICKS;
                    case "cracked_deepslate_tiles" -> hintItem = Items.CRACKED_DEEPSLATE_TILES;
                    case "waxed_oxidized_copper" -> hintItem = Items.WAXED_OXIDIZED_COPPER;
                    case "waxed_oxidized_cut_copper" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER;
                    case "waxed_oxidized_cut_copper_stairs" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS;
                    case "waxed_oxidized_cut_copper_slab" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER_SLAB;
                    case "raw_copper_block" -> hintItem = Items.RAW_COPPER_BLOCK;
                    case "raw_iron_block" -> hintItem = Items.RAW_IRON_BLOCK;
                    case "raw_gold_block" -> hintItem = Items.RAW_GOLD_BLOCK;
                    case "spore_blossom" -> hintItem = Items.SPORE_BLOSSOM;
                    case "deepslate_coal_ore" -> hintItem = Items.DEEPSLATE_COAL_ORE;
                    case "deepslate_copper_ore" -> hintItem = Items.DEEPSLATE_COPPER_ORE;
                    case "ochre_froglight" -> hintItem = Items.OCHRE_FROGLIGHT;
                    case "verdant_froglight" -> hintItem = Items.VERDANT_FROGLIGHT;
                    case "pearlescent_froglight" -> hintItem = Items.PEARLESCENT_FROGLIGHT;
                    case "mangrove_leaves" -> hintItem = Items.MANGROVE_LEAVES;
                    case "mangrove_log" -> hintItem = Items.MANGROVE_LOG;
                    case "stripped_mangrove_log" -> hintItem = Items.STRIPPED_MANGROVE_LOG;
                    case "mangrove_planks" -> hintItem = Items.MANGROVE_PLANKS;
                    case "mangrove_slab" -> hintItem = Items.MANGROVE_SLAB;
                    case "mangrove_pressure_plate" -> hintItem = Items.MANGROVE_PRESSURE_PLATE;
                    case "mangrove_fence" -> hintItem = Items.MANGROVE_FENCE;
                    case "mangrove_trapdoor" -> hintItem = Items.MANGROVE_TRAPDOOR;
                    case "mangrove_fence_gate" -> hintItem = Items.MANGROVE_FENCE_GATE;
                    case "mangrove_stairs" -> hintItem = Items.MANGROVE_STAIRS;
                    case "mangrove_button" -> hintItem = Items.MANGROVE_BUTTON;
                    case "mangrove_door" -> hintItem = Items.MANGROVE_DOOR;
                    case "mangrove_sign" -> hintItem = Items.MANGROVE_SIGN;
                    case "mangrove_propagule" -> hintItem = Items.MANGROVE_PROPAGULE;
                    case "mangrove_roots" -> hintItem = Items.MANGROVE_ROOTS;
                    case "mangrove_wood" -> hintItem = Items.MANGROVE_WOOD;
                    case "stripped_mangrove_wood" -> hintItem = Items.STRIPPED_MANGROVE_WOOD;
                    case "mud" -> hintItem = Items.MUD;
                    case "packed_mud" -> hintItem = Items.PACKED_MUD;
                    case "mud_bricks" -> hintItem = Items.MUD_BRICKS;
                    case "mud_brick_slab" -> hintItem = Items.MUD_BRICK_SLAB;
                    case "mud_brick_stairs" -> hintItem = Items.MUD_BRICK_STAIRS;
                    case "mud_brick_wall" -> hintItem = Items.MUD_BRICK_WALL;
                    case "muddy_mangrove_roots" -> hintItem = Items.MUDDY_MANGROVE_ROOTS;
                    case "sculk" -> hintItem = Items.SCULK;
                    case "sculk_catalyst" -> hintItem = Items.SCULK_CATALYST;
                    case "sculk_sensor" -> hintItem = Items.SCULK_SENSOR;
                    case "sculk_shrieker" -> hintItem = Items.SCULK_SHRIEKER;
                    case "sculk_vein" -> hintItem = Items.SCULK_VEIN;
                    case "bamboo_mosaic" -> hintItem = Items.BAMBOO_MOSAIC;
                    case "bamboo_mosaic_stairs" -> hintItem = Items.BAMBOO_MOSAIC_STAIRS;
                    case "bamboo_mosaic_slab" -> hintItem = Items.BAMBOO_MOSAIC_SLAB;
                    case "bamboo_planks" -> hintItem = Items.BAMBOO_PLANKS;
                    case "bamboo_button" -> hintItem = Items.BAMBOO_BUTTON;
                    case "bamboo_door" -> hintItem = Items.BAMBOO_DOOR;
                    case "bamboo_fence" -> hintItem = Items.BAMBOO_FENCE;
                    case "bamboo_fence_gate" -> hintItem = Items.BAMBOO_FENCE_GATE;
                    case "bamboo_pressure_plate" -> hintItem = Items.BAMBOO_PRESSURE_PLATE;
                    case "bamboo_sign" -> hintItem = Items.BAMBOO_SIGN;
                    case "bamboo_slab" -> hintItem = Items.BAMBOO_SLAB;
                    case "bamboo_stairs" -> hintItem = Items.BAMBOO_STAIRS;
                    case "bamboo_trapdoor" -> hintItem = Items.BAMBOO_TRAPDOOR;
                    case "bamboo_block" -> hintItem = Items.BAMBOO_BLOCK;
                    case "stripped_bamboo_block" -> hintItem = Items.STRIPPED_BAMBOO_BLOCK;
                    case "calibrated_sculk_sensor" -> hintItem = Items.CALIBRATED_SCULK_SENSOR;
                    case "cherry_leaves" -> hintItem = Items.CHERRY_LEAVES;
                    case "cherry_log" -> hintItem = Items.CHERRY_LOG;
                    case "stripped_cherry_log" -> hintItem = Items.STRIPPED_CHERRY_LOG;
                    case "cherry_planks" -> hintItem = Items.CHERRY_PLANKS;
                    case "cherry_slab" -> hintItem = Items.CHERRY_SLAB;
                    case "cherry_pressure_plate" -> hintItem = Items.CHERRY_PRESSURE_PLATE;
                    case "cherry_fence" -> hintItem = Items.CHERRY_FENCE;
                    case "cherry_trapdoor" -> hintItem = Items.CHERRY_TRAPDOOR;
                    case "cherry_fence_gate" -> hintItem = Items.CHERRY_FENCE_GATE;
                    case "cherry_stairs" -> hintItem = Items.CHERRY_STAIRS;
                    case "cherry_button" -> hintItem = Items.CHERRY_BUTTON;
                    case "cherry_door" -> hintItem = Items.CHERRY_DOOR;
                    case "cherry_sign" -> hintItem = Items.CHERRY_SIGN;
                    case "cherry_sapling" -> hintItem = Items.CHERRY_SAPLING;
                    case "cherry_wood" -> hintItem = Items.CHERRY_WOOD;
                    case "stripped_cherry_wood" -> hintItem = Items.STRIPPED_CHERRY_WOOD;
                    case "chiseled_bookshelf" -> hintItem = Items.CHISELED_BOOKSHELF;
                    case "decorated_pot" -> hintItem = Items.DECORATED_POT;
                    case "oak_hanging_sign" -> hintItem = Items.OAK_HANGING_SIGN;
                    case "spruce_hanging_sign" -> hintItem = Items.SPRUCE_HANGING_SIGN;
                    case "birch_hanging_sign" -> hintItem = Items.BIRCH_HANGING_SIGN;
                    case "jungle_hanging_sign" -> hintItem = Items.JUNGLE_HANGING_SIGN;
                    case "acacia_hanging_sign" -> hintItem = Items.ACACIA_HANGING_SIGN;
                    case "dark_oak_hanging_sign" -> hintItem = Items.DARK_OAK_HANGING_SIGN;
                    case "mangrove_hanging_sign" -> hintItem = Items.MANGROVE_HANGING_SIGN;
                    case "crimson_hanging_sign" -> hintItem = Items.CRIMSON_HANGING_SIGN;
                    case "warped_hanging_sign" -> hintItem = Items.WARPED_HANGING_SIGN;
                    case "bamboo_hanging_sign" -> hintItem = Items.BAMBOO_HANGING_SIGN;
                    case "cherry_hanging_sign" -> hintItem = Items.CHERRY_HANGING_SIGN;
                    case "piglin_head" -> hintItem = Items.PIGLIN_HEAD;
                    case "pink_petals" -> hintItem = Items.PINK_PETALS;
                    case "pitcher_plant" -> hintItem = Items.PITCHER_PLANT;
                    case "sniffer_egg" -> hintItem = Items.SNIFFER_EGG;
                    case "torchflower" -> hintItem = Items.TORCHFLOWER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:challenges/stack_all_the_items" -> {
                switch (criterion) {
                    case "acacia_boat" -> hintItem = Items.ACACIA_BOAT;
                    case "armor_stand" -> hintItem = Items.ARMOR_STAND;
                    case "beetroot_seeds" -> hintItem = Items.BEETROOT_SEEDS;
                    case "birch_boat" -> hintItem = Items.BIRCH_BOAT;
                    case "experience_bottle" -> hintItem = Items.EXPERIENCE_BOTTLE;
                    case "bucket" -> hintItem = Items.BUCKET;
                    case "cod_bucket" -> hintItem = Items.COD_BUCKET;
                    case "pufferfish_bucket" -> hintItem = Items.PUFFERFISH_BUCKET;
                    case "salmon_bucket" -> hintItem = Items.SALMON_BUCKET;
                    case "tropical_fish_bucket" -> hintItem = Items.TROPICAL_FISH_BUCKET;
                    case "carrot" -> hintItem = Items.CARROT;
                    case "cocoa_beans" -> hintItem = Items.COCOA_BEANS;
                    case "dark_oak_boat" -> hintItem = Items.DARK_OAK_BOAT;
                    case "egg" -> hintItem = Items.EGG;
                    case "ender_pearl" -> hintItem = Items.ENDER_PEARL;
                    case "end_crystal" -> hintItem = Items.END_CRYSTAL;
                    case "ender_eye" -> hintItem = Items.ENDER_EYE;
                    case "firework_rocket" -> hintItem = Items.FIREWORK_ROCKET;
                    case "fire_charge" -> hintItem = Items.FIRE_CHARGE;
                    case "item_frame" -> hintItem = Items.ITEM_FRAME;
                    case "jungle_boat" -> hintItem = Items.JUNGLE_BOAT;
                    case "kelp" -> hintItem = Items.KELP;
                    case "lava_bucket" -> hintItem = Items.LAVA_BUCKET;
                    case "lead" -> hintItem = Items.LEAD;
                    case "lingering_potion" -> hintItem = Items.LINGERING_POTION;
                    case "melon_seeds" -> hintItem = Items.MELON_SEEDS;
                    case "minecart" -> hintItem = Items.MINECART;
                    case "chest_minecart" -> hintItem = Items.CHEST_MINECART;
                    case "furnace_minecart" -> hintItem = Items.FURNACE_MINECART;
                    case "hopper_minecart" -> hintItem = Items.HOPPER_MINECART;
                    case "tnt_minecart" -> hintItem = Items.TNT_MINECART;
                    case "nether_wart" -> hintItem = Items.NETHER_WART;
                    case "oak_boat" -> hintItem = Items.OAK_BOAT;
                    case "painting" -> hintItem = Items.PAINTING;
                    case "potato" -> hintItem = Items.POTATO;
                    case "pumpkin_seeds" -> hintItem = Items.PUMPKIN_SEEDS;
                    case "redstone" -> hintItem = Items.REDSTONE;
                    case "snowball" -> hintItem = Items.SNOWBALL;
                    case "splash_potion" -> hintItem = Items.SPLASH_POTION;
                    case "spruce_boat" -> hintItem = Items.SPRUCE_BOAT;
                    case "string" -> hintItem = Items.STRING;
                    case "sugar_cane" -> hintItem = Items.SUGAR_CANE;
                    case "sweet_berries" -> hintItem = Items.SWEET_BERRIES;
                    case "trident" -> hintItem = Items.TRIDENT;
                    case "water_bucket" -> hintItem = Items.WATER_BUCKET;
                    case "wheat_seeds" -> hintItem = Items.WHEAT_SEEDS;
                    case "apple" -> hintItem = Items.APPLE;
                    case "arrow" -> hintItem = Items.ARROW;
                    case "baked_potato" -> hintItem = Items.BAKED_POTATO;
                    case "beetroot_soup" -> hintItem = Items.BEETROOT_SOUP;
                    case "beetroot" -> hintItem = Items.BEETROOT;
                    case "black_dye" -> hintItem = Items.BLACK_DYE;
                    case "blue_dye" -> hintItem = Items.BLUE_DYE;
                    case "bone" -> hintItem = Items.BONE;
                    case "bone_meal" -> hintItem = Items.BONE_MEAL;
                    case "writable_book" -> hintItem = Items.WRITABLE_BOOK;
                    case "bow" -> hintItem = Items.BOW;
                    case "bowl" -> hintItem = Items.BOWL;
                    case "bread" -> hintItem = Items.BREAD;
                    case "brown_dye" -> hintItem = Items.BROWN_DYE;
                    case "carrot_on_a_stick" -> hintItem = Items.CARROT_ON_A_STICK;
                    case "chainmail_boots" -> hintItem = Items.CHAINMAIL_BOOTS;
                    case "chainmail_chestplate" -> hintItem = Items.CHAINMAIL_CHESTPLATE;
                    case "chainmail_helmet" -> hintItem = Items.CHAINMAIL_HELMET;
                    case "chainmail_leggings" -> hintItem = Items.CHAINMAIL_LEGGINGS;
                    case "charcoal" -> hintItem = Items.CHARCOAL;
                    case "chorus_fruit" -> hintItem = Items.CHORUS_FRUIT;
                    case "coal" -> hintItem = Items.COAL;
                    case "cooked_chicken" -> hintItem = Items.COOKED_CHICKEN;
                    case "cooked_cod" -> hintItem = Items.COOKED_COD;
                    case "cooked_mutton" -> hintItem = Items.COOKED_MUTTON;
                    case "cooked_porkchop" -> hintItem = Items.COOKED_PORKCHOP;
                    case "cooked_rabbit" -> hintItem = Items.COOKED_RABBIT;
                    case "cooked_salmon" -> hintItem = Items.COOKED_SALMON;
                    case "cookie" -> hintItem = Items.COOKIE;
                    case "crossbow" -> hintItem = Items.CROSSBOW;
                    case "cyan_dye" -> hintItem = Items.CYAN_DYE;
                    case "diamond_axe" -> hintItem = Items.DIAMOND_AXE;
                    case "diamond_boots" -> hintItem = Items.DIAMOND_BOOTS;
                    case "diamond_chestplate" -> hintItem = Items.DIAMOND_CHESTPLATE;
                    case "diamond_helmet" -> hintItem = Items.DIAMOND_HELMET;
                    case "diamond_hoe" -> hintItem = Items.DIAMOND_HOE;
                    case "diamond_horse_armor" -> hintItem = Items.DIAMOND_HORSE_ARMOR;
                    case "diamond_leggings" -> hintItem = Items.DIAMOND_LEGGINGS;
                    case "diamond_pickaxe" -> hintItem = Items.DIAMOND_PICKAXE;
                    case "diamond_shovel" -> hintItem = Items.DIAMOND_SHOVEL;
                    case "diamond_sword" -> hintItem = Items.DIAMOND_SWORD;
                    case "dried_kelp" -> hintItem = Items.DRIED_KELP;
                    case "map" -> hintItem = Items.MAP;
                    case "enchanted_book" -> hintItem = Items.ENCHANTED_BOOK;
                    case "enchanted_golden_apple" -> hintItem = Items.ENCHANTED_GOLDEN_APPLE;
                    case "filled_map" -> hintItem = Items.FILLED_MAP;
                    case "fishing_rod" -> hintItem = Items.FISHING_ROD;
                    case "glass_bottle" -> hintItem = Items.GLASS_BOTTLE;
                    case "golden_apple" -> hintItem = Items.GOLDEN_APPLE;
                    case "golden_axe" -> hintItem = Items.GOLDEN_AXE;
                    case "golden_boots" -> hintItem = Items.GOLDEN_BOOTS;
                    case "golden_carrot" -> hintItem = Items.GOLDEN_CARROT;
                    case "golden_chestplate" -> hintItem = Items.GOLDEN_CHESTPLATE;
                    case "golden_helmet" -> hintItem = Items.GOLDEN_HELMET;
                    case "golden_hoe" -> hintItem = Items.GOLDEN_HOE;
                    case "golden_horse_armor" -> hintItem = Items.GOLDEN_HORSE_ARMOR;
                    case "golden_leggings" -> hintItem = Items.GOLDEN_LEGGINGS;
                    case "golden_pickaxe" -> hintItem = Items.GOLDEN_PICKAXE;
                    case "golden_shovel" -> hintItem = Items.GOLDEN_SHOVEL;
                    case "golden_sword" -> hintItem = Items.GOLDEN_SWORD;
                    case "gray_dye" -> hintItem = Items.GRAY_DYE;
                    case "green_dye" -> hintItem = Items.GREEN_DYE;
                    case "ink_sac" -> hintItem = Items.INK_SAC;
                    case "iron_axe" -> hintItem = Items.IRON_AXE;
                    case "iron_boots" -> hintItem = Items.IRON_BOOTS;
                    case "iron_chestplate" -> hintItem = Items.IRON_CHESTPLATE;
                    case "iron_helmet" -> hintItem = Items.IRON_HELMET;
                    case "iron_hoe" -> hintItem = Items.IRON_HOE;
                    case "iron_horse_armor" -> hintItem = Items.IRON_HORSE_ARMOR;
                    case "iron_leggings" -> hintItem = Items.IRON_LEGGINGS;
                    case "iron_nugget" -> hintItem = Items.IRON_NUGGET;
                    case "iron_pickaxe" -> hintItem = Items.IRON_PICKAXE;
                    case "iron_shovel" -> hintItem = Items.IRON_SHOVEL;
                    case "iron_sword" -> hintItem = Items.IRON_SWORD;
                    case "lapis_lazuli" -> hintItem = Items.LAPIS_LAZULI;
                    case "leather_boots" -> hintItem = Items.LEATHER_BOOTS;
                    case "leather_helmet" -> hintItem = Items.LEATHER_HELMET;
                    case "leather_horse_armor" -> hintItem = Items.LEATHER_HORSE_ARMOR;
                    case "leather_leggings" -> hintItem = Items.LEATHER_LEGGINGS;
                    case "leather_chestplate" -> hintItem = Items.LEATHER_CHESTPLATE;
                    case "light_blue_dye" -> hintItem = Items.LIGHT_BLUE_DYE;
                    case "light_gray_dye" -> hintItem = Items.LIGHT_GRAY_DYE;
                    case "lime_dye" -> hintItem = Items.LIME_DYE;
                    case "magenta_dye" -> hintItem = Items.MAGENTA_DYE;
                    case "melon_slice" -> hintItem = Items.MELON_SLICE;
                    case "milk_bucket" -> hintItem = Items.MILK_BUCKET;
                    case "mushroom_stew" -> hintItem = Items.MUSHROOM_STEW;
                    case "music_disc_ward" -> hintItem = Items.MUSIC_DISC_WARD;
                    case "music_disc_wait" -> hintItem = Items.MUSIC_DISC_WAIT;
                    case "music_disc_stal" -> hintItem = Items.MUSIC_DISC_STAL;
                    case "music_disc_mellohi" -> hintItem = Items.MUSIC_DISC_MELLOHI;
                    case "music_disc_mall" -> hintItem = Items.MUSIC_DISC_MALL;
                    case "music_disc_far" -> hintItem = Items.MUSIC_DISC_FAR;
                    case "music_disc_chirp" -> hintItem = Items.MUSIC_DISC_CHIRP;
                    case "music_disc_cat" -> hintItem = Items.MUSIC_DISC_CAT;
                    case "music_disc_blocks" -> hintItem = Items.MUSIC_DISC_BLOCKS;
                    case "music_disc_13" -> hintItem = Items.MUSIC_DISC_13;
                    case "music_disc_11" -> hintItem = Items.MUSIC_DISC_11;
                    case "music_disc_strad" -> hintItem = Items.MUSIC_DISC_STRAD;
                    case "name_tag" -> hintItem = Items.NAME_TAG;
                    case "orange_dye" -> hintItem = Items.ORANGE_DYE;
                    case "pink_dye" -> hintItem = Items.PINK_DYE;
                    case "poisonous_potato" -> hintItem = Items.POISONOUS_POTATO;
                    case "potion" -> hintItem = Items.POTION;
                    case "pufferfish" -> hintItem = Items.PUFFERFISH;
                    case "pumpkin_pie" -> hintItem = Items.PUMPKIN_PIE;
                    case "purple_dye" -> hintItem = Items.PURPLE_DYE;
                    case "rabbit_stew" -> hintItem = Items.RABBIT_STEW;
                    case "beef" -> hintItem = Items.BEEF;
                    case "chicken" -> hintItem = Items.CHICKEN;
                    case "cod" -> hintItem = Items.COD;
                    case "mutton" -> hintItem = Items.MUTTON;
                    case "porkchop" -> hintItem = Items.PORKCHOP;
                    case "rabbit" -> hintItem = Items.RABBIT;
                    case "salmon" -> hintItem = Items.SALMON;
                    case "red_dye" -> hintItem = Items.RED_DYE;
                    case "rotten_flesh" -> hintItem = Items.ROTTEN_FLESH;
                    case "saddle" -> hintItem = Items.SADDLE;
                    case "shears" -> hintItem = Items.SHEARS;
                    case "shield" -> hintItem = Items.SHIELD;
                    case "spectral_arrow" -> hintItem = Items.SPECTRAL_ARROW;
                    case "spider_eye" -> hintItem = Items.SPIDER_EYE;
                    case "cooked_beef" -> hintItem = Items.COOKED_BEEF;
                    case "stone_axe" -> hintItem = Items.STONE_AXE;
                    case "stone_hoe" -> hintItem = Items.STONE_HOE;
                    case "stone_pickaxe" -> hintItem = Items.STONE_PICKAXE;
                    case "stone_shovel" -> hintItem = Items.STONE_SHOVEL;
                    case "stone_sword" -> hintItem = Items.STONE_SWORD;
                    case "sugar" -> hintItem = Items.SUGAR;
                    case "suspicious_stew" -> hintItem = Items.SUSPICIOUS_STEW;
                    case "tipped_arrow" -> hintItem = Items.TIPPED_ARROW;
                    case "totem_of_undying" -> hintItem = Items.TOTEM_OF_UNDYING;
                    case "tropical_fish" -> hintItem = Items.TROPICAL_FISH;
                    case "turtle_helmet" -> hintItem = Items.TURTLE_HELMET;
                    case "wheat" -> hintItem = Items.WHEAT;
                    case "white_dye" -> hintItem = Items.WHITE_DYE;
                    case "wooden_axe" -> hintItem = Items.WOODEN_AXE;
                    case "wooden_hoe" -> hintItem = Items.WOODEN_HOE;
                    case "wooden_pickaxe" -> hintItem = Items.WOODEN_PICKAXE;
                    case "wooden_shovel" -> hintItem = Items.WOODEN_SHOVEL;
                    case "wooden_sword" -> hintItem = Items.WOODEN_SWORD;
                    case "written_book" -> hintItem = Items.WRITTEN_BOOK;
                    case "yellow_dye" -> hintItem = Items.YELLOW_DYE;
                    case "mojang_banner_pattern" -> hintItem = Items.MOJANG_BANNER_PATTERN;
                    case "skull_banner_pattern" -> hintItem = Items.SKULL_BANNER_PATTERN;
                    case "creeper_banner_pattern" -> hintItem = Items.CREEPER_BANNER_PATTERN;
                    case "globe_banner_pattern" -> hintItem = Items.GLOBE_BANNER_PATTERN;
                    case "flower_banner_pattern" -> hintItem = Items.FLOWER_BANNER_PATTERN;
                    case "blaze_powder" -> hintItem = Items.BLAZE_POWDER;
                    case "blaze_rod" -> hintItem = Items.BLAZE_ROD;
                    case "book" -> hintItem = Items.BOOK;
                    case "brick" -> hintItem = Items.BRICK;
                    case "clay_ball" -> hintItem = Items.CLAY_BALL;
                    case "clock" -> hintItem = Items.CLOCK;
                    case "compass" -> hintItem = Items.COMPASS;
                    case "diamond" -> hintItem = Items.DIAMOND;
                    case "dragon_breath" -> hintItem = Items.DRAGON_BREATH;
                    case "emerald" -> hintItem = Items.EMERALD;
                    case "feather" -> hintItem = Items.FEATHER;
                    case "fermented_spider_eye" -> hintItem = Items.FERMENTED_SPIDER_EYE;
                    case "firework_star" -> hintItem = Items.FIREWORK_STAR;
                    case "flint" -> hintItem = Items.FLINT;
                    case "flint_and_steel" -> hintItem = Items.FLINT_AND_STEEL;
                    case "glistering_melon_slice" -> hintItem = Items.GLISTERING_MELON_SLICE;
                    case "glowstone_dust" -> hintItem = Items.GLOWSTONE_DUST;
                    case "gold_ingot" -> hintItem = Items.GOLD_INGOT;
                    case "gold_nugget" -> hintItem = Items.GOLD_NUGGET;
                    case "ghast_tear" -> hintItem = Items.GHAST_TEAR;
                    case "gunpowder" -> hintItem = Items.GUNPOWDER;
                    case "heart_of_the_sea" -> hintItem = Items.HEART_OF_THE_SEA;
                    case "iron_ingot" -> hintItem = Items.IRON_INGOT;
                    case "leather" -> hintItem = Items.LEATHER;
                    case "magma_cream" -> hintItem = Items.MAGMA_CREAM;
                    case "nautilus_shell" -> hintItem = Items.NAUTILUS_SHELL;
                    case "nether_brick" -> hintItem = Items.NETHER_BRICK;
                    case "quartz" -> hintItem = Items.QUARTZ;
                    case "nether_star" -> hintItem = Items.NETHER_STAR;
                    case "paper" -> hintItem = Items.PAPER;
                    case "phantom_membrane" -> hintItem = Items.PHANTOM_MEMBRANE;
                    case "popped_chorus_fruit" -> hintItem = Items.POPPED_CHORUS_FRUIT;
                    case "prismarine_crystals" -> hintItem = Items.PRISMARINE_CRYSTALS;
                    case "prismarine_shard" -> hintItem = Items.PRISMARINE_SHARD;
                    case "rabbit_hide" -> hintItem = Items.RABBIT_HIDE;
                    case "rabbit_foot" -> hintItem = Items.RABBIT_FOOT;
                    case "scute" -> hintItem = Items.SCUTE;
                    case "shulker_shell" -> hintItem = Items.SHULKER_SHELL;
                    case "slime_ball" -> hintItem = Items.SLIME_BALL;
                    case "stick" -> hintItem = Items.STICK;
                    case "honey_bottle" -> hintItem = Items.HONEY_BOTTLE;
                    case "honeycomb" -> hintItem = Items.HONEYCOMB;
                    case "netherite_helmet" -> hintItem = Items.NETHERITE_HELMET;
                    case "netherite_chestplate" -> hintItem = Items.NETHERITE_CHESTPLATE;
                    case "netherite_leggings" -> hintItem = Items.NETHERITE_LEGGINGS;
                    case "netherite_boots" -> hintItem = Items.NETHERITE_BOOTS;
                    case "netherite_axe" -> hintItem = Items.NETHERITE_AXE;
                    case "netherite_hoe" -> hintItem = Items.NETHERITE_HOE;
                    case "netherite_sword" -> hintItem = Items.NETHERITE_SWORD;
                    case "netherite_pickaxe" -> hintItem = Items.NETHERITE_PICKAXE;
                    case "netherite_shovel" -> hintItem = Items.NETHERITE_SHOVEL;
                    case "netherite_ingot" -> hintItem = Items.NETHERITE_INGOT;
                    case "netherite_scrap" -> hintItem = Items.NETHERITE_SCRAP;
                    case "warped_fungus_on_a_stick" -> hintItem = Items.WARPED_FUNGUS_ON_A_STICK;
                    case "piglin_banner_pattern" -> hintItem = Items.PIGLIN_BANNER_PATTERN;
                    case "elytra" -> hintItem = Items.ELYTRA;
                    case "music_disc_pigstep" -> hintItem = Items.MUSIC_DISC_PIGSTEP;
                    case "amethyst_shard" -> hintItem = Items.AMETHYST_SHARD;
                    case "axolotl_bucket" -> hintItem = Items.AXOLOTL_BUCKET;
                    case "copper_ingot" -> hintItem = Items.COPPER_INGOT;
                    case "glow_berries" -> hintItem = Items.GLOW_BERRIES;
                    case "glow_ink_sac" -> hintItem = Items.GLOW_INK_SAC;
                    case "glow_item_frame" -> hintItem = Items.GLOW_ITEM_FRAME;
                    case "powder_snow_bucket" -> hintItem = Items.POWDER_SNOW_BUCKET;
                    case "spyglass" -> hintItem = Items.SPYGLASS;
                    case "raw_iron" -> hintItem = Items.RAW_IRON;
                    case "raw_copper" -> hintItem = Items.RAW_COPPER;
                    case "raw_gold" -> hintItem = Items.RAW_GOLD;
                    case "music_disc_otherside" -> hintItem = Items.MUSIC_DISC_OTHERSIDE;
                    case "tadpole_bucket" -> hintItem = Items.TADPOLE_BUCKET;
                    case "disc_fragment_5" -> hintItem = Items.DISC_FRAGMENT_5;
                    case "echo_shard" -> hintItem = Items.ECHO_SHARD;
                    case "goat_horn" -> hintItem = Items.GOAT_HORN;
                    case "mangrove_boat" -> hintItem = Items.MANGROVE_BOAT;
                    case "music_disc_5" -> hintItem = Items.MUSIC_DISC_5;
                    case "oak_chest_boat" -> hintItem = Items.OAK_CHEST_BOAT;
                    case "spruce_chest_boat" -> hintItem = Items.SPRUCE_CHEST_BOAT;
                    case "birch_chest_boat" -> hintItem = Items.BIRCH_CHEST_BOAT;
                    case "jungle_chest_boat" -> hintItem = Items.JUNGLE_CHEST_BOAT;
                    case "acacia_chest_boat" -> hintItem = Items.ACACIA_CHEST_BOAT;
                    case "dark_oak_chest_boat" -> hintItem = Items.DARK_OAK_CHEST_BOAT;
                    case "mangrove_chest_boat" -> hintItem = Items.MANGROVE_CHEST_BOAT;
                    case "brush" -> hintItem = Items.BRUSH;
                    case "music_disc_relic" -> hintItem = Items.MUSIC_DISC_RELIC;
                    case "pitcher_pod" -> hintItem = Items.PITCHER_POD;
                    case "torchflower_seeds" -> hintItem = Items.TORCHFLOWER_SEEDS;
                    case "bamboo_raft" -> hintItem = Items.BAMBOO_RAFT;
                    case "bamboo_chest_raft" -> hintItem = Items.BAMBOO_CHEST_RAFT;
                    case "cherry_boat" -> hintItem = Items.CHERRY_BOAT;
                    case "cherry_chest_boat" -> hintItem = Items.CHERRY_CHEST_BOAT;
                    case "smithing_template" -> hintItem = Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:enchanting/armor_for_the_masses" -> {
                switch (criterion) {
                    case "protection" -> hintItem = Items.BARRIER;
                    case "fire_protection" -> hintItem = Items.BARRIER;
                    case "blast_protection" -> hintItem = Items.BARRIER;
                    case "projectile_protection" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:enchanting/baron_of_blacksmiths" -> {
                switch (criterion) {
                    case "anvil" -> hintItem = Items.ANVIL;
                    case "chipped_anvil" -> hintItem = Items.CHIPPED_ANVIL;
                    case "damaged_anvil" -> hintItem = Items.DAMAGED_ANVIL;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:enchanting/complete_enchanter" -> {
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:enchanting/curses" -> {
                switch (criterion) {
                    case "binding_curse" -> hintItem = Items.BARRIER;
                    case "vanishing_curse" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:enchanting/fiery" -> {
                switch (criterion) {
                    case "fire_protection" -> hintItem = Items.BARRIER;
                    case "fire_aspect" -> hintItem = Items.BARRIER;
                    case "flame" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:enchanting/knocking_your_socks_off" -> {
                switch (criterion) {
                    case "knockback" -> hintItem = Items.BARRIER;
                    case "punch" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:enchanting/master_armorer" -> {
                switch (criterion) {
                    case "helmet_protection" -> hintItem = Items.BARRIER;
                    case "chestplate_protection" -> hintItem = Items.BARRIER;
                    case "leggings_protection" -> hintItem = Items.BARRIER;
                    case "boots_protection_depth_strider" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:enchanting/master_enchanter" -> {
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:enchanting/scuba_gear" -> {
                switch (criterion) {
                    case "respiration" -> hintItem = Items.BARRIER;
                    case "aqua_affinity" -> hintItem = Items.BARRIER;
                    case "depth_strider" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:end/purpurfect" -> {
                switch (criterion) {
                    case "purpur_block" -> hintItem = Items.PURPUR_BLOCK;
                    case "purpur_pillar" -> hintItem = Items.PURPUR_PILLAR;
                    case "purpur_slab" -> hintItem = Items.PURPUR_SLAB;
                    case "purpur_stairs" -> hintItem = Items.PURPUR_STAIRS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:end/somewhere_over_the_rainbow" -> {
                switch (criterion) {
                    case "white_shulker_box" -> hintItem = Items.WHITE_SHULKER_BOX;
                    case "orange_shulker_box" -> hintItem = Items.ORANGE_SHULKER_BOX;
                    case "magenta_shulker_box" -> hintItem = Items.MAGENTA_SHULKER_BOX;
                    case "light_blue_shulker_box" -> hintItem = Items.LIGHT_BLUE_SHULKER_BOX;
                    case "yellow_shulker_box" -> hintItem = Items.YELLOW_SHULKER_BOX;
                    case "lime_shulker_box" -> hintItem = Items.LIME_SHULKER_BOX;
                    case "pink_shulker_box" -> hintItem = Items.PINK_SHULKER_BOX;
                    case "gray_shulker_box" -> hintItem = Items.GRAY_SHULKER_BOX;
                    case "light_gray_shulker_box" -> hintItem = Items.LIGHT_GRAY_SHULKER_BOX;
                    case "cyan_shulker_box" -> hintItem = Items.CYAN_SHULKER_BOX;
                    case "purple_shulker_box" -> hintItem = Items.PURPLE_SHULKER_BOX;
                    case "blue_shulker_box" -> hintItem = Items.BLUE_SHULKER_BOX;
                    case "brown_shulker_box" -> hintItem = Items.BROWN_SHULKER_BOX;
                    case "green_shulker_box" -> hintItem = Items.GREEN_SHULKER_BOX;
                    case "red_shulker_box" -> hintItem = Items.RED_SHULKER_BOX;
                    case "black_shulker_box" -> hintItem = Items.BLACK_SHULKER_BOX;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:end/void_walker" -> {
                switch (criterion) {
                    case "the_end" -> hintItem = Items.BARRIER;
                    case "small_end_islands" -> hintItem = Items.BARRIER;
                    case "end_highlands" -> hintItem = Items.BARRIER;
                    case "end_midlands" -> hintItem = Items.BARRIER;
                    case "end_barrens" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:farming/a_gluttonous_diet" -> {
                switch (criterion) {
                    case "apple" -> hintItem = Items.APPLE;
                    case "mushroom_stew" -> hintItem = Items.MUSHROOM_STEW;
                    case "bread" -> hintItem = Items.BREAD;
                    case "porkchop" -> hintItem = Items.PORKCHOP;
                    case "cooked_porkchop" -> hintItem = Items.COOKED_PORKCHOP;
                    case "golden_apple" -> hintItem = Items.GOLDEN_APPLE;
                    case "enchanted_golden_apple" -> hintItem = Items.ENCHANTED_GOLDEN_APPLE;
                    case "raw_cod" -> hintItem = Items.COD;
                    case "raw_salmon" -> hintItem = Items.SALMON;
                    case "tropical_fish" -> hintItem = Items.TROPICAL_FISH;
                    case "pufferfish" -> hintItem = Items.PUFFERFISH;
                    case "cooked_cod" -> hintItem = Items.COOKED_COD;
                    case "cooked_salmon" -> hintItem = Items.COOKED_SALMON;
                    case "cookie" -> hintItem = Items.COOKIE;
                    case "melon_slice" -> hintItem = Items.MELON_SLICE;
                    case "beef" -> hintItem = Items.BEEF;
                    case "cooked_beef" -> hintItem = Items.COOKED_BEEF;
                    case "chicken" -> hintItem = Items.CHICKEN;
                    case "cooked_chicken" -> hintItem = Items.COOKED_CHICKEN;
                    case "rotten_flesh" -> hintItem = Items.ROTTEN_FLESH;
                    case "spider_eye" -> hintItem = Items.SPIDER_EYE;
                    case "carrot" -> hintItem = Items.CARROT;
                    case "potato" -> hintItem = Items.POTATO;
                    case "baked_potato" -> hintItem = Items.BAKED_POTATO;
                    case "poisonous_potato" -> hintItem = Items.POISONOUS_POTATO;
                    case "golden_carrot" -> hintItem = Items.GOLDEN_CARROT;
                    case "pumpkin_pie" -> hintItem = Items.PUMPKIN_PIE;
                    case "rabbit" -> hintItem = Items.RABBIT;
                    case "cooked_rabbit" -> hintItem = Items.COOKED_RABBIT;
                    case "rabbit_stew" -> hintItem = Items.RABBIT_STEW;
                    case "mutton" -> hintItem = Items.MUTTON;
                    case "cooked_mutton" -> hintItem = Items.COOKED_MUTTON;
                    case "chorus_fruit" -> hintItem = Items.CHORUS_FRUIT;
                    case "beetroot" -> hintItem = Items.BEETROOT;
                    case "beetroot_soup" -> hintItem = Items.BEETROOT_SOUP;
                    case "dried_kelp" -> hintItem = Items.DRIED_KELP;
                    case "suspicious_stew" -> hintItem = Items.SUSPICIOUS_STEW;
                    case "sweet_berries" -> hintItem = Items.SWEET_BERRIES;
                    case "honey_bottle" -> hintItem = Items.HONEY_BOTTLE;
                    case "glow_berries" -> hintItem = Items.GLOW_BERRIES;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:farming/combine_harvester" -> {
                switch (criterion) {
                    case "wheat" -> hintItem = Items.WHEAT;
                    case "carrots" -> hintItem = Items.CARROT;
                    case "potatoes" -> hintItem = Items.POTATO;
                    case "beetroots" -> hintItem = Items.BEETROOT;
                    case "bamboo" -> hintItem = Items.BAMBOO;
                    case "melon_stem" -> hintItem = Items.MELON;
                    case "pumpkin_stem" -> hintItem = Items.PUMPKIN;
                    case "oak_sapling" -> hintItem = Items.OAK_SAPLING;
                    case "spruce_sapling" -> hintItem = Items.SPRUCE_SAPLING;
                    case "birch_sapling" -> hintItem = Items.BIRCH_SAPLING;
                    case "jungle_sapling" -> hintItem = Items.JUNGLE_SAPLING;
                    case "acacia_sapling" -> hintItem = Items.ACACIA_SAPLING;
                    case "dark_oak_sapling" -> hintItem = Items.DARK_OAK_SAPLING;
                    case "sunflower" -> hintItem = Items.SUNFLOWER;
                    case "lilac" -> hintItem = Items.LILAC;
                    case "rose_bush" -> hintItem = Items.ROSE_BUSH;
                    case "peony" -> hintItem = Items.PEONY;
                    case "tall_grass" -> hintItem = Items.TALL_GRASS;
                    case "large_fern" -> hintItem = Items.LARGE_FERN;
                    case "tall_seagrass" -> hintItem = Items.SEAGRASS;
                    case "red_mushroom" -> hintItem = Items.RED_MUSHROOM;
                    case "brown_mushroom" -> hintItem = Items.BROWN_MUSHROOM;
                    case "cocoa" -> hintItem = Items.COCOA_BEANS;
                    case "sweet_berry_bush" -> hintItem = Items.SWEET_BERRIES;
                    case "sea_pickle" -> hintItem = Items.SEA_PICKLE;
                    case "kelp" -> hintItem = Items.KELP;
                    case "weeping_vines" -> hintItem = Items.WEEPING_VINES;
                    case "twisting_vines" -> hintItem = Items.TWISTING_VINES;
                    case "crimson_fungus" -> hintItem = Items.CRIMSON_FUNGUS;
                    case "warped_fungus" -> hintItem = Items.WARPED_FUNGUS;
                    case "glow_lichen" -> hintItem = Items.GLOW_LICHEN;
                    case "dripleaves" -> hintItem = Items.BIG_DRIPLEAF;
                    case "cave_vines" -> hintItem = Items.GLOW_BERRIES;
                    case "azalea" -> hintItem = Items.AZALEA;
                    case "flowering_azalea" -> hintItem = Items.FLOWERING_AZALEA;
                    case "rooted_dirt" -> hintItem = Items.ROOTED_DIRT;
                    case "mangrove_propagule" -> hintItem = Items.MANGROVE_PROPAGULE;
                    case "mangrove_leaves" -> hintItem = Items.MANGROVE_LEAVES;
                    case "cherry_sapling" -> hintItem = Items.CHERRY_SAPLING;
                    case "pink_petals" -> hintItem = Items.PINK_PETALS;
                    case "torchflower_seeds" -> hintItem = Items.TORCHFLOWER_SEEDS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:farming/come_to_the_countryside" -> {
                switch (criterion) {
                    case "wheat" -> hintItem = Items.WHEAT;
                    case "pumpkin_stem" -> hintItem = Items.PUMPKIN;
                    case "melon_stem" -> hintItem = Items.MELON;
                    case "beetroots" -> hintItem = Items.BEETROOT;
                    case "carrots" -> hintItem = Items.CARROT;
                    case "potatoes" -> hintItem = Items.POTATO;
                    case "torchflower_crop" -> hintItem = Items.TORCHFLOWER;
                    case "pitcher_crop" -> hintItem = Items.PITCHER_PLANT;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:farming/ecologist" -> {
                switch (criterion) {
                    case "oak_sapling" -> hintItem = Items.OAK_SAPLING;
                    case "spruce_sapling" -> hintItem = Items.SPRUCE_SAPLING;
                    case "birch_sapling" -> hintItem = Items.BIRCH_SAPLING;
                    case "jungle_sapling" -> hintItem = Items.JUNGLE_SAPLING;
                    case "acacia_sapling" -> hintItem = Items.ACACIA_SAPLING;
                    case "dark_oak_sapling" -> hintItem = Items.DARK_OAK_SAPLING;
                    case "azalea" -> hintItem = Items.AZALEA;
                    case "flowering_azalea" -> hintItem = Items.FLOWERING_AZALEA;
                    case "mangrove_propagule" -> hintItem = Items.MANGROVE_PROPAGULE;
                    case "bamboo_sapling" -> hintItem = Items.BAMBOO;
                    case "cherry_sapling" -> hintItem = Items.CHERRY_SAPLING;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:farming/meat_lovers" -> {
                switch (criterion) {
                    case "porkchop" -> hintItem = Items.PORKCHOP;
                    case "cooked_porkchop" -> hintItem = Items.COOKED_PORKCHOP;
                    case "raw_cod" -> hintItem = Items.COD;
                    case "raw_salmon" -> hintItem = Items.SALMON;
                    case "tropical_fish" -> hintItem = Items.TROPICAL_FISH;
                    case "pufferfish" -> hintItem = Items.PUFFERFISH;
                    case "cooked_cod" -> hintItem = Items.COOKED_COD;
                    case "cooked_salmon" -> hintItem = Items.COOKED_SALMON;
                    case "beef" -> hintItem = Items.BEEF;
                    case "cooked_beef" -> hintItem = Items.COOKED_BEEF;
                    case "chicken" -> hintItem = Items.CHICKEN;
                    case "cooked_chicken" -> hintItem = Items.COOKED_CHICKEN;
                    case "rotten_flesh" -> hintItem = Items.ROTTEN_FLESH;
                    case "spider_eye" -> hintItem = Items.SPIDER_EYE;
                    case "rabbit" -> hintItem = Items.RABBIT;
                    case "cooked_rabbit" -> hintItem = Items.COOKED_RABBIT;
                    case "rabbit_stew" -> hintItem = Items.RABBIT_STEW;
                    case "mutton" -> hintItem = Items.MUTTON;
                    case "cooked_mutton" -> hintItem = Items.COOKED_MUTTON;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:farming/shrooms" -> {
                switch (criterion) {
                    case "red_mushroom" -> hintItem = Items.RED_MUSHROOM;
                    case "brown_mushroom" -> hintItem = Items.BROWN_MUSHROOM;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:farming/vegetarian" -> {
                switch (criterion) {
                    case "apple" -> hintItem = Items.APPLE;
                    case "mushroom_stew" -> hintItem = Items.MUSHROOM_STEW;
                    case "bread" -> hintItem = Items.BREAD;
                    case "golden_apple" -> hintItem = Items.GOLDEN_APPLE;
                    case "enchanted_golden_apple" -> hintItem = Items.ENCHANTED_GOLDEN_APPLE;
                    case "melon_slice" -> hintItem = Items.MELON_SLICE;
                    case "carrot" -> hintItem = Items.CARROT;
                    case "potato" -> hintItem = Items.POTATO;
                    case "baked_potato" -> hintItem = Items.BAKED_POTATO;
                    case "poisonous_potato" -> hintItem = Items.POISONOUS_POTATO;
                    case "golden_carrot" -> hintItem = Items.GOLDEN_CARROT;
                    case "pumpkin_pie" -> hintItem = Items.PUMPKIN_PIE;
                    case "chorus_fruit" -> hintItem = Items.CHORUS_FRUIT;
                    case "beetroot" -> hintItem = Items.BEETROOT;
                    case "beetroot_soup" -> hintItem = Items.BEETROOT_SOUP;
                    case "dried_kelp" -> hintItem = Items.DRIED_KELP;
                    case "suspicious_stew" -> hintItem = Items.SUSPICIOUS_STEW;
                    case "sweet_berries" -> hintItem = Items.SWEET_BERRIES;
                    case "honey_bottle" -> hintItem = Items.HONEY_BOTTLE;
                    case "glow_berries" -> hintItem = Items.GLOW_BERRIES;
                    case "cookie" -> hintItem = Items.COOKIE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:farming/whats_new_with_composting" -> {
                switch (criterion) {
                    case "beetroot_seeds" -> hintItem = Items.BEETROOT_SEEDS;
                    case "dried_kelp" -> hintItem = Items.DRIED_KELP;
                    case "grass" -> hintItem = Items.GRASS;
                    case "kelp" -> hintItem = Items.KELP;
                    case "oak_leaves" -> hintItem = Items.OAK_LEAVES;
                    case "spruce_leaves" -> hintItem = Items.SPRUCE_LEAVES;
                    case "birch_leaves" -> hintItem = Items.BIRCH_LEAVES;
                    case "jungle_leaves" -> hintItem = Items.JUNGLE_LEAVES;
                    case "acacia_leaves" -> hintItem = Items.ACACIA_LEAVES;
                    case "dark_oak_leaves" -> hintItem = Items.DARK_OAK_LEAVES;
                    case "melon_seeds" -> hintItem = Items.MELON_SEEDS;
                    case "pumpkin_seeds" -> hintItem = Items.PUMPKIN_SEEDS;
                    case "oak_sapling" -> hintItem = Items.OAK_SAPLING;
                    case "spruce_sapling" -> hintItem = Items.SPRUCE_SAPLING;
                    case "birch_sapling" -> hintItem = Items.BIRCH_SAPLING;
                    case "jungle_sapling" -> hintItem = Items.JUNGLE_SAPLING;
                    case "acacia_sapling" -> hintItem = Items.ACACIA_SAPLING;
                    case "dark_oak_sapling" -> hintItem = Items.DARK_OAK_SAPLING;
                    case "seagrass" -> hintItem = Items.SEAGRASS;
                    case "sweet_berries" -> hintItem = Items.SWEET_BERRIES;
                    case "wheat_seeds" -> hintItem = Items.WHEAT_SEEDS;
                    case "cactus" -> hintItem = Items.CACTUS;
                    case "dried_kelp_block" -> hintItem = Items.DRIED_KELP_BLOCK;
                    case "melon_slice" -> hintItem = Items.MELON_SLICE;
                    case "sugar_cane" -> hintItem = Items.SUGAR_CANE;
                    case "tall_grass" -> hintItem = Items.TALL_GRASS;
                    case "vine" -> hintItem = Items.VINE;
                    case "weeping_vines" -> hintItem = Items.WEEPING_VINES;
                    case "twisting_vines" -> hintItem = Items.TWISTING_VINES;
                    case "nether_sprouts" -> hintItem = Items.NETHER_SPROUTS;
                    case "apple" -> hintItem = Items.APPLE;
                    case "beetroot" -> hintItem = Items.BEETROOT;
                    case "carrot" -> hintItem = Items.CARROT;
                    case "cocoa_beans" -> hintItem = Items.COCOA_BEANS;
                    case "fern" -> hintItem = Items.FERN;
                    case "large_fern" -> hintItem = Items.LARGE_FERN;
                    case "dandelion" -> hintItem = Items.DANDELION;
                    case "poppy" -> hintItem = Items.POPPY;
                    case "blue_orchid" -> hintItem = Items.BLUE_ORCHID;
                    case "allium" -> hintItem = Items.ALLIUM;
                    case "azure_bluet" -> hintItem = Items.AZURE_BLUET;
                    case "red_tulip" -> hintItem = Items.RED_TULIP;
                    case "orange_tulip" -> hintItem = Items.ORANGE_TULIP;
                    case "white_tulip" -> hintItem = Items.WHITE_TULIP;
                    case "pink_tulip" -> hintItem = Items.PINK_TULIP;
                    case "oxeye_daisy" -> hintItem = Items.OXEYE_DAISY;
                    case "cornflower" -> hintItem = Items.CORNFLOWER;
                    case "lily_of_the_valley" -> hintItem = Items.LILY_OF_THE_VALLEY;
                    case "wither_rose" -> hintItem = Items.WITHER_ROSE;
                    case "sunflower" -> hintItem = Items.SUNFLOWER;
                    case "lilac" -> hintItem = Items.LILAC;
                    case "rose_bush" -> hintItem = Items.ROSE_BUSH;
                    case "peony" -> hintItem = Items.PEONY;
                    case "lily_pad" -> hintItem = Items.LILY_PAD;
                    case "melon" -> hintItem = Items.MELON;
                    case "red_mushroom" -> hintItem = Items.RED_MUSHROOM;
                    case "brown_mushroom" -> hintItem = Items.BROWN_MUSHROOM;
                    case "potato" -> hintItem = Items.POTATO;
                    case "pumpkin" -> hintItem = Items.PUMPKIN;
                    case "carved_pumpkin" -> hintItem = Items.CARVED_PUMPKIN;
                    case "sea_pickle" -> hintItem = Items.SEA_PICKLE;
                    case "wheat" -> hintItem = Items.WHEAT;
                    case "crimson_fungus" -> hintItem = Items.CRIMSON_FUNGUS;
                    case "warped_fungus" -> hintItem = Items.WARPED_FUNGUS;
                    case "nether_wart" -> hintItem = Items.NETHER_WART;
                    case "crimson_roots" -> hintItem = Items.CRIMSON_ROOTS;
                    case "warped_roots" -> hintItem = Items.WARPED_ROOTS;
                    case "baked_potato" -> hintItem = Items.BAKED_POTATO;
                    case "bread" -> hintItem = Items.BREAD;
                    case "cookie" -> hintItem = Items.COOKIE;
                    case "hay_block" -> hintItem = Items.HAY_BLOCK;
                    case "red_mushroom_block" -> hintItem = Items.RED_MUSHROOM_BLOCK;
                    case "brown_mushroom_block" -> hintItem = Items.BROWN_MUSHROOM_BLOCK;
                    case "mushroom_stem" -> hintItem = Items.MUSHROOM_STEM;
                    case "nether_wart_block" -> hintItem = Items.NETHER_WART_BLOCK;
                    case "warped_wart_block" -> hintItem = Items.WARPED_WART_BLOCK;
                    case "cake" -> hintItem = Items.CAKE;
                    case "pumpkin_pie" -> hintItem = Items.PUMPKIN_PIE;
                    case "shroomlight" -> hintItem = Items.SHROOMLIGHT;
                    case "glow_berries" -> hintItem = Items.GLOW_BERRIES;
                    case "azalea_leaves" -> hintItem = Items.AZALEA_LEAVES;
                    case "flowering_azalea_leaves" -> hintItem = Items.FLOWERING_AZALEA_LEAVES;
                    case "small_dripleaf" -> hintItem = Items.SMALL_DRIPLEAF;
                    case "big_dripleaf" -> hintItem = Items.BIG_DRIPLEAF;
                    case "moss_carpet" -> hintItem = Items.MOSS_CARPET;
                    case "moss_block" -> hintItem = Items.MOSS_BLOCK;
                    case "azalea" -> hintItem = Items.AZALEA;
                    case "flowering_azalea" -> hintItem = Items.FLOWERING_AZALEA;
                    case "hanging_roots" -> hintItem = Items.HANGING_ROOTS;
                    case "glow_lichen" -> hintItem = Items.GLOW_LICHEN;
                    case "spore_blossom" -> hintItem = Items.SPORE_BLOSSOM;
                    case "mangrove_propagule" -> hintItem = Items.MANGROVE_PROPAGULE;
                    case "mangrove_leaves" -> hintItem = Items.MANGROVE_LEAVES;
                    case "mangrove_roots" -> hintItem = Items.MANGROVE_ROOTS;
                    case "cherry_sapling" -> hintItem = Items.CHERRY_SAPLING;
                    case "cherry_leaves" -> hintItem = Items.CHERRY_LEAVES;
                    case "torchflower_seeds" -> hintItem = Items.TORCHFLOWER_SEEDS;
                    case "torchflower" -> hintItem = Items.TORCHFLOWER;
                    case "pink_petals" -> hintItem = Items.PINK_PETALS;
                    case "pitcher_pod" -> hintItem = Items.PITCHER_POD;
                    case "pitcher_plant" -> hintItem = Items.PITCHER_PLANT;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/amethyst_miner" -> {
                switch (criterion) {
                    case "amethyst_block" -> hintItem = Items.AMETHYST_BLOCK;
                    case "small_amethyst_bud" -> hintItem = Items.SMALL_AMETHYST_BUD;
                    case "medium_amethyst_bud" -> hintItem = Items.MEDIUM_AMETHYST_BUD;
                    case "large_amethyst_bud" -> hintItem = Items.LARGE_AMETHYST_BUD;
                    case "amethyst_cluster" -> hintItem = Items.AMETHYST_CLUSTER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/bonfire_night" -> {
                switch (criterion) {
                    case "oak_log" -> hintItem = Items.OAK_LOG;
                    case "spruce_log" -> hintItem = Items.SPRUCE_LOG;
                    case "birch_log" -> hintItem = Items.BIRCH_LOG;
                    case "jungle_log" -> hintItem = Items.JUNGLE_LOG;
                    case "acacia_log" -> hintItem = Items.ACACIA_LOG;
                    case "dark_oak_log" -> hintItem = Items.DARK_OAK_LOG;
                    case "stripped_oak_log" -> hintItem = Items.STRIPPED_OAK_LOG;
                    case "stripped_spruce_log" -> hintItem = Items.STRIPPED_SPRUCE_LOG;
                    case "stripped_birch_log" -> hintItem = Items.STRIPPED_BIRCH_LOG;
                    case "stripped_jungle_log" -> hintItem = Items.STRIPPED_JUNGLE_LOG;
                    case "stripped_acacia_log" -> hintItem = Items.STRIPPED_ACACIA_LOG;
                    case "stripped_dark_oak_log" -> hintItem = Items.STRIPPED_DARK_OAK_LOG;
                    case "oak_wood" -> hintItem = Items.OAK_WOOD;
                    case "spruce_wood" -> hintItem = Items.SPRUCE_WOOD;
                    case "birch_wood" -> hintItem = Items.BIRCH_WOOD;
                    case "jungle_wood" -> hintItem = Items.JUNGLE_WOOD;
                    case "acacia_wood" -> hintItem = Items.ACACIA_WOOD;
                    case "dark_oak_wood" -> hintItem = Items.DARK_OAK_WOOD;
                    case "stripped_oak_wood" -> hintItem = Items.STRIPPED_OAK_WOOD;
                    case "stripped_spruce_wood" -> hintItem = Items.STRIPPED_SPRUCE_WOOD;
                    case "stripped_birch_wood" -> hintItem = Items.STRIPPED_BIRCH_WOOD;
                    case "stripped_jungle_wood" -> hintItem = Items.STRIPPED_JUNGLE_WOOD;
                    case "stripped_acacia_wood" -> hintItem = Items.STRIPPED_ACACIA_WOOD;
                    case "stripped_dark_oak_wood" -> hintItem = Items.STRIPPED_DARK_OAK_WOOD;
                    case "coal_block" -> hintItem = Items.COAL_BLOCK;
                    case "oak_planks" -> hintItem = Items.OAK_PLANKS;
                    case "spruce_planks" -> hintItem = Items.SPRUCE_PLANKS;
                    case "birch_planks" -> hintItem = Items.BIRCH_PLANKS;
                    case "jungle_planks" -> hintItem = Items.JUNGLE_PLANKS;
                    case "acacia_planks" -> hintItem = Items.ACACIA_PLANKS;
                    case "dark_oak_planks" -> hintItem = Items.DARK_OAK_PLANKS;
                    case "oak_slab" -> hintItem = Items.OAK_SLAB;
                    case "spruce_slab" -> hintItem = Items.SPRUCE_SLAB;
                    case "birch_slab" -> hintItem = Items.BIRCH_SLAB;
                    case "jungle_slab" -> hintItem = Items.JUNGLE_SLAB;
                    case "acacia_slab" -> hintItem = Items.ACACIA_SLAB;
                    case "dark_oak_slab" -> hintItem = Items.DARK_OAK_SLAB;
                    case "oak_fence_gate" -> hintItem = Items.OAK_FENCE_GATE;
                    case "spruce_fence_gate" -> hintItem = Items.SPRUCE_FENCE_GATE;
                    case "birch_fence_gate" -> hintItem = Items.BIRCH_FENCE_GATE;
                    case "jungle_fence_gate" -> hintItem = Items.JUNGLE_FENCE_GATE;
                    case "acacia_fence_gate" -> hintItem = Items.ACACIA_FENCE_GATE;
                    case "dark_oak_fence_gate" -> hintItem = Items.DARK_OAK_FENCE_GATE;
                    case "oak_fence" -> hintItem = Items.OAK_FENCE;
                    case "spruce_fence" -> hintItem = Items.SPRUCE_FENCE;
                    case "birch_fence" -> hintItem = Items.BIRCH_FENCE;
                    case "jungle_fence" -> hintItem = Items.JUNGLE_FENCE;
                    case "acacia_fence" -> hintItem = Items.ACACIA_FENCE;
                    case "dark_oak_fence" -> hintItem = Items.DARK_OAK_FENCE;
                    case "oak_stairs" -> hintItem = Items.OAK_STAIRS;
                    case "spruce_stairs" -> hintItem = Items.SPRUCE_STAIRS;
                    case "birch_stairs" -> hintItem = Items.BIRCH_STAIRS;
                    case "jungle_stairs" -> hintItem = Items.JUNGLE_STAIRS;
                    case "acacia_stairs" -> hintItem = Items.ACACIA_STAIRS;
                    case "dark_oak_stairs" -> hintItem = Items.DARK_OAK_STAIRS;
                    case "composter" -> hintItem = Items.COMPOSTER;
                    case "beehive" -> hintItem = Items.BEEHIVE;
                    case "target" -> hintItem = Items.TARGET;
                    case "tnt" -> hintItem = Items.TNT;
                    case "vine" -> hintItem = Items.VINE;
                    case "bookshelf" -> hintItem = Items.BOOKSHELF;
                    case "lectern" -> hintItem = Items.LECTERN;
                    case "bee_nest" -> hintItem = Items.BEE_NEST;
                    case "oak_leaves" -> hintItem = Items.OAK_LEAVES;
                    case "spruce_leaves" -> hintItem = Items.SPRUCE_LEAVES;
                    case "birch_leaves" -> hintItem = Items.BIRCH_LEAVES;
                    case "jungle_leaves" -> hintItem = Items.JUNGLE_LEAVES;
                    case "acacia_leaves" -> hintItem = Items.ACACIA_LEAVES;
                    case "dark_oak_leaves" -> hintItem = Items.DARK_OAK_LEAVES;
                    case "azalea_leaves" -> hintItem = Items.AZALEA_LEAVES;
                    case "flowering_azalea_leaves" -> hintItem = Items.FLOWERING_AZALEA_LEAVES;
                    case "white_wool" -> hintItem = Items.WHITE_WOOL;
                    case "orange_wool" -> hintItem = Items.ORANGE_WOOL;
                    case "magenta_wool" -> hintItem = Items.MAGENTA_WOOL;
                    case "light_blue_wool" -> hintItem = Items.LIGHT_BLUE_WOOL;
                    case "yellow_wool" -> hintItem = Items.YELLOW_WOOL;
                    case "lime_wool" -> hintItem = Items.LIME_WOOL;
                    case "pink_wool" -> hintItem = Items.PINK_WOOL;
                    case "gray_wool" -> hintItem = Items.GRAY_WOOL;
                    case "light_gray_wool" -> hintItem = Items.LIGHT_GRAY_WOOL;
                    case "cyan_wool" -> hintItem = Items.CYAN_WOOL;
                    case "purple_wool" -> hintItem = Items.PURPLE_WOOL;
                    case "blue_wool" -> hintItem = Items.BLUE_WOOL;
                    case "brown_wool" -> hintItem = Items.BROWN_WOOL;
                    case "green_wool" -> hintItem = Items.GREEN_WOOL;
                    case "red_wool" -> hintItem = Items.RED_WOOL;
                    case "black_wool" -> hintItem = Items.BLACK_WOOL;
                    case "dried_kelp_block" -> hintItem = Items.DRIED_KELP_BLOCK;
                    case "white_carpet" -> hintItem = Items.WHITE_CARPET;
                    case "orange_carpet" -> hintItem = Items.ORANGE_CARPET;
                    case "magenta_carpet" -> hintItem = Items.MAGENTA_CARPET;
                    case "light_blue_carpet" -> hintItem = Items.LIGHT_BLUE_CARPET;
                    case "yellow_carpet" -> hintItem = Items.YELLOW_CARPET;
                    case "lime_carpet" -> hintItem = Items.LIME_CARPET;
                    case "pink_carpet" -> hintItem = Items.PINK_CARPET;
                    case "gray_carpet" -> hintItem = Items.GRAY_CARPET;
                    case "light_gray_carpet" -> hintItem = Items.LIGHT_GRAY_CARPET;
                    case "cyan_carpet" -> hintItem = Items.CYAN_CARPET;
                    case "purple_carpet" -> hintItem = Items.PURPLE_CARPET;
                    case "blue_carpet" -> hintItem = Items.BLUE_CARPET;
                    case "brown_carpet" -> hintItem = Items.BROWN_CARPET;
                    case "green_carpet" -> hintItem = Items.GREEN_CARPET;
                    case "red_carpet" -> hintItem = Items.RED_CARPET;
                    case "black_carpet" -> hintItem = Items.BLACK_CARPET;
                    case "hay_block" -> hintItem = Items.HAY_BLOCK;
                    case "bamboo" -> hintItem = Items.BAMBOO;
                    case "scaffolding" -> hintItem = Items.SCAFFOLDING;
                    case "dandelion" -> hintItem = Items.DANDELION;
                    case "poppy" -> hintItem = Items.POPPY;
                    case "blue_orchid" -> hintItem = Items.BLUE_ORCHID;
                    case "allium" -> hintItem = Items.ALLIUM;
                    case "azure_bluet" -> hintItem = Items.AZURE_BLUET;
                    case "red_tulip" -> hintItem = Items.RED_TULIP;
                    case "orange_tulip" -> hintItem = Items.ORANGE_TULIP;
                    case "pink_tulip" -> hintItem = Items.PINK_TULIP;
                    case "white_tulip" -> hintItem = Items.WHITE_TULIP;
                    case "oxeye_daisy" -> hintItem = Items.OXEYE_DAISY;
                    case "cornflower" -> hintItem = Items.CORNFLOWER;
                    case "lily_of_the_valley" -> hintItem = Items.LILY_OF_THE_VALLEY;
                    case "wither_rose" -> hintItem = Items.WITHER_ROSE;
                    case "sunflower" -> hintItem = Items.SUNFLOWER;
                    case "lilac" -> hintItem = Items.LILAC;
                    case "rose_bush" -> hintItem = Items.ROSE_BUSH;
                    case "peony" -> hintItem = Items.PEONY;
                    case "grass" -> hintItem = Items.GRASS;
                    case "tall_grass" -> hintItem = Items.TALL_GRASS;
                    case "fern" -> hintItem = Items.FERN;
                    case "large_fern" -> hintItem = Items.LARGE_FERN;
                    case "dead_bush" -> hintItem = Items.DEAD_BUSH;
                    case "sweet_berry_bush" -> hintItem = Items.SWEET_BERRIES;
                    case "mangrove_log" -> hintItem = Items.MANGROVE_LOG;
                    case "stripped_mangrove_log" -> hintItem = Items.STRIPPED_MANGROVE_LOG;
                    case "mangrove_wood" -> hintItem = Items.MANGROVE_WOOD;
                    case "stripped_mangrove_wood" -> hintItem = Items.STRIPPED_MANGROVE_WOOD;
                    case "mangrove_planks" -> hintItem = Items.MANGROVE_PLANKS;
                    case "mangrove_slab" -> hintItem = Items.MANGROVE_SLAB;
                    case "mangrove_fence_gate" -> hintItem = Items.MANGROVE_FENCE_GATE;
                    case "mangrove_fence" -> hintItem = Items.MANGROVE_FENCE;
                    case "mangrove_stairs" -> hintItem = Items.MANGROVE_STAIRS;
                    case "mangrove_leaves" -> hintItem = Items.MANGROVE_LEAVES;
                    case "mangrove_roots" -> hintItem = Items.MANGROVE_ROOTS;
                    case "bamboo_block" -> hintItem = Items.BAMBOO_BLOCK;
                    case "stripped_bamboo_block" -> hintItem = Items.STRIPPED_BAMBOO_BLOCK;
                    case "bamboo_mosaic" -> hintItem = Items.BAMBOO_MOSAIC;
                    case "bamboo_mosaic_stairs" -> hintItem = Items.BAMBOO_MOSAIC_STAIRS;
                    case "bamboo_mosaic_slab" -> hintItem = Items.BAMBOO_MOSAIC_SLAB;
                    case "bamboo_planks" -> hintItem = Items.BAMBOO_PLANKS;
                    case "bamboo_slab" -> hintItem = Items.BAMBOO_SLAB;
                    case "bamboo_fence_gate" -> hintItem = Items.BAMBOO_FENCE_GATE;
                    case "bamboo_fence" -> hintItem = Items.BAMBOO_FENCE;
                    case "bamboo_stairs" -> hintItem = Items.BAMBOO_STAIRS;
                    case "cherry_log" -> hintItem = Items.CHERRY_LOG;
                    case "stripped_cherry_log" -> hintItem = Items.STRIPPED_CHERRY_LOG;
                    case "cherry_wood" -> hintItem = Items.CHERRY_WOOD;
                    case "stripped_cherry_wood" -> hintItem = Items.STRIPPED_CHERRY_WOOD;
                    case "cherry_planks" -> hintItem = Items.CHERRY_PLANKS;
                    case "cherry_slab" -> hintItem = Items.CHERRY_SLAB;
                    case "cherry_fence_gate" -> hintItem = Items.CHERRY_FENCE_GATE;
                    case "cherry_fence" -> hintItem = Items.CHERRY_FENCE;
                    case "cherry_stairs" -> hintItem = Items.CHERRY_STAIRS;
                    case "cherry_leaves" -> hintItem = Items.CHERRY_LEAVES;
                    case "chiseled_bookshelf" -> hintItem = Items.CHISELED_BOOKSHELF;
                    case "pink_petals" -> hintItem = Items.PINK_PETALS;
                    case "pitcher_plant" -> hintItem = Items.PITCHER_PLANT;
                    case "torchflower" -> hintItem = Items.TORCHFLOWER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/bottom_to_top" -> {
                switch (criterion) {
                    case "stone" -> hintItem = Items.STONE;
                    case "granite" -> hintItem = Items.GRANITE;
                    case "andesite" -> hintItem = Items.ANDESITE;
                    case "diorite" -> hintItem = Items.DIORITE;
                    case "gravel" -> hintItem = Items.GRAVEL;
                    case "dirt" -> hintItem = Items.DIRT;
                    case "deepslate" -> hintItem = Items.DEEPSLATE;
                    case "tuff" -> hintItem = Items.TUFF;
                    case "moss_block" -> hintItem = Items.MOSS_BLOCK;
                    case "moss_carpet" -> hintItem = Items.MOSS_CARPET;
                    case "clay" -> hintItem = Items.CLAY;
                    case "dripstone_block" -> hintItem = Items.DRIPSTONE_BLOCK;
                    case "pointed_dripstone" -> hintItem = Items.POINTED_DRIPSTONE;
                    case "sculk" -> hintItem = Items.SCULK;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/even_moar_tools" -> {
                switch (criterion) {
                    case "diamond_pickaxe" -> hintItem = Items.DIAMOND_PICKAXE;
                    case "diamond_axe" -> hintItem = Items.DIAMOND_AXE;
                    case "diamond_shovel" -> hintItem = Items.DIAMOND_SHOVEL;
                    case "diamond_hoe" -> hintItem = Items.DIAMOND_HOE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/lush_hour" -> {
                switch (criterion) {
                    case "small_dripleaf" -> hintItem = Items.SMALL_DRIPLEAF;
                    case "big_dripleaf" -> hintItem = Items.BIG_DRIPLEAF;
                    case "moss_carpet" -> hintItem = Items.MOSS_CARPET;
                    case "moss_block" -> hintItem = Items.MOSS_BLOCK;
                    case "azalea" -> hintItem = Items.AZALEA;
                    case "flowering_azalea" -> hintItem = Items.FLOWERING_AZALEA;
                    case "azalea_leaves" -> hintItem = Items.AZALEA_LEAVES;
                    case "flowering_azalea_leaves" -> hintItem = Items.FLOWERING_AZALEA_LEAVES;
                    case "spore_blossom" -> hintItem = Items.SPORE_BLOSSOM;
                    case "rooted_dirt" -> hintItem = Items.ROOTED_DIRT;
                    case "hanging_roots" -> hintItem = Items.HANGING_ROOTS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/mineral_collection" -> {
                switch (criterion) {
                    case "coal_block" -> hintItem = Items.COAL_BLOCK;
                    case "iron_block" -> hintItem = Items.IRON_BLOCK;
                    case "gold_block" -> hintItem = Items.GOLD_BLOCK;
                    case "lapis_block" -> hintItem = Items.LAPIS_BLOCK;
                    case "redstone_block" -> hintItem = Items.REDSTONE_BLOCK;
                    case "diamond_block" -> hintItem = Items.DIAMOND_BLOCK;
                    case "emerald_block" -> hintItem = Items.EMERALD_BLOCK;
                    case "quartz_block" -> hintItem = Items.QUARTZ_BLOCK;
                    case "netherite_block" -> hintItem = Items.NETHERITE_BLOCK;
                    case "copper_block" -> hintItem = Items.COPPER_BLOCK;
                    case "amethyst_block" -> hintItem = Items.AMETHYST_BLOCK;
                    case "raw_copper_block" -> hintItem = Items.RAW_COPPER_BLOCK;
                    case "raw_iron_block" -> hintItem = Items.RAW_IRON_BLOCK;
                    case "raw_gold_block" -> hintItem = Items.RAW_GOLD_BLOCK;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/moar_broken_tools" -> {
                switch (criterion) {
                    case "wooden_shovel" -> hintItem = Items.WOODEN_SHOVEL;
                    case "stone_shovel" -> hintItem = Items.STONE_SHOVEL;
                    case "gold_shovel" -> hintItem = Items.GOLDEN_SHOVEL;
                    case "iron_shovel" -> hintItem = Items.IRON_SHOVEL;
                    case "diamond_shovel" -> hintItem = Items.DIAMOND_SHOVEL;
                    case "netherite_shovel" -> hintItem = Items.NETHERITE_SHOVEL;
                    case "wooden_pickaxe" -> hintItem = Items.WOODEN_PICKAXE;
                    case "stone_pickaxe" -> hintItem = Items.STONE_PICKAXE;
                    case "gold_pickaxe" -> hintItem = Items.GOLDEN_PICKAXE;
                    case "iron_pickaxe" -> hintItem = Items.IRON_PICKAXE;
                    case "diamond_pickaxe" -> hintItem = Items.DIAMOND_PICKAXE;
                    case "netherite_pickaxe" -> hintItem = Items.NETHERITE_PICKAXE;
                    case "wooden_axe" -> hintItem = Items.WOODEN_AXE;
                    case "stone_axe" -> hintItem = Items.STONE_AXE;
                    case "gold_axe" -> hintItem = Items.GOLDEN_AXE;
                    case "iron_axe" -> hintItem = Items.IRON_AXE;
                    case "diamond_axe" -> hintItem = Items.DIAMOND_AXE;
                    case "netherite_axe" -> hintItem = Items.NETHERITE_AXE;
                    case "wooden_hoe" -> hintItem = Items.WOODEN_HOE;
                    case "stone_hoe" -> hintItem = Items.STONE_HOE;
                    case "gold_hoe" -> hintItem = Items.GOLDEN_HOE;
                    case "iron_hoe" -> hintItem = Items.IRON_HOE;
                    case "diamond_hoe" -> hintItem = Items.DIAMOND_HOE;
                    case "netherite_hoe" -> hintItem = Items.NETHERITE_HOE;
                    case "wooden_sword" -> hintItem = Items.WOODEN_SWORD;
                    case "stone_sword" -> hintItem = Items.STONE_SWORD;
                    case "gold_sword" -> hintItem = Items.GOLDEN_SWORD;
                    case "iron_sword" -> hintItem = Items.IRON_SWORD;
                    case "diamond_sword" -> hintItem = Items.DIAMOND_SWORD;
                    case "netherite_sword" -> hintItem = Items.NETHERITE_SWORD;
                    case "fishing_rod" -> hintItem = Items.FISHING_ROD;
                    case "bow" -> hintItem = Items.BOW;
                    case "shield" -> hintItem = Items.SHIELD;
                    case "trident" -> hintItem = Items.TRIDENT;
                    case "crossbow" -> hintItem = Items.CROSSBOW;
                    case "carrot_on_a_stick" -> hintItem = Items.CARROT_ON_A_STICK;
                    case "shears" -> hintItem = Items.SHEARS;
                    case "flint_and_steel" -> hintItem = Items.FLINT_AND_STEEL;
                    case "warped_fungus_on_a_stick" -> hintItem = Items.WARPED_FUNGUS_ON_A_STICK;
                    case "brush" -> hintItem = Items.BRUSH;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/moar_tools" -> {
                switch (criterion) {
                    case "stone_pickaxe" -> hintItem = Items.STONE_PICKAXE;
                    case "stone_axe" -> hintItem = Items.STONE_AXE;
                    case "stone_shovel" -> hintItem = Items.STONE_SHOVEL;
                    case "stone_hoe" -> hintItem = Items.STONE_HOE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/mr_bean" -> {
                switch (criterion) {
                    case "raw_iron_block" -> hintItem = Items.RAW_IRON_BLOCK;
                    case "raw_copper_block" -> hintItem = Items.RAW_COPPER_BLOCK;
                    case "raw_gold_block" -> hintItem = Items.RAW_GOLD_BLOCK;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/oresome" -> {
                switch (criterion) {
                    case "coal_ore" -> hintItem = Items.COAL_ORE;
                    case "iron_ore" -> hintItem = Items.IRON_ORE;
                    case "gold_ore" -> hintItem = Items.GOLD_ORE;
                    case "lapis_ore" -> hintItem = Items.LAPIS_ORE;
                    case "redstone_ore" -> hintItem = Items.REDSTONE_ORE;
                    case "diamond_ore" -> hintItem = Items.DIAMOND_ORE;
                    case "emerald_ore" -> hintItem = Items.EMERALD_ORE;
                    case "nether_quartz_ore" -> hintItem = Items.NETHER_QUARTZ_ORE;
                    case "nether_gold_ore" -> hintItem = Items.NETHER_GOLD_ORE;
                    case "ancient_debris" -> hintItem = Items.ANCIENT_DEBRIS;
                    case "copper_ore" -> hintItem = Items.COPPER_ORE;
                    case "deepslate_gold_ore" -> hintItem = Items.DEEPSLATE_GOLD_ORE;
                    case "deepslate_iron_ore" -> hintItem = Items.DEEPSLATE_IRON_ORE;
                    case "deepslate_lapis_ore" -> hintItem = Items.DEEPSLATE_LAPIS_ORE;
                    case "deepslate_diamond_ore" -> hintItem = Items.DEEPSLATE_DIAMOND_ORE;
                    case "deepslate_redstone_ore" -> hintItem = Items.DEEPSLATE_REDSTONE_ORE;
                    case "deepslate_coal_ore" -> hintItem = Items.DEEPSLATE_COAL_ORE;
                    case "deepslate_copper_ore" -> hintItem = Items.DEEPSLATE_COPPER_ORE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/sly_copper_the_copper_heist" -> {
                switch (criterion) {
                    case "copper_block" -> hintItem = Items.COPPER_BLOCK;
                    case "exposed_copper" -> hintItem = Items.EXPOSED_COPPER;
                    case "weathered_copper" -> hintItem = Items.WEATHERED_COPPER;
                    case "oxidized_copper" -> hintItem = Items.OXIDIZED_COPPER;
                    case "cut_copper" -> hintItem = Items.CUT_COPPER;
                    case "exposed_cut_copper" -> hintItem = Items.EXPOSED_CUT_COPPER;
                    case "weathered_cut_copper" -> hintItem = Items.WEATHERED_CUT_COPPER;
                    case "oxidized_cut_copper" -> hintItem = Items.OXIDIZED_CUT_COPPER;
                    case "cut_copper_stairs" -> hintItem = Items.CUT_COPPER_STAIRS;
                    case "exposed_cut_copper_stairs" -> hintItem = Items.EXPOSED_CUT_COPPER_STAIRS;
                    case "weathered_cut_copper_stairs" -> hintItem = Items.WEATHERED_CUT_COPPER_STAIRS;
                    case "oxidized_cut_copper_stairs" -> hintItem = Items.OXIDIZED_CUT_COPPER_STAIRS;
                    case "cut_copper_slab" -> hintItem = Items.CUT_COPPER_SLAB;
                    case "exposed_cut_copper_slab" -> hintItem = Items.EXPOSED_CUT_COPPER_SLAB;
                    case "weathered_cut_copper_slab" -> hintItem = Items.WEATHERED_CUT_COPPER_SLAB;
                    case "oxidized_cut_copper_slab" -> hintItem = Items.OXIDIZED_CUT_COPPER_SLAB;
                    case "waxed_copper_block" -> hintItem = Items.WAXED_COPPER_BLOCK;
                    case "waxed_exposed_copper" -> hintItem = Items.WAXED_EXPOSED_COPPER;
                    case "waxed_weathered_copper" -> hintItem = Items.WAXED_WEATHERED_COPPER;
                    case "waxed_oxidized_copper" -> hintItem = Items.WAXED_OXIDIZED_COPPER;
                    case "waxed_cut_copper" -> hintItem = Items.WAXED_CUT_COPPER;
                    case "waxed_exposed_cut_copper" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER;
                    case "waxed_weathered_cut_copper" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER;
                    case "waxed_oxidized_cut_copper" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER;
                    case "waxed_cut_copper_stairs" -> hintItem = Items.WAXED_CUT_COPPER_STAIRS;
                    case "waxed_exposed_cut_copper_stairs" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER_STAIRS;
                    case "waxed_weathered_cut_copper_stairs" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER_STAIRS;
                    case "waxed_oxidized_cut_copper_stairs" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER_STAIRS;
                    case "waxed_cut_copper_slab" -> hintItem = Items.WAXED_CUT_COPPER_SLAB;
                    case "waxed_exposed_cut_copper_slab" -> hintItem = Items.WAXED_EXPOSED_CUT_COPPER_SLAB;
                    case "waxed_weathered_cut_copper_slab" -> hintItem = Items.WAXED_WEATHERED_CUT_COPPER_SLAB;
                    case "waxed_oxidized_cut_copper_slab" -> hintItem = Items.WAXED_OXIDIZED_CUT_COPPER_SLAB;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/spelunker" -> {
                switch (criterion) {
                    case "lush_caves" -> hintItem = Items.BARRIER;
                    case "dripstone_caves" -> hintItem = Items.BARRIER;
                    case "deep_dark" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/weve_broken_our_last_shovel" -> {
                switch (criterion) {
                    case "wooden_shovel" -> hintItem = Items.WOODEN_SHOVEL;
                    case "stone_shovel" -> hintItem = Items.STONE_SHOVEL;
                    case "gold_shovel" -> hintItem = Items.GOLDEN_SHOVEL;
                    case "iron_shovel" -> hintItem = Items.IRON_SHOVEL;
                    case "diamond_shovel" -> hintItem = Items.DIAMOND_SHOVEL;
                    case "netherite_shovel" -> hintItem = Items.NETHERITE_SHOVEL;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:mining/where_are_all_your_clothes" -> {
                switch (criterion) {
                    case "leather_helmet" -> hintItem = Items.LEATHER_HELMET;
                    case "leather_chestplate" -> hintItem = Items.LEATHER_CHESTPLATE;
                    case "leather_leggings" -> hintItem = Items.LEATHER_LEGGINGS;
                    case "leather_boots" -> hintItem = Items.LEATHER_BOOTS;
                    case "golden_helmet" -> hintItem = Items.GOLDEN_HELMET;
                    case "golden_chestplate" -> hintItem = Items.GOLDEN_CHESTPLATE;
                    case "golden_leggings" -> hintItem = Items.GOLDEN_LEGGINGS;
                    case "golden_boots" -> hintItem = Items.GOLDEN_BOOTS;
                    case "chainmail_helmet" -> hintItem = Items.CHAINMAIL_HELMET;
                    case "chainmail_chestplate" -> hintItem = Items.CHAINMAIL_CHESTPLATE;
                    case "chainmail_leggings" -> hintItem = Items.CHAINMAIL_LEGGINGS;
                    case "chainmail_boots" -> hintItem = Items.CHAINMAIL_BOOTS;
                    case "iron_helmet" -> hintItem = Items.IRON_HELMET;
                    case "iron_chestplate" -> hintItem = Items.IRON_CHESTPLATE;
                    case "iron_leggings" -> hintItem = Items.IRON_LEGGINGS;
                    case "iron_boots" -> hintItem = Items.IRON_BOOTS;
                    case "diamond_helmet" -> hintItem = Items.DIAMOND_HELMET;
                    case "diamond_chestplate" -> hintItem = Items.DIAMOND_CHESTPLATE;
                    case "diamond_leggings" -> hintItem = Items.DIAMOND_LEGGINGS;
                    case "diamond_boots" -> hintItem = Items.DIAMOND_BOOTS;
                    case "netherite_helmet" -> hintItem = Items.NETHERITE_HELMET;
                    case "netherite_chestplate" -> hintItem = Items.NETHERITE_CHESTPLATE;
                    case "netherite_leggings" -> hintItem = Items.NETHERITE_LEGGINGS;
                    case "netherite_boots" -> hintItem = Items.NETHERITE_BOOTS;
                    case "turtle_helmet" -> hintItem = Items.TURTLE_HELMET;
                    case "elytra" -> hintItem = Items.ELYTRA;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:monsters/baby_baby_baby_noo" -> {
                switch (criterion) {
                    case "zombie" -> hintItem = Items.BARRIER;
                    case "zombie_villager" -> hintItem = Items.BARRIER;
                    case "husk" -> hintItem = Items.BARRIER;
                    case "zombified_piglin" -> hintItem = Items.BARRIER;
                    case "drowned" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:monsters/basketblock_championship" -> {
                switch (criterion) {
                    case "dandelion" -> hintItem = Items.DANDELION;
                    case "poppy" -> hintItem = Items.POPPY;
                    case "blue_orchid" -> hintItem = Items.BLUE_ORCHID;
                    case "allium" -> hintItem = Items.ALLIUM;
                    case "azure_bluet" -> hintItem = Items.AZURE_BLUET;
                    case "red_tulip" -> hintItem = Items.RED_TULIP;
                    case "orange_tulip" -> hintItem = Items.ORANGE_TULIP;
                    case "white_tulip" -> hintItem = Items.WHITE_TULIP;
                    case "pink_tulip" -> hintItem = Items.PINK_TULIP;
                    case "oxeye_daisy" -> hintItem = Items.OXEYE_DAISY;
                    case "cornflower" -> hintItem = Items.CORNFLOWER;
                    case "lily_of_the_valley" -> hintItem = Items.LILY_OF_THE_VALLEY;
                    case "wither_rose" -> hintItem = Items.WITHER_ROSE;
                    case "grass_block" -> hintItem = Items.GRASS_BLOCK;
                    case "dirt" -> hintItem = Items.DIRT;
                    case "coarse_dirt" -> hintItem = Items.COARSE_DIRT;
                    case "podzol" -> hintItem = Items.PODZOL;
                    case "sand" -> hintItem = Items.SAND;
                    case "red_sand" -> hintItem = Items.RED_SAND;
                    case "gravel" -> hintItem = Items.GRAVEL;
                    case "brown_mushroom" -> hintItem = Items.BROWN_MUSHROOM;
                    case "red_mushroom" -> hintItem = Items.RED_MUSHROOM;
                    case "tnt" -> hintItem = Items.TNT;
                    case "cactus" -> hintItem = Items.CACTUS;
                    case "clay" -> hintItem = Items.CLAY;
                    case "pumpkin" -> hintItem = Items.PUMPKIN;
                    case "carved_pumpkin" -> hintItem = Items.CARVED_PUMPKIN;
                    case "melon" -> hintItem = Items.MELON;
                    case "mycelium" -> hintItem = Items.MYCELIUM;
                    case "crimson_fungus" -> hintItem = Items.CRIMSON_FUNGUS;
                    case "crimson_nylium" -> hintItem = Items.CRIMSON_NYLIUM;
                    case "crimson_roots" -> hintItem = Items.CRIMSON_ROOTS;
                    case "warped_fungus" -> hintItem = Items.WARPED_FUNGUS;
                    case "warped_nylium" -> hintItem = Items.WARPED_NYLIUM;
                    case "warped_roots" -> hintItem = Items.WARPED_ROOTS;
                    case "rooted_dirt" -> hintItem = Items.ROOTED_DIRT;
                    case "moss_block" -> hintItem = Items.MOSS_BLOCK;
                    case "mud" -> hintItem = Items.MUD;
                    case "muddy_mangrove_roots" -> hintItem = Items.MUDDY_MANGROVE_ROOTS;
                    case "torchflower" -> hintItem = Items.TORCHFLOWER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:monsters/plane_walker" -> {
                switch (criterion) {
                    case "overworld" -> hintItem = Items.BARRIER;
                    case "nether" -> hintItem = Items.BARRIER;
                    case "end" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:monsters/void_ender" -> {
                switch (criterion) {
                    case "enderman" -> hintItem = Items.BARRIER;
                    case "endermite" -> hintItem = Items.BARRIER;
                    case "shulker" -> hintItem = Items.BARRIER;
                    case "ender_dragon" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:nether/hell_botanist" -> {
                switch (criterion) {
                    case "crimson_fungus" -> hintItem = Items.CRIMSON_FUNGUS;
                    case "warped_fungus" -> hintItem = Items.WARPED_FUNGUS;
                    case "crimson_roots" -> hintItem = Items.CRIMSON_ROOTS;
                    case "warped_roots" -> hintItem = Items.WARPED_ROOTS;
                    case "nether_sprouts" -> hintItem = Items.NETHER_SPROUTS;
                    case "weeping_vines" -> hintItem = Items.WEEPING_VINES;
                    case "twisting_vines" -> hintItem = Items.TWISTING_VINES;
                    case "nether_wart" -> hintItem = Items.NETHER_WART;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:nether/hung_drawn_and_bartered" -> {
                switch (criterion) {
                    case "enchanted_book" -> hintItem = Items.ENCHANTED_BOOK;
                    case "iron_boots" -> hintItem = Items.IRON_BOOTS;
                    case "splash_potion_fire_resistance" -> hintItem = Items.SPLASH_POTION;
                    case "potion_fire_resistance" -> hintItem = Items.POTION;
                    case "water_bottle" -> hintItem = Items.POTION;
                    case "iron_nugget" -> hintItem = Items.IRON_NUGGET;
                    case "ender_pearl" -> hintItem = Items.ENDER_PEARL;
                    case "string" -> hintItem = Items.STRING;
                    case "quartz" -> hintItem = Items.QUARTZ;
                    case "obsidian" -> hintItem = Items.OBSIDIAN;
                    case "crying_obsidian" -> hintItem = Items.CRYING_OBSIDIAN;
                    case "fire_charge" -> hintItem = Items.FIRE_CHARGE;
                    case "leather" -> hintItem = Items.LEATHER;
                    case "soul_sand" -> hintItem = Items.SOUL_SAND;
                    case "nether_brick" -> hintItem = Items.NETHER_BRICK;
                    case "spectral_arrow" -> hintItem = Items.SPECTRAL_ARROW;
                    case "gravel" -> hintItem = Items.GRAVEL;
                    case "blackstone" -> hintItem = Items.BLACKSTONE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:nether/lodes_of_applications" -> {
                switch (criterion) {
                    case "overworld" -> hintItem = Items.BARRIER;
                    case "the_nether" -> hintItem = Items.BARRIER;
                    case "the_end" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:nether/moar_upgraded_tools" -> {
                switch (criterion) {
                    case "netherite_pickaxe" -> hintItem = Items.NETHERITE_PICKAXE;
                    case "netherite_axe" -> hintItem = Items.NETHERITE_AXE;
                    case "netherite_shovel" -> hintItem = Items.NETHERITE_SHOVEL;
                    case "netherite_hoe" -> hintItem = Items.NETHERITE_HOE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:nether/nether_rock_collection" -> {
                switch (criterion) {
                    case "netherrack" -> hintItem = Items.NETHERRACK;
                    case "basalt" -> hintItem = Items.BASALT;
                    case "blackstone" -> hintItem = Items.BLACKSTONE;
                    case "magma_block" -> hintItem = Items.MAGMA_BLOCK;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:nether/spreading_corruption" -> {
                switch (criterion) {
                    case "netherrack" -> hintItem = Items.NETHERRACK;
                    case "soul_sand" -> hintItem = Items.SOUL_SAND;
                    case "glowstone" -> hintItem = Items.GLOWSTONE;
                    case "magma_block" -> hintItem = Items.MAGMA_BLOCK;
                    case "soul_soil" -> hintItem = Items.SOUL_SOIL;
                    case "basalt" -> hintItem = Items.BASALT;
                    case "ancient_debris" -> hintItem = Items.ANCIENT_DEBRIS;
                    case "crimson_nylium" -> hintItem = Items.CRIMSON_NYLIUM;
                    case "warped_nylium" -> hintItem = Items.WARPED_NYLIUM;
                    case "crimson_stem" -> hintItem = Items.CRIMSON_STEM;
                    case "warped_stem" -> hintItem = Items.WARPED_STEM;
                    case "nether_wart_block" -> hintItem = Items.NETHER_WART_BLOCK;
                    case "warped_wart_block" -> hintItem = Items.WARPED_WART_BLOCK;
                    case "shroomlight" -> hintItem = Items.SHROOMLIGHT;
                    case "nether_quartz_ore" -> hintItem = Items.NETHER_QUARTZ_ORE;
                    case "nether_gold_ore" -> hintItem = Items.NETHER_GOLD_ORE;
                    case "soul_fire" -> hintItem = Items.BARRIER;
                    case "blackstone" -> hintItem = Items.BLACKSTONE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:nether/the_struggle_nether_ends" -> {
                switch (criterion) {
                    case "netherrack" -> hintItem = Items.NETHERRACK;
                    case "soul_sand" -> hintItem = Items.SOUL_SAND;
                    case "glowstone" -> hintItem = Items.GLOWSTONE;
                    case "magma_block" -> hintItem = Items.MAGMA_BLOCK;
                    case "soul_soil" -> hintItem = Items.SOUL_SOIL;
                    case "basalt" -> hintItem = Items.BASALT;
                    case "ancient_debris" -> hintItem = Items.ANCIENT_DEBRIS;
                    case "crimson_nylium" -> hintItem = Items.CRIMSON_NYLIUM;
                    case "warped_nylium" -> hintItem = Items.WARPED_NYLIUM;
                    case "crimson_stem" -> hintItem = Items.CRIMSON_STEM;
                    case "warped_stem" -> hintItem = Items.WARPED_STEM;
                    case "nether_wart_block" -> hintItem = Items.NETHER_WART_BLOCK;
                    case "warped_wart_block" -> hintItem = Items.WARPED_WART_BLOCK;
                    case "shroomlight" -> hintItem = Items.SHROOMLIGHT;
                    case "nether_quartz_ore" -> hintItem = Items.NETHER_QUARTZ_ORE;
                    case "nether_gold_ore" -> hintItem = Items.NETHER_GOLD_ORE;
                    case "soul_fire" -> hintItem = Items.BARRIER;
                    case "blackstone" -> hintItem = Items.BLACKSTONE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:nether/welcome_to_warp_zone" -> {
                switch (criterion) {
                    case "warped_planks" -> hintItem = Items.WARPED_PLANKS;
                    case "warped_stem" -> hintItem = Items.WARPED_STEM;
                    case "stripped_warped_stem" -> hintItem = Items.STRIPPED_WARPED_STEM;
                    case "warped_hyphae" -> hintItem = Items.WARPED_HYPHAE;
                    case "stripped_warped_hyphae" -> hintItem = Items.STRIPPED_WARPED_HYPHAE;
                    case "warped_slab" -> hintItem = Items.WARPED_SLAB;
                    case "warped_pressure_plate" -> hintItem = Items.WARPED_PRESSURE_PLATE;
                    case "warped_fence" -> hintItem = Items.WARPED_FENCE;
                    case "warped_trapdoor" -> hintItem = Items.WARPED_TRAPDOOR;
                    case "warped_fence_gate" -> hintItem = Items.WARPED_FENCE_GATE;
                    case "warped_stairs" -> hintItem = Items.WARPED_STAIRS;
                    case "warped_button" -> hintItem = Items.WARPED_BUTTON;
                    case "warped_door" -> hintItem = Items.WARPED_DOOR;
                    case "warped_sign" -> hintItem = Items.WARPED_SIGN;
                    case "warped_hanging_sign" -> hintItem = Items.WARPED_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:nether/what_about_corruption_planks" -> {
                switch (criterion) {
                    case "crimson_planks" -> hintItem = Items.CRIMSON_PLANKS;
                    case "crimson_stem" -> hintItem = Items.CRIMSON_STEM;
                    case "stripped_crimson_stem" -> hintItem = Items.STRIPPED_CRIMSON_STEM;
                    case "crimson_hyphae" -> hintItem = Items.CRIMSON_HYPHAE;
                    case "stripped_crimson_hyphae" -> hintItem = Items.STRIPPED_CRIMSON_HYPHAE;
                    case "crimson_slab" -> hintItem = Items.CRIMSON_SLAB;
                    case "crimson_pressure_plate" -> hintItem = Items.CRIMSON_PRESSURE_PLATE;
                    case "crimson_fence" -> hintItem = Items.CRIMSON_FENCE;
                    case "crimson_trapdoor" -> hintItem = Items.CRIMSON_TRAPDOOR;
                    case "crimson_fence_gate" -> hintItem = Items.CRIMSON_FENCE_GATE;
                    case "crimson_stairs" -> hintItem = Items.CRIMSON_STAIRS;
                    case "crimson_button" -> hintItem = Items.CRIMSON_BUTTON;
                    case "crimson_door" -> hintItem = Items.CRIMSON_DOOR;
                    case "crimson_sign" -> hintItem = Items.CRIMSON_SIGN;
                    case "crimson_hanging_sign" -> hintItem = Items.CRIMSON_HANGING_SIGN;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:potion/failed_concoctions" -> {
                switch (criterion) {
                    case "thick_potion" -> hintItem = Items.BARRIER;
                    case "mundane_potion" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:potion/furious_ammunition" -> {
                switch (criterion) {
                    case "water" -> hintItem = Items.BARRIER;
                    case "thick" -> hintItem = Items.BARRIER;
                    case "mundane" -> hintItem = Items.BARRIER;
                    case "awkward" -> hintItem = Items.BARRIER;
                    case "night_vision" -> hintItem = Items.BARRIER;
                    case "long_night_vision" -> hintItem = Items.BARRIER;
                    case "invisibility" -> hintItem = Items.BARRIER;
                    case "long_invisibility" -> hintItem = Items.BARRIER;
                    case "leaping" -> hintItem = Items.BARRIER;
                    case "long_leaping" -> hintItem = Items.BARRIER;
                    case "strong_leaping" -> hintItem = Items.BARRIER;
                    case "fire_resistance" -> hintItem = Items.BARRIER;
                    case "long_fire_resistance" -> hintItem = Items.BARRIER;
                    case "swiftness" -> hintItem = Items.BARRIER;
                    case "long_swiftness" -> hintItem = Items.BARRIER;
                    case "strong_swiftness" -> hintItem = Items.BARRIER;
                    case "slowness" -> hintItem = Items.BARRIER;
                    case "long_slowness" -> hintItem = Items.BARRIER;
                    case "strong_slowness" -> hintItem = Items.BARRIER;
                    case "water_breathing" -> hintItem = Items.BARRIER;
                    case "long_water_breathing" -> hintItem = Items.BARRIER;
                    case "healing" -> hintItem = Items.BARRIER;
                    case "strong_healing" -> hintItem = Items.BARRIER;
                    case "harming" -> hintItem = Items.BARRIER;
                    case "strong_harming" -> hintItem = Items.BARRIER;
                    case "normal_poison" -> hintItem = Items.BARRIER;
                    case "long_poison" -> hintItem = Items.BARRIER;
                    case "strong_poison" -> hintItem = Items.BARRIER;
                    case "regeneration" -> hintItem = Items.BARRIER;
                    case "long_regeneration" -> hintItem = Items.BARRIER;
                    case "strong_regeneration" -> hintItem = Items.BARRIER;
                    case "strength" -> hintItem = Items.BARRIER;
                    case "long_strength" -> hintItem = Items.BARRIER;
                    case "strong_strength" -> hintItem = Items.BARRIER;
                    case "weakness" -> hintItem = Items.BARRIER;
                    case "long_weakness" -> hintItem = Items.BARRIER;
                    case "turtle_master" -> hintItem = Items.BARRIER;
                    case "long_turtle_master" -> hintItem = Items.BARRIER;
                    case "strong_turtle_master" -> hintItem = Items.BARRIER;
                    case "slow_falling" -> hintItem = Items.BARRIER;
                    case "long_slow_falling" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:redstone/engineer" -> {
                switch (criterion) {
                    case "redstone" -> hintItem = Items.REDSTONE;
                    case "redstone_torch" -> hintItem = Items.REDSTONE_TORCH;
                    case "redstone_block" -> hintItem = Items.REDSTONE_BLOCK;
                    case "repeater" -> hintItem = Items.REPEATER;
                    case "comparator" -> hintItem = Items.COMPARATOR;
                    case "target" -> hintItem = Items.TARGET;
                    case "lever" -> hintItem = Items.LEVER;
                    case "oak_button" -> hintItem = Items.OAK_BUTTON;
                    case "stone_button" -> hintItem = Items.STONE_BUTTON;
                    case "oak_pressure_plate" -> hintItem = Items.OAK_PRESSURE_PLATE;
                    case "light_weighted_pressure_plate" -> hintItem = Items.LIGHT_WEIGHTED_PRESSURE_PLATE;
                    case "heavy_weighted_pressure_plate" -> hintItem = Items.HEAVY_WEIGHTED_PRESSURE_PLATE;
                    case "sculk_sensor" -> hintItem = Items.SCULK_SENSOR;
                    case "calibrated_sculk_sensor" -> hintItem = Items.CALIBRATED_SCULK_SENSOR;
                    case "sculk_shrieker" -> hintItem = Items.SCULK_SHRIEKER;
                    case "amethyst_block" -> hintItem = Items.AMETHYST_BLOCK;
                    case "white_wool" -> hintItem = Items.WHITE_WOOL;
                    case "tripwire_hook" -> hintItem = Items.TRIPWIRE_HOOK;
                    case "string" -> hintItem = Items.STRING;
                    case "lectern" -> hintItem = Items.LECTERN;
                    case "daylight_detector" -> hintItem = Items.DAYLIGHT_DETECTOR;
                    case "lightning_rod" -> hintItem = Items.LIGHTNING_ROD;
                    case "piston" -> hintItem = Items.PISTON;
                    case "sticky_piston" -> hintItem = Items.STICKY_PISTON;
                    case "slime_block" -> hintItem = Items.SLIME_BLOCK;
                    case "honey_block" -> hintItem = Items.HONEY_BLOCK;
                    case "dispenser" -> hintItem = Items.DISPENSER;
                    case "dropper" -> hintItem = Items.DROPPER;
                    case "hopper" -> hintItem = Items.HOPPER;
                    case "chest" -> hintItem = Items.CHEST;
                    case "barrel" -> hintItem = Items.BARREL;
                    case "chiseled_bookshelf" -> hintItem = Items.CHISELED_BOOKSHELF;
                    case "furnace" -> hintItem = Items.FURNACE;
                    case "trapped_chest" -> hintItem = Items.TRAPPED_CHEST;
                    case "jukebox" -> hintItem = Items.JUKEBOX;
                    case "observer" -> hintItem = Items.OBSERVER;
                    case "note_block" -> hintItem = Items.NOTE_BLOCK;
                    case "composter" -> hintItem = Items.COMPOSTER;
                    case "cauldron" -> hintItem = Items.CAULDRON;
                    case "rail" -> hintItem = Items.RAIL;
                    case "powered_rail" -> hintItem = Items.POWERED_RAIL;
                    case "detector_rail" -> hintItem = Items.DETECTOR_RAIL;
                    case "activator_rail" -> hintItem = Items.ACTIVATOR_RAIL;
                    case "minecart" -> hintItem = Items.MINECART;
                    case "hopper_minecart" -> hintItem = Items.HOPPER_MINECART;
                    case "chest_minecart" -> hintItem = Items.CHEST_MINECART;
                    case "furnace_minecart" -> hintItem = Items.FURNACE_MINECART;
                    case "tnt_minecart" -> hintItem = Items.TNT_MINECART;
                    case "oak_chest_boat" -> hintItem = Items.OAK_CHEST_BOAT;
                    case "bamboo_chest_raft" -> hintItem = Items.BAMBOO_CHEST_RAFT;
                    case "oak_door" -> hintItem = Items.OAK_DOOR;
                    case "iron_door" -> hintItem = Items.IRON_DOOR;
                    case "oak_fence_gate" -> hintItem = Items.OAK_FENCE_GATE;
                    case "oak_trapdoor" -> hintItem = Items.OAK_TRAPDOOR;
                    case "iron_trapdoor" -> hintItem = Items.IRON_TRAPDOOR;
                    case "tnt" -> hintItem = Items.TNT;
                    case "redstone_lamp" -> hintItem = Items.REDSTONE_LAMP;
                    case "bell" -> hintItem = Items.BELL;
                    case "big_dripleaf" -> hintItem = Items.BIG_DRIPLEAF;
                    case "armor_stand" -> hintItem = Items.ARMOR_STAND;
                    case "redstone_ore" -> hintItem = Items.REDSTONE_ORE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:redstone/master_engineer" -> {
                switch (criterion) {
                    case "redstone" -> hintItem = Items.REDSTONE;
                    case "redstone_torch" -> hintItem = Items.REDSTONE_TORCH;
                    case "redstone_block" -> hintItem = Items.REDSTONE_BLOCK;
                    case "repeater" -> hintItem = Items.REPEATER;
                    case "comparator" -> hintItem = Items.COMPARATOR;
                    case "target" -> hintItem = Items.TARGET;
                    case "lever" -> hintItem = Items.LEVER;
                    case "oak_button" -> hintItem = Items.OAK_BUTTON;
                    case "stone_button" -> hintItem = Items.STONE_BUTTON;
                    case "oak_pressure_plate" -> hintItem = Items.OAK_PRESSURE_PLATE;
                    case "light_weighted_pressure_plate" -> hintItem = Items.LIGHT_WEIGHTED_PRESSURE_PLATE;
                    case "heavy_weighted_pressure_plate" -> hintItem = Items.HEAVY_WEIGHTED_PRESSURE_PLATE;
                    case "sculk_sensor" -> hintItem = Items.SCULK_SENSOR;
                    case "calibrated_sculk_sensor" -> hintItem = Items.CALIBRATED_SCULK_SENSOR;
                    case "sculk_shrieker" -> hintItem = Items.SCULK_SHRIEKER;
                    case "amethyst_block" -> hintItem = Items.AMETHYST_BLOCK;
                    case "white_wool" -> hintItem = Items.WHITE_WOOL;
                    case "tripwire_hook" -> hintItem = Items.TRIPWIRE_HOOK;
                    case "string" -> hintItem = Items.STRING;
                    case "lectern" -> hintItem = Items.LECTERN;
                    case "daylight_detector" -> hintItem = Items.DAYLIGHT_DETECTOR;
                    case "lightning_rod" -> hintItem = Items.LIGHTNING_ROD;
                    case "piston" -> hintItem = Items.PISTON;
                    case "sticky_piston" -> hintItem = Items.STICKY_PISTON;
                    case "slime_block" -> hintItem = Items.SLIME_BLOCK;
                    case "honey_block" -> hintItem = Items.HONEY_BLOCK;
                    case "dispenser" -> hintItem = Items.DISPENSER;
                    case "dropper" -> hintItem = Items.DROPPER;
                    case "hopper" -> hintItem = Items.HOPPER;
                    case "chest" -> hintItem = Items.CHEST;
                    case "barrel" -> hintItem = Items.BARREL;
                    case "chiseled_bookshelf" -> hintItem = Items.CHISELED_BOOKSHELF;
                    case "furnace" -> hintItem = Items.FURNACE;
                    case "trapped_chest" -> hintItem = Items.TRAPPED_CHEST;
                    case "jukebox" -> hintItem = Items.JUKEBOX;
                    case "observer" -> hintItem = Items.OBSERVER;
                    case "note_block" -> hintItem = Items.NOTE_BLOCK;
                    case "composter" -> hintItem = Items.COMPOSTER;
                    case "cauldron" -> hintItem = Items.CAULDRON;
                    case "rail" -> hintItem = Items.RAIL;
                    case "powered_rail" -> hintItem = Items.POWERED_RAIL;
                    case "detector_rail" -> hintItem = Items.DETECTOR_RAIL;
                    case "activator_rail" -> hintItem = Items.ACTIVATOR_RAIL;
                    case "minecart" -> hintItem = Items.MINECART;
                    case "hopper_minecart" -> hintItem = Items.HOPPER_MINECART;
                    case "chest_minecart" -> hintItem = Items.CHEST_MINECART;
                    case "furnace_minecart" -> hintItem = Items.FURNACE_MINECART;
                    case "tnt_minecart" -> hintItem = Items.TNT_MINECART;
                    case "oak_chest_boat" -> hintItem = Items.OAK_CHEST_BOAT;
                    case "bamboo_chest_raft" -> hintItem = Items.BAMBOO_CHEST_RAFT;
                    case "oak_door" -> hintItem = Items.OAK_DOOR;
                    case "iron_door" -> hintItem = Items.IRON_DOOR;
                    case "oak_fence_gate" -> hintItem = Items.OAK_FENCE_GATE;
                    case "oak_trapdoor" -> hintItem = Items.OAK_TRAPDOOR;
                    case "iron_trapdoor" -> hintItem = Items.IRON_TRAPDOOR;
                    case "tnt" -> hintItem = Items.TNT;
                    case "redstone_lamp" -> hintItem = Items.REDSTONE_LAMP;
                    case "bell" -> hintItem = Items.BELL;
                    case "big_dripleaf" -> hintItem = Items.BIG_DRIPLEAF;
                    case "armor_stand" -> hintItem = Items.ARMOR_STAND;
                    case "redstone_ore" -> hintItem = Items.REDSTONE_ORE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:redstone/noteworthy" -> {
                switch (criterion) {
                    case "banjo" -> hintItem = Items.BARRIER;
                    case "basedrum" -> hintItem = Items.BARRIER;
                    case "bass" -> hintItem = Items.BARRIER;
                    case "bell" -> hintItem = Items.BARRIER;
                    case "bit" -> hintItem = Items.BARRIER;
                    case "chime" -> hintItem = Items.BARRIER;
                    case "cow_bell" -> hintItem = Items.BARRIER;
                    case "didgeridoo" -> hintItem = Items.BARRIER;
                    case "flute" -> hintItem = Items.BARRIER;
                    case "guitar" -> hintItem = Items.BARRIER;
                    case "harp" -> hintItem = Items.BARRIER;
                    case "hat" -> hintItem = Items.BARRIER;
                    case "iron_xylophone" -> hintItem = Items.BARRIER;
                    case "pling" -> hintItem = Items.BARRIER;
                    case "snare" -> hintItem = Items.BARRIER;
                    case "xylophone" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:redstone/railwayman" -> {
                switch (criterion) {
                    case "rail" -> hintItem = Items.RAIL;
                    case "powered_rail" -> hintItem = Items.POWERED_RAIL;
                    case "detector_rail" -> hintItem = Items.DETECTOR_RAIL;
                    case "activator_rail" -> hintItem = Items.ACTIVATOR_RAIL;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:redstone/target_practise" -> {
                switch (criterion) {
                    case "arrow" -> hintItem = Items.ARROW;
                    case "trident" -> hintItem = Items.TRIDENT;
                    case "egg" -> hintItem = Items.EGG;
                    case "snowball" -> hintItem = Items.SNOWBALL;
                    case "potion" -> hintItem = Items.POTION;
                    case "firework_rocket" -> hintItem = Items.FIREWORK_ROCKET;
                    case "experience_bottle" -> hintItem = Items.EXPERIENCE_BOTTLE;
                    case "ender_pearl" -> hintItem = Items.ENDER_PEARL;
                    case "fishing_bobber" -> hintItem = Items.FISHING_ROD;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:redstone/the_incredible_sculk" -> {
                switch (criterion) {
                    case "sculk" -> hintItem = Items.SCULK;
                    case "sculk_vein" -> hintItem = Items.SCULK_VEIN;
                    case "sculk_sensor" -> hintItem = Items.SCULK_SENSOR;
                    case "sculk_catalyst" -> hintItem = Items.SCULK_CATALYST;
                    case "sculk_shrieker" -> hintItem = Items.SCULK_SHRIEKER;
                    case "calibrated_sculk_sensor" -> hintItem = Items.CALIBRATED_SCULK_SENSOR;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:weaponry/axeman" -> {
                switch (criterion) {
                    case "wooden_axe" -> hintItem = Items.WOODEN_AXE;
                    case "stone_axe" -> hintItem = Items.STONE_AXE;
                    case "iron_axe" -> hintItem = Items.IRON_AXE;
                    case "golden_axe" -> hintItem = Items.GOLDEN_AXE;
                    case "diamond_axe" -> hintItem = Items.DIAMOND_AXE;
                    case "netherite_axe" -> hintItem = Items.NETHERITE_AXE;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:weaponry/multiclassed" -> {
                switch (criterion) {
                    case "axe" -> hintItem = Items.DIAMOND_AXE;
                    case "shovel" -> hintItem = Items.DIAMOND_SHOVEL;
                    case "pickaxe" -> hintItem = Items.DIAMOND_PICKAXE;
                    case "hoe" -> hintItem = Items.DIAMOND_HOE;
                    case "sword" -> hintItem = Items.DIAMOND_SWORD;
                    case "bow" -> hintItem = Items.BOW;
                    case "crossbow" -> hintItem = Items.CROSSBOW;
                    case "trident_melee" -> hintItem = Items.TRIDENT;
                    case "tnt" -> hintItem = Items.TNT;
                    case "snowball" -> hintItem = Items.SNOWBALL;
                    case "egg" -> hintItem = Items.EGG;
                    case "fishing_rod" -> hintItem = Items.FISHING_ROD;
                    case "splash_potion" -> hintItem = Items.SPLASH_POTION;
                    case "lingering_potion" -> hintItem = Items.LINGERING_POTION;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:weaponry/pyrotechnic" -> {
                switch (criterion) {
                    case "small_ball" -> hintItem = Items.BARRIER;
                    case "large_ball" -> hintItem = Items.BARRIER;
                    case "star" -> hintItem = Items.BARRIER;
                    case "creeper" -> hintItem = Items.BARRIER;
                    case "burst" -> hintItem = Items.BARRIER;
                    case "flicker" -> hintItem = Items.BARRIER;
                    case "trail" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "blazeandcave:weaponry/shovel_knight" -> {
                switch (criterion) {
                    case "wooden_shovel" -> hintItem = Items.WOODEN_SHOVEL;
                    case "stone_shovel" -> hintItem = Items.STONE_SHOVEL;
                    case "iron_shovel" -> hintItem = Items.IRON_SHOVEL;
                    case "golden_shovel" -> hintItem = Items.GOLDEN_SHOVEL;
                    case "diamond_shovel" -> hintItem = Items.DIAMOND_SHOVEL;
                    case "netherite_shovel" -> hintItem = Items.NETHERITE_SHOVEL;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "minecraft:adventure/trim_with_all_exclusive_armor_patterns" -> {
                switch (criterion) {
                    case "coast_armor_trim" -> hintItem = Items.BARRIER;
                    case "dune_armor_trim" -> hintItem = Items.BARRIER;
                    case "eye_armor_trim" -> hintItem = Items.BARRIER;
                    case "host_armor_trim" -> hintItem = Items.BARRIER;
                    case "raiser_armor_trim" -> hintItem = Items.BARRIER;
                    case "rib_armor_trim" -> hintItem = Items.BARRIER;
                    case "sentry_armor_trim" -> hintItem = Items.BARRIER;
                    case "shaper_armor_trim" -> hintItem = Items.BARRIER;
                    case "silence_armor_trim" -> hintItem = Items.BARRIER;
                    case "snout_armor_trim" -> hintItem = Items.BARRIER;
                    case "spire_armor_trim" -> hintItem = Items.BARRIER;
                    case "tide_armor_trim" -> hintItem = Items.BARRIER;
                    case "vex_armor_trim" -> hintItem = Items.BARRIER;
                    case "ward_armor_trim" -> hintItem = Items.BARRIER;
                    case "wayfinder_armor_trim" -> hintItem = Items.BARRIER;
                    case "wild_armor_trim" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "minecraft:husbandry/balanced_diet" -> {
                switch (criterion) {
                    case "apple" -> hintItem = Items.APPLE;
                    case "mushroom_stew" -> hintItem = Items.MUSHROOM_STEW;
                    case "bread" -> hintItem = Items.BREAD;
                    case "porkchop" -> hintItem = Items.PORKCHOP;
                    case "cooked_porkchop" -> hintItem = Items.COOKED_PORKCHOP;
                    case "golden_apple" -> hintItem = Items.GOLDEN_APPLE;
                    case "enchanted_golden_apple" -> hintItem = Items.ENCHANTED_GOLDEN_APPLE;
                    case "raw_cod" -> hintItem = Items.COD;
                    case "raw_salmon" -> hintItem = Items.SALMON;
                    case "tropical_fish" -> hintItem = Items.TROPICAL_FISH;
                    case "pufferfish" -> hintItem = Items.PUFFERFISH;
                    case "cooked_cod" -> hintItem = Items.COOKED_COD;
                    case "cooked_salmon" -> hintItem = Items.COOKED_SALMON;
                    case "cookie" -> hintItem = Items.COOKIE;
                    case "melon_slice" -> hintItem = Items.MELON_SLICE;
                    case "beef" -> hintItem = Items.BEEF;
                    case "cooked_beef" -> hintItem = Items.COOKED_BEEF;
                    case "chicken" -> hintItem = Items.CHICKEN;
                    case "cooked_chicken" -> hintItem = Items.COOKED_CHICKEN;
                    case "rotten_flesh" -> hintItem = Items.ROTTEN_FLESH;
                    case "spider_eye" -> hintItem = Items.SPIDER_EYE;
                    case "carrot" -> hintItem = Items.CARROT;
                    case "potato" -> hintItem = Items.POTATO;
                    case "baked_potato" -> hintItem = Items.BAKED_POTATO;
                    case "poisonous_potato" -> hintItem = Items.POISONOUS_POTATO;
                    case "golden_carrot" -> hintItem = Items.GOLDEN_CARROT;
                    case "pumpkin_pie" -> hintItem = Items.PUMPKIN_PIE;
                    case "rabbit" -> hintItem = Items.RABBIT;
                    case "cooked_rabbit" -> hintItem = Items.COOKED_RABBIT;
                    case "rabbit_stew" -> hintItem = Items.RABBIT_STEW;
                    case "mutton" -> hintItem = Items.MUTTON;
                    case "cooked_mutton" -> hintItem = Items.COOKED_MUTTON;
                    case "chorus_fruit" -> hintItem = Items.CHORUS_FRUIT;
                    case "beetroot" -> hintItem = Items.BEETROOT;
                    case "beetroot_soup" -> hintItem = Items.BEETROOT_SOUP;
                    case "dried_kelp" -> hintItem = Items.DRIED_KELP;
                    case "suspicious_stew" -> hintItem = Items.SUSPICIOUS_STEW;
                    case "sweet_berries" -> hintItem = Items.SWEET_BERRIES;
                    case "honey_bottle" -> hintItem = Items.HONEY_BOTTLE;
                    case "glow_berries" -> hintItem = Items.GLOW_BERRIES;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
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
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
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
            case "minecraft:husbandry/froglights" -> {
                switch (criterion) {
                    case "verdant_froglight" -> hintItem = Items.VERDANT_FROGLIGHT;
                    case "ochre_froglight" -> hintItem = Items.OCHRE_FROGLIGHT;
                    case "pearlescent_froglight" -> hintItem = Items.PEARLESCENT_FROGLIGHT;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "minecraft:husbandry/leash_all_frog_variants" -> {
                switch (criterion) {
                    case "minecraft:temperate" -> hintItem = Items.BARRIER;
                    case "minecraft:warm" -> hintItem = Items.BARRIER;
                    case "minecraft:cold" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "minecraft:nether/explore_nether" -> {
                switch (criterion) {
                    case "nether_wastes" -> hintItem = Items.BARRIER;
                    case "crimson_forest" -> hintItem = Items.BARRIER;
                    case "warped_forest" -> hintItem = Items.BARRIER;
                    case "soul_sand_valley" -> hintItem = Items.BARRIER;
                    case "basalt_deltas" -> hintItem = Items.BARRIER;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
            case "minecraft:nether/netherite_armor" -> {
                switch (criterion) {
                    case "netherite_helmet" -> hintItem = Items.NETHERITE_HELMET;
                    case "netherite_chestplate" -> hintItem = Items.NETHERITE_CHESTPLATE;
                    case "netherite_leggings" -> hintItem = Items.NETHERITE_LEGGINGS;
                    case "netherite_boots" -> hintItem = Items.NETHERITE_BOOTS;
                    default -> throw new IllegalStateException("Unexpected value: " + criterion);
                }
            }
        }
        if (hintItem == null) {
            return null;
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
                if (id.equals("blazeandcave:challenges/the_perfect_run") || id.equals("blazeandcave:challenges/were_in_the_endgame_now") || id.equals("blazeandcave:nether/this_ones_mine") || id.equals("blazeandcave:redstone/take_notes")) {
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
