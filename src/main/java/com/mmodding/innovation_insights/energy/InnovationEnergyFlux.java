package com.mmodding.innovation_insights.energy;

import com.mmodding.library.energy.api.EnergyUnit;

public class InnovationEnergyFlux implements EnergyUnit {

	public static final EnergyUnit UNIT = new InnovationEnergyFlux();

	private InnovationEnergyFlux() {}

	@Override
	public long toFabricEnergy(long amount) {
		return Math.floorDiv(amount, 10);
	}

	@Override
	public long fromFabricEnergy(long amount) {
		return amount * 10;
	}
}
