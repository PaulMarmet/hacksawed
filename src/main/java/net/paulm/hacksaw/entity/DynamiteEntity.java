package net.paulm.hacksaw.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.paulm.hacksaw.Hacksaw;
import net.paulm.hacksaw.item.DynamiteItem;
import net.paulm.hacksaw.item.HacksawItems;

public class DynamiteEntity extends ThrownItemEntity {

    private int fuseTime;
    private boolean onImpact;

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, World world) {
        super(entityType, world);
    }

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, LivingEntity owner, World world) {
        super(entityType, owner, world);
    }

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    public void tick() {
        fuseTime--;
        if (this.isOnFire()) {
            fuseTime--;
            fuseTime--;
        }
        if (fuseTime <= 0) {
            this.explode();
        }
        //Do the tick() from entity
        this.baseTick();

        this.parentalTick();

        //And now, the supposedly actually functional collisions
        this.dynaCollision(MovementType.SELF, this.getVelocity());
    }

    public void parentalTick() {
        //This is a basically the interesting part of the ThrownEntity tick()
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        boolean bl = false;
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
            BlockState blockState = this.getWorld().getBlockState(blockPos);
            if (blockState.isOf(Blocks.NETHER_PORTAL)) {
                this.setInNetherPortal(blockPos);
                bl = true;
            } else if (blockState.isOf(Blocks.END_GATEWAY)) {
                BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
                if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
                    EndGatewayBlockEntity.tryTeleportingEntity(this.getWorld(), blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
                }
                bl = true;
            }
        }
        if (hitResult.getType() != HitResult.Type.MISS && !bl) {
            this.onCollision(hitResult);
        }
    }

    //The bounciness of the collision
    public static float getBounciness() {
        return 0.5f;
    }
    //The proportion of velocity kept when colliding vertically
    public static float getDrag() {
        return 0.7f;
    }

    public void dynaCollision(MovementType movementType, Vec3d movement) {
        //More bits from ThrownEntity
        this.checkBlockCollision();
        double g;
        float h;
        this.updateRotation();
        if (this.isTouchingWater()) {
            for (int i = 0; i < 4; ++i) {
                this.getWorld().addParticle(ParticleTypes.BUBBLE, this.getX(), this.getY(), this.getZ(), movement.x, movement.y, movement.z);
            }
            h = 0.8f;
        } else {
            h = 1f;
        }
        this.setVelocity(movement.multiply(h));
        movement = this.getVelocity();
        if (!this.hasNoGravity()) {
            this.setVelocity(movement.x, movement.y - (double)this.getGravity(), movement.z);
        }

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
            vec3d1 = vec3d1.multiply(-getBounciness(), 1, 1);
        }
        if (this.verticalCollision) {
            vec3d1 = vec3d1.multiply(getDrag(), -getBounciness(), getDrag());
        }
        if (bl2) {
            vec3d1 = vec3d1.multiply(1, 1, -getBounciness());
        }
        this.setVelocity(vec3d1);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            ItemStack newStack = HacksawItems.DYNAMITE_STICK.getDefaultStack();
            newStack.setCount(1);
            if (newStack.isOf(HacksawItems.DYNAMITE_STICK)) {
                ((DynamiteItem) newStack.getItem()).setFuse(newStack, getFuseTime());
                ((DynamiteItem) newStack.getItem()).setLit(newStack, true);
            }
            else {
                Hacksaw.LOGGER.info("Um, so for some reason, the dynamite item isn't dynamite???");
            }
            player.setStackInHand(hand, newStack);
            this.discard();
            return ActionResult.SUCCESS;
        }
        return super.interact(player, hand);
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
