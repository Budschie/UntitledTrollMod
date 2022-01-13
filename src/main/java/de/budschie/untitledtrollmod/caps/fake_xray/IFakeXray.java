package de.budschie.untitledtrollmod.caps.fake_xray;

import java.util.ArrayList;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public interface IFakeXray
{	
	void regenerate();
	ArrayList<FakeOre> getFakeOres();
	
	public static class FakeOre
	{
		private BlockState fakeOre;
		private BlockPos relativePos;
		
		public FakeOre(BlockState fakeOre, BlockPos relativePos)
		{
			this.fakeOre = fakeOre;
			this.relativePos = relativePos;
		}
		
		public BlockState getFakeOre()
		{
			return fakeOre;
		}
		
		public BlockPos getRelativePos()
		{
			return relativePos;
		}
	}
}
