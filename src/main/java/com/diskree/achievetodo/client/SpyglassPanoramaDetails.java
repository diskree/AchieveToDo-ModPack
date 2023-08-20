package com.diskree.achievetodo.client;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;

import java.io.File;

public record SpyglassPanoramaDetails(String type, String name) {

    public String generateURL(int face) {
        return "https://raw.githubusercontent.com/diskree/AchieveToDo-core/master/panorama/" + type + "/" + name + "/panorama_" + face + ".png";
    }

    public File generateCacheFile(File panoramaDir, int face) {
        return new File(panoramaDir, type + "/" + name + "/panorama_" + face + ".png");
    }

    public static SpyglassPanoramaDetails from(String panoramaPath) {
        String[] splitPath = panoramaPath.split("/");
        if (splitPath.length < 4) {
            return null;
        }
        String panoramaType = splitPath[2];
        String panoramaName = splitPath[3];
        return new SpyglassPanoramaDetails(panoramaType, panoramaName);
    }

    public static String getSpyglassPanoramaPath(ItemStack stack) {
        if (stack.isOf(Items.SPYGLASS)) {
            NbtCompound nbt = stack.getNbt();
            if (nbt != null) {
                return nbt.getString("Panorama");
            }
        }
        return null;
    }
}
