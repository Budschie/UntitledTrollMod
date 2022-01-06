package de.budschie.untitledtrollmod.entities;

import de.budschie.untitledtrollmod.entities.classes.TrollArrowEntity;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
}
