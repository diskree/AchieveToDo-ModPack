package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;
import java.util.stream.Stream;

@Mixin(PackScreen.class)
public class PackScreenMixin {

    @Redirect(method = "updatePackList", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;forEach(Ljava/util/function/Consumer;)V"))
    public void updatePackListRedirect(Stream<ResourcePackOrganizer.Pack> instance, Consumer<ResourcePackOrganizer.Pack> consumer) {
        instance.filter(pack -> {
            String name = pack.getName();
            boolean isHiddenDataPack = name.equals(AchieveToDo.BACAP_DATA_PACK) ||
                    name.equals(AchieveToDo.BACAP_HARDCORE_DATA_PACK) ||
                    name.equals(AchieveToDo.TERRALITH_DATA_PACK) ||
                    name.equals(AchieveToDo.BACAP_TERRALITH_DATA_PACK) ||
                    name.equals(AchieveToDo.AMPLIFIED_NETHER_DATA_PACK) ||
                    name.equals(AchieveToDo.BACAP_AMPLIFIED_NETHER_DATA_PACK) ||
                    name.equals(AchieveToDo.NULLSCAPE_DATA_PACK) ||
                    name.equals(AchieveToDo.BACAP_NULLSCAPE_DATA_PACK) ||
                    name.equals(AchieveToDo.BACAP_OVERRIDE_DATA_PACK.toString()) ||
                    name.equals(AchieveToDo.BACAP_OVERRIDE_HARDCORE_DATA_PACK.toString()) ||
                    name.equals(AchieveToDo.BACAP_REWARDS_ITEM_DATA_PACK_NAME.toString()) ||
                    name.equals(AchieveToDo.BACAP_REWARDS_EXPERIENCE_DATA_PACK_NAME.toString()) ||
                    name.equals(AchieveToDo.BACAP_REWARDS_TROPHY_DATA_PACK_NAME.toString()) ||
                    name.equals(AchieveToDo.BACAP_COOPERATIVE_MODE_DATA_PACK_NAME.toString());
            return !isHiddenDataPack;
        }).forEach(consumer);
    }
}
