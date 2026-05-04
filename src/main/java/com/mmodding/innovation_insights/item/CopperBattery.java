package com.mmodding.innovation_insights.item;

import com.mmodding.innovation_insights.InnovationEnergyFlux;
import net.minecraft.world.item.ItemStack;

public class CopperBattery extends AbstractBattery implements InnovationEnergyFlux.Item {

	public CopperBattery(Settings settings) {
		super(settings);
	}

	@Override
	public long getEnergyCapacity(ItemStack stack) {
		return 100000;
	}

	@Override
	public long getEnergyMaxInput(ItemStack stack) {
		return 10000;
	}

	@Override
	public long getEnergyMaxOutput(ItemStack stack) {
		return 10000;
	}
}
