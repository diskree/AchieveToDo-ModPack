package com.diskree.achievetodo;

import com.diskree.achievetodo.advancements.AdvancementRoot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;

public enum BlockedAction {
    EAT_SALMON("blocked.food", BlockedActionType.FOOD, FoodComponents.SALMON, null, 2),
    EAT_COD("blocked.food", BlockedActionType.FOOD, FoodComponents.COD, null, 3),
    EAT_TROPICAL_FISH("blocked.food", BlockedActionType.FOOD, FoodComponents.TROPICAL_FISH, null, 4),
    EAT_ROTTEN_FLESH("blocked.food", BlockedActionType.FOOD, FoodComponents.ROTTEN_FLESH, null, 6),
    JUMP("blocked.jump", BlockedActionType.ACTION, null, null, 7),
    EAT_SPIDER_EYE("blocked.food", BlockedActionType.FOOD, FoodComponents.SPIDER_EYE, null, 8),
    EAT_PUFFERFISH("blocked.food", BlockedActionType.FOOD, FoodComponents.PUFFERFISH, null, 12),
    EAT_CHICKEN("blocked.food", BlockedActionType.FOOD, FoodComponents.CHICKEN, null, 15),
    EAT_POISONOUS_POTATO("blocked.food", BlockedActionType.FOOD, FoodComponents.POISONOUS_POTATO, null, 17),
    EAT_SUSPICIOUS_STEW("blocked.food", BlockedActionType.FOOD, FoodComponents.SUSPICIOUS_STEW, null, 20),
    EAT_MUTTON("blocked.food", BlockedActionType.FOOD, FoodComponents.MUTTON, null, 22),
    EAT_RABBIT("blocked.food", BlockedActionType.FOOD, FoodComponents.RABBIT, null, 24),
    BREAK_BLOCKS_IN_POSITIVE_Y("blocked.break_blocks_pos", BlockedActionType.ACTION, null, null, 25),
    EAT_SWEET_BERRIES("blocked.food", BlockedActionType.FOOD, FoodComponents.SWEET_BERRIES, null, 27),
    EAT_GLOW_BERRIES("blocked.food", BlockedActionType.FOOD, FoodComponents.GLOW_BERRIES, null, 29),
    EAT_PORKCHOP("blocked.food", BlockedActionType.FOOD, FoodComponents.PORKCHOP, null, 33),
    EAT_BEETROOT("blocked.food", BlockedActionType.FOOD, FoodComponents.BEETROOT, null, 37),
    EAT_BEETROOT_SOUP("blocked.food", BlockedActionType.FOOD, FoodComponents.BEETROOT_SOUP, null, 45),
    USING_CRAFTING_TABLE("blocked.crafting_table", BlockedActionType.BLOCK, null, null, 50),
    EAT_CARROT("blocked.food", BlockedActionType.FOOD, FoodComponents.CARROT, null, 52),
    EAT_APPLE("blocked.food", BlockedActionType.FOOD, FoodComponents.APPLE, null, 55),
    EAT_POTATO("blocked.food", BlockedActionType.FOOD, FoodComponents.POTATO, null, 58),
    USING_FURNACE("blocked.furnace", BlockedActionType.BLOCK, null, null, 60),
    EAT_DRIED_KELP("blocked.food", BlockedActionType.FOOD, FoodComponents.DRIED_KELP, null, 62),
    EAT_MELON_SLICE("blocked.food", BlockedActionType.FOOD, FoodComponents.MELON_SLICE, null, 64),
    USING_STONE_TOOLS("blocked.stone_tools", BlockedActionType.TOOLS, null, null, 70),
    EAT_COOKIE("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKIE, null, 72),
    EAT_HONEY_BOTTLE("blocked.food", BlockedActionType.FOOD, FoodComponents.HONEY_BOTTLE, null, 77),
    USING_BOAT("blocked.boat", BlockedActionType.ACTION, null, null, 80),
    EAT_BEEF("blocked.food", BlockedActionType.FOOD, FoodComponents.BEEF, null, 88),
    USING_SHIELD("blocked.shield", BlockedActionType.ACTION, null, null, 90),
    USING_WATER_BUCKET("blocked.water_bucket", BlockedActionType.ACTION, null, null, 95),
    EAT_BREAD("blocked.food", BlockedActionType.FOOD, FoodComponents.BREAD, null, 100),
    EAT_RABBIT_STEW("blocked.food", BlockedActionType.FOOD, FoodComponents.RABBIT_STEW, null, 105),
    USING_IRON_TOOLS("blocked.iron_tools", BlockedActionType.TOOLS, null, null, 110),
    EAT_MUSHROOM_STEW("blocked.food", BlockedActionType.FOOD, FoodComponents.MUSHROOM_STEW, null, 111),
    EAT_BAKED_POTATO("blocked.food", BlockedActionType.FOOD, FoodComponents.BAKED_POTATO, null, 130),
    EAT_PUMPKIN_PIE("blocked.food", BlockedActionType.FOOD, FoodComponents.PUMPKIN_PIE, null, 145),
    EQUIP_IRON_ARMOR("blocked.equip_iron", BlockedActionType.ARMOR, null, null, 150),
    USING_ANVIL("blocked.anvil", BlockedActionType.BLOCK, null, null, 175),
    EAT_COOKED_SALMON("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_SALMON, null, 190),
    EAT_COOKED_COD("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_COD, null, 195),
    BREAK_BLOCKS_IN_NEGATIVE_Y("blocked.break_blocks_neg", BlockedActionType.ACTION, null, null, 200),
    VILLAGER_MASON("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.MASON, 210),
    EAT_COOKED_RABBIT("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_RABBIT, null, 220),
    VILLAGER_CARTOGRAPHER("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.CARTOGRAPHER, 230),
    USING_BOW("blocked.bow", BlockedActionType.ACTION, null, null, 240),
    USING_SMOKER("blocked.smoker", BlockedActionType.BLOCK, null, null, 250),
    EAT_GOLDEN_APPLE("blocked.food", BlockedActionType.FOOD, FoodComponents.GOLDEN_APPLE, null, 260),
    USING_BLAST_FURNACE("blocked.blast_furnace", BlockedActionType.BLOCK, null, null, 270),
    VILLAGER_LEATHERWORKER("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.LEATHERWORKER, 280),
    NETHER("blocked.nether", BlockedActionType.PORTAL, null, null, 300),
    VILLAGER_SHEPHERD("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.SHEPHERD, 315),
    VILLAGER_BUTCHER("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.BUTCHER, 330),
    USING_DIAMOND_TOOLS("blocked.diamond_tools", BlockedActionType.TOOLS, null, null, 350),
    EQUIP_DIAMOND_ARMOR("blocked.equip_diamond", BlockedActionType.ARMOR, null, null, 380),
    VILLAGER_FARMER("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.FARMER, 385),
    USING_ENDER_CHEST("blocked.ender_chest", BlockedActionType.BLOCK, null, null, 390),
    VILLAGER_CLERIC("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.CLERIC, 400),
    USING_BREWING_STAND("blocked.brewing_stand", BlockedActionType.BLOCK, null, null, 420),
    VILLAGER_FISHERMAN("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.FISHERMAN, 444),
    EAT_COOKED_CHICKEN("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_CHICKEN, null, 450),
    EAT_COOKED_MUTTON("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_MUTTON, null, 480),
    USING_BEACON("blocked.beacon", BlockedActionType.BLOCK, null, null, 500),
    EAT_COOKED_PORKCHOP("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_PORKCHOP, null, 510),
    VILLAGER_FLETCHER("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.FLETCHER, 520),
    USING_NETHERITE_TOOLS("blocked.netherite_tools", BlockedActionType.TOOLS, null, null, 530),
    VILLAGER_ARMORER("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.ARMORER, 540),
    EQUIP_NETHERITE_ARMOR("blocked.equip_netherite", BlockedActionType.ARMOR, null, null, 550),
    EAT_COOKED_BEEF("blocked.food", BlockedActionType.FOOD, FoodComponents.COOKED_BEEF, null, 580),
    VILLAGER_WEAPONSMITH("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.WEAPONSMITH, 590),
    END("blocked.end", BlockedActionType.PORTAL, null, null, 600),
    EQUIP_ELYTRA("blocked.elytra", BlockedActionType.ARMOR, null, null, 650),
    EAT_ENCHANTED_GOLDEN_APPLE("blocked.food", BlockedActionType.FOOD, FoodComponents.ENCHANTED_GOLDEN_APPLE, null, 680),
    EAT_CHORUS_FRUIT("blocked.food", BlockedActionType.FOOD, FoodComponents.CHORUS_FRUIT, null, 700),
    END_GATE("blocked.gateway", BlockedActionType.PORTAL, null, null, 800),
    USING_SHULKER_BOX("blocked.shulker_box", BlockedActionType.BLOCK, null, null, 825),
    USING_FIREWORKS_WHILE_FLY("blocked.firework", BlockedActionType.ACTION, null, null, 850),
    VILLAGER_TOOLSMITH("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.TOOLSMITH, 860),
    EAT_GOLDEN_CARROT("blocked.food", BlockedActionType.FOOD, FoodComponents.GOLDEN_CARROT, null, 888),
    VILLAGER_LIBRARIAN("blocked.trading", BlockedActionType.VILLAGER, null, VillagerProfession.LIBRARIAN, 900),
    USING_ENCHANTING_TABLE("blocked.enchanting_table", BlockedActionType.BLOCK, null, null, 950);

    final String description;
    final BlockedActionType actionType;
    final FoodComponent foodComponent;
    final VillagerProfession villagerProfession;
    final int unblockAdvancementsCount;

    BlockedAction(String description, BlockedActionType actionType, FoodComponent foodComponent, VillagerProfession villagerProfession, int unblockAdvancementsCount) {
        this.description = description;
        this.actionType = actionType;
        this.foodComponent = foodComponent;
        this.villagerProfession = villagerProfession;
        this.unblockAdvancementsCount = unblockAdvancementsCount;
    }

    public static BlockedAction map(String name) {
        for (BlockedAction action : values()) {
            if (action.name().equalsIgnoreCase(name)) {
                return action;
            }
        }
        return null;
    }

    public String getDescription() {
        return description;
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

    public int getUnblockAdvancementsCount() {
        return unblockAdvancementsCount;
    }

    public boolean isUnblocked(PlayerEntity player) {
        return AchieveToDo.getScore(player) >= unblockAdvancementsCount;
    }

    public Identifier buildAdvancementId() {
        return new Identifier(AchieveToDo.ID, AdvancementRoot.ACTION.name().toLowerCase() + "/" + name().toLowerCase());
    }

    public Text buildBlockedDescription(PlayerEntity player) {
        int leftAdvancementsCount = unblockAdvancementsCount - AchieveToDo.getScore(player);
        return Text.of(Text.translatable(description).getString() + Text.translatable("unblock.amount").getString() + leftAdvancementsCount)
                .copy()
                .formatted(Formatting.YELLOW);
    }
}
