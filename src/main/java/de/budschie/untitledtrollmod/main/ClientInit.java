package de.budschie.untitledtrollmod.main;

import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.entities.classes.TrollArrowEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class ClientInit
{
	@SubscribeEvent
	public static void onInitClient(FMLClientSetupEvent event)
	{
		EntityRenderers.register(EntityRegistry.TROLL_ARROW.get(), context -> new ThrownItemRenderer<TrollArrowEntity>(context));
	}
}
