package net.paulm.hacksaw.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class BouncyBallRenderer extends FlyingItemEntityRenderer<BouncyBallEntity> {
    public BouncyBallRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

}
