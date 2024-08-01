package net.pm.hacksawed.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pm.hacksawed.Hacksawed;
import net.pm.hacksawed.component.HacksawedComponents;

public class HacksawedItems {
    //This is where you place the item
    //Dont forget to add to the ItemGroups
    //And add to the lang file
    //And to the dataGen
    //And add the texture
    public static final Item HACKSAW = registerItem("hacksaw", new HacksawItem(1.5f, -2.0f, new Item.Settings()));

    public static final Item DYNAMITE_STICK = registerItem("dynamite_stick", new DynamiteItem(new Item.Settings().maxCount(16).component(HacksawedComponents.EXPLOSION_TIME, 0L)));
    public static final Item IMPACT_DYNAMITE_STICK = registerItem("impact_dynamite_stick", new ImpactDynamiteItem(new Item.Settings().maxCount(16).component(HacksawedComponents.CAN_ALWAYS_THROW, true).component(HacksawedComponents.EXPLOSION_TIME, 0L)));
    public static final Item AUTO_LIGHT_DYNAMITE_STICK = registerItem("auto_light_dynamite_stick", new DynamiteItem(new Item.Settings().maxCount(16).component(HacksawedComponents.CAN_ALWAYS_THROW, true).component(HacksawedComponents.EXPLOSION_TIME, 0L)));

    public static final Item BOUNCY_BALL = registerItem("bouncy_ball", new BouncyBallItem(new Item.Settings().maxCount(8)));
    public static final Item RETURNAL_ORB = registerItem("returnal_orb", new ReturnalOrbItem(new Item.Settings().maxCount(8)));

    //This registers the item, no need to touch
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Hacksawed.MOD_ID, name), item);
    }
    public static void  registerModItems() {
        //Hacksaw.LOGGER.info("Registering item for "+Hacksaw.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
