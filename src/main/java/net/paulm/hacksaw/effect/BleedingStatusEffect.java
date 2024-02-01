package net.paulm.hacksaw.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
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

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(entity.getDamageSources().create(HacksawDamageTypes.BLEEDING), 1.0f);
    }

//    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
//        super.onRemoved(entity, attributes, amplifier);
//        Hacksaw.LOGGER.info(String.valueOf(entity.hasStatusEffect(HacksawEffects.BLEEDING)));
//        if (amplifier > 1) {
//            entity.addStatusEffect(new StatusEffectInstance(HacksawEffects.BLEEDING, 20, amplifier-1));
//        }
//        entity.damage(entity.getDamageSources().create(HacksawDamageTypes.BLEEDING), 1.0f);
//        Hacksaw.LOGGER.info("Effect ran out with amplifier "+amplifier);
//    }
}
