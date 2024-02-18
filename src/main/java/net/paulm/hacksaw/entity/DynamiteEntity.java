package net.paulm.hacksaw.entity;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.paulm.hacksaw.Hacksaw;
import net.paulm.hacksaw.item.DynamiteItem;
import net.paulm.hacksaw.item.HacksawItems;

public class DynamiteEntity extends ThrownItemEntity {

    private int fuseTime;
    private boolean onImpact;

    public DynamiteEntity(EntityType<DynamiteEntity> entityType, World world) {
        super(entityType, world);
    }

    public DynamiteEntity(World world, LivingEntity owner) {
        super(HacksawEntities.DYNAMITE_STICK, owner, world);
    }

    public DynamiteEntity(World world, double x, double y, double z) {
        super(HacksawEntities.DYNAMITE_STICK, x, y, z, world);
    }

    public void tick() {
        super.tick();
        fuseTime--;
        if (this.isOnFire()) {
            fuseTime--;
            fuseTime--;
        }
        if (fuseTime <= 0) {
            this.explode();
        }
        this.noClip = !this.getWorld().isSpaceEmpty(this, this.getBoundingBox().contract(1.0E-7));
        if (this.noClip) {
            this.pushOutOfBlocks(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
        }
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        Hacksaw.LOGGER.info("Interact! "+hand);
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, HacksawItems.DYNAMITE_STICK.getDefaultStack());
            if (itemStack2.isOf(HacksawItems.DYNAMITE_STICK)) {
                ((DynamiteItem) itemStack2.getItem()).setFuse(itemStack2, getFuseTime());
            }
            else {
                Hacksaw.LOGGER.info("Um, so for some reason, the dynamite item isn't dynamite???");
            }
            player.setStackInHand(hand, itemStack2);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient && this.getOnImpact()) {
            this.explode();
            this.discard();
        }
    }

    private void explode() {
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 3.0f, World.ExplosionSourceType.MOB);
            this.discard();
        }
    }

    public void setFuseTime(int newFuseTime) {this.fuseTime = newFuseTime;}
    public int getFuseTime() {return fuseTime;}

    public void setOnImpact(boolean newOnImpact) {this.onImpact = newOnImpact;}
    public boolean getOnImpact() {return onImpact;}

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Fuse", this.fuseTime);
        nbt.putBoolean("OnImpact", this.onImpact);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("Fuse", NbtElement.NUMBER_TYPE)) {
            this.fuseTime = nbt.getInt("Fuse");
        }
        if (nbt.contains("OnImpact", NbtElement.BYTE_TYPE)) {
            this.onImpact = nbt.getBoolean("OnImpact");
        }
    }

    @Override
    protected Item getDefaultItem() {
        return HacksawItems.DYNAMITE_STICK;
    }
}
