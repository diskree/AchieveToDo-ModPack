package com.diskree.achievetodo.client;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.CommonTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@ClientOnly
public class DownloadExternalPackScreen extends ConfirmScreen {

    private final Screen parent;
    private final ExternalPack externalPack;
    private final BooleanConsumer exitCallback;
    private boolean exitWithCreateLevel;

    public DownloadExternalPackScreen(Screen parent, ExternalPack externalPack, BooleanConsumer exitCallback) {
        super(
                (download) -> Util.getOperatingSystem().open(download ? externalPack.getDownloadUrl() : externalPack.getPageUrl()),
                Text.translatable("external.pack.required_prefix").append(Text.of(externalPack.getName()).copy().formatted(externalPack.getColor(), Formatting.ITALIC)),
                Text.translatable("external.pack.help").copy().formatted(Formatting.YELLOW)
        );
        this.parent = parent;
        this.externalPack = externalPack;
        this.exitCallback = exitCallback;
    }

    @Override
    public void closeScreen() {
        if (client != null) {
            client.setScreen(parent);
            exitCallback.accept(exitWithCreateLevel);
        }
    }

    @Override
    protected void addButtons(int y) {
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("external.pack.open_page"), button -> callback.accept(false)).positionAndSize(this.width / 2 - 50 - 105, y, 100, 20).build());
        this.addDrawableChild(ButtonWidget.builder(CommonTexts.CANCEL, button -> closeScreen()).positionAndSize(this.width / 2 - 50, y, 100, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("external.pack.download"), button -> callback.accept(true)).positionAndSize(this.width / 2 - 50 + 105, y, 100, 20).build());
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            closeScreen();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void filesDragged(List<Path> paths) {
        super.filesDragged(paths);
        if (client != null && paths != null && paths.size() == 1) {
            Path downloadedFile = paths.get(0);
            Path fileName = downloadedFile.getFileName();
            if (fileName.toString().endsWith(".zip")) {
                Path globalPacksDir = new File(client.runDirectory, "datapacks").toPath();
                try {
                    if (!Files.exists(globalPacksDir)) {
                        Files.createDirectory(globalPacksDir);
                    }
                    if (fileName.toString().contains("UNZIP ME")) {
                        Path extractedArchive = AdvancementsEncryptor.unzip(downloadedFile, globalPacksDir);
                        Files.move(extractedArchive, globalPacksDir.resolve(externalPack.toFileName()));
                        Files.delete(downloadedFile);
                    } else {
                        Files.move(downloadedFile, globalPacksDir.resolve(externalPack.toFileName()));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            exitWithCreateLevel = true;
            closeScreen();
        }
    }
}