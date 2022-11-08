package com.diskree.achievetodo.mixin;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "getPreferredEquipmentSlot", at = @At("HEAD"), cancellable = true)
    private static void getPreferredEquipmentSlotInject(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if (stack.getHolder() == null) {
            if (stack.isOf(Items.ELYTRA) && AchieveToDoMod.isActionBlocked(BlockedAction.equip_elytra)) {
                cir.setReturnValue(EquipmentSlot.MAINHAND);
            } else {
                Item item = stack.getItem();
                if (item instanceof ArmorItem) {
                    ArmorMaterial armorMaterial = ((ArmorItem) item).getMaterial();
                    if (armorMaterial == ArmorMaterials.IRON && AchieveToDoMod.isActionBlocked(BlockedAction.equip_iron_armor) ||
                            armorMaterial == ArmorMaterials.DIAMOND && AchieveToDoMod.isActionBlocked(BlockedAction.equip_diamond_armor) ||
                            armorMaterial == ArmorMaterials.NETHERITE && AchieveToDoMod.isActionBlocked(BlockedAction.equip_netherite_armor)) {
                        cir.setReturnValue(EquipmentSlot.MAINHAND);
                    }
                }
            }
        }
    }
}
