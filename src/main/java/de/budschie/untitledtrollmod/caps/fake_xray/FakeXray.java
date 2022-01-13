package de.budschie.untitledtrollmod.caps.fake_xray;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

public class FakeXray implements IFakeXray
{
	private ArrayList<FakeOre> fakeOres = new ArrayList<>();
	private LevelChunk owner;

	public FakeXray(LevelChunk owner)
	{
		this.owner = owner;
		regenerate();
	}
	
	@Override
	public void regenerate()
	{
		fakeOres.clear();
		
		addFakeOreVein(-60, 11, 2, 1, Blocks.DIAMOND_ORE.defaultBlockState(), owner.getLevel().getRandom());
	}
	
	private void addFakeOreVein(int from, int to, int amountPerOre, int amountPerChunk, BlockState blockState, Random rand)
	{
		for(int i = 0; i < amountPerChunk; i++)
		{
			int chosenYPos = owner.getLevel().getRandom().nextInt(to - from) + from;
			
			int randX = rand.nextInt(16);
			int randZ = rand.nextInt(16);
			
			for(int j = 0; j < amountPerChunk; j++)
			{
				if(randX > 15 || randZ > 15 || chosenYPos < -64 || randX < 0 || randZ < 0)
					break;
				
				fakeOres.add(new FakeOre(blockState, new BlockPos(randX, chosenYPos, randZ)));
				
				Direction randDir = Direction.getRandom(rand);
				
				randX += randDir.getStepX();
				chosenYPos += randDir.getStepY();
				randZ += randDir.getStepZ();
			}
		}		
	}

	@Override
	public ArrayList<FakeOre> getFakeOres()
	{
		return fakeOres;
	}
}
