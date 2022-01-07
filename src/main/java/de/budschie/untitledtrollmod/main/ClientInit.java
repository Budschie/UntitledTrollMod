package de.budschie.untitledtrollmod.main;

import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.entities.classes.entities.TrollArrowEntity;
import de.budschie.untitledtrollmod.entities.classes.rendering.TrollTNTModel;
import de.budschie.untitledtrollmod.entities.classes.rendering.TrollTNTRenderer;
import de.budschie.untitledtrollmod.items.ItemRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class ClientInit
{
	@SubscribeEvent
	public static void onInitClient(FMLClientSetupEvent event)
	{
		EntityRenderers.register(EntityRegistry.TROLL_ARROW.get(), context -> new ThrownItemRenderer<TrollArrowEntity>(context));
		EntityRenderers.register(EntityRegistry.TROLL_TNT.get(), context -> new TrollTNTRenderer(context));
		EntityRenderers.register(EntityRegistry.SHEEP_HOPPER.get(), context -> new SheepRenderer(context));
		
		event.enqueueWork(() ->
		{
			ItemProperties.register(ItemRegistry.TROLL_BOW.get(), new ResourceLocation(UntitledMainClass.MODID, "pulling"), new ClampedItemPropertyFunction()
			{
				@Override
				public float unclampedCall(ItemStack stack, ClientLevel p_174565_, LivingEntity living, int p_174567_)
				{
					return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F;
				}
			});
			
			ItemProperties.register(ItemRegistry.TROLL_BOW.get(), new ResourceLocation(UntitledMainClass.MODID, "pull"), new ClampedItemPropertyFunction()
			{
				@Override
				public float unclampedCall(ItemStack stack, ClientLevel p_174565_, LivingEntity living, int p_174567_)
				{
					boolean currentlyUsingThisItem = living != null && living.isUsingItem() && living.getUseItem() == stack;
					
					if(currentlyUsingThisItem)
					{
						return living.getTicksUsingItem() / 40.0F;
					}
					else
					{
						return 0.0F;
					}
				}
			});
		});
	}
	
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(TrollTNTRenderer.LAYER_LOCATION, TrollTNTModel::createBodyLayer);
    }
}
