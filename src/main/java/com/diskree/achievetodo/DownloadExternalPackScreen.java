package com.diskree.achievetodo;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.CommonTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DownloadExternalPackScreen extends ConfirmScreen {

    private final Screen parent;
    private final ExternalPack externalPack;
    private final BooleanConsumer exitCallback;
    private boolean exitWithCreateLevel;

    public DownloadExternalPackScreen(Screen parent, ExternalPack externalPack, BooleanConsumer exitCallback) {
        super(
                (download) -> Util.getOperatingSystem().open(download ? externalPack.getDownloadUrl() : externalPack.getPageUrl()),
                Text.translatable("external.pack.required_prefix").append(Text.of(externalPack.getName()).copy().formatted(Formatting.BLUE, Formatting.ITALIC)),
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
            if (fileName.toString().endsWith(".zip") && !fileName.toString().startsWith("[UNZIP ME]")) {
                try {
                    Path globalPacksDir = new File(client.runDirectory, "datapacks").toPath();
                    if (!Files.exists(globalPacksDir)) {
                        Files.createDirectory(globalPacksDir);
                    }
                    Files.move(downloadedFile, globalPacksDir.resolve(externalPack.toFileName()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            exitWithCreateLevel = true;
            closeScreen();
        }
    }
}