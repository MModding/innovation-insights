package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.InnovationInsights;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.quiltmc.qsl.tag.api.QuiltTagKey;
import org.quiltmc.qsl.tag.api.TagType;

public class IITags {

	public static final TagKey<Item> BATTERIES = QuiltTagKey.of(Registry.ITEM_REGISTRY, InnovationInsights.createId("batteries"), TagType.NORMAL);
}
