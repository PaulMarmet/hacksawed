package net.paulm.hacksawed.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.DataComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.paulm.hacksawed.Hacksawed;

public class HacksawedComponents {

    public static final DataComponentType<Boolean> IS_LIT = Registry.register(Registries.DATA_COMPONENT_TYPE, new Identifier(Hacksawed.MOD_ID, "is_lit"), DataComponentType.<Boolean>builder().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOL).build());
    public static final DataComponentType<Long> EXPLOSION_TIME = Registry.register(Registries.DATA_COMPONENT_TYPE, new Identifier(Hacksawed.MOD_ID, "explosion_time"),DataComponentType.<Long>builder().codec(Codec.LONG).packetCodec(PacketCodecs.VAR_LONG).build());



    public void registerModComponents() {
        //register
    }
}
