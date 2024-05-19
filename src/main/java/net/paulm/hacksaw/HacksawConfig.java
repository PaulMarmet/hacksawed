package net.paulm.hacksaw;

import eu.midnightdust.lib.config.MidnightConfig;

public class HacksawConfig extends MidnightConfig {
    public enum SelectionType {
        ALL, SOME, VANILLA
    }
    //Bleeding Configs
    @Comment(category = "general") public static Comment bleedingConfig1;
    @Entry(category = "general", min = 0) public static int bleedingAmplificationTime = 800;
    @Entry(category = "general") public static float waterBleedingChance = 0.5f;
    //Hacksaw Configs
    @Comment(category = "general") public static Comment hacksawConfig1;
    @Entry(category = "general", min = 0) public static int initBleedTime = 40;
    @Entry(category = "general", min = 0) public static int continuationBleedTime = 45;

    //Dynamite & Bouncy Ball Configs
    @Comment(category = "general") public static Comment dynamiteConfig1;
    @Entry(category = "general", min = 0) public static int dynamiteFuseTime = 120;
    @Entry(category = "general", min = 0f) public static float dynamiteBounciness = 0.1f;
    @Entry(category = "general", min = 0f) public static float dynamiteDrag = 0.7f;
    @Comment(category = "general") public static Comment bouncyBallConfig1;
    @Entry(category = "general", min = 0f) public static float bouncyBallBounciness = 0.7f;
    @Entry(category = "general", min = 0f) public static float bouncyBallDrag = 0.7f;

    //Item Changes Configs
    @Comment(category = "general") public static Comment ItemConfig1;
    @Entry(category = "general") public static SelectionType explosionProofItems = SelectionType.ALL;
    @Entry(category = "general") public static SelectionType fireProofItems = SelectionType.SOME;

    //Other Configs
    @Comment(category = "general") public static Comment OtherConfig1;
    @Entry(category = "general", min = -16f, max = 16f) public static float recoilAmount = -0.3f;
    @Entry(category = "general") public static boolean untramplableWetFarmland = true;
    @Entry(category = "general") public static boolean echoShardRings = false;

}
