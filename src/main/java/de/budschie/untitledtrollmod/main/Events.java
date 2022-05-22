package de.budschie.untitledtrollmod.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import de.budschie.untitledtrollmod.caps.aggressive_animal.AggressiveAnimalProvider;
import de.budschie.untitledtrollmod.caps.crouch_lock.CrouchLockProvider;
import de.budschie.untitledtrollmod.caps.crouch_lock.ICrouchLock;
import de.budschie.untitledtrollmod.caps.fake_xray.FakeXrayProvider;
import de.budschie.untitledtrollmod.effects.MobEffectRegistry;
import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.entities.classes.entities.SheepHopper;
import de.budschie.untitledtrollmod.items.ItemRegistry;
import de.budschie.untitledtrollmod.networking.MainNetworkChannel;
import de.budschie.untitledtrollmod.networking.packets.CrouchLockSync;
import de.budschie.untitledtrollmod.utils.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.network.PacketDistributor;

@EventBusSubscriber
public class Events
{
	public static final LazyOptional<HashSet<EntityType<? extends PathfinderMob>>> WHITELIST_TROLL_ATTACK = LazyOptional.of(() -> new HashSet<>(
			Arrays.asList(EntityType.SHEEP, EntityType.CHICKEN, EntityType.HORSE, EntityType.PIG, EntityType.COW, EntityRegistry.SHEEP_HOPPER.get(), EntityType.MULE, EntityType.DONKEY, EntityType.FOX, EntityType.GOAT, EntityType.PILLAGER)));
	
	public static final LazyOptional<HashSet<Item>> CROUCH_LOCK_CRITERIUM = LazyOptional.of(() ->
	{
		HashSet<Item> set = new HashSet<>();
		set.add(ItemRegistry.ANTI_GRAVITATIONAL_DEVICE.get());
		
		return set;
	});
	
