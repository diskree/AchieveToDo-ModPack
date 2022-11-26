package com.diskree.achievetodo;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

public enum BlockedAction {
    eat_salmon("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SALMON, null, 2),
    eat_cod("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COD, null, 3),
    eat_tropical_fish("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.TROPICAL_FISH, null, 4),
    eat_rotten_flesh("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.ROTTEN_FLESH, null, 6),
    jump("Вы сейчас не можете прыгать", BlockedActionType.ACTION, null, null, 7),
    eat_spider_eye("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SPIDER_EYE, null, 8),
    eat_pufferfish("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.PUFFERFISH, null, 12),
    eat_chicken("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.CHICKEN, null, 15),
    eat_poisonous_potato("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.POISONOUS_POTATO, null, 17),
    eat_suspicious_stew("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SUSPICIOUS_STEW, null, 20),
    eat_mutton("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.MUTTON, null, 22),
    eat_rabbit("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.RABBIT, null, 24),
    break_blocks_in_positive_y("Вы сейчас не можете ломать блоки", BlockedActionType.ACTION, null, null, 25),
    eat_sweet_berries("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SWEET_BERRIES, null, 27),
    eat_glow_berries("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.GLOW_BERRIES, null, 29),
    eat_porkchop("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.PORKCHOP, null, 33),
    eat_beetroot("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BEETROOT, null, 37),
    eat_beetroot_soup("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BEETROOT_SOUP, null, 45),
    using_crafting_table("Вы сейчас не можете использовать верстак", BlockedActionType.BLOCK, null, null, 50),
    eat_carrot("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.CARROT, null, 52),
    eat_apple("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.APPLE, null, 55),
    eat_potato("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.POTATO, null, 58),
    using_furnace("Вы сейчас не можете использовать печь", BlockedActionType.BLOCK, null, null, 60),
    eat_dried_kelp("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.DRIED_KELP, null, 62),
    eat_melon_slice("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.MELON_SLICE, null, 64),
    using_stone_tools("Вы сейчас не можете использовать каменные инструменты", BlockedActionType.TOOLS, null, null, 70),
    eat_cookie("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKIE, null, 72),
    eat_honey_bottle("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.HONEY_BOTTLE, null, 77),
    using_boat("Вы сейчас не можете садиться в лодку", BlockedActionType.ACTION, null, null, 80),
    eat_beef("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BEEF, null, 88),
    using_shield("Вы сейчас не можете использовать щит", BlockedActionType.ACTION, null, null, 90),
    using_water_bucket("Вы сейчас не можете разливать воду", BlockedActionType.ACTION, null, null, 95),
    eat_bread("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BREAD, null, 100),
    eat_rabbit_stew("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.RABBIT_STEW, null, 105),
    using_iron_tools("Вы сейчас не можете использовать железные инструменты", BlockedActionType.TOOLS, null, null, 110),
    eat_mushroom_stew("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.MUSHROOM_STEW, null, 111),
    eat_baked_potato("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BAKED_POTATO, null, 130),
    eat_pumpkin_pie("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.PUMPKIN_PIE, null, 145),
    equip_iron_armor("Вы сейчас не можете одевать железную броню", BlockedActionType.ARMOR, null, null, 150),
    using_anvil("Вы сейчас не можете использовать наковальню", BlockedActionType.BLOCK, null, null, 175),
    eat_cooked_salmon("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_SALMON, null, 190),
    eat_cooked_cod("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_COD, null, 195),
    break_blocks_in_negative_y("Вы сейчас не можете ломать блоки ниже нуля", BlockedActionType.ACTION, null, null, 200),
    villager_mason("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.MASON, 210),
    eat_cooked_rabbit("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_RABBIT, null, 220),
    villager_cartographer("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.CARTOGRAPHER, 230),
    using_bow("Вы сейчас не можете использовать лук", BlockedActionType.ACTION, null, null, 240),
    using_smoker("Вы сейчас не можете использовать коптильню", BlockedActionType.BLOCK, null, null, 250),
    eat_golden_apple("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.GOLDEN_APPLE, null, 260),
    using_blast_furnace("Вы сейчас не можете использовать плавильную печь", BlockedActionType.BLOCK, null, null, 270),
    villager_leatherworker("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.LEATHERWORKER, 280),
    nether("Нижний Мир закрыт", BlockedActionType.PORTAL, null, null, 300),
    villager_shepherd("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.SHEPHERD, 315),
    villager_butcher("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.BUTCHER, 330),
    using_diamond_tools("Вы сейчас не можете использовать алмазные инструменты", BlockedActionType.TOOLS, null, null, 350),
    equip_diamond_armor("Вы сейчас не можете одевать алмазную броню", BlockedActionType.ARMOR, null, null, 380),
    villager_farmer("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.FARMER, 385),
    using_ender_chest("Вы сейчас не можете использовать эндер-сундук", BlockedActionType.BLOCK, null, null, 390),
    villager_cleric("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.CLERIC, 400),
    using_brewing_stand("Вы сейчас не можете использовать зельеварку", BlockedActionType.BLOCK, null, null, 420),
    villager_fisherman("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.FISHERMAN, 444),
    eat_cooked_chicken("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_CHICKEN, null, 450),
    eat_cooked_mutton("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_MUTTON, null, 480),
    using_beacon("Вы сейчас не можете использовать маяк", BlockedActionType.BLOCK, null, null, 500),
    eat_cooked_porkchop("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_PORKCHOP, null, 510),
    villager_fletcher("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.FLETCHER, 520),
    using_netherite_tools("Вы сейчас не можете использовать незеритовые инструменты", BlockedActionType.TOOLS, null, null, 530),
    villager_armorer("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.ARMORER, 540),
    equip_netherite_armor("Вы сейчас не можете одевать незеритовую броню", BlockedActionType.ARMOR, null, null, 550),
    eat_cooked_beef("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_BEEF, null, 580),
    villager_weaponsmith("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.WEAPONSMITH, 590),
    end("Край закрыт", BlockedActionType.PORTAL, null, null, 600),
    equip_elytra("Вы сейчас не можете одевать элитры", BlockedActionType.ARMOR, null, null, 650),
    eat_enchanted_golden_apple("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.ENCHANTED_GOLDEN_APPLE, null, 680),
    eat_chorus_fruit("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.CHORUS_FRUIT, null, 700),
    end_gate("Этот портал в данный момент недоступен", BlockedActionType.PORTAL, null, null, 800),
    using_shulker_box("Вы сейчас не можете использовать шалкеровый ящик", BlockedActionType.BLOCK, null, null, 825),
    using_fireworks_while_fly("Вы сейчас не можете использовать фейерверки для полёта", BlockedActionType.ACTION, null, null, 850),
    villager_toolsmith("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.TOOLSMITH, 860),
    eat_golden_carrot("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.GOLDEN_CARROT, null, 888),
    villager_librarian("Торговля с жителем этой профессии недоступна", BlockedActionType.VILLAGER, null, VillagerProfession.LIBRARIAN, 900),
    using_enchanting_table("Вы сейчас не можете использовать стол зачарований", BlockedActionType.BLOCK, null, null, 950),
    drop_totem("Тотем бессмертия недоступен", BlockedActionType.ACTION, null, null, 1000);

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
        return Text.of(description + ". Для разблокировки осталось выполнить достижений: " + leftAchievementsCount).copy().formatted(Formatting.YELLOW);
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
