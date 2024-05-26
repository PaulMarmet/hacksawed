package net.paulm.hacksawed.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireChargeItem.class)
public class FireChargeMixin extends ItemUseMixin{


	@Override
	protected TypedActionResult<ItemStack> doAThing(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		ItemStack itemStack = user.getStackInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
		if (!world.isClient) {
			Vec3d vec = user.getRotationVec(1.0f);
			FireballEntity fireballEntity = new FireballEntity(world, (LivingEntity)user, vec.getX(), vec.getY(), vec.getZ(), 1);
			fireballEntity.setPosition(user.getEyePos());
			world.spawnEntity(fireballEntity);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
		if (!user.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}
		return TypedActionResult.success(itemStack, world.isClient());
	}
}