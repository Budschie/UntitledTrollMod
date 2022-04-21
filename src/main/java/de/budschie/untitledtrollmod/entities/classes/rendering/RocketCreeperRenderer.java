package de.budschie.untitledtrollmod.entities.classes.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import de.budschie.untitledtrollmod.entities.classes.entities.RocketCreeper;
import de.budschie.untitledtrollmod.entities.classes.entities.RocketCreeper.RocketCreeperState;
import de.budschie.untitledtrollmod.utils.MathUtils;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.phys.Vec3;

public class RocketCreeperRenderer extends CreeperRenderer
{
	public RocketCreeperRenderer(Context context)
	{
		super(context);
		
		// Stupid hack because java won't shut up if we put rocket creeper in here...
		this.model = new RocketCreeperModel<Creeper>(context.bakeLayer(ModelLayers.CREEPER));
	}
	
	@Override
	public void render(Creeper pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight)
	{
		boolean pushed = false;
		
		RocketCreeper rocketCreeper = (RocketCreeper) pEntity;
		
		if(rocketCreeper.getRocketCreeperState() == RocketCreeperState.LAUNCHING)
			pMatrixStack.translate(MathUtils.getRangedRandom(pEntity.getRandom(), 0.1f), MathUtils.getRangedRandom(pEntity.getRandom(), 0.1f), MathUtils.getRangedRandom(pEntity.getRandom(), 0.1f));
		else if(rocketCreeper.getRocketCreeperState() == RocketCreeperState.HOMING)
		{
			Vec3 homingVector = rocketCreeper.getCurrentHomingDirection();

			if (homingVector != null)
			{
				pushed = true;
				pMatrixStack.pushPose();
				
				pMatrixStack.mulPose(Vector3f.XP.rotationDegrees(180f));
				pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
				pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot()) - 90f));				
			}
		}
		
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
		
		if(pushed)
			pMatrixStack.popPose();
	}	
}
