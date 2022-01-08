package de.budschie.untitledtrollmod.entities.classes.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import de.budschie.untitledtrollmod.entities.classes.entities.TrollTNTEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

// Made with Blockbench 4.1.1
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports

public class TrollTNTModel<T extends TrollTNTEntity> extends EntityModel<T>
{
	private final ModelPart root;

	private final ModelPart rightFoot;
	private final ModelPart leftFoot;
	private final ModelPart body;
	
	public TrollTNTModel(ModelPart root)
	{
		this.root = root.getChild("root");
		this.rightFoot = this.root.getChild("right_foot");
		this.leftFoot = this.root.getChild("left_foot");
		this.body = this.root.getChild("body");
	}

	public static LayerDefinition createBodyLayer()
	{
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_foot = root.addOrReplaceChild("right_foot",
				CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -4.0F, -3.0F));

		PartDefinition left_foot = root.addOrReplaceChild("left_foot",
				CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, -4.0F, -3.0F));

		PartDefinition body = root.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -16.0F, -5.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, -4.0F, -3.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		this.rightFoot.xRot = (float) (Math.sin(limbSwing) * limbSwingAmount);
		this.leftFoot.xRot = (float) (Math.sin(limbSwing - 90 * 2) * limbSwingAmount);
		this.body.xRot = (float) Math.sin(limbSwing) * 0.2f * limbSwingAmount;
		this.body.zRot = (float) Math.cos(limbSwing) * 0.2f * limbSwingAmount;
		
		float standupProgress = Math.min(entity.getStandupTime() + ageInTicks, entity.getMaxStandupTime());
		
		this.body.y = Mth.lerp(standupProgress, 0, -0.4f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		root.render(poseStack, buffer, packedLight, packedOverlay);
	}
}