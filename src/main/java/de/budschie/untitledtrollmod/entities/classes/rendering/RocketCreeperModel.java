package de.budschie.untitledtrollmod.entities.classes.rendering;

import de.budschie.untitledtrollmod.entities.classes.entities.RocketCreeper;
import de.budschie.untitledtrollmod.entities.classes.entities.RocketCreeper.RocketCreeperState;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.phys.Vec3;

public class RocketCreeperModel<T extends Creeper> extends CreeperModel<T>
{
	public RocketCreeperModel(ModelPart pRoot)
	{
		super(pRoot);
	}
	
	@Override
	public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch)
	{
		super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
		
		if(pEntity instanceof RocketCreeper rocketCreeper)
		{
			Vec3 dir = rocketCreeper.getCurrentHomingDirection();
			
			if(rocketCreeper.getRocketCreeperState() == RocketCreeperState.HOMING && dir != null)
			{
				head.xRot = 0;
				head.yRot = 0;
			}
		}
	}
}
