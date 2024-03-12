package net.paulm.hacksaw.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.paulm.hacksaw.enchantment.HacksawEnchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public class CrossbowMixin {

	private static float factor = -0.3f;

	//@Inject(method = "shoot(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;FZFFF)V", at = @At(value="INVOKE", target="Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
	@Inject(method = "Lnet/minecraft/item/CrossbowItem;shoot(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;FZFFF)V", at = @At(value="INVOKE", target="Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
	private static void recoil(World world, LivingEntity shooter, Hand hand, ItemStack crossbow, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated, CallbackInfo ci) {
		int recoilLvl = EnchantmentHelper.getLevel(HacksawEnchantments.RECOIL, crossbow);
		if (recoilLvl > 0) {
			Vec3d iVec = (new Vec3d(shooter.getRotationVec(1.0f).toVector3f())).multiply(recoilLvl*factor);
			shooter.addVelocity(iVec.x, iVec.y, iVec.z);
			if (shooter instanceof ServerPlayerEntity) {
				((ServerPlayerEntity) shooter).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(shooter));
			}
		}
	}
}