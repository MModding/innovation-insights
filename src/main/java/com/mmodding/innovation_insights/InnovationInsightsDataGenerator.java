package com.mmodding.innovation_insights;

import com.mmodding.innovation_insights.block.EnergyCableBlock;
import com.mmodding.innovation_insights.data.InnovationInsightsBlockModelProcessors;
import com.mmodding.innovation_insights.init.IIBlocks;
import com.mmodding.innovation_insights.init.IIItems;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.datagen.api.ExtendedDataGeneratorEntrypoint;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.DefaultDataHandlers;
import com.mmodding.library.datagen.api.provider.MModdingLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class InnovationInsightsDataGenerator implements ExtendedDataGeneratorEntrypoint {

	@Override
	public void setupManager(DataManager manager) {
		manager.chain(IIBlocks.class, DefaultDataHandlers.BLOCK_MODELS)
			.chain(block -> block instanceof EnergyCableBlock, InnovationInsightsBlockModelProcessors::registerEnergyCable)
			.chain(BlockModelGenerators::createTrivialCube);
		manager.task(IIItems.class, DefaultDataHandlers.ITEM_MODELS, (generators, item) -> generators.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
		manager.task(IIBlocks.class, DefaultDataHandlers.getTranslationHandler(Registries.BLOCK, Block.class), DefaultLangProcessors.CLASSIC);
		manager.task(IIItems.class, DefaultDataHandlers.getTranslationHandler(Registries.ITEM, Item.class), DefaultLangProcessors.CLASSIC);
	}

	@Override
	public void onInitializeDataGenerator(AdvancedContainer mod, FabricDataGenerator generator, FabricDataGenerator.Pack pack) {
		pack.addProvider(InnovationInsightsLanguageProvider::new);
	}

	private static class InnovationInsightsLanguageProvider extends MModdingLanguageProvider {

		private InnovationInsightsLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
		}

		@Override
		public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translations) {
			translations.add("ief.innovation_insights.amount", "IEF Amount");
			translations.add("ief.innovation_insights.capacity", "IEF Capacity");
			translations.add("itemGroup.innovation_insights.basics", "Innovation Insights Basic");
			translations.add("itemGroup.innovation_insights.reactors", "Innovation Insights Reactors");
			translations.add("itemGroup.innovation_insights.generators", "Innovation Insights Energy Generators");
			translations.add("itemGroup.innovation_insights.engines", "Innovation Insights Engines");
			translations.add("itemGroup.innovation_insights.materials", "Innovation Insights Materials");
			translations.add("itemGroup.innovation_insights.ores", "Innovation Insights Ores");
		}
	}
}
