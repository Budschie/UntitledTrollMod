package de.budschie.untitledtrollmod.main;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents
{
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event)
	{
		if(event.side == LogicalSide.CLIENT && event.phase == Phase.START)
		{
			if(Minecraft.getInstance().level != null && Minecraft.getInstance().player != null)
			{
				if(Minecraft.getInstance().level.getRandom().nextInt(100) == 0 && Minecraft.getInstance().player.isCrouching() && Minecraft.getInstance().player.canEnterPose(Pose.STANDING))
					Minecraft.getInstance().player.setPose(Pose.STANDING);
			}
		}
	}
}
