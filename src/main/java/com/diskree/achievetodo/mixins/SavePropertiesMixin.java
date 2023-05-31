package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.SavePropertiesImpl;
import net.minecraft.world.SaveProperties;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SaveProperties.class)
public interface SavePropertiesMixin extends SavePropertiesImpl {
}
