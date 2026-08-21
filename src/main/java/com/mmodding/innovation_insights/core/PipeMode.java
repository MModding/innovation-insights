package com.mmodding.innovation_insights.core;

import net.minecraft.util.StringRepresentable;

public enum PipeMode implements StringRepresentable {
	CLOSEST,
	FURTHEST,
	ROUND_ROBIN;

	@Override
	public String getSerializedName() {
		return this.name().toLowerCase();
	}
}
