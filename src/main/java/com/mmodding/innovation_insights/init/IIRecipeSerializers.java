package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.recipe.Compression;
import com.mmodding.innovation_insights.recipe.Extraction;
import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class IIRecipeSerializers {

	public static final RecipeSerializer<Compression> COMPRESSION = new RecipeSerializer<>(Compression.MAP_CODEC, Compression.STREAM_CODEC);
	public static final RecipeSerializer<Extraction> EXTRACTION = new RecipeSerializer<>(Extraction.MAP_CODEC, Extraction.STREAM_CODEC);

    public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.RECIPE_SERIALIZER, "compression", COMPRESSION);
		mod.register(BuiltInRegistries.RECIPE_SERIALIZER, "extraction", EXTRACTION);
    }
}
