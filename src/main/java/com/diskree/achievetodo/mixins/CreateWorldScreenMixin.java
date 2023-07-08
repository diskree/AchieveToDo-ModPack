package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.CreateWorldScreenImpl;
import com.diskree.achievetodo.WorldCreatorImpl;
import com.diskree.achievetodo.advancements.CreateWorldAdvancementsTab;
import com.mojang.datafixers.util.Pair;
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

import java.nio.file.Path;
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

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/HeaderBar$Builder;tabs([Lnet/minecraft/client/gui/tab/Tab;)Lnet/minecraft/client/gui/widget/HeaderBar$Builder;"))
    private void initInject(Args args) {
        CreateWorldScreen self = (CreateWorldScreen) (Object) this;
        Tab[] originalTabs = args.get(0);
        Tab[] newTabs = new Tab[originalTabs.length + 1];
        System.arraycopy(originalTabs, 0, newTabs, 0, 2);
        newTabs[2] = new CreateWorldAdvancementsTab(self);
        System.arraycopy(originalTabs, 2, newTabs, 3, originalTabs.length - 2);
        args.set(0, newTabs);
    }

    @Inject(method = "createLevel", at = @At("HEAD"), cancellable = true)
    private void createLevelInject(CallbackInfo ci) {
        if (!isWaitingDatapacks) {
            CreateWorldScreen self = (CreateWorldScreen) (Object) this;
            WorldCreatorImpl worldCreatorImpl = (WorldCreatorImpl) worldCreator;
            if (packManager == null) {
                getScannedPack(worldCreator.getContext().dataConfiguration());
            }
            if (packManager != null) {
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_HARDCORE_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.ACHIEVETODO_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.ACHIEVETODO_HARDCORE_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.REWARDS_ITEM_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.REWARDS_EXPERIENCE_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.REWARDS_TROPHY_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.TERRALITH_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_TERRALITH_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.AMPLIFIED_NETHER_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_AMPLIFIED_NETHER_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.NULLSCAPE_DATA_PACK_NAME);
                packManager.disablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_NULLSCAPE_DATA_PACK_NAME);

                packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_DATA_PACK_NAME);
                if (worldCreator.isHardcore()) {
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_HARDCORE_DATA_PACK_NAME);
                }
                packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.ACHIEVETODO_DATA_PACK_NAME);
                if (worldCreator.isHardcore()) {
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.ACHIEVETODO_HARDCORE_DATA_PACK_NAME);
                }

                if (worldCreatorImpl.achieveToDo$isItemRewardsEnabled()) {
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.REWARDS_ITEM_DATA_PACK_NAME);
                }
                if (worldCreatorImpl.achieveToDo$isExperienceRewardsEnabled()) {
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.REWARDS_EXPERIENCE_DATA_PACK_NAME);
                }
                if (worldCreatorImpl.achieveToDo$isTrophyRewardsEnabled()) {
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.REWARDS_TROPHY_DATA_PACK_NAME);
                }
                if (worldCreatorImpl.achieveToDo$isTerralithEnabled()) {
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.TERRALITH_DATA_PACK_NAME);
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_TERRALITH_DATA_PACK_NAME);
                }
                if (worldCreatorImpl.achieveToDo$isAmplifiedNetherEnabled()) {
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.AMPLIFIED_NETHER_DATA_PACK_NAME);
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_AMPLIFIED_NETHER_DATA_PACK_NAME);
                }
                if (worldCreatorImpl.achieveToDo$isNullscapeEnabled()) {
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.NULLSCAPE_DATA_PACK_NAME);
                    packManager.enablePackProfile(AchieveToDoMod.MOD_ID + "/" + AchieveToDoMod.BACAP_NULLSCAPE_DATA_PACK_NAME);
                }

                isWaitingDatapacks = true;
                applyDataPacks(packManager, true, (dataConfiguration) -> {
                    if (self.client != null) {
                        self.client.setScreen(self);
                    }
                });

                ci.cancel();
            }
        }
    }
}
