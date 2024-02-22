package net.paulm.hacksaw.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksaw.Hacksaw;

public class HacksawItems {
    //This is where you place the items
    //Dont forget to add to the ItemGroups
    //And add to the lang file
    //And to the dataGen
    //And add the texture
    public static final Item HACKSAW = registerItem("hacksaw", new HacksawItem(1.5f, 1.0f, new Item.Settings()));
    public static final Item DYNAMITE_STICK = registerItem("dynamite_stick", new DynamiteItem(new FabricItemSettings()));
    public static final Item IMPACT_DYNAMITE_STICK = registerItem("impact_dynamite_stick", new ImpactDynamiteItem(new FabricItemSettings()));

    //This registers the items, no need to touch
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Hacksaw.MOD_ID, name), item);
    }
    public static void  registerModItems() {
        //Hacksaw.LOGGER.info("Registering items for "+Hacksaw.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
