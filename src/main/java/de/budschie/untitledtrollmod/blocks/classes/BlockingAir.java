package de.budschie.untitledtrollmod.blocks.classes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockingAir extends Block
{
	public BlockingAir(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState pState)
	{
		return RenderShape.INVISIBLE;
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
	{
		return Shapes.block();
	}
	
	@Override
	public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
	{
		return Shapes.empty();
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
	{
		return Shapes.empty();
	}
	
	@Override
	public boolean canBeReplaced(BlockState pState, Fluid pFluid)
	{
		return true;
	}
	
	@Override
	public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext)
	{
		return true;
	}
}
