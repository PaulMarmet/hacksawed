package net.pm.hacksawed.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pm.hacksawed.Hacksawed;

public class HacksawedEntities {
    public static final EntityType<DynamiteEntity> DYNAMITE_STICK = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Hacksawed.MOD_ID, "dynamite_stick"), EntityType.Builder.<DynamiteEntity>create(DynamiteEntity::new, SpawnGroup.MISC).dimensions(0.4f, 0.4f).build());
    public static final EntityType<ImpactDynamiteEntity> IMPACT_DYNAMITE_STICK = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Hacksawed.MOD_ID, "impact_dynamite_stick"), EntityType.Builder.<ImpactDynamiteEntity>create(ImpactDynamiteEntity::new, SpawnGroup.MISC).dimensions(0.4f, 0.4f).build());

    public static final EntityType<BouncyBallEntity> BOUNCY_BALL = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Hacksawed.MOD_ID, "bouncy_ball"), EntityType.Builder.<BouncyBallEntity>create(BouncyBallEntity::new, SpawnGroup.MISC).dimensions(0.4f, 0.4f).build());
    public static final EntityType<ReturnalOrbEntity> RETURNAL_ORB = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Hacksawed.MOD_ID, "returnal_orb"), EntityType.Builder.<ReturnalOrbEntity>create(ReturnalOrbEntity::new, SpawnGroup.MISC).dimensions(0.4f, 0.4f).build());


    public static void  registerModEntities() {

    }
}
