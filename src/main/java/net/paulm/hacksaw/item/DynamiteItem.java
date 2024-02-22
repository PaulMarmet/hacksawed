package net.paulm.hacksaw.item;

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
import net.minecraft.world.World;
import net.paulm.hacksaw.entity.DynamiteEntity;
import net.paulm.hacksaw.entity.HacksawEntities;

public class DynamiteItem extends Item {
    public DynamiteItem(Settings settings) {
        super(settings);
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (isLit(stack)) {
            setFuse(stack, getFuse(stack) - 1);
            if (getFuse(stack) % 20 == 0 && world.isClient()) {
                entity.sendMessage(Text.of(getFuse(stack)/20+" seconds left!"));
            }
            if (getFuse(stack) <= 0) {
                this.explode(stack, world, entity);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!canThrow(user, itemStack, hand)) {
            if (world.isClient())
                user.sendMessage(Text.of("You haven't lit the stick! (Hold a flint and steel in other hand)"));
            return TypedActionResult.fail(itemStack);
        }
        throwDynamite(world, user, itemStack);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    public boolean canThrow(PlayerEntity user, ItemStack itemStack, Hand hand) {
        if (isLit(itemStack)) {
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
            DynamiteEntity dynamiteEntity = new DynamiteEntity(HacksawEntities.DYNAMITE_STICK, user, world);
            dynamiteEntity.setItem(itemStack);
            dynamiteEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.2f, 0.75f);
            dynamiteEntity.setFuseTime(getFuse(itemStack));
            dynamiteEntity.setOnImpact(false);
            world.spawnEntity(dynamiteEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
    }

    public boolean isLit(ItemStack itemStack) {
        if (itemStack.getNbt() == null || !itemStack.getNbt().contains("isLit"))
        {
            itemStack.getOrCreateNbt().putBoolean("isLit", false);
        }
        return itemStack.getOrCreateNbt().getBoolean("isLit");
    }

    public int getFuse(ItemStack stack) {
        if (stack.getNbt() == null || !stack.getNbt().contains("fuse"))
        {
            setFuse(stack, 120);
        }
        return stack.getOrCreateNbt().getInt("fuse");
    }

    public void setFuse(ItemStack stack, int fuseLength) {
        stack.getOrCreateNbt().putInt("fuse", fuseLength);
    }

    public void explode(ItemStack stack, World world, Entity entity) {
        if (!entity.getWorld().isClient) {
            entity.getWorld().createExplosion(entity, entity.getX(), entity.getBodyY(0.0625), entity.getZ(), 3.0f, World.ExplosionSourceType.MOB);
            stack.decrement(1);
        }
    }
}
