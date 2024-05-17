package com.diskree.achievetodo.mixin.client;

import com.diskree.achievetodo.ExternalDatapack;
import com.diskree.achievetodo.InternalDatapack;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Consumer;
import java.util.stream.Stream;

@Mixin(PackScreen.class)
public class PackScreenMixin {

    @Redirect(
            method = "updatePackList",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/stream/Stream;forEach(Ljava/util/function/Consumer;)V"
            )
    )
    public void updatePackListRedirect(
            @NotNull Stream<ResourcePackOrganizer.Pack> packs,
            Consumer<ResourcePackOrganizer.Pack> consumer
    ) {
        packs.filter(pack -> {
            String packName = pack.getName();
            for (ExternalDatapack externalDatapack : ExternalDatapack.values()) {
                if (packName.equals(externalDatapack.toDatapackId())) {
                    return false;
                }
            }
            for (InternalDatapack internalDatapack : InternalDatapack.values()) {
                if (packName.equals(internalDatapack.toDatapackId().toString())) {
                    return false;
                }
            }
            return true;
        }).forEach(consumer);
    }
}
