package net.paulm.hacksawed.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.item.HacksawedItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EndermanEntity.class)
public abstract class EndermanMixin {

    @Redirect(method = "Lnet/minecraft/entity/mob/EndermanEntity;isPlayerStaring(Lnet/minecraft/entity/player/PlayerEntity;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean isInTag(ItemStack itemStack, Item item) {
        return (itemStack.isIn(HacksawedItemTags.ENDERMAN_SAFE) && HacksawedConfig.moreEndermanSafeItems) || itemStack.isOf(Blocks.CARVED_PUMPKIN.asItem());
    }

}
