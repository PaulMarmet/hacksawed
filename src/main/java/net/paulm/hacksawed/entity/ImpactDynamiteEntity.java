package net.paulm.hacksawed.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class ImpactDynamiteEntity extends DynamiteEntity {

    public ImpactDynamiteEntity(EntityType<? extends ImpactDynamiteEntity> entityType, World world) {
        super(entityType, world);
    }

    public ImpactDynamiteEntity(EntityType<? extends ImpactDynamiteEntity> entityType, LivingEntity owner, World world, ItemStack item) {
        super(entityType, owner, world, item);
        ItemStack tempItem = item.copy();
        tempItem.setCount(1);
        this.setItem(tempItem);
    }

    public ImpactDynamiteEntity(EntityType<? extends ImpactDynamiteEntity> entityType, double x, double y, double z, World world) {
        super(entityType, x, y, z, world);
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!this.getWorld().isClient()) {
            if (this.getItem() != null) {
                player.giveItemStack(this.getItem());
            } else {
                player.giveItemStack(new ItemStack(getDefaultItem(), 1));
            }
        }
        this.discard();
        return ActionResult.SUCCESS;
    }


    //apparently it sometimes does a bounce so im making it explode if it somehow does
    @Override
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
    }

}
