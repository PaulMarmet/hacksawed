package net.paulm.hacksaw.entity;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.paulm.hacksaw.Hacksaw;
import net.paulm.hacksaw.HacksawConfig;
import net.paulm.hacksaw.item.HacksawItems;

import java.util.Objects;

public class BouncyBallEntity extends ThrownItemEntity {

    private static final TrackedData<Boolean> RETURN = DataTracker.registerData(BouncyBallEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(BouncyBallEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    public int stillTime = 0;

    public BouncyBallEntity(EntityType<? extends BouncyBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public BouncyBallEntity(EntityType<? extends BouncyBallEntity> entityType, LivingEntity owner, World world, ItemStack item) {
        super(entityType, owner, world);
        ItemStack tempItem = item.copy();
        tempItem.setCount(1);
        this.setItem(tempItem);
    }

    public BouncyBallEntity(EntityType<? extends BouncyBallEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(RETURN, false);
        this.dataTracker.startTracking(ITEM, new ItemStack(getDefaultItem()));
    }

    public boolean getReturn() {
        return this.dataTracker.get(RETURN);
    }
    public void setReturn(boolean val) {
        this.dataTracker.set(RETURN, val);
    }


    public void tick() {
        //If returning make return, otherwise, make collide
        if(this.getReturn() && this.getOwner() != null) {
            this.baseTick();
            this.moveTowardsOwner();
            HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
            if (hitResult.getType() != HitResult.Type.MISS) {
                this.onCollision(hitResult);
            }
        } else {
            //Doing the super.tick() but not the position updating
            Vec3d oldPos = this.getPos();
            super.tick();
            this.setPosition(oldPos);
            //And now, the supposedly actually functional collisions
            this.bouncyBallCollision(MovementType.SELF, this.getVelocity());
        }
        //Plop back into item if not moving for a bit and is not on its return
        if (this.getVelocity().length() <= 0.05) {
            this.stillTime++;
        }
        else {
            this.stillTime = 0;
        }
        if (this.stillTime >= 40) {
            if (this.getItem() != null) {
                if (0 < EnchantmentHelper.getLevel(Enchantments.LOYALTY, this.getItem()) && this.getOwner() != null) {
                    this.setReturn(true);
                } else {
                    this.returnToItem();
                }
            }
        }

    }

    public static float getReturnVelocity() {
        return 0.1f;
    }

    public void moveTowardsOwner() {
        Vec3d owner = Objects.requireNonNull(this.getOwner()).getPos();
        if (owner != null) {
            Vec3d ball = this.getPos();
            Vec3d posDiffRatio = new Vec3d(owner.getX() - ball.getX(), owner.getY() - ball.getY(), owner.getZ() - ball.getZ()).normalize();
            //Make redirect
            int redirect = 0;
            redirect += (this.getVelocity().getX() * posDiffRatio.getX()) < 0 ? 1 : 0;
            redirect += (this.getVelocity().getY() * posDiffRatio.getY()) < 0 ? 1 : 0;
            redirect += (this.getVelocity().getZ() * posDiffRatio.getZ()) < 0 ? 1 : 0;
            if ((redirect == 3 && ball.distanceTo(owner) >= 5) || (redirect >= 2 && ball.distanceTo(owner) >= 25)) {
                this.setVelocity(posDiffRatio.multiply(getReturnVelocity()));
            } else {
                this.setVelocity(this.getVelocity().add(posDiffRatio.multiply(getReturnVelocity())));
            }
            this.setPosition(this.getPos().add(this.getVelocity()));
        } else {
            this.returnToItem();
        }
    }

    //The bounciness of the collision
    public static float getBounciness() {
        return HacksawConfig.bouncyBallBounciness;
    }
    //The proportion of velocity kept when colliding vertically
    public static float getDrag() {
        return HacksawConfig.bouncyBallDrag;
    }

    public void bouncyBallCollision(MovementType movementType, Vec3d movement) {
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

    public void returnToItem() {
        if (this.getItem() != null && this.getItem().getCount() != 0) {
            Hacksaw.LOGGER.info(String.valueOf(this.getItem()));
            this.dropStack(this.getItem());
        } else {
            this.dropStack(new ItemStack(HacksawItems.BOUNCY_BALL, 1));
        }
        this.discard();
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        boolean added = false;
        if (!this.getWorld().isClient()) {
            if (this.getItem() != null && !this.getItem().isEmpty()) {
                Hacksaw.LOGGER.info(" "+this.getItem());
                added = player.giveItemStack(this.getItem());
            } else {
                added = player.giveItemStack(new ItemStack(this.getDefaultItem(), 1));
                Hacksaw.LOGGER.info(added+" "+this.getDefaultItem());
            }
        }
        if (added) {
            this.discard();
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    public float entityVelocityTransfer() {
        return 0.4f;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (this.getItem() != null && this.getOwner() != null) {
            if (this.isOwner(entityHitResult.getEntity()) && 0 < EnchantmentHelper.getLevel(Enchantments.LOYALTY, this.getItem())) {
                boolean added = false;
                if (!this.getWorld().isClient() && entityHitResult.getEntity() instanceof PlayerEntity) {
                    added = ((PlayerEntity) entityHitResult.getEntity()).giveItemStack(this.getItem());
                }
                if (added) {
                    this.discard();
                }
                return;
            }
        }
        if (this.getItem() != null && entityHitResult.getEntity() instanceof LivingEntity) {
            if (0 < EnchantmentHelper.getLevel(Enchantments.LOYALTY, this.getItem())) {
                entityHitResult.getEntity().damage(entityHitResult.getEntity().getDamageSources().create(DamageTypes.TRIDENT), this.getItem().getDamage()+1);
                entityHitResult.getEntity().addVelocity(this.getVelocity().multiply(entityVelocityTransfer()));
                this.setReturn(true);
                this.setVelocity(0, 0, 0);
                return;
            }
        }

    }

    @Override
    protected Item getDefaultItem() {
        return HacksawItems.BOUNCY_BALL;
    }
}
