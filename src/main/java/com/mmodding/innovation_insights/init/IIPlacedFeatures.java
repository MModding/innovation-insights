package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class IIPlacedFeatures {

	public static final ResourceKey<PlacedFeature> BAUXITE_ORE = InnovationInsights.createKey(Registries.PLACED_FEATURE, "bauxite_ore");

	public static void register(AdvancedContainer mod) {
		// BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES, BAUXITE_ORE);
    }
}
