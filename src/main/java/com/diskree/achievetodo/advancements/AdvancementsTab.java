package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.action.BlockedActionType;
import com.diskree.achievetodo.action.IGeneratedAdvancement;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.diskree.achievetodo.action.BlockedActionType.*;

public enum AdvancementsTab {
    BIOMES,
    ADVENTURE,
    WEAPONRY,
    HUSBANDRY,
    MONSTERS,
    MINING,
    BUILDING,
    FARMING,
    NETHER,
    END,
    BLOCKED_ACTIONS(
            Items.BOOK,
            Blocks.CHERRY_TRAPDOOR,
            List.of(
                    List.of(
                            EAT_SALMON,
                            EAT_COD,
                            EAT_TROPICAL_FISH,
                            EAT_ROTTEN_FLESH,
                            EAT_SPIDER_EYE,
                            EAT_SWEET_BERRIES,
                            EAT_GLOW_BERRIES,
                            EAT_PUFFERFISH,
                            EAT_POISONOUS_POTATO,
                            EAT_CHORUS_FRUIT
                    ),
                    List.of(
                            EAT_SUSPICIOUS_STEW,
                            EAT_BEETROOT,
                            EAT_CARROT,
                            EAT_CHICKEN,
                            EAT_DRIED_KELP,
                            EAT_BEETROOT_SOUP,
                            EAT_POTATO,
                            EAT_APPLE,
                            EAT_MELON_SLICE,
                            EAT_COOKIE
                    ),
                    List.of(
                            OPEN_CHEST,
                            USING_CRAFTING_TABLE,
                            USING_STONECUTTER,
                            OPEN_FURNACE,
                            USING_ANVIL,
                            USING_GRINDSTONE,
                            USING_LOOM,
                            OPEN_SMOKER
                    ),
                    List.of(
                            USING_GOLDEN_TOOLS,
                            EQUIP_GOLDEN_ARMOR,
                            USING_WOODEN_TOOLS,
                            USING_STONE_TOOLS,
                            EQUIP_LEATHER_ARMOR,
                            USING_IRON_TOOLS,
                            EQUIP_IRON_ARMOR,
                            EQUIP_CHAINMAIL_ARMOR
                    ),
                    List.of(
                            JUMP,
                            OPEN_DOOR,
                            SLEEP,
                            OPEN_INVENTORY,
                            BREAK_BLOCKS_IN_POSITIVE_Y,
                            USING_BOAT,
                            USING_SHIELD,
                            USING_WATER_BUCKET,
                            USING_SHEARS
                    ),
                    List.of(
                            VILLAGER_MASON,
                            VILLAGER_CARTOGRAPHER,
                            VILLAGER_LEATHERWORKER,
                            VILLAGER_SHEPHERD,
                            VILLAGER_BUTCHER,
                            VILLAGER_FARMER,
                            VILLAGER_CLERIC,
                            VILLAGER_FISHERMAN,
                            VILLAGER_FLETCHER,
                            VILLAGER_ARMORER,
                            VILLAGER_WEAPONSMITH,
                            VILLAGER_TOOLSMITH,
                            VILLAGER_LIBRARIAN
                    ),
                    List.of(
                            USING_CROSSBOW,
                            BREAK_BLOCKS_IN_NEGATIVE_Y,
                            USING_FISHING_ROD,
                            USING_BOW,
                            USING_BRUSH,
                            USING_SPYGLASS,
                            THROW_TRIDENT,
                            THROW_ENDER_PEARL,
                            FLY
                    ),
                    List.of(
                            BlockedActionType.NETHER,
                            EQUIP_DIAMOND_ARMOR,
                            USING_DIAMOND_TOOLS,
                            USING_NETHERITE_TOOLS,
                            EQUIP_NETHERITE_ARMOR,
                            BlockedActionType.END,
                            EQUIP_ELYTRA,
                            END_GATEWAY
                    ),
                    List.of(
                            OPEN_BLAST_FURNACE,
                            USING_CARTOGRAPHY_TABLE,
                            OPEN_ENDER_CHEST,
                            OPEN_BREWING_STAND,
                            USING_SMITHING_TABLE,
                            USING_BEACON,
                            OPEN_SHULKER_BOX,
                            USING_ENCHANTING_TABLE
                    ),
                    List.of(
                            EAT_MUSHROOM_STEW,
                            EAT_RABBIT_STEW,
                            EAT_HONEY_BOTTLE,
                            EAT_PUMPKIN_PIE,
                            EAT_GOLDEN_APPLE,
                            EAT_ENCHANTED_GOLDEN_APPLE,
                            EAT_RABBIT,
                            EAT_MUTTON,
                            EAT_PORKCHOP,
                            EAT_BEEF
                    ),
                    List.of(
                            EAT_BAKED_POTATO,
                            EAT_COOKED_SALMON,
                            EAT_COOKED_COD,
                            EAT_COOKED_RABBIT,
                            EAT_COOKED_CHICKEN,
                            EAT_COOKED_MUTTON,
                            EAT_COOKED_PORKCHOP,
                            EAT_COOKED_BEEF,
                            EAT_BREAD,
                            EAT_GOLDEN_CARROT
                    )
            )
    ),
    HINTS(true),
    STATISTICS,
    BACAP,
    REDSTONE,
    POTION,
    ENCHANTING,
    CHALLENGES;

    private static final String ROOT = "root";

    public final boolean isModded;
    final Item icon;
    final Block background;
    public final List<List<IGeneratedAdvancement>> children;

    AdvancementsTab() {
        this(false);
    }

    AdvancementsTab(boolean isModded) {
        this.isModded = isModded;
        icon = null;
        background = null;
        children = null;
    }

    AdvancementsTab(Item icon, Block background, @NotNull List<List<IGeneratedAdvancement>> children) {
        isModded = true;
        this.icon = icon;
        this.background = background;
        this.children = children;
    }

    public String getBasePath() {
        String name = name().toLowerCase();
        if (isModded) {
            name = AchieveToDo.ID + "_" + name;
        }
        return name;
    }

    @NotNull
    public String getRootAdvancementPath() {
        return getBasePath() + "/" + ROOT;
    }

    @NotNull
    public String getAdvancementPath(@NotNull IGeneratedAdvancement advancement) {
        return getBasePath() + "/" + advancement.getName();
    }

    @NotNull
    public Identifier getBackgroundTextureId() {
        return new Identifier("textures/block/" + Registries.BLOCK.getId(background).getPath() + ".png");
    }

    @Nullable
    public ItemStack getIcon() {
        return icon != null ? new ItemStack(icon) : null;
    }

    @NotNull
    public Text getTitle() {
        return Text.translatable("advancement.root." + name().toLowerCase() + ".title");
    }

    @NotNull
    public Text getDescription() {
        return Text.translatable("advancement.root." + name().toLowerCase() + ".description");
    }

    @NotNull
    public Identifier getAdvancementId(@NotNull IGeneratedAdvancement advancement) {
        return new Identifier(getAdvancementPath(advancement));
    }
}
