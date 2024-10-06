package net.pm.hacksawed.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "isImmuneToExplosion", at = @At(value="RETURN"), cancellable = true)
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue());
	}

}