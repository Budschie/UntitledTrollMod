package de.budschie.untitledtrollmod.main;

import de.budschie.untitledtrollmod.entities.classes.entities.SheepHopper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class Events
{
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
		}
	}
}
