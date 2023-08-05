package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.server.AchieveToDoServer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.advancement.AdvancementTabType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AdvancementTabType.class)
public class AdvancementTabTypeMixin {

    @ModifyConstant(method = "getTabX", constant = @Constant(intValue = 248), require = 1)
    public int getTabXModify(int constant) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.currentScreen == null) {
            return 0;
        }
        return client.currentScreen.width - AchieveToDoServer.ADVANCEMENTS_SCREEN_MARGIN * 2 - 4;
    }

    @ModifyConstant(method = "getTabY", constant = @Constant(intValue = 136), require = 1)
    public int getTabYModify(int constant) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.currentScreen == null) {
            return 0;
        }
        return client.currentScreen.height - AchieveToDoServer.ADVANCEMENTS_SCREEN_MARGIN * 2 - 4;
    }
}
