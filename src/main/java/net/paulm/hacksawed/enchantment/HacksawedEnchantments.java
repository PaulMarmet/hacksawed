package net.paulm.hacksawed.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

public class HacksawedEnchantments {

    public static Enchantment RECOIL = register("recoil", new RecoilEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.CROSSBOW, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(Hacksawed.MOD_ID, name), enchantment);
    }
    public static void registerHacksawEnchantments() {

    }
}
