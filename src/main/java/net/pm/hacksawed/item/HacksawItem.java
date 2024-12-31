package net.pm.hacksawed.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.pm.hacksawed.HacksawedConfig;
import net.pm.hacksawed.effect.BleedingStatusEffect;

public class HacksawItem extends AxeItem {
    public HacksawItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings);
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        BleedingStatusEffect.applyEffect(target, attacker, HacksawedConfig.initBleedTime, HacksawedConfig.continuationBleedTime);
        //apparently the item in question has no idea where it is so were just assuming its in the main hand ig
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        return true;
    }

}
