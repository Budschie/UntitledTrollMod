package de.budschie.untitledtrollmod.items.classes;

import de.budschie.untitledtrollmod.entities.classes.entities.TrollArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TrollSubstanceItem extends ArrowItem
{

	public TrollSubstanceItem(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public AbstractArrow createArrow(Level level, ItemStack itemStack, LivingEntity shooter)
	{
		return new TrollArrowEntity(shooter, level);
	}
}
