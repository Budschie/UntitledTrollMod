package de.budschie.untitledtrollmod.data_gen;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.mojang.datafixers.util.Pair;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

public class ModLootTableProvider extends LootTableProvider
{
	public ModLootTableProvider(DataGenerator pGenerator)
	{
		super(pGenerator);
	}
	
	@Override
	protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables()
	{
		Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet> pair = Pair.of(() -> biConsumer ->
		{
			biConsumer.accept(EntityRegistry.ROCKET_CREEPER.get().getDefaultLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, 3))
					.add(LootItem.lootTableItem(Items.GUNPOWDER)).add(LootItem.lootTableItem(Items.FIREWORK_ROCKET))));
		}, LootContextParamSets.ENTITY);
		
		Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet> blockPair = Pair.of(() -> new ModBlockLootProvider(), LootContextParamSets.BLOCK);
		
		return Arrays.asList(pair, blockPair);
	}
	
	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker)
	{
		// super.validate(map, validationtracker);
	}
	
	public static class ModBlockLootProvider extends BlockLoot
	{
		@Override
		protected void addTables()
		{
			dropSelf(BlockRegistry.FAKE_ANCIENT_DEBRIS.get());
			dropSelf(BlockRegistry.FAKE_BEDROCK.get());
			dropSelf(BlockRegistry.FAKE_CRAFTING_TABLE.get());
			dropSelf(BlockRegistry.FAKE_DIAMOND_BLOCK.get());
			dropSelf(BlockRegistry.FAKE_DIRT.get());
			dropSelf(BlockRegistry.FAKE_FARMLAND.get());
			dropSelf(BlockRegistry.FAKE_NETHERITE_BLOCK.get());
			dropSelf(BlockRegistry.FAKE_OBSIDIAN.get());
			dropSelf(BlockRegistry.FAKE_TNT.get());
			
			add(BlockRegistry.TROLL_TNT.get(), noDrop());
			add(BlockRegistry.BLOCKING_AIR.get(), noDrop());
		}
		
		@Override
		protected Iterable<Block> getKnownBlocks()
		{
			// Wtf minecraft?
			return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> block.getRegistryName().getNamespace().equals(UntitledMainClass.MODID)).collect(Collectors.toList());
		}
	}
}
