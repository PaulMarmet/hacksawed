package net.paulm.hacksawed;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import net.paulm.hacksawed.effect.HacksawedEffects;
import net.paulm.hacksawed.enchantment.HacksawedEnchantments;
import net.paulm.hacksawed.entity.HacksawedEntities;
import net.paulm.hacksawed.item.HacksawedItemGroups;
import net.paulm.hacksawed.item.HacksawedItemTags;
import net.paulm.hacksawed.item.HacksawedItems;
import net.paulm.hacksawed.particle.HacksawedParticles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hacksawed implements ModInitializer {
	public static final String MOD_ID = "hacksawed";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		HacksawedItemGroups.registerItemGroups();
		HacksawedItems.registerModItems();
		HacksawedEffects.registerModEffects();
		HacksawedEntities.registerModEntities();
		HacksawedEnchantments.registerHacksawEnchantments();
		HacksawedParticles.registerModParticles();
		HacksawedItemTags.registerModTags();
		MidnightConfig.init("hacksawed", HacksawedConfig.class);
	}
}