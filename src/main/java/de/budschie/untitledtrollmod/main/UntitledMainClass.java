package de.budschie.untitledtrollmod.main;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(value = UntitledMainClass.MODID)
@EventBusSubscriber(bus = Bus.MOD)
public class UntitledMainClass
{
	public static final String MODID = "untitledtrollmod";
	
	public UntitledMainClass()
	{
		
	}
}
