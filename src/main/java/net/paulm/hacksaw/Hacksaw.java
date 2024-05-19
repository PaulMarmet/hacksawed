package net.paulm.hacksaw;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;

import net.paulm.hacksaw.effect.HacksawEffects;
import net.paulm.hacksaw.enchantment.HacksawEnchantments;
import net.paulm.hacksaw.entity.HacksawEntities;
import net.paulm.hacksaw.item.HacksawItemGroups;
import net.paulm.hacksaw.item.HacksawItemTags;
import net.paulm.hacksaw.item.HacksawItems;
import net.paulm.hacksaw.particle.HacksawParticles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hacksaw implements ModInitializer {
	public static final String MOD_ID = "hacksaw";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		HacksawItemGroups.registerItemGroups();
		HacksawItems.registerModItems();
		HacksawEffects.registerModEffects();
		HacksawEntities.registerModEntities();
		HacksawEnchantments.registerHacksawEnchantments();
		HacksawParticles.registerModParticles();
		HacksawItemTags.registerModTags();
		MidnightConfig.init("hacksaw", HacksawConfig.class);

		LOGGER.info("Hacksawed On!");
	}
}