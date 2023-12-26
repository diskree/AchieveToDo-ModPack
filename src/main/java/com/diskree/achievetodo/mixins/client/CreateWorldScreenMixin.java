package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.diskree.achievetodo.advancements.CreateWorldTab;
import com.diskree.achievetodo.client.CreateWorldScreenImpl;
import com.diskree.achievetodo.client.DownloadExternalPackScreen;
import com.diskree.achievetodo.client.ExternalPack;
import com.diskree.achievetodo.client.WorldCreatorImpl;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.resource.ResourcePackManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Shadow
    @Final
    WorldCreator worldCreator;
    @Unique
    private boolean isWaitingDatapacks;
    @Shadow
    private @Nullable ResourcePackManager packManager;

    @Override
    public boolean achieveToDo$datapacksLoaded() {
        if (isWaitingDatapacks) {
            createLevel();
            isWaitingDatapacks = false;
            return true;
        }
        return false;
    }

    @Shadow
    protected abstract void createLevel();

    @Shadow
    @Nullable
    protected abstract Pair<Path, ResourcePackManager> getScannedPack(DataConfiguration settings);

    @Shadow
    protected abstract void applyDataPacks(ResourcePackManager dataPackManager, boolean warnForExperimentsIfApplicable, Consumer<DataConfiguration> consumer);

    @Shadow
    @Nullable
    protected abstract Path getDataPackTempDir();

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;tabs([Lnet/minecraft/client/gui/tab/Tab;)Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;"))
    private void initInject(Args args) {
        CreateWorldScreen createWorldScreen = (CreateWorldScreen) (Object) this;
        Tab[] originalTabs = args.get(0);
        Tab[] newTabs = new Tab[originalTabs.length + 1];
        System.arraycopy(originalTabs, 0, newTabs, 0, 2);
        newTabs[2] = new CreateWorldTab(createWorldScreen);
        System.arraycopy(originalTabs, 2, newTabs, 3, originalTabs.length - 2);
        args.set(0, newTabs);
    }

    @Inject(method = "createLevel", at = @At("HEAD"), cancellable = true)
    private void createLevelInject(CallbackInfo ci) {
        CreateWorldScreen createWorldScreen = (CreateWorldScreen) (Object) this;
        WorldCreatorImpl worldCreatorImpl = (WorldCreatorImpl) worldCreator;
        MinecraftClient client = createWorldScreen.client;
        if (client == null) {
            ci.cancel();
            return;
        }

        boolean isHardcoreEnabled = worldCreator.isHardcore();
        boolean isTerralithEnabled = worldCreatorImpl.achieveToDo$isTerralithEnabled();
        boolean isAmplifiedNetherEnabled = worldCreatorImpl.achieveToDo$isAmplifiedNetherEnabled();
        boolean isNullscapeEnabled = worldCreatorImpl.achieveToDo$isNullscapeEnabled();

        Path globalPacksDir = new File(client.runDirectory, "datapacks").toPath();
        List<ExternalPack> requiredPacks = new ArrayList<>();
        requiredPacks.add(ExternalPack.BACAP);
        if (isHardcoreEnabled) {
            requiredPacks.add(ExternalPack.BACAP_HARDCORE);
        }
        if (isTerralithEnabled) {
            requiredPacks.add(ExternalPack.BACAP_TERRALITH);
        }
        if (isAmplifiedNetherEnabled) {
            requiredPacks.add(ExternalPack.BACAP_AMPLIFIED_NETHER);
        }
        if (isNullscapeEnabled) {
            requiredPacks.add(ExternalPack.BACAP_NULLSCAPE);
        }
        if (isTerralithEnabled) {
            requiredPacks.add(ExternalPack.TERRALITH);
        }
        if (isAmplifiedNetherEnabled) {
            requiredPacks.add(ExternalPack.AMPLIFIED_NETHER);
        }
        if (isNullscapeEnabled) {
            requiredPacks.add(ExternalPack.NULLSCAPE);
        }
        for (ExternalPack requiredPack : requiredPacks) {
            if (Files.exists(globalPacksDir.resolve(requiredPack.toFileName()))) {
                continue;
            }
            client.setScreen(new DownloadExternalPackScreen(createWorldScreen, requiredPack, (exitWithCreateLevel) -> {
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
                for (ExternalPack pack : requiredPacks) {
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
                packManager.enable(AchieveToDo.BACAP_DATA_PACK);
                if (isHardcoreEnabled) {
                    packManager.enable(AchieveToDo.BACAP_HARDCORE_DATA_PACK);
                }
                if (isNullscapeEnabled) {
                    packManager.enable(AchieveToDo.BACAP_NULLSCAPE_DATA_PACK);
                }
                if (isAmplifiedNetherEnabled) {
                    packManager.enable(AchieveToDo.BACAP_AMPLIFIED_NETHER_DATA_PACK);
                }
                if (isTerralithEnabled) {
                    packManager.enable(AchieveToDo.BACAP_TERRALITH_DATA_PACK);
                }

                if (isNullscapeEnabled) {
                    packManager.enable(AchieveToDo.NULLSCAPE_DATA_PACK);
                }
                if (isAmplifiedNetherEnabled) {
                    packManager.enable(AchieveToDo.AMPLIFIED_NETHER_DATA_PACK);
                }
                if (isTerralithEnabled) {
                    packManager.enable(AchieveToDo.TERRALITH_DATA_PACK);
                }

                packManager.enable(AchieveToDo.BACAP_OVERRIDE_DATA_PACK.toString());
                if (isHardcoreEnabled) {
                    packManager.enable(AchieveToDo.BACAP_OVERRIDE_HARDCORE_DATA_PACK.toString());
                }
                if (worldCreatorImpl.achieveToDo$isCooperativeModeEnabled()) {
                    packManager.enable(AchieveToDo.BACAP_COOPERATIVE_MODE_DATA_PACK_NAME.toString());
                }
                if (worldCreatorImpl.achieveToDo$isItemRewardsEnabled()) {
                    packManager.enable(AchieveToDo.BACAP_REWARDS_ITEM_DATA_PACK_NAME.toString());
                }
                if (worldCreatorImpl.achieveToDo$isExperienceRewardsEnabled()) {
                    packManager.enable(AchieveToDo.BACAP_REWARDS_EXPERIENCE_DATA_PACK_NAME.toString());
                }
                if (worldCreatorImpl.achieveToDo$isTrophyRewardsEnabled()) {
                    packManager.enable(AchieveToDo.BACAP_REWARDS_TROPHY_DATA_PACK_NAME.toString());
                }

                isWaitingDatapacks = true;
                applyDataPacks(packManager, false, (dataConfiguration) -> client.setScreen(createWorldScreen));

                ci.cancel();
            }
        }
    }
}
