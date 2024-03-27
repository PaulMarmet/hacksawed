package net.paulm.hacksaw.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.paulm.hacksaw.entity.BouncyBallEntity;
import net.paulm.hacksaw.entity.HacksawEntities;

public class BouncyBallItem extends Item {
    public BouncyBallItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 10);
        throwBall(world, user, itemStack);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    public void throwBall(World world, PlayerEntity user, ItemStack itemStack) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            BouncyBallEntity bouncyBallEntity = new BouncyBallEntity(HacksawEntities.BOUNCY_BALL, user, world);
            bouncyBallEntity.setItem(itemStack);
            bouncyBallEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.2f, 0.75f);
            world.spawnEntity(bouncyBallEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
    }

    //Not really a better place to place this

}
