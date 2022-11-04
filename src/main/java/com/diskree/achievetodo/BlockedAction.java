package com.diskree.achievetodo;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public enum BlockedAction {
    EAT_SALMON("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SALMON, 2),
    EAT_COD("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COD, 3),
    EAT_TROPICAL_FISH("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.TROPICAL_FISH, 4),
    EAT_ROTTEN_FLESH("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.ROTTEN_FLESH, 6),
    JUMP("Вы сейчас не можете прыгать", BlockedActionType.ACTION, null, 7),
    EAT_SPIDER_EYE("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SPIDER_EYE, 8),
    SPRINT("Вы сейчас не можете бегать", BlockedActionType.ACTION, null, 10),
    EAT_CHICKEN("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.CHICKEN, 14),
    EAT_POISONOUS_POTATO("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.POISONOUS_POTATO, 16),
    EAT_SUSPICIOUS_STEW("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SUSPICIOUS_STEW, 19),
    EAT_MUTTON("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.MUTTON, 22),
    EAT_RABBIT("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.RABBIT, 24),
    BREAK_BLOCKS_IN_POSITIVE_Y("Вы сейчас не можете ломать блоки", BlockedActionType.ACTION, null, 25),
    EAT_SWEET_BERRIES("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.SWEET_BERRIES, 27),
    EAT_PORKCHOP("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.PORKCHOP, 29),
    EAT_BEETROOT("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BEETROOT, 37),
    EAT_BEETROOT_SOUP("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BEETROOT_SOUP, 45),
    USING_CRAFTING_TABLE("Вы сейчас не можете использовать верстак", BlockedActionType.BLOCK, null, 50),
    EAT_CARROT("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.CARROT, 52),
    EAT_APPLE("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.APPLE, 55),
    EAT_POTATO("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.POTATO, 58),
    USING_FURNACE("Вы сейчас не можете использовать печь", BlockedActionType.BLOCK, null, 60),
    EAT_DRIED_KELP("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.DRIED_KELP, 62),
    EAT_MELON_SLICE("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.MELON_SLICE, 64),
    USING_IRON_TOOLS("Вы сейчас не можете использовать железные инструменты", BlockedActionType.TOOLS, null, 70),
    EAT_COOKIE("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKIE, 72),
    EAT_HONEY_BOTTLE("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.HONEY_BOTTLE, 77),
    USING_BOAT("Вы сейчас не можете садиться в лодку", BlockedActionType.ACTION, null, 80),
    EAT_BEEF("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BEEF, 88),
    USING_SHIELD("Вы сейчас не можете использовать щит", BlockedActionType.ACTION, null, 90),
    USING_WATER_BUCKET("Вы сейчас не можете разливать воду", BlockedActionType.ACTION, null, 95),
    EAT_BREAD("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BREAD, 100),
    EAT_RABBIT_STEW("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.RABBIT_STEW, 105),
    EQUIP_IRON_ARMOR("Вы сейчас не можете одевать железную броню", BlockedActionType.ARMOR, null, 110),
    EAT_MUSHROOM_STEW("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.MUSHROOM_STEW, 111),
    USING_SMOKER("Вы сейчас не можете использовать коптильню", BlockedActionType.BLOCK, null, 125),
    EAT_PUMPKIN_PIE("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.PUMPKIN_PIE, 145),
    USING_BLAST_FURNACE("Вы сейчас не можете использовать плавильную печь", BlockedActionType.BLOCK, null, 150),
    USING_ANVIL("Вы сейчас не можете использовать наковальню", BlockedActionType.BLOCK, null, 175),
    EAT_COOKED_SALMON("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_SALMON, 190),
    EAT_COOKED_COD("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_COD, 195),
    BREAK_BLOCKS_IN_NEGATIVE_Y("Вы сейчас не можете ломать блоки ниже 0 высоты", BlockedActionType.ACTION, null, 200),
    EAT_GLOW_BERRIES("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.GLOW_BERRIES, 240),
    NETHER("Нижний Мир закрыт", BlockedActionType.PORTAL, null, 300),
    EAT_GOLDEN_APPLE("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.GOLDEN_APPLE, 333),
    USING_DIAMOND_TOOLS("Вы сейчас не можете использовать алмазные инструменты", BlockedActionType.TOOLS, null, 350),
    EAT_BAKED_POTATO("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.BAKED_POTATO, 370),
    EQUIP_DIAMOND_ARMOR("Вы сейчас не можете одевать алмазную броню", BlockedActionType.ARMOR, null, 400),
    EAT_COOKED_RABBIT("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_RABBIT, 420),
    USING_BREWING_STAND("Вы сейчас не можете использовать зельеварку", BlockedActionType.BLOCK, null, 450),
    EAT_PUFFERFISH("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.PUFFERFISH, 455),
    EAT_COOKED_CHICKEN("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_CHICKEN, 500),
    EAT_COOKED_MUTTON("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_MUTTON, 525),
    USING_BEACON("Вы сейчас не можете использовать маяк", BlockedActionType.BLOCK, null, 600),
    EAT_COOKED_PORKCHOP("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_PORKCHOP, 635),
    USING_NETHERITE_TOOLS("Вы сейчас не можете использовать незеритовые инструменты", BlockedActionType.TOOLS, null, 650),
    EAT_COOKED_BEEF("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.COOKED_BEEF, 675),
    EQUIP_NETHERITE_ARMOR("Вы сейчас не можете одевать незеритовую броню", BlockedActionType.ARMOR, null, 700),
    END("Край закрыт", BlockedActionType.PORTAL, null, 725),
    EAT_CHORUS_FRUIT("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.CHORUS_FRUIT, 740),
    EQUIP_ELYTRA("Вы сейчас не можете одевать элитры", BlockedActionType.ARMOR, null, 775),
    EAT_ENCHANTED_GOLDEN_APPLE("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.ENCHANTED_GOLDEN_APPLE, 777),
    END_GATE("Этот портал в данный момент недоступен", BlockedActionType.PORTAL, null, 800),
    USING_ENDER_CHEST("Вы сейчас не можете использовать эндер-сундук", BlockedActionType.BLOCK, null, 825),
    USING_SHULKER_BOX("Вы сейчас не можете использовать шалкеровый ящик", BlockedActionType.BLOCK, null, 850),
    EAT_GOLDEN_CARROT("Эта еда сейчас недоступна", BlockedActionType.FOOD, FoodComponents.GOLDEN_CARROT, 888),
    USING_FIREWORKS_WHILE_FLY("Вы сейчас не можете использовать фейерверки для полёта", BlockedActionType.ACTION, null, 900),
    TRADE_WITH_VILLAGER("Торговля с деревенскими жителями недоступна", BlockedActionType.ACTION, null, 925),
    USING_ENCHANTING_TABLE("Вы сейчас не можете использовать стол зачарований", BlockedActionType.BLOCK, null, 950),
    DROP_TOTEM("Тотем бессмертия недоступен", BlockedActionType.ACTION, null, 1000);

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
        return new Identifier(AchieveToDoMod.ID, AchieveToDoMod.ADVANCEMENT_PATH_PREFIX + name().toLowerCase());
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
