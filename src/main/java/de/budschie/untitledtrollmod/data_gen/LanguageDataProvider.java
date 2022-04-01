package de.budschie.untitledtrollmod.data_gen;

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
	}
}
