package net.paulm.hacksaw;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.*;
import net.paulm.hacksaw.entity.BouncyBallRenderer;
import net.paulm.hacksaw.entity.DynamiteRenderer;
import net.paulm.hacksaw.entity.HacksawEntities;
import net.paulm.hacksaw.particle.HacksawParticles;

public class HacksawClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(HacksawEntities.DYNAMITE_STICK, DynamiteRenderer::new);
        EntityRendererRegistry.register(HacksawEntities.IMPACT_DYNAMITE_STICK, DynamiteRenderer::new);
        EntityRendererRegistry.register(HacksawEntities.BOUNCY_BALL, BouncyBallRenderer::new);
        ParticleFactoryRegistry.getInstance().register(HacksawParticles.SPARK, FlameParticle.Factory::new);
    }
}
