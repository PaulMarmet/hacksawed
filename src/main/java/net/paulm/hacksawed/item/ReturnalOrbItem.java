package net.paulm.hacksawed.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.paulm.hacksawed.entity.BouncyBallEntity;
import net.paulm.hacksawed.entity.HacksawedEntities;
import net.paulm.hacksawed.entity.ReturnalOrbEntity;

public class ReturnalOrbItem extends BouncyBallItem {
    public ReturnalOrbItem(Settings settings) {
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

}
