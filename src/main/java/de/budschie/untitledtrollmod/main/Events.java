package de.budschie.untitledtrollmod.main;

import java.util.Arrays;
import java.util.HashSet;

import de.budschie.untitledtrollmod.caps.aggressive_animal.AggressiveAnimalProvider;
import de.budschie.untitledtrollmod.caps.crouch_lock.CrouchLockProvider;
import de.budschie.untitledtrollmod.caps.crouch_lock.ICrouchLock;
import de.budschie.untitledtrollmod.caps.fake_xray.FakeXrayProvider;
import de.budschie.untitledtrollmod.effects.MobEffectRegistry;
import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.entities.classes.entities.SheepHopper;
import de.budschie.untitledtrollmod.networking.MainNetworkChannel;
import de.budschie.untitledtrollmod.networking.packets.CrouchLockSync;
import de.budschie.untitledtrollmod.utils.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
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
			Arrays.asList(EntityType.SHEEP, EntityType.CHICKEN, EntityType.HORSE, EntityType.PIG, EntityType.COW, EntityRegistry.SHEEP_HOPPER.get(), EntityType.MULE, EntityType.DONKEY, EntityType.FOX, EntityType.GOAT)));
	
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
		if (cap.getCrouchLockedTicks() == -1 && event.player.getRandom().nextInt(500) == 0 && event.side == LogicalSide.SERVER)
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
	
	@SubscribeEvent
	public static void onPlayerInteractWithEntity(PlayerInteractEvent.EntityInteract event)
	{
		if(!event.getPlayer().level.isClientSide() && event.getItemStack().getItem() == Items.HOPPER && event.getTarget().getClass() == Sheep.class)
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
			
			EntityType.SHEEP.hashCode();
		}
	}
	
	@SubscribeEvent
	public static void onEntityHurt(LivingHurtEvent event)
	{
		if(!event.getEntityLiving().level.isClientSide() && WHITELIST_TROLL_ATTACK.resolve().get().contains(event.getEntity().getType()) && event.getEntity().level.getRandom().nextInt(5) < 5)
		{
			PathfinderMob mob = (PathfinderMob) event.getEntity();
			EntityUtil.makeEntityViolent(mob);
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
