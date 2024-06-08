package net.paulm.hacksawed.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

import java.util.List;

public class EchoShardItem extends Item {
    public EchoShardItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        float cooldown = cooldownTime(entity);
        if (cooldown <= 0.05) {
            for (int i = 0; i < stack.getCount(); i++) {
                float rand = world.getRandom().nextFloat();
                if (rand <= 0.1) {
                    BlockPos blockPos = world.getRandomPosInChunk(0, 0, 0, 0);
                    if (blockPos.getX() == Math.abs(entity.getBlockX() % 16) && blockPos.getZ() == Math.abs(entity.getBlockZ() % 16)) {
                        echo(world, entity);
                    }
                }
            }
        }
    }

    public static float cooldownTime(Entity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return 0f;
        }
        return ((PlayerEntity)entity).getItemCooldownManager().getCooldownProgress(Items.ECHO_SHARD, 0);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        echo(world, user);
        user.getItemCooldownManager().set(this, 600);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    public static void echo(World world, Entity entity) {
        //world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_WARDEN_SONIC_BOOM, SoundCategory.HOSTILE, 5.0f, 1.0f);
        //world.addParticle((ParticleEffect) ParticleTypes.SHRIEK, entity.getX(), entity.getY(), entity.getZ(), 0f, 0f, 0f);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.HOSTILE, 7.0f, 1.0f);
        world.emitGameEvent(GameEvent.RESONATE_15, entity.getPos(), GameEvent.Emitter.of(entity));
        push(world, entity, 15);
        if (entity instanceof LivingEntity) {
            if (((LivingEntity) entity).hasStatusEffect(StatusEffects.GLOWING)) {
                ((LivingEntity) entity).getStatusEffect(StatusEffects.GLOWING).upgrade(new StatusEffectInstance(StatusEffects.GLOWING, 600, 1));
            }
            else {((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 600, 1));}
        }
    }

    //Applying knockback using minecraft explosion code
    public static void push(World world, Entity entity, float force) {
        int k = MathHelper.floor(entity.getX() - 8);
        int l = MathHelper.floor(entity.getX() + 8);
        int r = MathHelper.floor(entity.getY() - 8);
        int s = MathHelper.floor(entity.getY() + 8);
        int t = MathHelper.floor(entity.getZ() - 8);
        int u = MathHelper.floor(entity.getZ() + 8);
        List<Entity> list = world.getOtherEntities(entity, new Box(k, r, t, l, s, u));
        Vec3d vec3d = new Vec3d(entity.getX(), entity.getY(), entity.getZ());
        for (int v = 0; v < list.size(); ++v) {
            double z;
            double y;
            double x;
            double aa;
            double w;
            Entity target = list.get(v);
            //there was 'target.isImmuneToExplosion() || ' in the if statement but i removed it because i didnt want to bother adding an explosion
            if (!((w = Math.sqrt(target.squaredDistanceTo(vec3d)) / (double)8) <= 1.0) || (aa = Math.sqrt((x = target.getX() - entity.getX()) * x + (y = (target instanceof TntEntity ? target.getY() : target.getEyeY()) - entity.getY()) * y + (z = target.getZ() - entity.getZ()) * z)) == 0.0) continue;
            x /= aa;
            y /= aa;
            z /= aa;
            double ab = Explosion.getExposure(vec3d, target);
            double ac = (1.0 - w) * (1 + ab);
            Vec3d vec3d2 = new Vec3d(x * (ac * 2.0), y * (ac*1.0), z * (ac*2.0));
            target.setVelocity(target.getVelocity().add(vec3d2));
            //if (!(target instanceof PlayerEntity) || (playerEntity = (PlayerEntity)target).isSpectator() || playerEntity.isCreative() && playerEntity.getAbilities().flying) continue;
            //this.affectedPlayers.put(playerEntity, vec3d2);
        }
    }
}
