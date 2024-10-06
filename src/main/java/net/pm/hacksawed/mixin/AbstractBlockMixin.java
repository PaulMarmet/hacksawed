package net.pm.hacksawed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "getCollisionShape", at = @At(value="RETURN"), cancellable = true)
	public void modifyShape(CallbackInfoReturnable<VoxelShape> cir, @Local ShapeContext context) {
		cir.setReturnValue(cir.getReturnValue());
	}

	@Inject(method = "hasRandomTicks", at = @At(value = "RETURN"), cancellable = true)
	public void hasRandomTick(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(cir.getReturnValue());
	}

	@Inject(method = "randomTick", at = @At(value = "HEAD"))
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {

	}

}