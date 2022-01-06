package de.budschie.untitledtrollmod.main;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.items.ItemRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = UntitledMainClass.MODID)
@EventBusSubscriber(bus = Bus.MOD)
public class UntitledMainClass
{
	public static final String MODID = "untitledtrollmod";
	
	public UntitledMainClass()
	{
		EntityRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
		ItemRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
		BlockRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
