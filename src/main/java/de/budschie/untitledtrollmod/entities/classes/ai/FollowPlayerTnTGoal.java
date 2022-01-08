package de.budschie.untitledtrollmod.entities.classes.ai;

import de.budschie.untitledtrollmod.entities.classes.entities.TrollTNTEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class FollowPlayerTnTGoal extends Goal
{
	private TrollTNTEntity trollTnt;
	private LivingEntity playerToFollow;
	private int timeToRecalcPath;
	private double speed;
	
	public FollowPlayerTnTGoal(TrollTNTEntity trollTnt, double speed)
	{
		this.trollTnt = trollTnt;
		this.speed = speed;
	}
	
	@Override
	public boolean canUse()
	{
		this.playerToFollow = trollTnt.getIgnitor();
		
		return this.playerToFollow != null;
	}
	
	@Override
	public boolean canContinueToUse()
	{
		return this.trollTnt.getNavigation().isDone();
	}
	
	public void start()
	{
		this.timeToRecalcPath = 0;
	}

	public void stop()
	{
		this.playerToFollow = null;
	}
	
	@Override
	public void tick()
	{
		this.trollTnt.getLookControl().setLookAt(this.playerToFollow, 10.0F, (float) this.trollTnt.getMaxHeadXRot());

		if (--this.timeToRecalcPath <= 0)
		{
			this.timeToRecalcPath = this.adjustedTickDelay(10);
			if (!this.trollTnt.isLeashed() && !this.trollTnt.isPassenger())
			{
				if (this.trollTnt.distanceToSqr(this.playerToFollow) >= 144.0D)
				{
					if(this.playerToFollow.isOnGround())
						this.teleportToOwner();
				} 
				else
				{
					this.trollTnt.getNavigation().moveTo(this.playerToFollow, speed);
				}

			}
		}
	}
	
	public void teleportToOwner()
	{
		trollTnt.copyPosition(playerToFollow);
	}
}
