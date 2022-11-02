package com.diskree.achievetodo;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;

public enum BlockedAction {
    EAT_SALMON(null, "Доступна еда", FoodComponents.SALMON, 2),
    EAT_COD(null, "Доступна еда", FoodComponents.COD, 3),
    EAT_TROPICAL_FISH(null, "Доступна еда", FoodComponents.TROPICAL_FISH, 4),
    EAT_ROTTEN_FLESH(null, "Доступна еда", FoodComponents.ROTTEN_FLESH, 6),
    JUMP("Вы сейчас не можете прыгать", "Доступно действие", null, 7),
    EAT_SPIDER_EYE(null, "Доступна еда", FoodComponents.SPIDER_EYE, 8),
    SPRINT("Вы сейчас не можете бегать", "Доступно действие", null, 10),
    EAT_CHICKEN(null, "Доступна еда", FoodComponents.CHICKEN, 14),
    EAT_POISONOUS_POTATO(null, "Доступна еда", FoodComponents.POISONOUS_POTATO, 16),
    EAT_SUSPICIOUS_STEW(null, "Доступна еда", FoodComponents.SUSPICIOUS_STEW, 19),
    EAT_MUTTON(null, "Доступна еда", FoodComponents.MUTTON, 22),
    EAT_RABBIT(null, "Доступна еда", FoodComponents.RABBIT, 24),
    BREAK_BLOCKS_IN_POSITIVE_Y("Вы сейчас не можете ломать блоки", "Доступно действие", null, 25),
    EAT_SWEET_BERRIES(null, "Доступна еда", FoodComponents.SWEET_BERRIES, 27),
    EAT_PORKCHOP(null, "Доступна еда", FoodComponents.PORKCHOP, 29),
    EAT_BEETROOT(null, "Доступна еда", FoodComponents.BEETROOT, 37),
    USING_BOAT("Вы сейчас не можете садиться в лодку", "Доступно действие", null, 40),
    EAT_BEETROOT_SOUP(null, "Доступна еда", FoodComponents.BEETROOT_SOUP, 45),
    USING_CRAFTING_TABLE("Вы сейчас не можете использовать верстак", "Доступен блок", null, 50),
    EAT_CARROT(null, "Доступна еда", FoodComponents.CARROT, 52),
    EAT_APPLE(null, "Доступна еда", FoodComponents.APPLE, 55),
    EAT_POTATO(null, "Доступна еда", FoodComponents.POTATO, 58),
    USING_FURNACE("Вы сейчас не можете использовать печь", "Доступен блок", null, 60),
    EAT_DRIED_KELP(null, "Доступна еда", FoodComponents.DRIED_KELP, 62),
    EAT_MELON_SLICE(null, "Доступна еда", FoodComponents.MELON_SLICE, 64),
    USING_IRON_TOOLS("Вы сейчас не можете использовать железные инструменты", "Доступны инструменты", null, 70),
    EAT_COOKIE(null, "Доступна еда", FoodComponents.COOKIE, 72),
    EAT_HONEY_BOTTLE(null, "Доступна еда", FoodComponents.HONEY_BOTTLE, 77),
    USING_SHIELD("Вы сейчас не можете использовать щит", "Доступно действие", null, 80),
    EAT_BEEF(null, "Доступна еда", FoodComponents.BEEF, 83),
    USING_WATER_BUCKET("Вы сейчас не можете разливать воду", "Доступно действие", null, 90),
    EAT_BREAD(null, "Доступна еда", FoodComponents.BREAD, 100),
    EAT_RABBIT_STEW(null, "Доступна еда", FoodComponents.RABBIT_STEW, 105),
    EQUIP_IRON_ARMOR("Вы сейчас не можете одевать железную броню", "Доступна броня", null, 110),
    EAT_MUSHROOM_STEW(null, "Доступна еда", FoodComponents.MUSHROOM_STEW, 111),
    USING_SMOKER("Вы сейчас не можете использовать коптильню", "Доступен блок", null, 125),
    EAT_PUMPKIN_PIE(null, "Доступна еда", FoodComponents.PUMPKIN_PIE, 145),
    USING_BLAST_FURNACE("Вы сейчас не можете использовать плавильную печь", "Доступен блок", null, 150),
    USING_ANVIL("Вы сейчас не можете использовать наковальню", "Доступен блок", null, 175),
    EAT_COOKED_SALMON(null, "Доступна еда", FoodComponents.COOKED_SALMON, 190),
    EAT_COOKED_COD(null, "Доступна еда", FoodComponents.COOKED_COD, 195),
    BREAK_BLOCKS_IN_NEGATIVE_Y("Вы сейчас не можете ломать блоки ниже 0 высоты", "Доступно действие", null, 200),
    EAT_GLOW_BERRIES(null, "Доступна еда", FoodComponents.GLOW_BERRIES, 240),
    NETHER("Нижний Мир закрыт", "Доступен портал", null, 300),
    EAT_GOLDEN_APPLE(null, "Доступна еда", FoodComponents.GOLDEN_APPLE, 333),
    USING_DIAMOND_TOOLS("Вы сейчас не можете использовать алмазные инструменты", "Доступны инструменты", null, 350),
    EAT_BAKED_POTATO(null, "Доступна еда", FoodComponents.BAKED_POTATO, 370),
    EQUIP_DIAMOND_ARMOR("Вы сейчас не можете одевать алмазную броню", "Доступна броня", null, 400),
    EAT_COOKED_RABBIT(null, "Доступна еда", FoodComponents.COOKED_RABBIT, 420),
    USING_BREWING_STAND("Вы сейчас не можете использовать зельеварку", "Доступен блок", null, 450),
    EAT_PUFFERFISH(null, "Доступна еда", FoodComponents.PUFFERFISH, 455),
    EAT_COOKED_CHICKEN(null, "Доступна еда", FoodComponents.COOKED_CHICKEN, 500),
    EAT_COOKED_MUTTON(null, "Доступна еда", FoodComponents.COOKED_MUTTON, 525),
    USING_BEACON("Вы сейчас не можете использовать маяк", "Доступен блок", null, 600),
    EAT_COOKED_PORKCHOP(null, "Доступна еда", FoodComponents.COOKED_PORKCHOP, 635),
    USING_NETHERITE_TOOLS("Вы сейчас не можете использовать незеритовые инструменты", "Доступны инструменты", null, 650),
    EAT_COOKED_BEEF(null, "Доступна еда", FoodComponents.COOKED_BEEF, 675),
    EQUIP_NETHERITE_ARMOR("Вы сейчас не можете одевать незеритовую броню", "Доступна броня", null, 700),
    END("Край закрыт", "Доступен портал", null, 725),
    EAT_CHORUS_FRUIT(null, "Доступна еда", FoodComponents.CHORUS_FRUIT, 740),
    EQUIP_ELYTRA("Вы сейчас не можете одевать элитры", "Доступна броня", null, 775),
    EAT_ENCHANTED_GOLDEN_APPLE(null, "Доступна еда", FoodComponents.ENCHANTED_GOLDEN_APPLE, 777),
    END_GATE("Этот портал в данный момент недоступен", "Доступен портал", null, 800),
    USING_ENDER_CHEST("Вы сейчас не можете использовать эндер-сундук", "Доступен блок", null, 825),
    USING_SHULKER_BOX("Вы сейчас не можете использовать шалкеровый ящик", "Доступен блок", null, 850),
    EAT_GOLDEN_CARROT(null, "Доступна еда", FoodComponents.GOLDEN_CARROT, 888),
    USING_FIREWORKS_WHILE_FLY("Вы сейчас не можете использовать фейерверки для полёта", "Доступно действие", null, 900),
    TRADE_WITH_VILLAGER("Торговля с деревенскими жителями недоступна", "Доступно действие", null, 925),
    USING_ENCHANTING_TABLE("Вы сейчас не можете использовать стол зачарований", "Доступен блок", null, 950),
    DROP_TOTEM("Тотем бессмертия недоступен", "Доступно действие", null, 1000);

    private final String description;
    private final String popupTitle;
    private final FoodComponent foodComponent;
    private final int achievementsCountToUnlock;

    BlockedAction(String description, String popupTitle, FoodComponent foodComponent, int achievementsCountToUnlock) {
        this.description = description;
        this.popupTitle = popupTitle;
        this.foodComponent = foodComponent;
        this.achievementsCountToUnlock = achievementsCountToUnlock;
    }

    public String getDescription() {
        return description;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public FoodComponent getFoodComponent() {
        return foodComponent;
    }

    public int getAchievementsCountToUnlock() {
        return achievementsCountToUnlock;
    }
}
