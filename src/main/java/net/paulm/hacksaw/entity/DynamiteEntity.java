package net.paulm.hacksaw.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.paulm.hacksaw.Hacksaw;
import net.paulm.hacksaw.HacksawConfig;
import net.paulm.hacksaw.item.DynamiteItem;
import net.paulm.hacksaw.item.HacksawItems;

public class DynamiteEntity extends ThrownItemEntity {

    private int fuseTime;
    private boolean onImpact;
    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(DynamiteEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, World world) {
        super(entityType, world);
    }

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, LivingEntity owner, World world, ItemStack item) {
        super(entityType, owner, world);
        ItemStack tempItem = item.copy();
        tempItem.setCount(1);
        this.setItem(tempItem);
    }

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ITEM, new ItemStack(getDefaultItem()));
    }

    public void tick() {
        fuseTime--;
        DynamiteItem.summonSpark(this);
        if (this.isOnFire()) {
            fuseTime--;
            fuseTime--;
        }
        if (fuseTime <= 0) {
            this.explode();
        }
        //Do the tick()
        Vec3d oldPos = this.getPos();
        super.tick();
        this.setPosition(oldPos);
        //And now, the supposedly actually functional collisions
        this.dynaCollision(MovementType.SELF, this.getVelocity());
    }

    //The bounciness of the collision
    public static float getBounciness() {
        return HacksawConfig.dynamiteBounciness;
    }
    //The proportion of velocity kept when colliding vertically
    public static float getDrag() {
        return HacksawConfig.dynamiteDrag;
    }

    public void dynaCollision(MovementType movementType, Vec3d movement) {
        double g;
        //Stuff from Entity move() for the actual collisions
        Vec3d vec3d;
        if ((g = (vec3d = Entity.adjustMovementForCollisions(this, movement = this.adjustMovementForSneaking(movement, movementType), this.getBoundingBox(), this.getWorld(), this.getWorld().getEntityCollisions(this, this.getBoundingBox().stretch(movement)))).lengthSquared()) > 1.0E-7) {
            BlockHitResult blockHitResult;
            if (this.fallDistance != 0.0f && g >= 1.0 && (blockHitResult = this.getWorld().raycast(new RaycastContext(this.getPos(), this.getPos().add(vec3d), RaycastContext.ShapeType.FALLDAMAGE_RESETTING, RaycastContext.FluidHandling.WATER, this))).getType() != HitResult.Type.MISS) {
                this.onLanding();
            }
            this.setPosition(this.getX() + vec3d.x, this.getY() + vec3d.y, this.getZ() + vec3d.z);
        }
        boolean bl = !MathHelper.approximatelyEquals(movement.x, vec3d.x);
        boolean bl2 = !MathHelper.approximatelyEquals(movement.z, vec3d.z);
        //:) boing boing collisions
        this.horizontalCollision = bl || bl2;
        this.verticalCollision = movement.y != vec3d.y;
        Vec3d vec3d1 = this.getVelocity();
        if (bl) {
            vec3d1 = vec3d1.multiply(-getBounciness(), getDrag(), getDrag());
        }
        if (this.verticalCollision) {
            vec3d1 = vec3d1.multiply(getDrag(), -getBounciness(), getDrag());
        }
        if (bl2) {
            vec3d1 = vec3d1.multiply(getDrag(), getDrag(), -getBounciness());
        }
        this.setVelocity(vec3d1);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        boolean added = false;
        if (!this.getWorld().isClient()) {
            if (this.getItem() != null) {
                this.getItem().getOrCreateNbt().putInt("fuse", this.getFuseTime());
                this.getItem().getOrCreateNbt().putBoolean("isLit", true);
                added = player.giveItemStack(this.getItem());
            } else {
                ItemStack item = new ItemStack(getDefaultItem(), 1);
                item.getOrCreateNbt().putInt("fuse", this.getFuseTime());
                item.getOrCreateNbt().putBoolean("isLit", true);
                added = player.giveItemStack(item);
            }
        }
        if (added) {
            this.discard();
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

    public void explode() {
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
