package net.paulm.hacksaw.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.paulm.hacksaw.item.HacksawItems;

public class ImpactDynamiteEntity extends DynamiteEntity {

    private int fuseTime;
    private boolean onImpact;

    public ImpactDynamiteEntity(EntityType<? extends ImpactDynamiteEntity> entityType, World world) {
        super(entityType, world);
    }

    public ImpactDynamiteEntity(EntityType<? extends ImpactDynamiteEntity> entityType, LivingEntity owner, World world) {
        super(entityType, owner, world);
    }

    public ImpactDynamiteEntity(EntityType<? extends ImpactDynamiteEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isEmpty()) {
            ItemStack newStack = HacksawItems.IMPACT_DYNAMITE_STICK.getDefaultStack();
            newStack.setCount(1);
            player.setStackInHand(hand, newStack);
            this.discard();
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }


    //apparently it sometimes does a bounce so im maing it explode if it somehow does
    @Override
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
            h = 0.99f;
        }
        this.setVelocity(movement.multiply(h));
        if (!this.hasNoGravity()) {
            this.setVelocity(movement.x, movement.y - (double)this.getGravity(), movement.z);
        }

        movement = this.getVelocity();
        //Stuff from Entity move() for the actual collisions
        Vec3d vec3d;
        if ((g = (vec3d = Entity.adjustMovementForCollisions(this, movement = this.adjustMovementForSneaking(movement, movementType), this.getBoundingBox(), this.getWorld(), this.getWorld().getEntityCollisions(this, this.getBoundingBox().stretch(movement)))).lengthSquared()) > 1.0E-7) {
            BlockHitResult blockHitResult;
            if (this.fallDistance != 0.0f && g >= 1.0 && (blockHitResult = this.getWorld().raycast(new RaycastContext(this.getPos(), this.getPos().add(vec3d), RaycastContext.ShapeType.FALLDAMAGE_RESETTING, RaycastContext.FluidHandling.WATER, this))).getType() != HitResult.Type.MISS) {
                this.onLanding();
            }
            this.setPosition(this.getX() + vec3d.x, this.getY() + vec3d.y, this.getZ() + vec3d.z);
        }
    }


    @Override
    protected Item getDefaultItem() {
        return HacksawItems.IMPACT_DYNAMITE_STICK;
    }
}
