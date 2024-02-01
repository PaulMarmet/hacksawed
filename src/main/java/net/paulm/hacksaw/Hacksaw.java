package net.paulm.hacksaw;

import net.fabricmc.api.ModInitializer;

import net.paulm.hacksaw.effect.HacksawEffects;
import net.paulm.hacksaw.item.HacksawItemGroups;
import net.paulm.hacksaw.item.HacksawItems;
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

		LOGGER.info("Hello Fabric world!");
	}
}