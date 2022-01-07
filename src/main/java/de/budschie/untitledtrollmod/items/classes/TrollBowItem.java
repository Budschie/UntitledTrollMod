package de.budschie.untitledtrollmod.items.classes;

import java.util.function.Predicate;

import de.budschie.untitledtrollmod.items.ItemRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TrollBowItem extends BowItem
{
	public TrollBowItem(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public Predicate<ItemStack> getAllSupportedProjectiles()
	{
		return stack -> stack.getItem() == ItemRegistry.TROLL_ARROW.get();
	}
	
	@Override
	public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int remainingTicks)
	{
		super.onUseTick(level, livingEntity, itemStack, remainingTicks);
		
		// Very funny
		if(remainingTicks == (getUseDuration(itemStack) - 39))
		{
			if(level.isClientSide())
			{
				level.playLocalSound(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.WOOD_FALL, SoundSource.PLAYERS, 2, 2, false);
				level.playLocalSound(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.WOOD_FALL, SoundSource.PLAYERS, 2, 1, false);
				level.playLocalSound(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 2, 1, false);
			}
			
			itemStack.hurtAndBreak(1, livingEntity, player -> 
			{ 
				player.broadcastBreakEvent(player.getUsedItemHand());				
			});
		}
	}
	
	@Override
	public int getUseDuration(ItemStack p_40680_)
	{
		return 40;
	}
}
