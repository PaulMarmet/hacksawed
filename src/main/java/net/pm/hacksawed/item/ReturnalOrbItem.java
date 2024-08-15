package net.pm.hacksawed.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.pm.hacksawed.entity.HacksawedEntities;
import net.pm.hacksawed.entity.ReturnalOrbEntity;

public class ReturnalOrbItem extends BouncyBallItem {
    public ReturnalOrbItem(Item.Settings settings) {
        super(settings);
    }

    public void throwBall(World world, PlayerEntity user, ItemStack itemStack) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            ReturnalOrbEntity returnalOrbEntity = new ReturnalOrbEntity(HacksawedEntities.RETURNAL_ORB, user, world, itemStack);
            returnalOrbEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.2f, 0.75f);
            world.spawnEntity(returnalOrbEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        ReturnalOrbEntity entity = new ReturnalOrbEntity(HacksawedEntities.RETURNAL_ORB, pos.getX(), pos.getY(), pos.getZ(), world);
        entity.setItem(stack);
        return entity;
    }
}
