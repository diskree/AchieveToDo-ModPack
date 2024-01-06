package com.diskree.achievetodo.action;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.Utils;
import com.diskree.achievetodo.advancements.AdvancementsTab;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.block.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public enum BlockedActionType implements IGeneratedAdvancement {
    JUMP(
            8
    ),
    OPEN_DOOR(
            15
    ),
    SLEEP(
            20
    ),
    OPEN_INVENTORY(
            25
    ),
    BREAK_BLOCKS_IN_POSITIVE_Y(
            30
    ),
    USING_BOAT(
            82
    ),
    USING_SHIELD(
            93, Items.SHIELD
    ),
    USING_WATER_BUCKET(
            135, Items.WATER_BUCKET
    ),
    USING_SHEARS(
            144, Items.SHEARS
    ),
    USING_CROSSBOW(
            178, Items.CROSSBOW
    ),
    BREAK_BLOCKS_IN_NEGATIVE_Y(
            206
    ),
    USING_FISHING_ROD(
            225, Items.FISHING_ROD
    ),
    USING_BOW(
            243, Items.BOW
    ),
    USING_BRUSH(
            261, Items.BRUSH
    ),
    USING_SPYGLASS(
            282, Items.SPYGLASS
    ),
    THROW_TRIDENT(
            311, Items.TRIDENT
    ),
    THROW_ENDER_PEARL(
            334, Items.ENDER_PEARL
    ),
    EQUIP_ELYTRA(
            657
    ),
    END_GATEWAY(
            679
    ),
    FLY(
            721, Items.FIREWORK_ROCKET
    ),
    OPEN_SHULKER_BOX(
            754
    ),

    EAT_SALMON(
            3, FoodComponents.SALMON
    ),
    EAT_COD(
            4, FoodComponents.COD
    ),
    EAT_TROPICAL_FISH(
            5, FoodComponents.TROPICAL_FISH
    ),
    EAT_ROTTEN_FLESH(
            10, FoodComponents.ROTTEN_FLESH
    ),
    EAT_SPIDER_EYE(
            12, FoodComponents.SPIDER_EYE
    ),
    EAT_SWEET_BERRIES(
            14, FoodComponents.SWEET_BERRIES
    ),
    EAT_GLOW_BERRIES(
            16, FoodComponents.GLOW_BERRIES
    ),
    EAT_PUFFERFISH(
            18, FoodComponents.PUFFERFISH
    ),
    EAT_POISONOUS_POTATO(
            22, FoodComponents.POISONOUS_POTATO
    ),
    EAT_CHORUS_FRUIT(
            27, FoodComponents.CHORUS_FRUIT
    ),
    EAT_SUSPICIOUS_STEW(
            32, FoodComponents.SUSPICIOUS_STEW
    ),
    EAT_BEETROOT(
            38, FoodComponents.BEETROOT
    ),
    EAT_CARROT(
            43, FoodComponents.CARROT
    ),
    EAT_CHICKEN(
            47, FoodComponents.CHICKEN
    ),
    EAT_DRIED_KELP(
            51, FoodComponents.DRIED_KELP
    ),
    EAT_BEETROOT_SOUP(
            68, FoodComponents.BEETROOT_SOUP
    ),
    EAT_POTATO(
            71, FoodComponents.POTATO
    ),
    EAT_APPLE(
            83, FoodComponents.APPLE
    ),
    EAT_MELON_SLICE(
            95, FoodComponents.MELON_SLICE
    ),
    EAT_COOKIE(
            102, FoodComponents.COOKIE
    ),
    EAT_MUSHROOM_STEW(
            114, FoodComponents.MUSHROOM_STEW
    ),
    EAT_RABBIT_STEW(
            127, FoodComponents.RABBIT_STEW
    ),
    EAT_HONEY_BOTTLE(
            132, FoodComponents.HONEY_BOTTLE
    ),
    EAT_PUMPKIN_PIE(
            141, FoodComponents.PUMPKIN_PIE
    ),
    EAT_GOLDEN_APPLE(
            155, FoodComponents.GOLDEN_APPLE
    ),
    EAT_ENCHANTED_GOLDEN_APPLE(
            166, FoodComponents.ENCHANTED_GOLDEN_APPLE
    ),
    EAT_RABBIT(
            182, FoodComponents.RABBIT
    ),
    EAT_MUTTON(
            212, FoodComponents.MUTTON
    ),
    EAT_PORKCHOP(
            226, FoodComponents.PORKCHOP
    ),
    EAT_BEEF(
            249, FoodComponents.BEEF
    ),
    EAT_BAKED_POTATO(
            252, FoodComponents.BAKED_POTATO
    ),
    EAT_COOKED_SALMON(
            312, FoodComponents.COOKED_SALMON
    ),
    EAT_COOKED_COD(
            373, FoodComponents.COOKED_COD
    ),
    EAT_COOKED_RABBIT(
            432, FoodComponents.COOKED_RABBIT
    ),
    EAT_COOKED_CHICKEN(
            459, FoodComponents.COOKED_CHICKEN
    ),
    EAT_COOKED_MUTTON(
            524, FoodComponents.COOKED_MUTTON
    ),
    EAT_COOKED_PORKCHOP(
            550, FoodComponents.COOKED_PORKCHOP
    ),
    EAT_COOKED_BEEF(
            603, FoodComponents.COOKED_BEEF
    ),
    EAT_BREAD(
            654, FoodComponents.BREAD
    ),
    EAT_GOLDEN_CARROT(
            702, FoodComponents.GOLDEN_CARROT
    ),

    OPEN_CHEST(
            36, Blocks.CHEST
    ),
    USING_CRAFTING_TABLE(
            52, Blocks.CRAFTING_TABLE
    ),
    USING_STONECUTTER(
            78, Blocks.STONECUTTER
    ),
    OPEN_FURNACE(
            99, Blocks.FURNACE
    ),
    USING_ANVIL(
            103, Blocks.ANVIL
    ),
    USING_GRINDSTONE(
            174, Blocks.GRINDSTONE
    ),
    USING_LOOM(
            215, Blocks.LOOM
    ),
    OPEN_SMOKER(
            257, Blocks.SMOKER
    ),
    OPEN_BLAST_FURNACE(
            272, Blocks.BLAST_FURNACE
    ),
    USING_CARTOGRAPHY_TABLE(
            304, Blocks.CARTOGRAPHY_TABLE
    ),
    OPEN_ENDER_CHEST(
            352, Blocks.ENDER_CHEST
    ),
    OPEN_BREWING_STAND(
            409, Blocks.BREWING_STAND
    ),
    USING_SMITHING_TABLE(
            453, Blocks.SMITHING_TABLE
    ),
    USING_BEACON(
            505, Blocks.BEACON
    ),
    USING_ENCHANTING_TABLE(
            937, Blocks.ENCHANTING_TABLE
    ),

    USING_GOLDEN_TOOLS(
            41, ToolMaterials.GOLD
    ),
    USING_WOODEN_TOOLS(
            63, ToolMaterials.WOOD
    ),
    USING_STONE_TOOLS(
            97, ToolMaterials.STONE
    ),
    USING_IRON_TOOLS(
            122, ToolMaterials.IRON
    ),
    USING_DIAMOND_TOOLS(
            358, ToolMaterials.DIAMOND
    ),
    USING_NETHERITE_TOOLS(
            504, ToolMaterials.NETHERITE
    ),

    EQUIP_GOLDEN_ARMOR(
            44, ArmorMaterials.GOLD
    ),
    EQUIP_LEATHER_ARMOR(
            98, ArmorMaterials.LEATHER
    ),
    EQUIP_IRON_ARMOR(
            158, ArmorMaterials.IRON
    ),
    EQUIP_CHAINMAIL_ARMOR(
            203, ArmorMaterials.CHAIN
    ),
    EQUIP_DIAMOND_ARMOR(
            305, ArmorMaterials.DIAMOND
    ),
    EQUIP_NETHERITE_ARMOR(
            552, ArmorMaterials.NETHERITE
    ),

    NETHER(
            275, World.NETHER
    ),
    END(
            575, World.END
    ),

    VILLAGER_MASON(
            210, VillagerProfession.MASON
    ),
    VILLAGER_CARTOGRAPHER(
            230, VillagerProfession.CARTOGRAPHER
    ),
    VILLAGER_LEATHERWORKER(
            280, VillagerProfession.LEATHERWORKER
    ),
    VILLAGER_SHEPHERD(
            315, VillagerProfession.SHEPHERD
    ),
    VILLAGER_BUTCHER(
            330, VillagerProfession.BUTCHER
    ),
    VILLAGER_FARMER(
            385, VillagerProfession.FARMER
    ),
    VILLAGER_CLERIC(
            400, VillagerProfession.CLERIC
    ),
    VILLAGER_FISHERMAN(
            444, VillagerProfession.FISHERMAN
    ),
    VILLAGER_FLETCHER(
            520, VillagerProfession.FLETCHER
    ),
    VILLAGER_ARMORER(
            540, VillagerProfession.ARMORER
    ),
    VILLAGER_WEAPONSMITH(
            590, VillagerProfession.WEAPONSMITH
    ),
    VILLAGER_TOOLSMITH(
            860, VillagerProfession.TOOLSMITH
    ),
    VILLAGER_LIBRARIAN(
            900, VillagerProfession.LIBRARIAN
    );

    private final int unblockAdvancementsCount;

    private final FoodComponent food;
    private final Item item;
    private final Block block;
    private final ToolMaterials toolMaterial;
    private final ArmorMaterials equipmentMaterial;
    private final RegistryKey<World> dimension;
    private final VillagerProfession villager;

    BlockedActionType(int unblockAdvancementsCount) {
        this(unblockAdvancementsCount, null, null, null, null, null, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, FoodComponent food) {
        this(unblockAdvancementsCount, food, null, null, null, null, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, Item item) {
        this(unblockAdvancementsCount, null, item, null, null, null, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, Block block) {
        this(unblockAdvancementsCount, null, null, block, null, null, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, ToolMaterials materials) {
        this(unblockAdvancementsCount, null, null, null, materials, null, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, ArmorMaterials materials) {
        this(unblockAdvancementsCount, null, null, null, null, materials, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, RegistryKey<World> dimension) {
        this(unblockAdvancementsCount, null, null, null, null, null, dimension, null);
    }

    BlockedActionType(int unblockAdvancementsCount, VillagerProfession villager) {
        this(unblockAdvancementsCount, null, null, null, null, null, null, villager);
    }

    BlockedActionType(
            int unblockAdvancementsCount,
            FoodComponent food,
            Item item,
            Block block,
            ToolMaterials toolMaterial,
            ArmorMaterials equipmentMaterial,
            RegistryKey<World> dimension,
            VillagerProfession villager
    ) {
        this.unblockAdvancementsCount = unblockAdvancementsCount;
        this.food = food;
        this.item = item;
        this.block = block;
        this.toolMaterial = toolMaterial;
        this.equipmentMaterial = equipmentMaterial;
        this.dimension = dimension;
        this.villager = villager;
    }

    public BlockedActionCategory getCategory() {
        if (food != null) {
            return BlockedActionCategory.FOOD;
        }
        if (item != null && this != USING_WATER_BUCKET && this != FLY) {
            return BlockedActionCategory.ITEM;
        }
        if (block != null || this == OPEN_SHULKER_BOX) {
            return BlockedActionCategory.BLOCK;
        }
        if (toolMaterial != null) {
            return BlockedActionCategory.TOOL;
        }
        if (equipmentMaterial != null || this == EQUIP_ELYTRA) {
            return BlockedActionCategory.EQUIPMENT;
        }
        if (dimension != null) {
            return BlockedActionCategory.DIMENSION;
        }
        if (villager != null) {
            return BlockedActionCategory.VILLAGER;
        }
        return BlockedActionCategory.ACTION;
    }

    public Text getBlockedMessage() {
        if (food != null) {
            return Utils.translateOrNull("blocked.food");
        }
        if (villager != null) {
            return Utils.translateOrNull("blocked.villager");
        }
        return Utils.translateOrNull("blocked." + getName());
    }

    public Text buildBlockedDescription(PlayerEntity player) {
        int leftAdvancementsCount = unblockAdvancementsCount - AchieveToDo.getScore(player);
        return Text.of(getBlockedMessage().getString() + Text.translatable("unblock.amount").getString() + leftAdvancementsCount)
                .copy()
                .formatted(Formatting.YELLOW);
    }

    public int getUnblockAdvancementsCount() {
        return unblockAdvancementsCount;
    }

    public boolean isUnblocked(PlayerEntity player) {
        return AchieveToDo.getScore(player) >= unblockAdvancementsCount;
    }

    public Identifier buildAdvancementId() {
        return AdvancementsTab.BLOCKED_ACTIONS.getAdvancementId(this);
    }

    public static BlockedActionType map(String name) {
        if (name == null) {
            return null;
        }
        for (BlockedActionType blockedAction : values()) {
            if (blockedAction.name().equalsIgnoreCase(name)) {
                return blockedAction;
            }
        }
        return null;
    }

    public static BlockedActionType map(PlacedAdvancement advancement) {
        return map(advancement.getAdvancementEntry().id());
    }

    public static BlockedActionType map(Identifier advancementId) {
        String[] pathPieces = advancementId.getPath().split("/");
        return pathPieces.length == 2 ? BlockedActionType.map(pathPieces[1]) : null;
    }

    @Nullable
    public static BlockedActionType findBlockedFood(FoodComponent food) {
        for (BlockedActionType blockedAction : BlockedActionType.values()) {
            if (food == blockedAction.food) {
                return blockedAction;
            }
        }
        return null;
    }

    @Nullable
    public static BlockedActionType findBlockedItem(PlayerEntity player, ItemStack stack) {
        if (stack.isOf(Items.CROSSBOW)) {
            return CrossbowItem.isCharged(stack) ? BlockedActionType.USING_CROSSBOW : null;
        }
        if (stack.isOf(Items.FIREWORK_ROCKET)) {
            return player.isFallFlying() ? BlockedActionType.FLY : null;
        }
        if (stack.isOf(Items.TRIDENT)) {
            return EnchantmentHelper.getRiptide(stack) == 0 ? BlockedActionType.THROW_TRIDENT : null;
        }
        for (BlockedActionType blockedAction : BlockedActionType.values()) {
            Item item = stack.getItem();
            if (item == blockedAction.item) {
                return blockedAction;
            }
        }
        return null;
    }

    @Nullable
    public static BlockedActionType findBlockedBlock(BlockState blockState) {
        if (blockState == null) {
            return null;
        }
        if (blockState.getBlock() instanceof ShulkerBoxBlock) {
            return OPEN_SHULKER_BOX;
        }
        if (blockState.getBlock() instanceof BedBlock) {
            return SLEEP;
        }
        if (blockState.getBlock() instanceof DoorBlock doorBlock) {
            return !doorBlock.isOpen(blockState) ? OPEN_DOOR : null;
        }
        for (BlockedActionType blockedAction : BlockedActionType.values()) {
            if (blockState.isOf(blockedAction.block)) {
                return blockedAction;
            }
        }
        return null;
    }

    @Nullable
    public static BlockedActionType findBlockedTool(Item item) {
        if (item == null) {
            return null;
        }
        if (item == Items.SHEARS) {
            return BlockedActionType.USING_SHEARS;
        }
        if (item instanceof ToolItem toolItem) {
            for (BlockedActionType blockedAction : BlockedActionType.values()) {
                if (toolItem.getMaterial() == blockedAction.toolMaterial) {
                    return blockedAction;
                }
            }
        }
        return null;
    }

    @Nullable
    public static BlockedActionType findBlockedEquipment(Item item) {
        if (item == null) {
            return null;
        }
        if (item == Items.ELYTRA) {
            return EQUIP_ELYTRA;
        }
        if (item instanceof ArmorItem armorItem) {
            for (BlockedActionType blockedAction : BlockedActionType.values()) {
                if (armorItem.getMaterial() == blockedAction.equipmentMaterial) {
                    return blockedAction;
                }
            }
        }
        return null;
    }

    @Nullable
    public static BlockedActionType findBlockedDimension(RegistryKey<World> dimension) {
        if (dimension == null) {
            return null;
        }
        for (BlockedActionType blockedAction : BlockedActionType.values()) {
            if (dimension == blockedAction.dimension) {
                return blockedAction;
            }
        }
        return null;
    }

    @Nullable
    public static BlockedActionType findBlockedVillager(VillagerProfession profession) {
        if (profession == null) {
            return null;
        }
        for (BlockedActionType blockedAction : BlockedActionType.values()) {
            if (profession == blockedAction.villager) {
                return blockedAction;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }

    @Override
    @Nullable
    public ItemStack getIcon() {
        Item iconItem = null;
        if (food != null) {
            iconItem = Registries.ITEM.stream()
                    .filter(item -> item.getFoodComponent() == food)
                    .findFirst()
                    .orElse(null);
        } else if (item != null) {
            iconItem = item;
        } else if (block != null) {
            iconItem = block.asItem();
        } else if (toolMaterial != null) {
            iconItem = Registries.ITEM.stream()
                    .filter(item -> item instanceof PickaxeItem pickaxeItem && pickaxeItem.getMaterial() == toolMaterial)
                    .findFirst()
                    .orElse(null);
        } else if (equipmentMaterial != null) {
            iconItem = Registries.ITEM.stream()
                    .filter(item -> item instanceof ArmorItem chestPlateItem && chestPlateItem.getType() == ArmorItem.Type.CHESTPLATE && chestPlateItem.getMaterial() == equipmentMaterial)
                    .findFirst()
                    .orElse(null);
        } else if (dimension != null) {
            iconItem = dimension == World.NETHER ? Items.OBSIDIAN : Items.END_PORTAL_FRAME;
        } else if (villager != null) {
            PointOfInterestType poi = Registries.POINT_OF_INTEREST_TYPE.get(new Identifier(villager.id()));
            if (poi != null && poi.blockStates() != null) {
                List<BlockState> blockStates = new ArrayList<>(poi.blockStates());
                BlockState blockState = blockStates.get(0);
                if (blockState != null) {
                    iconItem = blockState.getBlock().asItem();
                }
            }
        } else {
            iconItem = switch (this) {
                case JUMP -> Items.SLIME_BLOCK;
                case OPEN_DOOR -> Items.DARK_OAK_DOOR;
                case SLEEP -> Items.RED_BED;
                case OPEN_INVENTORY -> Items.BUNDLE;
                case BREAK_BLOCKS_IN_POSITIVE_Y -> Items.COBBLESTONE;
                case USING_BOAT -> Items.OAK_BOAT;
                case BREAK_BLOCKS_IN_NEGATIVE_Y -> Items.COBBLED_DEEPSLATE;
                case EQUIP_ELYTRA -> Items.ELYTRA;
                case END_GATEWAY -> Items.END_STONE_BRICKS;
                case OPEN_SHULKER_BOX -> Items.SHULKER_BOX;
                default -> null;
            };
        }
        return iconItem != null ? new ItemStack(iconItem) : null;
    }

    @Nullable
    @Override
    public Text getTitle() {
        return Utils.translateOrNull("blocked." + getName() + ".title");
    }

    @Nullable
    @Override
    public Text getDescription() {
        return Utils.translateOrNull("blocked." + getName() + ".description");
    }
}
