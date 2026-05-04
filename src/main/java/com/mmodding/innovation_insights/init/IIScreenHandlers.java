package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.menu.generator.AnvilFissionGeneratorScreenHandler;
import com.mmodding.innovation_insights.menu.engine.CompressorScreenHandler;
import com.mmodding.innovation_insights.menu.engine.ExtractorScreenHandler;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import com.mmodding.mmodding_lib.library.screenhandlers.CustomScreenHandlerType;

public class IIScreenHandlers implements ElementsInitializer {

    public static final CustomScreenHandlerType<CompressorScreenHandler> COMPRESSOR_HANDLER = new CustomScreenHandlerType<>(CompressorScreenHandler::new);

    public static final CustomScreenHandlerType<ExtractorScreenHandler> EXTRACTOR_HANDLER = new CustomScreenHandlerType<>(ExtractorScreenHandler::new);

    public static final CustomScreenHandlerType<AnvilFissionGeneratorScreenHandler> ANVIL_FISSION_GENERATOR_HANDLER = new CustomScreenHandlerType<>(AnvilFissionGeneratorScreenHandler::new);

	@Override
	public void register() {
		COMPRESSOR_HANDLER.register(InnovationInsights.createId("compress_handler"));
		EXTRACTOR_HANDLER.register(InnovationInsights.createId("extractor_handler"));
		ANVIL_FISSION_GENERATOR_HANDLER.register(InnovationInsights.createId("anvil_fission_generator_handler"));
	}
}
