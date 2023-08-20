package com.diskree.achievetodo.client;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.io.File;

public record SpyglassPanoramaDetails(String type, String name) {

    public static final String PANORAMA_TYPE = "PanoramaType";
    public static final String PANORAMA_NAME = "PanoramaName";

    public String generateURL(int face) {
        return "https://raw.githubusercontent.com/diskree/AchieveToDo-core/master/panorama/" + type + "/" + name + "/panorama_" + face + ".png";
    }

    public Identifier generatePanoramaTextureId() {
        return new Identifier(AchieveToDo.ID, "textures/panorama/" + type + "/" + name + "/panorama");
    }

    public Identifier generateFaceTextureId(int face) {
        return new Identifier(AchieveToDo.ID, generatePanoramaTextureId().getPath() + "_" + face + ".png");
    }

    public File generateCacheFile(File cacheDir, int face) {
        return new File(cacheDir, type + "/" + name + "/panorama_" + face + ".png");
    }

    public static SpyglassPanoramaDetails from(ItemStack stack) {
        if (stack == null || !stack.isOf(Items.SPYGLASS)) {
            return null;
        }
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) {
            return null;
        }
        String type = nbt.getString(PANORAMA_TYPE);
        String name = nbt.getString(PANORAMA_NAME);
        return new SpyglassPanoramaDetails(type, name);
    }
}
