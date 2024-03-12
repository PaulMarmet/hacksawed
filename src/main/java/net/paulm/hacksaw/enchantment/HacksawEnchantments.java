package net.paulm.hacksaw.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksaw.Hacksaw;

public class HacksawEnchantments {

    public static Enchantment RECOIL = register("recoil", new RecoilEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.CROSSBOW, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(Hacksaw.MOD_ID, name), enchantment);
    }
    public static void registerHacksawEnchantments() {

    }
}
