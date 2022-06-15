package de.budschie.untitledtrollmod.data_gen;

import java.util.function.Consumer;

import de.budschie.untitledtrollmod.items.ItemRegistry;
import de.budschie.untitledtrollmod.recipes.HoeRecipe.HoeRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
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
		// circled(pFinishedRecipeConsumer, ItemRegistry.TROLL_TNT.get(), 8, Items.TNT, Items.ROTTEN_FLESH, Items.ROTTEN_FLESH);
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.TROLL_TNT.get(), 7)
		.define('E', Items.TNT)
		.define('T', ItemRegistry.TROLL_SUBSTANCE.get())
		.define('R', Items.ROTTEN_FLESH)
		.pattern("ETE")
		.pattern("ERE")
		.pattern("EEE"), ItemRegistry.TROLL_SUBSTANCE.get())
		.save(pFinishedRecipeConsumer);
		
		circled(pFinishedRecipeConsumer, ItemRegistry.BLOCKING_AIR.get(), 8, Items.GLASS, ItemRegistry.TROLL_SUBSTANCE.get(), ItemRegistry.TROLL_SUBSTANCE.get());
		
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.UNIVERSAL_AIR_REMOVER.get())
		.define('B', Items.IRON_BLOCK)
		.define('I', Items.IRON_INGOT)
		.define('L', Items.LEVER)
		.define('R', Items.REDSTONE)
		.pattern("  B")
		.pattern(" IR")
		.pattern("I L"), Items.IRON_INGOT, Items.REDSTONE)
		.save(pFinishedRecipeConsumer);
		
		circled(pFinishedRecipeConsumer, ItemRegistry.ANTI_GRAVITATIONAL_DEVICE.get(), 1, Items.RED_WOOL, ItemRegistry.BLOCKING_AIR.get(), ItemRegistry.BLOCKING_AIR.get());
		
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
		
		circled(pFinishedRecipeConsumer, ItemRegistry.FAKE_DIRT.get(), 8, Items.BROWN_WOOL, ItemRegistry.TROLL_SUBSTANCE.get(), ItemRegistry.TROLL_SUBSTANCE.get());
		
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.FAKE_GRASS_BLOCK.get(), 6)
		.define('G', Items.GREEN_DYE)
		.define('D', ItemRegistry.FAKE_DIRT.get())
		.pattern("GGG")
		.pattern("DDD")
		.pattern("DDD"), ItemRegistry.FAKE_DIRT.get())
		.save(pFinishedRecipeConsumer);
		
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.FAKE_TNT.get(), 8)
		.define('R', Items.RED_WOOL)
		.define('W', Items.WHITE_WOOL)
		.define('T', ItemRegistry.TROLL_SUBSTANCE.get())
		.pattern("RRR")
		.pattern("WTW")
		.pattern("RRR"), ItemRegistry.TROLL_SUBSTANCE.get())
		.save(pFinishedRecipeConsumer);
		
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.FAKE_CRAFTING_TABLE.get(), 2)
		.define('W', ItemTags.PLANKS)
		.define('T', ItemRegistry.TROLL_SUBSTANCE.get())
		.pattern("WWW")
		.pattern("WTW")
		.pattern("WWW"), ItemRegistry.TROLL_SUBSTANCE.get())
		.save(pFinishedRecipeConsumer);
		
		circled(pFinishedRecipeConsumer, ItemRegistry.FAKE_OBSIDIAN.get(), 8, Items.BLACK_WOOL, ItemRegistry.TROLL_SUBSTANCE.get(), ItemRegistry.TROLL_SUBSTANCE.get());
		
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.FAKE_BEDROCK.get(), 8)
		.define('L', Items.LIGHT_GRAY_WOOL)
		.define('D', Items.GRAY_WOOL)
		.define('T', ItemRegistry.TROLL_SUBSTANCE.get())
		.pattern("DLD")
		.pattern("LTL")
		.pattern("DLD"), ItemRegistry.TROLL_SUBSTANCE.get())
		.save(pFinishedRecipeConsumer);
		
		circled(pFinishedRecipeConsumer, ItemRegistry.FAKE_NETHERITE_BLOCK.get(), 1, Items.BROWN_DYE, ItemRegistry.FAKE_DIAMOND_BLOCK.get(), ItemRegistry.FAKE_DIAMOND_BLOCK.get());
		
		requiresItem(ShapedRecipeBuilder.shaped(ItemRegistry.FAKE_ANCIENT_DEBRIS.get(), 8)
		.define('L', Items.NETHERRACK)
		.define('D', Items.BROWN_DYE)
		.define('T', ItemRegistry.TROLL_SUBSTANCE.get())
		.pattern("DLD")
		.pattern("LTL")
		.pattern("DLD"), ItemRegistry.TROLL_SUBSTANCE.get())
		.save(pFinishedRecipeConsumer);
		
		circled(pFinishedRecipeConsumer, ItemRegistry.FAKE_DIAMOND_BLOCK.get(), 8, Items.CYAN_WOOL, ItemRegistry.TROLL_SUBSTANCE.get(), ItemRegistry.TROLL_SUBSTANCE.get());
		
//		requiresTag(requiresItem(ShapelessRecipeBuilder.shapeless(ItemRegistry.FAKE_FARMLAND.get()).requires(ItemRegistry.FAKE_DIRT.get()).requires(ModItemTags.HOES),
//				ItemRegistry.FAKE_DIRT.get()), ModItemTags.HOES).save(pFinishedRecipeConsumer);
		
		HoeRecipeBuilder.create(ItemRegistry.FAKE_FARMLAND.get()).setIngredient(Ingredient.of(ItemRegistry.FAKE_DIRT.get())).save(pFinishedRecipeConsumer);
	}
	
	private void circled(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike finished, int finCount, ItemLike outerItem, ItemLike innerItem, ItemLike requiredItem)
	{
		ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(finished, finCount).define('O', outerItem).define('I', innerItem).pattern("OOO").pattern("OIO").pattern("OOO");
		
		requiresItem(builder, requiredItem);
		
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
