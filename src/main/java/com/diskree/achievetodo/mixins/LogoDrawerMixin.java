package com.diskree.achievetodo.mixins;

import net.minecraft.client.gui.LogoDrawer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LogoDrawer.class)
public class LogoDrawerMixin {

    @ModifyConstant(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", constant = @Constant(intValue = 128, ordinal = 0))
    private int modifyX(int x) {
        return 159;
    }

    @ModifyConstant(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", constant = @Constant(intValue = 256))
    private int modifyWidth(int orig) {
        return 318;
    }

}
