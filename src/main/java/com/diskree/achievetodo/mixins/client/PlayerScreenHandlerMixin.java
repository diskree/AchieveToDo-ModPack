package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.AchieveToDo;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin {

    @ModifyArg(method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/screen/PlayerScreenHandler;addSlot(Lnet/minecraft/screen/slot/Slot;)Lnet/minecraft/screen/slot/Slot;",
                    ordinal = 2
            )
    )
    private Slot initInject(Slot slot) {
        return new Slot(slot.inventory, slot.getIndex(), slot.x, slot.y) {

            @Override
            public void setStack(ItemStack stack, ItemStack previousStack) {
                slot.setStack(stack, previousStack);
            }

            @Override
            public int getMaxItemCount() {
                return slot.getMaxItemCount();
            }

            @Override
            public boolean canInsert(ItemStack stack) {
                if (AchieveToDo.isEquipmentBlocked(MinecraftClient.getInstance().player, stack)) {
                    if (MinecraftClient.getInstance().currentScreen != null) {
                        MinecraftClient.getInstance().currentScreen.close();
                    }
                    return false;
                }
                return slot.canInsert(stack);
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return slot.canTakeItems(playerEntity);
            }

            @Override
            public Pair<Identifier, Identifier> getBackgroundSprite() {
                return slot.getBackgroundSprite();
            }
        };
    }
}
