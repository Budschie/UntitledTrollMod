package de.budschie.untitledtrollmod.main;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import de.budschie.untitledtrollmod.caps.aggressive_animal.IAggressiveAnimal;
import de.budschie.untitledtrollmod.caps.crouch_lock.ICrouchLock;
import de.budschie.untitledtrollmod.caps.fake_xray.IFakeXray;
import de.budschie.untitledtrollmod.effects.MobEffectRegistry;
import de.budschie.untitledtrollmod.effects.PotionRegistry;
import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.items.ItemRegistry;
import de.budschie.untitledtrollmod.networking.MainNetworkChannel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = UntitledMainClass.MODID)
@EventBusSubscriber(bus = Bus.MOD)
public class UntitledMainClass
{
	public static final String MODID = "untitledtrollmod";
		
	public UntitledMainClass()
	{
		EntityRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
		ItemRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
		BlockRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
		MobEffectRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
		PotionRegistry.REGISTRY.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event)
	{
		MainNetworkChannel.registerPackets();
		
		event.enqueueWork(() ->
		{
			addPotionShort(Potions.WATER, Items.BREAD, PotionRegistry.JESUS_JUICE.get());
			addPotionShort(PotionRegistry.JESUS_JUICE.get(), Items.REDSTONE, PotionRegistry.LONG_JESUS_JUICE.get());
		});
	}
	
	private static void addPotionShort(Potion potionIn, Item ingredient, Potion potionOut)
	{
		BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(Items.POTION.getDefaultInstance(), potionIn)), Ingredient.of(ingredient), PotionUtils.setPotion(Items.POTION.getDefaultInstance(), potionOut));
	}
	
	@SubscribeEvent
	public static void onModifyingAttributes(EntityAttributeModificationEvent event)
	{
		for(EntityType<? extends Mob> entityType : Events.WHITELIST_TROLL_ATTACK.resolve().get())
		{
			event.add(entityType, Attributes.ATTACK_DAMAGE, 5.0d);
		}
	}
	
	@SubscribeEvent
	public static void onRegisteringCaps(RegisterCapabilitiesEvent event)
	{
		event.register(IAggressiveAnimal.class);
		event.register(IFakeXray.class);
		event.register(ICrouchLock.class);
	}
}
