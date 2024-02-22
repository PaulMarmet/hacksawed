package net.paulm.hacksaw.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.paulm.hacksaw.Hacksaw;

public class HacksawItemGroups {
    //Creates the tab in the creative menu
    public static final ItemGroup HACKSAW_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Hacksaw.MOD_ID, "test_item"), FabricItemGroup.builder().displayName(Text.translatable("itemgroup.hacksaw")).icon(() -> new ItemStack((HacksawItems.HACKSAW))).entries(((displayContext, entries) -> {
        //Now theoretically, they appear in order in the menu depending on the order here
        //But im lazy so all items, then all blocks
        //Here goes all the items:
        entries.add(HacksawItems.HACKSAW);
        entries.add(HacksawItems.DYNAMITE_STICK);
        entries.add(HacksawItems.IMPACT_DYNAMITE_STICK);

    })).build());
    public static void registerItemGroups() {
        //Hacksaw.LOGGER.info("Registering Item Groups for "+Hacksaw.MOD_ID);
    }
}
