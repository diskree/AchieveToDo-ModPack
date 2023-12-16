package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.data.server.tag.vanilla.VanillaBlockTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VanillaBlockTagProvider.class)
public class VanillaBlockTagProviderMixin {

    @Inject(method = "configure", at = @At("HEAD"))
    public void configureInject(RegistryWrapper.WrapperLookup lookup, CallbackInfo ci) {
        VanillaBlockTagProvider vanillaBlockTagProvider = (VanillaBlockTagProvider) (Object) this;
        vanillaBlockTagProvider.getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE)
                .add(AchieveToDo.ANCIENT_CITY_PORTAL_BLOCK, AchieveToDo.ANCIENT_CITY_PORTAL_BLOCK);
    }
}
