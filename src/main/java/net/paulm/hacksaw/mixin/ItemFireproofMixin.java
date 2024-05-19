package net.paulm.hacksaw.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.paulm.hacksaw.HacksawConfig;
import net.paulm.hacksaw.item.HacksawItemTags;
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
		if (HacksawConfig.fireProofItems == HacksawConfig.SelectionType.ALL) {
			cir.setReturnValue(true);
		} else if (HacksawConfig.fireProofItems == HacksawConfig.SelectionType.SOME) {
			cir.setReturnValue(cir.getReturnValue() || this.getStack().isIn(HacksawItemTags.FIREPROOF));
		}
	}

}