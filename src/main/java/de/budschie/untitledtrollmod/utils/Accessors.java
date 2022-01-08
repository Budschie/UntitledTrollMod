package de.budschie.untitledtrollmod.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class Accessors
{
	private static LazyOptional<Method> ambientSoundAccessor = LazyOptional.of(() -> 
	{
		Method method = ObfuscationReflectionHelper.findMethod(Mob.class, "m_7515_");
		method.setAccessible(true);
		return method;
	});
	
	public static SoundEvent getAmbientSound(Mob mob)
	{
		try
		{
			return (SoundEvent) ambientSoundAccessor.resolve().get().invoke(mob);
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
