package com.mmodding.innovation_insights;

import com.mmodding.innovation_insights.init.*;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.registry.IdentifierUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import net.minecraft.world.item.ItemStack;

public class InnovationInsights implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(IIBlocks::register);
		manager.content(IIBlockEntities::register);
		manager.content(IIItems::register);
		manager.content(IIEvents::register);
		manager.content(IIItemGroups::register);
		manager.content(IIFeatures::register);
		manager.content(IIScreenHandlers::register);
		manager.content(IIRecipeSerializers::register);
		manager.content(IIRecipeTypes::register);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	public static String namespace() {
		return "innovation_insights";
	}

	public static Identifier createId(String path) {
		return Identifier.fromNamespaceAndPath(namespace(), path);
	}

	public static Identifier createTexture(String path) {
		return IdentifierUtil.texture(namespace(), path);
	}

	public static <T> ResourceKey<T> createKey(ResourceKey<? extends Registry<T>> registry, String path) {
		return ResourceKey.create(registry, createId(path));
	}

	public static boolean excludeBasics(ItemStack stack) {
		return !stack.is(IIItems.INNOVATION_ENERGY_FLUX_METER) && !stack.is(IIItems.WRENCH);
	}
}
