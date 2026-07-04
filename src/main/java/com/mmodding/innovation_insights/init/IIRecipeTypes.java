package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.recipe.Compression;
import com.mmodding.innovation_insights.recipe.Extraction;
import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class IIRecipeTypes {

	public static final RecipeType<Compression> COMPRESSION = register("compression");
	public static final RecipeType<Extraction> EXTRACTION = register("extraction");

	static <T extends Recipe<?>> RecipeType<T> register(String name) {
		return Registry.register(BuiltInRegistries.RECIPE_TYPE, InnovationInsights.createId(name), new RecipeType<T>() {

			@Override
			public String toString() {
				return name;
			}
		});
	}

    public static void register(AdvancedContainer mod) {}
}
