package net.paulm.hacksaw.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.paulm.hacksaw.HacksawConfig;
import net.paulm.hacksaw.item.HacksawItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemImmuneExplosionMixin extends ImmuneExplosionMixin{

	@Shadow public abstract ItemStack getStack();

	@Override
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {
		if (HacksawConfig.explosionProofItems == HacksawConfig.SelectionType.ALL) {
			cir.setReturnValue(true);
		} else if (HacksawConfig.explosionProofItems == HacksawConfig.SelectionType.SOME) {
			cir.setReturnValue(cir.getReturnValue() || this.getStack().isIn(HacksawItemTags.EXPLOSION_PROOF));
		}
	}

}