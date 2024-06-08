package net.paulm.hacksawed.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

import java.util.Optional;

public class HacksawedEnchantments {

    public static Enchantment RECOIL = Registry.register(Registries.ENCHANTMENT, new Identifier(Hacksawed.MOD_ID, "recoil"), new Enchantment(new Enchantment.Properties(ItemTags.CROSSBOW_ENCHANTABLE, Optional.ofNullable(ItemTags.CROSSBOW_ENCHANTABLE), 2, 3, Enchantment.leveledCost(15, 5), Enchantment.leveledCost(30, 10), 3, FeatureSet.empty(), new EquipmentSlot[]{EquipmentSlot.MAINHAND})));
    //Registries.ENCHANTMENT.get(new Identifier(Hacksawed.MOD_ID, "recoil"));
    public static void registerHacksawEnchantments() {

    }
}
