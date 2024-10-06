package net.pm.hacksawed.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pm.hacksawed.Hacksawed;
import net.pm.hacksawed.HacksawedConfig;

public class HacksawedItemGroups {
    public static final ItemGroup HACKSAW_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(Hacksawed.MOD_ID, "hacksawed"), FabricItemGroup.builder().displayName(Text.translatable("itemgroup.hacksawed")).icon(() -> new ItemStack((HacksawedItems.HACKSAW))).entries(((displayContext, entries) -> {
        if (HacksawedConfig.hacksawEnabled) entries.add(HacksawedItems.HACKSAW);
        if (HacksawedConfig.dynamiteEnabled) {
            entries.add(HacksawedItems.DYNAMITE_STICK);
            entries.add(HacksawedItems.AUTO_LIGHT_DYNAMITE_STICK);
            if (HacksawedConfig.impactDynamite) entries.add(HacksawedItems.IMPACT_DYNAMITE_STICK);
        }
        if (HacksawedConfig.orbsEnabled) {
            entries.add(HacksawedItems.BOUNCY_BALL);
            entries.add(HacksawedItems.RETURNAL_ORB);
        }

    })).build());
    public static void registerItemGroups() {
    }
}
