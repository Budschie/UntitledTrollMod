package de.budschie.untitledtrollmod.items;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import de.budschie.untitledtrollmod.items.classes.TrollArrowItem;
import de.budschie.untitledtrollmod.items.classes.TrollBowItem;
import de.budschie.untitledtrollmod.items.classes.UniversalAirRemover;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry
{
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, UntitledMainClass.MODID);

	public static final RegistryObject<Item> TROLL_BOW = REGISTRY.register("troll_bow", () -> new TrollBowItem(new Item.Properties().defaultDurability(2).tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> TROLL_ARROW = REGISTRY.register("troll_arrow", () -> new TrollArrowItem(new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> FAIL_CROUCH_ITEM = REGISTRY.register("fail_crouch_item", () -> new TrollArrowItem(new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> UNIVERSAL_AIR_REMOVER = REGISTRY.register("universal_air_remover", () -> new UniversalAirRemover(new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	
	public static final RegistryObject<Item> TROLL_TNT = REGISTRY.register("troll_tnt", () -> new BlockItem(BlockRegistry.TROLL_TNT.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> BLOCKING_AIR = REGISTRY.register("blocking_air", () -> new BlockItem(BlockRegistry.BLOCKING_AIR.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));

	public static final RegistryObject<ArmorItem> XRAY_HEADSET = REGISTRY.register("xray_headset", () -> new ArmorItem(UntitledArmorMaterials.HEADSET, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
}
