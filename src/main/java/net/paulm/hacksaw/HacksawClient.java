package net.paulm.hacksaw;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.paulm.hacksaw.entity.DynamiteRenderer;
import net.paulm.hacksaw.entity.HacksawEntities;

public class HacksawClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(HacksawEntities.DYNAMITE_STICK, DynamiteRenderer::new);
    }
}
