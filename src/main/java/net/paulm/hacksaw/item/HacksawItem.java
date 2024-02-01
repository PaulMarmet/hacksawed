package net.paulm.hacksaw.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.paulm.hacksaw.effect.HacksawEffects;

public class HacksawItem extends AxeItem {
    public HacksawItem(float attackDamage, float attackSpeed, Settings settings) {
        super(HacksawMaterial.HACKSAW, attackDamage, attackSpeed, settings);
    }

    public static int initBleedDuration = 60;
    public static int contBleedDuration = 80;
    public static int bleedAmpDuration = 1600;

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        StatusEffectInstance effect;
        if(target.hasStatusEffect(HacksawEffects.BLEEDING)) {
            effect = target.getStatusEffect(HacksawEffects.BLEEDING);
            if (effect.getDuration() > bleedAmpDuration) {
                effect.upgrade(new StatusEffectInstance(HacksawEffects.BLEEDING, (effect.getDuration()+contBleedDuration)/2, effect.getAmplifier()+1));
            }
            effect.upgrade(new StatusEffectInstance(HacksawEffects.BLEEDING, effect.getDuration()+contBleedDuration, effect.getAmplifier()));
        }
        else {
            target.addStatusEffect(new StatusEffectInstance(HacksawEffects.BLEEDING, initBleedDuration, 1), attacker);
        }
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

//    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//        Hacksaw.LOGGER.info("Something shot! Is client?"+world.isClient()+" Is logical side for movement?"+user.isLogicalSideForUpdatingMovement());
//        user.addVelocity(new Vec3d(user.getRotationVec(1.0f).toVector3f()));
//        return TypedActionResult.pass(user.getStackInHand(hand));
//    }
}
