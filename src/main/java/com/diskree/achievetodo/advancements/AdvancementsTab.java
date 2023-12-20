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
                            EAT_PUFFERFISH,
                            EAT_CHICKEN,
                            EAT_POISONOUS_POTATO,
                            EAT_SUSPICIOUS_STEW,
                            EAT_MUTTON
                    ),
                    List.of(
                            EAT_RABBIT,
                            EAT_SWEET_BERRIES,
                            EAT_GLOW_BERRIES,
                            EAT_PORKCHOP,
                            EAT_BEETROOT,
                            EAT_BEETROOT_SOUP,
                            EAT_CARROT,
                            EAT_APPLE,
                            EAT_POTATO,
                            EAT_DRIED_KELP
                    ),
                    List.of(
                            EAT_MELON_SLICE,
                            EAT_COOKIE,
                            EAT_HONEY_BOTTLE,
                            EAT_BEEF,
                            EAT_BREAD,
                            EAT_RABBIT_STEW,
                            EAT_MUSHROOM_STEW,
                            EAT_BAKED_POTATO,
                            EAT_PUMPKIN_PIE,
                            EAT_COOKED_SALMON
                    ),
                    List.of(
                            EAT_COOKED_COD,
                            EAT_COOKED_RABBIT,
                            EAT_GOLDEN_APPLE,
                            EAT_COOKED_CHICKEN,
                            EAT_COOKED_MUTTON,
                            EAT_COOKED_PORKCHOP,
                            EAT_COOKED_BEEF,
                            EAT_ENCHANTED_GOLDEN_APPLE,
                            EAT_CHORUS_FRUIT,
                            EAT_GOLDEN_CARROT
                    ),
                    List.of(
                            JUMP,
                            BREAK_BLOCKS_IN_POSITIVE_Y,
                            USING_BOAT,
                            USING_SHIELD,
                            USING_WATER_BUCKET,
                            BREAK_BLOCKS_IN_NEGATIVE_Y,
                            USING_BOW,
                            USING_FIREWORKS_WHILE_FLY
                    ),
                    List.of(
                            USING_CRAFTING_TABLE,
                            USING_FURNACE,
                            USING_ANVIL,
                            USING_SMOKER,
                            USING_BLAST_FURNACE,
                            USING_ENDER_CHEST,
                            USING_BREWING_STAND,
                            USING_BEACON,
                            USING_SHULKER_BOX,
                            USING_ENCHANTING_TABLE
                    ),
                    List.of(
                            USING_STONE_TOOLS,
                            USING_IRON_TOOLS,
                            EQUIP_IRON_ARMOR,
                            BlockedActionType.NETHER,
                            USING_DIAMOND_TOOLS,
                            EQUIP_DIAMOND_ARMOR,
                            USING_NETHERITE_TOOLS,
                            EQUIP_NETHERITE_ARMOR,
                            BlockedActionType.END,
                            EQUIP_ELYTRA,
                            END_GATEWAY
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
                    )
            )
    ),
    STATISTICS,
    BACAP,
    ADVANCEMENTS_SEARCH(
            Items.AIR,
            Blocks.BLACK_CONCRETE,
            List.of()
    ),
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
        isModded = false;
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
    public Identifier getRootAdvancementId() {
        return new Identifier(getRootAdvancementPath());
    }

    @NotNull
    public Identifier getAdvancementId(@NotNull IGeneratedAdvancement advancement) {
        return new Identifier(getAdvancementPath(advancement));
    }
}
