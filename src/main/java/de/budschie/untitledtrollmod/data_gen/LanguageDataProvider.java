package de.budschie.untitledtrollmod.data_gen;

import java.util.function.Supplier;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import de.budschie.untitledtrollmod.effects.MobEffectRegistry;
import de.budschie.untitledtrollmod.effects.PotionRegistry;
import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.items.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageDataProvider extends LanguageProvider
{
	public LanguageDataProvider(DataGenerator gen, String modid, String locale)
	{
		super(gen, modid, locale);
	}

	@Override
	protected void addTranslations()
	{
		addItem(() -> ItemRegistry.XRAY_HEADSET.get(), "X-Ray Headset");
		addItem(() -> ItemRegistry.UNIVERSAL_AIR_REMOVER.get(), "Universal Air Remover");
		addItem(() -> ItemRegistry.TROLL_ARROW.get(), "Troll Arrow");
		addItem(() -> ItemRegistry.TROLL_BOW.get(), "Troll Bow");
		
		addBlock(() -> BlockRegistry.BLOCKING_AIR.get(), "Blocking Air");
		addBlock(() -> BlockRegistry.TROLL_TNT.get(), "Troll TNT");
		
		addEntityType(() -> EntityRegistry.ROCKET_CREEPER.get(), "Rocket Creeper");
		addEntityType(() -> EntityRegistry.TROLL_TNT.get(), "Troll TNT");
		addEntityType(() -> EntityRegistry.SHEEP_HOPPER.get(), "Sheep Hopper");
		
		addEffect(() -> MobEffectRegistry.JESUS_JUICE_EFFECT.get(), "Jesus");
		
		addPotionEffectName(() -> PotionRegistry.JESUS_JUICE.get(), "Jesus Juice");
		addPotionEffectName(() -> PotionRegistry.LONG_JESUS_JUICE.get(), "Long Jesus Juice");
	}
	
	public void addPotionEffectName(Supplier<Potion> potion, String name)
	{
		add("item.minecraft.potion.effect." + potion.get().getRegistryName().getPath() , name);
		add("item.minecraft.lingering_potion.effect." + potion.get().getRegistryName().getPath() , "Lingering " + name);
		add("item.minecraft.splash_potion.effect." + potion.get().getRegistryName().getPath() , "Splashable " + name);
	}
}
