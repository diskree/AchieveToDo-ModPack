package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDo;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlacedAdvancement;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Mixin(PlacedAdvancement.class)
public abstract class PlacedAdvancementMixin {

    @Shadow
    public abstract AdvancementEntry getAdvancementEntry();

    @Shadow
    @Final
    private Set<PlacedAdvancement> children;

    @Inject(method = "getChildren", at = @At("HEAD"), cancellable = true)
    public void getChildrenInject(CallbackInfoReturnable<Iterable<PlacedAdvancement>> cir) {
        Identifier id = getAdvancementEntry().id();
        if (id.getNamespace().equals(AchieveToDo.ID) && id.getPath().endsWith("/root")) {
            List<String> order = new ArrayList<>(Arrays.asList(
                    "action/eat_salmon",
                    "action/eat_rabbit",
                    "action/eat_melon_slice",
                    "action/eat_cooked_cod",
                    "action/jump",
                    "action/using_crafting_table",
                    "action/using_stone_tools",
                    "action/villager_mason",
                    "hints/hintly_hallows"
            ));
            List<PlacedAdvancement> childrenList = new ArrayList<>(children);
            childrenList.sort((a1, a2) -> {
                Integer order1 = order.indexOf(a1.getAdvancementEntry().id().getPath());
                Integer order2 = order.indexOf(a2.getAdvancementEntry().id().getPath());
                return order1.compareTo(order2);
            });
            cir.setReturnValue(childrenList);
        }
    }
}
