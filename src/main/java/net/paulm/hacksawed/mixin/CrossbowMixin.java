package net.paulm.hacksawed.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.enchantment.HacksawedEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RangedWeaponItem.class)
public class CrossbowMixin {

	@Inject(method = "shootAll", at = @At(value="INVOKE", target="Lnet/minecraft/item/RangedWeaponItem;shoot(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/projectile/ProjectileEntity;IFFFLnet/minecraft/entity/LivingEntity;)V"))
	private void recoil(World world, LivingEntity shooter, Hand hand, ItemStack stack, List<ItemStack> projectiles, float speed, float divergence, boolean critical, LivingEntity target, CallbackInfo ci) {
		if (!stack.hasEnchantments()) {return;}
		int recoilLvl = EnchantmentHelper.getLevel(HacksawedEnchantments.RECOIL, stack);
		if (recoilLvl > 0) {
			Vec3d iVec = (new Vec3d(shooter.getRotationVec(1.0f).toVector3f())).multiply(recoilLvl* HacksawedConfig.recoilAmount);
			shooter.addVelocity(iVec.x, iVec.y, iVec.z);
			if (shooter instanceof ServerPlayerEntity) {
				((ServerPlayerEntity) shooter).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(shooter));
			}
		}
	}
}