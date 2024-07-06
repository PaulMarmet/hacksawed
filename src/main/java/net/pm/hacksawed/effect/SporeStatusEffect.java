package net.pm.hacksawed.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.math.Vec3d;
import net.pm.hacksawed.damage.HacksawedDamageTypes;

import java.util.Objects;


public class SporeStatusEffect extends StatusEffect {
    protected SporeStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0x2A8533);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return (duration % 100 == 0 || duration == 1);
    }

    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(entity.getDamageSources().create(HacksawedDamageTypes.SPORED), 1.0F);
        if (entity.getStatusEffect(HacksawedEffects.SPORES) != null && Objects.requireNonNull(entity.getStatusEffect(HacksawedEffects.SPORES)).isDurationBelow(20)) {
            spawnCreeperling(entity);
        }
        return true;
    }

    public void spawnCreeperling(LivingEntity entity) {
        if (!entity.getWorld().isClient) {
            CreeperEntity creeperling = EntityType.CREEPER.create(entity.getWorld());
            if (creeperling != null) {
                creeperling.setPos(entity.getPos().x, entity.getPos().y, entity.getPos().z);
                creeperling.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), 0.0F);
                Vec3d newVel = entity.getVelocity();
                creeperling.setVelocity(newVel);
                entity.getWorld().spawnEntity(creeperling);
            }
        }
    }
}
