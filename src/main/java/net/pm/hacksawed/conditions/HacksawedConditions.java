package net.pm.hacksawed.conditions;

import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.util.Identifier;
import net.pm.hacksawed.Hacksawed;
import net.pm.hacksawed.HacksawedConfig;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class HacksawedConditions {
    static Dictionary<String, Boolean> configDict = new Hashtable<>();

    public static final ResourceConditionType<ConfigEnabledCondition> CONFIG_ENABLED = ResourceConditionType.create(Identifier.of(Hacksawed.MOD_ID, "config_enabled"), ConfigEnabledCondition.CODEC);

    public static void registerConditions() {
        ResourceConditions.register(CONFIG_ENABLED);
    }

    public static void addConfigToDict(String id, Boolean config) {
        configDict.put(id, config);
    }

    public static boolean configsActivated(List<String> configs) {
        HacksawedConfig.addToConditions();
        for (String config : configs) {
            if (!configDict.get(config)) {
                return false;
            }
        }
        return true;
    }
}
