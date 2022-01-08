package de.budschie.untitledtrollmod.entities;

import de.budschie.untitledtrollmod.entities.classes.entities.SheepHopper;
import de.budschie.untitledtrollmod.entities.classes.entities.TrollArrowEntity;
import de.budschie.untitledtrollmod.entities.classes.entities.TrollTNTEntity;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(bus = Bus.MOD)
public class EntityRegistry
{
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, UntitledMainClass.MODID);
	
	public static final RegistryObject<EntityType<TrollArrowEntity>> TROLL_ARROW = REGISTRY.register("troll_arrow", () ->
	EntityType.Builder.of(new EntityFactory<TrollArrowEntity>()
	{
		@Override
		public TrollArrowEntity create(EntityType<TrollArrowEntity> p_20722_, Level level)
		{
			return new TrollArrowEntity(level);
		}	
	}, MobCategory.MISC)
	.sized(1, 1)
	.setTrackingRange(40)
	.build("troll_arrow"));
	
	public static final RegistryObject<EntityType<TrollTNTEntity>> TROLL_TNT = REGISTRY.register("troll_tnt", () ->
	EntityType.Builder.of(new EntityFactory<TrollTNTEntity>()
	{
		@Override
		public TrollTNTEntity create(EntityType<TrollTNTEntity> p_20722_, Level level)
		{
			return new TrollTNTEntity(level);
		}	
	}, MobCategory.CREATURE)
	.sized(1, 1)
	.setTrackingRange(40)
	.build("troll_tnt"));
		
	public static final RegistryObject<EntityType<SheepHopper>> SHEEP_HOPPER = REGISTRY.register("sheep_hopper", () ->
	EntityType.Builder.of(new EntityFactory<SheepHopper>()
	{
		@Override
		public SheepHopper create(EntityType<SheepHopper> p_20722_, Level level)
		{
			return new SheepHopper(level);
		}	
	}, MobCategory.CREATURE)
	.sized(1, 1)
	.setTrackingRange(40)
	.build("troll_tnt"));
	
	@SubscribeEvent
	public static void onCreatingEntityAttributes(EntityAttributeCreationEvent event)
	{
		event.put(TROLL_TNT.get(), TrollTNTEntity.setupAttributes());
		event.put(SHEEP_HOPPER.get(), Sheep.createAttributes().build());
	}
}
