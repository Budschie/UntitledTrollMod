package de.budschie.untitledtrollmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.budschie.untitledtrollmod.effects.MobEffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
	@Inject(method = "canStandOnFluid(Lnet/minecraft/world/level/material/Fluid;)Z", at = @At("HEAD"), cancellable = true)
	private void canStandOnFluidMixin(Fluid fluid, CallbackInfoReturnable<Boolean> callback)
	{
		LivingEntity living = ((LivingEntity)((Object)this));
		
		if(living.hasEffect(MobEffectRegistry.JESUS_JUICE_EFFECT.get()) && fluid == Fluids.WATER)
		{
			callback.setReturnValue(true);
		}
	}
}
