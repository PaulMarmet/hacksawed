package net.pm.hacksawed.mixin;

import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.pm.hacksawed.HacksawedConfig;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContentsComponent.class)
public class BundleContentMixin {
    @Inject(method = "Lnet/minecraft/component/type/BundleContentsComponent;getOccupancy(Lnet/minecraft/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;", at = @At(value = "RETURN"), cancellable = true)
    private static void lowerMax(ItemStack stack, CallbackInfoReturnable<Fraction> cir) {
        if ((Fraction.getFraction(1, HacksawedConfig.bundleMaxSpaceCost).compareTo(cir.getReturnValue()) < 0) && !stack.isOf(Items.BUNDLE) && HacksawedConfig.bundleChanges) {
            cir.setReturnValue(Fraction.getFraction(1, HacksawedConfig.bundleMaxSpaceCost));
        }
    }
}
