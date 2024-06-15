package net.paulm.hacksawed.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

public class HacksawedParticles {
    public static final SimpleParticleType SPARK = Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Hacksawed.MOD_ID, "spark"), FabricParticleTypes.simple());


    public static void  registerModParticles() {

    }
}
