package net.pm.hacksawed.item;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.pm.hacksawed.Hacksawed;

public class HacksawedItemTags {
    public static final TagKey<Item> FIREPROOF = TagKey.of(RegistryKeys.ITEM, Identifier.of(Hacksawed.MOD_ID, "fire_proof"));
    public static final TagKey<Item> EXPLOSION_PROOF = TagKey.of(RegistryKeys.ITEM, Identifier.of(Hacksawed.MOD_ID, "explosion_proof"));
    public static final TagKey<Item> ENDERMAN_SAFE = TagKey.of(RegistryKeys.ITEM, Identifier.of(Hacksawed.MOD_ID, "enderman_safe"));

    public static void  registerModTags() {

    }
}
