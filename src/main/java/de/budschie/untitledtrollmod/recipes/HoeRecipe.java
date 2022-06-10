package de.budschie.untitledtrollmod.recipes;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.google.gson.JsonObject;

import de.budschie.untitledtrollmod.tags.ModItemTags;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class HoeRecipe implements CraftingRecipe
{
	private ResourceLocation id;
	private Ingredient otherItem;
	private ItemStack result;
	
	public HoeRecipe(ResourceLocation id, Ingredient otherItem, ItemStack result)
	{
		this.id = id;
		this.otherItem = otherItem;
		this.result = result;
	}
	
	@Override
	public boolean matches(CraftingContainer container, Level p_44003_)
	{
		return searchAndReturnIngredient(container, otherItem).size() == 1 && searchAndReturnTag(container, ModItemTags.HOES).size() == 1;
	}
	
	private ArrayList<Integer> searchAndReturnTag(CraftingContainer container, TagKey<Item> item)
	{
		return searchAndReturn(container, toCheck -> ForgeRegistries.ITEMS.tags().getTag(item).contains(toCheck));
	}
	
	private ArrayList<Integer> searchAndReturnIngredient(CraftingContainer container, Ingredient ingredient)
	{
		return searchAndReturn(container, toCheck -> ingredient.test(new ItemStack(toCheck)));
	}
	
	private ArrayList<Integer> searchAndReturn(CraftingContainer container, Predicate<Item> matchingFunction)
	{
		ArrayList<Integer> list = new ArrayList<>();
		
		for(int i = 0; i < container.getContainerSize(); i++)
		{
			if(matchingFunction.test(container.getItem(i).getItem()))
				list.add(i);
		}
		
		return list;
	}

	@Override
	public ItemStack assemble(CraftingContainer container)
	{
		return getResultItem();
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer container)
	{
	    NonNullList<ItemStack> nonnulllist = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
		
		ArrayList<Integer> hoes = searchAndReturnTag(container, ModItemTags.HOES);
		
		ItemStack newHoeStack = container.getItem(hoes.get(0));
		newHoeStack.setDamageValue(newHoeStack.getDamageValue() + 1);
		
		if(newHoeStack.getDamageValue() >= newHoeStack.getMaxDamage())
		{
			newHoeStack.shrink(1);
			newHoeStack.setDamageValue(0);
		}
		
		nonnulllist.set(hoes.get(0), newHoeStack.copy());

		return nonnulllist;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height)
	{
		return (width * height) >= 2;
	}

	@Override
	public ItemStack getResultItem()
	{
		return result.copy();
	}

	@Override
	public ResourceLocation getId()
	{
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer()
	{
		return RecipeRegistries.HOE_RECIPE_SERIALIZER.get();
	}
	
	public static class HoeRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<HoeRecipe>
	{		
		@Override
		public HoeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe)
		{
			return new HoeRecipe(pRecipeId, Ingredient.fromJson(pSerializedRecipe.get("ingredient")), ShapedRecipe.itemStackFromJson((JsonObject) pSerializedRecipe.get("result")));
		}

		@Override
		public HoeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer)
		{
			return new HoeRecipe(pBuffer.readResourceLocation(), Ingredient.fromNetwork(pBuffer), pBuffer.readItem());
		}

		@Override
		public void toNetwork(FriendlyByteBuf pBuffer, HoeRecipe pRecipe)
		{
			pBuffer.writeResourceLocation(pRecipe.getId());
			pRecipe.otherItem.toNetwork(pBuffer);
			pBuffer.writeItem(pRecipe.result);
		}
	}
	
	public static class HoeRecipeBuilder
	{
		ItemStack result;
		Ingredient ingredient;
		
		private HoeRecipeBuilder(ItemStack result)
		{
			this.result = result;
		}
		
		public static HoeRecipeBuilder create(Item item)
		{
			return new HoeRecipeBuilder(new ItemStack(item));
		}
		
		public static HoeRecipeBuilder create(ItemStack item)
		{
			return new HoeRecipeBuilder(item);
		}
		
		public HoeRecipeBuilder setIngredient(Ingredient ingredient)
		{
			this.ingredient = ingredient;
			return this;
		}
		
		public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer)
		{
			pFinishedRecipeConsumer.accept(new FinishedHoeRecipeBuilder(result.getItem().getRegistryName(), ingredient, result));
		}
		
		public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation rl)
		{
			pFinishedRecipeConsumer.accept(new FinishedHoeRecipeBuilder(rl, ingredient, result));
		}
		
		public static class FinishedHoeRecipeBuilder implements FinishedRecipe
		{
			private ResourceLocation resourceLocation;
			private Ingredient ingredient;
			private ItemStack result;

			public FinishedHoeRecipeBuilder(ResourceLocation resourceLocation, Ingredient ingredient, ItemStack result)
			{
				this.resourceLocation = resourceLocation;
				this.ingredient = ingredient;
				this.result = result;
			}

			@Override
			public void serializeRecipeData(JsonObject pJson)
			{
				JsonObject itemStackObject = new JsonObject();
				itemStackObject.addProperty("count", result.getCount());
				itemStackObject.addProperty("item", result.getItem().getRegistryName().toString());
				
				pJson.add("ingredient", ingredient.toJson());
				pJson.add("result", itemStackObject);
			}

			@Override
			public ResourceLocation getId()
			{
				return resourceLocation;
			}

			@Override
			public RecipeSerializer<?> getType()
			{
				return RecipeRegistries.HOE_RECIPE_SERIALIZER.get();
			}

			@Override
			public JsonObject serializeAdvancement()
			{
				return null;
			}

			@Override
			public ResourceLocation getAdvancementId()
			{
				return null;
			}
		}
	}
}
