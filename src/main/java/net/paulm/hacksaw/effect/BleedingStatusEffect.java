package net.paulm.hacksaw.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.paulm.hacksaw.damage.HacksawDamageTypes;


public class BleedingStatusEffect extends StatusEffect {
    protected BleedingStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x9B0417);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 20 >> (amplifier - 1);
        if (i > 0) {
            return duration % i == 0;
        }
        return true;
    }

    public static void applyEffect(LivingEntity target, LivingEntity attacker, int initBleedDuration, int contBleedDuration, int bleedAmpDuration) {
        StatusEffectInstance effect;
        //If the target already has the effect, add to the effect
        if(target.hasStatusEffect(HacksawEffects.BLEEDING)) {
            effect = target.getStatusEffect(HacksawEffects.BLEEDING);
            //If the effect's length is above the max point
            if (effect.getDuration() > bleedAmpDuration) {
                //add the time, then cut it in half and increase the effect level by one
                effect.upgrade(new StatusEffectInstance(HacksawEffects.BLEEDING, (effect.getDuration()+contBleedDuration)/2, effect.getAmplifier()+1));
            }
            //Otherwise just add the time
            else {
                //add the time(divided by the effect power)
                effect.upgrade(new StatusEffectInstance(HacksawEffects.BLEEDING, (effect.getDuration() + (contBleedDuration/ (int) (Math.pow(2, effect.getAmplifier()-1)))), effect.getAmplifier()));
            }
        }
        //Otherwise create a new effect
        else {
            target.addStatusEffect(new StatusEffectInstance(HacksawEffects.BLEEDING, initBleedDuration, 1), attacker);
        }
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        //Damage and add particle
        entity.damage(entity.getDamageSources().create(HacksawDamageTypes.BLEEDING), 1.0f);
        entity.getWorld().addParticle(ParticleTypes.DRIPPING_HONEY, entity.getX(), entity.getY(), entity.getZ(), 0f, 0f, 0f);
    }
}
