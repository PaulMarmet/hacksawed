package net.pm.hacksawed.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pm.hacksawed.Hacksawed;

public class HacksawedEffects {
    public static final StatusEffect BLEEDING = registerEffect("bleeding", new BleedingStatusEffect());
    public static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, Identifier.of(Hacksawed.MOD_ID, name), effect);
    }

    public static void registerModEffects() {

    }
}
