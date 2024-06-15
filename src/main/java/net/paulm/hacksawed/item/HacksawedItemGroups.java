package net.paulm.hacksawed.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

public class HacksawedItemGroups {
    //Creates the tab in the creative menu
    public static final ItemGroup HACKSAW_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(Hacksawed.MOD_ID, "hacksawed"), FabricItemGroup.builder().displayName(Text.translatable("itemgroup.hacksawed")).icon(() -> new ItemStack((HacksawedItems.HACKSAW))).entries(((displayContext, entries) -> {
        //Now theoretically, they appear in order in the menu depending on the order here
        //But im lazy so all item, then all blocks
        //Here goes all the item:
        entries.add(HacksawedItems.HACKSAW);
        entries.add(HacksawedItems.DYNAMITE_STICK);
        entries.add(HacksawedItems.IMPACT_DYNAMITE_STICK);
        entries.add(HacksawedItems.AUTO_LIGHT_DYNAMITE_STICK);
        entries.add(HacksawedItems.BOUNCY_BALL);
        entries.add(HacksawedItems.RETURNAL_ORB);

    })).build());
    public static void registerItemGroups() {
        //Hacksaw.LOGGER.info("Registering Item Groups for "+Hacksaw.MOD_ID);
    }
}
