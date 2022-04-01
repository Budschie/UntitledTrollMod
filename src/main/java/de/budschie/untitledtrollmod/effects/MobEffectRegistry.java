package de.budschie.untitledtrollmod.effects;

import de.budschie.untitledtrollmod.effects.classes.JesusJuiceEffect;
import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MobEffectRegistry
{
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, UntitledMainClass.MODID);
	
	public static final RegistryObject<MobEffect> JESUS_JUICE_EFFECT = REGISTRY.register("jesus_juice_effect", JesusJuiceEffect::new);
}
