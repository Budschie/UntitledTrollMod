package de.budschie.untitledtrollmod.blocks.classes;

import java.util.Optional;

import de.budschie.untitledtrollmod.entities.classes.entities.TrollTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TrollTNTBlock extends TntBlock
{
	public TrollTNTBlock(Properties p_57422_)
	{
		super(p_57422_);
	}
	
	@Override
	public void onCaughtFire(BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter)
	{
		createEntity(world, pos, Optional.of(igniter), 8 * 20);
	}
	
	@Override
	public void wasExploded(Level p_57441_, BlockPos p_57442_, Explosion explosion)
	{
		LivingEntity igniter = null;
		
		// Read ignitor from previous ignition
		if(explosion.getDamageSource().getEntity() instanceof PrimedTnt pTnt)
			igniter = pTnt.getOwner();
		else if(explosion.getDamageSource().getEntity() instanceof TrollTNT trollTNT)
			igniter = trollTNT.getIgnitor();
		
		createEntity(p_57441_, p_57442_, Optional.ofNullable(igniter), 2 * 20);
	}
	
	public void createEntity(Level world, BlockPos pos, Optional<LivingEntity> igniter, int time)
	{
		if(!world.isClientSide())
		{
			TrollTNT trollTnt = new TrollTNT(world);
			igniter.ifPresent(resolvedIgniter -> trollTnt.setIgnitor(resolvedIgniter));
			trollTnt.setIgnited(time);
			
			trollTnt.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
			
			world.addFreshEntity(trollTnt);
		}
	}
}
