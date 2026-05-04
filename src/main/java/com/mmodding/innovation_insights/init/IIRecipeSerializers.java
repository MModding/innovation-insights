package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.recipes.CompressionSerializer;
import com.mmodding.innovation_insights.recipes.ExtractionSerializer;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import net.minecraft.core.Registry;

public class IIRecipeSerializers implements ElementsInitializer {

    public static final CompressionSerializer COMPRESSION_SERIALIZER = new CompressionSerializer();
    public static final ExtractionSerializer EXTRACTION_SERIALIZER = new ExtractionSerializer();

    @Override
    public void register() {
        Registry.register(Registry.RECIPE_SERIALIZER, InnovationInsights.createId("compression"), COMPRESSION_SERIALIZER);
        Registry.register(Registry.RECIPE_SERIALIZER, InnovationInsights.createId("extraction"), EXTRACTION_SERIALIZER);
    }
}
