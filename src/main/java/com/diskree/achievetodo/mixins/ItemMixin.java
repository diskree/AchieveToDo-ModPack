package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "getMaxUseTime", at = @At("HEAD"), cancellable = true)
    private void getMaxUseTimeInject(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack != null && stack.getItem() != null && stack.getItem().isFood() && AchieveToDoMod.isFoodBlocked(stack.getItem().getFoodComponent())) {
            cir.setReturnValue(9);
        }
    }

    @Inject(method = "finishUsing", at = @At("HEAD"), cancellable = true)
    private void finishUsingInject(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (stack != null && stack.getItem() != null && stack.getItem().isFood() && user instanceof PlayerEntity && AchieveToDoMod.isFoodBlocked(stack.getItem().getFoodComponent())) {
            MinecraftClient.getInstance().options.useKey.setPressed(false);
            AchieveToDoMod.showFoodBlockedDescription(stack.getItem().getFoodComponent());
            cir.setReturnValue(stack);
        }
    }

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    private void appendTooltipInject(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (stack != null && stack.isOf(Items.SPYGLASS) && stack.hasNbt()) {
            NbtCompound nbt = stack.getNbt();
            if (nbt != null) {
                int panoramaType = nbt.getInt("CustomModelData");
                if (panoramaType != 0) {
                    tooltip.add(Text.translatable("spyglass.tooltip.panorama.header").formatted(Formatting.DARK_AQUA));
                    if (panoramaType == 1) {
                        tooltip.add(Text.translatable("spyglass.tooltip.panorama.biome").formatted(Formatting.DARK_GREEN));
                    } else if (panoramaType == 2) {
                        tooltip.add(Text.translatable("spyglass.tooltip.panorama.structure").formatted(Formatting.DARK_BLUE));
                    } else if (panoramaType == 3) {
                        tooltip.add(Text.translatable("spyglass.tooltip.panorama.chest").formatted(Formatting.DARK_PURPLE));
                    }
                }
            }
        }
    }
}
