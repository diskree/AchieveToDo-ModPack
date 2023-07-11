package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.*;
import com.diskree.achievetodo.advancements.CreateWorldAchieveToDoTab;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.world.WorldCreator;
import net.minecraft.resource.pack.ResourcePackManager;
import net.minecraft.server.world.FeatureAndDataSettings;
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
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin implements CreateWorldScreenImpl {

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
    @Final
    WorldCreator worldCreator;

    @Unique
    private boolean isWaitingDatapacks;

    @Shadow
    private @Nullable ResourcePackManager packManager;

    @Shadow
    protected abstract void createLevel();

    @Shadow
    @Nullable
    protected abstract Pair<Path, ResourcePackManager> getScannedPack(FeatureAndDataSettings settings);

    @Shadow
    protected abstract void applyDataPacks(ResourcePackManager dataPackManager, boolean warnForExperimentsIfApplicable, Consumer<FeatureAndDataSettings> consumer);

    @Shadow
    @Nullable
    protected abstract Path getDataPackTempDir();

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/HeaderBar$Builder;tabs([Lnet/minecraft/client/gui/tab/Tab;)Lnet/minecraft/client/gui/widget/HeaderBar$Builder;"))
    private void initInject(Args args) {
        CreateWorldScreen self = (CreateWorldScreen) (Object) this;
        Tab[] originalTabs = args.get(0);
        Tab[] newTabs = new Tab[originalTabs.length + 1];
        System.arraycopy(originalTabs, 0, newTabs, 0, 2);
        newTabs[2] = new CreateWorldAchieveToDoTab(self);
        System.arraycopy(originalTabs, 2, newTabs, 3, originalTabs.length - 2);
        args.set(0, newTabs);
    }

    @Inject(method = "createLevel", at = @At("HEAD"), cancellable = true)
    private void createLevelInject(CallbackInfo ci) {
        CreateWorldScreen self = (CreateWorldScreen) (Object) this;
        WorldCreatorImpl worldCreatorImpl = (WorldCreatorImpl) worldCreator;
        MinecraftClient client = self.client;
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
            if (!new File(globalPacksDir.toFile(), requiredPack.toFileName()).exists()) {
                client.setScreen(new DownloadExternalPackScreen(self, requiredPack, (exitWithCreateLevel) -> {
                    if (exitWithCreateLevel) {
                        createLevel();
                    }
                }));
                ci.cancel();
                return;
            }
        }

        if (!isWaitingDatapacks) {
            Path worldPacksDir = getDataPackTempDir();
            if (worldPacksDir == null) {
                ci.cancel();
                return;
            }

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(globalPacksDir)) {
                for (Path path : stream) {
                    if (!Files.isDirectory(path)) {
                        Files.copy(path, worldPacksDir.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (packManager == null) {
                getScannedPack(worldCreator.getContext().dataConfiguration());
            }
            if (packManager != null) {
                packManager.enablePackProfile(AchieveToDoMod.BACAP_DATA_PACK);
                if (isHardcoreEnabled) {
                    packManager.enablePackProfile(AchieveToDoMod.BACAP_HARDCORE_DATA_PACK);
                }
                if (isNullscapeEnabled) {
                    packManager.enablePackProfile(AchieveToDoMod.BACAP_NULLSCAPE_DATA_PACK);
                }
                if (isAmplifiedNetherEnabled) {
                    packManager.enablePackProfile(AchieveToDoMod.BACAP_AMPLIFIED_NETHER_DATA_PACK);
                }
                if (isTerralithEnabled) {
                    packManager.enablePackProfile(AchieveToDoMod.BACAP_TERRALITH_DATA_PACK);
                }

                if (isNullscapeEnabled) {
                    packManager.enablePackProfile(AchieveToDoMod.NULLSCAPE_DATA_PACK);
                }
                if (isAmplifiedNetherEnabled) {
                    packManager.enablePackProfile(AchieveToDoMod.AMPLIFIED_NETHER_DATA_PACK);
                }
                if (isTerralithEnabled) {
                    packManager.enablePackProfile(AchieveToDoMod.TERRALITH_DATA_PACK);
                }

                packManager.enablePackProfile(AchieveToDoMod.BACAP_OVERRIDE_DATA_PACK);
                if (isHardcoreEnabled) {
                    packManager.enablePackProfile(AchieveToDoMod.BACAP_HARDCORE_OVERRIDE_DATA_PACK);
                }
                if (worldCreatorImpl.achieveToDo$isItemRewardsEnabled()) {
                    packManager.enablePackProfile(AchieveToDoMod.BACAP_REWARDS_ITEM_DATA_PACK_NAME);
                }
                if (worldCreatorImpl.achieveToDo$isExperienceRewardsEnabled()) {
                    packManager.enablePackProfile(AchieveToDoMod.BACAP_REWARDS_EXPERIENCE_DATA_PACK_NAME);
                }
                if (worldCreatorImpl.achieveToDo$isTrophyRewardsEnabled()) {
                    packManager.enablePackProfile(AchieveToDoMod.BACAP_REWARDS_TROPHY_DATA_PACK_NAME);
                }

                isWaitingDatapacks = true;
                applyDataPacks(packManager, true, (dataConfiguration) -> client.setScreen(self));

                ci.cancel();
            }
        }
    }
}
