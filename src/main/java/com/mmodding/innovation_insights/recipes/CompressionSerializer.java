package com.mmodding.innovation_insights.recipes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mmodding.mmodding_lib.library.utils.RecipeSerializationUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CompressionSerializer implements RecipeSerializer<Compression> {

    @Override
    public Compression fromJson(ResourceLocation id, JsonObject json) {
        Compression.Json recipeJson = new Gson().fromJson(json, Compression.Json.class);

		int time = recipeJson.compressionTime != 0 ? recipeJson.compressionTime : 100;

		NonNullList<Ingredient> ingredients = RecipeSerializationUtils.getIngredients(json, "ingredients", 2);

		ItemStack result = new ItemStack(
			Registry.ITEM.getOptional(new ResourceLocation(recipeJson.result)).orElseThrow(),
			recipeJson.count
		);

        return new Compression(id, time, ingredients, result);
    }

    @Override
    public Compression fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {

		int time = buf.readInt();

		NonNullList<Ingredient> ingredients = RecipeSerializationUtils.readIngredients(buf);

		ItemStack result = buf.readItem();

        return new Compression(id, time, ingredients, result);
    }

    @Override
    public void write(FriendlyByteBuf buf, Compression recipe) {
		buf.writeInt(recipe.getCompressionTime());
		RecipeSerializationUtils.writeIngredients(buf, recipe.getIngredients());
        buf.writeItem(recipe.getResultItem());
        buf.writeInt(recipe.getCompressionTime());
    }
}
