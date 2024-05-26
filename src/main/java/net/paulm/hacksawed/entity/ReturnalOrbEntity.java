package net.paulm.hacksawed.entity;

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
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.paulm.hacksawed.Hacksawed;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.item.HacksawedItems;

import java.util.Objects;

public class ReturnalOrbEntity extends BouncyBallEntity {

    private static final TrackedData<Boolean> RETURN = DataTracker.registerData(ReturnalOrbEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public ReturnalOrbEntity(EntityType<? extends ReturnalOrbEntity> entityType, World world) {
        super(entityType, world);
    }

    public ReturnalOrbEntity(EntityType<? extends ReturnalOrbEntity> entityType, LivingEntity owner, World world, ItemStack item) {
        super(entityType, owner, world, item);
        if (this.getItem().isEmpty()) {
            this.setItem(new ItemStack(this.getDefaultItem()));
        }
    }

    public ReturnalOrbEntity(EntityType<? extends ReturnalOrbEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(RETURN, false);
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
        if (this.getItem() != null) {
            if (this.getOwner() != null) {
                this.setReturn(true);
            } else {
                this.returnToItem();
            }
        }
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
                this.setVelocity(posDiffRatio.multiply(HacksawedConfig.returnalOrbVelocity));
            } else {
                this.setVelocity(this.getVelocity().add(posDiffRatio.multiply(HacksawedConfig.returnalOrbVelocity)));
            }
            this.setPosition(this.getPos().add(this.getVelocity()));
        } else {
            this.returnToItem();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (this.getItem() != null && this.getOwner() != null) {
            boolean added = false;
            if (!this.getWorld().isClient() && entityHitResult.getEntity() instanceof PlayerEntity && entityHitResult.getEntity().getUuid() == this.getOwner().getUuid()) {
                Hacksawed.LOGGER.info(String.valueOf(this.getItem()));
                if (this.getItem().isOf(Items.AIR) || this.getItem().getCount() < 1) {
                    added = ((PlayerEntity) entityHitResult.getEntity()).giveItemStack(new ItemStack(this.getDefaultItem()));
                } else {
                    added = ((PlayerEntity) entityHitResult.getEntity()).giveItemStack(this.getItem());
                }
            }
            if (added) {
                this.discard();
                return;
            }
        }
        if (this.getItem() != null && entityHitResult.getEntity() instanceof LivingEntity) {
            entityHitResult.getEntity().damage(entityHitResult.getEntity().getDamageSources().create(DamageTypes.TRIDENT), this.getItem().getDamage()+1);
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
