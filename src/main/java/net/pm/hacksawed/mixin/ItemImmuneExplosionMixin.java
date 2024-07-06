package net.pm.hacksawed.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.pm.hacksawed.HacksawedConfig;
import net.pm.hacksawed.enchantment.HacksawedEnchantTags;
import net.pm.hacksawed.item.HacksawedItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemImmuneExplosionMixin extends EntityMixin{

	@Shadow public abstract ItemStack getStack();

	@Override
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {
		if (HacksawedConfig.explosionProofItems == HacksawedConfig.SelectionType.ALL) {
			cir.setReturnValue(true);
		} else if (HacksawedConfig.explosionProofItems == HacksawedConfig.SelectionType.SOME) {
			boolean hasTag = this.getStack().isIn(HacksawedItemTags.EXPLOSION_PROOF);
			boolean isProtected = EnchantmentHelper.hasEnchantments(this.getStack()) && EnchantmentHelper.hasAnyEnchantmentsIn(this.getStack(), HacksawedEnchantTags.EXPLOSION_RELATED);
			cir.setReturnValue(cir.getReturnValue() || hasTag || (isProtected && HacksawedConfig.explosionProofBlastProt));
		} else {
			boolean isProtected = EnchantmentHelper.hasEnchantments(this.getStack()) && EnchantmentHelper.hasAnyEnchantmentsIn(this.getStack(), HacksawedEnchantTags.EXPLOSION_RELATED);
			cir.setReturnValue(cir.getReturnValue() || (isProtected && HacksawedConfig.explosionProofBlastProt));
		}
	}

}