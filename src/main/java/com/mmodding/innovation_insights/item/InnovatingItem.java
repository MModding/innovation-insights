package com.mmodding.innovation_insights.item;

import com.mmodding.innovation_insights.energy.InnovationEnergyFlux;
import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.api.item.ItemEnergy;
import com.mmodding.library.java.api.color.Color;
import com.mmodding.library.java.api.color.RGB;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public abstract class InnovatingItem extends Item {

	public InnovatingItem(int capacity, long inputRate, long outputRate, Properties properties) {
		super(properties);
		ItemEnergy.defineEnergy(
			this,
			capacity,
			InnovationEnergyFlux.UNIT,
			(_, _, internalComponent) -> EnergyAccess.limited(
				EnergyAccess.from(internalComponent),
				inputRate,
				outputRate
			)
		);
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		EnergyComponent energy = ItemEnergy.retrieveFrom(stack);
		return !energy.isFull();
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		EnergyComponent energy = ItemEnergy.retrieveFrom(stack);
		return Math.round(13.0f - energy.remaining() * 13.0f / energy.capacity());
	}

	@Override
	public int getBarColor(ItemStack stack) {
		EnergyComponent energy = ItemEnergy.retrieveFrom(stack);
		int alteration = (int) ((double) energy.amount() / energy.capacity() * 60);
		RGB rgb = Color.rgb(120, 15, 245);
		rgb.alterRed(Math.negateExact(alteration));
		rgb.alterGreen(alteration);
		return rgb.toDecimal();
	}
}
