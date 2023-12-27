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
    EAT_SALMON(
            2, FoodComponents.SALMON
    ),
    EAT_COD(
            3, FoodComponents.COD
    ),
    EAT_TROPICAL_FISH(
            4, FoodComponents.TROPICAL_FISH
    ),
    EAT_ROTTEN_FLESH(
            6, FoodComponents.ROTTEN_FLESH
    ),
    JUMP(
            7
    ),
    EAT_SPIDER_EYE(
            8, FoodComponents.SPIDER_EYE
    ),
    EAT_PUFFERFISH(
            12, FoodComponents.PUFFERFISH
    ),
    EAT_CHICKEN(
            15, FoodComponents.CHICKEN
    ),
    EAT_POISONOUS_POTATO(
            17, FoodComponents.POISONOUS_POTATO
    ),
    EAT_SUSPICIOUS_STEW(
            20, FoodComponents.SUSPICIOUS_STEW
    ),
    EAT_MUTTON(
            22, FoodComponents.MUTTON
    ),
    EAT_RABBIT(
            24, FoodComponents.RABBIT
    ),
    BREAK_BLOCKS_IN_POSITIVE_Y(
            25
    ),
    EAT_SWEET_BERRIES(
            27, FoodComponents.SWEET_BERRIES
    ),
    EAT_GLOW_BERRIES(
            29, FoodComponents.GLOW_BERRIES
    ),
    EAT_PORKCHOP(
            33, FoodComponents.PORKCHOP
    ),
    EAT_BEETROOT(
            37, FoodComponents.BEETROOT
    ),
    EAT_BEETROOT_SOUP(
            45, FoodComponents.BEETROOT_SOUP
    ),
    USING_CRAFTING_TABLE(
            50, Blocks.CRAFTING_TABLE
    ),
    EAT_CARROT(
            52, FoodComponents.CARROT
    ),
    EAT_APPLE(
            55, FoodComponents.APPLE
    ),
    EAT_POTATO(
            58, FoodComponents.POTATO
    ),
    USING_FURNACE(
            60, Blocks.FURNACE
    ),
    EAT_DRIED_KELP(
            62, FoodComponents.DRIED_KELP
    ),
    EAT_MELON_SLICE(
            64, FoodComponents.MELON_SLICE
    ),
    USING_STONE_TOOLS(
            70, ToolMaterials.STONE
    ),
    EAT_COOKIE(
            72, FoodComponents.COOKIE
    ),
    EAT_HONEY_BOTTLE(
            77, FoodComponents.HONEY_BOTTLE
    ),
    USING_BOAT(
            80
    ),
    EAT_BEEF(
            88, FoodComponents.BEEF
    ),
    USING_SHIELD(
            90
    ),
    USING_WATER_BUCKET(
            95
    ),
    EAT_BREAD(
            100, FoodComponents.BREAD
    ),
    EAT_RABBIT_STEW(
            105, FoodComponents.RABBIT_STEW
    ),
    USING_IRON_TOOLS(
            110, ToolMaterials.IRON
    ),
    EAT_MUSHROOM_STEW(
            111, FoodComponents.MUSHROOM_STEW
    ),
    EAT_BAKED_POTATO(
            130, FoodComponents.BAKED_POTATO
    ),
    EAT_PUMPKIN_PIE(
            145, FoodComponents.PUMPKIN_PIE
    ),
    EQUIP_IRON_ARMOR(
            150, ArmorMaterials.IRON
    ),
    USING_ANVIL(
            175, Blocks.ANVIL
    ),
    EAT_COOKED_SALMON(
            190, FoodComponents.COOKED_SALMON
    ),
    EAT_COOKED_COD(
            195, FoodComponents.COOKED_COD
    ),
    BREAK_BLOCKS_IN_NEGATIVE_Y(
            200
    ),
    VILLAGER_MASON(
            210, VillagerProfession.MASON
    ),
    EAT_COOKED_RABBIT(
            220, FoodComponents.COOKED_RABBIT
    ),
    VILLAGER_CARTOGRAPHER(
            230, VillagerProfession.CARTOGRAPHER
    ),
    USING_BOW(
            240
    ),
    USING_SMOKER(
            250, Blocks.SMOKER
    ),
    EAT_GOLDEN_APPLE(
            260, FoodComponents.GOLDEN_APPLE
    ),
    USING_BLAST_FURNACE(
            270, Blocks.BLAST_FURNACE
    ),
    VILLAGER_LEATHERWORKER(
            280, VillagerProfession.LEATHERWORKER
    ),
    NETHER(
            300, World.NETHER
    ),
    VILLAGER_SHEPHERD(
            315, VillagerProfession.SHEPHERD
    ),
    VILLAGER_BUTCHER(
            330, VillagerProfession.BUTCHER
    ),
    USING_DIAMOND_TOOLS(
            350, ToolMaterials.DIAMOND
    ),
    EQUIP_DIAMOND_ARMOR(
            380, ArmorMaterials.DIAMOND
    ),
    VILLAGER_FARMER(
            385, VillagerProfession.FARMER
    ),
    USING_ENDER_CHEST(
            390, Blocks.ENDER_CHEST
    ),
    VILLAGER_CLERIC(
            400, VillagerProfession.CLERIC
    ),
    USING_BREWING_STAND(
            420, Blocks.BREWING_STAND
    ),
    VILLAGER_FISHERMAN(
            444, VillagerProfession.FISHERMAN
    ),
    EAT_COOKED_CHICKEN(
            450, FoodComponents.COOKED_CHICKEN
    ),
    EAT_COOKED_MUTTON(
            480, FoodComponents.COOKED_MUTTON
    ),
    USING_BEACON(
            500, Blocks.BEACON
    ),
    EAT_COOKED_PORKCHOP(
            510, FoodComponents.COOKED_PORKCHOP
    ),
    VILLAGER_FLETCHER(
            520, VillagerProfession.FLETCHER
    ),
    USING_NETHERITE_TOOLS(
            530, ToolMaterials.NETHERITE
    ),
    VILLAGER_ARMORER(
            540, VillagerProfession.ARMORER
    ),
    EQUIP_NETHERITE_ARMOR(
            550, ArmorMaterials.NETHERITE
    ),
    EAT_COOKED_BEEF(
            580, FoodComponents.COOKED_BEEF
    ),
    VILLAGER_WEAPONSMITH(
            590, VillagerProfession.WEAPONSMITH
    ),
    END(
            600, World.END
    ),
    EQUIP_ELYTRA(
            650
    ),
    EAT_ENCHANTED_GOLDEN_APPLE(
            680, FoodComponents.ENCHANTED_GOLDEN_APPLE
    ),
    EAT_CHORUS_FRUIT(
            700, FoodComponents.CHORUS_FRUIT
    ),
    END_GATEWAY(
            725
    ),
    USING_FIREWORKS_WHILE_FLY(
            750
    ),
    USING_SHULKER_BOX(
            825
    ),
    VILLAGER_TOOLSMITH(
            860, VillagerProfession.TOOLSMITH
    ),
    EAT_GOLDEN_CARROT(
            888, FoodComponents.GOLDEN_CARROT
    ),
    VILLAGER_LIBRARIAN(
            900, VillagerProfession.LIBRARIAN
    ),
    USING_ENCHANTING_TABLE(
            950, Blocks.ENCHANTING_TABLE
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
