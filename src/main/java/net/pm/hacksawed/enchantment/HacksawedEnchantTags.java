package net.pm.hacksawed.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class HacksawedEnchantTags {
    public static final TagKey<Enchantment> FIRE_RELATED = TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("hacksawed", "fire_related"));
    public static final TagKey<Enchantment> EXPLOSION_RELATED = TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("hacksawed", "explosion_related"));
}
