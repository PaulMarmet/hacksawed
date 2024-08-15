package net.pm.hacksawed;

import eu.midnightdust.lib.config.MidnightConfig;

public class HacksawedConfig extends MidnightConfig {
    public enum SelectionType {
        ALL, SOME, VANILLA
    }
    //Bleeding Configs
    @Comment(category = "general") public static Comment bleedingConfig1;
    @Entry(category = "general", min = 0) public static int bleedingAmplificationTime = 800;
    //Hacksaw Configs
    @Comment(category = "general") public static Comment hacksawConfig1;
    @Entry(category = "general", min = 0) public static int initBleedTime = 40;
    @Entry(category = "general", min = 0) public static int continuationBleedTime = 60;

    //Dynamite & Bouncy Ball Configs
    @Comment(category = "general") public static Comment dynamiteConfig1;
    @Entry(category = "general", min = 0) public static int dynamiteFuseTime = 120;
    @Entry(category = "general", min = 0f) public static float dynamiteBounciness = 0.1f;
    @Entry(category = "general", min = 0f) public static float dynamiteDrag = 0.7f;
    @Comment(category = "general") public static Comment bouncyBallConfig1;
    @Entry(category = "general", min = 0f) public static float bouncyBallBounciness = 0.7f;
    @Entry(category = "general", min = 0f) public static float bouncyBallDrag = 0.7f;
    @Entry(category = "general", min = 0f) public static float returnalOrbVelTransfer = 0.4f;

    //Item Changes Configs
    @Comment(category = "general") public static Comment ItemConfig1;
    @Entry(category = "general") public static SelectionType explosionProofItems = SelectionType.ALL;
    @Entry(category = "general") public static boolean explosionProofBlastProt = true;
    @Entry(category = "general") public static SelectionType fireProofItems = SelectionType.SOME;
    @Entry(category = "general") public static boolean fireProofFireProt = true;

    //Other Configs
    @Comment(category = "general") public static Comment OtherConfig1;
    @Entry(category = "general") public static boolean untramplableWetFarmland = true;
    @Entry(category = "general") public static boolean extendWaterRegion = true;
    @Entry(category = "general") public static boolean echoShardRings = false;
    @Entry(category = "general") public static boolean blocksReleaseSparks = true;
    @Entry(category = "general") public static boolean iceAndSnowDontMelt = true;
    @Entry(category = "general") public static boolean moreEndermanSafeItems = true;
    @Entry(category = "general") public static boolean sneakSweetBerries = true;
    @Entry(category = "general") public static boolean creeperSpores = true;
    @Entry(category = "general") public static boolean bundleChanges = true;
    @Entry(category = "general", min = 0, max = 64) public static int bundleMaxSpaceCost = 8;

}
