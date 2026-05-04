package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;

public class IIItemGroups implements ElementsInitializer {

    public static final CreativeModeTab INNOVATION_INSIGHTS_BASICS = QuiltItemGroup.createWithIcon(
        InnovationInsights.createId("basic"),
        () -> new ItemStack(IIItems.WRENCH)
    );

    public static final CreativeModeTab INNOVATION_INSIGHTS_REACTORS = QuiltItemGroup.createWithIcon(
        InnovationInsights.createId("reactors"),
        () -> new ItemStack(IIBlocks.THERMAL_REACTOR_FRAME)
    );

    public static final CreativeModeTab INNOVATION_INSIGHTS_GENERATORS = QuiltItemGroup.createWithIcon(
        InnovationInsights.createId("generators"),
        () -> new ItemStack(IIBlocks.ANVIL_FISSION_GENERATOR)
    );

    public static final CreativeModeTab INNOVATION_INSIGHTS_ENGINES = QuiltItemGroup.createWithIcon(
        InnovationInsights.createId("engines"),
        () -> new ItemStack(IIBlocks.COMPRESSOR)
    );

    public static final CreativeModeTab INNOVATION_INSIGHTS_MATERIALS = QuiltItemGroup.createWithIcon(
        InnovationInsights.createId("materials"),
        () -> new ItemStack(IIItems.STEEL_INGOT)
    );

    public static final CreativeModeTab INNOVATION_INSIGHTS_ORES = QuiltItemGroup.createWithIcon(
        InnovationInsights.createId("ores"),
        () -> new ItemStack(IIBlocks.BAUXITE_ORE)
    );

    @Override
    public void register() {}
}
