package net.paulm.hacksawed.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

public class HacksawedParticles {
    public static final DefaultParticleType SPARK = Registry.register(Registries.PARTICLE_TYPE, new Identifier(Hacksawed.MOD_ID, "spark"), FabricParticleTypes.simple());


    public static void  registerModParticles() {

    }
}
