package net.paulm.hacksaw.mixin;

import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemImmuneExplosionMixin extends ImmuneExplosionMixin{

	@Override
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}

}