package de.budschie.untitledtrollmod.data_gen;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import de.budschie.untitledtrollmod.blocks.classes.FakeBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider
{
	private String modid;
	private ExistingFileHelper helper;
	
	public ModBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper)
	{
		super(gen, modid, exFileHelper);
		this.modid = modid;
		this.helper = exFileHelper;
	}

	@Override
	protected void registerStatesAndModels()
	{
		blockWithItem(BlockRegistry.BLOCKING_AIR.get());
		
		fakeBlockWithItem(BlockRegistry.FAKE_DIRT.get());
		fakeBlockWithItem(BlockRegistry.FAKE_FARMLAND.get());
		fakeBlockWithItem(BlockRegistry.FAKE_TNT.get());
		fakeBlockWithItem(BlockRegistry.FAKE_CRAFTING_TABLE.get());
		fakeBlockWithItem(BlockRegistry.FAKE_OBSIDIAN.get());
		fakeBlockWithItem(BlockRegistry.FAKE_BEDROCK.get());
		fakeBlockWithItem(BlockRegistry.FAKE_DIAMOND_BLOCK.get());
		fakeBlockWithItem(BlockRegistry.FAKE_NETHERITE_BLOCK.get());
		fakeBlockWithItem(BlockRegistry.FAKE_ANCIENT_DEBRIS.get());
	}
	
	private void blockWithItem(Block block)
	{
		simpleBlock(block);
		simpleBlockItem(block, new ModelFile.ExistingModelFile(new ResourceLocation(block.getRegistryName().getNamespace(), "block/" + block.getRegistryName().getPath()), this.helper));
	}
	
	private void fakeBlockWithItem(FakeBlock block)
	{
		ExistingModelFile existingFile = new ModelFile.ExistingModelFile(
				new ResourceLocation(block.getFakedBlock().getRegistryName().getNamespace(), "block/" + block.getFakedBlock().getRegistryName().getPath()), helper);
		simpleBlock(block, existingFile);
		simpleBlockItem(block, existingFile);
	}
}
