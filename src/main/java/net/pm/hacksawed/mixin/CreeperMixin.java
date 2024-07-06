package net.pm.hacksawed.mixin;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.pm.hacksawed.HacksawedConfig;
import net.pm.hacksawed.effect.HacksawedEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public class CreeperMixin extends EntityMixin{

    @Override
    public void canExplosionBreakBlocks(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!HacksawedConfig.creeperSpores);
    }

    @Inject(method = "spawnEffectsCloud()V", at = @At(value = "TAIL"))
    private void sporeCloud(CallbackInfo ci) {
        if (HacksawedConfig.creeperSpores) {
            AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(getWorld(), getX(), getY(), getZ());
            areaEffectCloudEntity.setRadius(5.0F);
            areaEffectCloudEntity.setRadiusOnUse(-0.5F);
            areaEffectCloudEntity.setWaitTime(10);
            areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration());
            areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());

            areaEffectCloudEntity.addEffect(new StatusEffectInstance(HacksawedEffects.SPORES, 9600));

            this.getWorld().spawnEntity(areaEffectCloudEntity);
        }
    }
}
