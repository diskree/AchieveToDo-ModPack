package com.diskree.achievetodo.mixin.client;

import com.diskree.achievetodo.ExternalDatapack;
import com.diskree.achievetodo.InternalDatapack;
import com.diskree.achievetodo.gui.CreateWorldTab;
import com.diskree.achievetodo.gui.DownloadExternalDatapackScreen;
import com.diskree.achievetodo.injection.CreateWorldScreenImpl;
import com.diskree.achievetodo.injection.WorldCreatorImpl;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.world.level.LevelInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin implements CreateWorldScreenImpl {

    @Unique
    private boolean isWaitingDatapacks;

    @Override
    public boolean achievetodo$isDatapacksLoaded() {
        if (isWaitingDatapacks) {
            createLevel();
            isWaitingDatapacks = false;
            return true;
        }
        return false;
    }

    @Shadow
    @Final
    public WorldCreator worldCreator;

    @Shadow
    private @Nullable ResourcePackManager packManager;

    @Inject(
            method = "create(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/client/world/GeneratorOptionsHolder;Ljava/nio/file/Path;)Lnet/minecraft/client/gui/screen/world/CreateWorldScreen;",
            at = @At(value = "TAIL")
    )
    private static void fillSettingsOnWorldRecreate(
            MinecraftClient client,
            Screen parent,
            @NotNull LevelInfo levelInfo,
            GeneratorOptionsHolder generatorOptionsHolder,
            Path dataPackTempDir,
            CallbackInfoReturnable<CreateWorldScreen> cir,
            @Local @NotNull CreateWorldScreen createWorldScreen
    ) {
        DataConfiguration dataConfiguration = levelInfo.getDataConfiguration();
        if (dataConfiguration != null) {
            DataPackSettings dataPackSettings = dataConfiguration.dataPacks();
            if (dataPackSettings != null) {
                List<String> enabledPacks = dataPackSettings.getEnabled();
                if (enabledPacks != null) {
                    WorldCreator worldCreator = createWorldScreen.worldCreator;
                    if (worldCreator instanceof WorldCreatorImpl worldCreatorImpl) {
                        worldCreatorImpl.achievetodo$setTerralithEnabled(
                                enabledPacks.contains(ExternalDatapack.TERRALITH.toDatapackId())
                        );
                        worldCreatorImpl.achievetodo$setAmplifiedNetherEnabled(
                                enabledPacks.contains(ExternalDatapack.AMPLIFIED_NETHER.toDatapackId())
                        );
                        worldCreatorImpl.achievetodo$setNullscapeEnabled(
                                enabledPacks.contains(ExternalDatapack.NULLSCAPE.toDatapackId())
                        );
                        worldCreatorImpl.achievetodo$setItemRewardsEnabled(
                                enabledPacks.contains(InternalDatapack.BACAP_REWARDS_ITEM.toDatapackId().toString())
                        );
                        worldCreatorImpl.achievetodo$setExperienceRewardsEnabled(
                                enabledPacks.contains(InternalDatapack.BACAP_REWARDS_EXPERIENCE.toDatapackId().toString())
                        );
                        worldCreatorImpl.achievetodo$setTrophyRewardsEnabled(
                                enabledPacks.contains(InternalDatapack.BACAP_REWARDS_TROPHY.toDatapackId().toString())
                        );
                        worldCreatorImpl.achievetodo$setCooperativeModeEnabled(
                                enabledPacks.contains(InternalDatapack.BACAP_COOPERATIVE_MODE.toDatapackId().toString())
                        );
                    }
                }
            }
        }
    }

    @Shadow
    protected abstract void createLevel();

    @Shadow
    @Nullable
    protected abstract Pair<Path, ResourcePackManager> getScannedPack(DataConfiguration settings);

    @Shadow
    protected abstract void applyDataPacks(
            ResourcePackManager dataPackManager,
            boolean warnForExperimentsIfApplicable,
            Consumer<DataConfiguration> consumer
    );

    @Shadow
    @Nullable
    protected abstract Path getDataPackTempDir();

    @ModifyArgs(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;tabs([Lnet/minecraft/client/gui/tab/Tab;)Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;"
            )
    )
    private void addModTab(@NotNull Args args) {
        CreateWorldScreen createWorldScreen = (CreateWorldScreen) (Object) this;
        Tab[] originalTabs = args.get(0);
        Tab[] newTabs = new Tab[originalTabs.length + 1];
        System.arraycopy(originalTabs, 0, newTabs, 0, 2);
        newTabs[2] = new CreateWorldTab(createWorldScreen);
        System.arraycopy(originalTabs, 2, newTabs, 3, originalTabs.length - 2);
        args.set(0, newTabs);
    }

    @Inject(
            method = "createLevel",
            at = @At("HEAD"),
            cancellable = true
    )
    private void prepareDatapacks(CallbackInfo ci) {
        CreateWorldScreen createWorldScreen = (CreateWorldScreen) (Object) this;
        WorldCreatorImpl worldCreatorImpl = (WorldCreatorImpl) worldCreator;
        MinecraftClient client = createWorldScreen.client;
        if (client == null) {
            ci.cancel();
            return;
        }

        boolean isHardcoreEnabled = worldCreator.isHardcore();
        boolean isTerralithEnabled = worldCreatorImpl.achievetodo$isTerralithEnabled();
        boolean isAmplifiedNetherEnabled = worldCreatorImpl.achievetodo$isAmplifiedNetherEnabled();
        boolean isNullscapeEnabled = worldCreatorImpl.achievetodo$isNullscapeEnabled();

        Path globalPacksDir = new File(client.runDirectory, "datapacks").toPath();
        List<ExternalDatapack> requiredPacks = new ArrayList<>();
        requiredPacks.add(ExternalDatapack.BACAP);
        if (isHardcoreEnabled) {
            requiredPacks.add(ExternalDatapack.BACAP_HARDCORE);
        }
        if (isTerralithEnabled) {
            requiredPacks.add(ExternalDatapack.BACAP_TERRALITH);
        }
        if (isAmplifiedNetherEnabled) {
            requiredPacks.add(ExternalDatapack.BACAP_AMPLIFIED_NETHER);
        }
        if (isNullscapeEnabled) {
            requiredPacks.add(ExternalDatapack.BACAP_NULLSCAPE);
        }
        if (isTerralithEnabled) {
            requiredPacks.add(ExternalDatapack.TERRALITH);
        }
        if (isAmplifiedNetherEnabled) {
            requiredPacks.add(ExternalDatapack.AMPLIFIED_NETHER);
        }
        if (isNullscapeEnabled) {
            requiredPacks.add(ExternalDatapack.NULLSCAPE);
        }
        for (ExternalDatapack requiredPack : requiredPacks) {
            if (Files.exists(globalPacksDir.resolve(requiredPack.toFileName()))) {
                continue;
            }
            client.setScreen(new DownloadExternalDatapackScreen(createWorldScreen, requiredPack, exitWithCreateLevel -> {
                if (exitWithCreateLevel) {
                    createLevel();
                }
            }));
            ci.cancel();
            return;
        }

        if (!isWaitingDatapacks) {
            Path worldPacksDir = getDataPackTempDir();
            if (worldPacksDir == null) {
                ci.cancel();
                return;
            }
            try {
                for (ExternalDatapack pack : requiredPacks) {
                    Path globalPack = globalPacksDir.resolve(pack.toFileName());
                    Path worldPack = worldPacksDir.resolve(globalPack.getFileName());
                    Files.copy(globalPack, worldPack, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException ignored) {
            }

            if (packManager != null) {
                packManager.scanPacks();
            }
            getScannedPack(worldCreator.getGeneratorOptionsHolder().dataConfiguration());
            if (packManager != null) {
                for (ExternalDatapack externalDatapack : ExternalDatapack.values()) {
                    packManager.disable(externalDatapack.toDatapackId());
                }
                for (InternalDatapack internalDatapack : InternalDatapack.values()) {
                    packManager.disable(internalDatapack.toDatapackId().toString());
                }

                packManager.enable(ExternalDatapack.BACAP.toDatapackId());
                packManager.enable(InternalDatapack.BACAP_OVERRIDE.toDatapackId().toString());
                if (isHardcoreEnabled) {
                    packManager.enable(ExternalDatapack.BACAP_HARDCORE.toDatapackId());
                    packManager.enable(InternalDatapack.BACAP_OVERRIDE_HARDCORE.toDatapackId().toString());
                }
                if (isNullscapeEnabled) {
                    packManager.enable(ExternalDatapack.NULLSCAPE.toDatapackId());
                    packManager.enable(ExternalDatapack.BACAP_NULLSCAPE.toDatapackId());
                }
                if (isAmplifiedNetherEnabled) {
                    packManager.enable(ExternalDatapack.AMPLIFIED_NETHER.toDatapackId());
                    packManager.enable(ExternalDatapack.BACAP_AMPLIFIED_NETHER.toDatapackId());
                }
                if (isTerralithEnabled) {
                    packManager.enable(ExternalDatapack.TERRALITH.toDatapackId());
                    packManager.enable(ExternalDatapack.BACAP_TERRALITH.toDatapackId());
                }
                if (worldCreatorImpl.achievetodo$isItemRewardsEnabled()) {
                    packManager.enable(InternalDatapack.BACAP_REWARDS_ITEM.toDatapackId().toString());
                }
                if (worldCreatorImpl.achievetodo$isExperienceRewardsEnabled()) {
                    packManager.enable(InternalDatapack.BACAP_REWARDS_EXPERIENCE.toDatapackId().toString());
                }
                if (worldCreatorImpl.achievetodo$isTrophyRewardsEnabled()) {
                    packManager.enable(InternalDatapack.BACAP_REWARDS_TROPHY.toDatapackId().toString());
                }
                if (worldCreatorImpl.achievetodo$isCooperativeModeEnabled()) {
                    packManager.enable(InternalDatapack.BACAP_COOPERATIVE_MODE.toDatapackId().toString());
                }

                isWaitingDatapacks = true;
                applyDataPacks(packManager, false, (dataConfiguration) -> client.setScreen(createWorldScreen));

                ci.cancel();
            }
        }
    }
}
