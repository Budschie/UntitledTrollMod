package de.budschie.untitledtrollmod.main;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import de.budschie.untitledtrollmod.caps.aggressive_animal.AggressiveAnimalProvider;
import de.budschie.untitledtrollmod.caps.aggressive_animal.IAggressiveAnimal;
import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.entities.classes.entities.SheepHopper;
import de.budschie.untitledtrollmod.utils.Accessors;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class Events
{
	public static final LazyOptional<HashSet<EntityType<? extends PathfinderMob>>> WHITELIST_TROLL_ATTACK = LazyOptional.of(() -> new HashSet<>(
			Arrays.asList(EntityType.SHEEP, EntityType.CHICKEN, EntityType.HORSE, EntityType.PIG, EntityType.COW, EntityRegistry.SHEEP_HOPPER.get(), EntityType.MULE, EntityType.DONKEY)));
	
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
			
			// We give the animal double the speed and let it see through walls muhahahahahaaaaa
			
			LazyOptional<IAggressiveAnimal> aggressiveAnimal = mob.getCapability(AggressiveAnimalProvider.CAP);
			
			if(aggressiveAnimal.isPresent() && !aggressiveAnimal.resolve().get().isAggressive())
			{				
				mob.setCustomName(new TextComponent("Violent ").append(new TranslatableComponent(mob.getType().getDescriptionId())));
				mob.setCustomNameVisible(true);
				
				event.getEntity().level.playSound(null, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Accessors.getAmbientSound(mob), SoundSource.HOSTILE, 2, 0);
				
				aggressiveAnimal.resolve().get().setAggressive(true);
			}
		}
	}
	
	@SubscribeEvent
	public static void onAttachingCaps(AttachCapabilitiesEvent<Entity> event)
	{
		if(WHITELIST_TROLL_ATTACK.resolve().get().contains(event.getObject().getType()))
			event.addCapability(AggressiveAnimalProvider.CAP_NAME, new AggressiveAnimalProvider((PathfinderMob) event.getObject()));
	}
}
