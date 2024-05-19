package net.paulm.hacksaw.mixin;

import net.minecraft.entity.ItemEntity;
import net.paulm.hacksaw.HacksawConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemImmuneExplosionMixin extends ImmuneExplosionMixin{

	@Override
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {
		if (HacksawConfig.explosionProofItems == HacksawConfig.SelectionType.ALL) {
			cir.setReturnValue(true);
		} else if (HacksawConfig.explosionProofItems == HacksawConfig.SelectionType.SOME) {
			cir.setReturnValue(true);
		}
	}

}