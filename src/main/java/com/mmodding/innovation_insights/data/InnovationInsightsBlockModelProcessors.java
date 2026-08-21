package com.mmodding.innovation_insights.data;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.block.EnergyCableBlock;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class InnovationInsightsBlockModelProcessors {

	public static void registerEnergyCable(BlockModelGenerators generator, Block block) {
		MultiVariant core = plainVariant(InnovationInsights.createId("block/energy_cable_core"));
		MultiVariant up = plainVariant(InnovationInsights.createId("block/energy_cable_up"));
		MultiVariant down = plainVariant(InnovationInsights.createId("block/energy_cable_down"));
		MultiVariant west = plainVariant(InnovationInsights.createId("block/energy_cable_west"));
		MultiVariant north = plainVariant(InnovationInsights.createId("block/energy_cable_north"));
		MultiVariant east = plainVariant(InnovationInsights.createId("block/energy_cable_east"));
		MultiVariant south = plainVariant(InnovationInsights.createId("block/energy_cable_south"));
		generator.blockStateOutput.accept(
			MultiPartGenerator.multiPart(block)
				.with(core)
				.with(condition(EnergyCableBlock.UP, true), up)
				.with(condition(EnergyCableBlock.DOWN, true), down)
				.with(condition(EnergyCableBlock.WEST, true), west)
				.with(condition(EnergyCableBlock.NORTH, true), north)
				.with(condition(EnergyCableBlock.EAST, true), east)
				.with(condition(EnergyCableBlock.SOUTH, true), south)
		);
		generator.registerSimpleFlatItemModel(block.asItem());
	}
}
