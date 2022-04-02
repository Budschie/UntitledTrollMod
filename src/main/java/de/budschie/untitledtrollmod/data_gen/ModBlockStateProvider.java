package de.budschie.untitledtrollmod.data_gen;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
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
	}
	
	private void blockWithItem(Block block)
	{
		simpleBlock(block);
		simpleBlockItem(block, new ModelFile.ExistingModelFile(new ResourceLocation(block.getRegistryName().getNamespace(), "block/" + block.getRegistryName().getPath()), this.helper));
	}
}
