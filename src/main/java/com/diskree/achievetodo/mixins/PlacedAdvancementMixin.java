package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.action.IGeneratedAdvancement;
import com.diskree.achievetodo.advancements.AdvancementsTab;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlacedAdvancement;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
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
        String namespace = getAdvancementEntry().id().getPath();
        for (AdvancementsTab root : AdvancementsTab.values()) {
            if (root.getRootAdvancementPath().equals(namespace.toLowerCase()) && root.children != null) {
                List<String> advancementPaths = new ArrayList<>();
                for (List<IGeneratedAdvancement> row : root.children) {
                    advancementPaths.add(root.getAdvancementPath(row.get(0)));
                }
                List<PlacedAdvancement> childrenList = new ArrayList<>(children);
                childrenList.sort((a1, a2) -> {
                    Integer order1 = advancementPaths.indexOf(a1.getAdvancementEntry().id().getPath());
                    Integer order2 = advancementPaths.indexOf(a2.getAdvancementEntry().id().getPath());
                    return order1.compareTo(order2);
                });
                cir.setReturnValue(childrenList);
                break;
            }
        }
    }
}
