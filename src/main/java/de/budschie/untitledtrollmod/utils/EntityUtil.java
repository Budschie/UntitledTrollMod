package de.budschie.untitledtrollmod.utils;

import de.budschie.untitledtrollmod.caps.aggressive_animal.AggressiveAnimalProvider;
import de.budschie.untitledtrollmod.caps.aggressive_animal.IAggressiveAnimal;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.common.util.LazyOptional;

public class EntityUtil
{
	/** Calling this method makes the given PathfinderMob violent (if it isn't violent already). **/
	public static void makeEntityViolent(PathfinderMob pathfinderMob)
	{
		LazyOptional<IAggressiveAnimal> aggressiveAnimal = pathfinderMob.getCapability(AggressiveAnimalProvider.CAP);
		
		if(aggressiveAnimal.isPresent() && !aggressiveAnimal.resolve().get().isAggressive())
		{				
			pathfinderMob.setCustomName(new TextComponent("Violent ").append(new TranslatableComponent(pathfinderMob.getType().getDescriptionId())));
			pathfinderMob.setCustomNameVisible(true);
			
			pathfinderMob.level.playSound(null, pathfinderMob.getX(), pathfinderMob.getY(), pathfinderMob.getZ(), Accessors.getAmbientSound(pathfinderMob), SoundSource.HOSTILE, 2, 0);
			
			aggressiveAnimal.resolve().get().setAggressive(true, false);
		}
	}
	
	/** Returns whether the given entity is violent or not. **/
	public static boolean isViolent(Entity entity)
	{
		LazyOptional<IAggressiveAnimal> aggressiveAnimal = entity.getCapability(AggressiveAnimalProvider.CAP);
		
		if(aggressiveAnimal.isPresent())
			return aggressiveAnimal.resolve().get().isAggressive();
		
		return false;
	}
}
