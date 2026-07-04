package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.block.engine.CompressorBlock;
import com.mmodding.innovation_insights.block.engine.ExtractorBlock;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class IIBlocks {

	public static final Block COMPRESSOR = register("compressor", CompressorBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).destroyTime(3.0f).requiresCorrectToolForDrops()).registerItem();

	public static final Block CONDENSED_OBSIDIAN = register("condensed_obsidian", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(100.0f, 2400.0f)).registerItem();

	public static final Block STEEL_BLOCK = register("steel_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).destroyTime(5.0f).requiresCorrectToolForDrops()).registerItem();

	/* public static final AnvilFissionGenerator ANVIL_FISSION_GENERATOR = new AnvilFissionGenerator(
		QuiltBlockSettings.of(Material.METAL).destroyTime(5.0f).requiresCorrectToolForDrops(),
		true,
		IIItemGroups.INNOVATION_INSIGHTS_GENERATORS
	); */

	public static final Block EXTRACTOR = register("extractor", ExtractorBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).destroyTime(5.0f).requiresCorrectToolForDrops()).registerItem();

	public static final Block BAUXITE_ORE = register("bauxite_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).destroyTime(3.0f).requiresCorrectToolForDrops()).registerItem();

	public static final Block DEEPSLATE_BAUXITE_ORE = register("deepslate_bauxite_ore", BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).destroyTime(4.5f)).registerItem();

	public static final Block BAUXITE_BLOCK = register("bauxite_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)).registerItem();

	// public static final Block FURNACE_ASSEMBLER = register("furnace_assembler", );

	public static final Block ALUMINIUM_BLOCK = register("aluminium_block", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)).registerItem();

	public static final Block THERMAL_GLASS = register("thermal_glass", BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).destroyTime(0.5f)).registerItem();

	public static final Block THERMAL_REACTOR_INTERFACE = register("thermal_reactor_interface", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).destroyTime(5.0f).requiresCorrectToolForDrops()).registerItem();

	public static final Block THERMAL_REACTOR_CORE = register("thermal_reactor_core", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).destroyTime(5.0f).requiresCorrectToolForDrops()).registerItem();

	public static final Block THERMAL_REACTOR_CONTAINER = register("thermal_reactor_container", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).destroyTime(5.0f).requiresCorrectToolForDrops().noOcclusion()).registerItem();

	public static final Block THERMAL_REACTOR_FRAME = register("thermal_reactor_frame", BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).destroyTime(5.0f).requiresCorrectToolForDrops()).registerItem();

	public static Block register(String string, BlockBehaviour.Properties properties) {
		return register(string, Block::new, properties);
	}

	public static <T extends Block> Block register(String string, BlockFactory<T> factory, BlockBehaviour.Properties properties) {
		return Blocks.register(ResourceKey.create(Registries.BLOCK, InnovationInsights.createId(string)), factory::make, properties);
	}

	public static void register(AdvancedContainer mod) {}
}
