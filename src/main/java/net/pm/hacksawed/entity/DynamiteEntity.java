package net.pm.hacksawed.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
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
import net.pm.hacksawed.HacksawedConfig;
import net.pm.hacksawed.component.HacksawedComponents;
import net.pm.hacksawed.item.DynamiteItem;
import net.pm.hacksawed.item.HacksawedItems;

public class DynamiteEntity extends ThrownItemEntity {

    private long explosionTime;
    private boolean onImpact;

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, World world) {
        super(entityType, world);
    }

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, LivingEntity owner, World world, ItemStack item) {
        super(entityType, owner, world);
        ItemStack tempItem = item.copy();
        tempItem.setCount(1);
        this.setItem(tempItem);
        if (tempItem.get(HacksawedComponents.EXPLOSION_TIME) == 0) {
            this.setExplosionTime(world.getTime() + HacksawedConfig.dynamiteFuseTime);
        } else {
            this.setExplosionTime(tempItem.get(HacksawedComponents.EXPLOSION_TIME));
        }
    }

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    public void tick() {
        DynamiteItem.summonSpark(this);
        if (this.isOnFire()) {
            explosionTime--;
            explosionTime--;
        }
        if (explosionTime <= this.getWorld().getTime() && !getOnImpact()) {
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
        return HacksawedConfig.dynamiteBounciness;
    }
    //The proportion of velocity kept when colliding vertically
    public static float getDrag() {
        return HacksawedConfig.dynamiteDrag;
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
            if (this.getStack() != null) {
                this.getStack().set(HacksawedComponents.EXPLOSION_TIME, this.getExplosionTime());
                this.getStack().set(HacksawedComponents.IS_LIT, true);
                added = player.giveItemStack(this.getStack());
            } else {
                ItemStack item = new ItemStack(getDefaultItem(), 1);
                item.set(HacksawedComponents.EXPLOSION_TIME, this.getExplosionTime());
                item.set(HacksawedComponents.IS_LIT, true);
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

    public void setExplosionTime(long newExplosionTime) {this.explosionTime = newExplosionTime;}
    public long getExplosionTime() {return explosionTime;}

    public void setOnImpact(boolean newOnImpact) {this.onImpact = newOnImpact;}
    public boolean getOnImpact() {return onImpact;}

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putLong("ExplosionTime", this.explosionTime);
        nbt.putBoolean("OnImpact", this.onImpact);
    }
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("ExplosionTime", NbtElement.NUMBER_TYPE)) {
            this.explosionTime = nbt.getLong("ExplosionTime");
        }
        if (nbt.contains("OnImpact", NbtElement.BYTE_TYPE)) {
            this.onImpact = nbt.getBoolean("OnImpact");
        }
    }

    @Override
    protected Item getDefaultItem() {
        return HacksawedItems.DYNAMITE_STICK;
    }
}
