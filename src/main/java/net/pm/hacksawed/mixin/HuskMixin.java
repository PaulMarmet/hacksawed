package net.pm.hacksawed.mixin;

import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.pm.hacksawed.HacksawedConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HuskEntity.class)
public class HuskMixin {

    @Inject(method = "convertInWater", at = @At(value= "TAIL"))
	public void dropExtra(CallbackInfo ci) {
		if (HacksawedConfig.drowningDrops) {
			HuskEntity entity = ((HuskEntity) (Object) this);
			RegistryKey<LootTable> registryKey = RegistryKey.of(RegistryKeys.LOOT_TABLE, Registries.ENTITY_TYPE.getId(entity.getType()).withPrefixedPath("entities/drown/"));

			LootTable lootTable = entity.getWorld().getServer().getReloadableRegistries().getLootTable(registryKey);
			LootContextParameterSet.Builder builder = (new LootContextParameterSet.Builder((ServerWorld) entity.getWorld())).add(LootContextParameters.THIS_ENTITY, entity).add(LootContextParameters.ORIGIN, entity.getPos()).add(LootContextParameters.DAMAGE_SOURCE, null);

			LootContextParameterSet lootContextParameterSet = builder.build(LootContextTypes.ENTITY);
			lootTable.generateLoot(lootContextParameterSet, entity.getLootTableSeed(), entity::dropStack);
		}
	}

}