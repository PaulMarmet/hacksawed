package net.paulm.hacksaw.mixin;

import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemEntity.class)
public class ItemMixin {
	public boolean isImmuneToExplosion() {
		return true;
	}

}