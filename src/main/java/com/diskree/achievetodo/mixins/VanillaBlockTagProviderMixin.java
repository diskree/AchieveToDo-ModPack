package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.data.server.tag.BlockTagsProvider;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.tag.BlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockTagsProvider.class)
public class VanillaBlockTagProviderMixin {

    @Inject(method = "configure", at = @At("HEAD"))
    public void configureInject(HolderLookup.Provider lookup, CallbackInfo ci) {
        BlockTagsProvider blockTagsProvider = (BlockTagsProvider) (Object) this;
        blockTagsProvider.getOrCreateTagBuilder(BlockTags.DRAGON_IMMUNE)
                .add(AchieveToDo.ANCIENT_CITY_PORTAL_BLOCK, AchieveToDo.ANCIENT_CITY_PORTAL_BLOCK);
    }
}
