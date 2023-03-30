package com.diskree.achievetodo.mixins;

import com.diskree.achievetodo.AchieveToDoMod;
import com.diskree.achievetodo.BlockedAction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.Brightness;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.world.World;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.text.Style;
import net.minecraft.text.TextColor;

@Mixin(ShieldItem.class)
public class ShieldItemMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void useInject(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        DisplayEntity.TextDisplayEntity lightning = new DisplayEntity.TextDisplayEntity(EntityType.TEXT_DISPLAY, world);
        lightning.setPosition(user.getPos());
        lightning.setText(Text.of("Jokerge").getWithStyle(Style.EMPTY.withColor(TextColor.parse("#FFFF00"))).get(0));
        lightning.setBrightness(Brightness.FULL);
        lightning.setTransformation(new AffineTransformation(null, null, new Vector3f(10.0f, 10.0f, 10.0f), null));
        world.spawnEntity(lightning);
        if (AchieveToDoMod.isActionBlocked(BlockedAction.USING_SHIELD)) {
            cir.setReturnValue(TypedActionResult.fail(user.getStackInHand(hand)));
        }
    }
}
