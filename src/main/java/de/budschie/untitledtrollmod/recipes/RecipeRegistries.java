package de.budschie.untitledtrollmod.recipes;

import de.budschie.untitledtrollmod.main.UntitledMainClass;
import de.budschie.untitledtrollmod.recipes.HoeRecipe.HoeRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistries
{
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, UntitledMainClass.MODID);
	
	public static final RegistryObject<RecipeSerializer<?>> HOE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("hoe_recipes", HoeRecipeSerializer::new);
}
