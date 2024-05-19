package net.paulm.hacksaw.item;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class HacksawItemTags {
    public static final TagKey<Item> FIREPROOF = TagKey.of(RegistryKeys.ITEM, new Identifier("hacksaw", "fireproof"));

    public static void  registerModTags() {

    }
}
