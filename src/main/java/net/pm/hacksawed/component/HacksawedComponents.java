package net.pm.hacksawed.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class HacksawedComponents {

    public static final ComponentType<Boolean> IS_LIT = Registry.register(Registries.DATA_COMPONENT_TYPE, "is_lit", ComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build());
    public static final ComponentType<Long> EXPLOSION_TIME = Registry.register(Registries.DATA_COMPONENT_TYPE, "explosion_time",ComponentType.<Long>builder().codec(Codec.LONG).packetCodec(PacketCodecs.VAR_LONG).build());



    public void registerModComponents() {
        //register
    }
}
