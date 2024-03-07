package net.paulm.hacksaw.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.paulm.hacksaw.item.EchoShardItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Items.class)
public abstract class EchoShardMixin {
    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value="CONSTANT", args="stringValue=echo_shard", ordinal = 0)), at = @At(value = "NEW", target = "Lnet/minecraft/item/Item;", ordinal = 0))
    private static Item makeTheNewVersion(Item.Settings settings) {
        return new EchoShardItem(settings);
    }

}
