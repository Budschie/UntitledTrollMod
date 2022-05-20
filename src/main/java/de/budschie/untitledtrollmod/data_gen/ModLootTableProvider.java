package de.budschie.untitledtrollmod.data_gen;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Pair;

import de.budschie.untitledtrollmod.entities.EntityRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

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
		
		return Arrays.asList(pair);
	}
	
	@Override
	protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker)
	{
		// super.validate(map, validationtracker);
	}
}
