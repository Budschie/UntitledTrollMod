package de.budschie.untitledtrollmod.items.classes;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class AntiGravitationalDevice extends Item
{
	public AntiGravitationalDevice(Properties pProperties)
	{
		super(pProperties);
	}
	
	@Override
	public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced)
	{
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
		
		pTooltipComponents.add(new TranslatableComponent(getDescriptionId() + ".hover").withStyle(ChatFormatting.DARK_PURPLE));
	}
}
