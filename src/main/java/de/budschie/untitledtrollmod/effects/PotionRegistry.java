package de.budschie.untitledtrollmod.effects;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PotionRegistry
{
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, UntitledMainClass.MODID);
	
	public static final RegistryObject<Potion> JESUS_JUICE = REGISTRY.register("jesus_juice", () -> new Potion(new MobEffectInstance(MobEffectRegistry.JESUS_JUICE_EFFECT.get(), 20 * 60 * 4, 0)));
	public static final RegistryObject<Potion> LONG_JESUS_JUICE = REGISTRY.register("long_jesus_juice", () -> new Potion(new MobEffectInstance(MobEffectRegistry.JESUS_JUICE_EFFECT.get(), 20 * 60 * 8, 0)));
}
