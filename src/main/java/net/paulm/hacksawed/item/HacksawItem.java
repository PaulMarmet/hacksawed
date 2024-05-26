package net.paulm.hacksawed.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.effect.BleedingStatusEffect;

public class HacksawItem extends AxeItem {
    public HacksawItem(float attackDamage, float attackSpeed, Settings settings) {
        super(HacksawMaterial.HACKSAW, attackDamage, attackSpeed, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        BleedingStatusEffect.applyEffect(target, attacker, HacksawedConfig.initBleedTime, HacksawedConfig.continuationBleedTime);
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
        return true;
    }

}
