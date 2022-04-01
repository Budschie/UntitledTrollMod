package de.budschie.untitledtrollmod.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.budschie.untitledtrollmod.caps.crouch_lock.CrouchLockProvider;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

// Minecraft's code is so bad that we need a mixin for displaying sneak when we have the CROUCHING pose...
@Mixin(value = LocalPlayer.class)
public class LocalPlayerMixin
{
	@Shadow
	Input input;
	
	@Inject(at = @At("HEAD"), method = "isShiftKeyDown", cancellable = true)
	private void shiftkeyDownMixin(CallbackInfoReturnable<Boolean> callback)
	{
		((Player)((Object)this)).getCapability(CrouchLockProvider.CAP).ifPresent(cap ->
		{
			if(cap.getCrouchLockedTicks() > 0)
			{
				callback.setReturnValue(false);
			}
		});
	}
}
