package de.budschie.untitledtrollmod.networking.packets;

import java.util.function.Supplier;

import de.budschie.untitledtrollmod.networking.ISimplImplInterface;
import de.budschie.untitledtrollmod.networking.packets.RandomizeKeyBindings.RandomizeKeyBindingsPacket;
import de.budschie.untitledtrollmod.utils.UntitledUtilsClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

public class RandomizeKeyBindings implements ISimplImplInterface<RandomizeKeyBindingsPacket>
{	
	public static class RandomizeKeyBindingsPacket
	{
		
	}

	@Override
	public void writeMsgToBuffer(RandomizeKeyBindingsPacket packet, FriendlyByteBuf pBuffer)
	{
		
	}

	@Override
	public RandomizeKeyBindingsPacket readMsgToPacket(FriendlyByteBuf pBuffer)
	{
		return new RandomizeKeyBindingsPacket();
	}

	@Override
	public boolean handlePacket(RandomizeKeyBindingsPacket packet, Supplier<Context> ctx)
	{
		ctx.get().enqueueWork(() ->
		{
			UntitledUtilsClient.randomizeKeybindings();
		});
		
		return true;
	}
}
