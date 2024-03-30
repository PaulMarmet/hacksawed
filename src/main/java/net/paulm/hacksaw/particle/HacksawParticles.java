package net.paulm.hacksaw.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksaw.Hacksaw;

public class HacksawParticles {
    public static final DefaultParticleType SPARK = Registry.register(Registries.PARTICLE_TYPE, new Identifier(Hacksaw.MOD_ID, "spark"), FabricParticleTypes.simple());


    public static void  registerModParticles() {

    }
}
