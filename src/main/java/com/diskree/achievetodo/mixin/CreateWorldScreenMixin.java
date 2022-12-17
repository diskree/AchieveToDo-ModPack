package com.diskree.achievetodo.mixin;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
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
    private ButtonWidget dataPacksButton;

    @Shadow
    protected DataConfiguration dataConfiguration;

    @Inject(method = "createLevel", at = @At("HEAD"))
    public void applyDataPacksInject(CallbackInfo ci) {
        if (hardcore) {
            List<String> enabled = new ArrayList<>(dataConfiguration.dataPacks().getEnabled());
            enabled.add("globalOpt:BACAP_HC.zip");
            dataConfiguration = new DataConfiguration(new DataPackSettings(enabled, new ArrayList<>()), dataConfiguration.enabledFeatures());
        }
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void initInject(CallbackInfo ci) {
        dataPacksButton.active = false;
    }

}
