package net.paulm.hacksawed.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class TorchflowerMixin {
    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value="CONSTANT", args="stringValue=torchflower", ordinal = 0)), at = @At(value = "NEW", target = "Lnet/minecraft/block/FlowerBlock;", ordinal = 0))
    private static FlowerBlock makeTheNewVersion(RegistryEntry<StatusEffect> stewEffect, float effectLengthInSeconds, AbstractBlock.Settings settings) {
        return new FlowerBlock(stewEffect, effectLengthInSeconds, settings.luminance(state -> 15));
    }

}
