package de.budschie.untitledtrollmod.caps.aggressive_animal;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ByteTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class AggressiveAnimalProvider implements ICapabilitySerializable<ByteTag>
{
	public static final ResourceLocation CAP_NAME = new ResourceLocation(UntitledMainClass.MODID, "aggressive_animal");
	
	public static final Capability<IAggressiveAnimal> CAP = CapabilityManager.get(new CapabilityToken<>(){});
	
	private PathfinderMob owner;
	
	private LazyOptional<IAggressiveAnimal> capInstance = LazyOptional.of(() -> new AggressiveAnimal(owner));
	
	public AggressiveAnimalProvider(PathfinderMob owner)
	{
		this.owner = owner;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return CAP.orEmpty(cap, capInstance);
	}

	@Override
	public ByteTag serializeNBT()
	{
		return ByteTag.valueOf(capInstance.resolve().get().isAggressive());
	}

	@Override
	public void deserializeNBT(ByteTag nbt)
	{
		capInstance.resolve().get().setAggressive(nbt.getAsByte() == 1);
	}
}