	/** This method handles the jesus effect and the crouch locking. **/
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event)
	{
		// Jesus may not swim. Make him float up the water
		if (event.player.hasEffect(MobEffectRegistry.JESUS_JUICE_EFFECT.get()))
		{			
			FluidState state = event.player.level.getFluidState(new BlockPos(event.player.position().add(0, 0.5, 0)));
			
			if(event.player.isAffectedByFluids() && state.getType() == Fluids.WATER)
			{
				event.player.setDeltaMovement(event.player.getDeltaMovement().scale(1).add(0.0D, 0.05D, 0.0D));
			}
		}
		
		// The code is a bit ugly but who cares lulw
		LazyOptional<ICrouchLock> capOpt = event.player.getCapability(CrouchLockProvider.CAP);
		
		if(!capOpt.isPresent())
			return;
		
		ICrouchLock cap = capOpt.resolve().get();
		
		// Set cap to enabled and lock
		if (cap.getCrouchLockedTicks() == -1 && event.player.getRandom().nextInt(90) == 0 && event.side == LogicalSide.SERVER && event.player.getInventory().hasAnyOf(CROUCH_LOCK_CRITERIUM.resolve().get()))
		{
			int ticksToCrouchLock = event.player.getRandom().nextInt(30) + 100;

			cap.setCrouchLockedTicks(ticksToCrouchLock);

			MainNetworkChannel.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.player), new CrouchLockSync.CrouchLockPacket(ticksToCrouchLock));
		}
		
		updateCrouchCap(cap, event.player);
	}
	
	public static void updateCrouchCap(ICrouchLock cap, Player player)
	{
		// Check if our sneaking is stunned
		if(cap.getCrouchLockedTicks() > -1)
		{
			if(cap.getCrouchLockedTicks() > 0)
			{
				if((player.isDiscrete()) && player.getForcedPose() == null)
				{
					player.setForcedPose(Pose.STANDING);
				}
				else if(!player.isDiscrete() && player.getForcedPose() != null)
				{
					player.setForcedPose(null);
				}
			}
			
			
			// Decrement crouch lock
			cap.setCrouchLockedTicks(cap.getCrouchLockedTicks() - 1);			
		}
		
		if(cap.getCrouchLockedTicks() == 0 && player.getForcedPose() == Pose.STANDING)
			player.setForcedPose(null);
	}
	
	/** This event handles the hopper sheep creation and the right clicking of certain entities with a stick **/
	@SubscribeEvent
	public static void onPlayerInteractWithEntity(PlayerInteractEvent.EntityInteract event)
	{
		if(!event.getPlayer().level.isClientSide())
		{
			if(event.getItemStack().getItem() == Items.HOPPER && event.getTarget().getClass() == Sheep.class)
			{
				Sheep sheep = (Sheep) event.getTarget();
				
				SheepHopper sheepHopper = new SheepHopper(sheep);
				sheepHopper.copyPosition(sheep);
				
				event.getWorld().addFreshEntity(sheepHopper);
				sheep.discard();
				event.getPlayer().level.addFreshEntity(sheepHopper);
				
				event.getWorld().playSound(null, sheep.getX(), sheep.getY(), sheep.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.NEUTRAL, 1, 0);
				event.getWorld().playSound(null, sheep.getX(), sheep.getY(), sheep.getZ(), SoundEvents.SHEEP_AMBIENT, SoundSource.NEUTRAL, 1, 0);
				event.getWorld().playSound(null, sheep.getX(), sheep.getY(), sheep.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.NEUTRAL, 1, 2);
				
				if(!event.getPlayer().getAbilities().instabuild)
					event.getItemStack().setCount(event.getItemStack().getCount() - 1);
				
				event.setCancellationResult(InteractionResult.SUCCESS);
			}
			else if(event.getItemStack().getItem() == Items.STICK && WHITELIST_TROLL_ATTACK.resolve().get().contains(event.getTarget().getType()) && !EntityUtil.isViolent(event.getTarget()))
			{
				if(event.getTarget() instanceof AgeableMob ageableMob)
				{
					if(ageableMob.isBaby())
						return;
				}
				
				if(!event.getPlayer().getAbilities().instabuild)
					event.getItemStack().setCount(event.getItemStack().getCount() - 1);
				
				PathfinderMob mob = (PathfinderMob) event.getTarget();
				EntityUtil.makeEntityViolent(mob);
				event.getPlayer().level.playSound(null, mob.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1f, 0f);
				
				event.setCancellationResult(InteractionResult.SUCCESS);
			}
		}
	}
	
	/** This method handles the case where animals get angry when punched. **/
	@SubscribeEvent
	public static void onEntityHurt(LivingHurtEvent event)
	{
		if (!event.getEntityLiving().level.isClientSide() && !event.getSource().isCreativePlayer() && event.getSource().getEntity() != null && event.getSource().getEntity().getType() == EntityType.PLAYER
				&& WHITELIST_TROLL_ATTACK.resolve().get().contains(event.getEntity().getType()) && event.getEntity().level.getRandom().nextInt(100) < 5)
		{
			// Never casted null before kekw
			List<Entity> foundEntities = event.getEntity().getLevel().getEntities((Entity) null,
					new AABB(event.getEntity().blockPosition().offset(-6, -3, -6), event.getEntity().blockPosition().offset(6, 3, 6)),
					entity -> WHITELIST_TROLL_ATTACK.resolve().get().contains(entity.getType()) && !EntityUtil.isViolent(entity));
			
			Random rand = event.getEntity().getLevel().getRandom();
			
			// Pick a random number between 1 and 6
			int angryEntitiesLeft = rand.nextInt(3, 9);
			
			// Choose random entities in the foundEntities list x times and make them angry,
			// then remove them from the list so that we cannot choose them again.
			while(angryEntitiesLeft > 0 & foundEntities.size() > 0)
			{
				int violentIndex = rand.nextInt(foundEntities.size());
				EntityUtil.makeEntityViolent((PathfinderMob)foundEntities.get(violentIndex));
				foundEntities.remove(violentIndex);
				
				angryEntitiesLeft--;
			}
			
			// The hurt entity will definitely turn aggressive
			if(!EntityUtil.isViolent(event.getEntity()))
			{
				EntityUtil.makeEntityViolent((PathfinderMob) event.getEntity());
			}
		}
	}
	
	@SubscribeEvent
	public static void onJoinedGame(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(event.getPlayer() != null)
		{
			event.getPlayer().getCapability(CrouchLockProvider.CAP).ifPresent(cap ->
			{
				if(cap.getCrouchLockedTicks() > -1)
				{
					MainNetworkChannel.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()), new CrouchLockSync.CrouchLockPacket(cap.getCrouchLockedTicks()));
				}
			});
		}
	}
	
	@SubscribeEvent
	public static void onAttachingCaps(AttachCapabilitiesEvent<Entity> event)
	{
		if(WHITELIST_TROLL_ATTACK.resolve().get().contains(event.getObject().getType()))
			event.addCapability(AggressiveAnimalProvider.CAP_NAME, new AggressiveAnimalProvider((PathfinderMob) event.getObject()));
		
		if(event.getObject() instanceof Player)
			event.addCapability(CrouchLockProvider.CAP_NAME, new CrouchLockProvider());
	}
	
	@SubscribeEvent
	public static void onAttachingCapsChunk(AttachCapabilitiesEvent<LevelChunk> event)
	{
		if(event.getObject().getLevel().isClientSide())
			event.addCapability(FakeXrayProvider.CAP_NAME, new FakeXrayProvider(event.getObject()));
	}
}
