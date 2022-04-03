package de.budschie.untitledtrollmod.entities.classes.rendering;

import java.util.OptionalInt;

import com.mojang.blaze3d.vertex.PoseStack;

import de.budschie.untitledtrollmod.entities.classes.entities.TrollTNTEntity;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TrollTNTRenderer extends MobRenderer<TrollTNTEntity, TrollTNTModel<TrollTNTEntity>>
{
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(UntitledMainClass.MODID, "troll_tnt"), "main");
	
	public static final ResourceLocation TEXTURE = new ResourceLocation(UntitledMainClass.MODID, "textures/entity/troll_tnt.png");

	public TrollTNTRenderer(Context context)
	{
		super(context, new TrollTNTModel<>(context.bakeLayer(LAYER_LOCATION)), 1.0f);
	}

	@Override
	public ResourceLocation getTextureLocation(TrollTNTEntity p_114482_)
	{
		return TEXTURE;
	}
	
	@Override
	protected void scale(TrollTNTEntity pLivingEntity, PoseStack pMatrixStack, float pPartialTickTime)
	{
		super.scale(pLivingEntity, pMatrixStack, pPartialTickTime);
		
		OptionalInt rem = pLivingEntity.getIngitedTicksRemaining();
		
		if(rem.isPresent())
		{
			float scale = 1f + getProgress(pLivingEntity, pPartialTickTime) * 0.5f;
			pMatrixStack.scale(scale, scale, scale);
		}
	}
	
	@Override
	protected float getWhiteOverlayProgress(TrollTNTEntity pLivingEntity, float pPartialTicks)
	{
		OptionalInt rem = pLivingEntity.getIngitedTicksRemaining();
		
		if(rem.isPresent())
		{
			return getProgress(pLivingEntity, pPartialTicks);
		}
		
		return super.getWhiteOverlayProgress(pLivingEntity, pPartialTicks);
	}
	
	private float getProgress(TrollTNTEntity tntEntity, float pTicks)
	{
		OptionalInt rem = tntEntity.getIngitedTicksRemaining();
		
		if(rem.isPresent())
		{
			return (1f - Math.min((rem.getAsInt() + pTicks) / 10f, 1f));
		}
		
		return 0f;
	}
}
