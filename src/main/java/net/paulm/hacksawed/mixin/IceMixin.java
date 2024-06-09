package net.paulm.hacksawed.mixin;

import net.minecraft.block.*;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.paulm.hacksawed.HacksawedConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IceBlock.class)
public abstract class IceMixin {
    @Shadow protected abstract void melt(BlockState state, World world, BlockPos pos);

    @Redirect(method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/IceBlock;melt(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"))
    private void randomTick(IceBlock instance, BlockState state, World world, BlockPos pos) {
        if (!world.getBiome(pos).isIn(BiomeTags.SPAWNS_COLD_VARIANT_FROGS) || !HacksawedConfig.iceAndSnowDontMelt) {
            this.melt(state, world, pos);
        }
    }

}
