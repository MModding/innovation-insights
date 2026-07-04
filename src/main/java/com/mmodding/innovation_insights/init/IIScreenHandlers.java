package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.menu.engine.CompressorMenu;
import com.mmodding.innovation_insights.menu.engine.ExtractorMenu;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.inventory.api.menu.BasicInventoryAccessContainerMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class IIScreenHandlers {

    public static final MenuType<CompressorMenu> COMPRESSOR = new MenuType<>(BasicInventoryAccessContainerMenu.menuSupplier(CompressorMenu::new, 3), FeatureFlags.DEFAULT_FLAGS);

    public static final MenuType<ExtractorMenu> EXTRACTOR = new MenuType<>(BasicInventoryAccessContainerMenu.menuSupplier(ExtractorMenu::new, 7), FeatureFlags.DEFAULT_FLAGS);

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.MENU, "compressor", COMPRESSOR);
		mod.register(BuiltInRegistries.MENU, "extractor", EXTRACTOR);
	}
}
