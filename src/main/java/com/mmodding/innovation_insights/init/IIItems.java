package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import com.mmodding.innovation_insights.item.InnovatingItem;
import com.mmodding.innovation_insights.item.InnovationEnergyFluxMeter;
import com.mmodding.innovation_insights.item.Wrench;
import com.mmodding.library.core.api.AdvancedContainer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Function;

public class IIItems {

    public static final Item WRENCH = register("wrench", Wrench::new, new Item.Properties().stacksTo(1));

	public static final Item INNOVATION_ENERGY_FLUX_METER = register("innovation_energy_flux_meter", InnovationEnergyFluxMeter::new, new Item.Properties().stacksTo(1));

	public static final Item COPPER_BATTERY = register("copper_battery", p -> new InnovatingItem(100000, 10000, 10000, p), new Item.Properties().stacksTo(1));

	public static final Item ALUMINIUM_BATTERY = register("aluminium_battery", p -> new InnovatingItem(250000, 25000, 25000, p), new Item.Properties().stacksTo(1));

	public static final Item ANVIL_DEBRIS = register("anvil_debris", new Item.Properties());

	public static final Item ANVIL_FRAGMENTS = register("anvil_fragments", new Item.Properties().stacksTo(16));

    public static final Item STEEL_INGOT = register("steel_ingot", new Item.Properties());

	public static final Item RAW_BAUXITE = register("raw_bauxite", new Item.Properties());

	public static final Item BAUXITE_FRAGMENT = register("bauxite_fragment", new Item.Properties());

    public static final Item ALUMINIUM_SHARD = register("aluminium_shard", new Item.Properties());

    // AssemblerFurnace Aluminium Shard
    public static final Item ALUMINIUM_INGOT = register("aluminium_ingot", new Item.Properties());

    public static final Item ALUMINIUM_THERMAL_PROTECTION = register("aluminium_thermal_protection", new Item.Properties());

	private static Item register(String path, Item.Properties properties) {
		return register(path, Item::new, properties);
	}

	private static Item register(String path, Function<Item.Properties, Item> factory, Item.Properties properties) {
		ResourceKey<Item> key = InnovationInsights.createKey(Registries.ITEM, path);
		return Items.registerItem(key, factory, properties);
	}

	public static void register(AdvancedContainer mod) {}
}
