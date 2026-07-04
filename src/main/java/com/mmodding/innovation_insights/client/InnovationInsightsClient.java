package com.mmodding.innovation_insights.client;

import com.mmodding.innovation_insights.client.init.IIModelPredicates;
import com.mmodding.innovation_insights.client.init.IIScreens;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.client.ExtendedClientModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;

public class InnovationInsightsClient implements ExtendedClientModInitializer {

	@Override
	public void setupManager(ElementsManager manager) {
		// manager.content(IIModelPredicates::register);
		manager.content(IIScreens::register);
	}

	@Override
	public void onInitializeClient(AdvancedContainer mod) {}
}
