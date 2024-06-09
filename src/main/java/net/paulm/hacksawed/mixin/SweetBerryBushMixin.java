package net.paulm.hacksawed.mixin;

import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.paulm.hacksawed.HacksawedConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SweetBerryBushBlock.class)
public abstract class SweetBerryBushMixin {

    @Redirect(method = "onEntityCollision(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    private boolean damage(Entity instance, DamageSource source, float amount) {
        if (!instance.isSneaking() || !HacksawedConfig.sneakSweetBerries) {
            return instance.damage(source, 1.0F);
        }
        return false;
    }

}
