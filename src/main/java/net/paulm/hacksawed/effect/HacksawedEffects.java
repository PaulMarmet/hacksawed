package net.paulm.hacksawed.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

public class HacksawedEffects {
    public static final StatusEffect BLEEDING = registerEffect("bleeding", new BleedingStatusEffect());
    public static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Hacksawed.MOD_ID, name), effect);
    }

    public static void registerModEffects() {

    }
}
