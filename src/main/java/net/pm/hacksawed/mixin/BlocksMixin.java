package net.pm.hacksawed.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.CaveVinesBodyBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.pm.hacksawed.HacksawedConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class BlocksMixin {
    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value="CONSTANT", args="stringValue=torchflower", ordinal = 0)), at = @At(value = "NEW", target = "Lnet/minecraft/block/FlowerBlock;", ordinal = 0))
    private static FlowerBlock makeNewTorchflower(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, AbstractBlock.Settings settings) {
        if (HacksawedConfig.glowingTorchflower) return new FlowerBlock(stewEffect, effectLengthInSeconds, settings.luminance(state -> 15));
        return new FlowerBlock(stewEffect, effectLengthInSeconds, settings);
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value="CONSTANT", args="stringValue=cave_vines_plant", ordinal = 0)), at = @At(value = "NEW", target = "(Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/CaveVinesBodyBlock;", ordinal = 0))
    private static CaveVinesBodyBlock makeCaveVineTicker(AbstractBlock.Settings settings) {
        if (HacksawedConfig.regrowCaveVines) return new CaveVinesBodyBlock(settings.ticksRandomly());
        return new CaveVinesBodyBlock(settings);
    }

}
