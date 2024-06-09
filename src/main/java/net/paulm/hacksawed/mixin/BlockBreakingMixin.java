package net.paulm.hacksawed.mixin;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.particle.HacksawedParticles;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class BlockBreakingMixin {

    @Shadow @Nullable public ClientPlayerEntity player;

    @Shadow @Nullable public HitResult crosshairTarget;

    @Shadow @Nullable public ClientWorld world;

    @Inject(method = "handleBlockBreaking(Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleManager;addBlockBreakingParticles(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)V"))
    private void addSparks(boolean breaking, CallbackInfo ci) {
        if (HacksawedConfig.blocksReleaseSparks) {
            BlockPos pos = ((BlockHitResult) this.crosshairTarget).getBlockPos();
            Direction direction = ((BlockHitResult) this.crosshairTarget).getSide();
            BlockState blockState = this.world.getBlockState(pos);
            if (!this.player.canHarvest(blockState)) {
                if (blockState.getRenderType() == BlockRenderType.INVISIBLE) {
                    return;
                }
                Random random = Random.createLocal();
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                Box box = blockState.getOutlineShape(this.world, pos).getBoundingBox();
                double g = (double) i + random.nextDouble() * (box.maxX - box.minX - (double) 0.2f) + (double) 0.1f + box.minX;
                double e = (double) j + random.nextDouble() * (box.maxY - box.minY - (double) 0.2f) + (double) 0.1f + box.minY;
                double f = (double) k + random.nextDouble() * (box.maxZ - box.minZ - (double) 0.2f) + (double) 0.1f + box.minZ;

                double baseVel = 0.05;
                double dirVel = 0.1;
                double m = baseVel - (random.nextDouble() * 2 * baseVel);
                double n = baseVel - (random.nextDouble() * 2 * baseVel);
                double o = baseVel - (random.nextDouble() * 2 * baseVel);
                if (direction == Direction.DOWN) {
                    e = (double) j + box.minY - (double) 0.1f;
                    n -= dirVel;
                }
                if (direction == Direction.UP) {
                    e = (double) j + box.maxY + (double) 0.1f;
                    n += dirVel;
                }
                if (direction == Direction.NORTH) {
                    f = (double) k + box.minZ - (double) 0.1f;
                    o -= dirVel;
                }
                if (direction == Direction.SOUTH) {
                    f = (double) k + box.maxZ + (double) 0.1f;
                    o += dirVel;
                }
                if (direction == Direction.WEST) {
                    g = (double) i + box.minX - (double) 0.1f;
                    m -= dirVel;
                }
                if (direction == Direction.EAST) {
                    g = (double) i + box.maxX + (double) 0.1f;
                    m += dirVel;
                }
                world.addParticle(HacksawedParticles.SPARK, g, e, f, m, n, o);
            }
        }
    }
}
