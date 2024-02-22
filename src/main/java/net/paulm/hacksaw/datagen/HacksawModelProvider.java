package net.paulm.hacksaw.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.paulm.hacksaw.item.HacksawItems;

public class HacksawModelProvider extends FabricModelProvider {
    public HacksawModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(HacksawItems.HACKSAW, Models.GENERATED);
        itemModelGenerator.register(HacksawItems.DYNAMITE_STICK, Models.GENERATED);
        itemModelGenerator.register(HacksawItems.IMPACT_DYNAMITE_STICK, Models.GENERATED);
    }
}
