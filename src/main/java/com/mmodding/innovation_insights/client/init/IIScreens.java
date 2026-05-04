package com.mmodding.innovation_insights.client.init;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.innovation_insights.client.screen.engine.CompressorScreen;
import com.mmodding.innovation_insights.client.screen.engine.ExtractorScreen;
import com.mmodding.innovation_insights.client.screen.generator.AnvilFissionGeneratorScreen;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class IIScreens implements ClientElementsInitializer {

    @Override
    public void registerClient() {
        MenuScreens.register(IIScreenHandlers.COMPRESSOR_HANDLER, CompressorScreen::new);
        MenuScreens.register(IIScreenHandlers.EXTRACTOR_HANDLER, ExtractorScreen::new);
        MenuScreens.register(IIScreenHandlers.ANVIL_FISSION_GENERATOR_HANDLER, AnvilFissionGeneratorScreen::new);
    }
}
