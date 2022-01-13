package de.budschie.untitledtrollmod.main;

import org.lwjgl.opengl.GL30;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector4f;

import de.budschie.untitledtrollmod.caps.fake_xray.FakeXrayProvider;
import de.budschie.untitledtrollmod.caps.fake_xray.IFakeXray;
import de.budschie.untitledtrollmod.caps.fake_xray.IFakeXray.FakeOre;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEvents
{
	public static final int RADIUS = 2;
	
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
		
		ChunkPos srcPos = Minecraft.getInstance().player.chunkPosition();
						
		// Translate block
		Vec3 camPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
		event.getPoseStack().translate(-camPos.x, -camPos.y, -camPos.z);
		
		GL30.glDisable(GL30.GL_DEPTH_TEST);
		
		for(int i = 0; i < Minecraft.getInstance().level.getChunkSource().storage.chunks.length(); i++)
		{
			LevelChunk levelChunk = Minecraft.getInstance().level.getChunkSource().storage.chunks.get(i);
						
			if(levelChunk == null)
				continue;
			
			ChunkPos destPos = levelChunk.getPos();
			
			if((Math.abs(srcPos.x - destPos.x) > RADIUS) || (Math.abs(srcPos.z - destPos.z) > RADIUS))
				continue;
			
			// Funny name
			LazyOptional<IFakeXray> iFakeXray = levelChunk.getCapability(FakeXrayProvider.CAP);
			
			if(iFakeXray.isPresent())
			{
				// best code ever.
				IFakeXray yesIReallyFakeXray = iFakeXray.resolve().get();
				
				// Massive performance impact
				for(FakeOre fakeOre : yesIReallyFakeXray.getFakeOres())
				{
					renderSingleFakeXRayBlock(event.getPoseStack(), levelChunk.getPos().getWorldPosition().offset(fakeOre.getRelativePos()), fakeOre.getFakeOre(),
							Minecraft.getInstance().renderBuffers().bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, RenderType.translucent(),
							new Vector4f(0.7f, 0.7f, 1f, 0.5f));
				}
			}
		}
		 
//		renderSingleFakeXRayBlock(event.getPoseStack(), new BlockPos(1, 100, 0), Blocks.DIAMOND_ORE.defaultBlockState(),
//				Minecraft.getInstance().renderBuffers().bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, RenderType.translucent(),
//				new Vector4f(0.7f, 0.7f, 1f, 0.5f));
				
		event.getPoseStack().popPose();
		
		// End batch and use the same render type
		Minecraft.getInstance().renderBuffers().bufferSource().endBatch(RenderType.translucent());
		
		GL30.glEnable(GL30.GL_DEPTH_TEST);
	}
	
	// Render FakeXRAY that annoys the player
	public static void renderSingleFakeXRayBlock(PoseStack poseStack, BlockPos pos, BlockState state, MultiBufferSource buffer, int light, int overlay, RenderType renderType, Vector4f tint)
	{
		// Maybe a bit unefficient lol
		poseStack.pushPose();
		
		// Translate the block to match the given block pos
		poseStack.translate(pos.getX(), pos.getY(), pos.getZ());
		
		BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
		
		VertexConsumer consumer = buffer.getBuffer(renderType);
		
		// Ugly, but it works
		VertexConsumer adjustedConsumer = new VertexConsumer()
		{
			@Override
			public VertexConsumer vertex(double pX, double pY, double pZ)
			{
				return consumer.vertex(pX, pY, pZ);
			}
			
			@Override
			public VertexConsumer uv2(int pU, int pV)
			{
				return consumer.uv2(pU, pV);
			}
			
			@Override
			public VertexConsumer uv(float pU, float pV)
			{
				return consumer.uv(pU, pV);
			}
			
			@Override
			public void unsetDefaultColor()
			{
				consumer.unsetDefaultColor();
			}
			
			@Override
			public VertexConsumer overlayCoords(int pU, int pV)
			{
				return consumer.overlayCoords(pU, pV);
			}
			
			@Override
			public VertexConsumer normal(float pX, float pY, float pZ)
			{
				return consumer.normal(pX, pY, pZ);
			}
			
			@Override
			public void endVertex()
			{
				consumer.endVertex();
			}
			
			@Override
			public void defaultColor(int pRed, int pGreen, int pBlue, int pAlpha)
			{
				consumer.defaultColor(pRed, pGreen, pBlue, pAlpha);
			}
			
			@Override
			public VertexConsumer color(int pRed, int pGreen, int pBlue, int pAlpha)
			{
				return consumer.color(tint.x(), tint.y(), tint.z(), tint.w());
			}
		};
		
//		dispatcher.renderBatched(state, pos, Minecraft.getInstance().level, poseStack, adjustedConsumer, false, Minecraft.getInstance().level.random, EmptyModelData.INSTANCE);
//		
		BakedModel blockmodel = dispatcher.getBlockModel(state);
		
		dispatcher.getModelRenderer().renderModel(poseStack.last(), adjustedConsumer, state, blockmodel, 1, 1, 1, light, overlay, EmptyModelData.INSTANCE);
		
		poseStack.popPose();
	}
}
