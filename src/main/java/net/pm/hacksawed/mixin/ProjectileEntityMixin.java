package net.pm.hacksawed.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PersistentProjectileEntity.class)
public abstract class ProjectileEntityMixin {

    @ModifyArgs(method = "<init>(Lnet/minecraft/entity/EntityType;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;<init>(Lnet/minecraft/entity/EntityType;DDDLnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V"))
    private static void giveItemAOwner(Args args, @Local LivingEntity owner) {
        ItemStack stack = args.get(5);
        ItemStack shotFrom = args.get(6);
        stack.setHolder(owner);
        shotFrom.setHolder(owner);
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/EntityType;DDDLnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setCustomName(Lnet/minecraft/text/Text;)V"))
    private void preSetOwner(EntityType<? extends PersistentProjectileEntity> type, double x, double y, double z, World world, ItemStack stack, ItemStack weapon, CallbackInfo ci) {
        ((PersistentProjectileEntity)(Object)this).setOwner(stack.getHolder());
    }
}
