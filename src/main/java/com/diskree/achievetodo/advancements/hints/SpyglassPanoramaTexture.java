package com.diskree.achievetodo.advancements.hints;

import com.diskree.achievetodo.AchieveToDo;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class SpyglassPanoramaTexture extends ResourceTexture {

    private final File cacheFile;
    private final String url;
    @Nullable
    private final Consumer<Boolean> callback;
    @Nullable
    private CompletableFuture<?> loader;
    private boolean loaded;

    public SpyglassPanoramaTexture(File cacheFile, String url, @Nullable Consumer<Boolean> callback) {
        super(new Identifier(AchieveToDo.ID, "textures/panorama_placeholder.png"));
        this.cacheFile = cacheFile;
        this.url = url;
        this.callback = callback;
    }

    private void onTextureLoaded(NativeImage image) {
        if (callback != null) {
            callback.accept(true);
        }
        MinecraftClient.getInstance().execute(() -> {
            loaded = true;
            if (!RenderSystem.isOnRenderThread()) {
                RenderSystem.recordRenderCall(() -> uploadTexture(image));
            } else {
                uploadTexture(image);
            }
        });
    }

    private void uploadTexture(NativeImage image) {
        TextureUtil.prepareImage(getGlId(), image.getWidth(), image.getHeight());
        image.upload(0, 0, 0, true);
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        MinecraftClient.getInstance().execute(() -> {
            if (loaded) {
                return;
            }
            try {
                super.load(manager);
            } catch (IOException e) {
                if (callback != null) {
                    callback.accept(false);
                }
            }
            loaded = true;
        });
        if (loader == null) {
            NativeImage nativeImage = null;
            if (cacheFile.isFile()) {
                nativeImage = loadTexture(new FileInputStream(cacheFile));
            }
            if (nativeImage != null) {
                onTextureLoaded(nativeImage);
            } else {
                loader = CompletableFuture.runAsync(() -> {
                    HttpURLConnection httpURLConnection = null;
                    try {
                        httpURLConnection = (HttpURLConnection) new URL(url).openConnection(MinecraftClient.getInstance().getNetworkProxy());
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(false);
                        httpURLConnection.connect();

                        if (httpURLConnection.getResponseCode() / 100 == 2) {
                            File parentDir = cacheFile.getParentFile();
                            if (!parentDir.exists() && !parentDir.mkdirs()) {
                                if (callback != null) {
                                    callback.accept(false);
                                }
                                return;
                            }
                            FileUtils.copyInputStreamToFile(httpURLConnection.getInputStream(), cacheFile);
                            NativeImage loadedImage;
                            try (FileInputStream fileInputStream = new FileInputStream(cacheFile)) {
                                loadedImage = loadTexture(fileInputStream);
                            }
                            if (loadedImage != null) {
                                MinecraftClient.getInstance().execute(() -> onTextureLoaded(loadedImage));
                            }
                        }
                    } catch (Exception e) {
                        if (callback != null) {
                            callback.accept(false);
                        }
                    } finally {
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                    }
                }, Util.getMainWorkerExecutor());
            }
        }
    }

    @Nullable
    private NativeImage loadTexture(InputStream stream) {
        NativeImage nativeImage = null;
        try {
            nativeImage = NativeImage.read(stream);
        } catch (Exception e) {
            if (callback != null) {
                callback.accept(false);
            }
        }
        return nativeImage;
    }
}
