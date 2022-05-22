package de.budschie.untitledtrollmod.data_gen;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelDataProvider extends ItemModelProvider
{
	private static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
	
	public ItemModelDataProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
	{
		super(generator, modid, existingFileHelper);
	}

	@Override
	protected void registerModels()
	{
		singleTextureDefault("xray_headset");
		singleTextureDefault("universal_air_remover");
		singleTextureDefault("anti_gravitational_device");
	}
	
	private void singleTextureDefault(String name)
	{
		singleTexture(name, GENERATED, "layer0", new ResourceLocation(UntitledMainClass.MODID, "item/" + name));
	}
}
