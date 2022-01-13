package de.budschie.untitledtrollmod.caps.crouch_lock;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class CrouchLockProvider implements ICapabilityProvider
{
	public static final ResourceLocation CAP_NAME = new ResourceLocation(UntitledMainClass.MODID, "crouch_lock");
	
	public static final Capability<ICrouchLock> CAP = CapabilityManager.get(new CapabilityToken<>(){});
	
	private LazyOptional<ICrouchLock> capInstance = LazyOptional.of(() -> new CrouchLock());
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		return CAP.orEmpty(cap, capInstance);
	}	
}
