package de.budschie.untitledtrollmod.caps.aggressive_animal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;

public class AggressiveAnimal implements IAggressiveAnimal
{
	private boolean aggressive = false;
	private PathfinderMob owner;
	private boolean registeredAggressiveGoals = false;
	
	LazyOptional<MeleeAttackGoal> attackGoal;
	LazyOptional<NearestAttackableTargetGoal<Player>> nearestAttackableGoal;
	
	public AggressiveAnimal(PathfinderMob owner)
	{
		this.owner = owner;
		
		setupOptionals();
	}
	
	@Override
	public boolean isAggressive()
	{
		return aggressive;
	}

	@Override
	public void setAggressive(boolean value, boolean isLoadingWorld)
	{
		if(aggressive != value)
		{
			aggressive = value;
			
			if(value)
			{
				if(!isLoadingWorld)
				{
					registerAggressiveGoals();
				}
			}
			else
			{
				owner.goalSelector.removeGoal(attackGoal.resolve().get());
				owner.targetSelector.removeGoal(nearestAttackableGoal.resolve().get());
				
				// Reset optionals to not waste memory
				setupOptionals();
				
				registeredAggressiveGoals = false;
			}
		}
	}
	
	public void setupOptionals()
	{
		attackGoal = LazyOptional.of(() -> new MeleeAttackGoal(owner, 2.0D, true));
		nearestAttackableGoal = LazyOptional.of(() -> new NearestAttackableTargetGoal<>(owner, Player.class, false));
	}
	
	@Override
	public void registerAggressiveGoals()
	{
		if(!registeredAggressiveGoals)
		{
			owner.goalSelector.addGoal(-1, attackGoal.resolve().get());
			owner.targetSelector.addGoal(-1, nearestAttackableGoal.resolve().get());
			
			registeredAggressiveGoals = true;
		}
	}
}
