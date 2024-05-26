package net.paulm.hacksawed.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.item.HacksawedItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemFireproofMixin {

	@Shadow public abstract ItemStack getStack();

	@Inject(method = "Lnet/minecraft/entity/ItemEntity;isFireImmune()Z", at = @At(value="RETURN"), cancellable = true)
	public void isImmuneToFire(CallbackInfoReturnable<Boolean> cir) {
		if (HacksawedConfig.fireProofItems == HacksawedConfig.SelectionType.ALL) {
			cir.setReturnValue(true);
		} else if (HacksawedConfig.fireProofItems == HacksawedConfig.SelectionType.SOME) {
			cir.setReturnValue(cir.getReturnValue() || this.getStack().isIn(HacksawedItemTags.FIREPROOF));
		}
	}

}