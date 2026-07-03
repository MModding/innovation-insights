package com.mmodding.innovation_insights.init;

import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import com.mmodding.mmodding_lib.library.utils.Colors;
import com.mmodding.mmodding_lib.library.utils.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import org.quiltmc.qsl.tooltip.api.client.ItemTooltipCallback;

public class IIEvents implements ElementsInitializer {

    @Override
    public void register() {
        ItemTooltipCallback.EVENT.register((stack, player, context, lines) -> {
            if (stack.getItem() instanceof InnovationEnergyFluxOld.Item IEF) {

                MutableComponent amountText = ComponentUtils.wrapInSquareBrackets(Component.translatable("ief.innovation_insights.amount"))
                    .withStyle(style -> style.withColor(new Colors.RGB(60, 75, 245).toDecimal()));

                MutableComponent amountValue = Component.literal(String.valueOf(IEF.getIEF(stack))).withStyle(style -> style.withColor(ChatFormatting.GREEN));

                MutableComponent capacityText = ComponentUtils.wrapInSquareBrackets(Component.translatable("ief.innovation_insights.capacity"))
                    .withStyle(style -> style.withColor(new Colors.RGB(120, 15, 245).toDecimal()));

                MutableComponent capacityValue = Component.literal(String.valueOf(IEF.getCapacity(stack))).withStyle(style -> style.withColor(ChatFormatting.RED));

                lines.add(TextUtils.spaceBetween(amountText, amountValue));
                lines.add(TextUtils.spaceBetween(capacityText, capacityValue));
            }
        });
    }
}
