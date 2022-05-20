package de.budschie.untitledtrollmod.data_gen;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.items.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageDataProvider extends LanguageProvider
{
	public LanguageDataProvider(DataGenerator gen, String modid, String locale)
	{
		super(gen, modid, locale);
	}

	@Override
	protected void addTranslations()
	{
		addItem(() -> ItemRegistry.XRAY_HEADSET.get(), "X-Ray Headset");
		addItem(() -> ItemRegistry.UNIVERSAL_AIR_REMOVER.get(), "Universal Air Remover");
		
		addBlock(() -> BlockRegistry.BLOCKING_AIR.get(), "Blocking Air");
		
		addEntityType(() -> EntityRegistry.ROCKET_CREEPER.get(), "Rocket Creeper");
		addEntityType(() -> EntityRegistry.TROLL_TNT.get(), "Troll TNT");
		addEntityType(() -> EntityRegistry.SHEEP_HOPPER.get(), "Sheep Hopper");
	}
}
