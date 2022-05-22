package de.budschie.untitledtrollmod.caps.aggressive_animal;

public interface IAggressiveAnimal
{
	boolean isAggressive();
	/** Makes an entity aggressive. Returns a runnable that contains that for handling the addition of goals.
	 * You can either let this run instantly, or add it to a queue. **/
	void setAggressive(boolean value, boolean isLoadingEntity);
	
	void registerAggressiveGoals();
}
