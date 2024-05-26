package net.paulm.hacksawed.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class AutoLightDynamiteItem extends DynamiteItem {
    public AutoLightDynamiteItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canThrow(PlayerEntity user, ItemStack itemStack, Hand hand) {
        return true;
    }
}
