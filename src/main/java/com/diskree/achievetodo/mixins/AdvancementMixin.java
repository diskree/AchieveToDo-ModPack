package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.advancement.Advancement;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Mixin(Advancement.class)
public class AdvancementMixin {

    @Shadow @Final private Identifier id;

    @Shadow @Final private Set<Advancement> children;

    @Inject(method = "getRequirementCount", at = @At("HEAD"), cancellable = true)
    public void getRequirementCountInject(CallbackInfoReturnable<Integer> cir) {
        BlockedAction action = AchieveToDoMod.getBlockedActionFromAdvancement((Advancement) (Object) this);
        if (action != null) {
            cir.setReturnValue(action.getUnblockAdvancementsCount());
        }
    }

    @Inject(method = "getChildren", at = @At("HEAD"), cancellable = true)
    public void getChildrenInject(CallbackInfoReturnable<Iterable<Advancement>> cir) {
        if (id.getNamespace().equals(AchieveToDoMod.ID) && id.getPath().endsWith("/root")) {
            HashMap<String, Integer> order = new HashMap<>();
            order.put("action/eat_salmon", 0);
            order.put("action/eat_rabbit", 1);
            order.put("action/eat_melon_slice", 2);
            order.put("action/eat_cooked_cod", 3);
            order.put("action/jump", 4);
            order.put("action/using_crafting_table", 5);
            order.put("action/using_stone_tools", 6);
            order.put("action/villager_mason", 7);
            order.put("hints/hintly_hallows", 8);

            List<Advancement> childrenList = new ArrayList<>(children);
            childrenList.sort((a1, a2) -> {
                Integer order1 = order.get(a1.getId().getPath());
                Integer order2 = order.get(a2.getId().getPath());
                if (order1 == null || order2 == null) {
                    return 0;
                }
                return order1.compareTo(order2);
            });
            cir.setReturnValue(childrenList);
        }
    }
}
