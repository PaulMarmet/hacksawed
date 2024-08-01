package net.pm.hacksawed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.particle.*;
import net.minecraft.util.Identifier;
import net.pm.hacksawed.entity.BouncyBallRenderer;
import net.pm.hacksawed.entity.DynamiteRenderer;
import net.pm.hacksawed.entity.HacksawedEntities;
import net.pm.hacksawed.item.DynamiteItem;
import net.pm.hacksawed.item.HacksawedItems;
import net.pm.hacksawed.particle.HacksawedParticles;

public class HacksawedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(HacksawedEntities.DYNAMITE_STICK, DynamiteRenderer::new);
        EntityRendererRegistry.register(HacksawedEntities.IMPACT_DYNAMITE_STICK, DynamiteRenderer::new);
        EntityRendererRegistry.register(HacksawedEntities.BOUNCY_BALL, BouncyBallRenderer::new);
        EntityRendererRegistry.register(HacksawedEntities.RETURNAL_ORB, BouncyBallRenderer::new);
        ParticleFactoryRegistry.getInstance().register(HacksawedParticles.SPARK, FlameParticle.Factory::new);
        ModelPredicateProviderRegistry.register(HacksawedItems.DYNAMITE_STICK, Identifier.ofVanilla("lit"), (itemStack, clientWorld, livingEntity, seed) -> DynamiteItem.isLit(itemStack) ? 1 : 0);
        ModelPredicateProviderRegistry.register(HacksawedItems.AUTO_LIGHT_DYNAMITE_STICK, Identifier.ofVanilla("lit"), (itemStack, clientWorld, livingEntity, seed) -> DynamiteItem.isLit(itemStack) ? 1 : 0);
    }
}
