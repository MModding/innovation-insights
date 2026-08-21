package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.library.core.api.AdvancedContainer;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.BiPredicate;

public class IICreativeModTabs {

	public static final CreativeModeTab BASICS = create("basics", IIItems.WRENCH, (itemResourceKey, item) -> true);
	public static final CreativeModeTab REACTORS = create("reactors", IIBlocks.THERMAL_REACTOR_FRAME.asItem(), (itemResourceKey, item) -> false);
	public static final CreativeModeTab GENERATORS = create("generators", IIBlocks.CONDENSED_OBSIDIAN.asItem(), (itemResourceKey, item) -> false);
	public static final CreativeModeTab ENGINES = create("engines", IIBlocks.COMPRESSOR.asItem(), (itemResourceKey, item) -> false);
	public static final CreativeModeTab MATERIALS = create("materials", IIItems.STEEL_INGOT, (itemResourceKey, item) -> false);
	public static final CreativeModeTab ORES = create("ores", IIBlocks.BAUXITE_BLOCK.asItem(), (itemResourceKey, item) -> false);

	public static CreativeModeTab create(String translation, Item icon, BiPredicate<ResourceKey<Item>, Item> filter) {
		return FabricCreativeModeTab.builder()
			.title(Component.translatable("itemGroup.innovation_insights." + translation))
			.icon(icon::getDefaultInstance)
			.displayItems(
				(_, output) -> BuiltInRegistries.ITEM.stream()
					.filter(
						item -> item.builtInRegistryHolder().key().identifier().getNamespace().equals(InnovationInsights.namespace())
							&& filter.test(item.builtInRegistryHolder().key(), item)
					)
					.forEach(output::accept)
			)
			.build();
	}

    public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.CREATIVE_MODE_TAB, factory -> {
			factory.register("basics", BASICS);
			factory.register("reactors", REACTORS);
			factory.register("generators", GENERATORS);
			factory.register("engines", ENGINES);
			factory.register("materials", MATERIALS);
			factory.register("ores", ORES);
		});
    }
}
