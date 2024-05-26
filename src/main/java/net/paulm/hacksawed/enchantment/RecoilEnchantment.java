package net.paulm.hacksawed.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class RecoilEnchantment extends Enchantment {
    public RecoilEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(weight, target, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return 1 + (level) * 20;
    }


    @Override
    public int getMaxLevel() {
        return 2;
    }
}
