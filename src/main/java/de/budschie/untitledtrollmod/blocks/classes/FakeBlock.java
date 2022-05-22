package de.budschie.untitledtrollmod.blocks.classes;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FakeBlock extends Block
{
	private Supplier<Block> sourceBlock;
	
	public FakeBlock(Supplier<Block> sourceBlock)
	{
		super(removeToolRequirement(sourceBlock.get()));
		
		this.sourceBlock = sourceBlock;
	}
	
	public FakeBlock(Supplier<Block> sourceBlock, float strength)
	{
		super(removeToolRequirement(sourceBlock.get()).strength(strength));
		
		this.sourceBlock = sourceBlock;
	}
	
	// Returns a copied property with removed tool requirement
	private static Properties removeToolRequirement(Block fromBlock)
	{
		Properties prop = Properties.copy(fromBlock);
		prop.requiresCorrectToolForDrops = false;
		
		return prop;
	}
	
	@Override
	public String getDescriptionId()
	{
		return sourceBlock.get().getDescriptionId();
	}
	
	public Block getFakedBlock()
	{
		return sourceBlock.get();
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
	{
		return sourceBlock.get().getShape(pState, pLevel, pPos, pContext);
	}
}
