package de.budschie.untitledtrollmod.entities.classes.entities;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

import de.budschie.untitledtrollmod.utils.MathUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class RocketCreeper extends Creeper
{
	private static final EntityDataAccessor<Integer> DATA_CREEPER_STATE = SynchedEntityData.defineId(RocketCreeper.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<OptionalInt> DATA_TARGETTED_ENTITY = SynchedEntityData.defineId(RocketCreeper.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);
	private static final int MAX_LAUNCH_TICKS = 60;
	private static final int MAX_LAUNCHED_TICKS = 120;
	
	private int launchTicks = 0;
	private int launchedTicks = 0;
	
	private UUID entityToKillServer;
	private Vec3 currentTargettedPos;
	private Vec3 currentHomingDirection;
	
	public RocketCreeper(EntityType<? extends Creeper> p_32278_, Level p_32279_)
	{
		super(p_32278_, p_32279_);
	}
	
	@Override
	public void aiStep()
	{
		super.aiStep();
		
		// Update entity variable
		if(!this.level.isClientSide && this.getRocketCreeperState() == RocketCreeperState.HOMING && getEntityToKill().isEmpty() && this.entityToKillServer != null)
		{
			Entity foundEntity = ((ServerLevel)this.level).getEntity(entityToKillServer);
			
			if(foundEntity != null)
				setEntityToKill(foundEntity);
		}
				
		this.currentHomingDirection = null;
		
		if(!this.level.isClientSide())
		{
			if(this.getTarget() != null && this.getRocketCreeperState() == RocketCreeperState.DEFAULT)
			{
				this.setEntityToKill(getTarget());
				this.setRocketCreeperState(RocketCreeperState.LAUNCHING);
			}
			else if(this.getEntityToKill().isEmpty() && this.getRocketCreeperState() == RocketCreeperState.LAUNCHING)
			{
				this.setRocketCreeperState(RocketCreeperState.LAUNCHING);
				resetTickCounters();
			}
		}
		
		switch (getRocketCreeperState())
		{
		case LAUNCHING:
			launchTicks++;
			
			spawnClouds(0, 0, 0, 8, new Vec3(0, -0.1, 0));
			
			if(this.level.isClientSide)
				this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.HOSTILE, 2, 0, false);
			
			if(!this.level.isClientSide() && launchTicks >= MAX_LAUNCH_TICKS)
			{
				this.level.playSound(null, this, SoundEvents.CREEPER_PRIMED, SoundSource.HOSTILE, 2, 0);
				setRocketCreeperState(RocketCreeperState.LAUNCHED);
				resetTickCounters();
			}
			
			break;
		case LAUNCHED:
			launchedTicks++;
			
			if(this.level.isClientSide)
				this.level.playLocalSound(getX(), getY(), getZ(), SoundEvents.CREEPER_PRIMED, SoundSource.HOSTILE, 2, 0, false);
			
			this.setDeltaMovement(0, 1, 0);
			
			spawnClouds(0, -1, 0, 16, new Vec3(0, -1, 0));
			
			if(!this.level.isClientSide && launchedTicks >= MAX_LAUNCHED_TICKS)
			{
				this.level.playSound(null, this.blockPosition(), SoundEvents.CREEPER_HURT, SoundSource.HOSTILE, MAX_LAUNCH_TICKS, MAX_LAUNCHED_TICKS);
				
				Optional<Entity> entity = getEntityToKill();
				
				if(entity.isPresent())
				{
					setRocketCreeperState(RocketCreeperState.HOMING);
					resetTickCounters();
					updateTargetPos();
				}
				else
				{
					this.discard();
				}
			}
			break;
		case HOMING:
			updateTargetPos();
			
			Vec3 distance = currentTargettedPos.subtract(this.position());
			Vec3 movement = distance.normalize();
			
			spawnClouds(-movement.x, -movement.y, -movement.z, 20, movement.scale(-1));
			
			this.currentHomingDirection = movement;
			this.setDeltaMovement(movement.scale(1.5f));
			
			ProjectileUtil.rotateTowardsMovement(this, 0.35f);
			
			if(distance.lengthSqr() < (10 * 10))
				this.ignite();
			
			break;
			
		default:
		}
	}
	
	@Override
	public boolean requiresCustomPersistence()
	{
		return super.requiresCustomPersistence() || getRocketCreeperState() != RocketCreeperState.DEFAULT;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound)
	{
		super.addAdditionalSaveData(pCompound);
		
		if(this.launchedTicks != 0)
			pCompound.putInt("launched_ticks", this.launchedTicks);
		
		if(this.launchTicks != 0)
			pCompound.putInt("launch_ticks", this.launchTicks);
		
		if(this.currentTargettedPos != null)
		{
			pCompound.putDouble("x_target", this.currentTargettedPos.x);
			pCompound.putDouble("y_target", this.currentTargettedPos.y);
			pCompound.putDouble("z_target", this.currentTargettedPos.z);
		}
		
		if(this.entityToKillServer != null)
			pCompound.putUUID("uuid_target", this.entityToKillServer);
		
		pCompound.putString("rocket_creeper_state", this.getRocketCreeperState().toString().toLowerCase());
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound)
	{
		super.readAdditionalSaveData(pCompound);
		
		this.launchedTicks = pCompound.getInt("launched_ticks");
		this.launchTicks = pCompound.getInt("launch_ticks");
		
		if(pCompound.contains("x_target", Tag.TAG_DOUBLE))
		{
			this.currentTargettedPos = new Vec3(pCompound.getDouble("x_target"), pCompound.getDouble("y_target"), pCompound.getDouble("z_target"));
		}
		
		if(pCompound.contains("uuid_target", Tag.TAG_INT_ARRAY))
			this.entityToKillServer = pCompound.getUUID("uuid_target");
				
		RocketCreeperState parsedCreeperState = RocketCreeperState.DEFAULT;
		
		if(pCompound.contains("rocket_creeper_state", Tag.TAG_STRING))
		{
			String rocketCreeperStateName = pCompound.getString("rocket_creeper_state");

			// Just silently accept our fate
			try
			{
				parsedCreeperState = RocketCreeperState.valueOf(rocketCreeperStateName.toUpperCase());
			}
			catch(IllegalArgumentException ex)
			{
				
			}
		}
		
		this.setRocketCreeperState(parsedCreeperState);
	}
	
	@Override
	public boolean hurt(DamageSource pSource, float pAmount)
	{
		if(getRocketCreeperState() == RocketCreeperState.HOMING && pSource.isFall())
			explodeCreeper();
		
		return super.hurt(pSource, pAmount);
	}
	
	private void spawnClouds(double xOff, double yOff, double zOff, int amount, Vec3 dir)
	{
		for(int i = 0; i < amount; i++)
		{
			this.level.addParticle(ParticleTypes.CLOUD, this.getX() + MathUtils.getRangedRandom(getRandom(), 1) + xOff, this.getY() + MathUtils.getRangedRandom(getRandom(), 1) + yOff, this.getZ() + MathUtils.getRangedRandom(getRandom(), 1) + zOff, dir.x, dir.y, dir.z);
		}
	}
	
	public void updateTargetPos()
	{
		Optional<Entity> entityToKill = getEntityToKill();
		
		if(entityToKill.isPresent())
		{
			this.currentTargettedPos = entityToKill.get().position();
		}
	}
	
	public Vec3 getCurrentHomingDirection()
	{
		return currentHomingDirection;
	}
	
	public Vec3 getCurrentTargettedPos()
	{
		return currentTargettedPos;
	}
	
	@Override
	public void tick()
	{		
		super.tick();				
	}

	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(DATA_CREEPER_STATE, 0);
		this.entityData.define(DATA_TARGETTED_ENTITY, OptionalInt.empty());
	}
	
	public Optional<Entity> getEntityToKill()
	{
		OptionalInt entityId = this.entityData.get(DATA_TARGETTED_ENTITY);
		
		if(entityId.isPresent())
		{
			return Optional.ofNullable(this.level.getEntity(entityId.getAsInt()));
		}
		else
		{
			return Optional.empty();
		}
	}
	
	public void setEntityToKill(Entity entityToKill)
	{
		if(!this.level.isClientSide())
		{
			this.entityToKillServer = entityToKill.getUUID();
			this.entityData.set(DATA_TARGETTED_ENTITY, OptionalInt.of(entityToKill.getId()));
		}
	}
	
	public RocketCreeperState getRocketCreeperState()
	{
		return RocketCreeperState.values()[this.entityData.get(DATA_CREEPER_STATE)];
	}
	
	public void setRocketCreeperState(RocketCreeperState state)
	{
		this.entityData.set(DATA_CREEPER_STATE, state.ordinal());
	}
	
	public void resetTickCounters()
	{
		this.launchedTicks = 0;
		this.launchTicks = 0;
	}
	
	public static enum RocketCreeperState
	{
		DEFAULT, LAUNCHING, LAUNCHED, HOMING
	}
}
