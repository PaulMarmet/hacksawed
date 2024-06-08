package net.paulm.hacksawed.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.effect.BleedingStatusEffect;

public class HacksawItem extends AxeItem {
    public HacksawItem(float attackDamage, float attackSpeed, Settings settings) {
        super(HacksawMaterial.HACKSAW, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        BleedingStatusEffect.applyEffect(target, attacker, HacksawedConfig.initBleedTime, HacksawedConfig.continuationBleedTime);
        //apparently the item in question has no idea where it is so were just assuming its in the main hand ig
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

}
