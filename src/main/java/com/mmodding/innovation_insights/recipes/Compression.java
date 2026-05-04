package com.mmodding.innovation_insights.recipes;

import com.mmodding.innovation_insights.init.IIRecipeSerializers;
import com.mmodding.innovation_insights.init.IIRecipeTypes;
import com.mmodding.innovation_insights.inventories.ImplementedInventory;
import com.mmodding.mmodding_lib.library.utils.RecipeUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class Compression implements Recipe<ImplementedInventory> {

	private final ResourceLocation compressionId;
	private final int compressionTime;
	private final NonNullList<Ingredient> ingredients;
	private final ItemStack result;

    public Compression(ResourceLocation compressionId, int compressionTime, NonNullList<Ingredient> ingredients, ItemStack result) {
		this.compressionId = compressionId;
		this.compressionTime = compressionTime;
		this.ingredients = ingredients;
		this.result = result;
    }

    public static class Type implements RecipeType<Compression> {}

    public static class Json {
		int compressionTime;
		String result;
		int count;
    }

	@Override
	public ResourceLocation getId() {
		return this.compressionId;
	}

	public int getCompressionTime() {
		return this.compressionTime;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.ingredients;
	}

	@Override
	public ItemStack getResultItem() {
		return this.result;
	}

	@Override
	public ItemStack craft(ImplementedInventory inventory) {
		return getResultItem().copy();
	}

	@Override
	public boolean matches(ImplementedInventory inventory, Level world) {
		return RecipeUtils.ingredientMatches(inventory, IntArrayList.of(1, 2), this.getIngredients());
	}

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public RecipeSerializer<Compression> getSerializer() {
        return IIRecipeSerializers.COMPRESSION_SERIALIZER;
    }

    @Override
    public RecipeType<Compression> getType() {
        return IIRecipeTypes.COMPRESSION;
    }
}
