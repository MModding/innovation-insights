package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.block.entity.engine.CompressorEntity;
import com.mmodding.innovation_insights.block.entity.engine.ExtractorEntity;
import com.mmodding.innovation_insights.block.entity.generator.AnvilFissionGeneratorEntity;
import com.mmodding.mmodding_lib.library.blockentities.CustomBlockEntityType;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import team.reborn.energy.api.EnergyStorage;

public class IIBlockEntities implements ElementsInitializer {

    public static final CustomBlockEntityType<CompressorEntity> COMPRESSOR_ENTITY = new CustomBlockEntityType<>(CompressorEntity::new, IIBlocks.COMPRESSOR);

    public static final CustomBlockEntityType<ExtractorEntity> EXTRACTOR_ENTITY = new CustomBlockEntityType<>(ExtractorEntity::new, IIBlocks.EXTRACTOR);

    public static final CustomBlockEntityType<AnvilFissionGeneratorEntity> ANVIL_FISSION_GENERATOR_ENTITY = new CustomBlockEntityType<>(AnvilFissionGeneratorEntity::new, IIBlocks.ANVIL_FISSION_GENERATOR);

    @Override
    public void register() {
        COMPRESSOR_ENTITY.createAndRegister(InnovationInsights.createId("compressor_entity"));
        EXTRACTOR_ENTITY.createAndRegister(InnovationInsights.createId("extractor_entity"));
        ANVIL_FISSION_GENERATOR_ENTITY.createAndRegister(InnovationInsights.createId("anvil_fission_generator"));

        this.registerEnergyStorage(COMPRESSOR_ENTITY);
        this.registerEnergyStorage(EXTRACTOR_ENTITY);
		this.registerEnergyStorage(ANVIL_FISSION_GENERATOR_ENTITY);
    }

    public void registerEnergyStorage(CustomBlockEntityType<?> customBlockEntityType) {
        EnergyStorage.SIDED.registerForBlockEntity(
            (blockEntity, direction) -> ((InnovationEnergyFluxOld.Container) blockEntity).getEnergyStorage(),
            customBlockEntityType.getBlockEntityTypeIfCreated()
        );
    }
}
