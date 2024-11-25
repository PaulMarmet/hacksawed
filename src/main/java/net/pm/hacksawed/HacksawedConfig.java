package net.pm.hacksawed;

import eu.midnightdust.lib.config.MidnightConfig;
import net.pm.hacksawed.conditions.HacksawedConditions;

public class HacksawedConfig extends MidnightConfig {
    public enum SelectionType {
        ALL, SOME, VANILLA
    }
    //Hacksaw Configs
    @Comment(category = "general") public static Comment hacksawConfig1;
    @Entry(category = "general") public static boolean hacksawEnabled = true;
    @Entry(category = "general", min = 0) public static int initBleedTime = 40;
    @Entry(category = "general", min = 0) public static int continuationBleedTime = 60;
    //Bleeding Configs
    @Comment(category = "general") public static Comment bleedingConfig1;
    @Entry(category = "general", min = 0) public static int bleedingAmplificationTime = 800;

    //Dynamite & Bouncy Ball Configs
    @Comment(category = "general") public static Comment dynamiteConfig1;
    @Entry(category = "general") public static boolean dynamiteEnabled = true;
    @Entry(category = "general") public static boolean impactDynamite = false;
    @Entry(category = "general", min = 0) public static int dynamiteFuseTime = 120;
    @Entry(category = "general", min = 0f) public static float dynamiteBounciness = 0.1f;
    @Entry(category = "general", min = 0f) public static float dynamiteDrag = 0.7f;
    @Comment(category = "general") public static Comment bouncyBallConfig1;

    @Entry(category = "general") public static boolean orbsEnabled = true;
    @Entry(category = "general", min = 0f) public static float bouncyBallBounciness = 0.7f;
    @Entry(category = "general", min = 0f) public static float bouncyBallDrag = 0.7f;
    @Entry(category = "general", min = 0f) public static float returnalOrbVelTransfer = 0.4f;


    public static void addToConditions() {
        HacksawedConditions.addConfigToDict("hacksaw_enabled", hacksawEnabled);
        HacksawedConditions.addConfigToDict("dynamite_enabled", dynamiteEnabled);
        HacksawedConditions.addConfigToDict("impact_dynamite", impactDynamite);
        HacksawedConditions.addConfigToDict("orbs_enabled", orbsEnabled);
    }

}
