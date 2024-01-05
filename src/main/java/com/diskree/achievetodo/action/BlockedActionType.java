package com.diskree.achievetodo.action;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.advancements.AdvancementsTab;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
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
    USING_DOOR(
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
            80
    ),
    USING_SHIELD(
            90
    ),
    USING_WATER_BUCKET(
            130
    ),
    USING_SHEARS(
            140
    ),
    USING_CROSSBOW(
            150
    ),
    BREAK_BLOCKS_IN_NEGATIVE_Y(
            200
    ),
    USING_FISHING_ROD(
            220
    ),
    USING_BOW(
            240
    ),
    USING_BRUSH(
            260
    ),
    USING_TRIDENT(
            300
    ),
    USING_SPYGLASS(
            280
    ),
    EQUIP_ELYTRA(
            650
    ),
    END_GATEWAY(
            675
    ),
    USING_FIREWORKS_WHILE_FLY(
            725
    ),
    USING_SHULKER_BOX(
            750
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
            20, FoodComponents.POISONOUS_POTATO
    ),
    EAT_CHORUS_FRUIT(
            25, FoodComponents.CHORUS_FRUIT
    ),
    EAT_SUSPICIOUS_STEW(
            30, FoodComponents.SUSPICIOUS_STEW
    ),
    EAT_BEETROOT(
            35, FoodComponents.BEETROOT
    ),
    EAT_CARROT(
            40, FoodComponents.CARROT
    ),
    EAT_CHICKEN(
            45, FoodComponents.CHICKEN
    ),
    EAT_DRIED_KELP(
            50, FoodComponents.DRIED_KELP
    ),
    EAT_BEETROOT_SOUP(
            60, FoodComponents.BEETROOT_SOUP
    ),
    EAT_POTATO(
            70, FoodComponents.POTATO
    ),
    EAT_APPLE(
            80, FoodComponents.APPLE
    ),
    EAT_MELON_SLICE(
            90, FoodComponents.MELON_SLICE
    ),
    EAT_COOKIE(
            100, FoodComponents.COOKIE
    ),
    EAT_MUSHROOM_STEW(
            110, FoodComponents.MUSHROOM_STEW
    ),
    EAT_RABBIT_STEW(
            120, FoodComponents.RABBIT_STEW
    ),
    EAT_HONEY_BOTTLE(
            130, FoodComponents.HONEY_BOTTLE
    ),
    EAT_PUMPKIN_PIE(
            140, FoodComponents.PUMPKIN_PIE
    ),
    EAT_GOLDEN_APPLE(
            150, FoodComponents.GOLDEN_APPLE
    ),
    EAT_ENCHANTED_GOLDEN_APPLE(
            160, FoodComponents.ENCHANTED_GOLDEN_APPLE
    ),
    EAT_BAKED_POTATO(
            180, FoodComponents.BAKED_POTATO
    ),
    EAT_RABBIT(
            200, FoodComponents.RABBIT
    ),
    EAT_MUTTON(
            220, FoodComponents.MUTTON
    ),
    EAT_PORKCHOP(
            240, FoodComponents.PORKCHOP
    ),
    EAT_BEEF(
            250, FoodComponents.BEEF
    ),
    EAT_COOKED_SALMON(
            300, FoodComponents.COOKED_SALMON
    ),
    EAT_COOKED_COD(
            400, FoodComponents.COOKED_COD
    ),
    EAT_COOKED_RABBIT(
            450, FoodComponents.COOKED_RABBIT
    ),
    EAT_COOKED_CHICKEN(
            500, FoodComponents.COOKED_CHICKEN
    ),
    EAT_COOKED_MUTTON(
            550, FoodComponents.COOKED_MUTTON
    ),
    EAT_COOKED_PORKCHOP(
            600, FoodComponents.COOKED_PORKCHOP
    ),
    EAT_COOKED_BEEF(
            650, FoodComponents.COOKED_BEEF
    ),
    EAT_BREAD(
            700, FoodComponents.BREAD
    ),
    EAT_GOLDEN_CARROT(
            800, FoodComponents.GOLDEN_CARROT
    ),

    USING_CHEST(
            30, Blocks.CHEST
    ),
    USING_CRAFTING_TABLE(
            50, Blocks.CRAFTING_TABLE
    ),
    USING_STONECUTTER(
            75, Blocks.STONECUTTER
    ),
    USING_FURNACE(
            100, Blocks.FURNACE
    ),
    USING_ANVIL(
            150, Blocks.ANVIL
    ),
    USING_GRINDSTONE(
            175, Blocks.GRINDSTONE
    ),
    USING_LOOM(
            200, Blocks.LOOM
    ),
    USING_SMOKER(
            250, Blocks.SMOKER
    ),
    USING_BLAST_FURNACE(
            270, Blocks.BLAST_FURNACE
    ),
    USING_CARTOGRAPHY_TABLE(
            300, Blocks.CARTOGRAPHY_TABLE
    ),
    USING_ENDER_CHEST(
            350, Blocks.ENDER_CHEST
    ),
    USING_BREWING_STAND(
            400, Blocks.BREWING_STAND
    ),
    USING_SMITHING_TABLE(
            450, Blocks.SMITHING_TABLE
    ),
    USING_BEACON(
            500, Blocks.BEACON
    ),
    USING_ENCHANTING_TABLE(
            950, Blocks.ENCHANTING_TABLE
    ),

    USING_GOLD_TOOLS(
            40, ToolMaterials.GOLD
    ),
    USING_WOOD_TOOLS(
            70, ToolMaterials.WOOD
    ),
    USING_STONE_TOOLS(
            90, ToolMaterials.STONE
    ),
    USING_IRON_TOOLS(
            120, ToolMaterials.IRON
    ),
    USING_DIAMOND_TOOLS(
            350, ToolMaterials.DIAMOND
    ),
    USING_NETHERITE_TOOLS(
            500, ToolMaterials.NETHERITE
    ),

    EQUIP_GOLD_ARMOR(
            40, ArmorMaterials.GOLD
    ),
    EQUIP_LEATHER_ARMOR(
            90, ArmorMaterials.LEATHER
    ),
    EQUIP_IRON_ARMOR(
            150, ArmorMaterials.IRON
    ),
    EQUIP_CHAIN_ARMOR(
            200, ArmorMaterials.CHAIN
    ),
    EQUIP_TURTLE_ARMOR(
            300, ArmorMaterials.TURTLE
    ),
    EQUIP_DIAMOND_ARMOR(
            400, ArmorMaterials.DIAMOND
    ),
    EQUIP_NETHERITE_ARMOR(
            550, ArmorMaterials.NETHERITE
    ),

    NETHER(
            300, World.NETHER
    ),
    END(
            600, World.END
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
    private final Block block;
    private final ToolMaterials toolMaterial;
    private final ArmorMaterials equipmentMaterial;
    private final RegistryKey<World> dimension;
    private final VillagerProfession villager;

    BlockedActionType(int unblockAdvancementsCount) {
        this(unblockAdvancementsCount, null, null, null, null, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, FoodComponent food) {
        this(unblockAdvancementsCount, food, null, null, null, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, Block block) {
        this(unblockAdvancementsCount, null, block, null, null, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, ToolMaterials materials) {
        this(unblockAdvancementsCount, null, null, materials, null, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, ArmorMaterials materials) {
        this(unblockAdvancementsCount, null, null, null, materials, null, null);
    }

    BlockedActionType(int unblockAdvancementsCount, RegistryKey<World> dimension) {
        this(unblockAdvancementsCount, null, null, null, null, dimension, null);
    }

    BlockedActionType(int unblockAdvancementsCount, VillagerProfession villager) {
        this(unblockAdvancementsCount, null, null, null, null, null, villager);
    }

    BlockedActionType(
            int unblockAdvancementsCount,
            FoodComponent food,
            Block block,
            ToolMaterials toolMaterial,
            ArmorMaterials equipmentMaterial,
            RegistryKey<World> dimension,
            VillagerProfession villager
    ) {
        this.unblockAdvancementsCount = unblockAdvancementsCount;
        this.food = food;
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
        if (block != null || this == USING_SHULKER_BOX) {
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
            return Text.translatable("blocked.food");
        }
        if (villager != null) {
            return Text.translatable("blocked.villager");
        }
        return Text.translatable("blocked." + getName());
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
        for (BlockedActionType action : values()) {
            if (action.name().equalsIgnoreCase(name)) {
                return action;
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
    public static BlockedActionType findBlockedBlock(Block block) {
        if (block instanceof ShulkerBoxBlock) {
            return USING_SHULKER_BOX;
        }
        for (BlockedActionType blockedAction : BlockedActionType.values()) {
            if (block == blockedAction.block) {
                return blockedAction;
            }
        }
        return null;
    }

    @Nullable
    public static BlockedActionType findBlockedTool(Item item) {
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
        for (BlockedActionType blockedAction : BlockedActionType.values()) {
            if (dimension == blockedAction.dimension) {
                return blockedAction;
            }
        }
        return null;
    }

    @Nullable
    public static BlockedActionType findBlockedVillager(VillagerProfession profession) {
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
                case BREAK_BLOCKS_IN_POSITIVE_Y -> Items.WOODEN_PICKAXE;
                case USING_BOAT -> Items.OAK_BOAT;
                case USING_SHIELD -> Items.SHIELD;
                case USING_WATER_BUCKET -> Items.WATER_BUCKET;
                case BREAK_BLOCKS_IN_NEGATIVE_Y -> Items.STONE_PICKAXE;
                case USING_BOW -> Items.BOW;
                case USING_FIREWORKS_WHILE_FLY -> Items.FIREWORK_ROCKET;
                case USING_SHULKER_BOX -> Items.SHULKER_BOX;
                case NETHER -> Items.OBSIDIAN;
                case END -> Items.END_PORTAL_FRAME;
                case EQUIP_ELYTRA -> Items.ELYTRA;
                case END_GATEWAY -> Items.END_STONE_BRICKS;
                default -> null;
            };
        }
        return iconItem != null ? new ItemStack(iconItem) : null;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("blocked." + getName() + ".title");
    }

    @Override
    public Text getDescription() {
        return Text.translatable("blocked." + getName() + ".description");
    }
}
