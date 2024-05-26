package net.paulm.hacksawed.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

public class HacksawedEntities {
    public static final EntityType<DynamiteEntity> DYNAMITE_STICK = Registry.register(Registries.ENTITY_TYPE, new Identifier(Hacksawed.MOD_ID, "dynamite_stick"), FabricEntityTypeBuilder.<DynamiteEntity>create(SpawnGroup.MISC, DynamiteEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build());
    public static final EntityType<ImpactDynamiteEntity> IMPACT_DYNAMITE_STICK = Registry.register(Registries.ENTITY_TYPE, new Identifier(Hacksawed.MOD_ID, "impact_dynamite_stick"), FabricEntityTypeBuilder.<ImpactDynamiteEntity>create(SpawnGroup.MISC, ImpactDynamiteEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build());

    public static final EntityType<BouncyBallEntity> BOUNCY_BALL = Registry.register(Registries.ENTITY_TYPE, new Identifier(Hacksawed.MOD_ID, "bouncy_ball"), FabricEntityTypeBuilder.<BouncyBallEntity>create(SpawnGroup.MISC, BouncyBallEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build());
    public static final EntityType<ReturnalOrbEntity> RETURNAL_ORB = Registry.register(Registries.ENTITY_TYPE, new Identifier(Hacksawed.MOD_ID, "returnal_orb"), FabricEntityTypeBuilder.<ReturnalOrbEntity>create(SpawnGroup.MISC, ReturnalOrbEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.4f)).build());


    public static void  registerModEntities() {

    }
}
