package com.diskree.achievetodo;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

public enum BlockedAction {
    eat_salmon("blocked.food", BlockedActionType.FOOD, FoodComponents.SALMON, null, 2),
    eat_cod("blocked.food", BlockedActionType.FOOD, FoodComponents.COD, null, 3),
    eat_tropical_fish("blocked.food", BlockedActionType.FOOD, FoodComponents.TROPICAL_FISH, null, 4),
    eat_rotten_flesh("blocked.food", BlockedActionType.FOOD, FoodComponents.ROTTEN_FLESH, null, 6),
    jump("blocked.jump", BlockedActionType.ACTION, null, null, 7),
    eat_spider_eye("blocked.food", BlockedActionType.FOOD, FoodComponents.SPIDER_EYE, null, 8),
    eat_pufferfish("blocked.food", BlockedActionType.FOOD, FoodComponents.PUFFERFISH, null, 12),
    eat_chicken("blocked.food", BlockedActionType.FOOD, FoodComponents.CHICKEN, null, 15),
    eat_poisonous_potato("blocked.food", BlockedActionType.FOOD, FoodComponents.POISONOUS_POTATO, null, 17),
    eat_suspicious_stew("blocked.food", BlockedActionType.FOOD, FoodComponents.SUSPICIOUS_STEW, null, 20),
    eat_mutton("blocked.food", BlockedActionType.FOOD, FoodComponents.MUTTON, null, 22),
    eat_rabbit("blocked.food", BlockedActionType.FOOD, FoodComponents.RABBIT, null, 24),
    break_blocks_in_positive_y("blocked.break_blocks_pos", BlockedActionType.ACTION, null, null, 25),
    eat_sweet_berries("blocked.food", BlockedActionType.FOOD, FoodComponents.SWEET_BERRIES, null, 27),
    eat_glow_berries("blocked.food", BlockedActionType.FOOD, FoodComponents.GLOW_BERRIES, null, 29),
    eat_porkchop("blocked.food", BlockedActionType.FOOD, FoodComponents.PORKCHOP, null, 33),
    eat_beetroot("blocked.food", BlockedActionType.FOOD, FoodComponents.BEETROOT, null, 37),
    eat_beetroot_soup("blocked.food", BlockedActionType.FOOD, FoodComponents.BEETROOT_SOUP, null, 45),
    using_crafting_table("blocked.crafting_table", BlockedActionType.BLOCK, null, null, 50),
    eat_carrot("blocked.food", BlockedActionType.FOOD, FoodComponents.CARROT, null, 52),
    eat_apple("blocked.food", BlockedActionType.FOOD, FoodComponents.APPLE, null, 55),
    eat_potato("blocked.food", BlockedActionType.FOOD, FoodComponents.POTATO, null, 58),
    using_furnace("blocked.furnace", BlockedActionType.BLOCK, null, null, 60),
    eat_dried_kelp("blocked.food", BlockedActionType.FOOD, FoodComponents.DRIED_KELP, null, 62),
    eat_melon_slice("blocked.food", BlockedActionType.FOOD, FoodComponents.MELON_SLICE, null, 64),
    using_stone_tools("blocked.stone_tools", BlockedActionType.TOOLS, null, null, 70),
    eat_cookie("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKIE, null, 72),
    eat_honey_bottle("blocked.food", BlockedActionType.FOOD, FoodComponents.HONEY_BOTTLE, null, 77),
    using_boat("blocked.boat", BlockedActionType.ACTION, null, null, 80),
    eat_beef("blocked.food", BlockedActionType.FOOD, FoodComponents.BEEF, null, 88),
    using_shield("blocked.shield", BlockedActionType.ACTION, null, null, 90),
    using_water_bucket("blocked.water_bucket", BlockedActionType.ACTION, null, null, 95),
    eat_bread("blocked.food", BlockedActionType.FOOD, FoodComponents.BREAD, null, 100),
    eat_rabbit_stew("blocked.food", BlockedActionType.FOOD, FoodComponents.RABBIT_STEW, null, 105),
    using_iron_tools("blocked.iron_tools", BlockedActionType.TOOLS, null, null, 110),
    eat_mushroom_stew("blocked.food", BlockedActionType.FOOD, FoodComponents.MUSHROOM_STEW, null, 111),
    eat_baked_potato("blocked.food", BlockedActionType.FOOD, FoodComponents.BAKED_POTATO, null, 130),
    eat_pumpkin_pie("blocked.food", BlockedActionType.FOOD, FoodComponents.PUMPKIN_PIE, null, 145),
    equip_iron_armor("blocked.equip_iron", BlockedActionType.ARMOR, null, null, 150),
    using_anvil("blocked.anvil", BlockedActionType.BLOCK, null, null, 175),
    eat_cooked_salmon("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_SALMON, null, 190),
    eat_cooked_cod("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_COD, null, 195),
    break_blocks_in_negative_y("blocked.break_blocks_neg", BlockedActionType.ACTION, null, null, 200),
    villager_mason("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.MASON, 210),
    eat_cooked_rabbit("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_RABBIT, null, 220),
    villager_cartographer("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.CARTOGRAPHER, 230),
    using_bow("blocked.bow", BlockedActionType.ACTION, null, null, 240),
    using_smoker("blocked.smoker", BlockedActionType.BLOCK, null, null, 250),
    eat_golden_apple("blocked.food", BlockedActionType.FOOD, FoodComponents.GOLDEN_APPLE, null, 260),
    using_blast_furnace("blocked.blast_furnace", BlockedActionType.BLOCK, null, null, 270),
    villager_leatherworker("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.LEATHERWORKER, 280),
    nether("blocked.nether", BlockedActionType.PORTAL, null, null, 300),
    villager_shepherd("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.SHEPHERD, 315),
    villager_butcher("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.BUTCHER, 330),
    using_diamond_tools("blocked.diamond_tools", BlockedActionType.TOOLS, null, null, 350),
    equip_diamond_armor("blocked.equip_diamond", BlockedActionType.ARMOR, null, null, 380),
    villager_farmer("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.FARMER, 385),
    using_ender_chest("blocked.ender_chest", BlockedActionType.BLOCK, null, null, 390),
    villager_cleric("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.CLERIC, 400),
    using_brewing_stand("blocked.brewing_stand", BlockedActionType.BLOCK, null, null, 420),
    villager_fisherman("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.FISHERMAN, 444),
    eat_cooked_chicken("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_CHICKEN, null, 450),
    eat_cooked_mutton("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_MUTTON, null, 480),
    using_beacon("blocked.beacon", BlockedActionType.BLOCK, null, null, 500),
    eat_cooked_porkchop("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_PORKCHOP, null, 510),
    villager_fletcher("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.FLETCHER, 520),
    using_netherite_tools("blocked.netherite_tools", BlockedActionType.TOOLS, null, null, 530),
    villager_armorer("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.ARMORER, 540),
    equip_netherite_armor("blocked.equip_netherite", BlockedActionType.ARMOR, null, null, 550),
    eat_cooked_beef("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_BEEF, null, 580),
    villager_weaponsmith("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.WEAPONSMITH, 590),
    end("blocked.end", BlockedActionType.PORTAL, null, null, 600),
    equip_elytra("blocked.elytra", BlockedActionType.ARMOR, null, null, 650),
    eat_enchanted_golden_apple("blocked.food", BlockedActionType.FOOD, FoodComponents.ENCHANTED_GOLDEN_APPLE, null, 680),
    eat_chorus_fruit("blocked.food", BlockedActionType.FOOD, FoodComponents.CHORUS_FRUIT, null, 700),
    end_gate("blocked.gateway", BlockedActionType.PORTAL, null, null, 800),
    using_shulker_box("blocked.shulker_box", BlockedActionType.BLOCK, null, null, 825),
    using_fireworks_while_fly("blocked.firework", BlockedActionType.ACTION, null, null, 850),
    villager_toolsmith("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.TOOLSMITH, 860),
    eat_golden_carrot("blocked.food", BlockedActionType.FOOD, FoodComponents.GOLDEN_CARROT, null, 888),
    villager_librarian("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.LIBRARIAN, 900),
    using_enchanting_table("blocked.enchanting_table", BlockedActionType.BLOCK, null, null, 950),
    drop_totem("blocked.totem", BlockedActionType.ACTION, null, null, 1000);

    private final String description;
    private final BlockedActionType actionType;
    private final FoodComponent foodComponent;
    private final VillagerProfession villagerProfession;
    private final int achievementsCountToUnlock;

    BlockedAction(String description, BlockedActionType actionType, FoodComponent foodComponent, VillagerProfession villagerProfession, int achievementsCountToUnlock) {
        this.description = description;
        this.actionType = actionType;
        this.foodComponent = foodComponent;
        this.villagerProfession = villagerProfession;
        this.achievementsCountToUnlock = achievementsCountToUnlock;
    }

    public BlockedActionType getActionType() {
        return actionType;
    }

    public FoodComponent getFoodComponent() {
        return foodComponent;
    }

    public VillagerProfession getVillagerProfession() {
        return villagerProfession;
    }

    public int getAchievementsCountToUnlock() {
        return achievementsCountToUnlock;
    }

    public boolean isUnlocked() {
        return AchieveToDoMod.lastAchievementsCount >= getAchievementsCountToUnlock();
    }

    public Text getLockDescription() {
        int leftAchievementsCount = getAchievementsCountToUnlock() - AchieveToDoMod.lastAchievementsCount;
        return Text.of(Text.translatable(description).getString() + Text.translatable("unblock.amount").getString() + leftAchievementsCount).copy().formatted(Formatting.YELLOW);
    }

    public Identifier buildAdvancementId() {
        return new Identifier(AchieveToDoMod.ID, AchieveToDoMod.ADVANCEMENT_PATH_PREFIX + name());
    }

    public static BlockedAction map(String name) {
        for (BlockedAction action : values()) {
            if (action.name().equals(name)) {
                return action;
            }
        }
        return null;
    }
}
