package net.pm.hacksawed.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
	@Shadow
	public World getWorld() {return null;}

    @Shadow
    public double getX() {return 0;}
	@Shadow
	public double getY() {return 0;}
	@Shadow
	public double getZ() {return 0;}

    @Inject(method = "isImmuneToExplosion", at = @At(value="RETURN"), cancellable = true)
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue());
	}

	@Inject(method = "Lnet/minecraft/entity/Entity;canExplosionDestroyBlock(Lnet/minecraft/world/explosion/Explosion;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;F)Z", at = @At(value="RETURN"), cancellable = true)
	public void canExplosionBreakBlocks(CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue());
	}

}