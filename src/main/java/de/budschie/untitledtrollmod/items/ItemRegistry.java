package de.budschie.untitledtrollmod.items;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import de.budschie.untitledtrollmod.items.classes.AntiGravitationalDevice;
import de.budschie.untitledtrollmod.items.classes.TrollSubstanceItem;
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
	public static final RegistryObject<Item> TROLL_SUBSTANCE = REGISTRY.register("troll_substance", () -> new TrollSubstanceItem(new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> ANTI_GRAVITATIONAL_DEVICE = REGISTRY.register("anti_gravitational_device", () -> new AntiGravitationalDevice(new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> UNIVERSAL_AIR_REMOVER = REGISTRY.register("universal_air_remover", () -> new UniversalAirRemover(new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB).defaultDurability(100)));
	
	public static final RegistryObject<Item> TROLL_TNT = REGISTRY.register("troll_tnt", () -> new BlockItem(BlockRegistry.TROLL_TNT.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> BLOCKING_AIR = REGISTRY.register("blocking_air", () -> new BlockItem(BlockRegistry.BLOCKING_AIR.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	
	public static final RegistryObject<Item> FAKE_GRASS_BLOCK = REGISTRY.register("grass_block", () -> new BlockItem(BlockRegistry.FAKE_GRASS_BLOCK.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	// 
	public static final RegistryObject<Item> FAKE_DIRT = REGISTRY.register("dirt", () -> new BlockItem(BlockRegistry.FAKE_DIRT.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	//
	public static final RegistryObject<Item> FAKE_FARMLAND = REGISTRY.register("farmland", () -> new BlockItem(BlockRegistry.FAKE_FARMLAND.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> FAKE_TNT = REGISTRY.register("tnt", () -> new BlockItem(BlockRegistry.FAKE_TNT.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> FAKE_CRAFTING_TABLE = REGISTRY.register("crafting_table", () -> new BlockItem(BlockRegistry.FAKE_CRAFTING_TABLE.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> FAKE_OBSIDIAN = REGISTRY.register("obsidian", () -> new BlockItem(BlockRegistry.FAKE_OBSIDIAN.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> FAKE_BEDROCK = REGISTRY.register("bedrock", () -> new BlockItem(BlockRegistry.FAKE_BEDROCK.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> FAKE_NETHERITE_BLOCK = REGISTRY.register("netherite_block", () -> new BlockItem(BlockRegistry.FAKE_NETHERITE_BLOCK.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> FAKE_ANCIENT_DEBRIS = REGISTRY.register("ancient_debris", () -> new BlockItem(BlockRegistry.FAKE_ANCIENT_DEBRIS.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
	public static final RegistryObject<Item> FAKE_DIAMOND_BLOCK = REGISTRY.register("diamond_block", () -> new BlockItem(BlockRegistry.FAKE_DIAMOND_BLOCK.get(), new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));

	public static final RegistryObject<ArmorItem> XRAY_HEADSET = REGISTRY.register("xray_headset", () -> new ArmorItem(UntitledArmorMaterials.HEADSET, EquipmentSlot.HEAD, new Item.Properties().tab(ModCreativeModeTabs.TROLL_TAB)));
}
