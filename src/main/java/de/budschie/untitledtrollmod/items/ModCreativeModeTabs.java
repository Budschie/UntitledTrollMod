package de.budschie.untitledtrollmod.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs
{
	public static final CreativeModeTab TROLL_TAB = new CreativeModeTab("troll_tab")
	{
		@Override
		public ItemStack makeIcon()
		{
			return new ItemStack(ItemRegistry.TROLL_SUBSTANCE.get());
		}
	};
}
