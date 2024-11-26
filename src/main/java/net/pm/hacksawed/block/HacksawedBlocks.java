package net.pm.hacksawed.block;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import net.pm.hacksawed.Hacksawed;

public class HacksawedBlocks {

    public static final Block GLOWSTONE_DUST_BLOCK = registerBlock("glowstone_dust_block", new ColoredFallingBlock(new ColorCode(14406560), AbstractBlock.Settings.copy(Blocks.SAND).luminance((state) -> {return 15;}))); //fix colour code

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Hacksawed.MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, Identifier.of(Hacksawed.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks() {

    }
}
