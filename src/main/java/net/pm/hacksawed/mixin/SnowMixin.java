package net.pm.hacksawed.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pm.hacksawed.HacksawedConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(SnowBlock.class)
public abstract class SnowMixin {

    @Redirect(method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/SnowBlock;dropStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)V"))
    private void dontMelt(BlockState blockState, World world, BlockPos blockPos) {
        if (!world.getBiome(blockPos).isIn(BiomeTags.SPAWNS_COLD_VARIANT_FROGS) || !HacksawedConfig.iceAndSnowDontMelt) {
            SnowBlock.dropStacks(blockState, world, blockPos);
        }
    }
    @Redirect(method = "randomTick(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/random/Random;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;removeBlock(Lnet/minecraft/util/math/BlockPos;Z)Z"))
    private boolean dontMelt2(ServerWorld instance, BlockPos blockPos, boolean b) {
        if (!instance.getBiome(blockPos).isIn(BiomeTags.SPAWNS_COLD_VARIANT_FROGS) || !HacksawedConfig.iceAndSnowDontMelt) {
            return instance.removeBlock(blockPos, false);
        }
        return b;
    }

}
