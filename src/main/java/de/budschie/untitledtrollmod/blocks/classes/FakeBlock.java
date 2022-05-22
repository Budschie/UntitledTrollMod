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
		super(sourceBlock.get().properties);
		
		this.sourceBlock = sourceBlock;
	}
	
	public FakeBlock(Supplier<Block> sourceBlock, float strength)
	{
		super(Properties.copy(sourceBlock.get()).strength(strength));
		
		this.sourceBlock = sourceBlock;
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
