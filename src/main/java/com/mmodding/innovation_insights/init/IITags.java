package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class IITags {

	public static final TagKey<Item> BATTERIES = TagKey.create(Registries.ITEM, InnovationInsights.createId("batteries"));
}
