package de.budschie.untitledtrollmod.items;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.LazyOptional;

public enum UntitledArmorMaterials implements ArmorMaterial
{
	HEADSET(new int[] {300, 300, 300, 300}, new int[] {3, 3, 3, 3}, 4, LazyOptional.of(() -> SoundEvents.ARMOR_EQUIP_IRON), Ingredient.of(() -> Items.IRON_INGOT), "headset", 0, 0);
	
	private int[] durabilities, defense;
	private int enchantmentValue;
	private LazyOptional<SoundEvent> equipSound;
	private Ingredient repairIngredient;
	private String name;
	private float toughness;
	private float knockbackResistance;
	
	private UntitledArmorMaterials(int[] durabilities, int[] defense, int enchantmentValue, LazyOptional<SoundEvent> equipSound, Ingredient repairIngredient, String name,
			float toughness, float knockbackResistance)
	{
		this.durabilities = durabilities;
		this.defense = defense;
		this.enchantmentValue = enchantmentValue;
		this.equipSound = equipSound;
		this.repairIngredient = repairIngredient;
		this.name = UntitledMainClass.MODID + ":" + name;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot pSlot)
	{
		return durabilities[pSlot.getIndex()];
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot pSlot)
	{
		return defense[pSlot.getIndex()];
	}

	@Override
	public int getEnchantmentValue()
	{
		return enchantmentValue;
	}

	@Override
	public SoundEvent getEquipSound()
	{
		return equipSound.resolve().get();
	}

	@Override
	public Ingredient getRepairIngredient()
	{
		return repairIngredient;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public float getToughness()
	{
		return toughness;
	}

	@Override
	public float getKnockbackResistance()
	{
		return knockbackResistance;
	}
}
