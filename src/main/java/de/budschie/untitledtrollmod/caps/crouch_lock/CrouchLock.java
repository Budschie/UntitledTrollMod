package de.budschie.untitledtrollmod.caps.crouch_lock;

public class CrouchLock implements ICrouchLock
{
	private int crouchLockTicks = -1;
	
	@Override
	public int getCrouchLockedTicks()
	{
		return crouchLockTicks;
	}

	@Override
	public void setCrouchLockedTicks(int value)
	{
		this.crouchLockTicks = value;
	}
}
