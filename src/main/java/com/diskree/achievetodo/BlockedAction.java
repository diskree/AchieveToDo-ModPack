package com.diskree.achievetodo;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public enum BlockedAction {
    eat_salmon("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SALMON, 2),
    eat_cod("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COD, 3),
    eat_tropical_fish("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.TROPICAL_FISH, 4),
    eat_rotten_flesh("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.ROTTEN_FLESH, 6),
    jump("Вы сейчас не можете прыгать", BlockedActionType.ACTION, null, 7),
    eat_spider_eye("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SPIDER_EYE, 8),
    sprint("Вы сейчас не можете бегать", BlockedActionType.ACTION, null, 10),
    eat_pufferfish("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.PUFFERFISH, 12),
    eat_chicken("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.CHICKEN, 15),
    eat_poisonous_potato("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.POISONOUS_POTATO, 17),
    eat_suspicious_stew("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SUSPICIOUS_STEW, 20),
    eat_mutton("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.MUTTON, 22),
    eat_rabbit("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.RABBIT, 24),
    break_blocks_in_positive_y("Вы сейчас не можете ломать блоки", BlockedActionType.ACTION, null, 25),
    eat_sweet_berries("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SWEET_BERRIES, 27),
    eat_glow_berries("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.GLOW_BERRIES, 29),
    eat_porkchop("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.PORKCHOP, 33),
    eat_beetroot("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BEETROOT, 37),
    eat_beetroot_soup("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BEETROOT_SOUP, 45),
    using_crafting_table("Вы сейчас не можете использовать верстак", BlockedActionType.BLOCK, null, 50),
    eat_carrot("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.CARROT, 52),
    eat_apple("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.APPLE, 55),
    eat_potato("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.POTATO, 58),
    using_furnace("Вы сейчас не можете использовать печь", BlockedActionType.BLOCK, null, 60),
    eat_dried_kelp("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.DRIED_KELP, 62),
    eat_melon_slice("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.MELON_SLICE, 64),
    using_iron_tools("Вы сейчас не можете использовать железные инструменты", BlockedActionType.TOOLS, null, 70),
    eat_cookie("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKIE, 72),
    eat_honey_bottle("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.HONEY_BOTTLE, 77),
    using_boat("Вы сейчас не можете садиться в лодку", BlockedActionType.ACTION, null, 80),
    eat_beef("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BEEF, 88),
    using_shield("Вы сейчас не можете использовать щит", BlockedActionType.ACTION, null, 90),
    using_water_bucket("Вы сейчас не можете разливать воду", BlockedActionType.ACTION, null, 95),
    eat_bread("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BREAD, 100),
    eat_rabbit_stew("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.RABBIT_STEW, 105),
    equip_iron_armor("Вы сейчас не можете одевать железную броню", BlockedActionType.ARMOR, null, 110),
    eat_mushroom_stew("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.MUSHROOM_STEW, 111),
    eat_baked_potato("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BAKED_POTATO, 130),
    eat_pumpkin_pie("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.PUMPKIN_PIE, 145),
    using_anvil("Вы сейчас не можете использовать наковальню", BlockedActionType.BLOCK, null, 175),
    eat_cooked_salmon("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_SALMON, 190),
    eat_cooked_cod("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_COD, 195),
    break_blocks_in_negative_y("Вы сейчас не можете ломать блоки ниже нуля", BlockedActionType.ACTION, null, 200),
    eat_cooked_rabbit("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_RABBIT, 220),
    using_smoker("Вы сейчас не можете использовать коптильню", BlockedActionType.BLOCK, null, 250),
    using_blast_furnace("Вы сейчас не можете использовать плавильную печь", BlockedActionType.BLOCK, null, 270),
    nether("Нижний Мир закрыт", BlockedActionType.PORTAL, null, 300),
    eat_golden_apple("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.GOLDEN_APPLE, 333),
    using_diamond_tools("Вы сейчас не можете использовать алмазные инструменты", BlockedActionType.TOOLS, null, 350),
    equip_diamond_armor("Вы сейчас не можете одевать алмазную броню", BlockedActionType.ARMOR, null, 380),
    using_ender_chest("Вы сейчас не можете использовать эндер-сундук", BlockedActionType.BLOCK, null, 390),
    using_brewing_stand("Вы сейчас не можете использовать зельеварку", BlockedActionType.BLOCK, null, 420),
    eat_cooked_chicken("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_CHICKEN, 450),
    eat_cooked_mutton("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_MUTTON, 480),
    using_beacon("Вы сейчас не можете использовать маяк", BlockedActionType.BLOCK, null, 500),
    eat_cooked_porkchop("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_PORKCHOP, 510),
    using_netherite_tools("Вы сейчас не можете использовать незеритовые инструменты", BlockedActionType.TOOLS, null, 530),
    equip_netherite_armor("Вы сейчас не можете одевать незеритовую броню", BlockedActionType.ARMOR, null, 550),
    eat_cooked_beef("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_BEEF, 580),
    end("Край закрыт", BlockedActionType.PORTAL, null, 600),
    equip_elytra("Вы сейчас не можете одевать элитры", BlockedActionType.ARMOR, null, 650),
    eat_enchanted_golden_apple("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.ENCHANTED_GOLDEN_APPLE, 680),
    eat_chorus_fruit("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.CHORUS_FRUIT, 700),
    end_gate("Этот портал в данный момент недоступен", BlockedActionType.PORTAL, null, 800),
    using_shulker_box("Вы сейчас не можете использовать шалкеровый ящик", BlockedActionType.BLOCK, null, 825),
    using_fireworks_while_fly("Вы сейчас не можете использовать фейерверки для полёта", BlockedActionType.ACTION, null, 850),
    eat_golden_carrot("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.GOLDEN_CARROT, 888),
    trade_with_villager("Торговля с деревенскими жителями недоступна", BlockedActionType.ACTION, null, 900),
    using_enchanting_table("Вы сейчас не можете использовать стол зачарований", BlockedActionType.BLOCK, null, 950),
    drop_totem("Тотем бессмертия недоступен", BlockedActionType.ACTION, null, 1000);

    private final String description;
    private final BlockedActionType actionType;
    private final FoodComponent foodComponent;
    private final int achievementsCountToUnlock;

    BlockedAction(String description, BlockedActionType actionType, FoodComponent foodComponent, int achievementsCountToUnlock) {
        this.description = description;
        this.actionType = actionType;
        this.foodComponent = foodComponent;
        this.achievementsCountToUnlock = achievementsCountToUnlock;
    }

    public BlockedActionType getActionType() {
        return actionType;
    }

    public FoodComponent getFoodComponent() {
        return foodComponent;
    }

    public int getAchievementsCountToUnlock() {
        return achievementsCountToUnlock;
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
