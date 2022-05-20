package de.budschie.untitledtrollmod.items.classes;

import de.budschie.untitledtrollmod.blocks.BlockRegistry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

/** Removes air. **/
public class UniversalAirRemover extends Item
{
	public UniversalAirRemover(Properties pProperties)
	{
		super(pProperties);
	}

	@Override
	public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context)
	{
		if(context.getClickedPos() != null)
		{
			Block clickedBlock = context.getLevel().getBlockState(context.getClickedPos()).getBlock();
			
			// Who needs tags anyway?
			if(clickedBlock == BlockRegistry.BLOCKING_AIR.get())
			{
				if(!context.getLevel().isClientSide())
				{
					context.getLevel().destroyBlock(context.getClickedPos(), true);
					
					stack.hurtAndBreak(1, context.getPlayer(), (living) ->
					{
						living.broadcastBreakEvent(EquipmentSlot.MAINHAND);
					});
				}
				
				return InteractionResult.CONSUME;
			}
		}
		
		return super.onItemUseFirst(stack, context);
	}
}
