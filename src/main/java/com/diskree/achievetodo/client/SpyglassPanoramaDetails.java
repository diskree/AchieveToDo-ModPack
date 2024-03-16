package com.diskree.achievetodo.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.BuildConfig;
import com.diskree.achievetodo.advancements.hints.SpyglassPanoramaTexture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public record SpyglassPanoramaDetails(String type, String name) {

    public static final String PANORAMA_TYPE = "PanoramaType";
    public static final String PANORAMA_NAME = "PanoramaName";

    private static boolean isPanoramaLoading;

    public static SpyglassPanoramaDetails of(ItemStack stack) {
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

    public Identifier generatePanoramaTextureId() {
        return new Identifier(AchieveToDo.ID, "textures/panorama/" + type + "/" + name + "/panorama");
    }

    @Environment(EnvType.CLIENT)
    public boolean isPanoramaReady() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (isPanoramaLoading) {
            return false;
        }
        List<Integer> missingPanoramaFaces = new ArrayList<>();
        for (int face = 0; face < 6; face++) {
            if (client.getTextureManager().getOrDefault(generateFaceTextureId(face), MissingSprite.getMissingSpriteTexture()) == MissingSprite.getMissingSpriteTexture()) {
                missingPanoramaFaces.add(face);
            }
        }
        if (missingPanoramaFaces.isEmpty()) {
            return true;
        }
        File cacheDir = new File(client.runDirectory, "panorama");
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            return false;
        }
        isPanoramaLoading = true;
        boolean isPanoramaExists = true;
        for (int face : missingPanoramaFaces) {
            boolean isLastLoading = face == missingPanoramaFaces.get(missingPanoramaFaces.size() - 1);
            File cacheFile = generateCacheFile(cacheDir, face);
            boolean isFaceExists = cacheFile.exists();
            if (!isFaceExists) {
                isPanoramaExists = false;
            }
            String url = generateURL(face);
            SpyglassPanoramaTexture spyglassPanoramaTexture = new SpyglassPanoramaTexture(cacheFile, url, success -> {
                if (!isLastLoading || client.player == null) {
                    return;
                }
                isPanoramaLoading = false;
                if (!isFaceExists) {
                    if (success) {
                        client.player.sendMessage(Text.of(Text.translatable("spyglass.panorama.loading.success").getString()).copy().formatted(Formatting.GREEN), true);
                    } else {
                        client.player.sendMessage(Text.of(Text.translatable("spyglass.panorama.loading.error").getString()).copy().formatted(Formatting.RED), true);
                    }
                }
            });
            client.getTextureManager().registerTexture(generateFaceTextureId(face), spyglassPanoramaTexture);
        }
        if (!isPanoramaExists && client.player != null) {
            client.player.sendMessage(Text.of(Text.translatable("spyglass.panorama.loading").getString()).copy().formatted(Formatting.YELLOW), true);
        }
        return isPanoramaExists;
    }

    private String generateURL(int face) {
        return "https://raw.githubusercontent.com/diskree/" + BuildConfig.MOD_NAME + "/main/external/panorama/" + type + "/" + name + "/panorama_" + face + ".png";
    }

    private Identifier generateFaceTextureId(int face) {
        return new Identifier(AchieveToDo.ID, generatePanoramaTextureId().getPath() + "_" + face + ".png");
    }

    private File generateCacheFile(File cacheDir, int face) {
        return new File(cacheDir, type + "/" + name + "/panorama_" + face + ".png");
    }
}
