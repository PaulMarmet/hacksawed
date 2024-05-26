package net.paulm.hacksawed.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.item.HacksawedItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemImmuneExplosionMixin extends ImmuneExplosionMixin{

	@Shadow public abstract ItemStack getStack();

	@Override
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {
		if (HacksawedConfig.explosionProofItems == HacksawedConfig.SelectionType.ALL) {
			cir.setReturnValue(true);
		} else if (HacksawedConfig.explosionProofItems == HacksawedConfig.SelectionType.SOME) {
			cir.setReturnValue(cir.getReturnValue() || this.getStack().isIn(HacksawedItemTags.EXPLOSION_PROOF));
		}
	}

}