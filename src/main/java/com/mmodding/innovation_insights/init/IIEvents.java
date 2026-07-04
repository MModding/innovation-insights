package com.mmodding.innovation_insights.init;

import com.mmodding.innovation_insights.energy.InnovationEnergyFlux;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.api.item.ItemEnergy;
import com.mmodding.library.java.api.color.Color;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;

public class IIEvents {

    public static void register(AdvancedContainer mod) {
        ItemTooltipCallback.EVENT.register((stack, player, context, lines) -> {
	        EnergyAccess access = ItemEnergy.query(stack);

			if (access != null && access.unit() == InnovationEnergyFlux.UNIT) {

                MutableComponent amountText = ComponentUtils.wrapInSquareBrackets(Component.translatable("ief.innovation_insights.amount"))
                    .withStyle(style -> style.withColor(Color.rgb(60, 75, 245).toDecimal()));

                MutableComponent amountValue = Component.literal(String.valueOf(access.amount())).withStyle(style -> style.withColor(ChatFormatting.GREEN));

                MutableComponent capacityText = ComponentUtils.wrapInSquareBrackets(Component.translatable("ief.innovation_insights.capacity"))
                    .withStyle(style -> style.withColor(Color.rgb(120, 15, 245).toDecimal()));

                MutableComponent capacityValue = Component.literal(String.valueOf(access.capacity())).withStyle(style -> style.withColor(ChatFormatting.RED));

                lines.add(Component.empty().append(amountText).append(" ").append(amountValue));
				lines.add(Component.empty().append(capacityText).append(" ").append(capacityValue));
            }
        });
    }
}
