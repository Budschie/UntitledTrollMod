package de.budschie.untitledtrollmod.entities.classes;

import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.items.ItemRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;

public class TrollArrowEntity extends AbstractArrow implements ItemSupplier
{
	private static final LazyOptional<ItemStack> ITEM_TO_DISPLAY = LazyOptional.of(() -> new ItemStack(ItemRegistry.TROLL_ARROW.get()));
	
	public TrollArrowEntity(Level level)
	{
		super(EntityRegistry.TROLL_ARROW.get(), level);
	}
	
	public TrollArrowEntity(LivingEntity shooter, Level level)
	{
		super(EntityRegistry.TROLL_ARROW.get(), shooter, level);
	}

	@Override
	protected ItemStack getPickupItem()
	{
		return new ItemStack(ItemRegistry.TROLL_ARROW.get(), 1);
	}

	@Override
	public ItemStack getItem()
	{
		return ITEM_TO_DISPLAY.resolve().get();
	}
}
