package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import com.mmodding.mmodding_lib.library.worldgen.features.defaults.CustomOreFeature;
import java.util.List;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

public class IIFeatures implements ElementsInitializer {

    public static final List<OreConfiguration.TargetBlockState> BAUXITE_ORE_TARGETS = List.of(
        OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, IIBlocks.BAUXITE_ORE.getDefaultState()),
        OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, IIBlocks.DEEPSLATE_BAUXITE_ORE.getDefaultState())
    );

    public static final CustomOreFeature BAUXITE_ORE_FEATURE = new CustomOreFeature(5, 5, -60, 22, BAUXITE_ORE_TARGETS);

    @Override
    public void register() {
        BAUXITE_ORE_FEATURE.register(InnovationInsights.createId("bauxite_ore_feature"));

        BAUXITE_ORE_FEATURE.addDefaultToBiomes(ctx -> ctx.canGenerateIn(DimensionOptions.OVERWORLD));
    }
}
