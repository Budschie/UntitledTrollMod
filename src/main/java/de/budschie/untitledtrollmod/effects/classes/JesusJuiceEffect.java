package de.budschie.untitledtrollmod.effects.classes;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.common.ForgeMod;

public class JesusJuiceEffect extends MobEffect
{
	public JesusJuiceEffect()
	{
		super(MobEffectCategory.BENEFICIAL, 16777215);
		
		this.addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "b2b44cc8-d86c-4d1d-989d-a785a385778b", 1, Operation.ADDITION);
	}
}
