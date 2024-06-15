package net.paulm.hacksawed.enchantment;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.paulm.hacksawed.Hacksawed;
import net.paulm.hacksawed.HacksawedConfig;

public record RecoilEnchantmentEffect(EnchantmentLevelBasedValue knockback) implements EnchantmentEntityEffect {
    public static final MapCodec<RecoilEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("knockback").forGetter((recoilEnchantmentEffect) -> {
            return recoilEnchantmentEffect.knockback;
        })).apply(instance, RecoilEnchantmentEffect::new);
    });

    public RecoilEnchantmentEffect(EnchantmentLevelBasedValue knockback) {
        this.knockback = knockback;
    }

    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if(user instanceof ProjectileEntity) {
            Entity owner = ((ProjectileEntity) user).getOwner();
            if (owner != null) {
                Vec3d iVec = (new Vec3d(owner.getRotationVec(1.0f).toVector3f())).multiply(level * HacksawedConfig.recoilAmount);
                owner.addVelocity(iVec.x, iVec.y, iVec.z);
                if (owner instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity) owner).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(owner));
                }
            } else {
                Hacksawed.LOGGER.info("The owner is null...");
            }
        } else {
            Hacksawed.LOGGER.info("That's not a projectile entity??? "+user);
        }
        Hacksawed.LOGGER.info(user+" did something related to recoil, its owner is theoretically "+context.owner());
    }

    public MapCodec<RecoilEnchantmentEffect> getCodec() {
        return CODEC;
    }

    public EnchantmentLevelBasedValue knockback() {
        return this.knockback;
    }
}
