package net.paulm.hacksaw.damage;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.paulm.hacksaw.Hacksaw;

public class HacksawDamageTypes {
    public static final RegistryKey<DamageType> BLEEDING = register("bleeding");

    private static RegistryKey<DamageType> register(String name)
    {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Hacksaw.MOD_ID, name));
    }

}
