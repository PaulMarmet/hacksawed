package net.paulm.hacksaw.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class ImmuneExplosionMixin {

	@Inject(method = "Lnet/minecraft/entity/Entity;isImmuneToExplosion()Z", at = @At(value="RETURN"), cancellable = true)
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {

	}

}