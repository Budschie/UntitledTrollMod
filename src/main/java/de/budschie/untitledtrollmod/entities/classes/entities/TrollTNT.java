package de.budschie.untitledtrollmod.entities.classes.entities;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

import de.budschie.untitledtrollmod.entities.EntityRegistry;
import de.budschie.untitledtrollmod.entities.classes.ai.FollowPlayerTnTGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;

public class TrollTNT extends PathfinderMob
{
	private static final EntityDataAccessor<Optional<UUID>> DATA_IGNITOR_UUID_ID = SynchedEntityData.defineId(TrollTNT.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<OptionalInt> DATA_TICKS_TO_EXPLODE_REMAINING = SynchedEntityData.defineId(TrollTNT.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);
	
	protected TrollTNT(EntityType<? extends PathfinderMob> entityType, Level level)
	{
		super(entityType, level);
	}
	
	public TrollTNT(Level level)
	{
		super(EntityRegistry.TROLL_TNT.get(), level);
	}
	
	public static AttributeSupplier setupAttributes()
	{		
		return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.15d).build();
	}
	
	@Override
	protected void registerGoals()
	{
		super.registerGoals();
		
		this.goalSelector.addGoal(0, new FollowPlayerTnTGoal(this, 3.0D));
		this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0D));
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		
		this.entityData.define(DATA_TICKS_TO_EXPLODE_REMAINING, OptionalInt.empty());
		this.entityData.define(DATA_IGNITOR_UUID_ID, Optional.empty());
	}
	
	@Override
	public void tick()
	{
		super.tick();
		
		if(this.level.isClientSide())
		{
			this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.TNT_PRIMED, SoundSource.HOSTILE, 0, 1, false);
		}
		
		OptionalInt data = this.entityData.get(DATA_TICKS_TO_EXPLODE_REMAINING);
		
		if(data.isPresent())
		{
			this.entityData.set(DATA_TICKS_TO_EXPLODE_REMAINING, OptionalInt.of(data.getAsInt() - 1));
			
			if((data.getAsInt() - 1) <= 0)
			{
				explode();
			}
			
            this.level.addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 1.1D, this.getZ(), 0.0D, 0.0D, 0.0D);
		}
	}
	
	public void explode()
	{
		this.discard();
		
		if(this.level.isClientSide())
		{
			
		}
		else
		{
			this.level.explode(this, getX(), getY(), getZ(), 3.0f, BlockInteraction.BREAK);
		}
	}
	
	public boolean isIgnited()
	{
		return this.entityData.get(DATA_TICKS_TO_EXPLODE_REMAINING).isPresent();
	}
	
	public void setIgnited(int ticksRemaining)
	{
		this.entityData.set(DATA_TICKS_TO_EXPLODE_REMAINING, OptionalInt.of(ticksRemaining));
	}
	
	public LivingEntity getIgnitor()
	{
		Optional<UUID> ignitorUUID = this.getEntityData().get(DATA_IGNITOR_UUID_ID);
		
		// There is nothin' on the client side
		if(this.level.isClientSide())
			return null;
		
		if (ignitorUUID.isPresent())
			return (LivingEntity)((ServerLevel)this.level).getEntity(ignitorUUID.get());
		else
			return null;
	}
	
	public void setIgnitor(LivingEntity player)
	{
		this.entityData.set(DATA_IGNITOR_UUID_ID, Optional.of(player.getUUID()));
	}
	
	public void clearIgnitor()
	{
		this.entityData.set(DATA_IGNITOR_UUID_ID, Optional.empty());
	}
	
	@Override
	public boolean isInvulnerableTo(DamageSource p_20122_)
	{
		return super.isInvulnerableTo(p_20122_) || p_20122_.isExplosion();
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag tag)
	{
		super.readAdditionalSaveData(tag);
		
		if(this.entityData.get(DATA_IGNITOR_UUID_ID).isPresent())
			tag.putUUID("ignitor", this.entityData.get(DATA_IGNITOR_UUID_ID).get());
		
		if(this.entityData.get(DATA_TICKS_TO_EXPLODE_REMAINING).isPresent())
			tag.putInt("remaining_till_explosion", this.entityData.get(DATA_TICKS_TO_EXPLODE_REMAINING).getAsInt());
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag tag)
	{
		super.addAdditionalSaveData(tag);
		
		if(tag.contains("ignitor"))
			this.entityData.set(DATA_IGNITOR_UUID_ID, Optional.of(tag.getUUID("ignitor")));
		
		if(tag.contains("remaining_till_explosion"))
			this.entityData.set(DATA_TICKS_TO_EXPLODE_REMAINING, OptionalInt.of(tag.getInt("remaining_till_explosion")));
	}
}
