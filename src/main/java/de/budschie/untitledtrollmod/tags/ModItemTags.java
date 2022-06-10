package de.budschie.untitledtrollmod.tags;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags
{
	public static final TagKey<Item> HOES = create("hoes");
	
	public static TagKey<Item> create(ResourceLocation name)
	{
		return TagKey.create(Registry.ITEM_REGISTRY, name);
	}
	
	public static TagKey<Item> create(String name)
	{
		return create(new ResourceLocation(UntitledMainClass.MODID, name));
	}
}
