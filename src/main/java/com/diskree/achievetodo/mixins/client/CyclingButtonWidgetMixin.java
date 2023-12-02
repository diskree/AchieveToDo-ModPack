package com.diskree.achievetodo.mixins.client;

import com.diskree.achievetodo.BuildConfig;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.world.WorldCreator;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;

@Mixin(CyclingButtonWidget.Builder.class)
public abstract class CyclingButtonWidgetMixin {

    @Shadow
    public abstract CyclingButtonWidget.Builder<Object> values(CyclingButtonWidget.Values<Object> values);

    @Inject(method = "values(Ljava/util/Collection;)Lnet/minecraft/client/gui/widget/CyclingButtonWidget$Builder;", at = @At("HEAD"), cancellable = true)
    private void valuesInject(Collection<Object> values, CallbackInfoReturnable<CyclingButtonWidget.Builder<Object>> cir) {
        if (!BuildConfig.DEBUG && values != null) {
            ImmutableList<Object> list = ImmutableList.copyOf(values);
            if (!list.isEmpty()) {
                Object firstValue = list.get(0);
                if (firstValue instanceof WorldCreator.GameMode || firstValue instanceof Difficulty) {
                    Collection<Object> filteredList = new ArrayList<>();
                    for (Object value : values) {
                        if (value == WorldCreator.GameMode.CREATIVE || value == Difficulty.PEACEFUL) {
                            continue;
                        }
                        filteredList.add(value);
                    }
                    values(CyclingButtonWidget.Values.of(ImmutableList.copyOf(filteredList)));
                    //noinspection unchecked
                    CyclingButtonWidget.Builder<Object> builder = (CyclingButtonWidget.Builder<Object>) (Object) this;
                    cir.setReturnValue(builder);
                }
            }
        }
    }
}
