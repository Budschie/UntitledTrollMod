package de.budschie.untitledtrollmod.networking;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import de.budschie.untitledtrollmod.networking.packets.RandomizeKeyBindings;
import de.budschie.untitledtrollmod.networking.packets.RandomizeKeyBindings.RandomizeKeyBindingsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class MainNetworkChannel
{
	private static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
	    new ResourceLocation(UntitledMainClass.MODID, "main"),
	    () -> PROTOCOL_VERSION,
	    PROTOCOL_VERSION::equals,
	    PROTOCOL_VERSION::equals
	);
	
	private static int ID = 0;
	
	public static void registerPackets()
	{
		addPacket(new RandomizeKeyBindings(), RandomizeKeyBindingsPacket.class);
	}
	
	public static <P> void addPacket(ISimplImplInterface<P> packetCode, Class<P> packetClass)
	{
		INSTANCE.registerMessage(ID++, packetClass, packetCode::writeMsgToBuffer, packetCode::readMsgToPacket, (packet, context) ->
		{
			boolean succeeded = packetCode.handlePacket(packet, context);
			context.get().setPacketHandled(succeeded);
		});
	}
}
