package de.budschie.untitledtrollmod.items.classes;

import java.util.function.Predicate;

import de.budschie.untitledtrollmod.items.ItemRegistry;
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
	public void onUseTick(Level p_41428_, LivingEntity p_41429_, ItemStack p_41430_, int remainingTicks)
	{
		super.onUseTick(p_41428_, p_41429_, p_41430_, remainingTicks);
		
		// Very funny
		if(remainingTicks < (getUseDuration(p_41430_) - 60))
		{
			p_41430_.hurtAndBreak(1, p_41429_, player -> player.broadcastBreakEvent(player.getUsedItemHand()));
		}
	}
}
