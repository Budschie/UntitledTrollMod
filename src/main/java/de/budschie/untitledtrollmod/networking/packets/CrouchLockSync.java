package de.budschie.untitledtrollmod.networking.packets;

import java.util.function.Supplier;

import de.budschie.untitledtrollmod.caps.crouch_lock.CrouchLockProvider;
import de.budschie.untitledtrollmod.networking.ISimplImplInterface;
import de.budschie.untitledtrollmod.networking.packets.CrouchLockSync.CrouchLockPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class CrouchLockSync implements ISimplImplInterface<CrouchLockPacket>
{
	public static class CrouchLockPacket
	{
		private int crouchLockTicks;
		
		public CrouchLockPacket(int crouchLockTicks)
		{
			this.crouchLockTicks = crouchLockTicks;
		}
		
		public int getCrouchLockTicks()
		{
			return crouchLockTicks;
		}
	}

	@Override
	public void writeMsgToBuffer(CrouchLockPacket packet, FriendlyByteBuf pBuffer)
	{
		pBuffer.writeInt(packet.getCrouchLockTicks());
	}

	@Override
	public CrouchLockPacket readMsgToPacket(FriendlyByteBuf pBuffer)
	{
		return new CrouchLockPacket(pBuffer.readInt());
	}

	@Override
	public boolean handlePacket(CrouchLockPacket packet, Supplier<Context> ctx)
	{
		if(Minecraft.getInstance().player != null)
		{
			Minecraft.getInstance().player.getCapability(CrouchLockProvider.CAP).ifPresent(cap ->
			{
				cap.setCrouchLockedTicks(packet.getCrouchLockTicks());
			});
		}
		
		return true;
	}
}
