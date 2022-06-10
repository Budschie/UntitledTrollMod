package de.budschie.untitledtrollmod.data_gen;

import java.util.function.Consumer;

import de.budschie.untitledtrollmod.items.ItemRegistry;
import de.budschie.untitledtrollmod.tags.ModItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class CraftingRecipeProvider extends RecipeProvider
{
	public CraftingRecipeProvider(DataGenerator pGenerator)
	{
		super(pGenerator);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer)
	{
		circled(pFinishedRecipeConsumer, ItemRegistry.TROLL_TNT.get(), 8, Items.TNT, ItemRegistry.TROLL_SUBSTANCE.get());
		circled(pFinishedRecipeConsumer, ItemRegistry.BLOCKING_AIR.get(), 8, Items.GLASS, ItemRegistry.TROLL_SUBSTANCE.get());
		
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.UNIVERSAL_AIR_REMOVER.get())
		.define('B', Items.IRON_BLOCK)
		.define('I', Items.IRON_INGOT)
		.define('L', Items.LEVER)
		.define('R', Items.REDSTONE)
		.pattern("  B")
		.pattern(" IR")
		.pattern("I L"), Items.IRON_INGOT, Items.REDSTONE)
		.save(pFinishedRecipeConsumer);
		
		circled(pFinishedRecipeConsumer, ItemRegistry.ANTI_GRAVITATIONAL_DEVICE.get(), 1, Items.RED_WOOL, ItemRegistry.BLOCKING_AIR.get());
		
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.TROLL_BOW.get())
		.define('I', Items.STICK)
		.define('S', Items.STRING)
		.pattern(" SI")
		.pattern("S I")
		.pattern(" SI"), Items.STRING)
		.save(pFinishedRecipeConsumer);
		
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.XRAY_HEADSET.get())
		.define('G', Items.LIGHT_BLUE_STAINED_GLASS_PANE)
		.define('R', Items.REDSTONE)
		.define('T', Items.REDSTONE_TORCH)
		.define('I', Items.IRON_INGOT)
		.pattern("TRT")
		.pattern("GIG")
		.pattern("III"), Items.IRON_INGOT, Items.REDSTONE)
		.save(pFinishedRecipeConsumer);
		
		requiresTag(ShapedRecipeBuilder.shaped(ItemRegistry.TROLL_SUBSTANCE.get(), 9)
		.define('Y', Items.YELLOW_WOOL)
		.define('W', Items.WHITE_WOOL)
		.define('L', Items.LIGHT_BLUE_WOOL)
		.define('R', Items.RED_WOOL)
		.pattern("YYY")
		.pattern("WYW")
		.pattern("LRL"), ItemTags.WOOL)
		.save(pFinishedRecipeConsumer);
		
		circled(pFinishedRecipeConsumer, ItemRegistry.FAKE_DIRT.get(), 8, Items.BROWN_WOOL, ItemRegistry.TROLL_SUBSTANCE.get());
		
		requiresTag(requiresItem(ShapelessRecipeBuilder.shapeless(ItemRegistry.FAKE_FARMLAND.get()).requires(ItemRegistry.FAKE_DIRT.get()).requires(ModItemTags.HOES),
				ItemRegistry.FAKE_DIRT.get()), ModItemTags.HOES).save(pFinishedRecipeConsumer);
	}
	
	private void circled(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike finished, int finCount, ItemLike outerItem, ItemLike innerItem)
	{
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(finished, finCount).define('O', outerItem).define('I', innerItem).pattern("OOO").pattern("OIO").pattern("OOO");
		
		requiresItem(builder, outerItem, innerItem);
		
		builder.save(pFinishedRecipeConsumer);
	}	
	
	private RecipeBuilder requiresItem(RecipeBuilder builder, ItemLike...requirements)
	{
		for(ItemLike requirement : requirements)
		{
			builder.unlockedBy(getHasName(requirement), has(requirement));
		}
				
		return builder;
	}
	
	@SafeVarargs
	private RecipeBuilder requiresTag(RecipeBuilder builder, TagKey<Item>...itemTags)
	{
		for(TagKey<Item> requirement : itemTags)
		{
			builder.unlockedBy("has_tag_" + requirement.location().toString().replace(':', '_'), has(requirement));
		}
		
		return builder;
	}
	
//	private void shapeless(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike finished, int finCount, ItemLike... requiredItems)
//	{
//		ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(finished, finCount);
//		
//		for(ItemLike item : requiredItems)
//			builder.requires(item);
//		
//		builder.save(pFinishedRecipeConsumer);
//	}
}
