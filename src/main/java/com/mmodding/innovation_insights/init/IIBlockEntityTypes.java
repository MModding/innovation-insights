package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.block.entity.CreativeEnergyProviderBlockEntity;
import com.mmodding.innovation_insights.block.old.entity.engine.CompressorBlockEntity;
import com.mmodding.innovation_insights.block.old.entity.engine.ExtractorBlockEntity;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class IIBlockEntityTypes {

    public static final BlockEntityType<CompressorBlockEntity> COMPRESSOR_ENTITY = FabricBlockEntityTypeBuilder.create(CompressorBlockEntity::new, IIBlocks.COMPRESSOR).build();

    public static final BlockEntityType<ExtractorBlockEntity> EXTRACTOR_ENTITY = FabricBlockEntityTypeBuilder.create(ExtractorBlockEntity::new, IIBlocks.EXTRACTOR).build();

	public static final BlockEntityType<CreativeEnergyProviderBlockEntity> CREATIVE_ENERGY_PROVIDER = FabricBlockEntityTypeBuilder.create(CreativeEnergyProviderBlockEntity::new, IIBlocks.CREATIVE_ENERGY_PROVIDER).build();

    public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "compressor_entity", COMPRESSOR_ENTITY);
		mod.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "extractor_entity", EXTRACTOR_ENTITY);
		mod.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "creative_energy_provider", CREATIVE_ENERGY_PROVIDER);
    }
}
