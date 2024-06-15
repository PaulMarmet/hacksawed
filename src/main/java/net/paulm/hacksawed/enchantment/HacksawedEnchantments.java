package net.paulm.hacksawed.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

public class HacksawedEnchantments {

    public static Enchantment RECOIL;
    //Registries.ENCHANTMENT.get(new Identifier(Hacksawed.MOD_ID, "recoil"));

    public static void registerHacksawEnchantments() {
        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(Hacksawed.MOD_ID, "recoil"), RecoilEnchantmentEffect.CODEC);
    }
}
