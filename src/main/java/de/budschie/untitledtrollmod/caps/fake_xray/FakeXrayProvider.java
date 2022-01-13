package de.budschie.untitledtrollmod.caps.fake_xray;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class FakeXrayProvider implements ICapabilityProvider
{
	public static final ResourceLocation CAP_NAME = new ResourceLocation(UntitledMainClass.MODID, "fake_xray");
	
	public static final Capability<IFakeXray> CAP = CapabilityManager.get(new CapabilityToken<>(){});
	
	private LevelChunk owner;
	
	private LazyOptional<IFakeXray> capInstance = LazyOptional.of(() -> new FakeXray(owner));
	
	public FakeXrayProvider(LevelChunk owner)
	{
		this.owner = owner;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return CAP.orEmpty(cap, capInstance);
	}	
}
