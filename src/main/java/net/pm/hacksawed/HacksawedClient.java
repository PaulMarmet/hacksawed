package net.pm.hacksawed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.*;
import net.pm.hacksawed.entity.BouncyBallRenderer;
import net.pm.hacksawed.entity.DynamiteRenderer;
import net.pm.hacksawed.entity.HacksawedEntities;
import net.pm.hacksawed.particle.HacksawedParticles;

public class HacksawedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(HacksawedEntities.DYNAMITE_STICK, DynamiteRenderer::new);
        EntityRendererRegistry.register(HacksawedEntities.IMPACT_DYNAMITE_STICK, DynamiteRenderer::new);
        EntityRendererRegistry.register(HacksawedEntities.BOUNCY_BALL, BouncyBallRenderer::new);
        EntityRendererRegistry.register(HacksawedEntities.RETURNAL_ORB, BouncyBallRenderer::new);
        ParticleFactoryRegistry.getInstance().register(HacksawedParticles.SPARK, FlameParticle.Factory::new);
    }
}
