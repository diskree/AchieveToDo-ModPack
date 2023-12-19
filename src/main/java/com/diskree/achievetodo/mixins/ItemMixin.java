package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.client.SpyglassPanoramaDetails;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void appendTooltipInject(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        SpyglassPanoramaDetails panoramaDetails = SpyglassPanoramaDetails.of(stack);
        if (panoramaDetails != null) {
            tooltip.add(Text.translatable("spyglass.panorama.tooltip.header").formatted(Formatting.DARK_AQUA));
            tooltip.add(Text.translatable("spyglass.panorama.tooltip." + panoramaDetails.type()).formatted(Formatting.DARK_GREEN));
        }
    }
}