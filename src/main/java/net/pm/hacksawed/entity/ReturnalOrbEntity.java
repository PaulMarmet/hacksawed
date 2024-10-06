package net.pm.hacksawed.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pm.hacksawed.HacksawedConfig;
import net.pm.hacksawed.item.HacksawedItems;

import java.util.Objects;

public class ReturnalOrbEntity extends BouncyBallEntity {

    private static final TrackedData<Boolean> RETURN = DataTracker.registerData(ReturnalOrbEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public ReturnalOrbEntity(EntityType<? extends ReturnalOrbEntity> entityType, World world) {
        super(entityType, world);
    }

    public ReturnalOrbEntity(EntityType<? extends ReturnalOrbEntity> entityType, LivingEntity owner, World world, ItemStack item) {
        super(entityType, owner, world, item);
        if (this.getStack().isEmpty()) {
            this.setItem(new ItemStack(this.getDefaultItem()));
        }
    }

    public ReturnalOrbEntity(EntityType<? extends ReturnalOrbEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(RETURN, false);
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
            super.tick();
        }
    }

    public void stillAct() {
        if (this.getStack() != null) {
            if (this.getOwner() != null) {
                this.setReturn(true);
            } else {
                this.returnToItem();
            }
        }
    }

    public void moveTowardsOwner() {
        Vec3d owner = Objects.requireNonNull(this.getOwner()).getEyePos();
        if (owner != null) {
            Vec3d ball = this.getEyePos();
            Vec3d posDiffRatio = new Vec3d(owner.getX() - ball.getX(), owner.getY() - ball.getY(), owner.getZ() - ball.getZ()).normalize();
            //Make redirect
            int redirect = 0;
            redirect += (this.getVelocity().getX() * posDiffRatio.getX()) < 0 ? 1 : 0;
            redirect += (this.getVelocity().getY() * posDiffRatio.getY()) < 0 ? 1 : 0;
            redirect += (this.getVelocity().getZ() * posDiffRatio.getZ()) < 0 ? 1 : 0;
            if ((redirect == 3 && ball.distanceTo(owner) >= 5) || (redirect >= 2 && ball.distanceTo(owner) >= 25)) {
                this.setVelocity(posDiffRatio.multiply(0.1f));
            } else {
                this.setVelocity(this.getVelocity().add(posDiffRatio.multiply(0.1f)));
            }
            this.setPosition(this.getPos().add(this.getVelocity()));
        } else {
            this.returnToItem();
        }
    }

    public void moveTowardsOwnerv2() {
        Vec3d vec3d = Objects.requireNonNull(this.getOwner()).getEyePos().subtract(this.getPos());
        int i = 2;
        this.setPos(this.getX() + (vec3d.x * 0.015 * (double)i), this.getY() + (vec3d.y * 0.015 * (double)i), this.getZ() + (vec3d.z * 0.015 * (double)i));
        if (this.getWorld().isClient) {
            this.lastRenderY = this.getY();
        }

        double d = 0.05 * (double)i;
        this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(d)));
//        if (this.returnTimer == 0) {
//            this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
//        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (this.getStack() != null && this.getOwner() != null) {
            boolean added = false;
            if (!this.getWorld().isClient() && entityHitResult.getEntity() == this.getOwner()) {
                if (this.getStack().isOf(Items.AIR) || this.getStack().getCount() < 1) {
                    added = ((PlayerEntity) entityHitResult.getEntity()).giveItemStack(new ItemStack(this.getDefaultItem()));
                } else {
                    added = ((PlayerEntity) entityHitResult.getEntity()).giveItemStack(this.getStack());
                }
            }
            if (added) {
                this.discard();
                return;
            }
        }
        if (this.getStack() != null && entityHitResult.getEntity() instanceof LivingEntity) {
            entityHitResult.getEntity().damage(entityHitResult.getEntity().getDamageSources().create(DamageTypes.TRIDENT), this.getStack().getDamage()+1);
            entityHitResult.getEntity().addVelocity(this.getVelocity().multiply(HacksawedConfig.returnalOrbVelTransfer));
            this.setReturn(true);
            this.setVelocity(0, 0, 0);
        }

    }

    @Override
    protected Item getDefaultItem() {
        return HacksawedItems.RETURNAL_ORB;
    }
}
