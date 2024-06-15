package net.paulm.hacksawed.damage;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

public class HacksawedDamageTypes {
    public static final RegistryKey<DamageType> BLEEDING = register("bleeding");

    private static RegistryKey<DamageType> register(String name)
    {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Hacksawed.MOD_ID, name));
    }

}
