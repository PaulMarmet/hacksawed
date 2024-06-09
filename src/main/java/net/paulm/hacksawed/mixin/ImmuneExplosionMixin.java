package net.paulm.hacksawed.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class ImmuneExplosionMixin {

	@Inject(method = "isImmuneToExplosion", at = @At(value="RETURN"))
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {

	}

}