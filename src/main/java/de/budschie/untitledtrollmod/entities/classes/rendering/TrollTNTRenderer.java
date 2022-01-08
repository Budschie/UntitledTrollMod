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
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("bmorph", "troll_tnt"), "main");
	
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
	protected void scale(TrollTNTEntity trollEntity, PoseStack poseStack, float partialTicks)
	{
		OptionalInt rem = trollEntity.getIngitedTicksRemaining();
		
		if(rem.isPresent())
		{
			float scale = 1f + (1f - Math.min((Mth.lerp(partialTicks, rem.getAsInt(), rem.getAsInt())) / 20f, 1f)) * 0.5f;
			poseStack.scale(scale, scale, scale);
		}
	}
}
