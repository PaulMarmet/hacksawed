package net.pm.hacksawed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.pm.hacksawed.item.HacksawedItems;

public class HacksawedModelProvider extends FabricModelProvider {
    public HacksawedModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(HacksawedItems.HACKSAW, Models.GENERATED);
        itemModelGenerator.register(HacksawedItems.DYNAMITE_STICK, Models.GENERATED);
        itemModelGenerator.register(HacksawedItems.IMPACT_DYNAMITE_STICK, Models.GENERATED);
        itemModelGenerator.register(HacksawedItems.AUTO_LIGHT_DYNAMITE_STICK, Models.GENERATED);
        itemModelGenerator.register(HacksawedItems.BOUNCY_BALL, Models.GENERATED);
    }
}
