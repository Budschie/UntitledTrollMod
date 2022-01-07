package de.budschie.untitledtrollmod.entities.classes.rendering;

import de.budschie.untitledtrollmod.entities.classes.entities.TrollTNT;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.MobRenderer;

public class TrollTNTRenderer extends MobRenderer<TrollTNT, TrollTNTModel<TrollTNT>>
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
	public ResourceLocation getTextureLocation(TrollTNT p_114482_)
	{
		return TEXTURE;
	}
}
