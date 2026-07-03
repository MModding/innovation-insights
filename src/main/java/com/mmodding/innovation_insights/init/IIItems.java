package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.item.InnovationEnergyFluxMeter;
import com.mmodding.innovation_insights.item.Wrench;

public class IIItems implements ElementsInitializer {

    public static final Wrench WRENCH = new Wrench(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_BASICS).maxCount(1));

	public static final InnovationEnergyFluxMeter INNOVATION_ENERGY_FLUX_METER = new InnovationEnergyFluxMeter(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_BASICS).maxCount(1));

	public static final CopperBattery COPPER_BATTERY = new CopperBattery(100000, 10000, 10000, new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_BASICS).maxCount(1));

	public static final AluminiumBattery ALUMINIUM_BATTERY = new AluminiumBattery(250000, 25000, 25000, new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_BASICS).maxCount(1));

	public static final CustomItem ANVIL_DEBRIS = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

	public static final CustomItem ANVIL_FRAGMENTS = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS).maxCount(16));

    public static final CustomItem STEEL_INGOT = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

	public static final CustomItem RAW_BAUXITE = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

	public static final CustomItem BAUXITE_FRAGMENT = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

    public static final CustomItem ALUMINIUM_SHARD = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

    // AssemblerFurnace Aluminium Shard
    public static final CustomItem ALUMINIUM_INGOT = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

    public static final CustomItem ALUMINIUM_THERMAL_PROTECTION = new CustomItem(new AdvancedItemSettings().group(IIItemGroups.INNOVATION_INSIGHTS_MATERIALS));

	@Override
	public void register() {
		WRENCH.register(InnovationInsights.createId("wrench"));
		INNOVATION_ENERGY_FLUX_METER.register(InnovationInsights.createId("innovation_energy_flux_meter"));
		COPPER_BATTERY.register(InnovationInsights.createId("copper_battery"));
		ALUMINIUM_BATTERY.register(InnovationInsights.createId("aluminium_battery"));
		ANVIL_DEBRIS.register(InnovationInsights.createId("anvil_debris"));
		ANVIL_FRAGMENTS.register(InnovationInsights.createId("anvil_fragments"));
		STEEL_INGOT.register(InnovationInsights.createId("steel_ingot"));
		RAW_BAUXITE.register(InnovationInsights.createId("raw_bauxite"));
		BAUXITE_FRAGMENT.register(InnovationInsights.createId("bauxite_fragment"));
		ALUMINIUM_SHARD.register(InnovationInsights.createId("aluminium_shard"));
		ALUMINIUM_INGOT.register(InnovationInsights.createId("aluminium_ingot"));
		ALUMINIUM_THERMAL_PROTECTION.register(InnovationInsights.createId("aluminium_thermal_protection"));
	}
}
