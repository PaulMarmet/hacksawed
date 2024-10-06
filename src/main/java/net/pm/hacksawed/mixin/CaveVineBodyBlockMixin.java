package net.pm.hacksawed.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CaveVinesBodyBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.pm.hacksawed.HacksawedConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.block.CaveVines.BERRIES;

@Mixin(CaveVinesBodyBlock.class)
public abstract class CaveVineBodyBlockMixin extends AbstractBlockMixin {

    @Shadow public abstract void grow(ServerWorld world, Random random, BlockPos pos, BlockState state);

    @Override
    public void hasRandomTick(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (HacksawedConfig.regrowCaveVines) cir.setReturnValue(!((Boolean)state.get(BERRIES)));
        cir.setReturnValue(cir.getReturnValue());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (HacksawedConfig.regrowCaveVines) {
            boolean berriesAbove = berriesAtBlock(world, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
            boolean berriesBelow = berriesAtBlock(world, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
            if (!(berriesAbove && berriesBelow)) grow(world, random, pos, state);
        }
    }

    @Unique
    public boolean berriesAtBlock(ServerWorld world, BlockPos pos) {
        return ((world.getBlockState(pos)).getBlock() == Blocks.CAVE_VINES || ((world.getBlockState(pos)).getBlock() == Blocks.CAVE_VINES_PLANT)) && world.getBlockState(pos).get(BERRIES);
    }
}
