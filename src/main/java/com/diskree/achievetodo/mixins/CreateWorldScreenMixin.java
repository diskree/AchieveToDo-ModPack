package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.LevelInfoImpl;
import com.diskree.achievetodo.WorldCreatorImpl;
import com.diskree.achievetodo.advancements.CreateWorldAdvancementsTab;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.WorldCreator;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.world.GeneratorOptionsHolder;
import net.minecraft.world.level.LevelInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.nio.file.Path;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {

    @Shadow
    public abstract WorldCreator getWorldCreator();

    @SuppressWarnings({"MixinAnnotationTarget", "UnresolvedMixinReference"})
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

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "createLevelInfo", at = @At("RETURN"), cancellable = true)
    private void createLevelInfoInject(boolean debugWorld, CallbackInfoReturnable<LevelInfo> cir) {
        if (!debugWorld) {
            LevelInfo levelInfo = cir.getReturnValue();
            LevelInfoImpl levelInfoImpl = (LevelInfoImpl) (Object) levelInfo;
            WorldCreatorImpl worldCreatorImpl = (WorldCreatorImpl) getWorldCreator();
            levelInfoImpl.setItemRewardsEnabled(worldCreatorImpl.isItemRewardsEnabled());
            levelInfoImpl.setExperienceRewardsEnabled(worldCreatorImpl.isExperienceRewardsEnabled());
            levelInfoImpl.setTrophyRewardsEnabled(worldCreatorImpl.isTrophyRewardsEnabled());
            cir.setReturnValue(levelInfo);
        }
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "create(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/world/level/LevelInfo;Lnet/minecraft/client/world/GeneratorOptionsHolder;Ljava/nio/file/Path;)Lnet/minecraft/client/gui/screen/world/CreateWorldScreen;", at = @At("RETURN"), cancellable = true)
    private static void createInject(MinecraftClient client, Screen parent, LevelInfo levelInfo, GeneratorOptionsHolder generatorOptionsHolder, Path dataPackTempDir, CallbackInfoReturnable<CreateWorldScreen> cir) {
        CreateWorldScreen createWorldScreen = cir.getReturnValue();
        WorldCreatorImpl worldCreator = (WorldCreatorImpl) createWorldScreen.getWorldCreator();
        LevelInfoImpl levelInfoImpl = (LevelInfoImpl) (Object) levelInfo;
        worldCreator.setItemRewardsEnabled(levelInfoImpl.isItemRewardsEnabled());
        worldCreator.setExperienceRewardsEnabled(levelInfoImpl.isExperienceRewardsEnabled());
        worldCreator.setTrophyRewardsEnabled(levelInfoImpl.isTrophyRewardsEnabled());
        cir.setReturnValue(createWorldScreen);
    }
}
