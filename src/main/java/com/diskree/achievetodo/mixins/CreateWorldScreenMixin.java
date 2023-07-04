package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.CreateWorldScreenImpl;
import com.diskree.achievetodo.WorldCreatorImpl;
import com.diskree.achievetodo.advancements.CreateWorldAdvancementsTab;
import com.mojang.datafixers.util.Pair;
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

import java.nio.file.Path;
import java.util.function.Consumer;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin implements CreateWorldScreenImpl {

    @Unique
    private boolean isWaitingDatapacks;

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
    private @Nullable ResourcePackManager packManager;

    @Shadow
    @Final
    WorldCreator worldCreator;

    @Shadow
    protected abstract void applyDataPacks(ResourcePackManager dataPackManager, boolean fromPackScreen, Consumer<DataConfiguration> configurationSetter);

    @Shadow
    @Nullable
    protected abstract Pair<Path, ResourcePackManager> getScannedPack(DataConfiguration dataConfiguration);

    @Shadow protected abstract void createLevel();

    @ModifyArgs(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;tabs([Lnet/minecraft/client/gui/tab/Tab;)Lnet/minecraft/client/gui/widget/TabNavigationWidget$Builder;"))
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
                getScannedPack(worldCreator.getGeneratorOptionsHolder().dataConfiguration());
            }
            if (packManager != null) {
                packManager.disable(AchieveToDoMod.BACAP_DATA_PACK_ID.toString());
                packManager.disable(AchieveToDoMod.BACAP_HARDCORE_DATA_PACK_ID.toString());
                packManager.disable(AchieveToDoMod.CORE_DATA_PACK_ID.toString());
                packManager.disable(AchieveToDoMod.HARDCORE_CORE_DATA_PACK_ID.toString());
                packManager.disable(AchieveToDoMod.REWARDS_ITEM_DATA_PACK_ID.toString());
                packManager.disable(AchieveToDoMod.REWARDS_EXPERIENCE_DATA_PACK_ID.toString());
                packManager.disable(AchieveToDoMod.REWARDS_TROPHY_DATA_PACK_ID.toString());
                packManager.disable(AchieveToDoMod.TERRALITH_DATA_PACK_ID.toString());
                packManager.disable(AchieveToDoMod.BACAP_TERRALITH_DATA_PACK_ID.toString());

                packManager.enable(AchieveToDoMod.BACAP_DATA_PACK_ID.toString());
                packManager.enable(AchieveToDoMod.CORE_DATA_PACK_ID.toString());
                if (worldCreator.isHardcore()) {
                    packManager.enable(AchieveToDoMod.BACAP_HARDCORE_DATA_PACK_ID.toString());
                    packManager.enable(AchieveToDoMod.HARDCORE_CORE_DATA_PACK_ID.toString());
                }
                if (worldCreatorImpl.isTerralithEnabled()) {
                    packManager.enable(AchieveToDoMod.TERRALITH_DATA_PACK_ID.toString());
                    packManager.enable(AchieveToDoMod.BACAP_TERRALITH_DATA_PACK_ID.toString());
                }
                if (worldCreatorImpl.isItemRewardsEnabled()) {
                    packManager.enable(AchieveToDoMod.REWARDS_ITEM_DATA_PACK_ID.toString());
                }
                if (worldCreatorImpl.isExperienceRewardsEnabled()) {
                    packManager.enable(AchieveToDoMod.REWARDS_EXPERIENCE_DATA_PACK_ID.toString());
                }
                if (worldCreatorImpl.isTrophyRewardsEnabled()) {
                    packManager.enable(AchieveToDoMod.REWARDS_TROPHY_DATA_PACK_ID.toString());
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
