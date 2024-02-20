package net.paulm.hacksaw.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksaw.Hacksaw;

public class HacksawEntities {
    public static final EntityType<DynamiteEntity> DYNAMITE_STICK = Registry.register(Registries.ENTITY_TYPE, new Identifier(Hacksaw.MOD_ID, "dynamite_stick"), FabricEntityTypeBuilder.<DynamiteEntity>create(SpawnGroup.MISC, DynamiteEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.6f)).build());


    public static void  registerModEntities() {

    }
}
