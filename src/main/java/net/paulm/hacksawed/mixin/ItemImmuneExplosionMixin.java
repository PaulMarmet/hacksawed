package net.paulm.hacksawed.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.paulm.hacksawed.HacksawedConfig;
import net.paulm.hacksawed.item.HacksawedItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemImmuneExplosionMixin extends ImmuneExplosionMixin{

	@Shadow public abstract ItemStack getStack();

	@Override
	public void isImmuneToExplosion(CallbackInfoReturnable<Boolean> cir) {
		if (HacksawedConfig.explosionProofItems == HacksawedConfig.SelectionType.ALL) {
			cir.setReturnValue(true);
		} else if (HacksawedConfig.explosionProofItems == HacksawedConfig.SelectionType.SOME) {
			boolean hasTag = this.getStack().isIn(HacksawedItemTags.EXPLOSION_PROOF);
			boolean hasBlastProt = this.getStack().hasEnchantments() && EnchantmentHelper.getLevel(Enchantments.BLAST_PROTECTION, this.getStack()) > 0;
			cir.setReturnValue(cir.getReturnValue() || hasTag || (hasBlastProt && HacksawedConfig.explosionProofBlastProt));
		} else {
			boolean hasBlastProt = this.getStack().hasEnchantments() && EnchantmentHelper.getLevel(Enchantments.BLAST_PROTECTION, this.getStack()) > 0;
			cir.setReturnValue(cir.getReturnValue() || (hasBlastProt && HacksawedConfig.explosionProofBlastProt));
		}
	}

}