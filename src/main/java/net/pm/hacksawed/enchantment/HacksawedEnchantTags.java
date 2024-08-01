package net.pm.hacksawed.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.pm.hacksawed.Hacksawed;

public class HacksawedEnchantTags {
    public static final TagKey<Enchantment> FIRE_RELATED = TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Hacksawed.MOD_ID, "fire_related"));
    public static final TagKey<Enchantment> EXPLOSION_RELATED = TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Hacksawed.MOD_ID, "explosion_related"));
}
