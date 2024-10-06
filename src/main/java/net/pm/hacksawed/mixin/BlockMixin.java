package net.pm.hacksawed.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.pm.hacksawed.HacksawedConfig;
import net.pm.hacksawed.particle.HacksawedParticles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {

	//Sparks
	@Shadow public abstract BlockState getDefaultState();

	@Inject(method = "spawnBreakParticles(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V", at = @At(value= "TAIL"))
	public void addSparks(World world, PlayerEntity player, BlockPos pos, BlockState state, CallbackInfo ci) {
		if (!player.canHarvest(this.getDefaultState()) && HacksawedConfig.blocksReleaseSparks) {
			Random r = Random.createLocal();
			for(int i = 0; i < 20; i++) {
				double d = pos.getX() + r.nextFloat();
				double e = pos.getY() + r.nextFloat();
				double f = pos.getZ() + r.nextFloat();
				world.addParticle(HacksawedParticles.SPARK, d, e, f, (r.nextFloat() - 0.5) * 1, (r.nextFloat() - 0.5) * 1, (r.nextFloat() - 0.5) * 1);
			}
		}
	}

}