package net.pm.hacksawed.entity;

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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.pm.hacksawed.HacksawedConfig;
import net.pm.hacksawed.item.HacksawedItems;

public class BouncyBallEntity extends ThrownItemEntity {
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
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ITEM, new ItemStack(this.getDefaultItem(), 1));
    }


    public void tick() {
        //Doing the super.tick() but not the position updating
        Vec3d oldPos = this.getPos();
        super.tick();
        this.setPosition(oldPos);
        //And now, the supposedly actually functional collisions
        this.bouncyBallCollision(MovementType.SELF, this.getVelocity());

        //Plop back into item if not moving for a bit
        this.stillCheck();
    }

    public void stillCheck() {
        if (this.getVelocity().length() <= 0.05) {
            this.stillTime++;
        }
        else {
            this.stillTime = 0;
        }
        if (this.stillTime >= 40) {
            this.stillAct();
        }
    }

    public void stillAct() {
        if (this.getStack() != null) {
            this.returnToItem();
        }
    }

    //The bounciness of the collision
    public static float getBounciness() {
        return HacksawedConfig.bouncyBallBounciness;
    }
    //The proportion of velocity kept when colliding vertically
    public static float getDrag() {
        return HacksawedConfig.bouncyBallDrag;
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
        if (this.getStack() != null && this.getStack().getCount() != 0) {
            this.dropStack(this.getStack());
        } else {
            this.dropStack(new ItemStack(this.getDefaultItem(), 1));
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
            if (this.getStack() != null && !this.getStack().isEmpty()) {
                added = player.giveItemStack(this.getStack());
            } else {
                added = player.giveItemStack(new ItemStack(this.getDefaultItem(), 1));
            }
        }
        if (added) {
            this.discard();
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    protected Item getDefaultItem() {
        return HacksawedItems.BOUNCY_BALL;
    }
}
