package de.budschie.untitledtrollmod.data_gen;

import org.jetbrains.annotations.Nullable;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import de.budschie.untitledtrollmod.tags.ModItemTags;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemTagProvider extends TagsProvider<Item>
{
	protected ItemTagProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(pGenerator, Registry.ITEM, UntitledMainClass.MODID, existingFileHelper);
	}

	@Override
	public String getName()
	{
		return "Untitled Troll Mod Item Tag Provider";
	}

	@Override
	protected void addTags()
	{
		this.tag(ModItemTags.HOES).add(Items.WOODEN_HOE, Items.STONE_HOE, Items.GOLDEN_HOE, Items.IRON_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE);
	}
}
