package de.budschie.untitledtrollmod.entities.classes;

import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.items.ItemRegistry;
import de.budschie.untitledtrollmod.networking.MainNetworkChannel;
import de.budschie.untitledtrollmod.networking.packets.RandomizeKeyBindings;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;

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
	
	@Override
	protected void onHitEntity(EntityHitResult entityHitResult)
	{
		super.onHitEntity(entityHitResult);
		
		if(!entityHitResult.getEntity().getLevel().isClientSide() && entityHitResult.getEntity() instanceof ServerPlayer sp)
		{
			MainNetworkChannel.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sp), new RandomizeKeyBindings.RandomizeKeyBindingsPacket());
		}
	}
}
