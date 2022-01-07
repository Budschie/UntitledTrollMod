package de.budschie.untitledtrollmod.networking;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public interface ISimplImplInterface<P>
{
	void writeMsgToBuffer(P packet, FriendlyByteBuf pBuffer);
	P readMsgToPacket(FriendlyByteBuf pBuffer);
	
	boolean handlePacket(P packet, Supplier<NetworkEvent.Context> ctx);
}
