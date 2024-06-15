package net.paulm.hacksawed.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.EnchantmentTags;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.enchantment.HacksawedEnchantTags;
import net.paulm.hacksawed.item.HacksawedItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemFireproofMixin {

	@Shadow public abstract ItemStack getStack();

	@Inject(method = "isFireImmune()Z", at = @At(value="RETURN"), cancellable = true)
	public void isImmuneToFire(CallbackInfoReturnable<Boolean> cir) {
		if (HacksawedConfig.fireProofItems == HacksawedConfig.SelectionType.ALL) {
			cir.setReturnValue(true);
		} else if (HacksawedConfig.fireProofItems == HacksawedConfig.SelectionType.SOME) {
			boolean hasTag = this.getStack().isIn(HacksawedItemTags.FIREPROOF);
			boolean isProtected = EnchantmentHelper.hasAnyEnchantmentsIn(this.getStack(), HacksawedEnchantTags.FIRE_RELATED);
			cir.setReturnValue(cir.getReturnValue() || hasTag || (isProtected && HacksawedConfig.fireProofFireProt));
		} else {
			boolean isProtected = EnchantmentHelper.hasAnyEnchantmentsIn(this.getStack(), HacksawedEnchantTags.FIRE_RELATED);
			cir.setReturnValue(cir.getReturnValue() || (isProtected && HacksawedConfig.fireProofFireProt));
		}
	}

}