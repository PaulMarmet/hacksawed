package net.pm.hacksawed;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import net.pm.hacksawed.block.HacksawedBlocks;
import net.pm.hacksawed.conditions.HacksawedConditions;
import net.pm.hacksawed.effect.HacksawedEffects;
import net.pm.hacksawed.entity.HacksawedEntities;
import net.pm.hacksawed.item.HacksawedItemGroups;
import net.pm.hacksawed.item.HacksawedItemTags;
import net.pm.hacksawed.item.HacksawedItems;
import net.pm.hacksawed.particle.HacksawedParticles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hacksawed implements ModInitializer {
	public static final String MOD_ID = "hacksawed";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {
		MidnightConfig.init("hacksawed", HacksawedConfig.class);
		HacksawedConfig.addToConditions();
		HacksawedConditions.registerConditions();
		HacksawedItems.registerModItems();
		HacksawedItemGroups.registerItemGroups();
		HacksawedItemTags.registerModTags();
		HacksawedEffects.registerModEffects();
		HacksawedEntities.registerModEntities();
		HacksawedParticles.registerModParticles();
		HacksawedBlocks.registerBlocks();
	}
}