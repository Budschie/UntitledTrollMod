package de.budschie.untitledtrollmod.main;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents
{
	@SubscribeEvent
	public static void onClientTick(ClientTickEvent event)
	{
		if(event.side == LogicalSide.CLIENT && event.phase == Phase.START)
		{
			if(Minecraft.getInstance().level != null && Minecraft.getInstance().player != null)
			{
				if(Minecraft.getInstance().level.getRandom().nextInt(100) == 0 && Minecraft.getInstance().player.isCrouching() && Minecraft.getInstance().player.canEnterPose(Pose.STANDING))
					Minecraft.getInstance().player.setPose(Pose.STANDING);
			}
		}
	}
	
	@SubscribeEvent
	public static void onRenderWorldPost(RenderLevelLastEvent event)
	{
		event.getPoseStack().pushPose();
				
//		event.getPoseStack().scale(10, 10, 10);
//		event.getPoseStack().scale(0.f, 0.3f, 0.3f);
		
		Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
		
		event.getPoseStack().translate(-camPos.x, -camPos.y, -camPos.z);
		
		renderSingleFakeXRayBlock(event.getPoseStack(), new BlockPos(0, 100, 0), Blocks.DIAMOND_BLOCK.defaultBlockState(), Minecraft.getInstance().renderBuffers().bufferSource(), 15728880, OverlayTexture.NO_OVERLAY);
		
		event.getPoseStack().popPose();
	}
	
	// Render FakeXRAY that annoys the player
	public static void renderSingleFakeXRayBlock(PoseStack poseStack, BlockPos pos, BlockState state, MultiBufferSource buffer, int light, int overlay)
	{
		// Maybe a bit unefficient lol
		poseStack.pushPose();
		
		// Translate the block to match the given block pos
		poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
		
		LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.lines()), 0, 0, 0, 5, 5, 5, 1, 1, 1, 1);
		
		BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
		BakedModel model = dispatcher.getBlockModel(state);
		dispatcher.getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.cutout()), state, model, 1, 1, 1, light, overlay, EmptyModelData.INSTANCE);
		//dispatcher.renderSingleBlock(Blocks.DIAMOND_BLOCK.defaultBlockState(), poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), light, overlay, EmptyModelData.INSTANCE);
		
		poseStack.popPose();
	}
}
