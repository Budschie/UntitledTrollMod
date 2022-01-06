package de.budschie.untitledtrollmod.items;

import de.budschie.untitledtrollmod.items.classes.TrollArrowItem;
import de.budschie.untitledtrollmod.items.classes.TrollBowItem;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry
{
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, UntitledMainClass.MODID);

	public static final RegistryObject<Item> TROLL_BOW = REGISTRY.register("troll_bow", () -> new TrollBowItem(new Item.Properties().defaultDurability(1001).tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> TROLL_ARROW = REGISTRY.register("troll_arrow", () -> new TrollArrowItem(new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
}
