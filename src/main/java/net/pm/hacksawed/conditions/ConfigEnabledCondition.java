package net.pm.hacksawed.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ConfigEnabledCondition(List<String> configs) implements ResourceCondition {
    public static final MapCodec<ConfigEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.listOf().fieldOf("values").forGetter(ConfigEnabledCondition::configs)
    ).apply(instance, ConfigEnabledCondition::new));

    @Override
    public ResourceConditionType<?> getType() {
        return null;
    }

    @Override
    public boolean test(@Nullable RegistryWrapper.WrapperLookup registryLookup) {
        return HacksawedConditions.configsActivated(this.configs);
    }
}
