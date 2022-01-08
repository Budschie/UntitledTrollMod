package de.budschie.untitledtrollmod.entities.classes.entities;

import java.util.List;

import de.budschie.untitledtrollmod.entities.EntityRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HopperMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class SheepHopper extends Sheep implements Hopper, MenuProvider
{
	private int cooldown = 0;
	private NonNullList<ItemStack> items = NonNullList.withSize(5, new ItemStack(Items.AIR));
	
	private LazyOptional<IItemHandler> wrapper = LazyOptional.of(() -> new InvWrapper(this));
	
	public SheepHopper(EntityType<? extends Sheep> entityType, Level level)
	{
		super(entityType, level);
	}
	
	public SheepHopper(Level level)
	{
		super(EntityRegistry.SHEEP_HOPPER.get(), level);
	}
	
	public SheepHopper(Sheep sheep)
	{
		super(EntityRegistry.SHEEP_HOPPER.get(), sheep.getLevel());
		
		// Lazy but effective...
		CompoundTag sheepData = new CompoundTag();
		sheep.addAdditionalSaveData(sheepData);
		
		this.readAdditionalSaveData(sheepData);
		
		this.copyPosition(sheep);
		this.setXRot(sheep.getXRot());
		this.setYRot(sheep.getYRot());
		this.setYHeadRot(sheep.getYHeadRot());
		this.setYBodyRot(sheep.yBodyRot);
	}
	
	@Override
	protected void registerGoals()
	{
		super.registerGoals();
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		if(isAlive() && --cooldown <= 0)
		{
			suckInItems(4);
		}
	}
	
	protected void suckInItems(int cooldown)
	{
		this.cooldown = cooldown;
		
		List<ItemEntity> list = this.level.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(0.5D, 0.5D, 0.5D), EntitySelector.ENTITY_STILL_ALIVE);
		
		if (!list.isEmpty())
		{
			HopperBlockEntity.addItem(this, list.get(0));
		}
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag tag)
	{
		super.readAdditionalSaveData(tag);
		
		this.cooldown = tag.getInt("Cooldown");
		
		ContainerHelper.loadAllItems(tag, items);
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag tag)
	{
		super.addAdditionalSaveData(tag);
		
		tag.putInt("Cooldown", cooldown);
		
		ContainerHelper.saveAllItems(tag, items);
	}
	
	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand)
	{
		if(player.isCrouching())
		{
	         PiglinAi.angerNearbyPiglins(player, true);
	         
	         player.openMenu(this);
	         
	         return InteractionResult.SUCCESS;
		}
		else
		{
			return super.mobInteract(player, hand);
		}
	}

	@Override
	public int getContainerSize()
	{
		return items.size();
	}

	@Override
	public boolean isEmpty()
	{
		return items.isEmpty();
	}

	@Override
	public ItemStack getItem(int index)
	{
		return items.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int amount)
	{
		return ContainerHelper.removeItem(items, index, amount);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index)
	{
		return items.remove(index);
	}

	@Override
	public void setItem(int index, ItemStack itemStack)
	{
		items.set(index, itemStack);
	}

	@Override
	public void setChanged()
	{
	}

	@Override
	public boolean stillValid(Player player)
	{
		return !(this.isRemoved() || this.distanceToSqr(player) > 64.0D);
	}

	@Override
	public void clearContent()
	{
		this.items.clear();
	}

	@Override
	public double getLevelX()
	{
		return this.getX();
	}

	@Override
	public double getLevelY()
	{
		return this.getY();
	}

	@Override
	public double getLevelZ()
	{
		return this.getZ();
	}
	
	@Override
	public boolean hurt(DamageSource p_27567_, float p_27568_)
	{
		boolean bool = super.hurt(p_27567_, p_27568_);
		
		if(!isAlive())
		{
			Containers.dropContents(this.level, this.blockPosition(), items);
			items.clear();
		}
		
		return bool;
	}

	@Override
	public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_)
	{
		return new HopperMenu(p_39954_, p_39955_, this);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction facing)
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return wrapper.cast();
		
		return super.getCapability(capability, facing);
	}
	
	@Override
	public void invalidateCaps()
	{
		super.invalidateCaps();
		
		wrapper.invalidate();
	}
	
	@Override
	public void reviveCaps()
	{
		super.reviveCaps();
		
		wrapper = LazyOptional.of(() -> new InvWrapper(this));
	}
}
