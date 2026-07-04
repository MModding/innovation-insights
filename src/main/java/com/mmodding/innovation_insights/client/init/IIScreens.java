package com.mmodding.innovation_insights.client.init;

import com.mmodding.innovation_insights.init.IIScreenHandlers;
import com.mmodding.innovation_insights.client.screen.engine.CompressorScreen;
import com.mmodding.innovation_insights.client.screen.engine.ExtractorScreen;
import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.client.gui.screens.MenuScreens;

public class IIScreens {

    public static void register(AdvancedContainer mod) {
        MenuScreens.register(IIScreenHandlers.COMPRESSOR, CompressorScreen::new);
        MenuScreens.register(IIScreenHandlers.EXTRACTOR, ExtractorScreen::new);
    }
}
