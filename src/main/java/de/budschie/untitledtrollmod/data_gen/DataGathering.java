package de.budschie.untitledtrollmod.data_gen;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class DataGathering
{
	@SubscribeEvent
	public static void onGatherData(GatherDataEvent event)
	{
		event.getGenerator().addProvider(new ItemModelDataProvider(event.getGenerator(), UntitledMainClass.MODID, event.getExistingFileHelper()));
		event.getGenerator().addProvider(new LanguageDataProvider(event.getGenerator(), UntitledMainClass.MODID, "en_us"));
		event.getGenerator().addProvider(new ModBlockStateProvider(event.getGenerator(), UntitledMainClass.MODID, event.getExistingFileHelper()));
		event.getGenerator().addProvider(new ModLootTableProvider(event.getGenerator()));
		event.getGenerator().addProvider(new CraftingRecipeProvider(event.getGenerator()));
		event.getGenerator().addProvider(new ItemTagProvider(event.getGenerator(), event.getExistingFileHelper()));
	}
}
