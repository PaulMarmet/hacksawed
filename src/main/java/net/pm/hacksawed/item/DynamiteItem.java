package net.pm.hacksawed.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.pm.hacksawed.component.HacksawedComponents;
import net.pm.hacksawed.entity.DynamiteEntity;
import net.pm.hacksawed.entity.HacksawedEntities;
import net.pm.hacksawed.particle.HacksawedParticles;

public class DynamiteItem extends Item {
    public DynamiteItem(Settings settings) {
        super(settings);
    }

    public static boolean isLit(ItemStack itemStack) {
        return Boolean.TRUE.equals(itemStack.get(HacksawedComponents.IS_LIT));
    }

    public static boolean canAlwaysThrow(ItemStack itemStack) {
        return Boolean.TRUE.equals(itemStack.get(HacksawedComponents.CAN_ALWAYS_THROW));
    }

    public static long getExplosionTime(ItemStack itemStack) {
        return itemStack.get(HacksawedComponents.EXPLOSION_TIME);
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (isLit(stack)) {
            summonSpark(entity);
//            if (getFuse(stack) % 20 == 0 && world.isClient()) {
//                entity.sendMessage(Text.of(getFuse(stack)/20+" seconds left!"));
//            }
            if (getExplosionTime(stack) <= world.getTime() && getExplosionTime(stack) != 0) {
                this.explode(stack, world, entity);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!canThrow(user, itemStack, hand)) {
            if (world.isClient())
                user.sendMessage(Text.of("You haven't lit the stick! (Hold a flint and steel in other hand)"), true);
            return TypedActionResult.fail(itemStack);
        }
        user.getItemCooldownManager().set(this, 20);
        throwDynamite(world, user, itemStack);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    public boolean canThrow(PlayerEntity user, ItemStack itemStack, Hand hand) {
        if (isLit(itemStack) || canAlwaysThrow(itemStack)) {
            return true;
        }
        if (hand == Hand.MAIN_HAND) {
            return user.getOffHandStack().getItem() == Items.FLINT_AND_STEEL;
        }
        else {
            return user.getMainHandStack().getItem() == Items.FLINT_AND_STEEL;
        }
    }

    public void throwDynamite(World world, PlayerEntity user, ItemStack itemStack) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!world.isClient) {
            DynamiteEntity dynamiteEntity = new DynamiteEntity(HacksawedEntities.DYNAMITE_STICK, user, world, itemStack);
            dynamiteEntity.setItem(itemStack);
            dynamiteEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.2f, 0.75f);
            dynamiteEntity.setOnImpact(false);
            world.spawnEntity(dynamiteEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
    }

    public void explode(ItemStack stack, World world, Entity entity) {
        if (!entity.getWorld().isClient) {
            entity.getWorld().createExplosion(null, entity.getX(), entity.getBodyY(0.0625), entity.getZ(), 3.0f, World.ExplosionSourceType.MOB);
            stack.decrement(1);
        }
    }

    public static float sparkSpread = 0.1f;

    public static void summonSpark(Entity entity) {
        Box box = entity.getBoundingBox();
        Random r = Random.createLocal();
        double r1 = r.nextFloat()*box.getLengthX();
        double r2 = r.nextFloat()*box.getLengthY();
        double r3 = r.nextFloat()*box.getLengthZ();

        double d = box.minX + r1;
        double e = box.minY + r2;
        double f = box.minZ + r3;
        entity.getWorld().addParticle(HacksawedParticles.SPARK, d, e, f, (r.nextFloat()-0.5)*sparkSpread, (r.nextFloat()-0.5)*sparkSpread, (r.nextFloat()-0.5)*sparkSpread);
    }

}
