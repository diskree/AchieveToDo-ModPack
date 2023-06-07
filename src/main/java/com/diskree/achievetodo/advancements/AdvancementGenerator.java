package com.diskree.achievetodo.advancements;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementDisplay;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class AdvancementGenerator {

    @Nullable
    public static Advancement generateForCommand() {
        return generateRandomAdvancement(true);
    }

    @Nullable
    public static AdvancementHint generateForHint() {
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return null;
        }
        ServerPlayerEntity serverPlayer = server.getPlayerManager().getPlayerList().get(0);
        if (serverPlayer == null) {
            return null;
        }
        Advancement advancement = generateRandomAdvancement(false);
        if (advancement == null) {
            return null;
        }
        ArrayList<String> incompleteCriteria = new ArrayList<>();
        AdvancementProgress progress = serverPlayer.getAdvancementTracker().getProgress(advancement);
        for (String[] requirement : advancement.getRequirements()) {
            boolean isRequirementCompleted = false;
            for (String criterion : requirement) {
                CriterionProgress criterionProgress = progress.getCriterionProgress(criterion);
                if (criterionProgress != null && criterionProgress.isObtained()) {
                    isRequirementCompleted = true;
                    break;
                }
            }
            if (!isRequirementCompleted && requirement.length > 0) {
                incompleteCriteria.add(requirement[0]);
            }
        }
        if (incompleteCriteria.isEmpty()) {
            return null;
        }
        String criterion = incompleteCriteria.get(serverPlayer.getRandom().nextBetween(0, incompleteCriteria.size() - 1));
        Item hintItem = Registries.ITEM.get(new Identifier(criterion));
        NbtCompound nbt = new NbtCompound();
        if (hintItem == Items.AIR) {
            switch (advancement.getId().toString()) {
                case "blazeandcave:mining/bonfire_night" -> {
                    switch (criterion) {
                        case "sweet_berry_bush" -> hintItem = Items.SWEET_BERRIES; // block variant?
                    }
                }
                case "blazeandcave:animal/tropical_collection" -> {
                    hintItem = Items.TROPICAL_FISH_BUCKET;
                    nbt.putInt("BucketVariantTag", switch (criterion) {
                        case "fish1" -> 65536;
                        case "fish2" -> 917504;
                        case "fish3" -> 918273;
                        case "fish4" -> 918529;
                        case "fish5" -> 16778497;
                        case "fish6" -> 50660352;
                        case "fish7" -> 50726144;
                        case "fish8" -> 67108865;
                        case "fish9" -> 67110144;
                        case "fish10" -> 67371009;
                        case "fish11" -> 67764993;
                        case "fish12" -> 101253888;
                        case "fish13" -> 117441025;
                        case "fish14" -> 67699456;
                        case "fish15" -> 117441793;
                        case "fish16" -> 117506305;
                        case "fish17" -> 117899265;
                        case "fish18" -> 118161664;
                        case "fish19" -> 459008;
                        case "fish20" -> 185008129;
                        case "fish21" -> 234882305;
                        case "fish22" -> 235340288;
                        default -> throw new IllegalStateException("Unexpected value: " + criterion);
                    });
                }
                case "blazeandcave:animal/axolotl_of_them" -> {
                    hintItem = Items.AXOLOTL_BUCKET;
                    nbt.putInt("Variant", switch (criterion) {
                        case "leucistic" -> 0;
                        case "wild" -> 1;
                        case "gold" -> 2;
                        case "cyan" -> 3;
                        case "blue" -> 4;
                        default -> throw new IllegalStateException("Unexpected value: " + criterion);
                    });
                }
                case "minecraft:husbandry/complete_catalogue" -> {
                    hintItem = AchieveToDoMod.ANCIENT_CITY_PORTAL_HINT_ITEM;
                    nbt.putInt("Damage", switch (criterion) {
                        case "minecraft:black" -> 1;
                        case "minecraft:tabby" -> 2;
                        case "minecraft:ragdoll" -> 3;
                        case "minecraft:british_shorthair" -> 4;
                        case "minecraft:white" -> 5;
                        case "minecraft:persian" -> 6;
                        case "minecraft:calico" -> 7;
                        case "minecraft:siamese" -> 8;
                        case "minecraft:all_black" -> 9;
                        case "minecraft:jellie" -> 10;
                        case "minecraft:red" -> 11;
                        default -> throw new IllegalStateException("Unexpected value: " + criterion);
                    });
                }
            }
        }
        if (hintItem == Items.AIR) {
            hintItem = Items.BARRIER;
        }
        ItemStack itemStack = new ItemStack(hintItem);
        itemStack.setNbt(nbt);
        return new AdvancementHint(advancement, itemStack);
    }

    private static Advancement generateRandomAdvancement(boolean withSingleRequirement) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) {
            return null;
        }
        IntegratedServer server = MinecraftClient.getInstance().getServer();
        if (server == null) {
            return null;
        }
        ServerPlayerEntity serverPlayer = server.getPlayerManager().getPlayerList().get(0);
        if (serverPlayer == null) {
            return null;
        }
        ArrayList<Advancement> advancements = new ArrayList<>();
        for (Advancement advancement : player.networkHandler.getAdvancementHandler().getManager().getAdvancements()) {
            Identifier identifier = advancement.getId();
            String namespace = identifier.getNamespace();
            String tab = identifier.getPath().split("/")[0];
            String name = identifier.getPath().split("/")[1];
            AdvancementDisplay display = advancement.getDisplay();
            int requirementsCount = advancement.getRequirementCount();
            if (display == null || display.isHidden()) {
                continue;
            }
            if (namespace.equals(AchieveToDoMod.ID)) {
                continue;
            }
            if (name.equals("root")) {
                continue;
            }
            if (tab.equals(AdvancementRoot.STATISTICS.name().toLowerCase())) {
                continue;
            }
            if (withSingleRequirement) {
                if (requirementsCount > 1) {
                    continue;
                }
            }
            if (!withSingleRequirement) {
                if (requirementsCount == 1) {
                    continue;
                }
                if (tab.equals(AdvancementRoot.BACAP.name().toLowerCase())) {
                    continue;
                }
                String id = identifier.toString();
                if (id.equals("blazeandcave:nether/this_ones_mine")

                ) {
                    continue;
                }
            }
            AdvancementProgress progress = serverPlayer.getAdvancementTracker().getProgress(advancement);
            if (progress != null && progress.isDone()) {
                continue;
            }
            advancements.add(advancement);
        }
        if (advancements.isEmpty()) {
            return null;
        }
        return advancements.get(player.getRandom().nextBetween(0, advancements.size() - 1));
    }
}
