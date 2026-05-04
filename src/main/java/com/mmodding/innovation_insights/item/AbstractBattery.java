package com.mmodding.innovation_insights.item;

import com.mmodding.innovation_insights.InnovationEnergyFlux;
import com.mmodding.mmodding_lib.library.items.CustomItem;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractBattery extends CustomItem implements InnovationEnergyFlux.Item {

    public AbstractBattery(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return this.isIEFVisible(stack);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return this.getIEFStep(stack);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return this.getIEFColor(stack).toDecimal();
    }
}
