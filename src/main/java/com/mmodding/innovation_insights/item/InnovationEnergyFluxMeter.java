package com.mmodding.innovation_insights.item;

import com.mmodding.innovation_insights.InnovationEnergyFlux;
import com.mmodding.innovation_insights.InnovationInsights;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InnovationEnergyFluxMeter extends Item implements FabricItem {

	public InnovationEnergyFluxMeter(Item.Properties properties) {
		super(properties);
	}

	public static int getIndicatorRate(ItemStack stack) {
		return Mth.clamp(stack.getOrCreateTag().getInt("IndicatorRate"), 0, 10);
	}

	public static boolean isReturningToBasePos(ItemStack stack) {
		return stack.getOrCreateTag().getBoolean("ReturningToBasePos");
	}

	private void setIndicatorRate(ItemStack stack, int indicatorRate) {
		stack.getOrCreateTag().putInt("IndicatorRate", Mth.clamp(indicatorRate, 0, 10));
	}

	private void setReturningToBasePos(ItemStack stack, boolean returningToBasePos) {
		stack.getOrCreateTag().putBoolean("ReturningToBasePos", returningToBasePos);
	}

	@Override
	public InteractionResult useOnBlock(UseOnContext context) {

		if (context.getLevel().isClientSide()) {
			return super.useOnBlock(context);
		}

		BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());

		if (Registry.BLOCK.getKey(blockState.getBlock()).getNamespace().equals(InnovationInsights.id())) {
			if (blockState.hasBlockEntity()) {
				BlockEntity blockEntity = context.getLevel().getBlockEntity(context.getClickedPos());

				if (blockEntity instanceof InnovationEnergyFlux.Container IEF) {

					ItemStack stack = context.getItemInHand();

					this.setReturningToBasePos(stack, false);

					int totalIndicatorRateRange = (int) ((double) IEF.getIEF() / IEF.getCapacity() * 10);

					int indicatorRateRange;

					if (InnovationEnergyFluxMeter.getIndicatorRate(stack) > 0) {
						indicatorRateRange = totalIndicatorRateRange - InnovationEnergyFluxMeter.getIndicatorRate(stack);
					}
					else {
						indicatorRateRange = totalIndicatorRateRange;
					}

					WorldUtils.repeatSyncedTaskEachTimeUntil(
						context.getLevel(), 2, indicatorRateRange * 2L,
						() -> this.setIndicatorRate(stack, InnovationEnergyFluxMeter.getIndicatorRate(stack) + 1)
					);

					WorldUtils.doSyncedTaskAfter(context.getLevel(), indicatorRateRange * 2L + 60, () -> {
						this.setReturningToBasePos(stack, true);
						WorldUtils.repeatSyncedTaskEachTimeUntil(
							context.getWorld(), 5, totalIndicatorRateRange * 5L, () -> {
								if (InnovationEnergyFluxMeter.isReturningToBasePos(stack)) {
									this.setIndicatorRate(stack, InnovationEnergyFluxMeter.getIndicatorRate(stack) - 1);
								}
							}
						);
					});

					if (context.getPlayer() != null) {
						MutableComponent amount = ComponentUtils.wrapInSquareBrackets(Component.translatable("ief.innovation_insights.amount"))
							.withStyle(style -> style.withColor(new Colors.RGB(60, 75, 245).toDecimal()));

						MutableComponent capacity = ComponentUtils.wrapInSquareBrackets(Component.translatable("ief.innovation_insights.capacity"))
							.withStyle(style -> style.withColor(new Colors.RGB(120, 15, 245).toDecimal()));

						Component message = TextUtils.spaceBetween(
							amount,
							Component.literal(String.valueOf(IEF.getIEF())).withStyle(style -> style.withColor(ChatFormatting.GREEN)),
							Component.literal("/").withStyle(style -> style.withColor(new Colors.RGB(90, 45, 245).toDecimal())),
							capacity,
							Component.literal(String.valueOf(IEF.getCapacity())).withStyle(style -> style.withColor(ChatFormatting.RED))
						);

						context.getPlayer().displayClientMessage(message, true);
					}
				}
			}
		}

		return super.useOnBlock(context);
	}

	@Override
	public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
		return false;
	}
}
