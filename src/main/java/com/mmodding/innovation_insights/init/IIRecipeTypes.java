package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.recipes.Compression;
import com.mmodding.innovation_insights.recipes.Extraction;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import net.minecraft.core.Registry;

public class IIRecipeTypes implements ElementsInitializer {

    public static final Compression.Type COMPRESSION = new Compression.Type();
    public static final Extraction.Type EXTRACTION = new Extraction.Type();

    @Override
    public void register() {
        Registry.register(Registry.RECIPE_TYPE, InnovationInsights.createId("compression"), COMPRESSION);
        Registry.register(Registry.RECIPE_TYPE, InnovationInsights.createId("extraction"), EXTRACTION);
    }
}
