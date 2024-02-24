package net.paulm.hacksaw.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.paulm.hacksaw.entity.HacksawEntities;
import net.paulm.hacksaw.entity.ImpactDynamiteEntity;

public class AutoLightDynamiteItem extends DynamiteItem {
    public AutoLightDynamiteItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canThrow(PlayerEntity user, ItemStack itemStack, Hand hand) {
        return true;
    }
}
