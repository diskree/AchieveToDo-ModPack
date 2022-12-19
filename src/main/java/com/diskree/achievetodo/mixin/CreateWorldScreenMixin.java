package com.diskree.achievetodo.mixin;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.resource.DataPackSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(CreateWorldScreen.class)
public class CreateWorldScreenMixin {

    @Shadow
    public boolean hardcore;

    @Shadow
    protected DataConfiguration dataConfiguration;

    @Inject(method = "createLevel", at = @At("HEAD"))
    public void createLevelInject(CallbackInfo ci) {
        List<String> enabled = new ArrayList<>(dataConfiguration.dataPacks().getEnabled());
        List<String> disabled = new ArrayList<>(dataConfiguration.dataPacks().getDisabled());
        enabled.add("file/BACAP.zip");
        enabled.add("file/BACAP_AchieveToDo-core.zip");
        disabled.remove("file/BACAP.zip");
        disabled.remove("file/BACAP_AchieveToDo-core.zip");
        if (hardcore) {
            enabled.add("file/BACAP_HC.zip");
            disabled.remove("file/BACAP_HC.zip");
        }
        dataConfiguration = new DataConfiguration(new DataPackSettings(enabled, disabled), dataConfiguration.enabledFeatures());
    }
}
